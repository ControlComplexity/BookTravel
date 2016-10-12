package com.easemob.chatuidemo.activity;


import android.app.Activity;
import android.os.Bundle;

import com.easemob.chatuidemo.R;

public class PersonnalCenterActivity extends Activity {
    private String userphone;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_personnalcenter);
    Bundle bundle =this.getIntent().getExtras();
    userphone = bundle.getString("userphone");
}

}
