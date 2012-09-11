/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.applet;

import edu.sju.ee98.health.applet.panel.LoginPanel;
import javax.swing.JApplet;

/**
 *
 * @author Leo
 */
public class Main extends JApplet {
    
    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    @Override
    public void init() {
        String parameter = this.getParameter("account");
        System.out.println(parameter);
        this.setLayout(null);
        this.setSize(600, 600);
        LoginPanel loginPanel = new LoginPanel();
        loginPanel.setAccount(parameter);
        this.add(loginPanel);
        //        this.add(new Characterize());
        //        this.add(new Create());
        //        this.add(new Cost());
        
    }
    // TODO overwrite start(), stop() and destroy() methods

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
