package com.codepath.musichunter.model.data.network;

import com.codepath.musichunter.model.data.network.model.displaylyricsbyartistandtitle.LyricModel;
import com.codepath.musichunter.model.data.network.model.displaytracksbyalbum.TracksModel;
import com.codepath.musichunter.model.data.network.model.searchbyartist.ArtistModel;
import com.codepath.musichunter.model.data.network.model.searchtoptenlovedtracksbyArtist.TopTenLovedTracksByArtistModel;
import com.codepath.musichunter.model.data.network.model.searhalbumsbyartist.AlbumsModel;
import io.reactivex.Observable;
import retrofit2.http.Path;

/**
 * Created by uchit on 09/03/2018.
 */

public interface IApiHelper {


    Observable<ArtistModel> getArtists( String artistName);


    Observable<AlbumsModel> getAlbums(String artistName);


    Observable<TracksModel> getTracksByAlbum( String albumId);


    Observable<TopTenLovedTracksByArtistModel> getTopTenLovedTracks( String artistName);

    Observable<LyricModel> getLyric( String artist, String title);
}
