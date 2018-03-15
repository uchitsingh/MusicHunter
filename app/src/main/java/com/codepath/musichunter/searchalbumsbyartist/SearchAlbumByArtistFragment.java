package com.codepath.musichunter.searchalbumsbyartist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import com.codepath.musichunter.model.data.network.model.searhalbumsbyartist.AlbumsModel;
import com.codepath.musichunter.ui.base.BaseFragment;
import com.codepath.musichunter.ui.utils.rx.AppSchedulerProvider;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass. This Fragment is used to Search for Album details of a particular Artist.
 */
public class SearchAlbumByArtistFragment extends BaseFragment implements ISearchAlbumByArtistMvpView {

    @BindView(R.id.rv_AlbumsDetails)
    RecyclerView m_rv_AlbumsDetails;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout refreshLayout;
    private SearchAlbumByArtistPresenterImpl<SearchAlbumByArtistFragment> searchAlbumByArtistPresenter;

    public SearchAlbumByArtistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_album_by_artist, null);
        searchAlbumByArtistPresenter = new SearchAlbumByArtistPresenterImpl<>(new AppDataManager(), new AppSchedulerProvider(), new CompositeDisposable());
        searchAlbumByArtistPresenter.onAttach(this);
     //   callAlbumViewBySearchArtist();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycleView();

/*
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchAlbumByArtistPresenter.loadAlbumByArtistInformation(getString(R.string.default_artist));
            }
        });

        searchAlbumByArtistPresenter.loadAlbumByArtistInformation(getString(R.string.default_artist));
*/

      /*  refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callAlbumViewBySearchArtist();
            }
        });

         callAlbumViewBySearchArtist();*/
    }

    /**
     * Method used to check that the user is on SearchAlbumByArtistFragment Fragment instance before making call to load data from API.
     * Without this, resources were not being properly allocated, across viewpager and Fragments.
     * @param isVisibleToUser Ensures that the correct fragment instance is used before loading data from API
     *
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
          callAlbumViewBySearchArtist();
        }
    }

    /**
     * Method to initialize our recyclerView.
     */
    public void initRecycleView() {
        m_rv_AlbumsDetails.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }


    /**
     * This method is used to check if there is an internet connection using RxReactiveNetwork, which allows it to check for the Network State on a
     * seperate background thread. If there is an internet connection, search for the album details.
     */
    private void callAlbumViewBySearchArtist() {
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isConnectedToInternet) throws Exception {
                                if (isConnectedToInternet) {
                                    albumViewBySearchArtist();
                                } else {
                                  //  Toast.makeText(getContext(), "No Connection", Toast.LENGTH_SHORT).show();
                                }

                        }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                      //  Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    /**
     * This method makes the main call to Album Api.
     * This method gets the SharedPreference which is saved when a query to static SearchView is updated, and loads the album detail information according to the value(artist_Name) contained in sharedPreferencce.
     * We also register  the sharedPreferences with OnSharedPreferenceChangeListener, so whenever the preference value is changed, the album details are updated
     * accordingly.
     */
    public void albumViewBySearchArtist() {

        Log.i("AlbumSearchView", "AlbumSearchView");


        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String  searchValue = sharedPreferences.getString(MainActivity.searchView_Name, getString(R.string.default_artist));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchAlbumByArtistPresenter.loadAlbumByArtistInformation(searchValue);
            }});
        searchAlbumByArtistPresenter.loadAlbumByArtistInformation(searchValue);

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
                                        searchAlbumByArtistPresenter.loadAlbumByArtistInformation(searchValue);
                                    }});
                                searchAlbumByArtistPresenter.loadAlbumByArtistInformation(searchValue);
                            }catch(Exception ex){
                                Log.i("errorONsharedPreference", ex.getMessage());
                            }


                        }
                    }
                };

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);

   /*     MainActivity.getM_Sv_Artist().setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
             //   refreshLayout.setEnabled(true);
                refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        searchAlbumByArtistPresenter.loadAlbumByArtistInformation(s);
                    }
                });
                searchAlbumByArtistPresenter.loadAlbumByArtistInformation(s);

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
     * @param albumsModel the albumsModel returned from API is passed to the custom adapter
     */
    @Override
    public void onFetchDataSuccess(AlbumsModel albumsModel) {

        if (albumsModel.getAlbum().size() > 0) {
            refreshLayout.setRefreshing(false);
            m_rv_AlbumsDetails.setAdapter(new AlbumAdapter(albumsModel));
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
        //   Log.i("onSaveInstanceStateSArt", "onSaveInstanceState_SearchArti");
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



    /*public void rxAlbumViewBySearch() {
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
                .switchMap(new Function<CharSequence, ObservableSource<AlbumsModel>>() {
                    @Override
                    public ObservableSource<AlbumsModel> apply(CharSequence query) throws Exception {
                        return iRequestInterface.getAlbums(query.toString());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AlbumsModel>() {
                               @Override
                               public void accept(AlbumsModel albumsModel) throws Exception {

                                   if (albumsModel != null && albumsModel.getAlbum().size() > 0) {
                                       m_rv_AlbumsDetails.setAdapter(new AlbumAdapter(albumsModel));
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
