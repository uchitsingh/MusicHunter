package com.codepath.musichunter.displaylyricsbyartistandtitle;

import com.codepath.musichunter.ui.base.MvpPresenter;

/**
 * Created by uchit on 12/03/2018.
 */

public interface IDisplayLyricsByTrackMvpPresenter <V extends IDisplayLyricsByTrackMvpView> extends MvpPresenter<V> {
    void displayLyricsByTrack(String artistName, String title);
}
