package com.sointeractive.getresults.app.pebble.checker;

import android.util.Log;

import com.google.common.collect.Sets;
import com.sointeractive.getresults.app.data.App;
import com.sointeractive.getresults.app.pebble.responses.ResponseItem;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BeaconsInfoChangeChecker {
    private static final String TAG = BeaconsInfoChangeChecker.class.getSimpleName();

    public static void check(final Collection<ResponseItem> oldBeacons, final Collection<ResponseItem> newBeacons) {
        final Set<ResponseItem> changedBeacons = getChangedBeacons(oldBeacons, newBeacons);
        if (!changedBeacons.isEmpty()) {
            Log.i(TAG, "Checker: Beacons info changed");
            App.getPebbleConnector().sendDataToPebble(changedBeacons);
        }
    }

    private static Set<ResponseItem> getChangedBeacons(final Collection<ResponseItem> oldBeacons, final Collection<ResponseItem> newBeacons) {
        final Set<ResponseItem> oldBeaconsSet = new HashSet<ResponseItem>(oldBeacons);
        final Set<ResponseItem> newBeaconsSet = new HashSet<ResponseItem>(newBeacons);
        return Sets.difference(newBeaconsSet, oldBeaconsSet).immutableCopy();
    }
}
