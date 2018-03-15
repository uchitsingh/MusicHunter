package com.codepath.musichunter.controller;





import android.util.Log;

import com.codepath.musichunter.model.data.network.model.searchbyartist.Artist;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by uchit on 11/02/2018.
 * 
 */

public class RealmHelper {
    Realm realm;
    
    public RealmHelper(Realm realm ){
        this.realm = realm;
    }
    
    /*
    Make sure the function runs on seperate Thread when saving the data to Realm
    uses Executor Framework.
     */
    public void saveArtists(final Artist result){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Log.i("SaveDATAA","SAAVEEDATATAA");
                realm.copyToRealm(result);
            }
        });
    }
    
    /*
    Read Genre Data from Realm Database
     */
   public ArrayList<Artist> getArtists(){
       ArrayList<Artist> genreResults = new ArrayList<>();
       RealmResults<Artist> realmResults = realm.where(Artist.class).findAll();
       // realmResults.de
       for(Artist result :realmResults){
           genreResults.add(result);
       }
       return genreResults;
   }
    
    
}
