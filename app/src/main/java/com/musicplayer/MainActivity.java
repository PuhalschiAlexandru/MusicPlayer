package com.musicplayer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.database.sqlite.SQLiteQuery;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity {
    //        implements android.app.LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOAD_SONGS_ID = 1;
    private RecyclerView mRecyclerView;
    private SongsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        // specify an adapter (see also next example)
        mAdapter = new SongsAdapter();
        getSongList();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
//        getLoaderManager().initLoader(LOAD_SONGS_ID, null, this);
    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//        return null;
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
//
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//
//    }


    public void getSongList() {
        //retrieve song info
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{Audio.Media.ALBUM_ID, Audio.Media.TITLE, Audio.Media.ARTIST,Audio.Media.DURATION};
        Cursor musicCursor = musicResolver.query(musicUri, projection, null, null, null);
        Uri albumsUri = Audio.Albums.EXTERNAL_CONTENT_URI;
        String[] albumPojection = new String[]{Audio.Albums.ALBUM_ART};

        //iterate over results if valid
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex(Audio.Media.TITLE);
            int artistColumn = musicCursor.getColumnIndex(Audio.Media.ARTIST);
            int albumId = musicCursor.getColumnIndex(Audio.Media.ALBUM_ID);
            int duration = musicCursor.getColumnIndex(Audio.Media.DURATION);


            //add songs to list
            do {
                String albumArt = null;
                Cursor albumCursor = musicResolver.query(albumsUri, albumPojection, null, null, null);
//                Audio.Albums.ALBUM_ID +"=?"
                if (albumCursor != null && albumCursor.moveToFirst()) {
                    int albumArtKey = albumCursor.getColumnIndex(Audio.Albums.ALBUM_ART);
                    albumArt = albumCursor.getString(albumArtKey);
//                    if(albumArt == null){
//                        albumArt=
//                    }
                }

                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisDuration = getDuration(Integer.parseInt(musicCursor.getString(duration)));
                mAdapter.add(new Song(thisTitle, thisArtist, thisDuration,albumArt));
                albumCursor.moveToNext();
            }
            while (musicCursor.moveToNext());

        }
        musicCursor.close();
    }

    private static String getDuration(long milis) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milis);
        milis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milis);
        StringBuilder sb = new StringBuilder(6);
        sb.append(minutes < 10 ? "0" + minutes : minutes);
        sb.append(":");
        sb.append(seconds < 10 ? "0" + seconds : seconds);
        return sb.toString();
    }

}

