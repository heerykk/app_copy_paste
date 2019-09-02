import socket
import struct
import sys

message = 'very important data'
multicast_group = ('224.3.29.71', 10000)
##server_address = ('', 10001)
# Create the datagram socket
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock.bind(multicast_group)
mreq = struct.pack("4sl", socket.inet_aton(multicast_group[0]), socket.INADDR_ANY)

sock.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, mreq)
# Bind to the server address
##sock.bind(server_address)
# Set a timeout so the socket does not block indefinitely when trying
# to receive data.
# sock.settimeout(0.2)
# Set the time-to-live for messages to 1 so they do not go past the
# local network segment.
ttl = struct.pack('b', 5)
sock.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, ttl)
try:
    # Send data to the multicast group
    # print ('sending "%s"' % message)
    # sent = sock.sendto(message, multicast_group)
    # Look for responses from all recipients
    while True:
        print ('waiting to receive')
        try:
            data, server = sock.recvfrom(16)
        except socket.timeout:
            print ('timed out, no more responses')
            break
        else:
            print ('received "%s" from %s' % (data, server))
finally:
    print >> sys.stderr, 'closing socket'
    sock.close()
