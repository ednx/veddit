package com.eovalencia.veddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Eovalencia on 21/01/17.
 */

public class SubReddit {

    private String id;
    private String image;
    private String title;
    private String description;
    private long subscriber;
    private long date;
    private String icon_img;
    private String display_name;
    private String description_long;
    private String url;

    public SubReddit(String id, String image, String title, String description, long subscriber, long date, String icon_img, String display_name, String description_long, String url) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.description = description;
        this.subscriber = subscriber;
        this.date = date;
        this.icon_img = icon_img;
        this.display_name = display_name;
        this.description_long = description_long;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(long subscriber) {
        this.subscriber = subscriber;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getIcon_img() {
        return icon_img;
    }

    public void setIcon_img(String icon_img) {
        this.icon_img = icon_img;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getDescription_long() {
        return description_long;
    }

    public void setDescription_long(String description_long) {
        this.description_long = description_long;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
