package android.support.p000v4.net;

import android.os.Build.VERSION;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

/* renamed from: android.support.v4.net.TrafficStatsCompat */
public final class TrafficStatsCompat {
    private static final TrafficStatsCompatImpl IMPL;

    /* renamed from: android.support.v4.net.TrafficStatsCompat$Api24TrafficStatsCompatImpl */
    static class Api24TrafficStatsCompatImpl extends IcsTrafficStatsCompatImpl {
        Api24TrafficStatsCompatImpl() {
        }

        public void tagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
            DatagramSocket socket = datagramSocket;
            DatagramSocket datagramSocket2 = socket;
            TrafficStatsCompatApi24.tagDatagramSocket(socket);
        }

        public void untagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
            DatagramSocket socket = datagramSocket;
            DatagramSocket datagramSocket2 = socket;
            TrafficStatsCompatApi24.untagDatagramSocket(socket);
        }
    }

    /* renamed from: android.support.v4.net.TrafficStatsCompat$BaseTrafficStatsCompatImpl */
    static class BaseTrafficStatsCompatImpl implements TrafficStatsCompatImpl {
        private ThreadLocal<SocketTags> mThreadSocketTags = new ThreadLocal<SocketTags>(this) {
            final /* synthetic */ BaseTrafficStatsCompatImpl this$0;

            {
                BaseTrafficStatsCompatImpl this$02 = r5;
                BaseTrafficStatsCompatImpl baseTrafficStatsCompatImpl = this$02;
                this.this$0 = this$02;
            }

            /* access modifiers changed from: protected */
            public SocketTags initialValue() {
                return new SocketTags();
            }
        };

        /* renamed from: android.support.v4.net.TrafficStatsCompat$BaseTrafficStatsCompatImpl$SocketTags */
        private static class SocketTags {
            public int statsTag = -1;

            SocketTags() {
            }
        }

        BaseTrafficStatsCompatImpl() {
        }

        public void clearThreadStatsTag() {
            ((SocketTags) this.mThreadSocketTags.get()).statsTag = -1;
        }

        public int getThreadStatsTag() {
            return ((SocketTags) this.mThreadSocketTags.get()).statsTag;
        }

        public void incrementOperationCount(int i) {
            int i2 = i;
        }

        public void incrementOperationCount(int i, int i2) {
            int i3 = i;
            int i4 = i2;
        }

        public void setThreadStatsTag(int i) {
            int tag = i;
            int i2 = tag;
            ((SocketTags) this.mThreadSocketTags.get()).statsTag = tag;
        }

        public void tagSocket(Socket socket) {
            Socket socket2 = socket;
        }

        public void untagSocket(Socket socket) {
            Socket socket2 = socket;
        }

        public void tagDatagramSocket(DatagramSocket datagramSocket) {
            DatagramSocket datagramSocket2 = datagramSocket;
        }

        public void untagDatagramSocket(DatagramSocket datagramSocket) {
            DatagramSocket datagramSocket2 = datagramSocket;
        }
    }

    /* renamed from: android.support.v4.net.TrafficStatsCompat$IcsTrafficStatsCompatImpl */
    static class IcsTrafficStatsCompatImpl implements TrafficStatsCompatImpl {
        IcsTrafficStatsCompatImpl() {
        }

        public void clearThreadStatsTag() {
            TrafficStatsCompatIcs.clearThreadStatsTag();
        }

        public int getThreadStatsTag() {
            return TrafficStatsCompatIcs.getThreadStatsTag();
        }

        public void incrementOperationCount(int i) {
            int operationCount = i;
            int i2 = operationCount;
            TrafficStatsCompatIcs.incrementOperationCount(operationCount);
        }

        public void incrementOperationCount(int i, int i2) {
            int tag = i;
            int operationCount = i2;
            int i3 = tag;
            int i4 = operationCount;
            TrafficStatsCompatIcs.incrementOperationCount(tag, operationCount);
        }

        public void setThreadStatsTag(int i) {
            int tag = i;
            int i2 = tag;
            TrafficStatsCompatIcs.setThreadStatsTag(tag);
        }

        public void tagSocket(Socket socket) throws SocketException {
            Socket socket2 = socket;
            Socket socket3 = socket2;
            TrafficStatsCompatIcs.tagSocket(socket2);
        }

        public void untagSocket(Socket socket) throws SocketException {
            Socket socket2 = socket;
            Socket socket3 = socket2;
            TrafficStatsCompatIcs.untagSocket(socket2);
        }

        public void tagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
            DatagramSocket socket = datagramSocket;
            DatagramSocket datagramSocket2 = socket;
            TrafficStatsCompatIcs.tagDatagramSocket(socket);
        }

        public void untagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
            DatagramSocket socket = datagramSocket;
            DatagramSocket datagramSocket2 = socket;
            TrafficStatsCompatIcs.untagDatagramSocket(socket);
        }
    }

    /* renamed from: android.support.v4.net.TrafficStatsCompat$TrafficStatsCompatImpl */
    interface TrafficStatsCompatImpl {
        void clearThreadStatsTag();

        int getThreadStatsTag();

        void incrementOperationCount(int i);

        void incrementOperationCount(int i, int i2);

        void setThreadStatsTag(int i);

        void tagDatagramSocket(DatagramSocket datagramSocket) throws SocketException;

        void tagSocket(Socket socket) throws SocketException;

        void untagDatagramSocket(DatagramSocket datagramSocket) throws SocketException;

        void untagSocket(Socket socket) throws SocketException;
    }

    static {
        if ("N".equals(VERSION.CODENAME)) {
            IMPL = new Api24TrafficStatsCompatImpl();
        } else if (VERSION.SDK_INT < 14) {
            IMPL = new BaseTrafficStatsCompatImpl();
        } else {
            IMPL = new IcsTrafficStatsCompatImpl();
        }
    }

    public static void clearThreadStatsTag() {
        IMPL.clearThreadStatsTag();
    }

    public static int getThreadStatsTag() {
        return IMPL.getThreadStatsTag();
    }

    public static void incrementOperationCount(int i) {
        int operationCount = i;
        int i2 = operationCount;
        IMPL.incrementOperationCount(operationCount);
    }

    public static void incrementOperationCount(int i, int i2) {
        int tag = i;
        int operationCount = i2;
        int i3 = tag;
        int i4 = operationCount;
        IMPL.incrementOperationCount(tag, operationCount);
    }

    public static void setThreadStatsTag(int i) {
        int tag = i;
        int i2 = tag;
        IMPL.setThreadStatsTag(tag);
    }

    public static void tagSocket(Socket socket) throws SocketException {
        Socket socket2 = socket;
        Socket socket3 = socket2;
        IMPL.tagSocket(socket2);
    }

    public static void untagSocket(Socket socket) throws SocketException {
        Socket socket2 = socket;
        Socket socket3 = socket2;
        IMPL.untagSocket(socket2);
    }

    public static void tagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
        DatagramSocket socket = datagramSocket;
        DatagramSocket datagramSocket2 = socket;
        IMPL.tagDatagramSocket(socket);
    }

    public static void untagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
        DatagramSocket socket = datagramSocket;
        DatagramSocket datagramSocket2 = socket;
        IMPL.untagDatagramSocket(socket);
    }

    private TrafficStatsCompat() {
    }
}
