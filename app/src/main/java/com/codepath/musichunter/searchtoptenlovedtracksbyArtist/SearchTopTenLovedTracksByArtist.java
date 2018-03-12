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

import com.codepath.musichunter.model.data.network.service.IRequestInterface;
import com.codepath.musichunter.model.data.network.service.ServiceConnection;
import com.codepath.musichunter.searchalbumsbyartist.AlbumAdapter;
import com.jakewharton.rxbinding2.widget.RxSearchView;

import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTopTenLovedTracksByArtist extends Fragment {

    private IRequestInterface iRequestInterface;
    private RecyclerView m_rv_Top_Ten_Loved_Tracks;
    private SearchView m_Sv_Artist;
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

        /*CharSequence searchView = MainActivity.getM_Sv_Artist().getQuery();
        MainActivity.getM_Sv_Artist().setQuery(searchView, true);*/
  //     topTenLovedTracksViewByArtist();

       // rxTopTenViewBySearch();
    }



    private void initRecycleView() {
        m_rv_Top_Ten_Loved_Tracks = (RecyclerView) getActivity().findViewById(R.id.rv_Top_Ten_Loved_Tracks);
        m_rv_Top_Ten_Loved_Tracks.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void topTenLovedTracksViewByArtist() {
        Log.i("1010", "1010");



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
                                        m_rv_Top_Ten_Loved_Tracks.setAdapter(new TopTenLovedTracksAdapter(topTenLovedTracksByArtistModel));
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
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser)
        {
           topTenLovedTracksViewByArtist();
          //  rxTopTenViewBySearch();
        }
    }



    public void rxTopTenViewBySearch(){
        m_Sv_Artist = MainActivity.getM_Sv_Artist();
        RxSearchView.queryTextChanges(m_Sv_Artist)
                .debounce(350, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {
                        return !charSequence.toString().isEmpty();
                    }
                })
                //   .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .switchMap(new Function<CharSequence, ObservableSource<TopTenLovedTracksByArtistModel>>() {
                    @Override
                    public ObservableSource<TopTenLovedTracksByArtistModel> apply(CharSequence query) throws Exception {
                        return iRequestInterface.getTopTenLovedTracks(query.toString());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TopTenLovedTracksByArtistModel>() {
                               @Override
                               public void accept(TopTenLovedTracksByArtistModel topTenLovedTracksByArtistModel) throws Exception {

                                   if (topTenLovedTracksByArtistModel != null && topTenLovedTracksByArtistModel.getTrack().size() > 0) {
                                       m_rv_Top_Ten_Loved_Tracks.setAdapter(new TopTenLovedTracksAdapter(topTenLovedTracksByArtistModel));
                                   }

                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

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

}
