/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.applet.merchant;

import edu.sju.ee98.fingerprint.FingerCharacterize;
import edu.sju.ee98.health.applet.device.FingerModule;
import edu.sju.ee98.health.applet.device.SerialFinger;
import edu.sju.ee98.health.applet.panel.CharacterizePanel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author Leo
 */
public class FingerPrint {

    private FingerModule module;

    public FingerPrint() {
        module = SerialFinger.scan();
        if (this.module == null) {
            JOptionPane.showMessageDialog(null, "找不到指紋模組!", "指紋模組", JOptionPane.ERROR_MESSAGE, null);
        }
    }

    public FingerCharacterize gerCharacterize() {
        if (this.module == null && (module = SerialFinger.scan()) == null) {
            JOptionPane.showMessageDialog(null, "找不到指紋模組!", "指紋模組", JOptionPane.ERROR_MESSAGE, null);
            return null;
        }
        FingerCharacterize characterize = null;
        try {
            Thread option = new Thread() {
                @Override
                public void run() {
                    JOptionPane.showOptionDialog(null, "請按壓指紋。", "訊息", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"取消"}, null);
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
        return characterize;
    }
}
