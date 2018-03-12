package com.codepath.musichunter.displaytracksbyAlbum;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.musichunter.MainActivity;
import com.codepath.musichunter.R;
import com.codepath.musichunter.model.data.network.model.displaytracksbyalbum.TracksModel;
import com.codepath.musichunter.ItemClickListener;

/**
 * Created by uchit on 08/03/2018.
 */

class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.TracksViewHolder> {

    private TracksModel tracksModel;


    public TracksAdapter(TracksModel tracksModel) {
        this.tracksModel = tracksModel;

    }

    @Override
    public TracksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TracksViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tracks, parent, false));
    }

    @Override
    public void onBindViewHolder(TracksViewHolder holder, int position) {

        holder.m_Albums_TrackName.setText(tracksModel.getTrack().get(position).getStrTrack());
        holder.m_Albums_TrackNumber.setText(tracksModel.getTrack().get(position).getIntTrackNumber());
        holder.callItemClick(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                String artist_Name  = tracksModel.getTrack().get(position).getStrArtist();
                String song_title = tracksModel.getTrack().get(position).getStrTrack();

                DisplayTracksByAlbumActivity.displayLyrics(artist_Name, song_title);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tracksModel.getTrack().size();
    }

    public class TracksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView m_Albums_TrackName, m_Albums_TrackNumber;
        private ItemClickListener itemClickListener;

        public TracksViewHolder(View itemView) {
            super(itemView);
            m_Albums_TrackNumber = itemView.findViewById(R.id.tv_Albums_TrackNumber);
            m_Albums_TrackName = itemView.findViewById(R.id.tv_Albums_TrackName);
            itemView.setOnClickListener(this);

        }

        public void callItemClick(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getPosition());
        }
    }
}
