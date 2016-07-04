package com.musicplayer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {
    private ArrayList<Song> mSongs;

    // Provide a suitable constructor (depends on the kind of dataset)
    public SongsAdapter() {
        mSongs = new ArrayList<>();
    }

    public boolean add(Song song) {
        return mSongs.add(song);
    }

    public boolean addAll(Collection<? extends Song> c) {
        return mSongs.addAll(c);
    }

    public void clear() {
        mSongs.clear();
    }

    public Song remove(int index) {
        return mSongs.remove(index);
    }

    public SongsAdapter(ArrayList<Song> songs) {
        this.mSongs = songs;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SongsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        holder.title.setText(mSongs.get(position).getTitle());
        holder.description.setText(mSongs.get(position).getDesc());
        holder.image.setImageResource(mSongs.get(position).getImage());
        holder.duration.setText(mSongs.get(position).getDuration());


    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;

        TextView description;
        TextView title;
        ImageView image;
        TextView duration;


        public ViewHolder(View v) {
            super(v);
            mView = v;
            description = (TextView) v.findViewById(R.id.desc);
            title = (TextView) v.findViewById(R.id.title);
            image = (ImageView) v.findViewById(R.id.image);
            duration = (TextView) v.findViewById(R.id.duration);
        }
    }
}