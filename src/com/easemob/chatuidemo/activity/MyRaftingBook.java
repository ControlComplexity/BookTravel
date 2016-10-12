package com.easemob.chatuidemo.activity;

import net.tsz.afinal.FinalBitmap;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.application.booktravel.constant.Constants;
import com.application.booktravel.entity.RaftedBooksEntity;
import com.application.booktravel.user_defined.MyScrollView.OnGetBottomListener;
import com.application.booktravel.util.HttpUtil;
import com.application.booktravel.util.ParseJSON;
import com.application.booktravel.util.SharePreferenceUtil;
import com.easemob.chatuidemo.R;

/**
 * 环信的漂流书籍
 * 
 * @author wangcao
 * 
 */
public class MyRaftingBook extends Activity implements OnGetBottomListener,
        OnClickListener {
    private Button raftingbook_back_btn1 = null;
    private RaftedBooksEntity entity;
    private GridView gridview2;
    private ImageView imageview;
    private TextView raftingbooktextview;
    // MyListView mDetailImgList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raftingbook);
        raftingbook_back_btn1 = (Button) findViewById(R.id.raftingbook_back_btn1);
        raftingbook_back_btn1.setOnClickListener(this);
        raftingbooktextview = (TextView)findViewById(R.id.raftingbooktextview);
        imageview = (ImageView) findViewById(R.id.raftingbookimageview);
        gridview2 = (GridView) findViewById(R.id.gridview2);
        setListViewHeight(gridview2);
        initData();

    }

    private void initData() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String path1 = "http://120.27.37.245:2935/BookTravelWeb/QueryMyBooks.action?userphone=";
                    
                    SharePreferenceUtil preferenceUtil = new SharePreferenceUtil(MyRaftingBook.this,Constants.SAVE_USER);
                    String phone = preferenceUtil.getTel();
                    path1+=phone;
                    
                     RaftedBooksEntity raftedBooksEntity;
                    raftedBooksEntity = ParseJSON .getJSONRaftingBook(path1);
                    entity = raftedBooksEntity;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MyRaftingBook.this.runOnUiThread(new Runnable() {

                  
                    @Override
                    public void run() {
                        HttpUtil.QueryRaftedBook(MyRaftingBook.this, gridview2,
                                MyRaftingBook.this.getApplication());

                        Log.i("RaftingBook", entity.getPicurl());
                        String url = Constants.URL + "images/"
                                + entity.getPicurl();
                        String name = entity.getBookname();
                        FinalBitmap bitmap = FinalBitmap
                                .create(MyRaftingBook.this);
                        bitmap.display(imageview, url);
                        raftingbooktextview.setText(name);
                        Log.i("RaftingBook", url);
                    }
                });

            }
        }).start();

    }

    @Override
    public void onBottom() {
        // TODO Auto-generated method stub
        // mDetailImgList.setBottomFlag(true);

    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
        case R.id.reg_back_btn1:
            MyRaftingBook.this.finish();
        }
    }

    /**
     * 设置自定义listview的高度
     * 
     * @param listView
     *            自定义的listview
     */
    public static void setListViewHeight(GridView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(1, 1);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getHeight() * (listAdapter.getCount() - 1))
                + listView.getPaddingTop() + listView.getPaddingBottom();
        listView.setLayoutParams(params);
    }
}
