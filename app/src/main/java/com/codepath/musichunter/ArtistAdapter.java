package com.codepath.musichunter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.musichunter.model.data.network.model.ArtistModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by uchit on 04/03/2018.
 */

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private ArtistModel artistModel;


    public ArtistAdapter(ArtistModel artistModel) {
        this.artistModel = artistModel;

    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArtistViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        holder.m_ArtistName.setText(artistModel.getArtists().get(position).getStrArtist());
        holder.m_ArtistBiography.setText(artistModel.getArtists().get(position).getStrBiographyEN());
    }

    @Override
    public int getItemCount() {
        if (artistModel != null) {
            return artistModel.getArtists().size();
       }
        return 0;
    }

/*    public void addArtist(ArtistModel artistModel){
      //  mCompanyResponses.clear();
        this.artistModel = artistModel;
        notifyDataSetChanged();
        //this.artistModel = artistModel;
    }*/

    class ArtistViewHolder extends RecyclerView.ViewHolder {

        // @BindView(R.id.tv_ArtistName)    TextView m_ArtistName;

        private TextView m_ArtistName, m_ArtistBiography;

        public ArtistViewHolder(View itemView) {
            super(itemView);
            m_ArtistName = (TextView) itemView.findViewById(R.id.tv_ArtistName);
            m_ArtistBiography = (TextView) itemView.findViewById(R.id.tv_ArtistBiography);
            //ButterKnife.bind(this, itemView);
        }
    }
}
