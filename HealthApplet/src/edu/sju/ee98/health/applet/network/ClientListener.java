/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.applet.network;

import java.nio.ByteBuffer;

/**
 *
 * @author Leo
 */
public interface ClientListener {

    public final int STAGE_USER = 0;
    public final int STAGE_PASSWORD = 1;
    public final int STAGE_LOGON = 2;
    public final int STAGE_FINISH = 3;

    public ByteBuffer receivePerformed(byte[] data);
}
