#include "base.h"
#include "config.h"
#include "iap.h"

#define LENGTH 120

#define START_ACCOUNT 0x00
#define START_PASSWORD 0x10
#define START_SERVER 0x20


code unsigned char config[LENGTH] = "REGISTER0123\0\0\0\000000000000000\0\0\0\0\0\0163.21.76.50\0\0\0\0"; // = {0x12, 0xAB, 0xCD, 0x9A, 0x9A, 0xBC, 0x9A, 0x9A, 0xBC};
xdata unsigned char buffer[LENGTH];
xdata unsigned char set[CONFIG_DATA_SIZE];

void CONFIG_read() {
    unsigned char i;
    for (i = 0; i < LENGTH; i++) {
        buffer[i] = config[i];
    }
}

void CONFIG_write() {
    unsigned char i;
    IAP_EraseSector((unsigned int) &config);
    for (i = 0; i < LENGTH; i++) {
        IAP_ProgramUserCode((unsigned int) &config[i], buffer[i]);
    }

}

void CONFIG_getSet(unsigned char *pointer) {
    unsigned char i;
    for (i = 0; i < CONFIG_DATA_SIZE; i++) {
        set[i] = *pointer++;
    }
}

//account++++++++++++++++++++++++++++++++++++++++++++++++

void CONFIG_setAccount(unsigned char *account) {
    unsigned char i;
    for (i = START_ACCOUNT; i < START_ACCOUNT + CONFIG_DATA_SIZE; i++) {
        buffer[i] = *account++;
    }
    buffer[--i] = '\0';
}

unsigned char *CONFIG_getAccount() {
    CONFIG_getSet(&buffer[START_ACCOUNT]);
    return &set;
}

//password++++++++++++++++++++++++++++++++++++++++++++++++

void CONFIG_setPassword(unsigned char *password) {
    unsigned char i;
    for (i = START_PASSWORD; i < START_PASSWORD + CONFIG_DATA_SIZE; i++) {
        buffer[i] = *password++;
    }
    buffer[--i] = '\0';
}

unsigned char *CONFIG_getPassword() {
    CONFIG_getSet(&buffer[START_PASSWORD]);
    return &set;
}

//server+++++++++++++++++++++++++++++++++++++++++++++++++

void CONFIG_setServer(unsigned char *server) {
    unsigned char i;
    for (i = START_SERVER; i < START_SERVER + CONFIG_DATA_SIZE; i++) {
        buffer[i] = *server++;
    }
    buffer[--i] = '\0';
}

unsigned char *CONFIG_getServer() {
    CONFIG_getSet(&buffer[START_SERVER]);
    return &set;
}
