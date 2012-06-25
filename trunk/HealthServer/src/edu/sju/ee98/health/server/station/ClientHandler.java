package edu.sju.ee98.health.server.station;

import edu.sju.ee98.fingerprint.tfsmodule.TFSCharacterize;
import edu.sju.ee98.health.server.Manager;
import edu.sju.ee98.health.server.sql.Register;
import edu.sju.ee98.health.server.sql.User;
import edu.sju.ee98.sql.Table;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortException;

public class ClientHandler {

    private SelectionKey key;
    private SocketChannel channel;
    private ArrayList<Table> table;

    public ClientHandler(SelectionKey key) {
        this.key = key;
        this.channel = (SocketChannel) key.channel();
        try {
            send("CONNECTED\r\n".getBytes());
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleRead() throws IOException {
        ByteBuffer buff = ByteBuffer.allocate(1024);
        int count = channel.read(buff);

        System.out.println("receive" + Arrays.toString(buff.array()));
        if (count > 0) {
            buff.flip();
            String receive = new String(buff.array()).split("\r\n")[0];
            if (receive.split(":")[0].equals("LOGIN")) {
                table = Manager.sql.logInRegister(receive.split(":")[1], receive.split(":")[2]);
                if (table.isEmpty()) {
                    table = Manager.sql.logInUser(receive.split(":")[1], receive.split(":")[2]);
                }
                if (table.isEmpty()) {
                    send("LOGIN FAIL\r\n".getBytes());
                } else {
                    send("LOGIN SUCCESS\r\n".getBytes());
                }
            } else if (receive.split(":")[0].equals("REG")) {
                if (this.table.get(0) instanceof Register) {
                    byte[] characterize = Arrays.copyOfRange(buff.array(), 4, 200);
                    try {
                        Manager.module.open();
                        try {
                            char num = Manager.module.compar(new TFSCharacterize(characterize));
                            characterize = Manager.module.getCharacterize(num).getCharacterize();
                        } catch (IOException ex) {
                            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Manager.module.close();
                    } catch (SerialPortException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ArrayList<Table> user = Manager.sql.logInUser(characterize);
                    if (user.size() > 0) {
                        Manager.sql.createRecord(new Date(), ((User) user.get(0)).getUID(), ((Register) table.get(0)).getRID());
                        send(("NAME:" + ((User) user.get(0)).getLAST_NAME() + ((User) user.get(0)).getFIRST_NAME() + "\r\n"
                                + "POINT:" + (Manager.sql.plusPoints(((User) user.get(0))) - Manager.sql.costPoints(((User) user.get(0)))) + "\r\n").getBytes("BIG5"));
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
                    byte[] characterize = Arrays.copyOfRange(buff.array(), 9, 205);
                    try {
                        Manager.module.open();
                        try {
                            char num = Manager.module.compar(new TFSCharacterize(characterize));
                            characterize = Manager.module.getCharacterize(num).getCharacterize();
                        } catch (IOException ex) {
                            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Manager.module.close();
                    } catch (SerialPortException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ArrayList<Table> user = Manager.sql.logInUser(characterize);
                    if (user.size() > 0) {
                        if (Manager.sql.createCost(null, new Date(), ((User) table.get(0)).getUID(), ((User) user.get(0)).getUID(), expend) != null) {
                            send("EXP SUCCESS\r\n".getBytes());
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
                    byte[] characterize = Arrays.copyOfRange(buff.array(), 4, 200);
                    try {
                        Manager.module.open();
                        try {
                            char num = (char) (Manager.module.getSize() + 1);
                            Manager.module.addUser(num, (byte) 1, new TFSCharacterize(characterize));
                            characterize = Manager.module.getCharacterize(num).getCharacterize();
                            System.out.println("TFSchar=" + Arrays.toString(characterize));
                        } catch (IOException ex) {
                            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Manager.module.close();
                    } catch (SerialPortException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Manager.sql.createFingerprint(((User) this.table.get(0)).getUID(), characterize);
                    send("CHA SUCCESS\r\n".getBytes());
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

    public void send(byte[] bytes) throws IOException {
        ByteBuffer buff = ByteBuffer.allocate(1024);
        buff.put(bytes);
        buff.flip();
        channel.write(buff);
    }
}
