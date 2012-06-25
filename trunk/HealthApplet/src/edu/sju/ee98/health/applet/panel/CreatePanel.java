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
public class CreatePanel  extends JPanel {

    private static final String UPDATE = "更新";
    private CharacterizePanel cpane;
    private JButton update;

    public CreatePanel() {
        this.setLayout(null);
        this.setSize(300, 300);
        this.setBackground(Color.white);
        //
        this.cpane = new CharacterizePanel();
        this.cpane.setLocation(20, 20);
        this.add(this.cpane);

        this.update = new JButton();
        this.update.setText(UPDATE);
        this.update.setBounds(210, 210, 80, 30);
        this.update.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cpane.getCharacterize() == null) {
                    JOptionPane.showMessageDialog(null, "尚未取得指紋", "錯誤", JOptionPane.ERROR_MESSAGE);
                } else {
                    
                }
            }
        });
        this.add(this.update);
    }
}
