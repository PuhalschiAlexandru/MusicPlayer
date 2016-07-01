package com.musicplayer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends Activity {
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
//        mRecyclerView.setAdapter(mAdapter);
        for(int i=0;i<20;i++){
            mAdapter.add(new Song("Songs"+i, "Music", R.drawable.song));
        }
        mAdapter.notifyDataSetChanged();
        mAdapter.notifyItemInserted(0);
        mAdapter.remove(0);
        mAdapter.notifyItemRemoved(0);
        mRecyclerView.setAdapter(mAdapter);
    }

    }

