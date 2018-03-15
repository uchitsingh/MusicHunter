package com.codepath.musichunter.searchtoptenlovedtracksbyArtist;


import android.content.Context;
import android.content.SharedPreferences;
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
import com.codepath.musichunter.searchalbumsbyartist.AlbumAdapter;
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
 * A simple {@link Fragment} subclass. Fragment used to search for Top 10 track by Artist.
 */
public class SearchTopTenLovedTracksByArtist extends BaseFragment implements ISearchTopTenLovedTracksByArtistMvpView {

    @BindView(R.id.rv_Top_Ten_Loved_Tracks)
    RecyclerView m_rv_Top_Ten_Loved_Tracks;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout refreshLayout;
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
        // callTopTenLovedTracksViewByArtist();
        searchTopTenLovedTracksByArtistMvpPresenterIml = new SearchTopTenLovedTracksByArtistMvpPresenterIml<>(new AppDataManager(), new AppSchedulerProvider(), new CompositeDisposable());
        searchTopTenLovedTracksByArtistMvpPresenterIml.onAttach(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycleView();

    /*    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchTopTenLovedTracksByArtistMvpPresenterIml.loadTopTenByArtistInformation(getString(R.string.default_artist));
            }
        });
        searchTopTenLovedTracksByArtistMvpPresenterIml.loadTopTenByArtistInformation(getString(R.string.default_artist));

*/

     /*   refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callTopTenLovedTracksViewByArtist();
            }
        });

        callTopTenLovedTracksViewByArtist();*/
    }
    /**
     * Method used to check that the user is on SearchTopTenLovedTracksByArtist Fragment instance before making call to load data from API.
     * Without this, resources were not being properly allocated, across viewpager and Fragments.
     * @param isVisibleToUser Ensures that the correct fragment instance is used before loading data from API
     *
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
              callTopTenLovedTracksViewByArtist();

        }
    }

    /**
     * Method to initialize our recyclerView.
     */
    private void initRecycleView() {
        //  m_rv_Top_Ten_Loved_Tracks = (RecyclerView) getActivity().findViewById(R.id.rv_Top_Ten_Loved_Tracks);
        m_rv_Top_Ten_Loved_Tracks.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    /**
     * Method is used to check if there is an internet connection using RxReactiveNetwork, which allows it to check for the Network State on a
     * seperate background thread. If there is an internet connection, display TopTenLovedTracks details.
     */
    private void callTopTenLovedTracksViewByArtist() {
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isConnectedToInternet) throws Exception {
                        if (isConnectedToInternet) {
                            topTenLovedTracksViewByArtist();
                        } else {
                            //  Toast.makeText(getContext(), "No Connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    /**
     * This method makes the main call to Album Api.
     * This method gets the SharedPreference which is saved when a query to static SearchView is updated, and loads the album detail information according to the value(artist_Name) contained in sharedPreferencce.
     * We also register  the sharedPreferences with OnSharedPreferenceChangeListener, so whenever the preference value is changed, the topTenLovedTracks details are updated
     * accordingly.
     */
    private void topTenLovedTracksViewByArtist() {
        Log.i("1010", "1010");


        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String  searchValue = sharedPreferences.getString(MainActivity.searchView_Name, getString(R.string.default_artist));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchTopTenLovedTracksByArtistMvpPresenterIml.loadTopTenByArtistInformation(searchValue);
            }});
        searchTopTenLovedTracksByArtistMvpPresenterIml.loadTopTenByArtistInformation(searchValue);


        SharedPreferences.OnSharedPreferenceChangeListener listener  =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                        // listener implementation
                        if (key.equals(MainActivity.searchView_Name)){
                            String searchValue = prefs.getString(key, MainActivity.getM_Sv_Artist().getQuery().toString());
                            try {
                                refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                    @Override
                                    public void onRefresh() {
                                        searchTopTenLovedTracksByArtistMvpPresenterIml.loadTopTenByArtistInformation(searchValue);
                                    }});
                                searchTopTenLovedTracksByArtistMvpPresenterIml.loadTopTenByArtistInformation(searchValue);
                            }catch(Exception ex){
                                Log.i("errorONsharedPreference", ex.getMessage());
                            }


                        }
                    }
                };

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);

     /*   MainActivity.getM_Sv_Artist().setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //   refreshLayout.setEnabled(true);
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
        });*/
    }





    @Override
    public void onFetchDataProgress() {
        showLoading();
    }


    /**
     * if the data was successfully fetched, setup the recycler view with the adapter.
     * @param topTenLovedTracksByArtistModel the topTenLovedTracksByArtistModel returned from API is passed to the custom adapter
     */
    @Override
    public void onFetchDataSuccess(TopTenLovedTracksByArtistModel topTenLovedTracksByArtistModel) {

        if ( topTenLovedTracksByArtistModel.getTrack().size() > 0) {
                refreshLayout.setRefreshing(false);
                m_rv_Top_Ten_Loved_Tracks.setAdapter(new TopTenLovedTracksAdapter(topTenLovedTracksByArtistModel));
                hideLoading();
        }
    }

    @Override
    public void onFetchDataError(String error) {
        refreshLayout.setRefreshing(false);
        showMessage(error);
        hideLoading();

    }


/*    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        CharSequence searchView = MainActivity.getM_Sv_Artist().getQuery();
        outState.putCharSequence("savedSearchViewQuery", searchView);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            //    Log.i("onSaveInstanceStateSArt", "onSaveInstanceState_SearchArti");
            CharSequence searchViewQuery = (CharSequence) savedInstanceState.get("savedSearchViewQuery");
            MainActivity.getM_Sv_Artist().setQuery(searchViewQuery, true);
        }
    }*/


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
