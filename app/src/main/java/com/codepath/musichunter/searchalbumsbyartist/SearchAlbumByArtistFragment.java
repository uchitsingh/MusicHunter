package com.codepath.musichunter.searchalbumsbyartist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.codepath.musichunter.MainActivity;
import com.codepath.musichunter.R;
import com.codepath.musichunter.model.data.network.model.searhalbumsbyartist.AlbumsModel;
import com.codepath.musichunter.model.data.network.service.IRequestInterface;
import com.codepath.musichunter.model.data.network.service.ServiceConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchAlbumByArtistFragment extends Fragment {

    private IRequestInterface iRequestInterface;
    private RecyclerView m_rv_AlbumsDetails;
    public SearchAlbumByArtistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_album_by_artist, null);
      //  setRetainInstance(true);
        // Inflate the layout for this fragment
        return view;

        //getChildFragmentManager()

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iRequestInterface = ServiceConnection.getConnection();
        initRecycleView();

        albumViewBySearchArtist();
       //
    }


    public void initRecycleView(){
        m_rv_AlbumsDetails = (RecyclerView) getActivity().findViewById(R.id.rv_AlbumsDetails);
        m_rv_AlbumsDetails.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    public void albumViewBySearchArtist(){
       // MainActivity.getM_Sv_Artist().setQuery(,true);
      //  MainActivity.getM_Sv_Artist().seton
        MainActivity.getM_Sv_Artist().setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
              //  Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();

                iRequestInterface.getAlbums(s)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<AlbumsModel>() {
                            @Override
                            public void accept(AlbumsModel albumsModel) throws Exception {
                             m_rv_AlbumsDetails.setAdapter(new AlbumAdapter(albumsModel));
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                return true;
            }


            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });

    }
}
