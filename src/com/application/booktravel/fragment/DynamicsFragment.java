package com.application.booktravel.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.EMEventListener;
import com.easemob.EMGroupChangeListener;
import com.easemob.EMNotifierEvent;
import com.easemob.EMValueCallBack;
import com.easemob.applib.controller.HXSDKHelper;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactListener;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMConversation.EMConversationType;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.EMMessage.Type;
import com.easemob.chat.TextMessageBody;
import com.easemob.chatuidemo.Constant;
import com.easemob.chatuidemo.DemoApplication;
import com.easemob.chatuidemo.R;
import com.easemob.chatuidemo.activity.ChatActivity;
import com.easemob.chatuidemo.activity.ContactlistFragment;
import com.easemob.chatuidemo.activity.GroupsActivity;
import com.easemob.chatuidemo.db.InviteMessgeDao;
import com.easemob.chatuidemo.db.UserDao;
import com.easemob.chatuidemo.domain.InviteMessage;
import com.easemob.chatuidemo.domain.InviteMessage.InviteMesageStatus;
import com.easemob.chatuidemo.domain.User;
import com.easemob.chatuidemo.utils.CommonUtils;
import com.easemob.util.EMLog;
import com.easemob.util.HanziToPinyin;
import com.easemob.util.NetUtils;

public class DynamicsFragment extends Fragment implements OnClickListener,EMEventListener {
    protected static final String TAG = "DynamicsFragment";
    // 未读消息textview
    private TextView unreadLabel;
    // 未读通讯录textview
    private TextView unreadAddressLable;

    private int currentIndex;

    private MyConnectionListener connectionListener = null;

    private View view;
    private ViewPager mPaper;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    private ChatAllHistoryFragment f1;
    private ContactlistFragment f2;
    private SettingsFragment f3;

    private Button dynamicbtn_conversation;
    private Button dynamicbtn_address_list;
    private Button dynamicbtn_setting;
    private Display display;
    private int itemWidth;
    // private ViewAdapter videoAdapter_hot,videoAdapter_new;
    private GridView gridView_newVideo, gridView_hotVideo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dynamicsfragment_layout, container,
                false);

        initLayout();

        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return mFragments.size();
            }

            @Override
            public android.support.v4.app.Fragment getItem(int arg0) {
                // TODO Auto-generated method stub
                return mFragments.get(arg0);
            }
        };
        mPaper.setAdapter(mAdapter);
        mPaper.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                currentIndex = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        init();
        return view;
    }

    /**
     * 初始化控件
     */
    public void initLayout() {
        unreadLabel = (TextView) view
                .findViewById(R.id.dynamicunread_msg_number);
        unreadAddressLable = (TextView) view
                .findViewById(R.id.dynamicunread_address_number);

        dynamicbtn_conversation = (Button) view
                .findViewById(R.id.dynamicbtn_conversation);
        dynamicbtn_address_list = (Button) view
                .findViewById(R.id.dynamicbtn_address_list);
        dynamicbtn_setting = (Button) view
                .findViewById(R.id.dynamicbtn_setting);

        mPaper = (ViewPager) view.findViewById(R.id.dynamicsview_pager);

        dynamicbtn_conversation.setOnClickListener(this);
        dynamicbtn_address_list.setOnClickListener(this);
        dynamicbtn_setting.setOnClickListener(this);

        f1 = new ChatAllHistoryFragment();
        f2 = new ContactlistFragment();
        f3 = new SettingsFragment();

        mFragments.add(f1);
        mFragments.add(f2);
        mFragments.add(f3);

    }

    private void init() {
        // setContactListener监听联系人的变化等
        EMContactManager.getInstance().setContactListener(
                new MyContactListener());
        // 注册一个监听连接状态的listener

        connectionListener = new MyConnectionListener();
        EMChatManager.getInstance().addConnectionListener(connectionListener);

     
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.dynamicbtn_conversation:

            mPaper.setCurrentItem(0);
            break;
        case R.id.dynamicbtn_address_list:

            mPaper.setCurrentItem(1);
            break;
        case R.id.dynamicbtn_setting:

            mPaper.setCurrentItem(2);
            break;

        default:
            break;
        }
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

    // 环信MainActivity

    static void asyncFetchGroupsFromServer() {
        HXSDKHelper.getInstance().asyncFetchGroupsFromServer(new EMCallBack() {

            @Override
            public void onSuccess() {
                HXSDKHelper.getInstance().noitifyGroupSyncListeners(true);

                if (HXSDKHelper.getInstance().isContactsSyncedWithServer()) {
                    HXSDKHelper.getInstance().notifyForRecevingEvents();
                }
            }

            @Override
            public void onError(int code, String message) {
                HXSDKHelper.getInstance().noitifyGroupSyncListeners(false);
            }

            @Override
            public void onProgress(int progress, String status) {

            }

        });
    }

    static void asyncFetchContactsFromServer() {
        HXSDKHelper.getInstance().asyncFetchContactsFromServer(
                new EMValueCallBack<List<String>>() {

                    @Override
                    public void onSuccess(List<String> usernames) {
                        Context context = HXSDKHelper.getInstance()
                                .getAppContext();

                        System.out.println("----------------"
                                + usernames.toString());
                        EMLog.d("roster", "contacts size: " + usernames.size());
                        Map<String, User> userlist = new HashMap<String, User>();
                        for (String username : usernames) {
                            User user = new User();
                            user.setUsername(username);
                            // setUserHearder(username, user);
                            userlist.put(username, user);
                        }
                        // 添加user"申请与通知"
                        User newFriends = new User();
                        newFriends.setUsername(Constant.NEW_FRIENDS_USERNAME);
                        String strChat = context
                                .getString(R.string.Application_and_notify);
                        newFriends.setNick(strChat);

                        userlist.put(Constant.NEW_FRIENDS_USERNAME, newFriends);
                        // 添加"群聊"
                        User groupUser = new User();
                        String strGroup = context
                                .getString(R.string.group_chat);
                        groupUser.setUsername(Constant.GROUP_USERNAME);
                        groupUser.setNick(strGroup);
                        groupUser.setHeader("");
                        userlist.put(Constant.GROUP_USERNAME, groupUser);

                        // 添加"聊天室"
                        User chatRoomItem = new User();
                        String strChatRoom = context
                                .getString(R.string.chat_room);
                        chatRoomItem.setUsername(Constant.CHAT_ROOM);
                        chatRoomItem.setNick(strChatRoom);
                        chatRoomItem.setHeader("");
                        userlist.put(Constant.CHAT_ROOM, chatRoomItem);

                        // 添加"Robot"
                        User robotUser = new User();
                        String strRobot = context
                                .getString(R.string.robot_chat);
                        robotUser.setUsername(Constant.CHAT_ROBOT);
                        robotUser.setNick(strRobot);
                        robotUser.setHeader("");
                        userlist.put(Constant.CHAT_ROBOT, robotUser);

                        // 存入内存
                        DemoApplication.getInstance().setContactList(userlist);
                        // 存入db
                        UserDao dao = new UserDao(context);
                        List<User> users = new ArrayList<User>(userlist
                                .values());
                        dao.saveContactList(users);

                        HXSDKHelper.getInstance().notifyContactsSyncListener(
                                true);

                        if (HXSDKHelper.getInstance()
                                .isGroupsSyncedWithServer()) {
                            HXSDKHelper.getInstance().notifyForRecevingEvents();
                        }

                    }

                    @Override
                    public void onError(int error, String errorMsg) {
                        HXSDKHelper.getInstance().notifyContactsSyncListener(
                                false);
                    }

                });
    }

    static void asyncFetchBlackListFromServer() {
        HXSDKHelper.getInstance().asyncFetchBlackListFromServer(
                new EMValueCallBack<List<String>>() {

                    @Override
                    public void onSuccess(List<String> value) {
                        EMContactManager.getInstance().saveBlackList(value);
                        HXSDKHelper.getInstance().notifyBlackListSyncListener(
                                true);
                    }

                    @Override
                    public void onError(int error, String errorMsg) {
                        HXSDKHelper.getInstance().notifyBlackListSyncListener(
                                false);
                    }

                });
    }

    /**
     * 刷新未读消息数
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            unreadLabel.setText(String.valueOf(count));
            unreadLabel.setVisibility(View.VISIBLE);
        } else {
            unreadLabel.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 刷新申请与通知消息数
     */
    public void updateUnreadAddressLable() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                int count = getUnreadAddressCountTotal();
                if (count > 0) {
                    unreadAddressLable.setText(String.valueOf(count));
                    unreadAddressLable.setVisibility(View.VISIBLE);
                } else {
                    unreadAddressLable.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    /**
     * 获取未读申请与通知消息
     * 
     * @return
     */
    public int getUnreadAddressCountTotal() {
        int unreadAddressCountTotal = 0;
        if (DemoApplication.getInstance().getContactList()
                .get(Constant.NEW_FRIENDS_USERNAME) != null)
            unreadAddressCountTotal = DemoApplication.getInstance()
                    .getContactList().get(Constant.NEW_FRIENDS_USERNAME)
                    .getUnreadMsgCount();
        return unreadAddressCountTotal;
    }

    /**
     * 获取未读消息数
     * 
     * @return
     */
    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal = 0;
        int chatroomUnreadMsgCount = 0;
        unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
        for (EMConversation conversation : EMChatManager.getInstance()
                .getAllConversations().values()) {
            if (conversation.getType() == EMConversationType.ChatRoom)
                chatroomUnreadMsgCount = chatroomUnreadMsgCount
                        + conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal - chatroomUnreadMsgCount;
    }

    private InviteMessgeDao inviteMessgeDao;
    private UserDao userDao;

    /***
     * 好友变化listener
     * 
     */
    public class MyContactListener implements EMContactListener {

        @Override
        public void onContactAdded(List<String> usernameList) {
            EMLog.i("MainActivity",
                    "onContactAdded : " + usernameList.toString());
            // 保存增加的联系人
            Map<String, User> localUsers = DemoApplication.getInstance()
                    .getContactList();
            Map<String, User> toAddUsers = new HashMap<String, User>();
            for (String username : usernameList) {
                User user = setUserHead(username);
                // 添加好友时可能会回调added方法两次
                if (!localUsers.containsKey(username)) {
                    userDao.saveContact(user);
                }
                toAddUsers.put(username, user);
            }
            localUsers.putAll(toAddUsers);
            // 刷新ui
            if (currentIndex == 1)
                f2.refresh();

        }

        @Override
        public void onContactDeleted(final List<String> usernameList) {
            EMLog.i("MainActivity",
                    "onContactDeleted : " + usernameList.toString());
            // 被删除
            Map<String, User> localUsers = DemoApplication.getInstance()
                    .getContactList();
            for (String username : usernameList) {
                localUsers.remove(username);
                userDao.deleteContact(username);
                inviteMessgeDao.deleteMessage(username);
            }
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    // 如果正在与此用户的聊天页面
                    String st10 = getResources().getString(
                            R.string.have_you_removed);
                    if (ChatActivity.activityInstance != null
                            && usernameList
                                    .contains(ChatActivity.activityInstance
                                            .getToChatUsername())) {
                        Toast.makeText(
                                getActivity(),
                                ChatActivity.activityInstance
                                        .getToChatUsername() + st10, 1).show();
                        ChatActivity.activityInstance.finish();
                    }
                    updateUnreadLabel();
                    // 刷新ui
                    f2.refresh();
                    f1.refresh();
                }
            });

        }

        @Override
        public void onContactInvited(String username, String reason) {
            EMLog.i("MainActivity", "onContactInvited : " + username + "--"
                    + reason);
            // 接到邀请的消息，如果不处理(同意或拒绝)，掉线后，服务器会自动再发过来，所以客户端不需要重复提醒
            List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();

            for (InviteMessage inviteMessage : msgs) {
                if (inviteMessage.getGroupId() == null
                        && inviteMessage.getFrom().equals(username)) {
                    inviteMessgeDao.deleteMessage(username);
                }
            }
            // 自己封装的javabean
            InviteMessage msg = new InviteMessage();
            msg.setFrom(username);
            msg.setTime(System.currentTimeMillis());
            msg.setReason(reason);
            Log.d(TAG, username + "请求加你为好友,reason: " + reason);
            // 设置相应status
            msg.setStatus(InviteMesageStatus.BEINVITEED);
            notifyNewIviteMessage(msg);

        }

        @Override
        public void onContactAgreed(String username) {
            EMLog.i("MainActivity", "onContactAgreed : " + username);
            List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();
            for (InviteMessage inviteMessage : msgs) {
                if (inviteMessage.getFrom().equals(username)) {
                    return;
                }
            }
            // 自己封装的javabean
            InviteMessage msg = new InviteMessage();
            msg.setFrom(username);
            msg.setTime(System.currentTimeMillis());
            Log.d(TAG, username + "同意了你的好友请求");
            msg.setStatus(InviteMesageStatus.BEAGREED);
            notifyNewIviteMessage(msg);

        }

        @Override
        public void onContactRefused(String username) {
            EMLog.i("MainActivity", "onContactRefused : " + username);
            // 参考同意，被邀请实现此功能,demo未实现
            Log.d(username, username + "拒绝了你的好友请求");
        }

    }

    /**
     * set head
     * 
     * @param username
     * @return
     */
    User setUserHead(String username) {
        User user = new User();
        user.setUsername(username);
        String headerName = null;
        if (!TextUtils.isEmpty(user.getNick())) {
            headerName = user.getNick();
        } else {
            headerName = user.getUsername();
        }
        if (username.equals(Constant.NEW_FRIENDS_USERNAME)) {
            user.setHeader("");
        } else if (Character.isDigit(headerName.charAt(0))) {
            user.setHeader("#");
        } else {
            user.setHeader(HanziToPinyin.getInstance()
                    .get(headerName.substring(0, 1)).get(0).target.substring(0,
                    1).toUpperCase());
            char header = user.getHeader().toLowerCase().charAt(0);
            if (header < 'a' || header > 'z') {
                user.setHeader("#");
            }
        }
        return user;
    }

    /**
     * 连接监听listener
     * 
     */
    public class MyConnectionListener implements EMConnectionListener {

        @Override
        public void onConnected() {
            boolean groupSynced = HXSDKHelper.getInstance()
                    .isGroupsSyncedWithServer();
            boolean contactSynced = HXSDKHelper.getInstance()
                    .isContactsSyncedWithServer();

            // in case group and contact were already synced, we supposed to
            // notify sdk we are ready to receive the events
            if (groupSynced && contactSynced) {
                new Thread() {
                    @Override
                    public void run() {
                        HXSDKHelper.getInstance().notifyForRecevingEvents();
                    }
                }.start();
            } else {
                if (!groupSynced) {
                    asyncFetchGroupsFromServer();
                }

                if (!contactSynced) {
                    asyncFetchContactsFromServer();
                }

                if (!HXSDKHelper.getInstance().isBlackListSyncedWithServer()) {
                    asyncFetchBlackListFromServer();
                }
            }

            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    f1.errorItem.setVisibility(View.GONE);
                }

            });
        }

        @Override
        public void onDisconnected(final int error) {
            final String st1 = getResources().getString(
                    R.string.can_not_connect_chat_server_connection);
            final String st2 = getResources().getString(
                    R.string.the_current_network);
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                        // showAccountRemovedDialog();
                    } else if (error == EMError.CONNECTION_CONFLICT) {
                        // 显示帐号在其他设备登陆dialog
                        // showConflictDialog();
                    } else {
                        f1.errorItem.setVisibility(View.VISIBLE);
                        if (NetUtils.hasNetwork(getActivity()))
                            f1.errorText.setText(st1);
                        else
                            f1.errorText.setText(st2);

                    }
                }

            });
        }
    }

    /**
     * MyGroupChangeListener
     */
    public class MyGroupChangeListener implements EMGroupChangeListener {

        @Override
        public void onInvitationReceived(String groupId, String groupName,
                String inviter, String reason) {

            boolean hasGroup = false;
            for (EMGroup group : EMGroupManager.getInstance().getAllGroups()) {
                if (group.getGroupId().equals(groupId)) {
                    hasGroup = true;
                    break;
                }
            }
            if (!hasGroup)
                return;

            // 被邀请
            String st3 = getResources().getString(
                    R.string.Invite_you_to_join_a_group_chat);
            EMMessage msg = EMMessage.createReceiveMessage(Type.TXT);
            msg.setChatType(ChatType.GroupChat);
            msg.setFrom(inviter);
            msg.setTo(groupId);
            msg.setMsgId(UUID.randomUUID().toString());
            msg.addBody(new TextMessageBody(inviter + " " + st3));
            // 保存邀请消息
            EMChatManager.getInstance().saveMessage(msg);
            // 提醒新消息
            HXSDKHelper.getInstance().getNotifier().viberateAndPlayTone(msg);

            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    updateUnreadLabel();
                    // 刷新ui
                    if (currentIndex == 0)
                        f1.refresh();
                    if (CommonUtils.getTopActivity(getActivity()).equals(
                            GroupsActivity.class.getName())) {
                        GroupsActivity.instance.onResume();
                    }
                }
            });

        }

        @Override
        public void onInvitationAccpted(String groupId, String inviter,
                String reason) {

        }

        @Override
        public void onInvitationDeclined(String groupId, String invitee,
                String reason) {

        }

        @Override
        public void onUserRemoved(String groupId, String groupName) {

            // 提示用户被T了，demo省略此步骤
            // 刷新ui
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        updateUnreadLabel();
                        if (currentIndex == 0)
                            f1.refresh();
                        if (CommonUtils.getTopActivity(getActivity()).equals(
                                GroupsActivity.class.getName())) {
                            GroupsActivity.instance.onResume();
                        }
                    } catch (Exception e) {
                        EMLog.e(TAG, "refresh exception " + e.getMessage());
                    }
                }
            });
        }

        @Override
        public void onGroupDestroy(String groupId, String groupName) {

            // 群被解散
            // 提示用户群被解散,demo省略
            // 刷新ui
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    updateUnreadLabel();
                    if (currentIndex == 0)
                        f2.refresh();
                    if (CommonUtils.getTopActivity(getActivity()).equals(
                            GroupsActivity.class.getName())) {
                        GroupsActivity.instance.onResume();
                    }
                }
            });

        }

        @Override
        public void onApplicationReceived(String groupId, String groupName,
                String applyer, String reason) {

            // 用户申请加入群聊
            InviteMessage msg = new InviteMessage();
            msg.setFrom(applyer);
            msg.setTime(System.currentTimeMillis());
            msg.setGroupId(groupId);
            msg.setGroupName(groupName);
            msg.setReason(reason);
            Log.d(TAG, applyer + " 申请加入群聊：" + groupName);
            msg.setStatus(InviteMesageStatus.BEAPPLYED);
            notifyNewIviteMessage(msg);
        }

        @Override
        public void onApplicationAccept(String groupId, String groupName,
                String accepter) {

            String st4 = getResources().getString(
                    R.string.Agreed_to_your_group_chat_application);
            // 加群申请被同意
            EMMessage msg = EMMessage.createReceiveMessage(Type.TXT);
            msg.setChatType(ChatType.GroupChat);
            msg.setFrom(accepter);
            msg.setTo(groupId);
            msg.setMsgId(UUID.randomUUID().toString());
            msg.addBody(new TextMessageBody(accepter + " " + st4));
            // 保存同意消息
            EMChatManager.getInstance().saveMessage(msg);
            // 提醒新消息
            HXSDKHelper.getInstance().getNotifier().viberateAndPlayTone(msg);

            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    updateUnreadLabel();
                    // 刷新ui
                    if (currentIndex == 0)
                        f1.refresh();
                    if (CommonUtils.getTopActivity(getActivity()).equals(
                            GroupsActivity.class.getName())) {
                        GroupsActivity.instance.onResume();
                    }
                }
            });
        }

        @Override
        public void onApplicationDeclined(String groupId, String groupName,
                String decliner, String reason) {
            // 加群申请被拒绝，demo未实现
        }
    }

    /**
     * 保存提示新消息
     * 
     * @param msg
     */
    private void notifyNewIviteMessage(InviteMessage msg) {
        saveInviteMsg(msg);
        // 提示有新消息
        HXSDKHelper.getInstance().getNotifier().viberateAndPlayTone(null);

        // 刷新bottom bar消息未读数
        updateUnreadAddressLable();
        // 刷新好友页面ui
        if (currentIndex == 1)
            f2.refresh();
    }

    /**
     * 保存邀请等msg
     * 
     * @param msg
     */
    private void saveInviteMsg(InviteMessage msg) {
        // 保存msg
        inviteMessgeDao.saveMessage(msg);
        // 未读数加1
        User user = DemoApplication.getInstance().getContactList()
                .get(Constant.NEW_FRIENDS_USERNAME);
        if (user.getUnreadMsgCount() == 0)
            user.setUnreadMsgCount(user.getUnreadMsgCount() + 1);
    }

    private android.app.AlertDialog.Builder conflictBuilder;
    private android.app.AlertDialog.Builder accountRemovedBuilder;
    private boolean isConflictDialogShow;
    private boolean isAccountRemovedDialogShow;
    private BroadcastReceiver internalDebugReceiver;

    @Override
    public void onEvent(EMNotifierEvent event) {
        EMLog.i("ssss", "MainActivity--" + ((EMMessage) event.getData()).getBody().toString());
        switch (event.getEvent()) {
        case EventNewMessage: // 普通消息
        {
            EMMessage message = (EMMessage) event.getData();

            // 提示新消息
            HXSDKHelper.getInstance().getNotifier().onNewMsg(message);

            refreshUI();
            break;
        }

        case EventOfflineMessage: {
            refreshUI();
            break;
        }

        case EventConversationListChanged: {
            refreshUI();
            break;
        }
        
        default:
            break;
        }
        
    }
    private void refreshUI() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                // 刷新bottom bar消息未读数
                updateUnreadLabel();
                if (currentIndex == 0) {
                    // 当前页面如果为聊天历史页面，刷新此页面
                    if (f1 != null) {
                        f1.refresh();
                    }
                }
            }
        });
    }
}
