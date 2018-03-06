package com.codepath.musichunter.model.data.network.service;

import com.codepath.musichunter.model.data.network.model.ArtistModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by uchit on 04/03/2018.
 */

public interface IRequestInterface {
    @GET(ApiList.RELATIVE_URL_ARTIST_DETAIL)
    Observable<ArtistModel> getArtists(@Query("s") String artistName);

 /*   @GET(ApiList.RELATIVE_URL_ARTIST_DETAIL)
    Observable<ArtistModel> getArtists();
*/


}
