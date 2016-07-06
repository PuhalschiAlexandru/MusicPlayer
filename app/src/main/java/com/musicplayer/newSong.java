package com.musicplayer;

public class NewSong {
    String AlbumId;
    String title;
    String desc;
    String duration;
    String songData;


    public NewSong(String AlbumId, String title, String desc, String duration,String songData) {
        this.title = title;
        this.desc = desc;
        this.duration = duration;
        this.AlbumId = AlbumId;
        this.songData = songData;

        }
    }
