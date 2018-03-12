package com.codepath.musichunter.searchalbumsbyartist;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.musichunter.MyApp;
import com.codepath.musichunter.R;
import com.codepath.musichunter.displaytracksbyAlbum.DisplayTracksByAlbumActivity;
import com.codepath.musichunter.model.data.network.model.searhalbumsbyartist.AlbumsModel;
import com.codepath.musichunter.ItemClickListener;
import com.squareup.picasso.Picasso;

/**
 * Created by uchit on 07/03/2018.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private AlbumsModel albumsModel;
    //  private IRequestInterface iRequestInterface;

    public AlbumAdapter(AlbumsModel albumsModel) {
        this.albumsModel = albumsModel;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlbumViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_album, parent, false));
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        //    holder.m_AlbumName.setText(albumsModel.getAlbum().get(position).getStrAlbum());
        //  holder.m_AlbumDescription.setText(albumsModel.getAlbum().get(position).getStrDescriptionEN());

        //iv_albumArt
        String albumArt = albumsModel.getAlbum().get(position).getStrAlbumThumb();
        if (albumArt != null && !albumArt.isEmpty()) {
            Picasso.with(MyApp.getInstance().getAppContext()).load(albumArt)
                    .resize(500, 500)
                    .centerCrop()
                    .into(holder.m_iv_albumArt);

        } else {
            Picasso.with(MyApp.getInstance().getAppContext()).load("http://saveabandonedbabies.org/wp-content/uploads/2015/08/default.png")
                    .resize(500, 500)
                    .centerCrop()
                    .into(holder.m_iv_albumArt);

            holder.m_AlbumName.setVisibility(View.VISIBLE);
            holder.m_AlbumName.setText(albumsModel.getAlbum().get(position).getStrAlbum());
        }


        holder.callItemClick(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                String albumId = albumsModel.getAlbum().get(position).getIdAlbum();
                String albumDescription = albumsModel.getAlbum().get(position).getStrDescriptionEN();

                String albumartThumb = albumsModel.getAlbum().get(position).getStrAlbumThumbBack();
                Intent intent = new Intent(MyApp.getInstance().getAppContext(), DisplayTracksByAlbumActivity.class);
                if (albumartThumb != null && !albumArt.isEmpty()) {
                    intent.putExtra("albumartThumb", albumartThumb);
                } else {

                }
                intent.putExtra("albumId", albumId);
                intent.putExtra("albumDescription", albumDescription);
                MyApp.getInstance().getAppContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumsModel.getAlbum().size();
    }


    public class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView m_AlbumName, m_AlbumDescription;
        private ItemClickListener itemClickListener;
        private ImageView m_iv_albumArt;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            //    this.m_AlbumName = m_AlbumName;
            m_AlbumName = (TextView) itemView.findViewById(R.id.tv_AlbumName);
/*            m_AlbumDescription = (TextView) itemView.findViewById(R.id.tv_AlbumDescription);*/
            m_iv_albumArt = (ImageView) itemView.findViewById(R.id.iv_albumArt);
            itemView.setOnClickListener(this); //sets the view.onclicklistner
        }

        public void callItemClick(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getPosition());

        }
    }
}
