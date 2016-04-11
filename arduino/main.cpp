#include <main.h>
#include <Arduino.h>
#include <SoftwareSerial.h>
#include <SoftwareSerialInputStream.h>
#include <SoftwareSerialOutputStream.h>
#include <AccelerometerMMA8451.h>
#include <External24cl256Eeprom.h>
#include <ExternalEepromInputStream.h>
#include <ExternalEepromOutputStream.h>
#include <ByteArrayOutputStream.h>
#include <ByteArrayInputStream.h>
#include <DataOutputStream.h>
#include <DataInputStream.h>
#include <Message.h>
#include <MessageParser.h>
#include <RemoteDevice.h>
#include <Settings.h>

volatile bool triggered = false;
unsigned char buf[6];

unsigned char payloadBuffer[MAX_PAYLOAD_SIZE] = { 0 };
unsigned char parserBuffer[MAX_MESSAGE_SIZE] = { 0 };

AccelerometerMMA8451 acc(0);
External24cl256Eeprom eeprom = External24cl256Eeprom(EXTERNAL_EEPROM_ADDRESS);

SoftwareSerial serial = SoftwareSerial(BLUETOOTH_RX, BLUETOOTH_TX);
SoftwareSerialInputStream softwareSerialInputStream = SoftwareSerialInputStream(&serial);
SoftwareSerialOutputStream softwareSerialOutputStream = SoftwareSerialOutputStream(&serial);

ExternalEepromOutputStream eepromOutputStream = ExternalEepromOutputStream(&eeprom);
ExternalEepromInputStream eepromInputStream = ExternalEepromInputStream(&eeprom);

ByteArrayOutputStream payloadOutputStream = ByteArrayOutputStream((unsigned char *) payloadBuffer, MAX_PAYLOAD_SIZE);
ByteArrayInputStream payloadInputStream = ByteArrayInputStream((unsigned char *) payloadBuffer, MAX_PAYLOAD_SIZE);

DataOutputStream eepromDataOutput = DataOutputStream(&eepromOutputStream);
DataInputStream eepromDataInput = DataInputStream(&eepromInputStream);

DataOutputStream payloadDataOutput = DataOutputStream(&payloadOutputStream);
DataInputStream payloadDataInput = DataInputStream(&payloadInputStream);

Message message(payloadBuffer);
MessageParser parser(parserBuffer, MAX_MESSAGE_SIZE);
RemoteDevice device(&parser, &message, &softwareSerialOutputStream, &softwareSerialInputStream);

Settings settings = Settings(&eepromOutputStream, &eepromInputStream);

void setupAccelerometer() {
    acc.standby();
    acc.setDynamicRange(AccelerometerMMA8451::DR_4G);
    acc.setOutputDataRate(AccelerometerMMA8451::ODR_100HZ_10_MS);
    acc.setOrientationDetection(true, false);
    acc.setOrientationBackFrontCompensation(0x01, 0x04);
    acc.setOrientationThresholdAndHysteresis(0x10, 0x04);
    acc.setOrientationDebounceCounter(0x05);
    acc.enableInterrupt(AccelerometerMMA8451::INT_LNDPRT);
    acc.routeInterruptToInt1(AccelerometerMMA8451::INT_LNDPRT);
    acc.activate();
}

void isr() {
    triggered = true;
}

void setup() {
    Serial.begin(9600);
    pinMode(STATUS_LED_PIN, OUTPUT);
    settings.load();
    device.connect(INFINITY_TIMEOUT);
    setupAccelerometer();
    attachInterrupt(ISR_VECTOR, isr, FALLING);
    Serial.println("initialized");
}

void processSettings(unsigned char setting, unsigned char value) {
    settings.update(setting, value);
    setupAccelerometer();
}

void processReceivedMessage() {
    detachInterrupt(ISR_VECTOR);
    switch (message.getType()) {
    case Message::SETTINGS:
        payloadInputStream.seek(0);
        unsigned char mask = payloadDataInput.readUnsignedChar();
        unsigned char setting = payloadDataInput.readUnsignedChar();
        processSettings(mask, setting);
        Serial.print("mask: ");
        Serial.println(mask, HEX);
        Serial.print("setting: ");
        Serial.println(setting, HEX);
        break;
    }
    attachInterrupt(ISR_VECTOR, isr, FALLING);
}

void loop() {

    if (device.receiveMessage(&message)) {
        processReceivedMessage();
    }

    if (triggered) {
        triggered = false;
        AccelerometerMMA8451::INT_SOURCEbits src;
        src.value = acc.readRegister(AccelerometerMMA8451::INT_SOURCE);
        if (src.SRC_LNDPRT) {
            AccelerometerMMA8451::PL_STATUSbits pls;
            pls.value = acc.readRegister(AccelerometerMMA8451::PL_STATUS);
            Serial.println(pls.value, HEX);
        }
    }
}

int main(void) {
    init();
#if defined(USBCON)
    USBDevice.attach();
#endif
    setup();
    for (;;) {
        loop();
        if (serialEventRun) {
            serialEventRun();
        }
    }
    return 0;
}
