package com.codepath.musichunter.displaytracksbyAlbum;

import com.codepath.musichunter.model.data.network.model.displaytracksbyalbum.TracksModel;
import com.codepath.musichunter.model.data.network.model.searchtoptenlovedtracksbyArtist.TopTenLovedTracksByArtistModel;
import com.codepath.musichunter.model.data.network.service.IDataManager;
import com.codepath.musichunter.ui.base.BasePresenter;
import com.codepath.musichunter.ui.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by uchit on 12/03/2018.
 */

public class DisplayTracksByAlbumMvpPresenterIml <V extends IDisplayTracksByAlbumMvpView> extends BasePresenter<V> implements IDisplayTracksByAlbumMvpPresenter<V>{
    public DisplayTracksByAlbumMvpPresenterIml(IDataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void displayTracksbyAlbum(String artistName) {

        getCompositeDisposable().add(getDataManager().getTracksByAlbum(artistName)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<TracksModel>() {
                    @Override
                    public void accept(TracksModel tracksModel) throws Exception {

                        getMvpView().onFetchDataSuccess(tracksModel);

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
