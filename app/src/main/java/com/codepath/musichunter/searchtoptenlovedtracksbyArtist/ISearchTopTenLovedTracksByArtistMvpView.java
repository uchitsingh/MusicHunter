package com.codepath.musichunter.searchtoptenlovedtracksbyArtist;

import com.codepath.musichunter.model.data.network.model.displaytracksbyalbum.TracksModel;
import com.codepath.musichunter.model.data.network.model.searchtoptenlovedtracksbyArtist.TopTenLovedTracksByArtistModel;
import com.codepath.musichunter.ui.base.MvpView;

/**
 * Created by uchit on 12/03/2018.
 */

public interface ISearchTopTenLovedTracksByArtistMvpView extends MvpView {

    void onFetchDataProgress();
    void onFetchDataSuccess(TopTenLovedTracksByArtistModel topTenLovedTracksByArtistModel);
    void onFetchDataError(String error);
}
