package com.application.booktravel.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.application.booktravel.entity.RaftedBooksEntity;
import com.easemob.chatuidemo.activity.MyRaftingBook;

public class ParseJSON {

    private static RaftedBooksEntity parseJSONRaftingBook(InputStream inStream)
            throws Exception {

        byte[] data = StreamTool.read(inStream);
        String json = new String(data);
        JSONObject jsonObject = new JSONObject(json);
        RaftedBooksEntity raftedBooksEntity = new RaftedBooksEntity();
        JSONArray jsonArray = jsonObject.getJSONArray("readings");
        JSONObject jsonObject3 = (JSONObject) jsonArray.get(0);
        String cover = jsonObject3.getString("cover");
        String title = jsonObject3.getString("title");
        String isbn = jsonObject3.getString("isbn");
        // String introduction = jsonObject3.getString("introduction");
        raftedBooksEntity.setBookname(title);
        raftedBooksEntity.setPicurl(cover);
        raftedBooksEntity.setIsbn(isbn);
        Log.i("json", cover);
        return raftedBooksEntity;
    }

    public static RaftedBooksEntity getJSONRaftingBook(String path) throws Exception {
        
        URL url = new URL(path);
        Log.i("json", "fff");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        Log.i("json", "aftter");
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        Log.i("json", conn.getResponseCode() + "fff");
        if (conn.getResponseCode() == 200) {
            InputStream inStream = conn.getInputStream();
            return parseJSONRaftingBook(inStream);
        }
        return null;
    }
}
