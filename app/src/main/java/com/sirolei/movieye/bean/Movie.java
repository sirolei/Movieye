package com.sirolei.movieye.bean;

import java.io.Serializable;

/**
 * Created by sansi on 2016/1/16.
 */
public class Movie implements Serializable{

    private String imgUrl;
    private int id;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
