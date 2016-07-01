package com.musicplayer;

import java.util.ArrayList;
import java.util.List;


public class Song {
    String title;
    String desc;
    int image;


    public Song(String title, String desc, int image) {
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public int getImage() {
        return image;
    }
}
