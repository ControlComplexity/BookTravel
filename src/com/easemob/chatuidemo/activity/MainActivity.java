/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easemob.chatuidemo.activity;
//书旅
import net.tsz.afinal.FinalBitmap;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.application.booktravel.fragment.DriftLandFragment;
import com.application.booktravel.fragment.DynamicsFragment;
import com.application.booktravel.fragment.IndexFragment;
import com.application.booktravel.fragment.PersonnalCenterFragment;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.applib.utils.ToolUtils;
import com.easemob.chatuidemo.R;

public class MainActivity extends FragmentActivity implements EMEventListener,OnClickListener {

    protected static final String TAG = "MainActivity";


    /**
     * 用于展示漂流岛的Fragment
     */
    private DriftLandFragment messageFragment;

    /**
     * 用于展示首页的Fragment
     */
    private IndexFragment contactsFragment;

    /**
     * 用于展示"消息"的Fragment
     */
    private DynamicsFragment informationFragment;

    
    /**
     * 用于展示"我的"的Fragment
     */
    private PersonnalCenterFragment settingFragment;

    /**
     * 消息界面布局
     */
    private View messageLayout;

    /**
     * 联系人界面布局
     */
    private View contactsLayout;

    /**
     * 设置界面布局
     */
    private View settingLayout;

    /**
     * 消息界面布局
     */
    private View informationLayout;




    private TextView contactsText1;
    private TextView settingText1;
    private TextView messageText1;
    private TextView informationText1;

    
    /**
     * 在Tab布局上显示消息标题的控件
     */
    private TextView messageText;

    /**
     * 在Tab布局上显示联系人标题的控件
     */
    private TextView contactsText;

    /**
     * 在Tab布局上显示设置标题的控件
     */
    private TextView settingText;

    /**
     * 在Tab布局上显示消息标题的控件
     */
    private TextView informationText;

    
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;

 
    
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // 设置窗体风格
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.booktravel_main);
        Bundle name = getIntent().getExtras();
        String tel = name.getString("tel");
        Toast.makeText(this, tel, 0).show();// 这个现实的不是电话号码，而是名字，比如谭英杰
        /**
         * 初始化布局元素
         */
        initViews();
        fragmentManager = getSupportFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);
        
        ToolUtils.finalbitmap=FinalBitmap.create(getApplicationContext());
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     * 
     * @param index
     *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
     */
    private void setTabSelection(int index) {
        // 每次选中前先清除上次选中的状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
        case 0:
            // 当点击了消息tab时，改变控件的图片和文字颜色
            //messageImage.setImageResource(R.drawable.message_selected);
            messageText.setTextColor(Color.WHITE);
            if (messageFragment == null) {
                // 如果MessageFragment为空，则创建一个并添加到界面上
                messageFragment = new DriftLandFragment();
                transaction.add(R.id.content, messageFragment);
            } else {
                // 如果MessageFragment不为空，则直接将它显示出来
                transaction.show(messageFragment);
            }
            break;
        case 1:
        //  contactsImage.setImageResource(R.drawable.contacts_selected);
            contactsText.setTextColor(Color.WHITE);
            if (contactsFragment == null) {
                contactsFragment = new IndexFragment();
                transaction.add(R.id.content, contactsFragment);
            } else {
                transaction.show(contactsFragment);
            }
            break;
    
        
        case 2:
    //  informationImage.setImageResource(R.drawable.setting_selected);
        informationText.setTextColor(Color.WHITE);
        if (informationFragment == null) {
            informationFragment = new DynamicsFragment();
           
            transaction.add(R.id.content, informationFragment);
        } else {
            transaction.show(informationFragment);
        }
        break;
        
        case 3:
        //  settingImage.setImageResource(R.drawable.setting_selected);
            settingText.setTextColor(Color.WHITE);
            if (settingFragment == null) {
                settingFragment = new PersonnalCenterFragment();
                transaction.add(R.id.content, settingFragment);
            } else {
                transaction.show(settingFragment);
            }
            break;
    }
        
        transaction.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     * 
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (messageFragment != null) {
            transaction.hide(messageFragment);
        }
        if (contactsFragment != null) {
            transaction.hide(contactsFragment);
        }
        if (settingFragment != null) {
            transaction.hide(settingFragment);
        }
        if(informationFragment != null)
        {
            transaction.hide(informationFragment);
        }
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
    
        messageText.setTextColor(Color.parseColor("#82858b"));

        contactsText.setTextColor(Color.parseColor("#82858b"));
    
        settingText.setTextColor(Color.parseColor("#82858b"));
    
        informationText.setTextColor(Color.parseColor("#82858b"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.message_layout:
            setTabSelection(0);
            break;
        case R.id.contacts_layout:
            setTabSelection(1);
            break;
        
        case R.id.information_layout:
            setTabSelection(2);
            break;
        case R.id.setting_layout:
            setTabSelection(3);
            break;
        }
    }

    /**
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */
    private void initViews() {
        messageLayout = findViewById(R.id.message_layout);
        contactsLayout = findViewById(R.id.contacts_layout);
        settingLayout = findViewById(R.id.setting_layout);
        informationLayout = findViewById(R.id.information_layout);
        
        messageText1 = (TextView) findViewById(R.id.message_text1);
        contactsText1 = (TextView) findViewById(R.id.contacts_text1);
        settingText1 = (TextView) findViewById(R.id.setting_text1);
        informationText1 = (TextView) findViewById(R.id.information_text1);
        
        
        messageText = (TextView) findViewById(R.id.message_text);
        contactsText = (TextView) findViewById(R.id.contacts_text);
        settingText = (TextView) findViewById(R.id.setting_text);
        informationText = (TextView) findViewById(R.id.information_text);
        
        messageLayout.setOnClickListener(this);
        contactsLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
        informationLayout.setOnClickListener(this);
    }

    @Override
    public void onEvent(EMNotifierEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    
   
    

  
   

}
