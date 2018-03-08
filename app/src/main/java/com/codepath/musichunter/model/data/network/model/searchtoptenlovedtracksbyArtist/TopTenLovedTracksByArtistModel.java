package com.codepath.musichunter.model.data.network.model.searchtoptenlovedtracksbyArtist;

import com.codepath.musichunter.model.data.network.model.displaytracksbyalbum.Track;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by uchit on 08/03/2018.
 */

public class TopTenLovedTracksByArtistModel {

    @SerializedName("track")
    @Expose
    private List<Track> track = null;

    public List<Track> getTrack() {
        return track;
    }

    public void setTrack(List<Track> track) {
        this.track = track;
    }

}