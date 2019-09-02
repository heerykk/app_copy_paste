package android.support.p003v7.app;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.p000v4.content.PermissionChecker;
import android.util.Log;
import java.util.Calendar;

/* renamed from: android.support.v7.app.TwilightManager */
class TwilightManager {
    private static final int SUNRISE = 6;
    private static final int SUNSET = 22;
    private static final String TAG = "TwilightManager";
    private static TwilightManager sInstance;
    private final Context mContext;
    private final LocationManager mLocationManager;
    private final TwilightState mTwilightState = new TwilightState();

    /* renamed from: android.support.v7.app.TwilightManager$TwilightState */
    private static class TwilightState {
        boolean isNight;
        long nextUpdate;
        long todaySunrise;
        long todaySunset;
        long tomorrowSunrise;
        long yesterdaySunset;

        TwilightState() {
        }
    }

    static TwilightManager getInstance(@NonNull Context context) {
        Context context2 = context;
        Context context3 = context2;
        if (sInstance == null) {
            Context context4 = context2.getApplicationContext();
            sInstance = new TwilightManager(context4, (LocationManager) context4.getSystemService("location"));
        }
        return sInstance;
    }

    @VisibleForTesting
    static void setInstance(TwilightManager twilightManager) {
        TwilightManager twilightManager2 = twilightManager;
        TwilightManager twilightManager3 = twilightManager2;
        sInstance = twilightManager2;
    }

    @VisibleForTesting
    TwilightManager(@NonNull Context context, @NonNull LocationManager locationManager) {
        Context context2 = context;
        LocationManager locationManager2 = locationManager;
        Context context3 = context2;
        LocationManager locationManager3 = locationManager2;
        this.mContext = context2;
        this.mLocationManager = locationManager2;
    }

    /* access modifiers changed from: 0000 */
    public boolean isNight() {
        boolean z;
        TwilightState state = this.mTwilightState;
        if (isStateValid()) {
            return state.isNight;
        }
        Location lastKnownLocation = getLastKnownLocation();
        Location location = lastKnownLocation;
        if (lastKnownLocation == null) {
            int i = Log.i(TAG, "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
            Calendar instance = Calendar.getInstance();
            Calendar calendar = instance;
            int i2 = instance.get(11);
            int hour = i2;
            if (i2 >= 6 && hour < 22) {
                z = false;
            } else {
                z = true;
            }
            return z;
        }
        updateState(location);
        return state.isNight;
    }

    private Location getLastKnownLocation() {
        Location location;
        Location coarseLoc = null;
        Location fineLoc = null;
        int checkSelfPermission = PermissionChecker.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION");
        int i = checkSelfPermission;
        if (checkSelfPermission == 0) {
            coarseLoc = getLastKnownLocationForProvider("network");
        }
        int checkSelfPermission2 = PermissionChecker.checkSelfPermission(this.mContext, "android.permission.ACCESS_FINE_LOCATION");
        int permission = checkSelfPermission2;
        if (checkSelfPermission2 == 0) {
            fineLoc = getLastKnownLocationForProvider("gps");
        }
        if (fineLoc == null || coarseLoc == null) {
            if (fineLoc == null) {
                location = coarseLoc;
            } else {
                location = fineLoc;
            }
            return location;
        }
        return !((fineLoc.getTime() > coarseLoc.getTime() ? 1 : (fineLoc.getTime() == coarseLoc.getTime() ? 0 : -1)) <= 0) ? fineLoc : coarseLoc;
    }

    private Location getLastKnownLocationForProvider(String str) {
        String provider = str;
        String str2 = provider;
        if (this.mLocationManager != null) {
            try {
                if (this.mLocationManager.isProviderEnabled(provider)) {
                    return this.mLocationManager.getLastKnownLocation(provider);
                }
            } catch (Exception e) {
                Exception exc = e;
                int d = Log.d(TAG, "Failed to get last known location", e);
            }
        }
        return null;
    }

    private boolean isStateValid() {
        boolean z;
        if (this.mTwilightState != null) {
            if (!(this.mTwilightState.nextUpdate <= System.currentTimeMillis())) {
                z = true;
                return z;
            }
        }
        z = false;
        return z;
    }

    private void updateState(@NonNull Location location) {
        long nextUpdate;
        boolean z;
        boolean z2;
        long nextUpdate2;
        Location location2 = location;
        Location location3 = location2;
        TwilightState state = this.mTwilightState;
        long currentTimeMillis = System.currentTimeMillis();
        long now = currentTimeMillis;
        TwilightCalculator instance = TwilightCalculator.getInstance();
        TwilightCalculator calculator = instance;
        instance.calculateTwilight(currentTimeMillis - 86400000, location2.getLatitude(), location2.getLongitude());
        long yesterdaySunset = calculator.sunset;
        calculator.calculateTwilight(now, location2.getLatitude(), location2.getLongitude());
        boolean isNight = calculator.state == 1;
        long todaySunrise = calculator.sunrise;
        long todaySunset = calculator.sunset;
        calculator.calculateTwilight(now + 86400000, location2.getLatitude(), location2.getLongitude());
        long tomorrowSunrise = calculator.sunrise;
        if (todaySunrise == -1 || todaySunset == -1) {
            nextUpdate = now + 43200000;
        } else {
            if (now <= todaySunset) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                nextUpdate2 = 0 + tomorrowSunrise;
            } else {
                if (now <= todaySunrise) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (!z2) {
                    nextUpdate2 = 0 + todaySunset;
                } else {
                    nextUpdate2 = 0 + todaySunrise;
                }
            }
            nextUpdate = nextUpdate2 + 60000;
        }
        state.isNight = isNight;
        state.yesterdaySunset = yesterdaySunset;
        state.todaySunrise = todaySunrise;
        state.todaySunset = todaySunset;
        state.tomorrowSunrise = tomorrowSunrise;
        state.nextUpdate = nextUpdate;
    }
}
