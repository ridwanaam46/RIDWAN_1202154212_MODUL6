package com.example.asusx453sa.ridwan_1202154212_modul6;

public class Post {
    String id;
    private String userId;
    private String usernamePost;
    private String titlePost;
    private String descPost;
    private String imagePost;

    public Post() {
    }

    public Post(String id, String userId, String usernamePost, String titlePost, String descPost, String imagePost) {
        this.id = id;
        this.userId = userId;
        this.usernamePost = usernamePost;
        this.titlePost = titlePost;
        this.descPost = descPost;
        this.imagePost = imagePost;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsernamePost() {
        return usernamePost;
    }

    public String getTitlePost() {
        return titlePost;
    }

    public String getDescPost() {
        return descPost;
    }

    public String getImagePost() {
        return imagePost;
    }
}
