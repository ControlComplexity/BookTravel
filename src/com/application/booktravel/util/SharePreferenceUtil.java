package com.application.booktravel.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreferenceUtil {
    private SharedPreferences preferences;
    private Editor editor;

    public SharePreferenceUtil(Context context, String file) {
        preferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setTel(String tel) {
        editor.putString("tel", tel);
        editor.commit();
    }

    public String getTel() {
        return preferences.getString("tel", "");
    }

    public void setPhoto(String photo) {
        editor.putString("photo", photo);
        editor.commit();
    }

    public String getPhoto() {
        return preferences.getString("photo", "");
    }

    public void setPasswd(String passwd) {
        editor.putString("passwd", passwd);
        editor.commit();
    }

    public String getPasswd() {
        return preferences.getString("passwd", "");
    }
    
    public void setHasBook(String str) {
        editor.putString("hasbook", str);
        editor.commit();
    }

    public String  getHasBook() {
        return preferences.getString("hasbook", "");
    }


    public void setId(String id) {
        editor.putString("id", id);
        editor.commit();
    }

    public String getId() {
        return preferences.getString("id", "");
    }

    public String getName() {
        return preferences.getString("name", "");
    }

    public void setName(String name) {
        editor.putString("name", name);
        editor.commit();
    }

    public String getAge() {
        return preferences.getString("age", "");
    }

    public void setAge(String age) {
        editor.putString("age", age);
        editor.commit();
    }

    // 0-男 1-女
    public String getSex() {
        return preferences.getString("sex", "");
    }

    public void setSex(String sex) {
        editor.putString("sex", sex);
        editor.commit();
    }

    public String getRaftingBook() {
        return preferences.getString("raftingbook", "");
    }

    public void setRaftingfBook(String sex) {
        editor.putString("raftingbook", sex);
        editor.commit();
    }

    //扫码
    public void setIsbn(String isbn) {
        editor.putString("isbn", isbn);
        editor.commit();
    }

    public String getIsbn() {
           return preferences.getString("isbn", "");
    }
    
    public void setBookname(String bookname) {
        editor.putString("bookname", bookname);
        editor.commit();
    }

    public String getBookname() {
           return preferences.getString("bookname", "");
    }
    
    
//    
//    public void setDriftpoint(String isbn) {
//        editor.putString("driftpoint", isbn);
//        editor.commit();
//    }
//
//    public String getDriftpoint() {
//           return preferences.getString("driftpoint", "");
//    }
 
   
}
