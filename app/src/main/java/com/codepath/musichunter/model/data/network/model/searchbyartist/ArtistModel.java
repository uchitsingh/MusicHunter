package com.codepath.musichunter.model.data.network.model.searchbyartist;
import java.io.Serializable;
import java.util.List;

import com.codepath.musichunter.model.data.network.model.searchbyartist.Artist;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by uchit on 04/03/2018.
 */



public class ArtistModel implements Serializable {

    @SerializedName("artists")
    @Expose
    private List<Artist> artists = null;

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

}

