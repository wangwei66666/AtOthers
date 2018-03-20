package com.ww.kotlin.study.atothers;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ww
 */
public class MainActivity extends AppCompatActivity {
    private TextInputEditText edit;
    private Button btnSend;
    private TextView recTxt;
    private TextView atmTxt;
    private Button btnTurndown;
    private TextInputEditText bohuiEdit;
    private static final char COMMA_SYMBOL = ',';
    private static final String AT_SYMBOL = "@";
    private TextInputLayout textInput;
    private View view;
    private UserDialogSimpleAdapter adapter;
    List<User> mData = new ArrayList<>();
    /**
     * 存储用户信息
     */
    private List<User> list = new ArrayList<>();
    private String[] userNames = new String[]{"全选", "路飞", "索隆", "山治", "娜美", "乔巴", "罗宾", "布鲁克", "乌索普", "弗兰奇"};
    private String[] userIds = new String[]{"000", "001", "002", "003", "004", "005", "006", "007", "008", "009"};
    /**
     * 初始复选情况
     */
    boolean[] flags = new boolean[]{false, false, false, false, false, false, false, false, false};
    /**
     * 存储当前消息
     */
    private String strContent;
    private String strContent2;

    LayoutInflater inflater;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        dealView();
    }

    private void dealView() {

        int lengh = flags.length;
        for (int i = 0; i < lengh; i++) {
            User item = new User(userIds[i], userNames[i]);
            mData.add(item);
        }
        bohuiEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (before != 0) {
                    String delText = strContent2.substring(start, start + before);
                    if (delText.equals((char) 8200 + "") && start != 0) {
                        int index = text.lastIndexOf('驳', start - 1);
                        if (index != -1) {
                            //截取字符串
                            text = text.substring(0, index);
                            bohuiEdit.setText(text);
                            bohuiEdit.setSelection(index);
                        }

                    }
                }
                strContent2 = text;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                atFun(s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /*
         * 发送消息
         */
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strMsg = edit.getText().toString();
                if (TextUtils.isEmpty(strMsg)) {
                    return;
                }
                //发送@消息钱再次验证@信息的正确（防止用户修改）
                for (int index = list.size() - 1; index >= 0; index--) {
                    User user = list.get(index);
                    if (!strContent.contains("@" + user.getUserName() + (char) 8201)) {
                        list.remove(index);
                    }
                }

                //发消息
                if (list.size() == 0) {
                    sendMsg(strMsg, null);
                } else {
                    //存储所@的所有人的的全部id
                    StringBuilder strAtUserId = new StringBuilder();
                    for (User user : list) {
                        strAtUserId.append(user.getUserId());
                        strAtUserId.append(',');
                    }
                    if (strAtUserId.charAt(strAtUserId.length() - 1) == COMMA_SYMBOL) {
                        strAtUserId.deleteCharAt(strAtUserId.length() - 1);
                    }
                    sendMsg(strMsg, strAtUserId.toString());
                    list.clear();
                }
            }
        });

        /*
         * 驳回理由
         */
        btnTurndown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //文本内容
                SpannableString ss = new SpannableString("驳回理由:" + (char) 8200);
                //设置0-2的字符颜色
                int len = ss.length();
                ss.setSpan(new ForegroundColorSpan(Color.RED), 0, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                bohuiEdit.setText(ss);
                bohuiEdit.setSelection(len);

                bohuiEdit.requestFocus();
                bohuiEdit.setFocusableInTouchMode(true);
                bohuiEdit.setFocusable(true);
            }
        });

    }

    private void sendMsg(String msg, String userIds) {
        recTxt.setText(msg);
        if (userIds == null || !userIds.contains("001")) {
            atmTxt.setText("无人@你");
        } else {
            atmTxt.setText("有人@你");
        }
    }

    /**
     * EditText的TextChangedListener
     */
    private void atFun(CharSequence s, int start, int before, int count) {
        String text = s.toString();
        //增加符号
        if (before == 0) {
            String addT = text.substring(start, start + count);
            if (addT.equals(AT_SYMBOL)) {
                showDialog();
            }
        } else {
            String delText = strContent.substring(start, start + before);
            if (delText.equals((char) 8201 + "") && start != 0) {
                int index = text.lastIndexOf("@", start - 1);
                if (index != -1) {
                    //截取字符串
                    text = text.substring(0, index);
                    edit.setText(text);
                    edit.setSelection(index);
                }

            }
        }
        strContent = text;
    }

    private void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this, R.style.Theme_User_Dialog);
//        // 动态加载一个listview的布局文件进来
        view = inflater.inflate(R.layout.im_user_dialog, null);
        listview = view.findViewById(R.id.user_listview);
        adapter = new UserDialogSimpleAdapter(MainActivity.this, mData, flags);
        // 给listview加入适配器
        listview.setAdapter(adapter);
        listview.setItemsCanFocus(false);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listview.setOnItemClickListener(new ItemOnClick());
        //注：每次都需要重新获取view并设值，否则会回报“The specified child already has a parent. You must call removeView”的错误
        //不设置值，显示的列表为空
        //设置加载的listview
        alert.setView(view);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int count = 0;
                StringBuilder sb = new StringBuilder(edit.getText().toString());
                sb = new StringBuilder(sb.substring(0, sb.length() - 1));
                for (int i = 0; i < flags.length; i++) {
                    if (flags[i]) {
                        count++;
                        User user = new User();
                        user.setUserName(userNames[i]);
                        user.setUserId(userIds[i]);
                        list.add(user);
                        //添加@的名字
//                        int selectionEnd = edit.getSelectionEnd();
                        int selectionEnd = sb.length();
                        String strAt = "@" + userNames[i] + (char) 8201;
                        sb.insert(selectionEnd, strAt);
                        edit.setText(sb.toString());
                        edit.setSelection(selectionEnd + strAt.length());
                    }
                }
                Log.e("TAG", "----true的个数：" + count);
            }
        });
        alert.create();
        alert.show();
    }

    private void initView() {
        inflater = LayoutInflater.from(MainActivity.this);
        bohuiEdit = findViewById(R.id.bohui_edit);
        edit = findViewById(R.id.edit);
        btnSend = findViewById(R.id.btn_send);
        recTxt = findViewById(R.id.rec_txt);
        atmTxt = findViewById(R.id.atm_txt);
        btnTurndown = findViewById(R.id.btn_turndown);
        textInput = findViewById(R.id.text_input);
    }

    class ItemOnClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
            CheckBox cBox = (CheckBox) view.findViewById(R.id.im_dialog_checkbox);
            if (cBox.isChecked()) {
                cBox.setChecked(false);
            } else {
                Log.i("TAG", "取消该选项");
                cBox.setChecked(true);
            }

            if (position == 0 && (cBox.isChecked())) {
                //如果是选中 全选  就把所有的都选上 然后更新
                for (int i = 0; i < flags.length; i++) {
                    flags[i] = true;
                }
                adapter.notifyDataSetChanged();
            } else if (position == 0 && (!cBox.isChecked())) {
                //如果是取消全选 就把所有的都取消 然后更新
                for (int i = 0; i < flags.length; i++) {
                    flags[i] = false;
                }
                adapter.notifyDataSetChanged();
            }
            if (position != 0 && (!cBox.isChecked())) {
                // 如果把其它的选项取消   把全选取消
                flags[0] = false;
                flags[position] = false;
                adapter.notifyDataSetChanged();
            } else if (position != 0 && (cBox.isChecked())) {
                //如果选择其它的选项，看是否全部选择
                //先把该选项选中 设置为true
                flags[position] = true;
                int a = 0;
                for (int i = 1; i < flags.length; i++) {
                    if (flags[i] == false) {
                        //如果有一个没选中  就不是全选 直接跳出循环
                        break;
                    } else {
                        //计算有多少个选中的
                        a++;
                        if (a == flags.length - 1) {
                            //如果选项都选中，就把全选 选中，然后更新
                            flags[0] = true;
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }

    }
}
