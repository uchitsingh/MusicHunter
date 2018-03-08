package com.codepath.musichunter.model.data.network.service;

/**
 * Created by uchit on 04/03/2018.
 */

public class ApiList {
    //SearchArtist
    //http://www.theaudiodb.com/api/v1/json/{APIKEY}/search.php?s={Artist name}
    public static final String BASE_URL = "http://www.theaudiodb.com/api/v1/";

    /*
    Search By artist: search.php?s=coldplay
     */

    public static final String RELATIVE_URL_ARTIST_DETAIL = "json/1/search.php ";

    /*  searchalbumsByArtist : searchalbum.php?s=coldplay*/
    public static final String RELATIVE_URL_ALBUMS_BY_ARTIST_DETAIL = "json/1/searchalbum.php";

    /*    Return Album tracks By Album Id : track.php*/
    public static final String RELATIVE_URL_Tracks_BY_ALBUMS = "track.php";


}
