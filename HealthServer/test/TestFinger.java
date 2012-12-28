/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.sju.ee98.fingerprint.ACKException;
import edu.sju.ee98.fingerprint.tfsmodule.TFSCharacterize;
import edu.sju.ee98.health.server.Manager;
import edu.sju.ee98.health.sql.Fingerprint;
import edu.sju.ee98.health.sql.User;
import edu.sju.ee98.sql.Table;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author MA780G
 */
public class TestFinger {

    public static void main(String[] args) {
        Manager.manager();
        try {
            Manager.module.open();
            try {
                int ss = Manager.module.getSize();
                System.out.println(ss);

//                Manager.module.deleteAll();



//                ArrayList<Table> sp = Manager.sql.getFingerprint();
////                System.out.println("SQLchar=" + Arrays.toString(((Fingerprint) sp.get(0)).getFINGERPRINT()));
//                for (int i = 0; i < sp.size(); i++) {
//                    byte finger[] = ((Fingerprint) sp.get(i)).getFINGERPRINT();
//                    Manager.module.addUser((char) finger[1], finger[2], new TFSCharacterize(finger));
//                }

                byte[] characterize;
//                char num = Manager.module.compar(new TFSCharacterize(characterize));
//                System.out.println("module=" + (int) num);


                characterize = Manager.module.getCharacterize((char) 1).getCharacterize();
                System.out.println("TFSchar=" + Arrays.toString(characterize));
                ArrayList user = Manager.sql.logInUser(characterize);
                System.out.println(user.size());
                ((User) user.get(0)).print();


//                Manager.module.addUser((char) 1, (byte) 1);
//                Manager.module.getCharacterize();
            } catch (ACKException ex) {
                Logger.getLogger(TestFinger.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TestFinger.class.getName()).log(Level.SEVERE, null, ex);
            }
            Manager.module.close();
        } catch (SerialPortException ex) {
            Logger.getLogger(TestFinger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
