package com.codepath.musichunter.displaytracksbyAlbum;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.codepath.musichunter.R;
import com.codepath.musichunter.displaylyricsbyartistandtitle.DisplayLyricsFragement;

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


    public static void displayLyrics(String name, String title) {
        //DetailsMoviesFragement detailsMoviesFragement= new DetailsMoviesFragement();
        DisplayLyricsFragement displayLyricsFragement = new DisplayLyricsFragement();

        Bundle bundle= new Bundle();
        bundle.putString("artist_name", name);
        bundle.putString("song_title", title);
        displayLyricsFragement.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, displayLyricsFragement)
                .addToBackStack(null)
                .commit();


    }


}
