package com.adw.udptest2;

import android.os.Handler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Arrays;

class MulticastListenerThread extends MulticastThread {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    MulticastListenerThread(MainActivity mainActivity, String str, int i) {
        super("MulticastListenerThread", mainActivity, str, i, new Handler());
    }

    //private static String bytesToHex(byte[] bArr) {
        //char[] cArr = new char[(bArr.length * 2)];
        //for (int i = 0; i < bArr.length; i++) {
            //byte b = bArr[i] & 255;
            //cArr[i * 2] = HEX_ARRAY[b >>> 4];
            //cArr[(i * 2) + 1] = HEX_ARRAY[b & 15];
        //}
        //StringBuilder sb = new StringBuilder();
        //for (int i2 = 0; i2 < cArr.length; i2 += 2) {
            //sb.append("0x").append(cArr[i2]).append(cArr[i2 + 1]).append(" ");
        //}
        //return sb.toString();
    //}

    /* access modifiers changed from: 0000 */
    /* renamed from: lambda$-net_liveforcode_multicasttester_MulticastListenerThread_lambda$1 */
    public /* synthetic */ void mo7932x2aa06d2c(String str) {
        this.activity.outputTextToConsole(str);
    }

    public void run() {
        super.run();
        DatagramPacket datagramPacket = new DatagramPacket(new byte[512], 512);
        while (this.running.get()) {
            datagramPacket.setData(new byte[1024]);
            try {
                if (this.multicastSocket == null) {
                    break;
                }
                this.multicastSocket.receive(datagramPacket);
                //String trim = this.activity.isDisplayedInHex() ? "" + bytesToHex(Arrays.copyOf(datagramPacket.getData(), datagramPacket.getLength())) : new String(datagramPacket.getData()).trim();
                this.activity.log("Received! ");// + trim);
                this.handler.post(new $Lambda$1(this, "[" + (getLocalIP().equals(datagramPacket.getAddress().getHostAddress()) ? "You" : datagramPacket.getAddress().getHostAddress()) + "] " + "\n"));//+ trim + "\n"));
            } catch (IOException e) {
            }
        }
        if (this.multicastSocket != null) {
            this.multicastSocket.close();
        }
    }
}