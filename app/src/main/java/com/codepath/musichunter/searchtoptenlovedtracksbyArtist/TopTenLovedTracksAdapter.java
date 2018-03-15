package com.codepath.musichunter.searchtoptenlovedtracksbyArtist;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.musichunter.ItemClickListener;
import com.codepath.musichunter.MyApp;
import com.codepath.musichunter.R;
import com.codepath.musichunter.model.data.network.model.searchtoptenlovedtracksbyArtist.TopTenLovedTracksByArtistModel;

import java.text.DecimalFormat;
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
       String duration = String.format("%d: %d" , minutes, (seconds));
       holder.m_TrackDescription.setText(duration);
       /*
       Todo: Change 10/10 into 10 stars
        */

       String score = (String) topTenLovedTracksByArtistModel.getTrack().get(position).getIntScore();
       // String num = score.substring(0, score.indexOf("."));
       holder.m_TrackScore.setText( score + "/10");
       holder.callItemClick(new ItemClickListener() {
           @Override
           public void onClick(View view, int position) {
               try{
                   String videoLink = topTenLovedTracksByArtistModel.getTrack().get(position).getStrMusicVid().toString();
                   if(videoLink != null && !videoLink.isEmpty()){
                       Intent videoClient = new Intent(Intent.ACTION_VIEW);
                       videoClient.setData(Uri.parse(videoLink));
                       MyApp.getInstance().getAppContext().startActivity(videoClient);
                   }else{
                       Toast.makeText( MyApp.getInstance().getAppContext(),topTenLovedTracksByArtistModel.getTrack().get(position).getStrTrack() + "\nVideo Not Found" , Toast.LENGTH_SHORT).show();
                   }
               }catch(Exception ex){

               }


           }
       });

   /*
        Intent videoClient = new Intent(Intent.ACTION_VIEW);
        videoClient.setData(Uri.parse("http://m.youtube.com/watch?v="+videoId));
        startActivityForResult(videoClient, 1234);*/

    }

    @Override
    public int getItemCount() {
            return topTenLovedTracksByArtistModel.getTrack().size();
    }



    public class TopTenLovedTracksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView m_TrackName, m_TrackDescription, m_TrackScore;
        private ItemClickListener itemClickListener;

        public TopTenLovedTracksViewHolder(View itemView) {
            super(itemView);
            m_TrackName =(TextView) itemView.findViewById(R.id.tv_TopTen_TrackName);
            m_TrackDescription = (TextView) itemView.findViewById(R.id.tv_TopTen_TrackDuration);
            m_TrackScore = (TextView) itemView.findViewById(R.id.tv_TopTen_TrackScore);
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
