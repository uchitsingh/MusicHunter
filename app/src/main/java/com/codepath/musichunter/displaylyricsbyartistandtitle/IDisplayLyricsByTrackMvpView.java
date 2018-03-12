package com.codepath.musichunter.displaylyricsbyartistandtitle;

import com.codepath.musichunter.model.data.network.model.displaylyricsbyartistandtitle.LyricModel;
import com.codepath.musichunter.model.data.network.model.displaytracksbyalbum.TracksModel;
import com.codepath.musichunter.ui.base.MvpView;

/**
 * Created by uchit on 12/03/2018.
 */

public interface IDisplayLyricsByTrackMvpView extends MvpView {
    void onFetchDataProgress();
    void onFetchDataSuccess(LyricModel lyricModel);
    void onFetchDataError(String error);


}
