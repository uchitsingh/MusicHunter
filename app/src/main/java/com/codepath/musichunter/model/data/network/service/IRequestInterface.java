package com.codepath.musichunter.model.data.network.service;

import com.codepath.musichunter.model.data.network.model.displaylyricsbyartistandtitle.LyricModel;
import com.codepath.musichunter.model.data.network.model.displaytracksbyalbum.TracksModel;
import com.codepath.musichunter.model.data.network.model.searchbyartist.ArtistModel;
import com.codepath.musichunter.model.data.network.model.searchtoptenlovedtracksbyArtist.TopTenLovedTracksByArtistModel;
import com.codepath.musichunter.model.data.network.model.searhalbumsbyartist.AlbumsModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by uchit on 04/03/2018.
 */

public interface IRequestInterface {
    @GET(ApiList.RELATIVE_URL_ARTIST_DETAIL)
    Observable<ArtistModel> getArtists(@Query("s") String artistName);

    @GET(ApiList.RELATIVE_URL_ALBUMS_BY_ARTIST_DETAIL)
    Observable<AlbumsModel> getAlbums(@Query("s") String artistName);

    @GET(ApiList.RELATIVE_URL_Tracks_BY_ALBUMS)
    Observable<TracksModel> getTracksByAlbum(@Query("m") String albumId);

    @GET(ApiList.RELATIVE_URL_TOP_TEN_TRACKS_BY_ARTIST)
    Observable<TopTenLovedTracksByArtistModel> getTopTenLovedTracks(@Query("s") String artistName);

    @GET(ApiList.RELATIVE_URL_LYRICS_BY_ARTIST_AND_TITLE)
    Observable<LyricModel> getLyric(@Path("artist") String artist, @Path("title") String title);
}
