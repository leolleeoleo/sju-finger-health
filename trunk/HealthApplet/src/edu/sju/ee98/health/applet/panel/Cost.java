/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.applet.panel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Leo
 */
public class Cost extends JPanel {

    private static final String POINT = "點數";
    private static final String COST = "消費";
    private CharacterizePanel cpane;
    private JLabel label_point;
    private JTextField point;
    private JButton cost;

    public Cost() {
        this.setLayout(null);
        this.setSize(300, 300);
        this.setBackground(Color.white);
        //
        this.cpane = new CharacterizePanel();
        this.cpane.setLocation(50, 50);
        this.add(this.cpane);

        this.label_point = new JLabel();
        this.label_point.setText(POINT);
        this.label_point.setBounds(60, 210, 30, 30);
        this.add(this.label_point);

        this.point = new JTextField();
        this.point.setBounds(110, 210, 130, 30);
        this.add(this.point);

        this.cost = new JButton();
        this.cost.setText(COST);
        this.cost.setBounds(210, 260, 80, 30);
        this.cost.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cpane.getCharacterize() == null) {
                    JOptionPane.showMessageDialog(null, "尚未取得指紋", "錯誤", JOptionPane.ERROR_MESSAGE);
                } else {
                    
                }
            }
        });
        this.add(this.cost);
    }
}
