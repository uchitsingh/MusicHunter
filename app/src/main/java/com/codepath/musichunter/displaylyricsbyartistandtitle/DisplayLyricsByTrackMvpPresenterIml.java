package com.codepath.musichunter.displaylyricsbyartistandtitle;

import com.codepath.musichunter.model.data.network.model.displaylyricsbyartistandtitle.LyricModel;
import com.codepath.musichunter.model.data.network.model.displaytracksbyalbum.TracksModel;
import com.codepath.musichunter.model.data.network.service.IDataManager;
import com.codepath.musichunter.ui.base.BasePresenter;
import com.codepath.musichunter.ui.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by uchit on 12/03/2018.
 */

public class DisplayLyricsByTrackMvpPresenterIml <V extends IDisplayLyricsByTrackMvpView> extends BasePresenter<V> implements IDisplayLyricsByTrackMvpPresenter<V>{
    public DisplayLyricsByTrackMvpPresenterIml(IDataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void displayLyricsByTrack(String artistName, String title) {
        getCompositeDisposable().add(getDataManager().getLyric(artistName, title)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<LyricModel>() {
                    @Override
                    public void accept(LyricModel lyricModel) throws Exception {
                        getMvpView().onFetchDataSuccess(lyricModel);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().onFetchDataError(throwable.getMessage());
                    }
                })
        );
        getMvpView().onFetchDataProgress();
    }
}
