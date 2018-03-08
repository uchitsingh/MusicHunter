package com.codepath.musichunter.displaytracksbyAlbum;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.codepath.musichunter.R;

public class DisplayTracksByAlbumActivity extends AppCompatActivity {
    private static FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_tracks_by_album);
        fragmentManager = getSupportFragmentManager();
        DisplayTracksByAlbumFragment displayTracksByAlbumFragment = new DisplayTracksByAlbumFragment();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, displayTracksByAlbumFragment)
                .disallowAddToBackStack()
                .commit();

    }




}
