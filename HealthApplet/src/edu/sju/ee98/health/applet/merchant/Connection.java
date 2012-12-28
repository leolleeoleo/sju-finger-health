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
            JOptionPane.showMessageDialog(null, "無法與伺服器建立連線", "連線訊息", JOptionPane.ERROR_MESSAGE, null);
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    @Override
    public ByteBuffer receivePerformed(byte[] data) {
        ByteBuffer buff = ByteBuffer.allocate(1024);
        String s = new String(data).split("\r\n")[0];
        Logger.getLogger(Connection.class.getName()).log(Level.INFO, s);
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
        } else if (s.split(":")[0].equals("EXP SUCCESS")) {
            String name = s.split(":")[1];
            String point = s.split(":")[2];
            JOptionPane.showMessageDialog(null, "消費扣點成功" + "\r\n姓名:" + name + "\r\n剩餘點數" + point,
                    "連線訊息", JOptionPane.INFORMATION_MESSAGE, null);
            try {
                buff = null;
                this.disconnect();
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String info;
            if (s.endsWith("NOTENOUGH")) {
                info = "點數不足";
            } else if (s.endsWith("USER")) {
                info = "指紋辨識失敗，請重新輸入指紋";
            } else {
                info = s;
            }
            JOptionPane.showMessageDialog(null, info, "連線訊息", JOptionPane.WARNING_MESSAGE, null);
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
