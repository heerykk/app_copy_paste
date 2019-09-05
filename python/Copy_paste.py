from tkinter import Tk, Label, RAISED
import socket
import struct
import sys
import pyperclip

def updateClipboard():
    cliptext = pyperclip.paste()
    processClipping(cliptext=cliptext)
    root.after(ms=100, func=updateClipboard)

def processClipping(cliptext):
    cliptextCleaned = cleanClipText(cliptext=cliptext)
    label["text"] = cliptextCleaned

def cleanClipText(cliptext):
    cliptext = "".join([c for c in cliptext if ord(c) <= 65535])
    multicast_group = ('224.3.29.71', 10000)
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    sock.settimeout(0.2)
    ttl = struct.pack('b', 8)
    sock.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, ttl)
    sent = sock.sendto(cliptext, multicast_group)
    return cliptext

def onClick(labelElem):
    labelText = labelElem["text"]
    print(labelText)
    pyperclip.copy(labelText)

if __name__ == '__main__':
    root = Tk()
    label = Label(root, text="", cursor="plus", relief=RAISED, pady=5,  wraplength=500)
    label.bind("<Button-1>", lambda event, labelElem=label: onClick(labelElem))
    label.pack()
    updateClipboard()
    root.mainloop()
