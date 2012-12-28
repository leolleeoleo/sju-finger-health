/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import edu.sju.ee98.fingerprint.ACKException;
import edu.sju.ee98.fingerprint.FingerCharacterize;
import edu.sju.ee98.health.applet.device.FingerModule;
import edu.sju.ee98.health.applet.device.SerialFinger;
import edu.sju.ee98.health.applet.network.ClientListener;
import edu.sju.ee98.health.applet.network.ClientNio;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author Leo
 */
public class Client {

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
