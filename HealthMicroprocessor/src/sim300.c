#include <stdio.h>
#include "base.h"
#include "uart.h"
#include "config.h"

#define SIM300_RECEIVE 50

xdata unsigned char a[SIM300_RECEIVE];

idata unsigned char GPRS_count;

unsigned char *GPRS_getReceive() {
    return a;
}

unsigned char GPRS_initial(unsigned char initial) {
    unsigned char i = 0;
    unsigned char chk;
    if (initial) {
        GPRS_count = 0;
        UART1_print("AT\r\n", 4);
        printf("GPRS COMMAND : AT\r\n");
    }
    switch (GPRS_count) {
        case 0:
            UART1_receive(a, SIM300_RECEIVE);
            UART1_print("AT\r\n", 4);
            printf("GPRS COMMAND : AT\r\n");
            chk = 0;
            GPRS_count++;
            break;
        case 1:
            UART1_receive(a, SIM300_RECEIVE);
            UART1_print("AT\r\n", 4);
            printf("GPRS COMMAND : AT\r\n");
            chk = 0;
            GPRS_count++;
            break;
        case 2:
            for (i = 0; i < SIM300_RECEIVE; i++) {
                if (a[i] == 'O' && a[i + 1] == 'K') {
                    UART1_receive(a, SIM300_RECEIVE);
                    UART1_print("AT+CSTT=\"internet\"\r\n", 20);
                    printf("GPRS COMMAND : AT+CSTT\r\n");
                    GPRS_count++;
                    chk = 0;
                    return 0;
                }
            }
            break;
        case 3:
            for (i = 0; i < SIM300_RECEIVE; i++) {
                if (a[i] == 'O' && a[i + 1] == 'K') {
                    UART1_receive(a, SIM300_RECEIVE);
                    UART1_print("AT+CIICR\r\n", 10);
                    printf("GPRS COMMAND : AT+CIICR\r\n");
                    GPRS_count++;
                    chk = 0;
                    return 0;
                }
            }
            break;
        case 4:
            for (i = 0; i < SIM300_RECEIVE; i++) {
                if (a[i] == 'O' && a[i + 1] == 'K') {
                    UART1_receive(a, SIM300_RECEIVE);
                    UART1_print("AT+CIFSR\r\n", 10);
                    printf("GPRS COMMAND : AT+CIFSR\r\n");
                    GPRS_count++;
                    return 0;
                }
            }
            break;
        case 5:
            chk = 0;
            for (i = 0; i < SIM300_RECEIVE; i++) {
                if (a[i] == '\r' || a[i] == '\n' || a[i] == '.') {
                    chk++;
                }
            }
            if (chk >= 7) {
                printf("GPRS INITIALED\r\n");
                GPRS_count++;
            }
            break;
        case 6:
            return 1;
            break;
    }
    return 0;
}

unsigned char GPRS_connect(unsigned char initial) {
    unsigned char i;
    unsigned char *ip;
    if (initial) {
        GPRS_count = 0;
        CONFIG_read();
        ip = CONFIG_getServer();
        UART1_receive(a, SIM300_RECEIVE);
        UART1_print("AT+CIPSTART=\"TCP\",\"", 19);
        while (*ip != '\0') {
            UART1_put(*ip++);
        }
        UART1_print("\",\"1201\"\r\n", 10);
        printf("GPRS COMMAND : AT+CIPSTART\r\n");
    }
    switch (GPRS_count) {
        case 0:
            for (i = 0; i < SIM300_RECEIVE; i++) {
                if (a[i] == 'C' && a[i + 1] == 'O') {
                    printf("GPRS CONNECT\r\n");
                    GPRS_count++;
                }
            }
            break;
        case 1:
            return 1;
            break;
    }
    return 0;
}

unsigned char GPRS_send(unsigned char initial, unsigned char *send, unsigned char length) {
    unsigned char i;
    if (initial) {
        GPRS_count = 0;
        UART1_receive(a, SIM300_RECEIVE);
        UART1_print("AT+CIPSEND=", 11);
        UART1_print(dec_ascii(length + 2), 3);
        UART1_print("\r\n", 2);
        printf("GPRS COMMAND : AT+CIPSEND\r\n");
    }
    switch (GPRS_count) {
        case 0:
            for (i = 0; i < SIM300_RECEIVE; i++) {
                if (a[i] == '>') {
                    UART1_print(send, length);
                    UART1_receive(a, SIM300_RECEIVE);
                    UART1_print("\r\n", 2);
                    GPRS_count++;
                }
            }
            break;
        case 1:
            for (i = 0; i < SIM300_RECEIVE; i++) {
                if (a[i] == 'S' && a[i + 1] == 'E') {
                    printf("GPRS SEND\r\n");
                    GPRS_count++;
                }
            }
            break;
        case 2:
            return 1;
            break;
    }
    return 0;
}

void GPRS_close() {
    UART1_receive(a, 10);
    UART1_print("AT+CIPCLOSE\r\n", 13);
    delay_ms(1000);
}

