package com.sointeractive.getresults.app.data;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.sointeractive.getresults.app.config.Settings;
import com.sointeractive.getresults.app.data.isaacloud.Location;
import com.sointeractive.getresults.app.data.isaacloud.LoginData;
import com.sointeractive.getresults.app.data.isaacloud.Person;
import com.sointeractive.getresults.app.data.isaacloud.UserData;
import com.sointeractive.getresults.app.pebble.PebbleConnector;
import com.sointeractive.getresults.app.pebble.cache.LoginCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.sointeractive.isaacloud.Isaacloud;
import pl.sointeractive.isaacloud.exceptions.InvalidConfigException;

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();

    private static PebbleConnector pebbleConnector;
    private static Isaacloud isaacloudConnector;
    private static App obj;
    private static FileManager fileManager;
    private static DataManager dataManager;
    private static EventManager eventManager;

    @SuppressWarnings("WeakerAccess")
    public App() {
        initPebbleConnector();
    }

    public static PebbleConnector getPebbleConnector() {
        return pebbleConnector;
    }

    public static DataManager getDataManager() {
        return dataManager;
    }

    public static EventManager getEventManager() {
        return eventManager;
    }

    public static void saveUserData(final UserData userData) {
        fileManager.saveUserData(userData, obj);
        LoginCache.INSTANCE.findChanges();
    }

    public static UserData loadUserData() {
        return fileManager.loadUserData(obj);
    }

    public static void saveLoginData(final LoginData data) {
        fileManager.saveLoginData(data, obj);
    }

    public static LoginData loadLoginData() {
        return fileManager.loadLoginData(obj);
    }

    public static String loadConfigData() {
        return fileManager.loadConfigData(obj);
    }

    public static void saveConfigData(final String data) {
        fileManager.saveConfigData(data, obj);
    }

    public static App getInstance() {
        return obj;
    }

    public static Isaacloud getIsaacloudConnector() {
        return isaacloudConnector;
    }

    public static void setIsaacloudConnector(final Isaacloud isaacloudConnector) {
        App.isaacloudConnector = isaacloudConnector;
    }

    public static boolean isOnline() {
        final ConnectivityManager cm =
                (ConnectivityManager) obj.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static List<Location> getLocations() {
        return dataManager.getLocations();
    }

    public static List<Person> getPeopleAtLocation(final Location l) {
        return dataManager.getPeopleAtLocation(l);
    }

    private void initPebbleConnector() {
        pebbleConnector = new PebbleConnector(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        obj = this;
        Log.d(TAG, "Action: Create managers");
        fileManager = new FileManager();
        eventManager = new EventManager();
        dataManager = new DataManager();
    }

}