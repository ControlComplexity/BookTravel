package com.easemob.chatuidemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.application.booktravel.util.HttpUtil;
import com.easemob.chatuidemo.R;

public class MyCollectionsActivity extends Activity implements OnClickListener {
    Button reg_back_btn3 = null;
    private int[] item;
    private static String TAG = "MyCollections";
    
    GridView gridview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycollection);
        gridview2 = (GridView) findViewById(R.id.gridview2);
        initData();
        reg_back_btn3 = (Button) findViewById(R.id.mycollection_back_btn3);
        reg_back_btn3.setOnClickListener(this);
    }

    private void initData() {

        HttpUtil.QueryMyCollections(this, gridview2, this.getApplication());
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
        case R.id.mycollection_back_btn3:
            MyCollectionsActivity.this.finish();
        }
    }
}
