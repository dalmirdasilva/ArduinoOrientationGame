package com.dalmirdasilva.arduinoorientationgameapp.device.message;

public class SettingsMessage extends Message {

    public static final byte SETTING_THRESHOLD_BIT = 0x01;
    public static final byte SETTING_COUNT_BIT = 0x02;
    public static final byte SETTING_AXIS_BIT = 0x04;
    public static final byte SETTING_MIN_INTERVAL_BIT = 0x08;

    public static final byte AXIS_X_BIT = 0x01;
    public static final byte AXIS_Y_BIT = 0x02;
    public static final byte AXIS_Z_BIT = 0x04;
    public static final byte AXIS_POLARITY_BIT = 0x08;


    public enum SettingType {

        THRESHOLD(SETTING_THRESHOLD_BIT),
        COUNT(SETTING_COUNT_BIT),
        AXIS(SETTING_AXIS_BIT),
        MIN_INTERVAL(SETTING_MIN_INTERVAL_BIT);

        protected byte mask;

        private SettingType(byte mask) {
            this.mask = mask;
        }
    }

    private SettingType settingType;
    private byte settingValue;

    public SettingsMessage() {
        this(SettingType.THRESHOLD, (byte) 0x07);
    }

    public SettingsMessage(SettingType settingType, byte settingValue) {
        super(MESSAGE_TYPE_SETTINGS);
        this.settingType = settingType;
        this.settingValue = settingValue;
    }

    public SettingsMessage(Byte id, Byte type, Byte[] payload) {
        super(id, MESSAGE_TYPE_SETTINGS, payload);
    }

    public void setSettingType(SettingType settingType) {
        this.settingType = settingType;
    }

    public SettingType getSettingType() {
        return this.settingType;
    }

    public void setSettingValue(int settingValue) {
        this.settingValue = (byte) (settingValue & 0xff);
    }

    public int getSettingValue() {
        return (int) (settingValue & 0xff);
    }

    public void setPayload(Byte[] payload) {
        super.setPayload(payload);
        if (payload.length == 2) {
            if ((payload[0] & SETTING_THRESHOLD_BIT) > 0) {
                this.settingType = SettingType.THRESHOLD;
            } else if ((payload[0] & SETTING_COUNT_BIT) > 0) {
                this.settingType = SettingType.COUNT;
            } else if ((payload[0] & SETTING_AXIS_BIT) > 0) {
                this.settingType = SettingType.AXIS;
            } else if ((payload[0] & SETTING_MIN_INTERVAL_BIT) > 0) {
                this.settingType = SettingType.MIN_INTERVAL;
            }
            settingValue = payload[1];
        }
    }

    public byte[] toRaw() {
        this.payload = new Byte[]{settingType.mask, settingValue};
        return super.toRaw();
    }

}
