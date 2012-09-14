/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.applet;

import edu.sju.ee98.health.applet.device.FingerModule;
import edu.sju.ee98.health.applet.device.SerialFinger;
import edu.sju.ee98.health.applet.network.ClientListener;
import edu.sju.ee98.health.applet.network.ClientNio;
import edu.sju.ee98.health.applet.panel.CharacterizePanel;
import edu.sju.ee98.health.applet.panel.LoginPanel;
import edu.sju.ee98.fingerprint.FingerCharacterize;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author Leo
 */
public class UpdateFingerprint extends JApplet {

    public static final String CREATE = "更新指紋";
    private LoginPanel login;
    private JButton create;
    private FingerCharacterize characterize = null;

    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    public void init() {
        this.setLayout(null);
        this.setSize(300, 200);
        this.getContentPane().setBackground(Color.WHITE);
        // TODO start asynchronous download of heavy resources
        this.login = new LoginPanel();
        this.login.setLocation(50, 20);
        this.add(this.login);

        this.create = new JButton();
        this.create.setBounds(100, 130, 100, 30);
        this.create.setText(CREATE);
        this.create.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                FingerAction action = new FingerAction();
                action.start();
            }
        });
        this.add(this.create);

    }

    // TODO overwrite start(), stop() and destroy() methods
    private class FingerAction extends Thread {

        private FingerModule module;

        public FingerAction() {
            module = SerialFinger.scan();
        }

        @Override
        public void run() {
            if (this.module == null) {
                JOptionPane.showMessageDialog(null, "Can not connect to the Fingerprint Module!", "Serial Port Error", JOptionPane.ERROR_MESSAGE, null);
            } else {
                try {
                    Thread option = new Thread() {

                        @Override
                        public void run() {
                            JOptionPane.showOptionDialog(null, "Please scan Fingerprint, or click Cancel", "Information", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Cancel"}, null);
                            module.setTimeout(1);
                        }
                    };
                    option.start();
                    module.setTimeout(0);
                    module.getSerial().openPort();
                    module.getSerial().setParams(SerialPort.BAUDRATE_19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                    try {
                        this.module.deleteAll();
                        this.module.addUser((char) 1, (byte) 1);
                        characterize = this.module.getCharacterize((char) 1);
                    } catch (IOException ex) {
                    }
                    option.interrupt();
                    try {
                        option.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(CharacterizePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    module.setTimeout(0);
                    try {
                        module.deleteAll();
                    } catch (IOException ex) {
                        Logger.getLogger(CharacterizePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    module.getSerial().closePort();
                } catch (SerialPortException ex) {
                    Logger.getLogger(CharacterizePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (characterize != null) {
                JOptionPane.showMessageDialog(null, "Get Fingerprint characterize Successful.", "Fingerprint Information", JOptionPane.INFORMATION_MESSAGE, null);
                System.out.println(characterize);
            } else {
                JOptionPane.showMessageDialog(null, "Get Fingerprint characterize Fail.", "Fingerprint Information", JOptionPane.WARNING_MESSAGE, null);
                return;
            }
            ClientNio client = new ClientNio("sju.servehttp.com", 1201);
            Listener listener = new Listener(client);
            client.addClientListener(listener);
            try {
                client.connect();
                client.start();
                try {
                    client.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(UpdateFingerprint.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (IOException ex) {
                Logger.getLogger(ClientNio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class Listener implements ClientListener {

        private ClientNio client;
        private int stage = STAGE_USER;
        private byte[] bytes;

        public Listener(ClientNio client) {
            this.client = client;
        }

        @Override
        public ByteBuffer receivePerformed(byte[] data) {
            ByteBuffer buff = ByteBuffer.allocate(1024);
            String s = new String(data).split("\r\n")[0];
            if (s.equals("CONNECTED")) {
                buff.put(("LOGIN:" + login.getAccount() + ":" + login.getPassword() + "\r\n").getBytes());
            } else if (s.equals("LOGIN SUCCESS")) {
                buff.put(("CHA:").getBytes());
                buff.put(characterize.getCharacterize());
                buff.put(("\r\n").getBytes());
            } else {
                try {
                    buff = null;
                    this.client.disconnect();
                } catch (IOException ex) {
                    Logger.getLogger(UpdateFingerprint.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return buff;
        }
    }
}
