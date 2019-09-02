package com.adw.udptest2;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.concurrent.atomic.AtomicBoolean;

class MulticastThread extends Thread {
    final MainActivity activity;
    final Handler handler;
    private InetAddress inetAddress;
    final String multicastIP;
    final int multicastPort;
    MulticastSocket multicastSocket;
    final AtomicBoolean running = new AtomicBoolean(true);

    MulticastThread(String str, MainActivity mainActivity, String str2, int i, Handler handler2) {
        super(str);
        this.activity = mainActivity;
        this.multicastIP = str2;
        this.multicastPort = i;
        this.handler = handler2;
    }

    private void outputErrorToConsole(final String str) {
        this.handler.post(new Runnable() {
            public void run() {
                MulticastThread.this.activity.outputErrorToConsole(str);
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public String getLocalIP() {
        return this.inetAddress.getHostAddress();
    }

    public void run() {
        try {
            int ipAddress = ((WifiManager) this.activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getIpAddress();
            this.inetAddress = InetAddress.getByAddress(new byte[]{(byte) (ipAddress & 255), (byte) ((ipAddress >> 8) & 255), (byte) ((ipAddress >> 16) & 255), (byte) ((ipAddress >> 24) & 255)});
            NetworkInterface byInetAddress = NetworkInterface.getByInetAddress(this.inetAddress);
            this.multicastSocket = new MulticastSocket(this.multicastPort);
            this.multicastSocket.setNetworkInterface(byInetAddress);
            this.multicastSocket.joinGroup(InetAddress.getByName(this.multicastIP));
            this.multicastSocket.setSoTimeout(100);
            this.multicastSocket.setTimeToLive(1);
        } catch (BindException e) {
            this.handler.post(new Runnable() {
                public void run() {
                    MulticastThread.this.activity.stopListening();
                }
            });
            String str = "Error: Cannot bind Address or Port.";
            if (this.multicastPort < 1024) {
                str = str + "\nTry binding to a port larger than 1024.";
            }
            outputErrorToConsole(str);
        } catch (IOException e2) {
            this.handler.post(new Runnable() {
                public void run() {
                    MulticastThread.this.activity.stopListening();
                }
            });
            outputErrorToConsole("Error: Cannot bind Address or Port.\nAn error occurred: " + e2.getMessage());
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: 0000 */
    public void stopRunning() {
        this.running.set(false);
    }
}
