/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.applet.merchant;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Leo
 */
public class Cost extends JPanel {

    public static final String TEXT_POINT = "點數";
    private JLabel label_point;
    private JTextField point;

    public Cost() {
        this.setLayout(null);
        this.setSize(600, 50);
        this.setBackground(Color.white);

        this.label_point = new JLabel();
        this.label_point.setText(TEXT_POINT);
        this.label_point.setBounds(60, 10, 50, 30);
        this.add(this.label_point);

        this.point = new JTextField();
        this.point.setBounds(110, 10, 130, 30);
        this.add(this.point);
    }

    public int getPoint() {
//        try {
//            point = Integer.parseInt(this.point.getText());
//        } catch (NumberFormatException e) {
//        }
        return Integer.parseInt(this.point.getText());
    }
}
