package com.codepath.musichunter.displaytracksbyAlbum;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.codepath.musichunter.R;
import com.codepath.musichunter.displaylyricsbyartistandtitle.DisplayLyricsFragement;


/**
 * This is the second Activity of our Application, and launched via Intent when , the user clicks on particular album Image displayed in {@link com.codepath.musichunter.searchalbumsbyartist.SearchAlbumByArtistFragment} Adapter {@link com.codepath.musichunter.searchalbumsbyartist.AlbumAdapter}
 *
 */
public class DisplayTracksByAlbumActivity extends AppCompatActivity {
    private static FragmentManager fragmentManager;

    /**
     * Initializes fragmentManager, and adds the default Fragment, {@link DisplayTracksByAlbumFragment} to the current activity.
     * @param savedInstanceState
     */
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


    /**
     * Static method that is used by {@link TracksAdapter} to replace {@link DisplayTracksByAlbumFragment} with {@link DisplayLyricsFragement} passing in its arguments the artist_Name, and Song_Title using Bundle.
     * @param name  the artist_Name
     * @param title the Song_Title
     */
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
