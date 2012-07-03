/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.server;

import edu.sju.ee98.health.sql.TestSQL;
import edu.sju.ee98.fingerprint.tfsmodule.TFSCharacterize;
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
 * @author Leo
 */
public class Command extends Thread {

    private boolean state = false;

    public Command() {
        this.state(true);
        this.start();
    }

    public void state(boolean state) {
        this.state = state;
        if (state) {
            synchronized (this) {
                this.notify();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (!this.state) {
                    synchronized (Command.this) {
                        Command.this.wait();
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.print(">");
            byte b[] = new byte[1024];
            try {
                System.in.read(b);
                String read = new String(b);
                if (read.startsWith("help")) {
                    System.out.println("help");
                    System.out.println("service");
                    System.out.println("finger");
                    System.out.println("sql");
                    System.out.println("point record");
                    System.out.println("exit");
                } else if (read.startsWith("service")) {
                    if (read.startsWith("service start")) {
                        Manager.server.state(true);
                    } else if (read.startsWith("service stop")) {
                        Manager.server.state(false);
                    } else {
                        System.out.println("error");
                        System.out.println("service start");
                        System.out.println("service stop");
                    }
                } else if (read.startsWith("finger")) {
                    if (read.startsWith("finger size")) {
                        try {
                            Manager.module.setTimeout(0);
                            Manager.module.getSerial().openPort();
                            Manager.module.getSerial().setParams(SerialPort.BAUDRATE_19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                            try {
                                System.out.println(Manager.module.getSize());
                            } catch (IOException ex) {
                                Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Manager.module.getSerial().closePort();
                        } catch (SerialPortException ex) {
                            Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (read.startsWith("finger delete")) {
                        try {
                            Manager.module.setTimeout(0);
                            Manager.module.getSerial().openPort();
                            Manager.module.getSerial().setParams(SerialPort.BAUDRATE_19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                            try {
                                Manager.module.deleteAll();
                            } catch (IOException ex) {
                                Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Manager.module.getSerial().closePort();
                        } catch (SerialPortException ex) {
                            Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (read.startsWith("finger import")) {
                        try {
                            Manager.module.setTimeout(0);
                            Manager.module.getSerial().openPort();
                            Manager.module.getSerial().setParams(SerialPort.BAUDRATE_19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                            try {
                                Manager.module.deleteAll();
                                ArrayList<Table> sp = Manager.sql.getFingerprint();
                                for (int i = 0; i < sp.size(); i++) {
                                    byte finger[] = ((Fingerprint) sp.get(i)).getFINGERPRINT();
                                    Manager.module.addUser((char) finger[1], finger[2], new TFSCharacterize(finger));
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Manager.module.getSerial().closePort();
                        } catch (SerialPortException ex) {
                            Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (read.startsWith("finger check")) {
                        int num = Integer.parseInt(read.split(" ")[2]);
                        try {
                            Manager.module.setTimeout(0);
                            Manager.module.getSerial().openPort();
                            Manager.module.getSerial().setParams(SerialPort.BAUDRATE_19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                            try {
                                byte[] characterize;
                                characterize = Manager.module.getCharacterize((char) num).getCharacterize();
                                System.out.println("TFSchar=" + Arrays.toString(characterize));
                                ArrayList user = Manager.sql.logInUser(characterize);
                                System.out.println(user.size());
                                ((User) user.get(0)).print();
                            } catch (IOException ex) {
                                Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Manager.module.getSerial().closePort();
                        } catch (SerialPortException ex) {
                            Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        System.out.println("error");
                        System.out.println("finger size");
                        System.out.println("finger delete");
                        System.out.println("finger import");
                        System.out.println("finger check (num)");
                    }
                } else if (read.startsWith("sql")) {
                    if (read.startsWith("sql drop")) {
                        Manager.sql.dropTables();
                    } else if (read.startsWith("sql create")) {
                        Manager.sql.createTables();
                    } else if (read.startsWith("sql example")) {
                        TestSQL.insertExample();
                    } else {
                        System.out.println("error");
                        System.out.println("sql drop");
                        System.out.println("sql create");
                        System.out.println("sql example");
                    }
                } else if (read.startsWith("point")) {
                    if (read.startsWith("point record")) {
                        String user = read.split(" ")[2];
                        User u = new User();
                        u.setUID(user);
                        System.out.println("point record = " + Manager.sql.plusPoints(u));
                    } else if (read.startsWith("point cost")) {
                        String user = read.split(" ")[2];
                        User u = new User();
                        u.setUID(user);
                        System.out.println("point cost = " + Manager.sql.costPoints(u));
                    } else if (read.startsWith("point count")) {
                        String user = read.split(" ")[2];
                        System.out.println(user);
                        User u = new User();
                        u.setUID(user);
                        System.out.println("point count = " + (Manager.sql.plusPoints(u) - Manager.sql.costPoints(u)));
                    } else {
                        System.out.println("error");
                        System.out.println("point record");
                        System.out.println("point cost");
                        System.out.println("point count");
                    }
                } else if (read.startsWith("exit")) {
                    System.exit(0);
                } else {
                    System.out.println("command eror, try help!");
                }

            } catch (IOException ex) {
                Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
