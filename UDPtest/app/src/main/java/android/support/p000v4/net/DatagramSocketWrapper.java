package android.support.p000v4.net;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;

/* renamed from: android.support.v4.net.DatagramSocketWrapper */
class DatagramSocketWrapper extends Socket {

    /* renamed from: android.support.v4.net.DatagramSocketWrapper$DatagramSocketImplWrapper */
    private static class DatagramSocketImplWrapper extends SocketImpl {
        public DatagramSocketImplWrapper(DatagramSocket datagramSocket, FileDescriptor fileDescriptor) {
            DatagramSocket socket = datagramSocket;
            FileDescriptor fd = fileDescriptor;
            DatagramSocket datagramSocket2 = socket;
            FileDescriptor fileDescriptor2 = fd;
            this.localport = socket.getLocalPort();
            this.fd = fd;
        }

        /* access modifiers changed from: protected */
        public void accept(SocketImpl socketImpl) throws IOException {
            SocketImpl socketImpl2 = socketImpl;
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public int available() throws IOException {
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public void bind(InetAddress inetAddress, int i) throws IOException {
            InetAddress inetAddress2 = inetAddress;
            int i2 = i;
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public void close() throws IOException {
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public void connect(String str, int i) throws IOException {
            String str2 = str;
            int i2 = i;
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public void connect(InetAddress inetAddress, int i) throws IOException {
            InetAddress inetAddress2 = inetAddress;
            int i2 = i;
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public void create(boolean z) throws IOException {
            boolean z2 = z;
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public InputStream getInputStream() throws IOException {
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public OutputStream getOutputStream() throws IOException {
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public void listen(int i) throws IOException {
            int i2 = i;
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public void connect(SocketAddress socketAddress, int i) throws IOException {
            SocketAddress socketAddress2 = socketAddress;
            int i2 = i;
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: protected */
        public void sendUrgentData(int i) throws IOException {
            int i2 = i;
            throw new UnsupportedOperationException();
        }

        public Object getOption(int i) throws SocketException {
            int i2 = i;
            throw new UnsupportedOperationException();
        }

        public void setOption(int i, Object obj) throws SocketException {
            int i2 = i;
            Object obj2 = obj;
            throw new UnsupportedOperationException();
        }
    }

    public DatagramSocketWrapper(DatagramSocket datagramSocket, FileDescriptor fileDescriptor) throws SocketException {
        DatagramSocket socket = datagramSocket;
        FileDescriptor fd = fileDescriptor;
        DatagramSocket datagramSocket2 = socket;
        FileDescriptor fileDescriptor2 = fd;
        super(new DatagramSocketImplWrapper(socket, fd));
    }
}
