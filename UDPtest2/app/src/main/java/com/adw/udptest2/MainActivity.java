package com.adw.udptest2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.internal.view.SupportMenu;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private TextView consoleView;
    private boolean errorDisplayed = false;
    private boolean isDisplayedInHex = false;
    private boolean isListening = false;
    private EditText messageToSendField;
    private EditText multicastIPField;
    private MulticastListenerThread multicastListenerThread;
    private EditText multicastPortField;
    private MulticastSenderThread multicastSenderThread;
    //private Toolbar toolbar;
    private WifiManager.MulticastLock wifiLock;
    private WifiMonitoringReceiver wifiMonitoringReceiver;

    private void clearConsole() {
        this.consoleView.setText("");
        this.consoleView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    private void sendMulticastMessage(String str) {
        log("Sending Message: " + str);
        if (this.isListening) {
            this.multicastSenderThread = new MulticastSenderThread(this, getMulticastIP(), getMulticastPort(), str);
            this.multicastSenderThread.start();
        }
    }

    private void setWifiLockAcquired(boolean z) {
        if (z) {
            if (this.wifiLock != null && this.wifiLock.isHeld()) {
                this.wifiLock.release();
            }
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                this.wifiLock = wifiManager.createMulticastLock("MulticastTester");
                this.wifiLock.acquire();
            }
        } else if (this.wifiLock != null && this.wifiLock.isHeld()) {
            this.wifiLock.release();
        }
    }

    private void setWifiMonitorRegistered(boolean z) {
        if (z) {
            if (this.wifiMonitoringReceiver != null) {
                unregisterReceiver(this.wifiMonitoringReceiver);
            }
            this.wifiMonitoringReceiver = new WifiMonitoringReceiver(this);
            registerReceiver(this.wifiMonitoringReceiver, new IntentFilter("android.net.wifi.STATE_CHANGE"));
        } else if (this.wifiMonitoringReceiver != null) {
            unregisterReceiver(this.wifiMonitoringReceiver);
            this.wifiMonitoringReceiver = null;
        }
    }

    private void startListening() {
        if (this.isListening) {
            return;
        }
        this.multicastListenerThread = new MulticastListenerThread(this, getMulticastIP(), getMulticastPort());
        this.multicastListenerThread.start();
        this.isListening = true;
        updateButtonStates();
    }

    private void updateButtonStates() {
        ((Button) findViewById(R.id.startListeningButton)).setText(this.isListening ? R.string.stop_listening : R.string.start_listening);
        findViewById(R.id.sendMessageButton).setEnabled(this.isListening);
    }

    private void stopThreads() {
        if (this.multicastListenerThread != null) {
            this.multicastListenerThread.stopRunning();
        }
        if (this.multicastSenderThread != null) {
            this.multicastSenderThread.interrupt();
        }
    }

    private boolean validateInputFields() {
        if (this.multicastIPField.getText().toString().isEmpty()) {
            outputErrorToConsole("Error: Multicast IP is Empty!");
            return false;
        }
        String[] split = this.multicastIPField.getText().toString().split("\\.");
        if (split.length != 4) {
            outputErrorToConsole("Error: Please enter an IP Address in this format: xxx.xxx.xxx.xxx\nEach 'xxx' segment may range from 0 to 255.");
            return false;
        }
        int i = 0;
        while (i < split.length) {
            try {
                int parseInt = Integer.parseInt(split[i]);
                if (i == 0 && (parseInt < 224 || parseInt > 239)) {
                    outputErrorToConsole("Error: Multicast IPs may only range from:\n224.0.0.0\nto\n239.255.255.255");
                    return false;
                } else if (parseInt > 255) {
                    outputErrorToConsole("Error: Please enter an IP Address in this format: xxx.xxx.xxx.xxx\nEach 'xxx' segment may range from 0 to 255.");
                    return false;
                } else {
                    i++;
                }
            } catch (NumberFormatException e) {
                outputErrorToConsole("Error: Multicast IP must contain only decimals and numbers.");
                return false;
            }
        }
        try {
            if (this.multicastPortField.getText().toString().isEmpty()) {
                outputErrorToConsole("Error: Multicast Port is Empty!");
                return false;
            } else if (Integer.parseInt(this.multicastPortField.getText().toString()) <= 65535) {
                return true;
            } else {
                outputErrorToConsole("Error: Multicast Port must be between 0 and 65535.");
                return false;
            }
        } catch (NumberFormatException e2) {
            outputErrorToConsole("Error: Multicast Port may only contain numbers.");
            return false;
        }
    }

    public String getMessageToSend() {
        return this.messageToSendField.getText().toString();
    }

    public String getMulticastIP() {
        return this.multicastIPField.getText().toString();
    }

    public int getMulticastPort() {
        return Integer.parseInt(this.multicastPortField.getText().toString());
    }

    public boolean isDisplayedInHex() {
        return this.isDisplayedInHex;
    }

    public void log(String str) {
        Logger.getLogger("MulticastTester").info(str);
    }

    public void onButton(View view) {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (view.getId() == R.id.startListeningButton) {
            if (this.isListening) {
                stopListening();
            } else {
                startListening();
            }
        } else if (view.getId() == R.id.clearConsoleButton) {
            clearConsole();
        } else if (view.getId() == R.id.sendMessageButton) {
            sendMulticastMessage(getMessageToSend());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.isListening) {
            stopListening();
        }
        stopThreads();
    }

    public void onStart() {
        super.onStart();
        this.multicastIPField = (EditText) findViewById(R.id.multicastIP);
        this.multicastPortField = (EditText) findViewById(R.id.multicastPort);
        this.consoleView = (TextView) findViewById(R.id.consoleTextView);
        this.messageToSendField = (EditText) findViewById(R.id.messageToSend);
    }

    public void onToggleHexCheckbox(View view) {
        if (view.getId() == R.id.hexDisplayCheckBox) {
            this.isDisplayedInHex = ((CheckBox) view).isChecked();
        }
    }

    public void outputErrorToConsole(String str) {
        clearConsole();
        outputTextToConsole(str);
        this.consoleView.setTextColor(SupportMenu.CATEGORY_MASK);
        this.errorDisplayed = true;
    }

    public void outputTextToConsole(String str) {
        if (this.errorDisplayed) {
            clearConsole();
            this.errorDisplayed = false;
        }
        this.consoleView.append(str);
        ScrollView scrollView = (ScrollView) this.consoleView.getParent();
        scrollView.post(new $Lambda$0(scrollView));
    }

    public void stopListening() {
        if (this.isListening) {
            this.isListening = false;
            updateButtonStates();
            stopThreads();
        }
    }
}
