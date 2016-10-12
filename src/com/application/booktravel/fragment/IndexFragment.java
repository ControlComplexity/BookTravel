package com.application.booktravel.fragment;

import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.application.booktravel.constant.Constants;
import com.application.booktravel.user_defined.IndexBannerLayout;
import com.application.booktravel.user_defined.IndexBannerLayout.OnItemClickListener;
import com.application.booktravel.util.HttpUtil;
import com.application.booktravel.util.SharePreferenceUtil;
import com.easemob.chatuidemo.R;
import com.easemob.chatuidemo.activity.DriftNotesActivity;
import com.zijunlin.Zxing.Demo.CaptureActivity;
import com.zijunlin.Zxing.Demo.ReturnCaptureActivity;

public class IndexFragment extends Fragment {
    private Button button1;
    private Button button2;
    private View view;
    private FinalBitmap fb;
    private InputMethodManager inputMethodManager;
    // 搜索框
    private EditText indexquery;
    private ImageButton indexclearSearch;
    // banner图片
    private IndexBannerLayout bl;

    private ImageView imageview1;
    private ImageView imageview2;
    private ImageView imageview3;
    private ImageView imageview4;
    private ListView listView;
    private String boolean1;
    
    private  Activity activity;
   
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater
                .inflate(R.layout.indexfragment_layout, container, false);
        activity = getActivity();
        HttpUtil.JudgeIfHasBook(getActivity());
        initLayout();
     
        SharePreferenceUtil share = new SharePreferenceUtil(getActivity(),Constants.SAVE_USER);
        boolean1 = share.getHasBook();
        Log.i("IndexFragment",boolean1+"index");
        return view;

    }
    
    
    @Override
    public void onResume() {
        super.onResume();
      
        HttpUtil.QueryColumnByTime(activity, listView, getActivity().getApplication());
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      
        inputMethodManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        indexquery.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {

                if (s.length() > 0) {
                    indexclearSearch.setVisibility(View.VISIBLE);
                } else {
                    indexclearSearch.setVisibility(View.INVISIBLE);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        indexclearSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                indexquery.getText().clear();
                hideSoftKeyboard();
            }
        });

        button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                    HttpUtil.ChooseWriteNotesInterface(getActivity());

            }
        });

        button2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = null;
             
             
                    intent = new Intent(activity, CaptureActivity.class);
                    startActivity(intent);
                
            }
        });
    }

    void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (activity.getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity()
                        .getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 初始化控件
     */
    public void initLayout() {
        listView = (ListView)view.findViewById(android.R.id.list);
        button1 = (Button) view.findViewById(R.id.indexpiaoliubiji);
        button2 = (Button) view.findViewById(R.id.indexjiehuanshu);
        indexquery = (EditText) view.findViewById(R.id.indexquery1);
        //
        // // 搜索框中清除button
        indexclearSearch = (ImageButton) view
                .findViewById(R.id.indexsearch_clear1);

        // banner中的图
        fb = FinalBitmap.create(getActivity());

        bl = (IndexBannerLayout) view.findViewById(R.id.indexbanner2);
        bl.setOnItemClickListener(new OnItemClickListener() {

            public void onClick(int index, View childview) {
                Toast.makeText(activity, "点击了index：" + index,
                        Toast.LENGTH_SHORT).show();
            }
        });
        imageview1 = (ImageView) view.findViewById(R.id.indeximageview1);
        imageview2 = (ImageView) view.findViewById(R.id.indeximageview2);
        imageview3 = (ImageView) view.findViewById(R.id.indeximageview3);
        imageview4 = (ImageView) view.findViewById(R.id.indeximageview4);

        fb.display(imageview1,
                "http://120.27.37.245:2935/BookTravelWeb/images/caoguo.png");
        fb.display(imageview2,
                "http://120.27.37.245:2935/BookTravelWeb/images/heisexilie.png");
        fb.display(imageview3,
                "http://120.27.37.245:2935/BookTravelWeb/images/jiuhaopiaoliudao.png");
        fb.display(imageview4,
                "http://120.27.37.245:2935/BookTravelWeb/images/shishangba.png");

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
