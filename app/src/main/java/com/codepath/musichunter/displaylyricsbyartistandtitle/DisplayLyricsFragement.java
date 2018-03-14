package com.codepath.musichunter.displaylyricsbyartistandtitle;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.musichunter.R;

import com.codepath.musichunter.model.data.network.AppDataManager;
import com.codepath.musichunter.model.data.network.model.displaylyricsbyartistandtitle.LyricModel;
import com.codepath.musichunter.model.data.network.service.ApiList;
import com.codepath.musichunter.model.data.network.service.IRequestInterface;
import com.codepath.musichunter.ui.base.BaseFragment;
import com.codepath.musichunter.ui.utils.rx.AppSchedulerProvider;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayLyricsFragement extends BaseFragment implements IDisplayLyricsByTrackMvpView {

    @BindView(R.id.tv_lyrics)
    TextView m_lyrics;
    @BindView(R.id.tv_lyrics_track_name)
    TextView m_lyrics_track_name;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout refreshLayout;

    private DisplayLyricsByTrackMvpPresenterIml<DisplayLyricsFragement> displayLyricsByTrackMvpPresenterIml;

    public DisplayLyricsFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        displayLyricsByTrackMvpPresenterIml = new DisplayLyricsByTrackMvpPresenterIml<>(new AppDataManager(), new AppSchedulerProvider(), new CompositeDisposable());
        displayLyricsByTrackMvpPresenterIml.onAttach(this);
        return inflater.inflate(R.layout.fragment_display_lyrics_fragement, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callDisplayLyricsDetailsh();
            }
        });
        callDisplayLyricsDetailsh();

    }

    public void displayLyricsDetails() {

        String artist_Name = getArguments().getString("artist_name");
        String song_Title = getArguments().getString("song_title");
        m_lyrics_track_name.setText(song_Title);
        displayLyricsByTrackMvpPresenterIml.displayLyricsByTrack(artist_Name, song_Title);
    }

    private void callDisplayLyricsDetailsh() {
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isConnectedToInternet) throws Exception {
                        if (isConnectedToInternet) {
                            Toast.makeText(getContext(), "Connected", Toast.LENGTH_SHORT).show();
                            displayLyricsDetails();
                        } else {
                            Toast.makeText(getContext(), "No Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onFetchDataProgress() {
        showLoading();

    }

    @Override
    public void onFetchDataSuccess(LyricModel lyricModel) {
        refreshLayout.setRefreshing(false);
     //   if (lyricModel != null && lyricModel.getLyrics().length() > 0) {
            if (lyricModel != null && !lyricModel.getLyrics().isEmpty()) {
            m_lyrics.setText(lyricModel.getLyrics());
        }

        hideLoading();
    }

    @Override
    public void onFetchDataError(String error) {
        refreshLayout.setRefreshing(false);
        showMessage(error);
        hideLoading();
    }
}
