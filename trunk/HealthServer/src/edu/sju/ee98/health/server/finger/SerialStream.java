/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.server.finger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author Leo
 */
public class SerialStream extends SerialPort {

    private Input input;
    private Output output;

    public SerialStream(String portName) {
        super(portName);
        this.input = new Input();
        this.output = new Output();
    }

    public Input getInputStream() {
        return input;
    }

    public Output getOutputStream() {
        return output;
    }

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
