package com.musicplayer;

public class NewSong {
    String AlbumId;
    String title;
    String desc;
    String duration;


    public NewSong(String AlbumId, String title, String desc, String duration) {
        this.title = title;
        this.desc = desc;
        this.duration = duration;
        this.AlbumId = AlbumId;

        }
    }
