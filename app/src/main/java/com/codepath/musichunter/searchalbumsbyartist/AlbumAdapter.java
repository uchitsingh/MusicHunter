package com.codepath.musichunter.searchalbumsbyartist;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.musichunter.MyApp;
import com.codepath.musichunter.R;
import com.codepath.musichunter.displaytracksbyAlbum.DisplayTracksByAlbumActivity;
import com.codepath.musichunter.model.data.network.model.searhalbumsbyartist.AlbumsModel;
import com.codepath.musichunter.searchbyartist.ItemClickListener;

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
        return new AlbumViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.album_row, parent, false));
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        holder.m_AlbumName.setText(albumsModel.getAlbum().get(position).getStrAlbum());
        holder.m_AlbumDescription.setText(albumsModel.getAlbum().get(position).getStrDescriptionEN());

        holder.callItemClick(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
              //  Toast.makeText(MyApp.getInstance().getAppContext(), albumsModel.getAlbum().get(position).getIntYearReleased(),Toast.LENGTH_SHORT).show();
            //    iRequestInterface

             //   MainActivity.dispayTracksByAlbum(Integer.parseInt(albumsModel.getAlbum().get(position).getIdAlbum()));
              //  FragmentManager fragmentManager =;
                /*Fragment pager= MainActivity.fragmentManager.findFragmentById(R.id.pager);


                FragmentTransaction transaction =pager.getChildFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id, displayTracksByAlbumFragment).commit();
            */
                Intent intent = new Intent(MyApp.getInstance().getAppContext(), DisplayTracksByAlbumActivity.class);
                MyApp.getInstance().getAppContext().startActivity(intent);

                //String message = editText.getText().toString();
                //intent.putExtra(EXTRA_MESSAGE, message);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (albumsModel != null) {
            return albumsModel.getAlbum().size();
        }
        return 0;
    }


    public class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView m_AlbumName, m_AlbumDescription;
        private ItemClickListener itemClickListener;
        public AlbumViewHolder(View itemView) {
            super(itemView);
        //    this.m_AlbumName = m_AlbumName;
            m_AlbumName = (TextView) itemView.findViewById(R.id.tv_AlbumName);
            m_AlbumDescription = (TextView) itemView.findViewById(R.id.tv_AlbumDescription);
            itemView.setOnClickListener(this); //sets the view.onclicklistner
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
