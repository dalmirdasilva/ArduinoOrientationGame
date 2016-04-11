package com.dalmirdasilva.arduinoorientationgameapp.device;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import com.dalmirdasilva.arduinoorientationgameapp.device.message.DataMessage;
import com.dalmirdasilva.arduinoorientationgameapp.device.message.Message;
import com.dalmirdasilva.arduinoorientationgameapp.device.message.MessageFactory;

import java.util.List;

public class BluetoothManager implements CommunicationHandler {

    public static final String MAC_ADDRESS = "00:12:01:31:01:08";
    private static final String TAG = "BluetoothManager";

    private final OnBluetoothManagerInteractionListener interactionListener;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDeviceAdapter bluetoothDeviceAdapter;

    public interface OnBluetoothManagerInteractionListener {

        void setState(AdapterState state);

        boolean processSamples(List<Long> samples);
    }

    public BluetoothManager(OnBluetoothManagerInteractionListener interactionListener) {
        if (interactionListener == null) {
            throw new RuntimeException("On BluetoothManager interactionListener cannot be null");
        }
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.interactionListener = interactionListener;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    public boolean isConnected() {
        return bluetoothDeviceAdapter != null && bluetoothDeviceAdapter.isConnected();
    }

    public void setupDevice() {
        if (bluetoothDeviceAdapter != null) {
            bluetoothDeviceAdapter.close();
        }
        BluetoothDevice remoteDevice = bluetoothAdapter.getRemoteDevice(MAC_ADDRESS);
        bluetoothDeviceAdapter = new BluetoothDeviceAdapter(this, remoteDevice);
        bluetoothDeviceAdapter.connect();
    }

    public void onConnectionStateChange(AdapterState state) {
        interactionListener.setState(state);
        if (state == AdapterState.STATE_CONNECTED) {
            sendMessage(MessageFactory.newConnectMessage());
        }
    }

    public void onMessageReceived(Message message) {
        processReceivedMessage(message);
    }

    private void processReceivedMessage(Message message) {
        switch (message.getType()) {
            case Message.MESSAGE_TYPE_CONNECT:
                sendMessage(MessageFactory.newConnectMessage());
                break;
            case Message.MESSAGE_TYPE_EPOCH:
                sendMessage(MessageFactory.newEpochMessage());
                break;
            case Message.MESSAGE_TYPE_DATA:
                if (interactionListener.processSamples(((DataMessage) message).getSamples())) {
                    sendMessage(MessageFactory.newAckMessage());
                }
                break;
            case Message.MESSAGE_TYPE_PING:
                sendMessage(MessageFactory.newAckMessage());
                break;
        }
    }

    public void sendMessage(Message message) {
        byte[] raw = message.toRaw();
        Log.d(TAG, "Sending message: " + message.toString());
        if (bluetoothDeviceAdapter != null) {
            bluetoothDeviceAdapter.write(raw);
        }
    }

    public void disconnect() {
        if (bluetoothDeviceAdapter != null) {
            bluetoothDeviceAdapter.close();
        }
    }
}
