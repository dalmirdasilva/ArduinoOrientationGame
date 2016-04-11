package com.dalmirdasilva.arduinoorientationgameapp.device.message;

import java.util.Arrays;

/**
 * Message raw components:
 * [
 * START_OF_MESSAGE_MARK {1}
 * ID {1}
 * TYPE {1}
 * PAYLOAD_LENGTH {2}
 * PAYLOAD {PAYLOAD_LENGTH}
 * END_OF_MESSAGE_MARK {1}
 * ]
 */
public abstract class Message {

    public static final byte MESSAGE_TYPE_DATA = 0x00;
    public static final byte MESSAGE_TYPE_ACK = 0x01;
    public static final byte MESSAGE_TYPE_CONNECT = 0x02;
    public static final byte MESSAGE_TYPE_SYNC = 0x03;
    public static final byte MESSAGE_TYPE_EPOCH = 0x04;
    public static final byte MESSAGE_TYPE_SETTINGS = 0x05;
    public static final byte MESSAGE_TYPE_PING = 0x06;

    public static final byte START_MESSAGE_MARK = (byte) 0xaa;
    public static final byte END_MESSAGE_MARK = (byte) 0xbb;

    public static final int ID_POS = 0x01;
    public static final int TYPE_POS = 0x02;
    public static final int PAYLOAD_LENGTH_POS = 0x03;
    public static final int PAYLOAD_POS = 0x04;

    public static final int FLAG_IN_PAYLOAD_POS = 0x05;

    public static final int STATIC_MESSAGE_SIZE = 0x05;

    public static final byte LAST_MESSAGE_BIT = 0x01;

    public static final byte EPOCH_SIZE = 0x04;
    public static final byte FLAG_SIZE = 0x01;
    public static final byte SAMPLE_SIZE = 0x04;

    protected static byte NEXT_ID = 0x00;

    protected Byte id;
    protected Byte type;
    protected Byte[] payload;

    protected Message(Byte type) {
        this(NEXT_ID++, type, new Byte[] {});
    }

    public Message(Byte id, Byte type, Byte[] payload) {
        this.id = id;
        this.type = type;
        this.payload = payload;
    }

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte[] getPayload() {
        return payload;
    }

    public void setPayload(Byte[] payload) {
        this.payload = payload;
    }

    public byte[] toRaw() {
        int i = 0, rawSize = STATIC_MESSAGE_SIZE + payload.length;
        byte[] raw = new byte[rawSize];
        raw[i++] = START_MESSAGE_MARK;
        raw[i++] = id;
        raw[i++] = type;
        raw[i++] = (byte) payload.length;
        for (Byte b : payload) {
            raw[i++] = b;
        }
        raw[i++] = END_MESSAGE_MARK;
        return raw;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", type=" + type +
                ", payload=" + Arrays.toString(payload) +
                '}';
    }
}
