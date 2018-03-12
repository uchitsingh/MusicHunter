package com.codepath.musichunter.searchtoptenlovedtracksbyArtist;

import com.codepath.musichunter.model.data.network.model.searchtoptenlovedtracksbyArtist.TopTenLovedTracksByArtistModel;
import com.codepath.musichunter.model.data.network.service.IDataManager;
import com.codepath.musichunter.ui.base.BasePresenter;
import com.codepath.musichunter.ui.utils.rx.SchedulerProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by uchit on 12/03/2018.
 */

public class SearchTopTenLovedTracksByArtistMvpPresenterIml<V extends ISearchTopTenLovedTracksByArtistMvpView> extends BasePresenter<V> implements ISearchTopTenLovedTracksByArtistMvpPresenter<V>{
    public SearchTopTenLovedTracksByArtistMvpPresenterIml(IDataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void loadTopTenByArtistInformation(String artistName) {

        getCompositeDisposable().add(getDataManager().getTopTenLovedTracks(artistName)
            .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<TopTenLovedTracksByArtistModel>() {
                    @Override
                    public void accept(TopTenLovedTracksByArtistModel topTenLovedTracksByArtistModel) throws Exception {

                        getMvpView().onFetchDataSuccess(topTenLovedTracksByArtistModel);

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
