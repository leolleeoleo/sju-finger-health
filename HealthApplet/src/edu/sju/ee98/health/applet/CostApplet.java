/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.applet;

import edu.sju.ee98.health.applet.device.*;
import edu.sju.ee98.health.applet.network.*;
import edu.sju.ee98.health.applet.panel.*;
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
import javax.swing.JTextField;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author Leo
 */
public class CostApplet extends JApplet {

    public static final String COST = "消費點數";
    private LoginPanel login;
    private JButton cost;
    private FingerCharacterize characterize = null;
    private int point;

    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    public void init() {
        this.setLayout(null);
        this.setSize(300, 300);
        this.getContentPane().setBackground(Color.WHITE);
        // TODO start asynchronous download of heavy resources
        this.login = new LoginPanel();
        this.login.setLocation(50, 50);
        this.add(this.login);
        
        this.cost = new JButton();
        this.cost.setBounds(100, 200, 100, 30);
        this.cost.setText(COST);
        this.cost.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                FingerAction action = new FingerAction();
                action.start();
            }
        });
        this.add(this.cost);

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
                        characterize = this.module.getCharacterize();
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
            point = Integer.parseInt(JOptionPane.showInputDialog(null, "Cost point :", "COST", JOptionPane.QUESTION_MESSAGE));
            ClientNio client = new ClientNio("163.21.76.50", 1201);
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
            characterize = null;
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
                buff.put(("EXP:").getBytes());
                buff.put((byte) (point >> 24));
                buff.put((byte) (point >> 16));
                buff.put((byte) (point >> 8));
                buff.put((byte) (point));
                buff.put((":").getBytes());
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
