#ifndef __MAIN_H__
#define __MAIN_H__ 1

#define INFINITY_TIMEOUT                0
#define CONNECTION_TRY_TIMEOUT          100
#define IS_CONNECTED_TIMEOUT            500

#define EXTERNAL_EEPROM_ADDRESS         0xa0

#define ISR_VECTOR                      0

#define BLUETOOTH_RX                    0x04
#define BLUETOOTH_TX                    0x05

#define STATIC_MESSAGE_SIZE             0x05
#define MAX_MESSAGE_SIZE                64
#define MAX_PAYLOAD_SIZE                MAX_MESSAGE_SIZE - STATIC_MESSAGE_SIZE

#define EEPROM_SETTINGS_ADDRESS         0x08

#define STATUS_LED_PIN                  13

#endif // __MAIN_H__
