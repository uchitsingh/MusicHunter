package com.codepath.musichunter.displaytracksbyAlbum;

import com.codepath.musichunter.model.data.network.model.displaytracksbyalbum.TracksModel;
import com.codepath.musichunter.model.data.network.model.searhalbumsbyartist.AlbumsModel;
import com.codepath.musichunter.ui.base.MvpView;

/**
 * Created by uchit on 12/03/2018.
 */

public interface IDisplayTracksByAlbumMvpView extends MvpView {

    void onFetchDataProgress();
    void onFetchDataSuccess(TracksModel tracksModel);
    void onFetchDataError(String error);


}
