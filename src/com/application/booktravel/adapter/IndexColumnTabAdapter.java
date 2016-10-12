package com.application.booktravel.adapter;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.booktravel.entity.ColumnEntity;
import com.easemob.chatuidemo.R;
import com.easemob.chatuidemo.activity.DriftNotesActivity;

public class IndexColumnTabAdapter extends BaseAdapter {
    private Context context;
    private Context context1;
    private ArrayList<ColumnEntity> _list;
    private FinalBitmap fb;

    public IndexColumnTabAdapter() {
    }

    public IndexColumnTabAdapter(Context context1, Context context,
            ArrayList<ColumnEntity> _list) {
        super();
        this.context1 = context1;
        this.context = context;
        this._list = _list;
        fb = FinalBitmap.create(context);
    }

    @Override
    public int getCount() {
        return _list.size();
        
    }

    @Override
    public Object getItem(int position) {
        return _list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.indexfragment_tab_item, null);
        RelativeLayout relativeLayout = (RelativeLayout)convertView.findViewById(R.id.indexrelativelayout);
        
        ImageView imgView = (ImageView) convertView
                .findViewById(R.id.tab_userpic);
        TextView tab_username = (TextView) convertView
                .findViewById(R.id.tab_username);
        TextView tab_columntitle = (TextView) convertView
                .findViewById(R.id.tab_columntitle);
        final ColumnEntity b = _list.get(position);
        // 如果有图片则读取，没有则跳过
      
        
        if (b.getUserimage() != null) {
            Log.i("IndexColumnTabAdapter", b.getUserimage());
            fb.display(imgView, b.getUserimage());
        }else
        {
            imgView.setImageResource(R.drawable.nanshengmorentouxiang);
        }

        if (b.getUsername() != null) {

            tab_username.setText(b.getUsername());
        }
        if (b.getColumntitle() != null) {

            tab_columntitle.setText(b.getColumntitle());
        }

        
        relativeLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Toast.makeText(context, "bookhaha", 0).show();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("columnid", b.getColumnid());
                intent.putExtras(bundle);
                intent.setClass(context1, DriftNotesActivity.class);
                context1.startActivity(intent);
            }
        });

        return convertView;
    }

}
