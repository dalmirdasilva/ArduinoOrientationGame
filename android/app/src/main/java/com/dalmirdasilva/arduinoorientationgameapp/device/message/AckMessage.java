package com.dalmirdasilva.arduinoorientationgameapp.device.message;

public class AckMessage extends Message {

    public AckMessage() {
        super(Message.MESSAGE_TYPE_ACK);
    }
}
