package com.application.booktravel.entity;

public class ColumnEntity {
    private String bookisbn;
    private String bookname;
    private String columnid;
    private String columnpic;
    private String columntitle;
    private String introduction;
    private String time;
    private String username;
    private String userimage;
    private String userphone;

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookname() {
        return bookname;
    }

    public String getBookisbn() {
        return bookisbn;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public void setBookisbn(String bookisbn) {
        this.bookisbn = bookisbn;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public ColumnEntity() {
    }

    public String getColumnpic() {
        return columnpic;
    }

    public void setColumnpic(String columnpic) {
        this.columnpic = columnpic;
    }

    public String getColumntitle() {
        return columntitle;
    }

    public void setColumntitle(String columntitle) {
        this.columntitle = columntitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public ColumnEntity(String columnid, String columnpic, String columntitle,
            String time, String username, String userimage) {
        super();
        this.columnid = columnid;
        this.columnpic = columnpic;
        this.columntitle = columntitle;
        this.time = time;
        this.username = username;
        this.userimage = userimage;
    }

    public String getColumnid() {
        return columnid;
    }

    public void setColumnid(String columnid) {
        this.columnid = columnid;
    }

}
