package com.dalmirdasilva.arduinoorientationgameapp.device.message;

import java.util.ArrayList;
import java.util.List;

public class DataMessage extends Message {

    public DataMessage() {
        super(Message.MESSAGE_TYPE_DATA);
    }

    public List<Long> getSamples() {
        List<Long> samples = new ArrayList<>();
        long shortEpoch = extractEpoch();
        List<Long> raws = extractRawSamples();
        long epoch = EpochMessage.makeSystemEpoch(shortEpoch);
        for (long raw : raws) {
            long sample = epoch + raw;
            samples.add(sample);
        }
        return samples;
    }

    public List<Long> extractRawSamples() {
        List<Long> rawSamples = new ArrayList<>();
        Byte[] payload = getPayload();
        int headerSize = Message.EPOCH_SIZE + Message.FLAG_SIZE;
        int samplesSize = payload.length - headerSize;
        if (samplesSize > 0) {
            for (int i = 0; i < samplesSize; i += 4) {
                long sample = payload[headerSize + i + 3] & 0xff;
                sample <<= 8;
                sample |= payload[headerSize + i + 2] & 0xff;
                sample <<= 8;
                sample |= payload[headerSize + i + 1] & 0xff;
                sample <<= 8;
                sample |= payload[headerSize + i] & 0xff;
                rawSamples.add(sample);
            }
        }
        return rawSamples;
    }

    public boolean isLastMessage() {
        return (payload[FLAG_IN_PAYLOAD_POS] & LAST_MESSAGE_BIT) > 0;
    }

    public long extractEpoch() {
        long epoch = payload[3] & 0xff;
        epoch <<= 8;
        epoch |= payload[2] & 0xff;
        epoch <<= 8;
        epoch |= payload[1] & 0xff;
        epoch <<= 8;
        epoch |= payload[0] & 0xff;
        return epoch;
    }
}
