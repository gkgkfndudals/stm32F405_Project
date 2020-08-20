package com.example.refrigerator;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable iconDrawable ; //제품 사진
    private int _id ; // 키
    private String name ; // 제품명
    private String kind ; // 제품 종류
    private String expiration ; //유통기한


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFind() {
        return kind;
    }

    public void setFind(String find) {
        this.kind = find;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public void setIcon(Drawable icon) {
        this.iconDrawable = icon;
    }


    public Drawable getIcon() {
        return this.iconDrawable ;
    }

}
