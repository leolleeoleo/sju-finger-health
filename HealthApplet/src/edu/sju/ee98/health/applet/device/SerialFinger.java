/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.applet.device;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 *
 * @author Leo
 */
public class SerialFinger extends Thread {

    private SerialStream serial;
    private FingerModule module;

    public static FingerModule scan() {
        String list[] = SerialPortList.getPortNames();
        SerialFinger scan[] = new SerialFinger[list.length];
        for (int i = 0; i < list.length; i++) {
            scan[i] = new SerialFinger(list[i]);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialFinger.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < list.length; i++) {
            if (scan[i].getModule() != null) {
                return scan[i].getModule();
            }
        }
        return null;
    }

    public static FingerModule test(String port) {
        SerialFinger test = new SerialFinger(port);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialFinger.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (test.getModule() != null) {
            return test.getModule();
        }
        return null;
    }

    private SerialFinger(String port) {
        this.serial = new SerialStream(port);
        this.start();
    }

    private FingerModule getModule() {
        return module;
    }

    @Override
    public void run() {
        try {
            this.serial.openPort();
            this.serial.setParams(SerialPort.BAUDRATE_19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            try {
                this.module = new FingerModule(this.serial);
                this.module.setTimeout(500);
                this.module.getSize();
            } catch (IOException ex) {
                this.module = null;
            }
            this.serial.closePort();
            this.serial = null;
        } catch (SerialPortException ex) {
            Logger.getLogger(SerialFinger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
