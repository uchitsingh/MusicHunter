package com.codepath.musichunter.searchalbumsbyartist;

import com.codepath.musichunter.model.data.network.model.searchbyartist.ArtistModel;
import com.codepath.musichunter.model.data.network.model.searhalbumsbyartist.AlbumsModel;
import com.codepath.musichunter.model.data.network.service.IDataManager;
import com.codepath.musichunter.ui.base.BasePresenter;
import com.codepath.musichunter.ui.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by uchit on 09/03/2018.
 */

public class SearchAlbumByArtistPresenterImpl <V extends  ISearchAlbumByArtistMvpView> extends BasePresenter<V> implements  ISearchAlbumByArtistPresenter<V>{

    public SearchAlbumByArtistPresenterImpl(IDataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void loadAlbumByArtistInformation(String artistName) {

        getCompositeDisposable().add(getDataManager().getAlbums(artistName)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<AlbumsModel>() {
                    @Override
                    public void accept(AlbumsModel albumsModel) throws Exception {
                        getMvpView().onFetchDataSuccess(albumsModel);
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
