package com.codepath.musichunter.model.data.network.model.displaylyricsbyartistandtitle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by uchit on 12/03/2018.
 */

public class LyricModel {

    @SerializedName("lyrics")
    @Expose
    private String lyrics;

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

}