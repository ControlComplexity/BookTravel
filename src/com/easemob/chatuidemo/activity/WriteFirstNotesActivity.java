package com.easemob.chatuidemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.application.booktravel.constant.Constants;
import com.application.booktravel.entity.ColumnEntity;
import com.application.booktravel.util.HttpUtil;
import com.application.booktravel.util.SharePreferenceUtil;
import com.easemob.chatuidemo.R;

public class WriteFirstNotesActivity extends Activity{
    private TextView columnbookname;
    private EditText columntitle;
    private EditText columnintroduction;
    private String bookname;
    private String bookisbn;
    private String userphone;
    private String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writenotes_firstnote);
        initview();
        getBookInfo();
        columnbookname.setText(bookname);
    }

    public void getBookInfo() {
        final SharePreferenceUtil userutil = new SharePreferenceUtil(this,
                Constants.SAVE_USER);
         userid = "" + userutil.getId();
         userphone = "" + userutil.getTel();  
         
        HttpUtil.QueryMyReading(this,userid);
        final SharePreferenceUtil bookutil = new SharePreferenceUtil(this,
                Constants.SAVE_USER);
        bookisbn = "" + bookutil.getIsbn();
        bookname = "" + bookutil.getBookname();
        Log.i("WriteFirstNotesActivity",bookisbn+bookname+userphone);
    }

    public void initview() {
        columntitle = (EditText)findViewById(R.id.columntitle);
        columnintroduction = (EditText)findViewById(R.id.columnintroduction);
        columnbookname = (TextView)findViewById(R.id.columnbookname);
    }

    public void onclick(View v)
    {
        ColumnEntity columnEntity = new ColumnEntity();
        columnEntity.setColumntitle(columntitle.getText().toString());
        columnEntity.setIntroduction(columnintroduction.getText().toString());
        columnEntity.setBookisbn(bookisbn);
        columnEntity.setUserphone(userphone);
        HttpUtil.CreateColumn(this,columnEntity);
    }
}
