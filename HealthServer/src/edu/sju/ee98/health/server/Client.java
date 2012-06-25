/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leo
 */
public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 1201);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            
            byte buff[] = new byte[1];
//            buff[0] = 'A';
//            out.write(buff);
//            out.flush();
            
            
            in.read(buff);
            System.out.println(buff[0] );
            in.read(buff);
            System.out.println(buff[0] );
            in.read(buff);
            System.out.println(buff[0] );
            in.read(buff);
            System.out.println(buff[0] );
            in.read(buff);
            System.out.println(buff[0] );
            
            buff[0] = 0x7F;
            out.write(buff);
            out.flush();
            
            
            in.read(buff);
            System.out.println(buff[0] );
            
            in.close();
            out.close();


        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
