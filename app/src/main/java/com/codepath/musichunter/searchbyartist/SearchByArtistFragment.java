package com.codepath.musichunter.searchbyartist;


import android.app.backup.BackupManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.musichunter.MainActivity;
import com.codepath.musichunter.R;
import com.codepath.musichunter.controller.RealmBackupRestore;
import com.codepath.musichunter.controller.RealmHelper;
import com.codepath.musichunter.model.data.network.AppDataManager;
import com.codepath.musichunter.model.data.network.model.searchbyartist.Artist;
import com.codepath.musichunter.model.data.network.model.searchbyartist.ArtistModel;
import com.codepath.musichunter.model.data.network.service.IRequestInterface;
import com.codepath.musichunter.model.data.network.service.ServiceConnection;
import com.codepath.musichunter.searchalbumsbyartist.AlbumAdapter;
import com.codepath.musichunter.searchalbumsbyartist.SearchAlbumByArtistFragment;
import com.codepath.musichunter.ui.base.BaseFragment;
import com.codepath.musichunter.ui.utils.rx.AppSchedulerProvider;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass. This Fragment is used to Search for Artist details.
 */
public class SearchByArtistFragment extends BaseFragment implements ISearchByArtistMvpView  {

    @BindView(R.id.cv_ArtistDetails)
    CardView m_Cv_ArtistDetails;

    @BindView(R.id.tv_ArtistBiography)
    TextView m_ArtistBio;
    @BindView(R.id.tv_ArtistGenre)
    TextView m_ArtistGenre;
    @BindView(R.id.tv_ArtistStyle)
    TextView m_ArtistStyle;
    @BindView(R.id.tv_ArtistMood)
    TextView m_ArtistMood;
    @BindView(R.id.tv_ArtistLabel)
    TextView m_ArtistLabel;
    @BindView(R.id.tv_ArtistCountry)
    TextView m_ArtistCountry;
    @BindView(R.id.tv_ArtistTimeline)
    TextView m_ArtistTimeline;
    @BindView(R.id.iv_ArtistImageLogo)
    ImageView m_ArtistImageLogo;
    //   @BindView(R.id.tv_notFound)
    //  TextView m_NotFound;
    @BindView(R.id.iv_ArtistImageCleanArt)
    ImageView m_ArtistImageClearArt;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout refreshLayout;
    private SearchByArtistPresenterIml<SearchByArtistFragment> searchByArtistPresenterIml;
    private Realm realm;
    private RealmHelper realmHelper;
    private RealmBackupRestore realmBackupRestore;
    private ArrayList<Artist> artistModelArrayList;

    public SearchByArtistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_by_artist, null);
        searchByArtistPresenterIml = new SearchByArtistPresenterIml<>(new AppDataManager(), new AppSchedulerProvider(), new CompositeDisposable());
        searchByArtistPresenterIml.onAttach(this);

       // callArtistViewBySearch();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRealm();

/*        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchByArtistPresenterIml.loadArtistInformation(getString(R.string.default_artist));
            }
        });
        searchByArtistPresenterIml.loadArtistInformation(getString(R.string.default_artist));*/

   /*     refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callArtistViewBySearch();
            }
        });


        callArtistViewBySearch();*/
        realmBackupRestore = new RealmBackupRestore(getActivity());
    }

    private void initRealm() {
        realm = realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);

    }

    public void saveDataToRealm(String artistBio, String artistGenre, String artistStyle, String artistMood
            , String artistLabel, String artistCountry, String formedYear, String diedYear,  String artistImageLogo, String artistImageClearArt)
        {

        Artist artist = new Artist(artistBio, artistGenre,artistStyle
                , artistMood, artistLabel, artistCountry, formedYear, diedYear, artistImageLogo, artistImageClearArt);
        realmHelper.saveArtists(artist);
        realmBackupRestore.backup();
    }

    /**
     * Method used to check that the user is on SearchByArtistFragment Fragment instance before making call to load data from API.
     * Without this, resources were not being properly allocated, across viewpager and Fragments.
     * @param isVisibleToUser Ensures that the correct fragment instance is used before loading data from API
     *
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
           callArtistViewBySearch();
        }
    }


    /**
     * This method is used to check if there is an internet connection using RxReactiveNetwork, which allows it to check for the Network State on a
     * seperate background thread. If there is an internet connection, search for the artist and load their details.
     */
    public void callArtistViewBySearch() {
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isConnectedToInternet) throws Exception {
                            if (isConnectedToInternet) {
                                artistViewBySearch();
                            } else {
                             //   Toast.makeText(getContext(), "No Connection", Toast.LENGTH_SHORT).show();
                              //  Artist artist = (Artist) realmHelper.getArtists().get(0);

                              //  artistModelArrayList =
                              // int position = realmHelper.getArtists().size()-1;

                               populateViews(realmHelper.getArtists());
                                artistViewBySearch();
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
     * This method makes the main call to Artist Api.
     * This method gets the SharedPreference which is saved when a query to static SearchView is updated, and loads the artist detail information according to the value(artist_Name) contained in sharedPreferencce.
     * We also register  the sharedPreferences with OnSharedPreferenceChangeListener, so whenever the preference value is changed, the artist details are updated
     * accordingly.
     */
    public void artistViewBySearch() {
        Log.i("artistSearch", "artistSearchView");

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String  searchValue = sharedPreferences.getString(MainActivity.searchView_Name, getString(R.string.default_artist));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchByArtistPresenterIml.loadArtistInformation(searchValue);
            }});
        searchByArtistPresenterIml.loadArtistInformation(searchValue);

        SharedPreferences.OnSharedPreferenceChangeListener listener  =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                            // listener implementation
                        if (key.equals(MainActivity.searchView_Name)){
                            String searchValue = prefs.getString(key, MainActivity.getM_Sv_Artist().getQuery().toString());
                          /*
                            });*/try {
                                refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                    @Override
                                    public void onRefresh() {
                                        searchByArtistPresenterIml.loadArtistInformation(searchValue);
                                    }});
                                searchByArtistPresenterIml.loadArtistInformation(searchValue);
                            }catch(Exception ex){
                              Log.i("errorONsharedPreference", ex.getMessage());
                            }


                        }
                    }
                };

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);


/*        MainActivity.getM_Sv_Artist().setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        searchByArtistPresenterIml.loadArtistInformation(s);
                    }
                });
                searchByArtistPresenterIml.loadArtistInformation(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });*/

    }


    /**
     * This method is used to populate views with related artistModel details
     * @param artistModel The Artist Model will be supplied when the data is fetched successfully from APl
     */
    public void populateViews(List<Artist> artistModel) {
        if (artistModel != null && artistModel.size() > 0) {
            m_Cv_ArtistDetails.setVisibility(View.VISIBLE);
            //     m_NotFound.setVisibility(View.INVISIBLE);
            //        m_ArtistName.setText(artistModel.getArtists().get(0).getStrArtist());
            m_ArtistBio.setText(artistModel.get(0).getStrBiographyEN());
            m_ArtistGenre.setText(artistModel.get(0).getStrGenre());
            m_ArtistStyle.setText(artistModel.get(0).getStrStyle());
            m_ArtistMood.setText(artistModel.get(0).getStrMood());

            String artist_Label = artistModel.get(0).getStrLabel();
            if (artist_Label != null) {
                m_ArtistLabel.setText(artistModel.get(0).getStrLabel());
            } else {
                m_ArtistLabel.setText("N/A");
            }

            m_ArtistCountry.setText(artistModel.get(0).getStrCountry());

            String formedYear = artistModel.get(0).getIntFormedYear();

            String diedYear = (String) artistModel.get(0).getIntDiedYear();

            if (formedYear != null && diedYear != null) {
                m_ArtistTimeline.setText(String.valueOf(formedYear + " - " + diedYear));
            } else if (formedYear != null) {
                m_ArtistTimeline.setText(String.valueOf(formedYear));
            } else if (diedYear != null) {
                m_ArtistTimeline.setText(String.valueOf(diedYear));
            }

            //    int disbandedYear = Integer.parseInt(artistModel.getArtists().get(0).getIntFormedYear());

            Picasso.with(getContext()).load(artistModel.get(0).getStrArtistLogo()).into(m_ArtistImageLogo);


            String artistImageClearArt = artistModel.get(0).getStrArtistClearart();

            if (artistImageClearArt == null) {
                artistImageClearArt = artistModel.get(0).getStrArtistFanart();

            }
            Picasso.with(getContext()).load(artistImageClearArt)
                 /*   .resize(5000,5000)
                    .centerCrop()*/
                    .into(m_ArtistImageClearArt);
        } else {

        }
    }


    @Override
    public void onFetchDataProgress() {
        showLoading();

    }

    /**
     * if the data was successfully fetched, popuplate views with artistModel
     * @param artistModel the artistModel fetched from API is passed to the custom adapter
     */
    @Override
    public void onFetchDataSuccess(ArtistModel artistModel) {

        if ( artistModel.getArtists().size() > 0) {
            refreshLayout.setRefreshing(false);
            populateViews(artistModel.getArtists());
            hideLoading();
           // if(artistModel.getArtists().get(0).getStrArtist() )


            realm.deleteAll();
            saveDataToRealm(artistModel.getArtists().get(0).getStrBiographyEN(),
                    artistModel.getArtists().get(0).getStrGenre(),
                    artistModel.getArtists().get(0).getStrStyle(),
                    artistModel.getArtists().get(0).getStrMood(),
                    artistModel.getArtists().get(0).getStrLabel(),
                    artistModel.getArtists().get(0).getStrCountry(),
                    artistModel.getArtists().get(0).getIntFormedYear(),
                    artistModel.getArtists().get(0).getIntDiedYear().toString(),
                    artistModel.getArtists().get(0).getStrArtistLogo(),
                    artistModel.getArtists().get(0).getStrArtistClearart()
                    );

        }
    }

    @Override
    public void onFetchDataError(String error) {
        //   Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        refreshLayout.setRefreshing(false);
        showMessage(error);
        hideLoading();
    }


  /*  @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //    Log.i("onSaveInstanceStateSArt", "onSaveInstanceState_SearchArti");
        CharSequence searchView = MainActivity.getM_Sv_Artist().getQuery();
        outState.putCharSequence("savedSearchViewQuery", searchView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            //      Log.i("onRetainInstance", "onRetainInstance");
            CharSequence searchViewQuery = (CharSequence) savedInstanceState.get("savedSearchViewQuery");
            MainActivity.getM_Sv_Artist().setQuery(searchViewQuery, true);
        }
    }
*/




  /*  @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(MainActivity.searchView_Name)){
            // Write your code here
            String searchValue = sharedPreferences.getString(key, MainActivity.getM_Sv_Artist().getQuery().toString());
         //   Preference connectionPref = findPreference(key);
            searchByArtistPresenterIml.loadArtistInformation(searchValue);


        }
    }*/
}
