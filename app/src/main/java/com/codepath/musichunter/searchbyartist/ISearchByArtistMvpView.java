package com.codepath.musichunter.searchbyartist;

import com.codepath.musichunter.model.data.network.model.searchbyartist.ArtistModel;
import com.codepath.musichunter.ui.base.MvpView;

/**
 * Created by uchit on 09/03/2018.
 */

public interface ISearchByArtistMvpView extends MvpView {

    void onFetchDataProgress();
    void onFetchDataSuccess(ArtistModel artistModel);
    void onFetchDataError(String error);

}
