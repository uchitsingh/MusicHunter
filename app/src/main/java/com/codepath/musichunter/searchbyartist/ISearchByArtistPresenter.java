package com.codepath.musichunter.searchbyartist;

import com.codepath.musichunter.ui.base.MvpPresenter;

/**
 * Created by uchit on 09/03/2018.
 */

public interface ISearchByArtistPresenter<V extends ISearchByArtistMvpView> extends MvpPresenter<V> {
    void loadArtistInformation(String artistName);
}
