package com.adw.udptest2;

import android.os.Handler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class MulticastSenderThread extends MulticastThread {
    private String messageToSend;

    public MulticastSenderThread(MainActivity mainActivity, String str, int i, String str2) {
        super("MulticastSenderThread", mainActivity, str, i, new Handler());
        this.messageToSend = str2;
    }

    public void run() {
        super.run();
        try {
            byte[] bytes = this.messageToSend.getBytes();
            this.multicastSocket.send(new DatagramPacket(bytes, bytes.length, InetAddress.getByName(this.multicastIP), this.multicastPort));
            this.multicastSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}