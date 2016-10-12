package com.application.booktravel.fragment;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.application.booktravel.user_defined.DriftLandBannerLayout;
import com.application.booktravel.user_defined.DriftLandBannerLayout.OnItemClickListener;
import com.easemob.chatuidemo.R;

public class DriftLandFragment extends Fragment {

    private View view;
    private ViewPager mPaper;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private TextView tv_midfield, tv_forward, tv_guard;
    private FinalBitmap fb;
    
    private Spinner sp_quancheng;
    private Spinner sp_zhinengtuijian;
    private Spinner sp_fengge;

    // banner图片
    private DriftLandBannerLayout bl;

    private ImageView imageview1;
    private ImageView imageview2;
    private ImageView imageview3;
    private ImageView imageview4;

    private List<String> zhinengtuijian_list;
    private List<String> quancheng_list;
    private List<String> fengge_list;
    // Spinner的适配器
    private ArrayAdapter<String> zhinengtuijian_adapter;
    private ArrayAdapter<String> quancheng_adapter;
    private ArrayAdapter<String> fengge_adapter;

    private Display display;
    private int itemWidth;

    
   
    // private ViewAdapter videoAdapter_hot,videoAdapter_new;
    // private GridView gridView_newVideo,gridView_hotVideo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.driftlandfragment_layout, container,
                false);

        initLayout();

        zhinengtuijian_list = new ArrayList<String>();
        zhinengtuijian_list.add("离我最近");
        zhinengtuijian_list.add("人气最高");
        zhinengtuijian_list.add("活动最多");
        zhinengtuijian_list.add("消费最高");

        quancheng_list = new ArrayList<String>();
        quancheng_list.add("附近");
        quancheng_list.add("岳麓区");
        quancheng_list.add("芙蓉区");
        quancheng_list.add("天心区");
        quancheng_list.add("雨花区");
        quancheng_list.add("开福区");

        fengge_list = new ArrayList<String>();
        fengge_list.add("中国风");
        fengge_list.add("日韩");
        fengge_list.add("欧美");
        fengge_list.add("流行");

        // 适配器
        zhinengtuijian_adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, zhinengtuijian_list);
        quancheng_adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, quancheng_list);
        fengge_adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, fengge_list);
        // 设置样式
        zhinengtuijian_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quancheng_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fengge_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 加载适配器
        sp_zhinengtuijian.setAdapter(zhinengtuijian_adapter);
        sp_quancheng.setAdapter(quancheng_adapter);
        sp_fengge.setAdapter(fengge_adapter);

        // 给智能推荐点击事件
        sp_zhinengtuijian
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                            View view, int position, long id) {
                        Toast.makeText(getActivity(), "id=" + id, 1).show();
                        mPaper.setCurrentItem((int) id + 1);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(getActivity(), "unselected", 0).show();
                    }
                });

        // 给全城设置点击事件
        sp_quancheng.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
                Toast.makeText(getActivity(), "id=" + id, 0).show();
                mPaper.setCurrentItem((int) id+3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "unselected", 0).show();
            }
        });

        // 给风格设置点击事件
        sp_fengge.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
                Toast.makeText(getActivity(), "id=" + id, 0).show();
                mPaper.setCurrentItem((int) id + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "unselected", 0).show();
            }
        });

        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {

                return mFragments.size();
            }

            @Override
            public android.support.v4.app.Fragment getItem(int arg0) {
                // TODO Auto-generated method stub
                return mFragments.get(arg0);
            }
        };
        mPaper.setAdapter(mAdapter);
        mPaper.setOnPageChangeListener(new OnPageChangeListener() {

            private int currentIndex;

            @Override
            public void onPageSelected(int position) {
                resetColor();
                switch (position) {

                }
                currentIndex = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        return view;
    }

    /**
     * 初始化控件
     */
    public void initLayout() {
        // tv_footable = (TextView)view.findViewById(R.id.tv_youngfootable);

        sp_zhinengtuijian = (Spinner) view.findViewById(R.id.sp_zhinengtuijian);
        sp_quancheng = (Spinner) view.findViewById(R.id.sp_quancheng);
        sp_fengge = (Spinner) view.findViewById(R.id.sp_fengge);

        mPaper = (ViewPager) view.findViewById(R.id.view_pager);

        // banner中的图
        fb = FinalBitmap.create(getActivity());
        
        bl = (DriftLandBannerLayout) view.findViewById(R.id.banner2);
        bl.setOnItemClickListener(new OnItemClickListener() {

            public void onClick(int index, View childview) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), "点击了index：" + index,
                        0).show();
            }
        });
            
        imageview1 = (ImageView) view.findViewById(R.id.imageview1);
        imageview2 = (ImageView) view.findViewById(R.id.imageview2);
        imageview3 = (ImageView) view.findViewById(R.id.imageview3);
        imageview4 = (ImageView) view.findViewById(R.id.imageview4);
                        
       IntelligentChooseFragment f1 = new IntelligentChooseFragment();
       LocationChooseFragment f2 = new LocationChooseFragment();
       StyleChooseFragment f3 = new StyleChooseFragment();
       Choose_YueluFragment f4 = new Choose_YueluFragment();
        mFragments.add(f1);
        mFragments.add(f2);
        mFragments.add(f3);
        mFragments.add(f4);

    }

    public void resetColor() {

    }

    /**
     * ViewPager适配器
     */
    public class MyPagerAdapter extends PagerAdapter {
        public List<Activity> mListViews;

        public MyPagerAdapter(List<Activity> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }
        
        
        
    }
    
    
    
    
   
}
