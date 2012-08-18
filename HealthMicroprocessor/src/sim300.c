#include <stdio.h>
#include "base.h"
#include "uart.h"

#define SIM300_SIZE 50

xdata unsigned char SIM300_DATA[SIM300_SIZE];

unsigned char *GPRS_getData() {
    return SIM300_DATA;
}


//Initial===============================================

void GPRS_initial() {
    UART1_print("AT\r\n", 4);
    UART1_print("AT\r\n", 4);
}

void GPRS_initialTCPIP() {
    UART1_receive(SIM300_DATA, SIM300_SIZE);
    UART1_print("AT+CIPSHUT\r\n", 12);
    UART1_print("AT+CSTT=\"internet\"\r\n", 20);
    UART1_print("AT+CIICR\r\n", 10);
}

//Status===============================================

unsigned char *GPRS_getSignalQuality() {
    UART1_receive(SIM300_DATA, SIM300_SIZE);
    UART1_print("AT+CSQ\r\n", 8);
    delay_ms(100);
    SIM300_DATA[1] = 0;
    SIM300_DATA[2] = 0;
    SIM300_DATA[0] = SIM300_DATA[8];
    if (SIM300_DATA[9] != 0x2C) {
        SIM300_DATA[1] = SIM300_DATA[9];
    }
    return SIM300_DATA;
}

unsigned char *GPRS_getOperatorSlelction() {
    unsigned char chk;
    unsigned char i;
    UART1_receive(SIM300_DATA, SIM300_SIZE);
    UART1_print("AT+COPS?\r\n", 10);
    delay_ms(100);
    chk = 0;
    for (i = 0; i < SIM300_SIZE; i++) {
        if (SIM300_DATA[i] == '\"') {
            chk++;
        }
    }
    if (chk == 2) {
        chk = 0;
        i = 0;
        while (SIM300_DATA[i++] != '\"');
        do {
            SIM300_DATA[chk++] = SIM300_DATA[i++];
        } while (SIM300_DATA[i] != '\"');
        SIM300_DATA[chk++] = 0;
    } else {
        for (i = 0; i < SIM300_SIZE; i++) {
            SIM300_DATA[i] = 0;
        }
    }
    return SIM300_DATA;
}

unsigned char *GPRS_getIPAddress() {
    unsigned char i;
    UART1_receive(SIM300_DATA, SIM300_SIZE);
    UART1_print("AT+CIFSR\r\n", 10);
    delay_ms(100);
    for (i = 2; i < SIM300_SIZE; i++) {
        if (SIM300_DATA[i] == 0x0D) {
            SIM300_DATA[i - 2] = 0;
            break;
        }
        SIM300_DATA[i - 2] = SIM300_DATA[i];
    }
    return SIM300_DATA;
}

//Connection=============================================

void GPRS_connect(unsigned char *address, unsigned char *port) {
    UART1_receive(SIM300_DATA, SIM300_SIZE);
    UART1_print("AT+CIPSTART=\"TCP\",\"", 19);
    while (*address != '\0') {
        UART1_put(*address++);
    }
    UART1_print("\",\"", 3);
    while (*port != '\0') {
        UART1_put(*port++);
    }
    UART1_print("\"\r\n", 3);
    printf("GPRS COMMAND : AT+CIPSTART\r\n");
}

unsigned char GPRS_checkConnect() {
    unsigned char i;
    for (i = 0; i < SIM300_SIZE; i++) {
        if (SIM300_DATA[i] == 'C' && SIM300_DATA[i + 1] == 'O') {
            printf("GPRS CONNECT\r\n");
        }
    }
    return 0;
}

void GPRS_send(unsigned char length) {
    UART1_receive(SIM300_DATA, SIM300_SIZE);
    UART1_print("AT+CIPSEND=", 11);
    UART1_print(dec_ascii(length), 3);
    UART1_print("\r\n", 2);
    printf("GPRS COMMAND : AT+CIPSEND\r\n");
}

unsigned char GPRS_checkSend() {
    unsigned char i;
    for (i = 0; i < SIM300_SIZE; i++) {
        if (SIM300_DATA[i] == '>') {
            return 0xFF;
        }
    }
    return 0;
}

void GPRS_write(unsigned char *value, unsigned char length) {
    UART1_receive(SIM300_DATA, SIM300_SIZE);
    UART1_print(value, length);
}

unsigned char GPRS_checkWrite() {
    unsigned char i;
    for (i = 0; i < SIM300_SIZE; i++) {
        if (SIM300_DATA[i] == 'S' && SIM300_DATA[i + 1] == 'E') {
            return 0xFF;
        }
    }
    return 0x00;
}

void GPRS_close() {
    UART1_receive(SIM300_DATA, SIM300_SIZE);
    UART1_print("AT+CIPCLOSE\r\n", 13);
}
