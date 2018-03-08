package com.codepath.musichunter.searchtoptenlovedtracksbyArtist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.musichunter.R;
import com.codepath.musichunter.model.data.network.model.searchtoptenlovedtracksbyArtist.TopTenLovedTracksByArtistModel;

import java.util.concurrent.TimeUnit;

/**
 * Created by uchit on 08/03/2018.
 */

class TopTenLovedTracksAdapter extends RecyclerView.Adapter<TopTenLovedTracksAdapter.TopTenLovedTracksViewHolder> {

    private TopTenLovedTracksByArtistModel topTenLovedTracksByArtistModel;

    public TopTenLovedTracksAdapter(TopTenLovedTracksByArtistModel topTenLovedTracksByArtistModel) {
        this.topTenLovedTracksByArtistModel = topTenLovedTracksByArtistModel;
    }

    @Override
    public TopTenLovedTracksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TopTenLovedTracksViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_top_ten_loved,parent, false));
    }

    @Override
    public void onBindViewHolder(TopTenLovedTracksViewHolder holder, int position) {
        holder.m_TrackName.setText(topTenLovedTracksByArtistModel.getTrack().get(position).getStrTrack());
      //  int min = convertMillisecondsToMinute(Integer.parseInt(topTenLovedTracksByArtistModel.getTrack().get(position).getIntDuration()));
       long minutes = TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(topTenLovedTracksByArtistModel.getTrack().get(position).getIntDuration())) % 60;
       long seconds = TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(topTenLovedTracksByArtistModel.getTrack().get(position).getIntDuration())) % 60;
        //String.format("%d Minutes %d Seconds", minutes, seconds);
       holder.m_TrackDescription.setText(String.format("%dm %ds" , minutes, seconds));
       holder.m_TrackScore.setText(String.valueOf(topTenLovedTracksByArtistModel.getTrack().get(position).getIntScore()));
    }

    @Override
    public int getItemCount() {
            return topTenLovedTracksByArtistModel.getTrack().size();
    }



    public class TopTenLovedTracksViewHolder extends RecyclerView.ViewHolder {
        private TextView m_TrackName, m_TrackDescription, m_TrackScore;
        public TopTenLovedTracksViewHolder(View itemView) {
            super(itemView);
            m_TrackName =(TextView) itemView.findViewById(R.id.tv_TopTen_TrackName);
            m_TrackDescription = (TextView) itemView.findViewById(R.id.tv_TopTen_TrackDuration);
            m_TrackScore = (TextView) itemView.findViewById(R.id.tv_TopTen_TrackScore);


        }
    }




}
