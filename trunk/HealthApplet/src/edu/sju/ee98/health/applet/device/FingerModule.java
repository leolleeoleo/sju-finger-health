/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.applet.device;

import edu.sju.ee98.fingerprint.tfsmodule.TFS_M51;

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
}
