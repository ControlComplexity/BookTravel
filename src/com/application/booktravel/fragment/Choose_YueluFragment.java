package com.application.booktravel.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.application.booktravel.util.HttpUtil;
import com.easemob.chatuidemo.R;

public class Choose_YueluFragment extends Fragment {
    private ListView list;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_yeulufragment, null);
        list = (ListView) view.findViewById(android.R.id.list);
      
        setListViewHeight(list);
     
        return view;
    }



 

    @Override
    public void onResume() {
        super.onResume();

        HttpUtil.QueryDriftpointsByLocation(getActivity(), list, getActivity().getApplication());
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

   
}
