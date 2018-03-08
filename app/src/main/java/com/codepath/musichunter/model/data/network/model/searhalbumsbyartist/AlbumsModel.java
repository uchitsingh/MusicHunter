package com.codepath.musichunter.model.data.network.model.searhalbumsbyartist;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by uchit on 07/03/2018.
 */




public class AlbumsModel {

    @SerializedName("album")
    @Expose
    private List<Album> album = null;

    public List<Album> getAlbum() {
        return album;
    }

    public void setAlbum(List<Album> album) {
        this.album = album;
    }

}