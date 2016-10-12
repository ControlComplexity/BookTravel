package com.application.booktravel.fragment;

import net.tsz.afinal.FinalBitmap;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.application.booktravel.constant.Constants;
import com.application.booktravel.util.HttpUtil;
import com.application.booktravel.util.SharePreferenceUtil;
import com.easemob.chatuidemo.R;
import com.easemob.chatuidemo.activity.MyBookActivity;
import com.easemob.chatuidemo.activity.MyDriftNotesActivity;
import com.easemob.chatuidemo.activity.SettingsActivity;

public class PersonnalCenterFragment extends Fragment {
    private String TAG = "Fragment1";
    private ListView list;
    private TextView yhmingcheng;
    private SimpleAdapter adapter;
    private int[] item;
    private TextView age, sex;
    // private CircleImageView circleimage;
    private String imageurl;
    private ImageView gerenshezhi;
    private ImageView circleimage1;
    private FinalBitmap fbitmap;

    /**
     * Fragment的生命周期：onAttach(),onCreate(),onCreateView(),onActivityCreated(),
     * onStart(),onResume(),(Fragment is
     * active),onPause(),onStop(),onDestroyView(),
     * onDestroy(),onDetach(),(Fragment is destroyed);
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personnalcenter_layout,
                container, false);
        gerenshezhi = (ImageView) view.findViewById(R.id.gerenshezhi);
        circleimage1 = (ImageView) view.findViewById(R.id.circleimage1);
        list = (ListView) view.findViewById(android.R.id.list);
        SharePreferenceUtil sharePreferenceUtil = new SharePreferenceUtil(
                getActivity(), Constants.SAVE_USER);
        sharePreferenceUtil.getPhoto();
        fbitmap = FinalBitmap.create(getActivity());

        SharePreferenceUtil util = new SharePreferenceUtil(getActivity(),
                Constants.SAVE_USER);
        imageurl = Constants.URL + "images/" + util.getPhoto();
        Log.e("img", imageurl);
      //  setListViewHeight(list);
        item = new int[] { R.id.show_time, R.id.title };
        fbitmap.display(circleimage1, imageurl);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HttpUtil.QueryMyDriftProcess(getActivity(), list, item);
    }

    /**
     * 设置自定义listview的高度
     * 
     * @param listView
     *            自定义的listview
     */
    public static void setListViewHeight(ListView listView) {
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
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1))
                + listView.getPaddingTop() + listView.getPaddingBottom();
        listView.setLayoutParams(params);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.gerenshezhi).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), SettingsActivity.class);
                        startActivity(intent);
                    }
                });

        view.findViewById(R.id.myreadnotes_l1).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(),
                                MyDriftNotesActivity.class);
                        startActivity(intent);
                    }
                });

        view.findViewById(R.id.mybooks_l2).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), MyBookActivity.class);
                        startActivity(intent);
                    }
                });

    }

}
