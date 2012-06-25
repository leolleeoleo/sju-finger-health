#include <stdio.h>
#include "sim300.h"
#include "uart.h"
#include "config.h"
#include "tfs_m51.h"

//code unsigned char acc[] = "register1\r\n";
//code unsigned char pass[] = "00000000\r\n";
code unsigned char regis[] = "REGISTER";
//code unsigned char fin[] = "aaaaaaaaaAAAAAAAAAABBBBBBBBBBAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaa\r\n";

xdata unsigned char CLIENT_NAME[10];
xdata unsigned char CLIENT_POINT[10];

idata unsigned char CLIENT_count;

unsigned char PROTOCOL_action(unsigned char initial) {
    unsigned char i;
    unsigned char j;
    unsigned char *receive = GPRS_getReceive();

    if (initial) {
        CLIENT_count = 0;
        GPRS_connect(1);
    }
    switch (CLIENT_count) {
        case 0:
            if (GPRS_connect(0)) {
                CLIENT_count++;
            }
            break;
        case 1:
            for (i = 0; i < 50; i++) {
                if (receive[i] == 'A' && receive[i + 1] == 'C' && receive[i + 2] == 'C') {
                    CONFIG_read();
                    GPRS_send(1, CONFIG_getAccount(), 12);
                    CLIENT_count++;
                }
            }
            break;
        case 2:
            //傳送帳號
            if (GPRS_send(0, CONFIG_getAccount(), 12)) {
                CLIENT_count++;
            }
            break;
        case 3:
            for (i = 0; i < 50; i++) {
                if (receive[i] == 'P' && receive[i + 1] == 'A' && receive[i + 2] == 'S') {
                    CONFIG_read();
                    GPRS_send(1, CONFIG_getPassword(), 12);
                    CLIENT_count++;
                }
            }
            break;
        case 4:
            //傳送密碼
            if (GPRS_send(0, CONFIG_getPassword(), 12)) {
                CLIENT_count++;
            }
            break;
        case 5:
            for (i = 0; i < 50; i++) {
                if (receive[i] == 'L' && receive[i + 1] == 'O' && receive[i + 2] == 'G') {
                    GPRS_send(1, regis, 8);
                    CLIENT_count++;
                }
            }
            break;
        case 6:
            if (GPRS_send(0, regis, 8)) {
                CLIENT_count++;
            }
            break;
        case 7:
            for (i = 0; i < 50; i++) {
                if (receive[i] == 'R' && receive[i + 1] == 'E' && receive[i + 2] == 'G') {
                    GPRS_send(1, getCharacterize(), 207);
                    CLIENT_count++;
                }
            }
            break;
        case 8:
            if (GPRS_send(0, getCharacterize(), 207)) {
                CLIENT_count++;
            }
            break;
        case 9:
            for (i = 0; i < 50; i++) {
                if (receive[i] == 'S' && receive[i + 1] == 'U' && receive[i + 2] == 'C') {
                    CLIENT_count++;
                }
                if (receive[i] == 'C' && receive[i + 1] == 'L' && receive[i + 2] == 'O') {
                    return 2;
                }
            }
            break;
        case 10:
            do {
                if (++i > 48) {
                    i = 0;
                }
            } while (receive[i] != 'N' || receive[i + 1] != 'A' || receive[i + 2] != 'M' || receive[i + 3] != 'E');
            i += 4;
            j = 0;
            while (receive[i] != '\r' || receive[i + 1] != '\n') {
                CLIENT_NAME[j++] = receive[i];
                putchar(receive[i++]);
            }
            CLIENT_NAME[j++] = '\0';
            do {
                if (++i > 48) {
                    i = 0;
                }
            } while (receive[i] != 'P' || receive[i + 1] != 'O' || receive[i + 2] != 'I' || receive[i + 3] != 'N' || receive[i + 4] != 'T');
            i += 5;
            j = 0;
            while (receive[i] != '\r' || receive[i + 1] != '\n') {
                CLIENT_POINT[j++] = receive[i];
                putchar(receive[i++]);
            }
            CLIENT_POINT[j++] = '\0';

            //關閉連線
            GPRS_close();
            return 1;
            break;
    }
    return 0;
}

unsigned char * CLIENT_getName() {
    return CLIENT_NAME;
}

unsigned char * CLIENT_getPoint() {
    return CLIENT_POINT;
}