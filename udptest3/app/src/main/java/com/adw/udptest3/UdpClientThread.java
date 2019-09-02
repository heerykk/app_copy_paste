package com.adw.udptest3;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpClientThread extends Thread{

    String dstAddress;
    String message;
    int dstPort;
    private boolean running;
    MainActivity activity;
    InetAddress inetAddress;
    MainActivity.UdpClientHandler handler;
    MulticastSocket multicastSocket;

    DatagramSocket socket;
    InetAddress address;

    public UdpClientThread(String addr, int port, MainActivity.UdpClientHandler handler) {
        super();
        dstAddress = addr;
        dstPort = port;
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
            address = InetAddress.getByName(dstAddress);
//            int ipAddress = ((WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getIpAddress();
//            inetAddress = InetAddress.getByAddress(new byte[]{(byte) (ipAddress & 255), (byte) ((ipAddress >> 8) & 255), (byte) ((ipAddress >> 16) & 255), (byte) ((ipAddress >> 24) & 255)});
//            NetworkInterface byInetAddress = NetworkInterface.getByInetAddress(inetAddress);

            // send request
            //byte[] buf = new byte[256];
            String massage = "test henry";
            byte[] buf = massage.getBytes();
//            DatagramPacket packet =
//                    new DatagramPacket(buf, buf.length, address, dstPort);
//            socket.send(packet);

            multicastSocket = new MulticastSocket(dstPort);
           // multicastSocket.setNetworkInterface(byInetAddress);
            multicastSocket.joinGroup(address);
            multicastSocket.setSoTimeout(100);
            multicastSocket.setTimeToLive(8);
            multicastSocket.send(new DatagramPacket(buf, buf.length, address, dstPort));
            multicastSocket.close();
            sendState("connected");

            // get response
//            packet = new DatagramPacket(buf, buf.length);
//
//
//            socket.receive(packet);
//            String line = new String(packet.getData(), 0, packet.getLength());
//
//            handler.sendMessage(
//                    Message.obtain(handler, MainActivity.UdpClientHandler.UPDATE_MSG, line));

        } catch (Exception e) {
            sendState(e.getMessage());
            e.printStackTrace();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
        } finally {
            if(socket != null){
                socket.close();
                handler.sendEmptyMessage(MainActivity.UdpClientHandler.UPDATE_END);
            }
        }
    }
}
