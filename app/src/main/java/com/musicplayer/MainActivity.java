package com.musicplayer;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOAD_SONGS_ID = 0;
    private static final int LOAD_ALBUM_ID = 1;
    private RecyclerView mRecyclerView;
    private SongsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<NewSong> mSongs;
    private Map<String, String> mAlbumUris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SongsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        getLoaderManager().initLoader(LOAD_SONGS_ID, null, this);
        getLoaderManager().initLoader(LOAD_ALBUM_ID, null, this);
        mSongs = new ArrayList<>();
        mAlbumUris = new HashMap<>();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        switch (id) {
            case LOAD_SONGS_ID:
                Uri musicUri = Audio.Media.EXTERNAL_CONTENT_URI;
                String[] projection = new String[]{Audio.Media.ALBUM_ID, Audio.Media.TITLE,
                        Audio.Media.ARTIST, Audio.Media.DURATION};
                return new CursorLoader(this, musicUri, projection, null, null, null);
            case LOAD_ALBUM_ID:
                Uri albumsUri = Audio.Albums.EXTERNAL_CONTENT_URI;
                String[] albumPojection = new String[]{Audio.Albums._ID, Audio.Albums.ALBUM_ART};
                return new CursorLoader(this, albumsUri, albumPojection, null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case LOAD_SONGS_ID:
                if (cursor != null && cursor.moveToFirst()) {
                    int titleColumn = cursor.getColumnIndex(Audio.Media.TITLE);
                    int artistColumn = cursor.getColumnIndex(Audio.Media.ARTIST);
                    int albumColumn = cursor.getColumnIndex(Audio.Media.ALBUM_ID);
                    int duration = cursor.getColumnIndex(Audio.Media.DURATION);
                    do {
                        String mTitleId = cursor.getString(titleColumn);
                        String mAristId = cursor.getString(artistColumn);
                        String mDurationId = getDuration(Integer.parseInt(cursor.getString(duration)));
                        String mAlbumMediaId = cursor.getString(albumColumn);
                        mSongs.add(new NewSong(mAlbumMediaId, mTitleId, mAristId, mDurationId));
                    }
                    while (cursor.moveToNext());
                }
                break;
            case LOAD_ALBUM_ID:
                int albumArt = cursor.getColumnIndex(Audio.Albums.ALBUM_ART);
                int albumArtID = cursor.getColumnIndex(Audio.Albums._ID);
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        String mAlbumArtId = cursor.getString(albumArt);
                        String mAlbumId = cursor.getString(albumArtID);
                        mAlbumUris.put(mAlbumId, mAlbumArtId);
                    }
                    while (cursor.moveToNext());
                }
                break;
        }
        if (!mSongs.isEmpty() && !mAlbumUris.isEmpty()) {
            Iterator<NewSong> iterator = mSongs.iterator();
            while (iterator.hasNext()) {
                NewSong val = iterator.next();
                if (mAlbumUris.containsKey(val.AlbumId)) {
                    mAdapter.add(new Song(val.title, val.desc, val.duration, mAlbumUris.get(val.AlbumId)));
                } else {
                    mAdapter.add(new Song(val.title, val.desc, val.duration, null));
                }
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

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

