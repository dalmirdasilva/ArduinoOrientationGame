#include <main.h>
#include <Arduino.h>
#include "Settings.h"

Settings::Settings(SeekableOutputStream *outputStream, SeekableInputStream *inputStream)
        : outputStream(outputStream), inputStream(inputStream), threshold(0), debounceCounter(0), tripAngleThreshold(0), zLockAngle(0), hysteresis(0), minInterval(0) {
}

void Settings::load() {
    inputStream->mark();
    inputStream->seek(EEPROM_SETTINGS_ADDRESS);
    threshold = inputStream->read() & 0xff;
    debounceCounter = inputStream->read() & 0xff;
    tripAngleThreshold = inputStream->read() & 0xff;
    zLockAngle = inputStream->read() & 0xff;
    hysteresis = inputStream->read() & 0xff;
    minInterval = inputStream->read() & 0xff;
    inputStream->reset();
}

void Settings::save() {
    outputStream->mark();
    outputStream->seek(EEPROM_SETTINGS_ADDRESS);
    outputStream->write(threshold);
    outputStream->write(debounceCounter);
    outputStream->write(tripAngleThreshold);
    outputStream->write(zLockAngle);
    outputStream->write(hysteresis);
    outputStream->write(minInterval);
    outputStream->reset();
}

void Settings::update(unsigned char setting, unsigned char value) {
    if (setting & SETTING_THRESHOLD_BIT) {
        threshold = value;
    } else if (setting & SETTING_DEBOUNCE_COUNTER_BIT) {
        debounceCounter = value;
    } else if (setting & SETTING_TRIP_ANGLE_THRESHOLD_BIT) {
        tripAngleThreshold = value;
    } else if (setting & SETTING_Z_LOCK_ANGLE_BIT) {
        zLockAngle = value;
    } else if (setting & SETTING_HYSTERESIS_BIT) {
        hysteresis = value;
    } else if (setting & SETTING_MIN_INTERVAL_BIT) {
        minInterval = value;
    }
    save();
}

unsigned char Settings::getThreshold() {
    return threshold;
}

unsigned char Settings::getDebounceCounter() {
    return debounceCounter;
}

unsigned char Settings::getTripAngleThreshold() {
    return tripAngleThreshold;
}

unsigned char Settings::getZLockAngle() {
    return zLockAngle;
}

unsigned char Settings::getHysteresis() {
    return hysteresis;
}

unsigned char Settings::getMinInterval() {
    return minInterval;
}

unsigned int Settings::getMinIntervalInMillis() {
    return minInterval * SETTING_MIN_INTERVAL_TO_MILLIS;
}
