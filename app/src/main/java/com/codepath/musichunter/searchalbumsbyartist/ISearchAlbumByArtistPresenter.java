package com.codepath.musichunter.searchalbumsbyartist;

import com.codepath.musichunter.ui.base.MvpPresenter;

/**
 * Created by uchit on 09/03/2018.
 */

public interface ISearchAlbumByArtistPresenter <V extends ISearchAlbumByArtistMvpView> extends MvpPresenter<V>{

    void loadAlbumByArtistInformation(String artistName );
}
