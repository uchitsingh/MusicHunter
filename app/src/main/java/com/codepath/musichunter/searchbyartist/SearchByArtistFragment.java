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
import com.codepath.musichunter.model.data.network.AppDataManager;
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


/**
 * A simple {@link Fragment} subclass.
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

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
           callArtistViewBySearch();
        }
    }

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
                            }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                       // Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


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



    public void populateViews(ArtistModel artistModel) {
        if (artistModel != null && artistModel.getArtists().size() > 0) {
            m_Cv_ArtistDetails.setVisibility(View.VISIBLE);
            //     m_NotFound.setVisibility(View.INVISIBLE);
            //        m_ArtistName.setText(artistModel.getArtists().get(0).getStrArtist());
            m_ArtistBio.setText(artistModel.getArtists().get(0).getStrBiographyEN());
            m_ArtistGenre.setText(artistModel.getArtists().get(0).getStrGenre());
            m_ArtistStyle.setText(artistModel.getArtists().get(0).getStrStyle());
            m_ArtistMood.setText(artistModel.getArtists().get(0).getStrMood());

            String artist_Label = artistModel.getArtists().get(0).getStrLabel();
            if (artist_Label != null) {
                m_ArtistLabel.setText(artistModel.getArtists().get(0).getStrLabel());
            } else {
                m_ArtistLabel.setText("N/A");
            }

            m_ArtistCountry.setText(artistModel.getArtists().get(0).getStrCountry());

            String formedYear = artistModel.getArtists().get(0).getIntFormedYear();

            String diedYear = (String) artistModel.getArtists().get(0).getIntDiedYear();

            if (formedYear != null && diedYear != null) {
                m_ArtistTimeline.setText(String.valueOf(formedYear + " - " + diedYear));
            } else if (formedYear != null) {
                m_ArtistTimeline.setText(String.valueOf(formedYear));
            } else if (diedYear != null) {
                m_ArtistTimeline.setText(String.valueOf(diedYear));
            }

            //    int disbandedYear = Integer.parseInt(artistModel.getArtists().get(0).getIntFormedYear());

            Picasso.with(getContext()).load(artistModel.getArtists().get(0).getStrArtistLogo()).into(m_ArtistImageLogo);


            String artistImageClearArt = artistModel.getArtists().get(0).getStrArtistClearart();

            if (artistImageClearArt == null) {
                artistImageClearArt = artistModel.getArtists().get(0).getStrArtistFanart();

            }
            Picasso.with(getContext()).load(artistImageClearArt).into(m_ArtistImageClearArt);
        } else {

        }
    }


    @Override
    public void onFetchDataProgress() {
        showLoading();

    }

    @Override
    public void onFetchDataSuccess(ArtistModel artistModel) {

        if ( artistModel.getArtists().size() > 0) {
            refreshLayout.setRefreshing(false);
            populateViews(artistModel);
            hideLoading();
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
