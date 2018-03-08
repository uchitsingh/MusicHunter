package com.codepath.musichunter.model.data.network.model.displaytracksbyalbum;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by uchit on 07/03/2018.
 */

public class TracksModel {

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