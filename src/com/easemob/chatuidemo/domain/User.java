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
package com.easemob.chatuidemo.domain;

import java.util.HashSet;
import java.util.Set;

import com.easemob.chat.EMContact;

public class User extends EMContact {
    private int unreadMsgCount;
    private String header;
    private String avatar;

    // 书旅的属性
    private Integer userid;
    // private MyBookActivity mybook;
    private String nickname;
    private String password;
    private String tel;
    private String photo;
    private Set topics = new HashSet(0);
    private Set driftprocesses = new HashSet(0);
    private Set comments = new HashSet(0);
    private Set mybooks = new HashSet(0);
    private Set bookreviews = new HashSet(0);

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String userphone, String password) {
        this.username = username;
        this.password = password;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Set getTopics() {
        return topics;
    }

    public void setTopics(Set topics) {
        this.topics = topics;
    }

    public Set getDriftprocesses() {
        return driftprocesses;
    }

    public void setDriftprocesses(Set driftprocesses) {
        this.driftprocesses = driftprocesses;
    }

    public Set getComments() {
        return comments;
    }

    public void setComments(Set comments) {
        this.comments = comments;
    }

    public Set getMybooks() {
        return mybooks;
    }

    public void setMybooks(Set mybooks) {
        this.mybooks = mybooks;
    }

    public Set getBookreviews() {
        return bookreviews;
    }

    public void setBookreviews(Set bookreviews) {
        this.bookreviews = bookreviews;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getUnreadMsgCount() {
        return unreadMsgCount;
    }

    public void setUnreadMsgCount(int unreadMsgCount) {
        this.unreadMsgCount = unreadMsgCount;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int hashCode() {
        return 17 * getUsername().hashCode();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof User)) {
            return false;
        }
        return getUsername().equals(((User) o).getUsername());
    }

    @Override
    public String toString() {
        return nick == null ? username : nick;
    }
}
