/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.applet.panel;

import edu.sju.ee98.health.applet.device.FingerModule;
import edu.sju.ee98.health.applet.device.SerialFinger;
import edu.sju.ee98.fingerprint.FingerCharacterize;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 *
 * @author Leo
 */
public class CharacterizePanel extends JPanel {

    private static final String TITLE = "指紋操作";
    private static final String SCAN = "掃描";
    private static final String RUN = "執行";
    private static final String CREATE = "建立";
    private static final String GET = "取得";
    private JLabel Title;
    private JComboBox combo;
    private JButton scan;
    private JComboBox function;
    private JButton run;
    private FingerCharacterize characterize = null;

    public CharacterizePanel() {
        this.setLayout(null);
        this.setSize(200, 150);
        this.setBackground(Color.white);
        //

        this.Title = new JLabel();
        this.Title.setText(TITLE);
        this.Title.setBounds(10, 10, 80, 30);
        this.add(this.Title);

        this.combo = new JComboBox();
        this.combo.setBounds(10, 60, 80, 30);
        this.scanList();
        this.add(this.combo);

        this.scan = new JButton();
        this.scan.setText(SCAN);
        this.scan.setBounds(110, 60, 80, 30);
        this.scan.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                scanList();
            }
        });
        this.add(this.scan);


        this.function = new JComboBox();
        this.function.setBounds(10, 110, 80, 30);
        this.function.addItem(CREATE);
        this.function.addItem(GET);
        this.add(this.function);

        this.run = new JButton();
        this.run.setText(RUN);
        this.run.setBounds(110, 110, 80, 30);
        this.run.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                characterize = null;
                FingerAction action = new FingerAction();
                action.start();
            }
        });
        this.add(this.run);
    }

    private void scanList() {
        this.combo.removeAllItems();
        this.combo.addItem("AUTO");
        String list[] = SerialPortList.getPortNames();
        for (int i = 0; i < list.length; i++) {
            this.combo.addItem(list[i]);
        }
    }

    public FingerCharacterize getCharacterize() {
        return characterize;
    }

    private class FingerAction extends Thread {

        private FingerModule module;

        public FingerAction() {
            if (combo.getSelectedIndex() == 0) {
                module = SerialFinger.scan();
            } else {
                module = SerialFinger.test(combo.getSelectedItem().toString());
            }
        }

        @Override
        public void run() {
            if (this.module == null) {
                JOptionPane.showMessageDialog(null, "Can not connect to the Fingerprint Module!", "Serial Port Error", JOptionPane.ERROR_MESSAGE, null);
                return;
            }
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
                    switch (function.getSelectedIndex()) {
                        case 0:
                            this.module.deleteAll();
                            this.module.addUser((char) 1, (byte) 1);
                            characterize = this.module.getCharacterize((char) 1);
                            break;
                        case 1:
                            characterize = this.module.getCharacterize();
                            break;
                        default:
                            break;
                    }
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
            if (characterize != null) {
                JOptionPane.showMessageDialog(null, "Get Fingerprint characterize Successful.", "Fingerprint Information", JOptionPane.INFORMATION_MESSAGE, null);
                System.out.println(characterize);
            } else {
                JOptionPane.showMessageDialog(null, "Get Fingerprint characterize Fail.", "Fingerprint Information", JOptionPane.WARNING_MESSAGE, null);
            }
        }
    }
}
