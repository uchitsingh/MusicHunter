package com.codepath.musichunter.model.data.network;

import com.codepath.musichunter.model.data.network.model.displaytracksbyalbum.TracksModel;
import com.codepath.musichunter.model.data.network.model.searchbyartist.ArtistModel;
import com.codepath.musichunter.model.data.network.model.searchtoptenlovedtracksbyArtist.TopTenLovedTracksByArtistModel;
import com.codepath.musichunter.model.data.network.model.searhalbumsbyartist.AlbumsModel;
import com.codepath.musichunter.model.data.network.service.IRequestInterface;
import com.codepath.musichunter.model.data.network.service.ServiceConnection;

import io.reactivex.Observable;

/**
 * Created by uchit on 09/03/2018.
 */

public class AppApiHelper implements IApiHelper {
    private IRequestInterface iRequestInterface;
    public AppApiHelper() {
        iRequestInterface = ServiceConnection.getConnection();
    }

    @Override
    public Observable<ArtistModel> getArtists(String artistName) {
        return iRequestInterface.getArtists(artistName);
    }

    @Override
    public Observable<AlbumsModel> getAlbums(String artistName) {
        return iRequestInterface.getAlbums(artistName);
    }

    @Override
    public Observable<TracksModel> getTracksByAlbum(String albumId) {
        return iRequestInterface.getTracksByAlbum(albumId);
    }

    @Override
    public Observable<TopTenLovedTracksByArtistModel> getTopTenLovedTracks(String artistName) {
        return iRequestInterface.getTopTenLovedTracks(artistName);
    }
}
