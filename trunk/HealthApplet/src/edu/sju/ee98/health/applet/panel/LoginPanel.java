/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.applet.panel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Leo
 */
public class LoginPanel extends JPanel {

    private static final String ACCOUNT = "帳號";
    private static final String PASSWORD = "密碼";
    private JLabel label_account;
    private JLabel label_password;
    private JTextField account;
    private JPasswordField password;

    public LoginPanel() {
        this.setLayout(null);
        this.setSize(200, 100);
        this.setBackground(Color.WHITE);
        //
        this.label_account = new JLabel();
        this.label_account.setText(ACCOUNT);
        this.label_account.setBounds(10, 10, 80, 20);
        this.add(this.label_account);

        this.label_password = new JLabel();
        this.label_password.setText(PASSWORD);
        this.label_password.setBounds(10, 60, 80, 20);
        this.add(this.label_password);

        this.account = new JTextField();
        this.account.setBounds(60, 10, 130, 20);
        this.account.setText("store1234");
        this.add(this.account);

        this.password = new JPasswordField();
        this.password.setBounds(60, 60, 130, 20);
        this.password.setText("98765432");
        this.add(this.password);

    }

    public String getAccount() {
        return account.getText();
    }

    public String getPassword() {
        return password.getText();
    }
}
