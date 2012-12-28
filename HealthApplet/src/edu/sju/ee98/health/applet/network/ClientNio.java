/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.applet.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientNio extends Thread {

    public static final int IAC = 255;
    public static final int WILL = 251;
    public static final int WONT = 252;
    public static final int DO = 253;
    public static final int DONT = 254;
    private InetSocketAddress addr;
    private SocketChannel channel;
    private ClientListener listener;

    public ClientNio(String host, int port) {
        this.addr = new InetSocketAddress(host, port);
    }

    public void connect() throws IOException {
        channel = SocketChannel.open(addr);
    }

    public void disconnect() throws IOException {
        channel.close();
    }

    public void addClientListener(ClientListener listener) {
        this.listener = listener;
    }

    public void send(ByteBuffer b) {
//        ByteBuffer buff = ByteBuffer.allocate(b.length);
//        buff.put(b);
        b.flip();
        try {
            channel.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        ByteBuffer buff = ByteBuffer.allocate(1024);
        while (channel.isOpen()) {
            try {
                buff.clear();
                channel.read(buff);
                if (buff.position() == 0) {
                    continue;
                }
                buff.flip();
                if (this.listener != null) {
                    ByteBuffer send = this.listener.receivePerformed(buff.array());
                    if (send != null) {
                        this.send(send);
                    }
                }
                if (new String(buff.array()).split("\r\n")[0].equals("FINISH")) {
                    channel.close();
                }
            } catch (IOException ex) {
                try {
                    channel.close();
                } catch (IOException ex1) {
                    break;
                }
            }
        }
    }
}
