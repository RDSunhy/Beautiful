package com.shy.beautiful.bean;

/**
 * Created by Shy on 2019/3/30.
 */

public class ImagesBean {


    /**
     * https://uploadbeta.com/_s/
     */

    private String id;
    private String url;
    private String flag;
    private String created;
    private String count;
    private String search;
    private String last;
    private String title;
    private String width;
    private String height;
    private String size;
    private String filename;
    private String comment;
    private String ip;
    private String checksum;
    private String picgroup;

    @Override
    public String toString() {
        return "ImagesBean{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", flag='" + flag + '\'' +
                ", created='" + created + '\'' +
                ", count='" + count + '\'' +
                ", search='" + search + '\'' +
                ", last='" + last + '\'' +
                ", title='" + title + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", size='" + size + '\'' +
                ", filename='" + filename + '\'' +
                ", comment='" + comment + '\'' +
                ", ip='" + ip + '\'' +
                ", checksum='" + checksum + '\'' +
                ", picgroup='" + picgroup + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getPicgroup() {
        return picgroup;
    }

    public void setPicgroup(String picgroup) {
        this.picgroup = picgroup;
    }
}
