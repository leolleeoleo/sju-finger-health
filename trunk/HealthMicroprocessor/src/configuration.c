#include "base.h"
#include "iap.h"


#define CONFIGURATION_DATA_SIZE 0x10
#define LENGTH 128

code unsigned char CONFIGURATION_DATA[LENGTH] = "REGISTER0123\0\0\0\000000000000000\0\0\0\0\0\0163.21.76.50\0\0\0\0"; // = {0x12, 0xAB, 0xCD, 0x9A, 0x9A, 0xBC, 0x9A, 0x9A, 0xBC};
xdata unsigned char CONFIGURATION_buffer[LENGTH];

void CONFIGURATION_read() {
    unsigned char i;
    for (i = 0; i < LENGTH; i++) {
        CONFIGURATION_buffer[i] = CONFIGURATION_DATA[i];
    }
}

void CONFIGURATION_write() {
    unsigned char i;
    IAP_EraseSector((unsigned int) &CONFIGURATION_DATA);
    for (i = 0; i < LENGTH; i++) {
        IAP_ProgramUserCode((unsigned int) &CONFIGURATION_DATA[i], CONFIGURATION_buffer[i]);
    }

}

unsigned char *CONFIGURATION_get(unsigned char num) {
    return &CONFIGURATION_buffer[CONFIGURATION_DATA_SIZE * num];
}