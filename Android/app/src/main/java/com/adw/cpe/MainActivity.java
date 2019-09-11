package com.adw.cpe;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;


public class MainActivity extends AppCompatActivity {
    EditText ed1, ed2;
    Button b1, b2;

    // Declair varibles for sending
    Button buttonConnect;
    TextView infoIp, infoPort;
    TextView textViewState, textViewPrompt, textViewRx;
    UdpClientHandler udpClientHandler;
    UdpClientThread udpClientThread;

    MulticastSocket multicastSocket;
    static final int UdpServerPORT = 10000;
    UdpServerThread udpServerThread;

    // Declare variables for the clipboard
    private ClipboardManager myClipboard;
    private ClipData myClip;

    // This class is run when the application is opened
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed2 = (EditText) findViewById(R.id.editText2);
        ed1 = (EditText) findViewById(R.id.editText);
        b2 = (Button) findViewById(R.id.button2);
        b1 = (Button) findViewById(R.id.button);

        // variables for sending
        buttonConnect = (Button) findViewById(R.id.connect);
        textViewState = (TextView)findViewById(R.id.state);
        textViewRx = (TextView)findViewById(R.id.received);

        infoIp = (TextView) findViewById(R.id.infoip);
        infoPort = (TextView) findViewById(R.id.infoport);
        textViewPrompt = (TextView)findViewById(R.id.prompt);
        infoIp.setText(getIpAddress());
        infoPort.setText(String.valueOf(UdpServerPORT));

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);

        udpClientHandler = new UdpClientHandler(this);

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        // Runs when the copy button is pressed and will copy text to clipboard
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String text;
                text = ed1.getText().toString();

                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(getApplicationContext(), "Text Copied",
                        Toast.LENGTH_SHORT).show();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ClipData abc = myClipboard.getPrimaryClip();
                ClipData.Item item = abc.getItemAt(0);

                String text = item.getText().toString();
                ed2.setText(text);

                Toast.makeText(getApplicationContext(), "Text Pasted",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // All ways running to react when clipboard changes
        final ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.addPrimaryClipChangedListener( new ClipboardManager.OnPrimaryClipChangedListener() {
            public void onPrimaryClipChanged() {
                String text = clipboard.getText().toString();
                udpClientThread = new UdpClientThread(text,
                        udpClientHandler);
                udpClientThread.start();
                Toast.makeText(getBaseContext(),"Copy:\n"+text,Toast.LENGTH_LONG).show();
            }
        });
    }
    View.OnClickListener buttonConnectOnClickListener =
            new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    ClipData abc = myClipboard.getPrimaryClip();
                    ClipData.Item item = abc.getItemAt(0);
                    String text = item.getText().toString();

                    //udpClientThread = new UdpClientThread(text,
                      //      udpClientHandler);
                    //udpClientThread.start();

                    buttonConnect.setEnabled(false);
                }
            };

    // method to display massgas on screen
    private void updateStateC(String state){
        textViewState.setText(state);
    }

    private void updateRxMsg(String rxmsg){
        textViewRx.append(rxmsg + "\n");
    }

    private void clientEnd(){
        udpClientThread = null;
        textViewState.setText("Sent");
        buttonConnect.setEnabled(true);

    }

    public static class UdpClientHandler extends Handler {
        public static final int UPDATE_STATE = 0;
        public static final int UPDATE_MSG = 1;
        public static final int UPDATE_END = 2;
        private MainActivity parent;

        public UdpClientHandler(MainActivity parent) {
            super();
            this.parent = parent;
        }
        
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case UPDATE_STATE:
                    parent.updateStateC((String)msg.obj);
                    break;
                case UPDATE_MSG:
                    parent.updateRxMsg((String)msg.obj);
                    break;
                case UPDATE_END:
                    parent.clientEnd();
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }

    //for udp server
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

    // class for the udp server
    private class UdpServerThread extends Thread{

        int serverPort;
        boolean running;

        public UdpServerThread(int serverPort) {
            super();
            this.serverPort = serverPort;
        }

        public void setRunning(boolean running){
            this.running = running;
        }

        @Override
        // This will run when the class is called
        public void run() {

            running = true;
            myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

            try {
                updateState("Starting UDP Server");
                multicastSocket = new MulticastSocket(serverPort);
                InetAddress castAddress = InetAddress.getByName("224.3.29.71");
                multicastSocket.joinGroup(castAddress);
                multicastSocket.setBroadcast(true);
                updateState("UDP Server is running");

                final ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.addPrimaryClipChangedListener( new ClipboardManager.OnPrimaryClipChangedListener() {
                    public void onPrimaryClipChanged() {
                        String a = clipboard.getText().toString();
                        Toast.makeText(getBaseContext(),"Copy:\n"+a,Toast.LENGTH_LONG).show();
                    }
                });

                while(running){
                    byte[] buf = new byte[256];

                    // receive request
                    DatagramPacket packet = new DatagramPacket(new byte[512], 512);
                    packet.setData(new byte[1024]);

                    multicastSocket.receive(packet);     //this code block the program flow
                    updateState("UDP Server test");
                    // send the response to the client at "address" and "port"
                    InetAddress address = packet.getAddress();
                    String hostIP = "/" + getIpAddress();
                    int port = packet.getPort();
                    String trim = new String(packet.getData());
                    updatePrompt("Request containing -" + trim + " from: " + address + ":" + port + "\n");
                    if (!address.toString().equals(hostIP)){
                        myClip = ClipData.newPlainText("text", trim);
                        myClipboard.setPrimaryClip(myClip);
                    }



                    String dString = new Date().toString() + "\n"
                            + "Your address " + address.toString() + ":" + String.valueOf(port);
                    buf = dString.getBytes();
                    packet = new DatagramPacket(buf, buf.length, address, port);
                    //multicastSocket.send(packet);
                }

            } catch (Exception e) {
                updateState(e.getMessage());
                e.printStackTrace();
            } finally {
                if(multicastSocket != null){
                    //multicastSocket.close();
                    run();
                    updateState("Closed port");
                }
            }
        }
    }

    // gets ip address as string
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
                        ip += inetAddress.getHostAddress();
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