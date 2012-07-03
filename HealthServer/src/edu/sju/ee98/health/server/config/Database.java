/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.server.config;

import edu.sju.ee98.health.sql.SQL;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MA780G
 */
public class Database implements java.io.Serializable {

    private String address;
    private int port;
    private String name;
    private String account;
    private String password;

    public Database(String address, int port, String name, String account, String password) {
        this.address = address;
        this.port = port;
        this.name = name;
        this.account = account;
        this.password = password;
    }

    public SQL connecter() {
//        return new SQL(this.address, this.name, this.account, this.password);
        return null;
    }

    public static Database read() {
        Database file = null;
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(Database.class.getSimpleName()));
            Object o = in.readObject();
            file = (Database) o;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return file;
    }

    public void write() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(Database.class.getSimpleName()));
            out.writeObject(this);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
