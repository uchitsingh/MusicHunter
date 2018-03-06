package com.codepath.musichunter.model.data.network.service;

import com.codepath.musichunter.model.data.network.model.Artist;

/**
 * Created by uchit on 04/03/2018.
 */

public class ApiList {
    //http://www.theaudiodb.com/api/v1/json/{APIKEY}/search.php?s={Artist name}
    //http://www.theaudiodb.com/api/v1/json/1/search.php?s=coldplay
    public static final String BASE_URL ="http://www.theaudiodb.com/api/v1/";

    public static final String RELATIVE_URL_ARTIST_DETAIL = "json/1/search.php ";
}
