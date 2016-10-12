package com.application.booktravel.adapter;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.booktravel.entity.CollectionEntity;
import com.easemob.chatuidemo.R;

public class MyCollctionAdapter extends BaseAdapter {

    private Context _context;
    private Context context1;
    private ArrayList<CollectionEntity> _list;
    private FinalBitmap fb;
    
    
    public MyCollctionAdapter(Context context1,
            Context context,
            ArrayList<CollectionEntity> imageList) {
        this._context = context;
        this._list = imageList;
        this.context1 = context1;
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
        LayoutInflater inflater = LayoutInflater.from(_context);
        convertView = inflater.inflate(R.layout.mycollectionsbook_item, null);

        ImageView imgView = (ImageView) convertView
                .findViewById(R.id.book_image);
        ImageView imgView2 = (ImageView) convertView
                .findViewById(R.id.xing_xing);
        TextView textView = (TextView) convertView.findViewById(R.id.book_name);

        final CollectionEntity b = _list.get(position);
        // 如果有图片则读取，没有则跳过
        if (b.getPicurl() != null) {
            fb.display(imgView, b.getPicurl());
            imgView2.setImageResource(R.drawable.xingxing);
        }

        if (b.getBookname() != null) {

            textView.setText(b.getBookname());
        }

        imgView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                
                Toast.makeText(_context, "bookhaha", 0).show();
                Intent intent = new Intent("aaaa");
                context1.startActivity(intent);
            }
        });
        imgView2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Toast.makeText(_context, "starhh", 0).show();
            }
        });

        // convertView.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View view) {
        // // TODO Auto-generated method stub
        // Toast.makeText(_context, b.getIsbn(), 0).show();
        // }
        // });
        return convertView;
    }
}