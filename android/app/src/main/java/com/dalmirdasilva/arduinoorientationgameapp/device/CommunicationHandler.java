package com.dalmirdasilva.arduinoorientationgameapp.device;

import com.dalmirdasilva.arduinoorientationgameapp.device.message.Message;

public interface CommunicationHandler {

    void onMessageReceived(Message message);

    void onConnectionStateChange(AdapterState state);
}
