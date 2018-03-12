package com.codepath.musichunter.searchbyartist;

import com.codepath.musichunter.model.data.network.model.searchbyartist.ArtistModel;
import com.codepath.musichunter.model.data.network.service.ApiList;
import com.codepath.musichunter.model.data.network.service.IDataManager;
import com.codepath.musichunter.ui.base.BasePresenter;
import com.codepath.musichunter.ui.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by uchit on 09/03/2018.
 */

public class SearchByArtistPresenterIml <V extends ISearchByArtistMvpView> extends BasePresenter<V> implements ISearchByArtistPresenter<V> {

    public SearchByArtistPresenterIml(IDataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }



    @Override
    public void loadArtistInformation(String artistName) {
        getCompositeDisposable().add(getDataManager().getArtists(artistName)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ArtistModel>() {
                    @Override
                    public void accept(ArtistModel artistModel) throws Exception {

                        getMvpView().onFetchDataSuccess(artistModel);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().onFetchDataError(throwable.getMessage());
                    }
                }));
        getMvpView().onFetchDataProgress();
    }
}
