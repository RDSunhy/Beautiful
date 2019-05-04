package com.shy.beautiful.bean;

/**
 * Created by Shy on 2019/3/30.
 */

public class CollectionBean {
    public String url;
    public String title;

    @Override
    public String toString() {
        return "CollectionBean{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
