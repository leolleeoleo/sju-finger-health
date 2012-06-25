package edu.sju.ee98.health.server.station;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
//import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerNio extends Thread {

    private boolean state = false;
//    private StationService service;
    private int port;
    private ServerSocketChannel serverChannel;
    private ServerSocket ss;
    private Selector selector;

    public ServerNio() {
        this(1201);
    }

    public ServerNio(int port) {
        this.port = port;
        this.start();
    }
    
    

    public void state(boolean state) {
        if (state == this.state) {
            if (state) {
                Logger.getLogger(ServerNio.class.getName()).log(Level.INFO, "Server is already start!");
            } else {
                Logger.getLogger(ServerNio.class.getName()).log(Level.INFO, "Server is already stop!");
            }
            return;
        }
        this.state = state;
        if (state) {
            this.startServer();
            synchronized (this) {
                this.notify();
            }
        } else {
            try {
                Object[] k = selector.keys().toArray();
                System.out.println(k.length);
                for (int i = 0; i < k.length; i++) {
                    SelectionKey kk = (SelectionKey) k[i];
                    SelectableChannel sc = kk.channel();
                    if (sc instanceof ServerSocketChannel) {
                        ServerSocketChannel ssc = (ServerSocketChannel) sc;
                        System.out.println(ssc);
                        ssc.close();
                        kk.cancel();
                    } else if (sc instanceof SocketChannel) {
                        SocketChannel c = (SocketChannel) sc;
                        System.out.println(c.getRemoteAddress());
                        c.close();
                        kk.cancel();
                    }
                }
//                selector.wakeup();
            } catch (IOException ex) {
                Logger.getLogger(ServerNio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void startServer() {
        Logger.getLogger(ServerNio.class.getName()).log(Level.INFO, "Server Start");
        try {
            serverChannel = ServerSocketChannel.open();
            ss = serverChannel.socket();
            ss.bind(new InetSocketAddress(port));
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException ex) {
            Logger.getLogger(ServerNio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void stopServer() {
        Logger.getLogger(ServerNio.class.getName()).log(Level.INFO, "Server Stop");
        try {
            if (ss != null) {
                ss.close();
            }
            if (selector != null) {
                selector.close();
            }
            if (serverChannel != null) {
                serverChannel.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerNio.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.ss = null;
        this.selector = null;
        this.serverChannel = null;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (!this.state) {
                    this.stopServer();
                    synchronized (ServerNio.this) {
                        ServerNio.this.wait();
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerNio.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                int zz = selector.select();
                
                Object[] k = selector.keys().toArray();
                System.out.println(k.length);
//                for (int i = 0; i < k.length; i++) {
//                    SelectionKey kk = (SelectionKey) k[i];
//                    SelectableChannel sc = kk.channel();
//                    if (sc instanceof ServerSocketChannel) {
//                        System.out.println((ServerSocketChannel) sc);
//                    } else if (sc instanceof SocketChannel) {
//                        System.out.println(((SocketChannel) sc).getRemoteAddress());
//                    }
//                }

                
                Iterator it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey) it.next();
                    it.remove();
                    if (key.isAcceptable()) {
                        Logger.getLogger(ServerNio.class.getName()).log(Level.INFO, "Connected");
                        SocketChannel client = ((ServerSocketChannel) key.channel()).accept();
                        client.configureBlocking(false);
                        SelectionKey clientKey = client.register(selector, SelectionKey.OP_READ);
                        clientKey.attach(new ClientHandler(clientKey));
                    } else if (key.isReadable()) {
                        try {
                            ((ClientHandler) key.attachment()).handleRead();
                        } catch (Exception ex) {
                            ((SocketChannel) key.channel()).close();
                            key.cancel();
                            Logger.getLogger(ServerNio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } catch (ClosedChannelException ex) {
                Logger.getLogger(ServerNio.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ServerNio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
