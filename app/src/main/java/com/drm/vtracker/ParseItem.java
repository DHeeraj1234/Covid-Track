package com.drm.vtracker;

public class ParseItem {

    //private String imgUrl;
    private String title;
    private String detailUrl;
    private String newCases;

    public ParseItem() {
    }

    public ParseItem( String title, String detailUrl) {
        //this.imgUrl = imgUrl;
        this.title = title;
        this.detailUrl = detailUrl;
        this.newCases = newCases;
    }

    //public String getImgUrl() {
    //    return imgUrl;
    //}

    //public void setImgUrl(String imgUrl) {
    //    this.imgUrl = imgUrl;
    //}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }
    public String getNewCases() {
        return newCases;
    }

    public void setNewCases(String newCases) {
        this.newCases = newCases;
    }
}
