package com.codepath.musichunter.model.data.network.service;

/**
 * Created by uchit on 04/03/2018.
 */

public class ApiList {
    //SearchArtist
    //http://www.theaudiodb.com/api/v1/json/{APIKEY}/search.php?s={Artist name}
    public static final String BASE_URL = "http://www.theaudiodb.com/api/v1/";

    /* Search By artist: search.php?s=coldplay */

    public static final String RELATIVE_URL_ARTIST_DETAIL = "json/1/search.php ";

    /* Search Albums By Artist : searchalbum.php?s=coldplay */
    public static final String RELATIVE_URL_ALBUMS_BY_ARTIST_DETAIL = "json/1/searchalbum.php";

    /* Return Album tracks By Album Id : track.php */
    public static final String RELATIVE_URL_Tracks_BY_ALBUMS = "json/1/track.php";

    /* Search Top ten Loved tracks By Artist: track-top10.php?s=coldplay*/
    public static final String RELATIVE_URL_TOP_TEN_TRACKS_BY_ARTIST = "json/1/track-top10.php";


    //https://api.lyrics.ovh/v1/coldplay/yellow
    public static final String BASE_URL_LYRICS= "https://api.lyrics.ovh/v1/";
    public static final String RELATIVE_URL_LYRICS_BY_ARTIST_AND_TITLE="{artist}/{title}";


}
