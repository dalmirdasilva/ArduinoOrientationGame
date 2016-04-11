package com.dalmirdasilva.arduinoorientationgameapp.device;

import android.util.Log;

import com.dalmirdasilva.arduinoorientationgameapp.device.message.Message;

import java.lang.ref.WeakReference;

public class MessageHandler extends android.os.Handler {

    private static final String TAG = "MessageHandler";
    private final WeakReference<CommunicationHandler> communicationHandler;
    private final MessageParser parser;

    public MessageHandler(CommunicationHandler communicationHandler) {
        this.communicationHandler = new WeakReference<CommunicationHandler>(communicationHandler);
        this.parser = new MessageParser();
    }

    private void processIncomingBytes(byte[] buf) {
        int decoded = parser.parse(buf);
        if (parser.wasMessageDecoded()) {
            Message decodedMessage = parser.getDecodedMessage();
            communicationHandler.get().onMessageReceived(decodedMessage);
        } else if (decoded != buf.length) {
            Log.e(TAG, "Decoded bytes shorter than buffer length and none message decoded. It should not happen!");
            parser.reset();
        }
    }

    @Override
    public void handleMessage(android.os.Message msg) {
        switch (msg.what) {
            case BluetoothDeviceAdapter.MESSAGE_STATE_CHANGE:
                communicationHandler.get().onConnectionStateChange((AdapterState) msg.obj);
                break;
            case BluetoothDeviceAdapter.MESSAGE_RECEIVE:
                Log.d(TAG, "message received_");
                byte[] buf = (byte[]) msg.obj;
                processIncomingBytes(buf);
                break;
        }
    }
}
