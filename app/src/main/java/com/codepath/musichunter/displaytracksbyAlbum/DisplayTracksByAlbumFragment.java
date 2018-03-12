package com.codepath.musichunter.displaytracksbyAlbum;


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
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.musichunter.MainActivity;
import com.codepath.musichunter.MyApp;
import com.codepath.musichunter.R;
import com.codepath.musichunter.model.data.network.AppDataManager;
import com.codepath.musichunter.model.data.network.model.displaytracksbyalbum.TracksModel;
import com.codepath.musichunter.model.data.network.model.searchbyartist.ArtistModel;
import com.codepath.musichunter.model.data.network.model.searhalbumsbyartist.AlbumsModel;
import com.codepath.musichunter.model.data.network.service.IRequestInterface;
import com.codepath.musichunter.model.data.network.service.ServiceConnection;
import com.codepath.musichunter.ui.base.BaseFragment;
import com.codepath.musichunter.ui.utils.rx.AppSchedulerProvider;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayTracksByAlbumFragment extends BaseFragment implements IDisplayTracksByAlbumMvpView {

    //private IRequestInterface iRequestInterface;
    @BindView(R.id.rv_trackDetails) RecyclerView m_rv_tracksDetails;
    @BindView(R.id.tv_Albums_AlbumDescription) TextView m_Albums_AlbumDescription;
    @BindView(R.id.iv_albums_cdart) ImageView m_iv_albums_cdart;
    @BindView(R.id.swiperefresh) SwipeRefreshLayout refreshLayout;

    private DisplayTracksByAlbumMvpPresenterIml<DisplayTracksByAlbumFragment> displayTracksByAlbumMvpPresenterIml;

    public DisplayTracksByAlbumFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_display_tracks_by_album, null);
        displayTracksByAlbumMvpPresenterIml = new DisplayTracksByAlbumMvpPresenterIml<>(new AppDataManager(), new AppSchedulerProvider(), new CompositeDisposable());
        displayTracksByAlbumMvpPresenterIml.onAttach(this);

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      //  iRequestInterface = ServiceConnection.getConnection();
        initRecycleView();
       // refreshLayout.setEnabled(false);
        initAlbumArt_Description();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /*
                if(NetworkUtils.isNetworkConnected(getActivity())){
                    displayTopRatedMovies();
                }else{
                    Toast.makeText(getActivity(), "no network availiable", Toast.LENGTH_SHORT).show();
                    //AlertDialog ->builder pattern
                }*/
                callTrackViewBySearch();
            }
        });

        // displayTopRatedMovies();
      //  callService();
        callTrackViewBySearch();

    }

    private void initAlbumArt_Description(){
        String albumDescription = getActivity().getIntent().getExtras().getString("albumDescription");
        m_Albums_AlbumDescription.setText(albumDescription);


        String albumsCdArt = getActivity().getIntent().getExtras().getString("albumartThumb");
        if (albumsCdArt != null && !albumsCdArt.isEmpty()) {
            Picasso.with(MyApp.getInstance().getAppContext()).load(albumsCdArt)
                    .resize(1500, 1500)
                    .centerCrop()
                    .into(m_iv_albums_cdart);
        } else {
        }
    }

    private void initRecycleView() {

        m_rv_tracksDetails.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void trackViewBySearch() {
        String albumId = getActivity().getIntent().getExtras().getString("albumId");

      //  refreshLayout.setEnabled(true);
/*        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayTracksByAlbumMvpPresenterIml.displayTracksbyAlbum(albumId);

            }
        });*/
        displayTracksByAlbumMvpPresenterIml.displayTracksbyAlbum(albumId);
    }

    private void callTrackViewBySearch() {
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
                            trackViewBySearch();
                        } else {
                            Toast.makeText(getContext(), "No Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    @Override
    public void onFetchDataProgress() {
        showLoading();
    }

    @Override
    public void onFetchDataSuccess(TracksModel tracksModel) {
        refreshLayout.setRefreshing(false);
        m_rv_tracksDetails.setAdapter(new TracksAdapter(tracksModel));
        hideLoading();



    }

    @Override
    public void onFetchDataError(String error) {
       refreshLayout.setRefreshing(false);
        showMessage(error);
        hideLoading();
    }
}
