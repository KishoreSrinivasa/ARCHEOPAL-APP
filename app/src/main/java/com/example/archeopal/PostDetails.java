package com.example.archeopal;


public class PostDetails {
    public String title, article, state, district, uri;

    PostDetails(String title, String article, String uri, String state, String district) {
        this.title = title;
        this.article = article;
        this.uri = uri;
        this.state = state;
        this.district = district;
    }
}
