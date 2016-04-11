package com.dalmirdasilva.arduinoorientationgameapp.device;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class BluetoothDeviceAdapter {

    private static final String TAG = "BluetoothDeviceAdapter";

    public static final int MESSAGE_STATE_CHANGE = 0;
    public static final int MESSAGE_RECEIVE = 1;

    private final BluetoothDevice device;
    private final Handler handler;
    private ConnectionThread connectionThread;
    private CommunicationThread communicationThread;

    private AdapterState state;

    public BluetoothDeviceAdapter(CommunicationHandler communicationHandler, BluetoothDevice device) {
        this.device = device;
        this.handler = new MessageHandler(communicationHandler);
        state = AdapterState.STATE_INITIAL;
    }

    private synchronized void setState(AdapterState state) {
        this.state = state;
        handler.obtainMessage(MESSAGE_STATE_CHANGE, state).sendToTarget();
    }

    public synchronized void connect() {
        closeThreads();
        connectionThread = new ConnectionThread();
        connectionThread.start();
        setState(AdapterState.STATE_CONNECTING);
    }

    public synchronized void connected(BluetoothSocket socket) {
        closeThreads();
        communicationThread = new CommunicationThread(socket);
        communicationThread.start();
        setState(AdapterState.STATE_CONNECTED);
    }

    public synchronized boolean isConnected() {
        return state == AdapterState.STATE_CONNECTED;
    }

    public synchronized void close() {
        closeThreads();
        setState(AdapterState.STATE_INITIAL);
    }

    public void write(byte[] out) {
        CommunicationThread communicationThread;
        synchronized (this) {
            if (state != AdapterState.STATE_CONNECTED) {
                return;
            }
            communicationThread = this.communicationThread;
        }
        communicationThread.write(out);
    }

    private void connectionFailed() {
        closeThreads();
        setState(AdapterState.STATE_CONNECTION_FAILED);
    }

    private void connectionLost() {
        setState(AdapterState.STATE_CONNECTION_LOST);
    }

    private void closeThreads() {
        if (connectionThread != null) {
            connectionThread.close();
            connectionThread = null;
        }
        if (communicationThread != null) {
            communicationThread.close();
            communicationThread = null;
        }
    }

    private class ConnectionThread extends Thread {

        private final BluetoothSocket socket;

        public ConnectionThread() {
            BluetoothSocket socket = null;
            ParcelUuid[] uuids = device.getUuids();
            if (uuids != null && uuids.length > 0) {
                try {
                    socket = device.createInsecureRfcommSocketToServiceRecord(uuids[0].getUuid());
                } catch (IOException e) {
                    Log.e(TAG, "Socket: create() failed", e);
                }
            }
            this.socket = socket;
        }

        public void run() {
            Log.i(TAG, "Running connectionThread");
            setName("ConnectionThread");
            if (this.socket != null) {
                try {
                    socket.connect();
                } catch (IOException e) {
                    Log.e(TAG, "unable to connect() socket", e);
                    try {
                        socket.close();
                    } catch (IOException ee) {
                        Log.e(TAG, "unable to close() socket during connection failure", ee);
                    }
                    connectionFailed();
                    return;
                }
                synchronized (BluetoothDeviceAdapter.this) {
                    connectionThread = null;
                }
                connected(socket);
            } else {
                connectionFailed();
                Log.d(TAG, "socket is null, cannot connect");
            }
        }

        public void close() {
            try {
                socket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    private class CommunicationThread extends Thread {

        private final BluetoothSocket socket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public CommunicationThread(BluetoothSocket socket) {
            Log.d(TAG, "Creating CommunicationThread");
            this.socket = socket;
            InputStream is = null;
            OutputStream os = null;
            try {
                is = socket.getInputStream();
                os = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "I/O streams not created.", e);
            }
            inputStream = is;
            outputStream = os;
        }

        public void run() {
            Log.i(TAG, "Running CommunicationThread");
            setName("CommunicationThread");
            byte[] buffer = new byte[1024];
            int bytes;
            while (true) {
                try {
                    bytes = inputStream.read(buffer);
                    byte[] buf = Arrays.copyOfRange(buffer, 0, bytes);

                    Log.d(TAG, "Received raw: " + Util.formatHex(buf));

                    handler.obtainMessage(MESSAGE_RECEIVE, bytes, -1, buf).sendToTarget();
                } catch (IOException e) {
                    Log.e(TAG, "Disconnected", e);
                    connectionLost();
                    break;
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                outputStream.write(buffer);
            } catch (IOException e) {
                Log.e(TAG, "Exception during write.", e);
            }
        }

        public void close() {
            try {
                socket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed.", e);
            }
        }
    }
}
