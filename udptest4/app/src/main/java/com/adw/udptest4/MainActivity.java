package com.adw.udptest4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    private ClipboardManager myClipboard;
    private ClipData myClip;

    TextView infoIp, infoPort;
    TextView textViewState, textViewPrompt;
    MulticastSocket multicastSocket;

    static final int UdpServerPORT = 10000;

    UdpServerThread udpServerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoIp = (TextView) findViewById(R.id.infoip);
        infoPort = (TextView) findViewById(R.id.infoport);
        textViewState = (TextView)findViewById(R.id.state);
        textViewPrompt = (TextView)findViewById(R.id.prompt);

        infoIp.setText(getIpAddress());
        infoPort.setText(String.valueOf(UdpServerPORT));
    }

    @Override
    protected void onStart() {
        udpServerThread = new UdpServerThread(UdpServerPORT);
        udpServerThread.start();
        super.onStart();
    }

    @Override
    protected void onStop() {
        if(udpServerThread != null){
            udpServerThread.setRunning(false);
            udpServerThread = null;
        }

        super.onStop();
    }

    private void updateState(final String state){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewState.setText(state);
            }
        });
    }

    private void updatePrompt(final String prompt){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewPrompt.append(prompt);
            }
        });
    }

    private class UdpServerThread extends Thread{

        int serverPort;
        DatagramSocket socket;

        boolean running;

        public UdpServerThread(int serverPort) {
            super();
            this.serverPort = serverPort;
        }

        public void setRunning(boolean running){
            this.running = running;
        }

        @Override
        public void run() {

            running = true;
            myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//            @todo auto copy paste
//            final ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//            clipboard.addPrimaryClipChangedListener( new ClipboardManager.OnPrimaryClipChangedListener() {
//                public void onPrimaryClipChanged() {
//                    String a = clipboard.getText().toString();
//                    Toast.makeText(getBaseContext(),"Copy:\n"+a,Toast.LENGTH_LONG).show();
//                }
//            });

            try {
                updateState("Starting UDP Server");
                multicastSocket = new MulticastSocket(serverPort);
                InetAddress castAddress = InetAddress.getByName("224.3.29.71");
                multicastSocket.joinGroup(castAddress);
                multicastSocket.setBroadcast(true);
                updateState("UDP Server is running");
                Log.e(TAG, "UDP Server is running");

                while(running){
                    byte[] buf = new byte[256];

                    // receive request
                    DatagramPacket packet = new DatagramPacket(new byte[512], 512);
                    packet.setData(new byte[1024]);

                    multicastSocket.receive(packet);     //this code block the program flow
                    updateState("UDP Server test");
                    // send the response to the client at "address" and "port"
                    InetAddress address = packet.getAddress();
                    InetAddress hostIP = InetAddress.getLocalHost();
                    int port = packet.getPort();
                    String trim = new String(packet.getData());
                    updatePrompt("Request containing -" + trim + " from: " + address + ":" + port + "\n");
                    if (address != hostIP){
                        myClip = ClipData.newPlainText("text", trim);
                        myClipboard.setPrimaryClip(myClip);
                    }



                    String dString = new Date().toString() + "\n"
                            + "Your address " + address.toString() + ":" + String.valueOf(port);
                    buf = dString.getBytes();
                    packet = new DatagramPacket(buf, buf.length, address, port);
                    //multicastSocket.send(packet);
                }

                //Log.e(TAG, "UDP Server ended");

            } catch (Exception e) {
                updateState(e.getMessage());
                e.printStackTrace();
            } finally {
                if(multicastSocket != null){
                    //multicastSocket.close();
                    run();
                    Log.e(TAG, "socket.close()");
                    updateState("Closed port");
                }
            }
        }
    }

    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "SiteLocalAddress: " + inetAddress.getHostAddress() + "\n";
                    }
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }
}