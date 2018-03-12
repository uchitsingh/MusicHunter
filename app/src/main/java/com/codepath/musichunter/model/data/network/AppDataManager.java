package com.codepath.musichunter.model.data.network;

import com.codepath.musichunter.model.data.network.model.displaytracksbyalbum.TracksModel;
import com.codepath.musichunter.model.data.network.model.searchbyartist.ArtistModel;
import com.codepath.musichunter.model.data.network.model.searchtoptenlovedtracksbyArtist.TopTenLovedTracksByArtistModel;
import com.codepath.musichunter.model.data.network.model.searhalbumsbyartist.AlbumsModel;
import com.codepath.musichunter.model.data.network.service.IDataManager;

import io.reactivex.Observable;

/**
 * Created by uchit on 09/03/2018.
 */

public class AppDataManager implements IDataManager {

    private IApiHelper iApiHelper;
    public AppDataManager() {
        iApiHelper = new AppApiHelper();
    }

    @Override
    public Observable<ArtistModel> getArtists(String artistName) {
        return iApiHelper.getArtists(artistName);
    }

    @Override
    public Observable<AlbumsModel> getAlbums(String artistName) {
        return iApiHelper.getAlbums(artistName);
    }

    @Override
    public Observable<TracksModel> getTracksByAlbum(String albumId) {
        return iApiHelper.getTracksByAlbum(albumId);
    }

    @Override
    public Observable<TopTenLovedTracksByArtistModel> getTopTenLovedTracks(String artistName) {
        return iApiHelper.getTopTenLovedTracks(artistName);
    }
}
