package com.codepath.musichunter.searchtoptenlovedtracksbyArtist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.codepath.musichunter.model.data.network.AppDataManager;
import com.codepath.musichunter.model.data.network.model.searchtoptenlovedtracksbyArtist.TopTenLovedTracksByArtistModel;

import com.codepath.musichunter.model.data.network.service.ServiceConnection;
import com.codepath.musichunter.ui.base.BaseFragment;
import com.codepath.musichunter.ui.utils.rx.AppSchedulerProvider;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.jakewharton.rxbinding2.widget.RxSearchView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTopTenLovedTracksByArtist extends BaseFragment implements ISearchTopTenLovedTracksByArtistMvpView {

    @BindView(R.id.rv_Top_Ten_Loved_Tracks) RecyclerView m_rv_Top_Ten_Loved_Tracks;
    @BindView(R.id.swiperefresh)  SwipeRefreshLayout refreshLayout;
    private SearchTopTenLovedTracksByArtistMvpPresenterIml<SearchTopTenLovedTracksByArtist> searchTopTenLovedTracksByArtistMvpPresenterIml;

    public SearchTopTenLovedTracksByArtist() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_top_ten_loved_tracks_by_artist, null);
     //   setRetainInstance(true);

        searchTopTenLovedTracksByArtistMvpPresenterIml = new SearchTopTenLovedTracksByArtistMvpPresenterIml<>(new AppDataManager(), new AppSchedulerProvider(), new CompositeDisposable());
        searchTopTenLovedTracksByArtistMvpPresenterIml.onAttach(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
 //       iRequestInterface = ServiceConnection.getConnection();
        initRecycleView();
        refreshLayout.setEnabled(false);

        /*CharSequence searchView = MainActivity.getM_Sv_Artist().getQuery();
        MainActivity.getM_Sv_Artist().setQuery(searchView, true);*/
  //     topTenLovedTracksViewByArtist();

       // rxTopTenViewBySearch();
    }



    private void initRecycleView() {
      //  m_rv_Top_Ten_Loved_Tracks = (RecyclerView) getActivity().findViewById(R.id.rv_Top_Ten_Loved_Tracks);
        m_rv_Top_Ten_Loved_Tracks.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void topTenLovedTracksViewByArtist() {
        Log.i("1010", "1010");

            MainActivity.getM_Sv_Artist().setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    refreshLayout.setEnabled(true);
                    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            searchTopTenLovedTracksByArtistMvpPresenterIml.loadTopTenByArtistInformation(s);

                        }
                    });

                    searchTopTenLovedTracksByArtistMvpPresenterIml.loadTopTenByArtistInformation(s);

                    return true;

                }


                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
    private void callTopTenLovedTracksViewByArtist() {
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isConnectedToInternet) throws Exception {
                        if (isConnectedToInternet) {

                            Toast.makeText(getContext(), "Connected", Toast.LENGTH_SHORT).show();
                            //showMessage("Connection");
                            //onError("Connection");
                            topTenLovedTracksViewByArtist();
                        } else {
                            Toast.makeText(getContext(), "No Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser)
        {
            callTopTenLovedTracksViewByArtist();

       //    topTenLovedTracksViewByArtist();
          //  rxTopTenViewBySearch();
        }
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

    @Override
    public void onFetchDataProgress() {
        showLoading();
    }

    @Override
    public void onFetchDataSuccess(TopTenLovedTracksByArtistModel topTenLovedTracksByArtistModel) {
        refreshLayout.setRefreshing(false);
        m_rv_Top_Ten_Loved_Tracks.setAdapter(new TopTenLovedTracksAdapter(topTenLovedTracksByArtistModel));
        hideLoading();
    }

    @Override
    public void onFetchDataError(String error) {
        refreshLayout.setRefreshing(false);
        showMessage(error);
        hideLoading();

    }





/*    public void rxTopTenViewBySearch(){
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
    }*/
}
