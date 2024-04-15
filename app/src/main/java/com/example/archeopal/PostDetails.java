package com.example.archeopal;


public class PostDetails {
    public String name, title, article, state, district, uri;

    public PostDetails() {

    }

    PostDetails(String name, String title, String article, String uri, String state, String district) {
        this.name = name;
        this.title = title;
        this.article = article;
        this.uri = uri;
        this.state = state;
        this.district = district;
    }
}
