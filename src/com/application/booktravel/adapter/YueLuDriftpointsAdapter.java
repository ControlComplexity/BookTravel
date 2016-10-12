package com.application.booktravel.adapter;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;

import com.application.booktravel.entity.CollectionEntity;
import com.application.booktravel.entity.DriftpointEntity;
import com.easemob.chatuidemo.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class YueLuDriftpointsAdapter extends BaseAdapter {
    private Context context;
    private Context context1;
    private ArrayList<DriftpointEntity> _list;
    private FinalBitmap fb;

    public YueLuDriftpointsAdapter() {
    }

    public YueLuDriftpointsAdapter(Context context1, Context context,
            ArrayList<DriftpointEntity> _list) {
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
        convertView = inflater.inflate(R.layout.choose_yuelu_item, null);

        ImageView imgView = (ImageView) convertView
                .findViewById(R.id.yuelu_imageview);
        TextView mingzi = (TextView) convertView.findViewById(R.id.yuelu_piaoliudianming);
        TextView jianjie = (TextView) convertView.findViewById(R.id.yuelu_piaoliudianmjianjie);

        final DriftpointEntity b = _list.get(position);
        // 如果有图片则读取，没有则跳过
        if (b.getImg()!= null) {
            fb.display(imgView, b.getImg());
          //  imgView.setImageResource(R.drawable.aaa);
        }

        if (b.getName() != null) {

            mingzi.setText(b.getName());
        }
        if (b.getIntroduction() != null) {

            jianjie.setText(b.getIntroduction());
        }

        imgView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                
                Toast.makeText(context, "bookhaha", 0).show();
//                Intent intent = new Intent("aaaa");
//                context1.startActivity(intent);
            }
        });
//        mingzi.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                Toast.makeText(_context, "starhh", 0).show();
//            }
//        });

      
        return convertView;
    }

}
