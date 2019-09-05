package com.adw.cpe;

import android.os.Message;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class UdpClientThread extends Thread{

    String dstAddress;
    String message;
    String Copyied;
    int dstPort;
    private boolean running;
    MainActivity activity;
    InetAddress inetAddress;
    MainActivity.UdpClientHandler handler;
    MulticastSocket multicastSocket;

    DatagramSocket socket;
    InetAddress address;

    public UdpClientThread(String text, MainActivity.UdpClientHandler handler) {
        super();
        Copyied = text;
        this.handler = handler;
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    private void sendState(String state){
        handler.sendMessage(
                Message.obtain(handler,
                        MainActivity.UdpClientHandler.UPDATE_STATE, state));
    }

    @Override
    public void run() {
        sendState("connecting...");

        running = true;

        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("224.3.29.71");

            String massage = Copyied;
            byte[] buf = massage.getBytes();

            multicastSocket = new MulticastSocket(10000);
            multicastSocket.joinGroup(address);
            multicastSocket.setSoTimeout(100);
            multicastSocket.setTimeToLive(8);
            multicastSocket.send(new DatagramPacket(buf, buf.length, address, 10000));
            multicastSocket.close();
            sendState("connected");

        } catch (Exception e) {
            sendState(e.getMessage());
            e.printStackTrace();

        } finally {
            if(socket != null){
                socket.close();
                handler.sendEmptyMessage(MainActivity.UdpClientHandler.UPDATE_END);
            }
        }
    }
}