/*
 * Copyright (c) 2012, St. John's University and/or its affiliates. All rights reserved.
 */
package edu.sju.ee98.health.server.finger;

import edu.sju.ee98.fingerprint.tfsmodule.TFS_M51;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * 指紋模組
 *
 * @author 98405067
 */
public class FingerModule extends TFS_M51 {

    private SerialStream serial;

    /**
     * 建立指紋模組
     *
     * @param serial
     */
    public FingerModule(SerialStream serial) {
        super(serial.getInputStream(), serial.getOutputStream());
        this.serial = serial;
    }

    /**
     * 開啟連線
     *
     * @throws SerialPortException 串列埠例外
     */
    public void open() throws SerialPortException {
        this.setTimeout(0);
        serial.openPort();
        serial.setParams(SerialPort.BAUDRATE_19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
    }

    /**
     * 關閉連線
     *
     * @throws SerialPortException 串列埠例外
     */
    public void close() throws SerialPortException {
        serial.closePort();
    }
}
