package com.ww.kotlin.study.atothers;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Date:        2018/3/20 14:23
 *
 * @author ww
 */

public class UserDialogSimpleAdapter extends BaseAdapter {

    private Context mContext;
    List<User> list;
    boolean[] flags;

    public UserDialogSimpleAdapter(Context mContext, List<User> list, boolean[] flags) {
        this.mContext = mContext;
        this.list = list;
        this.flags = flags;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.im_user_item_dialog, null);
            viewHolder.tvUserName = convertView.findViewById(R.id.im_dialog_username);
            viewHolder.isChecked = convertView.findViewById(R.id.im_dialog_checkbox);
            // 通过setTag将ViewHolder和convertView绑定
            convertView.setTag(viewHolder);
        } else {
            // 获取，通过ViewHolder找到相应的控件
            viewHolder = (ViewHolder) convertView.getTag();
        }
        User itemBean = list.get(position);
        viewHolder.tvUserName.setText(itemBean.getUserName());
        if (flags[position] == true) {
            viewHolder.isChecked.setChecked(true);
        } else if (flags[position] == false) {
            viewHolder.isChecked.setChecked(false);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvUserName;
        CheckBox isChecked;
    }
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LinearLayout.inflate(mContext, R.layout.im_user_item_dialog, null);
//        }
//        CheckBox ckBox = (CheckBox) convertView.findViewById(R.id.im_dialog_checkbox);
//        //每次都根据 bl[]来更新checkbox
//        if (flags[position] == true) {
//            ckBox.setChecked(true);
//        } else if (flags[position] == false) {
//            ckBox.setChecked(false);
//        }
//        return super.getView(position, convertView, parent);
//    }

}
