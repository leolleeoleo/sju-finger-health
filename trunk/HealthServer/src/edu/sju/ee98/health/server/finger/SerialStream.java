/*
 * Copyright (c) 2012, St. John's University and/or its affiliates. All rights reserved.
 */
package edu.sju.ee98.health.server.finger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * 串流
 *
 * @author 98405067
 */
public class SerialStream extends SerialPort {

    private Input input;
    private Output output;

    /**
     * 建立串流
     *
     * @param portName 串列埠名稱
     */
    public SerialStream(String portName) {
        super(portName);
        this.input = new Input();
        this.output = new Output();
    }

    /**
     * 輸入資料流
     *
     * @return
     */
    Input getInputStream() {
        return input;
    }

    /**
     * 輸出資料流
     *
     * @return
     */
    Output getOutputStream() {
        return output;
    }

    /**
     * 輸入
     *
     */
    private class Input extends InputStream {

        @Override
        public int available() throws IOException {
            try {
                return getInputBufferBytesCount();
            } catch (SerialPortException ex) {
                throw new IOException(ex);
            }
        }

        @Override
        public int read() throws IOException {
            try {
                return (int) readBytes(1)[0];
            } catch (SerialPortException ex) {
                throw new IOException(ex);
            }
        }
    }

    /**
     * 輸出
     *
     */
    private class Output extends OutputStream {

        @Override
        public void write(int b) throws IOException {
            try {
                writeInt(b);
            } catch (SerialPortException ex) {
                throw new IOException(ex);
            }
        }
    }
}
