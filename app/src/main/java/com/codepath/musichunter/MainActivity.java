package com.codepath.musichunter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SearchView;
import android.widget.TextView;

import com.codepath.musichunter.searchalbumsbyartist.SearchAlbumByArtistFragment;
import com.codepath.musichunter.searchbyartist.SearchByArtistFragment;
import com.codepath.musichunter.searchtoptenlovedtracksbyArtist.SearchTopTenLovedTracksByArtist;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {

    private ViewPager mViewPager;
    private MusicPagerAdapter musicPagerAdapter;
    private TextView textview;
    private LayoutParams layoutparams;
    private static SearchView m_Sv_Artist ;

    public static SearchView getM_Sv_Artist() {
        return m_Sv_Artist;
    }
    public static FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null){
            fragmentManager = getSupportFragmentManager();
        }
        initViewPager();
        m_Sv_Artist = (SearchView) findViewById(R.id.sv_artist);
    }


    public void initViewPager(){
//        musicPagerAdapter = new MusicPagerAdapter(getSupportFragmentManager());
        musicPagerAdapter = new MusicPagerAdapter(fragmentManager);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(false);
        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //Make the Title of actionbar centered
        textview = new TextView(getApplicationContext());
        layoutparams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        textview.setLayoutParams(layoutparams);
        textview.setText(R.string.app_name);
        textview.setTextColor(Color.WHITE);
        textview.setGravity(Gravity.CENTER);
        textview.setTextSize(20);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(textview);


        //Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(musicPagerAdapter);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

 /*       mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });*/
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
              // actionBar.setSelectedNavigationItem(position);
              // actionBar.setScrollPosition(position,0f,true);
            }

            @Override
            public void onPageSelected(int position) {
               // actionBar.setSelectedNavigationItem(position);
               actionBar.getTabAt(position).select();
              //  mViewPager.setCurrentItem(position,false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

      //  actionBar.setupWithViewPager(this.mViewPager);


        // Add 3 tabs, specifying the tab's text and TabListener
        for (int i = 0; i < musicPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(musicPagerAdapter.getPageTitle(i).toString())
                            .setTabListener(this)
            );
        }




    }

 /*   @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }*/
/*
        @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
    }*/
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }



    //fragmentstatePageAdapter manage fragments page
    public class MusicPagerAdapter extends FragmentStatePagerAdapter {
        private SparseArray<Fragment> array = new SparseArray<>();
        public MusicPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {


            switch (position) {
                case 0: {
                    Fragment searchArtistByFragment = new SearchByArtistFragment();
           /*         fragmentManager.beginTransaction()
                            .add(R.id.fragment_container, searchArtistByFragment)
                            .disallowAddToBackStack()
                            .commit();*/
                    array.setValueAt(0, searchArtistByFragment);
                    return searchArtistByFragment;
                }
                case 1: {
                    Fragment searchAlbumByArtistFragment = new SearchAlbumByArtistFragment();
              /*      fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, searchAlbumByArtistFragment)
                            .disallowAddToBackStack()
                            .commit();*/
                    array.setValueAt(1, searchAlbumByArtistFragment);
                    return searchAlbumByArtistFragment;
                }
                case 2: {
                    Fragment topTenLovedTracksByArtist = new SearchTopTenLovedTracksByArtist();
         /*           fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, topTenLovedTracksByArtist)
                            .disallowAddToBackStack()
                            .commit();*/
                    array.setValueAt(2, topTenLovedTracksByArtist);
                    return topTenLovedTracksByArtist;
                }

                default:
                    throw new RuntimeException("Invalid Count for pager adapter");
            }


        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Artist Details";
                case 1:
                    return "Album Details";
                case 2:
                    return "Top Ten";
            }
            return "OBJECT " + (position + 1);
        }

        public SparseArray<Fragment> getFragmentArray() {
            return array;
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }


    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

/*    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("onSaveInstanceStateSArt", "onSaveInstanceState_SearchArti");
        CharSequence searchView = m_Sv_Artist.getQuery();
        outState.putCharSequence("savedSearchViewQuery", searchView);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("onRestoreInstanceState", "onRestoreInstanceState");
        CharSequence searchViewQuery = (CharSequence) savedInstanceState.get("savedSearchViewQuery");
        MainActivity.getM_Sv_Artist().setQuery(searchViewQuery, true);

    }*/

}
