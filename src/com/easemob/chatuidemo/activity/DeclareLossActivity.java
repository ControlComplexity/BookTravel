package com.easemob.chatuidemo.activity;

import net.tsz.afinal.FinalBitmap;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.booktravel.constant.Constants;
import com.application.booktravel.entity.RaftedBooksEntity;
import com.application.booktravel.util.ParseJSON;
import com.application.booktravel.util.SharePreferenceUtil;
import com.easemob.chatuidemo.R;
import com.easemob.chatuidemo.widget.BottomMenu;

public class DeclareLossActivity extends Activity implements OnClickListener {
    Button declareloss_back_btn3, booktopaymoney = null;
    private BottomMenu menuWindow;
    private int[] item;
    private static String TAG = "MyCollections";
    private RaftedBooksEntity entity;
    private ImageView imageview;
    private TextView declarelossbookname;
    private FinalBitmap fb;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String str = (String) msg.obj;
            String tu[] = str.split(",");
            fb.display(imageview, tu[0]);
            declarelossbookname.setText(tu[1]);
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declareloss);
        fb = FinalBitmap.create(this);
        findViewById(R.id.booktopaymoney).setOnClickListener(this);
        imageview = (ImageView) findViewById(R.id.declarelossbooknameimage);
        declarelossbookname = (TextView) findViewById(R.id.declarelossbookname);
        declareloss_back_btn3 = (Button) findViewById(R.id.declareloss_back_btn3);
        declareloss_back_btn3.setOnClickListener(this);
        booktopaymoney = (Button) findViewById(R.id.booktopaymoney);
        booktopaymoney.setOnClickListener(this);
        // initData();
        new MyThread().start();
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            try {
                String path1 = "http://120.27.37.245:2935/BookTravelWeb/QueryMyBooks.action?userphone=";

                SharePreferenceUtil preferenceUtil = new SharePreferenceUtil(
                        DeclareLossActivity.this, Constants.SAVE_USER);
                String phone = preferenceUtil.getTel();
                path1 += phone;

                entity = ParseJSON.getJSONRaftingBook(path1);

                String str = Constants.URL + "images/" + entity.getPicurl()+",";
                str +=entity.getBookname();
                Message message = new Message();
                message.obj = str;
               
                handler.sendMessage(message);
               
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
        case R.id.declareloss_back_btn3:
            DeclareLossActivity.this.finish();
            break;
        case R.id.booktopaymoney:
            menuWindow = new BottomMenu(DeclareLossActivity.this, clickListener);
            menuWindow.show();
            break;
        }
    }

    private OnClickListener clickListener = new OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.btn1:
                Toast.makeText(DeclareLossActivity.this, "menu1",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn2:
                Toast.makeText(DeclareLossActivity.this, "menu2",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
            }
        }
    };
}
