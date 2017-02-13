package com.lrchao.sharesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.lrchao.share.OnShareItemClickListener;
import com.lrchao.share.ShareCallback;
import com.lrchao.share.ShareClient;
import com.lrchao.share.content.SMSContent;
import com.lrchao.share.content.WebContent;
import com.lrchao.share.platform.QQ;
import com.lrchao.share.platform.QZone;
import com.lrchao.share.platform.SMS;
import com.lrchao.share.platform.SharePlatform;
import com.lrchao.share.platform.SinaMicroBlog;
import com.lrchao.share.platform.WeChatFriend;
import com.lrchao.share.platform.WeChatMoment;
import com.lrchao.share.result.ShareResult;


/**
 * Description: 测试分享的页面
 *
 * @author liuranchao
 * @date 16/7/6 下午5:37
 */
public class FirstActivity extends AppCompatActivity implements View.OnClickListener, OnShareItemClickListener, ShareCallback {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        findViewById(R.id.btn_show_share_dialog).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show_share_dialog:
                showShareDialog();
                break;

            default:
                break;
        }
    }

    private void showShareDialog() {
        ShareClient client = new ShareClient.Builder()
                .dialogTitle("分享给业主")
                .dialogBtnTextShown(false)
                .platform(new SMS(new SMSContent("短信分享内容啊啊啊", "", "13023992342")))
                .platform(new WeChatFriend(new WebContent("https://github.com/", "标题11", "描述111",
                        R.drawable.ic_wechat_friend, "")))
                .platform(new WeChatMoment(new WebContent("https://github.com/", "标题11", "描述111",
                        R.drawable.ic_wechat_friend, "")))
                .platform(new QQ(new WebContent("https://github.com/", "标题11", "描述111",
                        R.drawable.ic_wechat_friend, "")))
                .platform(new QZone(new WebContent("https://github.com/", "标题11", "描述111",
                        R.drawable.ic_wechat_friend, "")))
                .platform(new SinaMicroBlog(new WebContent("https://github.com/", "标题11", "描述111",
                        R.drawable.ic_wechat_friend, "")))
                .platform(new SinaMicroBlog(new WebContent("https://github.com/", "标题11", "描述111",
                        R.drawable.ic_wechat_friend, "")))
                .onShareItemClickListener(this)
                .showToast(true)
                .callback(this)
                .build();
        client.show(this);
    }


    @Override
    public void onShareItemClick(SharePlatform sharePlatform) {
        Log.e(TAG, "onSharePlatformItemClick=" + sharePlatform.getTitle());
    }

    @Override
    public void onShareSuccess(ShareResult shareResult) {
        Log.e(TAG, "onShareSuccess＝＝" + shareResult);
    }

    @Override
    public void onShareCancel(ShareResult shareResult) {
        Log.e(TAG, "onShareCancel==" + shareResult);
    }

    @Override
    public void onShareFailed(ShareResult shareResult) {
        Log.e(TAG, "onShareFailed＝＝" + shareResult);
    }

}
