package com.codepath.musichunter.displaytracksbyAlbum;

import com.codepath.musichunter.ui.base.MvpPresenter;

/**
 * Created by uchit on 12/03/2018.
 */

public interface IDisplayTracksByAlbumMvpPresenter <V extends IDisplayTracksByAlbumMvpView> extends MvpPresenter<V>{
    void displayTracksbyAlbum(String artistName);
}
