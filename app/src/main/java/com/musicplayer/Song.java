package com.musicplayer;

import android.net.Uri;


public class Song {
    String title;
    String desc;
    String duration;
    String image;
    Uri imageUri;


    public Song(String title, String desc, String duration, String image) {
        this.title = title;
        this.desc = desc;
        this.duration = duration;
        this.image = image;
        if (image != null) {
            imageUri = Uri.parse(image);
        }
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

    public String getImage() {
        return image;
    }
}
