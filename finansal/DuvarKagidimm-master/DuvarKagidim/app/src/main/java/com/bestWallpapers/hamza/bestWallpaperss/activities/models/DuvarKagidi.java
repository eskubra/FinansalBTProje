package com.bestWallpapers.hamza.bestWallpaperss.activities.models;

import com.google.firebase.database.Exclude;

public class DuvarKagidi {
    @Exclude
    public String id;
    public String title, desc, url;
    @Exclude
    public String category;
    @Exclude
    public boolean isFavori = false;

    public DuvarKagidi(String id, String title, String desc, String url, String category) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.url = url;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
