package com.musicplayer;

import java.util.ArrayList;
import java.util.List;


public class Song {
    String title;
    String desc;
    String duration;
    int image;


    public Song(String title, String desc,String duration, int image) {
        this.title = title;
        this.desc = desc;
        this.duration = duration;
        this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
    public String getDuration() {
        return duration;
    }

    public int getImage() {
        return image;
    }
}
