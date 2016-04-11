package com.dalmirdasilva.arduinoorientationgameapp.device;

import android.util.Log;

import com.dalmirdasilva.arduinoorientationgameapp.device.message.Message;
import com.dalmirdasilva.arduinoorientationgameapp.device.message.MessageFactory;

import java.util.ArrayList;
import java.util.List;

public class MessageParser {

    private static final String TAG = "MessageParser";

    public enum State {
        INITIAL,
        START_OF_MESSAGE_MARK_PARSED,
        ID_PARSED,
        TYPE_PARSED,
        PAYLOAD_LENGTH_PARSED,
        PAYLOAD_PARSED,
        END_OF_MESSAGE_MARK_PARSED
    }

    private List<Byte> raw;
    private State state;
    private byte payloadLength;
    private int parsedPayloadPosition;

    public MessageParser() {
        reset();
    }

    public void reset() {
        state = State.INITIAL;
    }

    public int parse(byte[] bytes) {
        int i;
        for (i = 0; i < bytes.length; i++) {
            if (!parse(bytes[i])) {
                break;
            }
        }
        return i;
    }

    private boolean parse(byte b) {
        boolean ingested = true;
        switch (state) {
            case INITIAL:
                if (b == Message.START_MESSAGE_MARK) {
                    raw = new ArrayList<>();
                    parsedPayloadPosition = 0;
                    state = State.START_OF_MESSAGE_MARK_PARSED;
                } else {
                    Log.d(TAG, "State is INITIAL but START_MESSAGE_MARK mismatches.");
                    ingested = false;
                }
                break;
            case START_OF_MESSAGE_MARK_PARSED:
                state = State.ID_PARSED;
                break;
            case ID_PARSED:
                state = State.TYPE_PARSED;
                break;
            case TYPE_PARSED:
                state = State.PAYLOAD_LENGTH_PARSED;
                payloadLength = b;
                if (payloadLength > 0) {
                    state = State.PAYLOAD_LENGTH_PARSED;
                } else {
                    state = State.PAYLOAD_PARSED;
                }
                break;
            case PAYLOAD_LENGTH_PARSED:
                if (++parsedPayloadPosition >= payloadLength) {
                    state = State.PAYLOAD_PARSED;
                }
                break;
            case PAYLOAD_PARSED:
                if (b == Message.END_MESSAGE_MARK) {
                    state = State.END_OF_MESSAGE_MARK_PARSED;
                } else {
                    Log.d(TAG, "State is CHECKSUM_PARSED1 but END_MESSAGE_MARK mismatches.");
                    ingested = false;
                }
                break;
            case END_OF_MESSAGE_MARK_PARSED:
                Log.d(TAG, "Message was fully parsed and wasn't yet retrieved, but more data is coming.");
                ingested = false;
                break;
        }
        if (ingested) {
            raw.add(b);
        }
        return ingested;
    }

    public boolean wasMessageDecoded() {
        return state == State.END_OF_MESSAGE_MARK_PARSED;
    }

    public Message getDecodedMessage() {
        if (!wasMessageDecoded()) {
            return null;
        }
        Message message = MessageFactory.newMessageFromType(raw.get(Message.TYPE_POS));
        message.setId(raw.get(Message.ID_POS));
        List<Byte> payload = raw.subList(Message.PAYLOAD_POS, Message.PAYLOAD_POS + payloadLength);
        Byte[] bytes = payload.toArray(new Byte[payloadLength]);
        message.setPayload(bytes);
        reset();
        return message;
    }

    public State getState() {
        return state;
    }
}
