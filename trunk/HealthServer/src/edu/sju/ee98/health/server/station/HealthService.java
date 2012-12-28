/*
 * Copyright (c) 2012, St. John's University and/or its affiliates. All rights reserved.
 */
package edu.sju.ee98.health.server.station;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 健康服務
 *
 * @author 98405067
 */
public class HealthService extends Thread {

    private boolean state = false;
    private int port;
    private ServerSocketChannel serverChannel;
    private ServerSocket ss;
    private Selector selector;

    /**
     * 建立服務
     *
     */
    public HealthService() {
        this(1201);
    }

    /**
     * 建立服務
     *
     * @param port 埠號
     */
    public HealthService(int port) {
        this.port = port;
        this.start();
    }

    /**
     * 設定服務狀態 開啟或關閉
     *
     * @param state 狀態
     */
    public void state(boolean state) {
        if (state == this.state) {
            if (state) {
                Logger.getLogger(HealthService.class.getName()).log(Level.INFO, "Server is already start!");
            } else {
                Logger.getLogger(HealthService.class.getName()).log(Level.INFO, "Server is already stop!");
            }
            return;
        }
        this.state = state;
        if (state) {
            this.startService();
            synchronized (this) {
                this.notify();
            }
        } else {
            try {
                Object[] keys = selector.keys().toArray();
                for (int i = 0; i < keys.length; i++) {
                    SelectionKey sk = (SelectionKey) keys[i];
                    SelectableChannel sc = sk.channel();
                    if (sc instanceof ServerSocketChannel) {
                        ServerSocketChannel ssc = (ServerSocketChannel) sc;
                        ssc.close();
                        sk.cancel();
                    } else if (sc instanceof SocketChannel) {
                        SocketChannel c = (SocketChannel) sc;
                        c.close();
                        sk.cancel();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(HealthService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * 開啟服務
     *
     */
    private void startService() {
        Logger.getLogger(HealthService.class.getName()).log(Level.INFO, "Server Start");
        try {
            serverChannel = ServerSocketChannel.open();
            ss = serverChannel.socket();
            ss.bind(new InetSocketAddress(port));
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException ex) {
            Logger.getLogger(HealthService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 關閉服務
     *
     */
    private void stopService() {
        Logger.getLogger(HealthService.class.getName()).log(Level.INFO, "Server Stop");
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
            Logger.getLogger(HealthService.class.getName()).log(Level.SEVERE, null, ex);
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
                    this.stopService();
                    synchronized (HealthService.this) {
                        HealthService.this.wait();
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(HealthService.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                selector.select();
                Object[] k = selector.keys().toArray();
                Iterator it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey) it.next();
                    it.remove();
                    if (key.isAcceptable()) {
                        Logger.getLogger(HealthService.class.getName()).log(Level.INFO, "Connected");
                        SocketChannel client = ((ServerSocketChannel) key.channel()).accept();
                        client.configureBlocking(false);
                        SelectionKey clientKey = client.register(selector, SelectionKey.OP_READ);
                        clientKey.attach(new ServiceHandler(clientKey));
                    } else if (key.isReadable()) {
                        try {
                            ((ServiceHandler) key.attachment()).handle();
                        } catch (Exception ex) {
                            ((SocketChannel) key.channel()).close();
                            key.cancel();
                            Logger.getLogger(HealthService.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } catch (ClosedChannelException ex) {
                Logger.getLogger(HealthService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(HealthService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
