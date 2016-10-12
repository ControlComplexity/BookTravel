package com.easemob.chatuidemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.easemob.EMCallBack;
import com.easemob.chatuidemo.DemoApplication;
import com.easemob.chatuidemo.R;

/**
 * 书旅设置activity，包括退出登录等等
 * 
 * @author wangcao
 * 
 */
public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
    }

    public void haoyouqingqiu(View v) {
        startActivity(new Intent(this,CopyOfNewFriendsMsgActivity.class));
    }

    public void logout(View v) {

        DemoApplication.getInstance().logout(new EMCallBack() {

            public void onSuccess() {
                SettingsActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        // 重新显示登陆页面

                        startActivity(new Intent(SettingsActivity.this,
                                LoginActivity.class));

                    }
                });
            }

            public void onProgress(int arg0, String arg1) {

            }

            public void onError(int arg0, String arg1) {

            }
        });
    }

}
