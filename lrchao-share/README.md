#齐家分享模块#

##配置##
###AndroidManifest.xml1###
添加权限

        <uses-permission android:name="android.permission.GET_TASKS" />
        <uses-permission android:name="android.permission.INTERNET" />
        <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
        <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
        <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        <uses-permission android:name="android.permission.READ_PHONE_STATE" />
        <uses-permission android:name="android.permission.MANAGE_ACCOUNTS"/>
        <uses-permission android:name="android.permission.GET_ACCOUNTS"/>
        <!-- 蓝牙分享所需的权限 -->
        <uses-permission android:name="android.permission.BLUETOOTH" />
        <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" /> 
 
###混淆###

        -keep class com.tencent.mm.sdk.** {
           *;
        }
        
        -keep class com.sina.weibo.sdk.** {
           *;
        }

##微信分享##
需要在com.xxx.xxx.packagewxapi下创建WXEntryActivity，并继承WXEntryBaseActivity（com.xxx.xxx为包名）

需要在AndroidManifest.xml中添加    
            
        <activity
            android:name=".wxapi.WXEntryActivity"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:exported="true"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Translucent.NoTitleBar"/>
            
            
####使用方式####
1.在Application中     

        ShareSDK.getInstance().init(this);     
        PlatformConfig.setWeChat(APP_ID, APP_SECRET);
        PlatformConfig.setQQ(QQ_APP_ID);
  
2.调用分享面板   



        ShareClient client = new ShareClient.Builder()
               .dialogTitle("分享给业主")
               .dialogBtnTextShown(false)
               .platform(new WeChatFriend(new WebContent("https://github.com/", "标题11", "描述111",
                       R.drawable.ic_wechat_friend, "")))
               .platform(new WeChatMoment(new WebContent("https://github.com/", "标题11", "描述111",
                       R.drawable.ic_wechat_friend, "")))
               .onShareItemClickListener(this)
               .callback(this)
               .build();
        client.show(this);
           

##QQ分享##

需要在AndroidManifest.xml中添加

        <activity
            android:name="com.tencent.tauth.AuthActivity"
            android:noHistory="true"
            android:screenOrientation="portrait"
            android:launchMode="singleTask" >
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
                <data android:scheme="tencent222222" />
            </intent-filter>
        </activity>