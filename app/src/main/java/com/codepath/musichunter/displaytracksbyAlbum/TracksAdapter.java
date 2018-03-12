package com.codepath.musichunter.displaytracksbyAlbum;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.musichunter.R;
import com.codepath.musichunter.model.data.network.model.displaytracksbyalbum.TracksModel;

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
        return new TracksViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tracks,parent,false));
    }

    @Override
    public void onBindViewHolder(TracksViewHolder holder, int position) {

        holder.m_Albums_TrackName.setText(tracksModel.getTrack().get(position).getStrTrack());
        holder.m_Albums_TrackNumber.setText(tracksModel.getTrack().get(position).getIntTrackNumber());


        //String desc = tracksModel.getTrack().get(position).getStrDescriptionEN().toString();
      /*  if (tracksModel.getTrack().get(position).getStrDescriptionEN().toString() != null && tracksModel.getTrack().get(position).getStrDescriptionEN().toString().length()>0 ){
            String desc = tracksModel.getTrack().get(position).getStrDescriptionEN().toString();
            holder.m_Albums_AlbumDescription.setText(desc);
        }

*/



    }

    @Override
    public int getItemCount() {
        return tracksModel.getTrack().size();
    }

    public class TracksViewHolder extends RecyclerView.ViewHolder {
        private TextView m_Albums_AlbumDescription, m_Albums_TrackName, m_Albums_TrackNumber;
        public TracksViewHolder(View itemView) {
            super(itemView);
            m_Albums_TrackNumber = itemView.findViewById(R.id.tv_Albums_TrackNumber);
            m_Albums_TrackName = itemView.findViewById(R.id.tv_Albums_TrackName);

        }
    }
}
