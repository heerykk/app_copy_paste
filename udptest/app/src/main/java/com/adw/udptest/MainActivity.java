package com.adw.udptest;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.support.p000v4.internal.view.SupportMenu;
import android.support.p003v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.logging.Logger;
import com.adw.udptest.receivers.WifiMonitoringReceiver;

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
    private Toolbar toolbar;
    private MulticastLock wifiLock;
    private WifiMonitoringReceiver wifiMonitoringReceiver;

    private void clearConsole() {
        this.consoleView.setText("");
        this.consoleView.setTextColor(getResources().getColor(C0370R.color.colorPrimaryDark));
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
            WifiManager wifiManager = (WifiManager) getSystemService("wifi");
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
        if (!((ConnectivityManager) getSystemService("connectivity")).getNetworkInfo(1).isConnected()) {
            outputErrorToConsole("Error: You are not connected to a WiFi network!");
        } else if (validateInputFields()) {
            setWifiLockAcquired(true);
            if (this.errorDisplayed) {
                clearConsole();
                this.errorDisplayed = false;
            }
            this.multicastListenerThread = new MulticastListenerThread(this, getMulticastIP(), getMulticastPort());
            this.multicastListenerThread.start();
            this.isListening = true;
            updateButtonStates();
        }
    }

    private void stopThreads() {
        if (this.multicastListenerThread != null) {
            this.multicastListenerThread.stopRunning();
        }
        if (this.multicastSenderThread != null) {
            this.multicastSenderThread.interrupt();
        }
    }

    private void updateButtonStates() {
        ((Button) findViewById(C0370R.C0372id.startListeningButton)).setText(this.isListening ? C0370R.string.stop_listening : C0370R.string.start_listening);
        findViewById(C0370R.C0372id.sendMessageButton).setEnabled(this.isListening);
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
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (view.getId() == C0370R.C0372id.startListeningButton) {
            if (this.isListening) {
                stopListening();
            } else {
                startListening();
            }
        } else if (view.getId() == C0370R.C0372id.clearConsoleButton) {
            clearConsole();
        } else if (view.getId() == C0370R.C0372id.sendMessageButton) {
            sendMulticastMessage(getMessageToSend());
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0370R.layout.activity_main);
        this.toolbar = (Toolbar) findViewById(C0370R.C0372id.toolbar);
        setSupportActionBar(this.toolbar);
        this.toolbar.setLogo((int) C0370R.mipmap.ic_launcher);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0370R.C0373menu.menu_main, menu);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.isListening) {
            stopListening();
        }
        stopThreads();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return super.onOptionsItemSelected(menuItem);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.multicastIPField = (EditText) findViewById(C0370R.C0372id.multicastIP);
        this.multicastPortField = (EditText) findViewById(C0370R.C0372id.multicastPort);
        this.consoleView = (TextView) findViewById(C0370R.C0372id.consoleTextView);
        this.messageToSendField = (EditText) findViewById(C0370R.C0372id.messageToSend);
        setWifiMonitorRegistered(true);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        setWifiMonitorRegistered(false);
    }

    public void onToggleHexCheckbox(View view) {
        if (view.getId() == C0370R.C0372id.hexDisplayCheckBox) {
            this.isDisplayedInHex = ((CheckBox) view).isChecked();
        }
    }

    public void onWifiDisconnected() {
        if (this.isListening) {
            stopListening();
            outputErrorToConsole("Error: WiFi has been disconnected. Listening has stopped.");
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

    /* access modifiers changed from: 0000 */
    public void stopListening() {
        if (this.isListening) {
            this.isListening = false;
            updateButtonStates();
            stopThreads();
            setWifiLockAcquired(false);
        }
    }
}
