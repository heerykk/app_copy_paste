import socket, struct, sys, pyperclip, threading
from threading import Thread, Lock

class ServerThread(threading.Thread):

    def __init__(self):
        threading.Thread.__init__(self)
    
    def kill(self):
        self.die = True
        
    def run(self):
        multicast_group = ('224.3.29.71', 10000)
        sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        sock.bind(multicast_group)
        mreq = struct.pack("4sl", socket.inet_aton(multicast_group[0]), socket.INADDR_ANY)
        sock.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, mreq)
        ttl = struct.pack('b', 5)
        sock.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, ttl)
        while True:
            print ('waiting to receive')
            try:
                data, server = sock.recvfrom(16)
            except socket.timeout:
                pyperclip.copy(str(data))
                print ('timed out, no more responses')
                break
            else:
                pyperclip.copy(str(data))
                print ('received "%s" from %s' % (data, server))


class ClientThread(threading.Thread):

    def __init__(self):
        threading.Thread.__init__(self)
        
    def kill(self):
        self.die = True
        
    def run(self):
        multicast_group = ('224.3.29.71', 10000)
        sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        sock.settimeout(0.2)
        ttl = struct.pack('b', 8)
        sock.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, ttl)
        lastCopy = pyperclip.paste()
        while True:
            if pyperclip.paste() != lastCopy:
                try:
                    # Send data to the multicast group
                    print >>sys.stderr, 'sending "%s"' % pyperclip.paste()
                    sent = sock.sendto(pyperclip.paste(), multicast_group)

                    # Look for responses from all recipients
                    while True:
                        print >>sys.stderr, 'waiting to receive'
                        try:
                            data, server = sock.recvfrom(16)
                        except socket.timeout:
                            print >>sys.stderr, 'timed out, no more responses'
                            lastCopy = pyperclip.paste()
                            break
                        else:
                            print >>sys.stderr, 'received "%s" from %s' % (data, server)
                finally:
                    print >>sys.stderr, ''
                    ##sock.close()
            
global client
global server
client = ClientThread()
server = ServerThread()
server.start()
client.start()
client.kill()
server.kill()

