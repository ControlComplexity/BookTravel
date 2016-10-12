package com.easemob.chatuidemo.activity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.booktravel.entity.ColumnEntity;
import com.application.booktravel.util.HttpUtil;
import com.easemob.chatuidemo.R;

public class DriftNotesActivity extends Activity {
    private ViewPager viewPager;
    private int bmpW;// 动画图片宽度
    private List<View> views;// Tab页面列表
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private ImageView imageView;// 动画图片
    private TextView textView1, textView2, textView3;
    private View view1, view2, view3;// 各个页卡
    //关注
    private Button driftnotes_columnconcern;
    //写笔记图像按钮    
   private  ImageButton driftnotes_imagebutton;
  
   //用户名
    private TextView driftnotes_username;
    //头像
    private  ImageView driftnotes_userphoto;
    //标题
    private TextView driftnotes_columntitle;
    //简介
    private TextView driftnotes_columnintroduction;
    //书名
    private TextView   driftnotes_bookname;
    
    private String columnid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driftnotes);
        viewPager = (ViewPager) findViewById(R.id.note_vPager);
        Bundle bundle =this.getIntent().getExtras();
        columnid = bundle.getString("columnid");
        Log.i("DriftNotesActivity",columnid);
        InitComponents();
        InitImageView();
        InitTextView();
        InitViewPager();
    }

    private void InitComponents()
    {
        
        driftnotes_username = (TextView)findViewById(R.id.driftnotes_username);
        driftnotes_userphoto = (ImageView)findViewById(R.id.driftnotes_userphoto);
        driftnotes_columntitle = (TextView)findViewById(R.id.driftnotes_columntitle);
        driftnotes_columnintroduction =  (TextView)findViewById(R.id.driftnotes_columnintroduction);
        driftnotes_bookname =  (TextView)findViewById(R.id.driftnotes_bookname);
        driftnotes_userphoto = (ImageView)findViewById(R.id.driftnotes_userphoto);
        
        //搞定图片加载
        HttpUtil.InitDriftNotes(this,columnid,driftnotes_username,driftnotes_columntitle,driftnotes_columnintroduction
                ,driftnotes_bookname,driftnotes_userphoto); 
        
        
      
        driftnotes_columnconcern = (Button)findViewById(R.id.driftnotes_columnconcern);
        driftnotes_columnconcern.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                HttpUtil.SaveConcernToColumn(DriftNotesActivity.this,columnid);
                   Toast.makeText(DriftNotesActivity.this, "关注成功", 0).show(); 
            }
        });  
    }

    private void InitImageView() {
        imageView = (ImageView) findViewById(R.id.cursor);
        bmpW = bmpW = BitmapFactory
                .decodeResource(getResources(), R.drawable.a).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);// 设置动画初始位置
    }

    /**
     * 初始化头标
     */

    private void InitTextView() {
        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        textView3 = (TextView) findViewById(R.id.text3);

        textView1.setOnClickListener(new MyOnClickListener(0));
        textView2.setOnClickListener(new MyOnClickListener(1));
        textView3.setOnClickListener(new MyOnClickListener(2));
    }

    private void InitViewPager() {

        views = new ArrayList<View>();
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.note_dushubiji, null);
        view2 = inflater.inflate(R.layout.note_piaoyousuiping, null);
        view3 = inflater.inflate(R.layout.note_piaoliulicheng, null);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 
     * 头标点击监听 3
     */
    private class MyOnClickListener implements OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }

    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {

        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        public void onPageSelected(int arg0) {
         
            Animation animation = new TranslateAnimation(one * currIndex, one
                    * arg0, 0, 0);
            currIndex = arg0;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            imageView.startAnimation(animation);
            Toast.makeText(DriftNotesActivity.this,
                    "您选择了" + viewPager.getCurrentItem() + "页卡",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
