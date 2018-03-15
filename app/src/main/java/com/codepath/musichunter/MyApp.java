package com.codepath.musichunter;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 *  This class is used to get a Single Instance of MyApp class, to access a global context.
 */

public class MyApp extends Application {

    private static MyApp sInstance;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        configRealm();
    }

    /**
     * @return the single instance of the MyApp Class
     */
    public static MyApp getInstance() {
        if (sInstance == null) {
            sInstance = new MyApp();
        }
        return sInstance;
    }

    /**
     * @return the global context
     */
    public Context getAppContext(){
        return context;
    }


    /**
     *  Function to allow configuration
     */
    public void configRealm(){

        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(1) //Droping is different than Deleting, If i have only one column, drop existing structure of table, add new one but not erasing the  value of the existing table
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);

    }

}
