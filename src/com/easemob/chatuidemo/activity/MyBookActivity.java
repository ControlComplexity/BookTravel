package com.easemob.chatuidemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.application.booktravel.constant.Constants;
import com.application.booktravel.util.SharePreferenceUtil;
import com.application.booktravel.widget.CircleImageView;
import com.easemob.applib.utils.ToolUtils;
import com.easemob.chatuidemo.R;

public class MyBookActivity extends Activity {
    LinearLayout linearlayout1 = null;
    LinearLayout linearlayout2 = null;
    private CircleImageView circleimage;
    private String imageurl;
    LinearLayout linearlayout3 = null;

    Button reg_back_btn1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybook_layout);
        circleimage = (CircleImageView) findViewById(R.id.circleimage2);
        SharePreferenceUtil util = new SharePreferenceUtil(MyBookActivity.this,
                Constants.SAVE_USER);
        imageurl = Constants.URL + "images/" + util.getPhoto();
        Log.e("MyBook", imageurl);
        ToolUtils.finalbitmap.display(circleimage, imageurl);
        reg_back_btn1 = (Button) findViewById(R.id.mybook_back_btn);
        linearlayout1 = (LinearLayout) findViewById(R.id.mybook_linearlayout1);
        linearlayout2 = (LinearLayout) findViewById(R.id.mybook_linearlayout2);
        linearlayout3 = (LinearLayout) findViewById(R.id.mybook_linearlayout3);
        
        linearlayout1.setOnClickListener(onclicklistener);
        linearlayout2.setOnClickListener(onclicklistener);
        linearlayout3.setOnClickListener(onclicklistener);

        reg_back_btn1.setOnClickListener(onclicklistener);
    }

    public OnClickListener onclicklistener = new OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.mybook_linearlayout1:

                Intent intent = new Intent();
                intent.setClass(MyBookActivity.this, MyRaftingBook.class);
                startActivity(intent);
                break;
            case R.id.mybook_linearlayout2:

                Intent intent2 = new Intent();
                intent2.setClass(MyBookActivity.this, MyCollectionsActivity.class);
                startActivity(intent2);
                break;

            case R.id.mybook_linearlayout3:
                Intent intent5 = new Intent();
                intent5.setClass(MyBookActivity.this, DeclareLossActivity.class);
                startActivity(intent5);
                break;
            case R.id.reg_back_btn1:
                MyBookActivity.this.finish();
                break;
            default:
                break;
            }
        }

    };

}
