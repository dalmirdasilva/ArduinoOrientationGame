################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../Settings/Settings.cpp 

OBJS += \
./Settings/Settings.o 

CPP_DEPS += \
./Settings/Settings.d 


# Each subdirectory must supply rules for building sources it contributes
Settings/%.o: ../Settings/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: AVR C++ Compiler'
	avr-g++ -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoAccelerometerDriver/Accelerometer" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoMessagingProtocol/Message" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoMessagingProtocol/MessageParser" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoMessagingProtocol/Notifier" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoMessagingProtocol/RemoteDevice" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoAccelerometerDriver/AccelerometerADXL335" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoAccelerometerDriver/AccelerometerMMA7455" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoAccelerometerDriver/AccelerometerMMA8451" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoAccelerometerDriver/AccelerometerMPU9250" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoAccelerometerDriver/AccelerometerNunchuk" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/BufferedInputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/BufferedOutputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/ByteArrayInputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/ByteArrayOutputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/Closeable" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/ExternalEepromInputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/ExternalEepromOutputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/FilterInputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/FilterOutputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/HardwareSerialInputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/HardwareSerialOutputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/InputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/InternalEepromInputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/InternalEepromOutputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/OutputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/PgmspaceInputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/PgmspaceSeekableInputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/RandomAccess" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/RandomAccessByteArray" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/RandomAccessExternalEeprom" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/RandomAccessResource" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/RegisterBasedWiredDeviceInputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/ResourceInputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/ResourceOutputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/ResourceSeekableInputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/Seekable" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/SeekableOutputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/SeekableInputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/SerialInputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/SerialOutputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/SoftwareSerialInputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/SoftwareSerialOutputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/WireInputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/WireOutputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoCore" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoCore/libraries/Wire/src" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoCore/variants/standard" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoDevice/EepromBasedWiredDevice" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoDevice/RegisterBasedWiredDevice" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoDevice/WiredDevice" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoMagnetometer/Magnetometer" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoMagnetometer/MagnetometerHMC5883L" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoMagnetometer/MagnetometerHMC5983" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoCore/libraries/SoftwareSerial/src" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/DataInput" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/DataInputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/DataOutput" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoIO/DataOutputStream" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoMemoryDriver/External24cl256Eeprom" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoMemoryDriver/External24x16Eeprom" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoMemoryDriver/ExternalByteArrayEeprom" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoMemoryDriver/ExternalEeprom" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoMemoryDriver/ExternalFileEeprom" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoMemoryDriver/ExternalMappedEeprom" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoOrientationGame/arduino" -I"/work/opensource/personal/microcontroller/arduino/ArduinoLibraries/ArduinoOrientationGame/arduino/Settings" -Wall -Os -fpack-struct -fshort-enums -ffunction-sections -fdata-sections -funsigned-char -funsigned-bitfields -fno-exceptions -mmcu=atmega328p -DF_CPU=16000000UL -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@)" -c -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


