/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.server.finger;

import edu.sju.ee98.fingerprint.tfsmodule.TFS_M51;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author Leo
 */
public class FingerModule extends TFS_M51 {

    private SerialStream serial;

    public FingerModule(SerialStream serial) {
        super(serial.getInputStream(), serial.getOutputStream());
        this.serial = serial;
    }

    public SerialStream getSerial() {
        return serial;
    }

    public void open() throws SerialPortException {
        this.setTimeout(0);
        serial.openPort();
        serial.setParams(SerialPort.BAUDRATE_19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
    }

    public void close() throws SerialPortException {
        serial.closePort();
    }
}
