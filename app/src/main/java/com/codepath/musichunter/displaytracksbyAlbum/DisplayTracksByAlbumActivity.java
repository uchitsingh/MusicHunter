package com.codepath.musichunter.displaytracksbyAlbum;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codepath.musichunter.R;
import com.codepath.musichunter.displaylyricsbyartistandtitle.DisplayLyricsFragement;


/**
 * This is the second Activity of our Application, and launched via Intent when , the user clicks on particular album Image displayed in {@link com.codepath.musichunter.searchalbumsbyartist.SearchAlbumByArtistFragment} Adapter {@link com.codepath.musichunter.searchalbumsbyartist.AlbumAdapter}
 *
 */
public class DisplayTracksByAlbumActivity extends AppCompatActivity {
    private static FragmentManager fragmentManager;
    private TextView textview;
    private RelativeLayout.LayoutParams layoutparams;

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


        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(false);
        // Specify that tabs should be displayed in the action bar.
      //  actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //Make the Title of actionbar centered
        textview = new TextView(getApplicationContext());
        layoutparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textview.setLayoutParams(layoutparams);
        textview.setText(R.string.app_name);
        textview.setTextColor(Color.WHITE);
        textview.setGravity(Gravity.CENTER);
        textview.setTextSize(20);

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(textview);
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
