package com.adw.udptest.receivers.;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import com.adw.udptest.MainActivity;

public class WifiMonitoringReceiver extends BroadcastReceiver {
    private MainActivity mainActivity;

    public WifiMonitoringReceiver(MainActivity mainActivity2) {
        this.mainActivity = mainActivity2;
    }

    public void onReceive(Context context, Intent intent) {
        if (this.mainActivity != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            this.mainActivity.log("Checking Wifi...");
            if (!connectivityManager.getNetworkInfo(1).isConnected()) {
                this.mainActivity.log("Wifi is not enabled!");
                this.mainActivity.onWifiDisconnected();
            }
        }
    }
}
