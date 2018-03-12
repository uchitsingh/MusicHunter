package com.codepath.musichunter.searchalbumsbyartist;

import com.codepath.musichunter.model.data.network.model.searhalbumsbyartist.AlbumsModel;
import com.codepath.musichunter.ui.base.MvpView;

/**
 * Created by uchit on 09/03/2018.
 */

public interface ISearchAlbumByArtistMvpView extends MvpView {

    void onFetchDataProgress();
    void onFetchDataSuccess(AlbumsModel albumsModel);
    void onFetchDataError(String error);

}
