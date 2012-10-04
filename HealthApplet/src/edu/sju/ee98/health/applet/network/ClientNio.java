/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.applet.network;

import edu.sju.ee98.fingerprint.ACKException;
import edu.sju.ee98.fingerprint.FingerCharacterize;
import edu.sju.ee98.health.applet.device.FingerModule;
import edu.sju.ee98.health.applet.device.SerialFinger;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;

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

    public static void main(String[] args) {
        FingerModule module = SerialFinger.scan();
        FingerCharacterize characterize = null;
        try {
            module.setTimeout(0);
            module.getSerial().openPort();
            module.getSerial().setParams(SerialPort.BAUDRATE_19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            try {
                characterize = module.getCharacterize();
            } catch (ACKException ex) {
                Logger.getLogger(ClientNio.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ClientNio.class.getName()).log(Level.SEVERE, null, ex);
            }
            module.setTimeout(0);
            module.getSerial().closePort();
        } catch (SerialPortException ex) {
            Logger.getLogger(ClientNio.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (characterize == null) {
            System.exit(0);
        }
        byte[] fingerCharacterize = characterize.getCharacterize();
        byte[] finger = new byte[207];
        for (int i = 0; i < fingerCharacterize.length; i++) {
            finger[i + 9] = fingerCharacterize[i];
        }
        System.out.println(Arrays.toString(finger));
        final byte[] fi = finger;

        for (int i = 0; i < 100; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        test(fi);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ClientNio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };
            t.start();
        }

    }

    public static void test(final byte[] finger) {
        final ClientNio client = new ClientNio("192.168.11.80", 1201);

        ClientListener l = new ClientListener() {
            int stage = STAGE_USER;
            String buff = "";

            @Override
            public ByteBuffer receivePerformed(byte[] data) {

                ByteBuffer buff = ByteBuffer.allocate(1024);
                String s = new String(data).split("\r\n")[0];
                System.out.println(s);
                if (s.equals("CONNECTED")) {
                    buff.put(("LOGIN:" + "REGISTER0156" + ":" + "111111111111" + "\r\n").getBytes());
                } else if (s.equals("LOGIN SUCCESS")) {
                    buff.put(("REG:").getBytes());
                    buff.put(finger);
                    buff.put(("\r\n").getBytes());
                } else {
                    try {
                        buff = null;
                        client.disconnect();
                    } catch (IOException ex) {
                        Logger.getLogger(ClientNio.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return buff;
            }
        };

        client.addClientListener(l);
        try {
            client.connect();
            client.start();

        } catch (IOException ex) {
            Logger.getLogger(ClientNio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
