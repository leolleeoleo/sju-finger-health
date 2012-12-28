/*
 * Copyright (c) 2012, St. John's University and/or its affiliates. All rights reserved.
 */
package edu.sju.ee98.health.server.station;

import edu.sju.ee98.fingerprint.FingerCharacterize;
import edu.sju.ee98.fingerprint.tfsmodule.TFSCharacterize;
import edu.sju.ee98.health.server.Manager;
import edu.sju.ee98.health.sql.Cost;
import edu.sju.ee98.health.sql.Fingerprint;
import edu.sju.ee98.health.sql.Register;
import edu.sju.ee98.health.sql.User;
import edu.sju.ee98.sql.Table;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortException;

/**
 * 服務處理程序
 *
 * @author 98405067
 */
public class ServiceHandler {

    private SelectionKey key;
    private SocketChannel channel;
    private ArrayList<Table> table;

    /**
     * 建立服務處理程序
     *
     * @param key 選擇鍵
     */
    public ServiceHandler(SelectionKey key) {
        this.key = key;
        this.channel = (SocketChannel) key.channel();
        try {
            //傳送連線建立訊息
            send("CONNECTED\r\n".getBytes());
        } catch (IOException ex) {
            Logger.getLogger(ServiceHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 程序
     *
     * @throws IOException
     */
    public void handle() throws IOException {
        ByteBuffer buff = ByteBuffer.allocate(1024);
        int count = channel.read(buff);
        Logger.getLogger(ServiceHandler.class.getName()).log(Level.INFO, Arrays.toString(buff.array()));
        if (count > 0) {
            buff.flip();
            String receive = new String(buff.array()).split("\r\n")[0];
            if (receive.split(":")[0].equals("LOGIN")) {
                String account = receive.split(":")[1];
                String password = receive.split(":")[2];
                Logger.getLogger(ServiceHandler.class.getName()).log(Level.INFO, account);
                Logger.getLogger(ServiceHandler.class.getName()).log(Level.INFO, password);
                table = Manager.sql.logInRegister(account, password);
                if (table.isEmpty()) {
                    table = Manager.sql.logInUser(account, password);
                }
                if (table.isEmpty()) {
                    send("LOGIN FAIL\r\n".getBytes());
                } else {
                    send("LOGIN SUCCESS\r\n".getBytes());
                }
            } else if (receive.split(":")[0].equals("REG")) {
                if (this.table.get(0) instanceof Register) {
                    FingerCharacterize characterize = new TFSCharacterize(Arrays.copyOfRange(buff.array(), 13, 209));
                    Logger.getLogger(ServiceHandler.class.getName()).log(Level.INFO, characterize.toString());
                    try {
                        Manager.module.open();
                        try {
                            char num = Manager.module.compar(characterize);
                            characterize = Manager.module.getCharacterize(num);
                        } catch (IOException ex) {
                            Logger.getLogger(ServiceHandler.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                        }
                        Manager.module.close();
                    } catch (SerialPortException ex) {
                        Logger.getLogger(ServiceHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ArrayList<Table> userList = Manager.sql.logInUser(characterize.getCharacterize());
                    if (userList.size() > 0) {
                        User user = (User) userList.get(0);
                        Manager.sql.createRecord(new Date(), user.getUID(), ((Register) table.get(0)).getRID());
                        send(("NAME:" + user.getLAST_NAME() + user.getFIRST_NAME() + "\r\n"
                                + "POINT:" + (Manager.sql.plusPoints(user) - Manager.sql.costPoints(user))
                                + "\r\n").getBytes("BIG5"));
                        send("REG SUCCESS\r\n".getBytes());
                    } else {
                        send("REG FAIL USER\r\n".getBytes());
                    }
                } else {
                    send("LOGON ERROR\r\n".getBytes());
                }
            } else if (receive.split(":")[0].equals("EXP")) {
                if (this.table.get(0) instanceof User) {
                    int expend = (int) (((buff.array()[4] & 0xFF) << 24) | ((buff.array()[5] & 0xFF) << 16) | ((buff.array()[6] & 0xFF) << 8) | (buff.array()[7] & 0xFF));
                    FingerCharacterize characterize = new TFSCharacterize(Arrays.copyOfRange(buff.array(), 9, 205));
                    try {
                        Manager.module.open();
                        try {
                            char num = Manager.module.compar(characterize);
                            characterize = Manager.module.getCharacterize(num);
                        } catch (IOException ex) {
                            Logger.getLogger(ServiceHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Manager.module.close();
                    } catch (SerialPortException ex) {
                        Logger.getLogger(ServiceHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ArrayList<Table> user = Manager.sql.logInUser(characterize.getCharacterize());
                    if (user.size() > 0) {
                        Cost cost = Manager.sql.createCost(new Date(), ((User) table.get(0)).getUID(), ((User) user.get(0)).getUID(), expend);
                        if (cost != null) {
                            send(("EXP SUCCESS:" + ((User) user.get(0)).getLAST_NAME() + ((User) user.get(0)).getFIRST_NAME()
                                    + ":" + (Manager.sql.plusPoints(((User) user.get(0))) - Manager.sql.costPoints(((User) user.get(0)))) + "\r\n").getBytes("BIG5"));
                        } else {
                            send("EXP FAIL NOTENOUGH\r\n".getBytes());
                        }
                    } else {
                        send("EXP FAIL USER\r\n".getBytes());
                    }
                } else {
                    send("LOGON ERROR\r\n".getBytes());
                }
            } else if (receive.split(":")[0].equals("CHA")) {
                if (this.table.get(0) instanceof User) {
                    FingerCharacterize characterize = new TFSCharacterize(Arrays.copyOfRange(buff.array(), 4, 200));
                    synchronized (Manager.module) {
                        try {
                            Manager.module.open();
                            try {
                                char num;
                                ArrayList<Table> fingerprint = Manager.sql.getFingerprint(((User) this.table.get(0)).getUID());
                                if (fingerprint.size() > 0) {
                                    num = (char) (((Fingerprint) fingerprint.get(0)).getFINGERPRINT()[1]);
                                    Manager.sql.deleteFingerprint(((User) this.table.get(0)).getUID());
                                    Manager.module.deleteUser(num);
                                } else {
                                    num = (char) (Manager.module.getSize() + 1);
                                }
                                Manager.module.addUser(num, (byte) 1, characterize);
                                characterize = Manager.module.getCharacterize(num);
                            } catch (IOException ex) {
                                Logger.getLogger(ServiceHandler.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Manager.module.close();
                        } catch (SerialPortException ex) {
                            Logger.getLogger(ServiceHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    ArrayList<Table> createFingerprint = Manager.sql.createFingerprint(((User) this.table.get(0)).getUID(), characterize.getCharacterize());
                    if (createFingerprint.size() > 0) {
                        send("CHA SUCCESS\r\n".getBytes());
                    } else {
                        send("CHA FAIL\r\n".getBytes());
                    }
                } else {
                    send("LOGON ERROR\r\n".getBytes());
                }
            }
            buff.clear();
        } else {
            channel.close();
            key.cancel();
        }
    }

    /**
     * 傳送
     *
     * @param bytes 資料
     * @throws IOException 例外
     */
    private void send(byte[] bytes) throws IOException {
        ByteBuffer buff = ByteBuffer.allocate(1024);
        buff.put(bytes);
        buff.flip();
        channel.write(buff);
    }
}
