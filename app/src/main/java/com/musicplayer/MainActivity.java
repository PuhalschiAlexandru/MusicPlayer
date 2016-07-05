package com.musicplayer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOAD_SONGS_ID = 1;
    private static final int LOAD_ALBUM_ID = 2;
    private RecyclerView mRecyclerView;
    private SongsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] mAlbumSelectionArgs;
    private String mAlbumArtId,mTitleId,mAristId,mDurationId,mAlbumId;

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
        getLoaderManager().initLoader(LOAD_SONGS_ID, null, this);
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
                String[] albumPojection = new String[]{Audio.Albums.ALBUM_ART};
                return new CursorLoader(this, albumsUri, albumPojection, Audio.Albums._ID + "=?",
                        mAlbumSelectionArgs, null);

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
                        mTitleId = cursor.getString(titleColumn);
                        mAristId = cursor.getString(artistColumn);
                        mDurationId = getDuration(Integer.parseInt(cursor.getString(duration)));
                        mAlbumId = cursor.getString(albumColumn);
                        mAlbumSelectionArgs = new String[]{mAlbumId};
                    }
                    while (cursor.moveToNext());

                }
//                cursor.close();
                getLoaderManager().initLoader(LOAD_ALBUM_ID, null, this);
                break;
            case LOAD_ALBUM_ID:
                int albumArt = cursor.getColumnIndex(Audio.Albums.ALBUM_ART);
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        mAlbumArtId = cursor.getString(albumArt);
                    }
                    while (cursor.moveToNext());
                }
                break;
        }
            for(int i=0;i < cursor.getColumnCount();i++){
                mAdapter.add(new Song(mTitleId, mAristId, mDurationId, mAlbumArtId));


            }
//            mAdapter.add(new Song(titleId, artistId, durationId, albumArtId));




    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
//

    public void getSongList() {
        //retrieve song info
        //ContentResolver musicResolver = getContentResolver();


        //iterate over results if valid

        //add songs to list

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

