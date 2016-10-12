package com.application.booktravel.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.application.booktravel.adapter.IndexColumnTabAdapter;
import com.application.booktravel.adapter.MyCollctionAdapter;
import com.application.booktravel.adapter.RaftedBooksAdapter;
import com.application.booktravel.adapter.YueLuDriftpointsAdapter;
import com.application.booktravel.constant.Constants;
import com.application.booktravel.entity.CollectionEntity;
import com.application.booktravel.entity.ColumnEntity;
import com.application.booktravel.entity.DriftpointEntity;
import com.application.booktravel.entity.RaftedBooksEntity;
import com.easemob.chatuidemo.R;
import com.easemob.chatuidemo.activity.DriftnotesActivityError;
import com.easemob.chatuidemo.activity.MainActivity;
import com.easemob.chatuidemo.activity.PersonnalCenterActivity;
import com.easemob.chatuidemo.activity.WriteFirstNotesActivity;
import com.easemob.chatuidemo.activity.WriteNotesActivity;
import com.easemob.chatuidemo.domain.User;

public class HttpUtil extends Activity {
    
    
    static String TAG = "HttpUtil";
    private ImageView myImg;
    private static Handler myHandler;
    private static Bitmap bitmap;

    // private static MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // app = (MyApplication) getApplicationContext();
    }

    /**
     * 用户注册请求
     * 
     * @param mcontext
     * @param user
     */
    public synchronized static void register(final Context mcontext,
            final User user) {
        String url = Constants.URL + "UserRegister.action";
        AjaxParams params = new AjaxParams();
        params.put("userphone", user.getTel());
        params.put("userpassword", user.getPassword());
        params.put("username", user.getNickname());
        FinalHttp fh = new FinalHttp();
        fh.post(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {
                // TODO Auto-generated method stub
                try {
                    JSONObject json = new JSONObject(t);
                    int code = json.getInt("code");
                    if (code == 100) {
                        Toast.makeText(mcontext, "手机号已被注册", 0).show();
                        // gotoLogin(mcontext);
                    } else if (code == 200) {
                        Toast.makeText(mcontext, "注册成功", 0).show();
                    } else {
                        Toast.makeText(mcontext, "注册失败", 0).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {

                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                Toast.makeText(mcontext, "请确认网络状况", 0).show();
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }

    /**
     * 用户登录请求
     * 
     * @param mcontext
     * @param mdialog
     * @param user
     */
    public static void login(final Context mcontext, final User user) {
        String url = Constants.URL + "UserLogin.action";
        AjaxParams params = new AjaxParams();
        params.put("userphone", user.getTel());
        params.put("password", user.getPassword());
        FinalHttp fh = new FinalHttp();
        fh.post(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {
                // TODO Auto-generated method stub
                try {
                    JSONObject json = new JSONObject(t);
                    int code = json.getInt("code");
                    if (code == 100) {
                        Toast.makeText(mcontext, "手机号未被注册", 0).show();
                    } else if (code == 500) {
                        Toast.makeText(mcontext, "密码错误", 0).show();
                    } else if (code == 200) {
                        JSONObject user = json.getJSONObject("user");
                        SharePreferenceUtil share = new SharePreferenceUtil(
                                mcontext, Constants.SAVE_USER);
                        share.setId(user.getString("userid"));
                        share.setName(user.getString("username"));
                        share.setPasswd(user.getString("password"));
                        share.setTel(user.getString("tel"));

                        share.setPhoto(user.getString("photo"));
                        // share.setAge(user.getString("birth"));
                        // if (user.getBoolean("gender"))
                        // share.setSex("女");
                        // else
                        // share.setSex("男");
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("tel", share.getTel());
                        intent.putExtras(bundle);
                        intent.setClass(mcontext, MainActivity.class);
                        mcontext.startActivity(intent);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(mcontext, e.getMessage(), 0).show();
                } finally {

                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                Toast.makeText(mcontext, "请确认网络状况", 0).show();
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }

    /**
     * 我的漂流历程
     * 
     */
    @SuppressLint("NewApi")
    public static void QueryMyDriftProcess(final Context context,
            final ListView listview, final int[] item) {
        String url = Constants.URL + "UserQueryPersonalCenter.action";
        final SharePreferenceUtil userutil = new SharePreferenceUtil(context,
                Constants.SAVE_USER);
        AjaxParams params = new AjaxParams();
        params.put("userphone", userutil.getTel());
        FinalHttp fh = new FinalHttp();
        fh.post(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    int code = jsonObject.getInt("code");
                    if (code == 200) {
                        JSONObject user = jsonObject.getJSONObject("user");
                        JSONArray driftprocess = user
                                .getJSONArray("driftprocesses");
                        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                        for (int i = 0; i < driftprocess.length(); i++) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            JSONObject item = (JSONObject) driftprocess.get(i);
                            map.put("time", item.getString("time"));
                            String process = userutil.getName() + "在"
                                    + item.getString("place") + "取走了"
                                    + item.get("book");
                            map.put("title", process);
                            list.add(map);
                        }
                        Log.e(TAG, list.toString());
                        SimpleAdapter adapter = new SimpleAdapter(context,
                                (List<? extends Map<String, ?>>) list,
                                R.layout.fruit_item, new String[] { "time",
                                        "title" }, item);
                        listview.setAdapter(adapter);
                        listview.deferNotifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "漂流历程获取失败,请重试...", 0).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(context, "请确认网络状况", 0).show();
            }
        });

    }

    @SuppressLint("NewApi")
    public static void QueryRaftedBook(final Context context,
            final GridView gridview, final Application application) {

        final String url = Constants.URL + "QueryMyBooks.action";
        final SharePreferenceUtil userutil = new SharePreferenceUtil(context,
                Constants.SAVE_USER);

        AjaxParams params = new AjaxParams();
        params.put("userphone", userutil.getTel());
        FinalHttp fh = new FinalHttp();
        fh.post(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
                try {

                    JSONObject jsonObject = new JSONObject(t);
                    int code = jsonObject.getInt("code");
                    if (code == 200) {

                        JSONArray readeds = jsonObject.getJSONArray("readeds");
                        ArrayList<RaftedBooksEntity> raftedbooksentity = new ArrayList<RaftedBooksEntity>();
                        for (int i = 0; i < readeds.length(); i++) {
                            final JSONObject item = (JSONObject) readeds.get(i);
                            String url = Constants.URL + "images/"
                                    + item.getString("cover");
                            String name = item.getString("title");
                            String isbn = item.getString("isbn");
                            RaftedBooksEntity entity = new RaftedBooksEntity();
                            entity.setPicurl(url);
                            entity.setBookname(name);
                            entity.setIsbn(isbn);
                            raftedbooksentity.add(entity);
                            SharePreferenceUtil util = new SharePreferenceUtil(
                                    context, "books");
                            util.setRaftingfBook(url);
                        }

                        RaftedBooksAdapter adapter = new RaftedBooksAdapter(
                                application, raftedbooksentity);
                        gridview.setAdapter(adapter);
                    } else {
                        Toast.makeText(context, "漂流历程获取失败,请重试...", 0).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                }

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(context, "请确认网络状况", 0).show();
            }
        });
    }

    @SuppressLint("NewApi")
    public static void QueryMyCollections(final Context context,
            final GridView gridview, final Application application) {

        final String url = Constants.URL + "QueryMyBooks.action";
        final SharePreferenceUtil userutil = new SharePreferenceUtil(context,
                Constants.SAVE_USER);

        AjaxParams params = new AjaxParams();
        params.put("userphone", userutil.getTel());
        FinalHttp fh = new FinalHttp();
        fh.post(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
                try {

                    JSONObject jsonObject = new JSONObject(t);
                    int code = jsonObject.getInt("code");
                    if (code == 200) {
                        JSONArray collections = jsonObject
                                .getJSONArray("readings");
                        ArrayList<CollectionEntity> collectionentity = new ArrayList<CollectionEntity>();
                        for (int i = 0; i < collections.length(); i++) {
                            final JSONObject item = (JSONObject) collections
                                    .get(i);
                            String url = Constants.URL + "images/"
                                    + item.getString("cover");
                            String name = item.getString("title");
                            String isbn = item.getString("isbn");
                            CollectionEntity entity = new CollectionEntity();
                            entity.setPicurl(url);
                            entity.setBookname(name);
                            entity.setIsbn(isbn);
                            collectionentity.add(entity);
                            // app.setValue(url);
                            // Log.i(TAG,"cao"+app.getValue());
                        }
                        MyCollctionAdapter adapter = new MyCollctionAdapter(
                                context, application, collectionentity);
                        gridview.setAdapter(adapter);
                    } else {
                        Toast.makeText(context, "漂流历程获取失败,请重试...", 0).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                }

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(context, "请确认网络状况", 0).show();
            }
        });
    }

    /**
     * 借还书
     * 
     * @param mcontext
     * @param mdialog
     * @param user
     */
    public static void borrowbook(final Context mcontext, final String usertel,
            final String isbn, final String driftpoint) {
        String url = Constants.URL + "borrowBook.action";
        AjaxParams params = new AjaxParams();
        params.put("userphone", usertel);
        params.put("bookisbn", isbn);
        params.put("driftpointid", driftpoint);
        FinalHttp fh = new FinalHttp();
        fh.post(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {
                // TODO Auto-generated method stub
                try {
                    JSONObject json = new JSONObject(t);
                    int code = json.getInt("code");
                    if (code == 100) {
                        Toast.makeText(mcontext, "该用户或者书不存在", 0).show();
                    } else if (code == 500) {
                        Toast.makeText(mcontext, "借书失败", 0).show();
                    } else if (code == 200) {

                        Toast.makeText(mcontext, "借书成功", 0).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(mcontext, e.getMessage(), 0).show();
                } finally {

                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                Toast.makeText(mcontext, "请确认网络状况", 0).show();
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }

    /**
     * 借还书
     * 
     * @param mcontext
     * @param mdialog
     * @param user
     */
    public static void returnbook(final Context mcontext, final String usertel,
            final String driftpoint) {
        String url = Constants.URL + "returnBook.action";
        AjaxParams params = new AjaxParams();
        params.put("userphone", usertel);
        params.put("driftpointid", driftpoint);
        FinalHttp fh = new FinalHttp();
        fh.post(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {
                // TODO Auto-generated method stub
                try {
                    JSONObject json = new JSONObject(t);
                    int code = json.getInt("code");
                    if (code == 100) {
                        Toast.makeText(mcontext, "该用户没有借未借书", 0).show();
                    } else if (code == 500) {
                        Toast.makeText(mcontext, "还书失败", 0).show();
                    } else if (code == 200) {
                        SharePreferenceUtil preferenceUtil = new SharePreferenceUtil(
                                mcontext, "borrowBook");

                        Toast.makeText(mcontext, "还书成功", 0).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(mcontext, e.getMessage(), 0).show();
                } finally {

                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                Toast.makeText(mcontext, "请确认网络状况", 0).show();
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }

    public static void QueryDriftpointsByLocation(final Context mcontext,
            final ListView listview, final Application application) {
        String url = Constants.URL + "queryDriftpointsByLocation.action";

        AjaxParams params = new AjaxParams();
        params.put("location", "yueluqu");
        FinalHttp fh = new FinalHttp();
        fh.post(url, params, new AjaxCallBack<String>() {
            @SuppressLint("NewApi")
            @Override
            public void onSuccess(String t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    int code = jsonObject.getInt("code");
                    if (code == 200) {
                        JSONArray driftpoint = jsonObject
                                .getJSONArray("driftpoint");

                        ArrayList<DriftpointEntity> driftpointEntities = new ArrayList<DriftpointEntity>();
                        for (int i = 0; i < driftpoint.length(); i++) {
                            DriftpointEntity driftpointEntity = new DriftpointEntity();
                            JSONObject item = (JSONObject) driftpoint.get(i);
                            // map.put("image", item.getString("image"));
                            // map.put("introduction",
                            // item.get("introduction"));
                            // map.put("name", item.get("name"));
                            String imgurl = Constants.URL + "images/"
                                    + item.getString("image");
                            driftpointEntity.setImg(imgurl);
                            driftpointEntity.setName(item.getString("name"));
                            driftpointEntity.setIntroduction(item
                                    .getString("introduction"));
                            driftpointEntities.add(driftpointEntity);
                        }
                        Log.e(TAG, driftpointEntities.toString());
                        YueLuDriftpointsAdapter adapter = new YueLuDriftpointsAdapter(
                                mcontext, application, driftpointEntities);
                        listview.setAdapter(adapter);
                        listview.deferNotifyDataSetChanged();
                    } else {
                        Toast.makeText(mcontext, "漂流岛获取失败...", 0).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(mcontext, "请确认网络状况", 0).show();
            }
        });

    }

    public static void QueryColumnByTime(final Context mcontext,
            final ListView listView, final Application application) {
        String url = Constants.URL + "QueryColumnByTime.action";
        AjaxParams params = new AjaxParams();
        FinalHttp fh = new FinalHttp();
        fh.post(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    int code = jsonObject.getInt("code");
                    if (code == 200) {
                        JSONArray columns = jsonObject.getJSONArray("columns");

                        ArrayList<ColumnEntity> columnEntities = new ArrayList<ColumnEntity>();
                        for (int i = 0; i < columns.length(); i++) {
                            ColumnEntity columnEntity = new ColumnEntity();
                            JSONObject item = (JSONObject) columns.get(i);
                            String imgurl = Constants.URL + "images/"
                                    + item.getString("columnpic");
                            columnEntity.setColumnid(item.getString("columnid"));
                            columnEntity.setColumnpic(imgurl);
                            columnEntity.setColumntitle(item
                                    .getString("columntitle"));
                            columnEntity.setTime(item.getString("time"));
                            // 获取user数据
                            JSONObject jsonObject2 = (JSONObject) item
                                    .getJSONObject("user");
                            columnEntity.setUsername(jsonObject2
                                    .getString("username"));
                            String userimgurl = Constants.URL + "images/"
                                    + jsonObject2.getString("photo");
                            columnEntity.setUserimage(userimgurl);
                            columnEntities.add(columnEntity);
                        }
                        Log.i(TAG, columnEntities.size() + "");
                        IndexColumnTabAdapter adapter = new IndexColumnTabAdapter(
                                mcontext, application, columnEntities);
                        listView.setAdapter(adapter);
                        listView.deferNotifyDataSetChanged();
                    } else {
                        Toast.makeText(mcontext, "栏目获取失败...", 0).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(mcontext, "栏目获取失败了...", 0).show();
            }
        });

    }

    /**
     * 
     * @param mcontext
     *            上下文
     * @param columnid
     */
    public static void SaveConcernToColumn(final Context mcontext,
            final String columnid) {
        final SharePreferenceUtil userutil = new SharePreferenceUtil(mcontext,
                Constants.SAVE_USER);
        String userid = "" + userutil.getId();
        String url = Constants.URL + "saveConcernToColumn.action";
        AjaxParams params = new AjaxParams();
        params.put("user_from", userid);
        params.put("columnid", columnid);
        Toast.makeText(mcontext, "HttpUtil关注成功了吗...", 0).show();
        FinalHttp fh = new FinalHttp();
        fh.post(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    int code = jsonObject.getInt("code");
                    if (code == 200) {
                        Toast.makeText(mcontext, "HttpUtil关注成功啦...", 0).show();
                    } else {
                        Toast.makeText(mcontext, "栏目获取失败...", 0).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(mcontext, "请确认网络状况", 0).show();
            }
        });

    }

    public static void ChooseWriteNotesInterface(final Context mcontext) {
        final SharePreferenceUtil userutil = new SharePreferenceUtil(mcontext,
                Constants.SAVE_USER);
        final String userid = "" + userutil.getId();
        String url = Constants.URL + "QueryMyReading.action";
        AjaxParams params = new AjaxParams();
        params.put("userid", userid);
        Toast.makeText(mcontext, "HttpUtil找到了跳转页面吗...", 0).show();
        FinalHttp fh = new FinalHttp();
        fh.post(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    int code = jsonObject.getInt("code");
                    if (code == 200) {

                        hasPreviousNotes(mcontext, userid);
                    } else if (code == 100) {
                        Intent intent = new Intent();

                        intent.setClass(mcontext, DriftnotesActivityError.class);
                        mcontext.startActivity(intent);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(mcontext, "请确认网络状况", 0).show();
            }
        });

    }
/**
 * 判断是否借了书籍
 * @param mcontext
 */
    public static void JudgeIfHasBook(final Context mcontext) {
        final SharePreferenceUtil userutil = new SharePreferenceUtil(mcontext,
                Constants.SAVE_USER);
        final String userid = "" + userutil.getId();
        String url = Constants.URL + "QueryMyReading.action";
        AjaxParams params = new AjaxParams();
        params.put("userid", userid);
        Toast.makeText(mcontext, "judge..", 0).show();
        FinalHttp fh = new FinalHttp();
        fh.post(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    int code = jsonObject.getInt("code");
                    Log.i(TAG, "code="+code+"");
                 
                    if (code == 200) {
                     
                        userutil.setHasBook("yes");  
                        Log.i("HttpUtil",""+userutil.getHasBook());
                        
                    } else if (code == 100) {
                        userutil.setHasBook("no");   
                        Log.i("HttpUtil",""+userutil.getHasBook());
                        
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(mcontext, "请确认网络状况", 0).show();
            }
        });

    }

    /**
     * code=200表示已经创建栏目，code=100表示为创建栏目
     * 
     * @param mcontext
     * @param userid
     */
    public static void hasPreviousNotes(final Context mcontext,
            final String userid) {
        String url = Constants.URL + "hasThisBookColumns.action";
        AjaxParams params = new AjaxParams();
        params.put("userid", userid);
        Toast.makeText(mcontext, "HttpUtil正在判断..", 0).show();
        FinalHttp fh = new FinalHttp();
        fh.post(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    int code = jsonObject.getInt("code");
                    if (code == 100) {
                        Intent intent = new Intent();
                        intent.setClass(mcontext, WriteFirstNotesActivity.class);
                        mcontext.startActivity(intent);
                    } else if (code == 200) {
                        Intent intent = new Intent();
                        intent.setClass(mcontext, WriteNotesActivity.class);
                        mcontext.startActivity(intent);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(mcontext, "请确认网络状况", 0).show();
            }
        });

    }

    public static void QueryMyReading(final Context mcontext,
            final String userid) {

        String url = Constants.URL + "QueryMyReading.action";
        AjaxParams params = new AjaxParams();
        params.put("userid", userid);
        Toast.makeText(mcontext, "HttpUtil正在查询现在他借的书籍..", 0).show();
        FinalHttp fh = new FinalHttp();
        fh.post(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    int code = jsonObject.getInt("code");
                    if (code == 100) {
                        Toast.makeText(mcontext, "获取失败", 0).show();
                    } else if (code == 200) {
                        JSONArray jsonArray = jsonObject.getJSONArray("books");
                        JSONArray jsonArray2 = jsonArray.getJSONArray(0);
                        JSONObject jsonObject2 = (JSONObject) jsonArray2.get(0);
                        String isbn = jsonObject2.getString("isbn");
                        String title = jsonObject2.getString("title");
                        Log.i(TAG, isbn + title);
                        final SharePreferenceUtil bookutil = new SharePreferenceUtil(
                                mcontext, Constants.SAVE_USER);
                        bookutil.setIsbn(isbn);
                        bookutil.setBookname(title);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(mcontext, "请确认网络状况", 0).show();
            }
        });
    }

    /**
     * 创建栏目
     * 
     * @param mcontext
     * @param columnEntity
     */
    public static void CreateColumn(final Context mcontext,
            final ColumnEntity columnEntity) {
        String url = Constants.URL + "CreateColumn.action";
        AjaxParams params = new AjaxParams();
        params.put("bookisbn", columnEntity.getBookisbn());
        params.put("userphone", columnEntity.getUserphone());
        params.put("columntitle", columnEntity.getColumntitle());
        params.put("introduction", columnEntity.getIntroduction());

        Toast.makeText(mcontext, "HttpUtil正在创建栏目呢..", 0).show();
        FinalHttp fh = new FinalHttp();
        fh.post(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    int code = jsonObject.getInt("code");
                    if (code == 100) {
                        Toast.makeText(mcontext, "获创建失败", 0).show();
                    } else if (code == 200) {
                        Toast.makeText(mcontext, "创建成功", 0).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(mcontext, "请确认网络状况", 0).show();
            }
        });
    }

    public static void InitDriftNotes(final Context mcontext,final String columnid,final TextView driftnotes_username
         ,   final TextView driftnotes_columntitle,final TextView driftnotes_columnintroduction,final TextView driftnotes_bookname
         ,final ImageView driftnotes_userphoto   ) {
        String url = Constants.URL + "findColumnByColumnid.action";
        AjaxParams params = new AjaxParams();
        params.put("columnid", columnid);
        Toast.makeText(mcontext, "HttpUtil正在查找栏目..", 0).show();
        FinalHttp fh = new FinalHttp();
        fh.post(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    int code = jsonObject.getInt("code");
                    if (code == 100) {
                        Toast.makeText(mcontext, "获取失败", 0).show();
                    } else if (code == 200) {
                           JSONObject jsonObject2 = jsonObject.getJSONObject("column");
                            String imgurl = Constants.URL + "images/"
                                    + jsonObject2.getString("columnpic");
                            Log.i(TAG,imgurl);
                            driftnotes_columntitle.setText(jsonObject2.getString("columntitle"));
                            driftnotes_columnintroduction.setText(jsonObject2.getString("introduction"));
                            // 获取user数据
                            final JSONObject jsonObject3 = (JSONObject) jsonObject2
                                    .getJSONObject("user");
                            driftnotes_username.setText(jsonObject3.getString("username"));
                            String userimgurl = Constants.URL + "images/"
                                    + jsonObject3.getString("photo");
                            FinalBitmap bitmap = FinalBitmap.create(mcontext);
                            bitmap.display(driftnotes_userphoto, userimgurl);
                            JSONObject jsonObject4 = (JSONObject) jsonObject2
                                    .getJSONObject("book");
                            driftnotes_bookname.setText(jsonObject4.getString("title"));
                            driftnotes_userphoto.setOnClickListener(new OnClickListener() {
                                public void onClick(View arg0) {
                                    Intent intent = new Intent();
                                    Bundle bundle = new Bundle();
                                    try {
                                        bundle.putString("userphone", jsonObject3.getString("tel"));
                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    intent.putExtras(bundle);
                                    intent.setClass(mcontext, PersonnalCenterActivity.class);
                                    mcontext.startActivity(intent);
                                }
                            });
                        Toast.makeText(mcontext, "获取成功", 0).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(mcontext, "请确认网络状况", 0).show();
            }
        });
    }
}
