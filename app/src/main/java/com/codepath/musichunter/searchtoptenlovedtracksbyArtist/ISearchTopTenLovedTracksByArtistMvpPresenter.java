package com.codepath.musichunter.searchtoptenlovedtracksbyArtist;

import com.codepath.musichunter.ui.base.MvpPresenter;

/**
 * Created by uchit on 12/03/2018.
 */

public interface ISearchTopTenLovedTracksByArtistMvpPresenter<V extends ISearchTopTenLovedTracksByArtistMvpView> extends MvpPresenter<V> {

    void loadTopTenByArtistInformation(String artistName);
}
