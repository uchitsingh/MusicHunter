package com.codepath.musichunter.searchtoptenlovedtracksbyArtist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.codepath.musichunter.MainActivity;
import com.codepath.musichunter.R;
import com.codepath.musichunter.model.data.network.model.searchtoptenlovedtracksbyArtist.TopTenLovedTracksByArtistModel;
import com.codepath.musichunter.model.data.network.model.searhalbumsbyartist.AlbumsModel;
import com.codepath.musichunter.model.data.network.service.IRequestInterface;
import com.codepath.musichunter.model.data.network.service.ServiceConnection;
import com.codepath.musichunter.searchalbumsbyartist.AlbumAdapter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTopTenLovedTracksByArtist extends Fragment {

    private IRequestInterface iRequestInterface;
    private RecyclerView m_rv_Top_Ten_Loved_Tracks_;
    public SearchTopTenLovedTracksByArtist() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_top_ten_loved_tracks_by_artist, null);
     //   setRetainInstance(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iRequestInterface = ServiceConnection.getConnection();
        initRecycleView();
         topTenLovedTracksViewByArtist();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
     //   Log.i("onSaveInstanceStateSArt", "onSaveInstanceState_SearchArti");
        CharSequence searchView = MainActivity.getM_Sv_Artist().getQuery();
        outState.putCharSequence("savedSearchViewQuery", searchView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
        //    Log.i("onSaveInstanceStateSArt", "onSaveInstanceState_SearchArti");
            CharSequence searchViewQuery = (CharSequence) savedInstanceState.get("savedSearchViewQuery");
            MainActivity.getM_Sv_Artist().setQuery(searchViewQuery, true);
        }
    }



    private void initRecycleView() {
        m_rv_Top_Ten_Loved_Tracks_ = (RecyclerView) getActivity().findViewById(R.id.rv_Top_Ten_Loved_Tracks);
        m_rv_Top_Ten_Loved_Tracks_.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void topTenLovedTracksViewByArtist() {

            MainActivity.getM_Sv_Artist().setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    iRequestInterface.getTopTenLovedTracks(s)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<TopTenLovedTracksByArtistModel>() {
                                @Override
                                public void accept(TopTenLovedTracksByArtistModel topTenLovedTracksByArtistModel) throws Exception {
                                    //    Toast.makeText(getContext(), topTenLovedTracksByArtistModel.getTrack().get(0).getStrTrack(), Toast.LENGTH_SHORT).show();

                                      if (topTenLovedTracksByArtistModel != null && topTenLovedTracksByArtistModel.getTrack().size() > 0) {
                                          m_rv_Top_Ten_Loved_Tracks_.setAdapter(new TopTenLovedTracksAdapter(topTenLovedTracksByArtistModel));
                                      }

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
