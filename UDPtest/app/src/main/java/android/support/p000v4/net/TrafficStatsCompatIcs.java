package android.support.p000v4.net;

import android.annotation.TargetApi;
import android.net.TrafficStats;
import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v4.net.TrafficStatsCompatIcs */
class TrafficStatsCompatIcs {
    TrafficStatsCompatIcs() {
    }

    public static void clearThreadStatsTag() {
        TrafficStats.clearThreadStatsTag();
    }

    public static int getThreadStatsTag() {
        return TrafficStats.getThreadStatsTag();
    }

    public static void incrementOperationCount(int i) {
        int operationCount = i;
        int i2 = operationCount;
        TrafficStats.incrementOperationCount(operationCount);
    }

    public static void incrementOperationCount(int i, int i2) {
        int tag = i;
        int operationCount = i2;
        int i3 = tag;
        int i4 = operationCount;
        TrafficStats.incrementOperationCount(tag, operationCount);
    }

    public static void setThreadStatsTag(int i) {
        int tag = i;
        int i2 = tag;
        TrafficStats.setThreadStatsTag(tag);
    }

    public static void tagSocket(Socket socket) throws SocketException {
        Socket socket2 = socket;
        Socket socket3 = socket2;
        TrafficStats.tagSocket(socket2);
    }

    public static void untagSocket(Socket socket) throws SocketException {
        Socket socket2 = socket;
        Socket socket3 = socket2;
        TrafficStats.untagSocket(socket2);
    }

    public static void tagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
        DatagramSocket socket = datagramSocket;
        DatagramSocket datagramSocket2 = socket;
        ParcelFileDescriptor pfd = ParcelFileDescriptor.fromDatagramSocket(socket);
        TrafficStats.tagSocket(new DatagramSocketWrapper(socket, pfd.getFileDescriptor()));
        int detachFd = pfd.detachFd();
    }

    public static void untagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
        DatagramSocket socket = datagramSocket;
        DatagramSocket datagramSocket2 = socket;
        ParcelFileDescriptor pfd = ParcelFileDescriptor.fromDatagramSocket(socket);
        TrafficStats.untagSocket(new DatagramSocketWrapper(socket, pfd.getFileDescriptor()));
        int detachFd = pfd.detachFd();
    }
}
