# AtOthers
聊天中@其他人以及设置EditText中部分字体颜色

只是一个简单的实例，代码写的比较**乱**，没时间整理，可参考**思路**。


思路：@其他人： EditText的addTextChangedListener事件中onTextChanged判断是增加还是删除，若@则弹出多选框，选择后拼成字符串
例如：@路飞+（char）8201同时将useName和userId信息保存，在点击发送时再次判断（用户手动输入的@路飞无效），将user信息传过去；
接收端接收消息遍历是user信息是否与接收端userId匹配。
EditText中设置部分字体变色，通过SpannableString设置的。


效果图如下：


![@others.gif](https://upload-images.jianshu.io/upload_images/2194177-7962d570ac611c08.gif?imageMogr2/auto-orient/strip)
![效果图1.png](https://upload-images.jianshu.io/upload_images/2194177-2ed5e48398c4c468.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![效果图2.png](https://upload-images.jianshu.io/upload_images/2194177-b720aa7924cad8e1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
