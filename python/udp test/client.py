import socket
import struct
import sys
import pyperclip

message = 'very important data'
multicast_group = ('224.3.29.71', 10000)
##server_address = ('', 10001)
# Create the datagram socket
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

# Bind to the server address
##sock.bind(server_address)

# Set a timeout so the socket does not block indefinitely when trying
# to receive data.
sock.settimeout(0.2)

# Set the time-to-live for messages to 1 so they do not go past the
# local network segment.
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
                    lastCopy = pyperclip.paste()
        finally:
            print >>sys.stderr, ''
            ##sock.close()
