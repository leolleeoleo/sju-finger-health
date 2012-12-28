/*
 * Copyright (c) 2012, St. John's University and/or its affiliates. All rights reserved.
 */
package edu.sju.ee98.health.server.finger;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 * 串列埠掃描器
 *
 * @author 98405067
 */
public class SerialScanner extends Thread {

    private SerialStream serial;
    private FingerModule module;

    /**
     * 掃描串列埠
     *
     * @return 指紋模組
     */
    public static FingerModule scan() {
        String list[] = SerialPortList.getPortNames();
        SerialScanner scan[] = new SerialScanner[list.length];
        for (int i = 0; i < list.length; i++) {
            Logger.getLogger(SerialScanner.class.getName()).log(Level.INFO, list[i]);
            scan[i] = new SerialScanner(list[i]);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < list.length; i++) {
            if (scan[i].getModule() != null) {
                return scan[i].getModule();
            }
        }
        return null;
    }

    /**
     * 測試串列埠
     *
     * @param port 埠名稱
     * @return 指紋模組
     */
    public static FingerModule test(String port) {
        SerialScanner test = new SerialScanner(port);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (test.getModule() != null) {
            return test.getModule();
        }
        return null;
    }

    private SerialScanner(String port) {
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
            Logger.getLogger(SerialScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
