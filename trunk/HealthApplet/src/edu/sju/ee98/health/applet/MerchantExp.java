/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.applet;

import edu.sju.ee98.health.applet.merchant.Connection;
import edu.sju.ee98.health.applet.merchant.Cost;
import edu.sju.ee98.health.applet.merchant.FingerPrint;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Leo
 */
public class MerchantExp extends JApplet implements ActionListener {

    private String account;
    private String password;
    private String host;
    private int port;
    private Cost cost;
    private JButton enter;
    private FingerPrint print;
    private Connection connect;

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    public void init() {
        this.initParameter();
        this.initLayout();
        this.cost = new Cost();
        this.cost.setLocation(0, 0);
        this.add(cost);
        this.enter = new JButton("確定");
        this.enter.setBounds(100, 100, 80, 30);
        this.enter.addActionListener(this);
        this.add(enter);

        this.print = new FingerPrint();
        this.connect = new Connection(this.host, this.port);
    }

    private void initParameter() {
        this.account = this.getParameter("account");
        this.password = this.getParameter("password");
        this.host = this.getParameter("host");
        this.port = Integer.parseInt(this.getParameter("port"));
    }

    private void initLayout() {
        this.setLayout(null);
        this.setSize(300, 200);
        this.getContentPane().setBackground(Color.WHITE);
    }
    // TODO overwrite start(), stop() and destroy() methods

    @Override
    public void actionPerformed(ActionEvent e) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    int point = cost.getPoint();
                    connect.action(account, password, point, print.gerCharacterize());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "請輸入整數。", "消費扣點", JOptionPane.WARNING_MESSAGE, null);
                }
            }
        };
        thread.start();
    }
}
