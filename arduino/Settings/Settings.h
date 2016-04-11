/**
 * Settings
 */

#ifndef __SETTINGS_H__
#define __SETTINGS_H__ 1

#include <SeekableOutputStream.h>
#include <SeekableInputStream.h>
#include <Arduino.h>

#define SETTING_TRIP_ANGLE_THRESHOLD_BIT    0x01
#define SETTING_Z_LOCK_ANGLE_BIT            0x02
#define SETTING_THRESHOLD_BIT               0x04
#define SETTING_HYSTERESIS_BIT              0x08
#define SETTING_DEBOUNCE_COUNTER_BIT        0x10
#define SETTING_MIN_INTERVAL_BIT            0x20

#define SETTING_MIN_INTERVAL_TO_MILLIS      0x0a

class Settings {

    SeekableOutputStream *outputStream;
    SeekableInputStream *inputStream;

    unsigned char threshold;
    unsigned char debounceCounter;
    unsigned char tripAngleThreshold;
    unsigned char zLockAngle;
    unsigned char hysteresis;
    unsigned char minInterval;

public:

    Settings(SeekableOutputStream *outputStream, SeekableInputStream *inputStream);

    void load();

    void save();

    void update(unsigned char mask, unsigned char setting);

    unsigned char getThreshold();

    unsigned char getDebounceCounter();

    unsigned char getTripAngleThreshold();

    unsigned char getZLockAngle();

    unsigned char getHysteresis();

    unsigned char getMinInterval();

    unsigned int getMinIntervalInMillis();

    void dump() {
        Serial.print("threshold: ");
        Serial.println(threshold);
        Serial.print("debounceCounter: ");
        Serial.println(debounceCounter);
        Serial.print("tripAngleThreshold: ");
        Serial.println(tripAngleThreshold);
        Serial.print("zLockAngle: ");
        Serial.println(zLockAngle);
        Serial.print("hysteresis: ");
        Serial.println(hysteresis);
        Serial.print("minInterval: ");
        Serial.println(minInterval);
    }
};

#endif // __SETTINGS_H__
