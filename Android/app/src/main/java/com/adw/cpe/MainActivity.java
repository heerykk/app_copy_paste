package com.adw.cpe;

import android.content.ClipData;
import android.content.ClipboardManager;
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
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    EditText ed1, ed2;
    Button b1, b2;

    // Declair varibles for sending
    Button buttonConnect;
    TextView textViewState, textViewRx;
    UdpClientHandler udpClientHandler;
    UdpClientThread udpClientThread;

    private ClipboardManager myClipboard;
    private ClipData myClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed2 = (EditText) findViewById(R.id.editText2);
        ed1 = (EditText) findViewById(R.id.editText);
        b2 = (Button) findViewById(R.id.button2);
        b1 = (Button) findViewById(R.id.button);

        // veriables for sending
        buttonConnect = (Button) findViewById(R.id.connect);
        textViewState = (TextView)findViewById(R.id.state);
        textViewRx = (TextView)findViewById(R.id.received);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);

        udpClientHandler = new UdpClientHandler(this);

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

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
    }
    View.OnClickListener buttonConnectOnClickListener =
            new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    ClipData abc = myClipboard.getPrimaryClip();
                    ClipData.Item item = abc.getItemAt(0);
                    String text = item.getText().toString();

                    udpClientThread = new UdpClientThread(text,
                            udpClientHandler);
                    udpClientThread.start();

                    buttonConnect.setEnabled(false);
                }
            };
    private void updateState(String state){
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
                    parent.updateState((String)msg.obj);
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
}