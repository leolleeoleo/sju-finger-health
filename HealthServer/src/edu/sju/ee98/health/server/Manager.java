/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.server;

import edu.sju.ee98.health.server.finger.FingerModule;
import edu.sju.ee98.health.server.finger.SerialFinger;
import edu.sju.ee98.health.sql.SQL;
import edu.sju.ee98.health.server.station.ServerNio;

/**
 *
 * @author Leo
 */
public class Manager {

    private static Manager manager = null;
    public static Command command = null;
    public static ServerNio server = null;
    public static SQL sql = null;
    public static FingerModule module = null;

    private Manager() {
        Manager.server = new ServerNio();
        Manager.command = new Command();
        Manager.sql = new SQL("127.0.0.1", "health_test", "finger", "health");
        Manager.module = SerialFinger.scan();
    }

    public static Manager manager() {
        if (Manager.manager == null) {
            Manager.manager = new Manager();
        }
        return Manager.manager;
    }
}
