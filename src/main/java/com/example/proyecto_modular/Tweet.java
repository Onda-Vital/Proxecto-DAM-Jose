package com.example.proyecto_modular;

public class Tweet {
    private String id;
    private String userName;
    private String handel;
    private String text;
    private int avatarResId;
    private  Integer imageResId;
    private int likes;
    private int respost;

    public Tweet(String id, String userName, String handel, String text, int avatarResId, Integer imageResId, int likes, int respost){
        this.id = id;
        this.userName = userName;
        this.handel = handel;
        this.text = text;
        this.avatarResId = avatarResId;
        this.imageResId = imageResId;
        this.likes = likes;
        this.respost = respost;

    }
    public String getUserName(){return  userName;}
    public String getHandel(){return handel;}
    public String getText(){return text;}
    public int getAvatarResId(){return avatarResId;}
    public Integer getImageResId(){return  imageResId;}
    public int getLikes(){return likes;}
    public int getRespost(){return respost;}
}
