/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.applet.merchant;

import edu.sju.ee98.fingerprint.FingerCharacterize;
import edu.sju.ee98.health.applet.network.ClientListener;
import edu.sju.ee98.health.applet.network.ClientNio;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Leo
 */
public class Connection extends ClientNio implements ClientListener {

    public Connection(String host, int port) {
        super(host, port);
        this.addClientListener(this);
    }
    private String account;
    private String password;
    private int point;
    private FingerCharacterize characterize;

    public void action(String account, String password, int point, FingerCharacterize characterize) {
        this.account = account;
        this.password = password;
        this.point = point;
        this.characterize = characterize;
        try {
            this.connect();
            this.run();
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    @Override
    public ByteBuffer receivePerformed(byte[] data) {
        ByteBuffer buff = ByteBuffer.allocate(1024);
        String s = new String(data).split("\r\n")[0];
        if (s.equals("CONNECTED")) {
            buff.put(("LOGIN:" + this.account + ":" + this.password + "\r\n").getBytes());
        } else if (s.equals("LOGIN SUCCESS")) {
            buff.put(("EXP:").getBytes());
            buff.put((byte) (point >> 24));
            buff.put((byte) (point >> 16));
            buff.put((byte) (point >> 8));
            buff.put((byte) (point));
            buff.put((":").getBytes());
            buff.put(this.characterize.getCharacterize());
            buff.put(("\r\n").getBytes());
        } else if (s.equals("EXP SUCCESS")) {
            JOptionPane.showMessageDialog(null, "Cost Point Successful", "Connection", JOptionPane.INFORMATION_MESSAGE, null);
            try {
                buff = null;
                this.disconnect();
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error : " + s, "Connection", JOptionPane.WARNING_MESSAGE, null);
            try {
                buff = null;
                this.disconnect();
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return buff;
    }
}
