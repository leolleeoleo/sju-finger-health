#include <stdio.h>
#include "base.h"
#include "gprs.h"
#include "uart.h"
#include "tfs_m51.h"
#include "configuration.h"

#define STATE_config_type_account 0x00
#define STATE_config_type_password 0x01
#define STATE_config_type_server 0x02

code unsigned char regis[] = "REGISTER";

xdata unsigned char CLIENT_NAME[10];
xdata unsigned char CLIENT_POINT[10];

idata unsigned char CLIENT_count;

void CLIENT_connect() {
    GPRS_connect(CONFIGURATION_get(STATE_config_type_server), "1201");
}

void CLIENT_login() {
    GPRS_send(33);
    delay_ms(200);
    GPRS_write("LOGIN", 5);
    GPRS_write(":", 1);
    GPRS_write(CONFIGURATION_get(STATE_config_type_account), 12);
    GPRS_write(":", 1);
    GPRS_write(CONFIGURATION_get(STATE_config_type_password), 12);
    GPRS_write("\r\n", 2);
}

void CLIENT_register(unsigned char *finger) {
    GPRS_send(211);
    delay_ms(200);
    GPRS_write("REG", 3);
    GPRS_write(":", 1);
    GPRS_write(finger, 207);
}

unsigned char PROTOCOL_action(unsigned char initial) {
    unsigned char i;
    unsigned char j;
    unsigned char *receive = GPRS_getData();
    if (initial) {
        CLIENT_count = 0;
        CLIENT_connect();
    }
    switch (CLIENT_count) {
        case 0:
            for (i = 0; i < 50; i++) {
                if (receive[i] == 'T' && receive[i + 1] == 'E' && receive[i + 2] == 'D') {
                    CLIENT_login();
                    CLIENT_count++;
                }
            }
            break;
        case 1:
            for (i = 0; i < 50; i++) {
                if (receive[i] == 'S' && receive[i + 1] == 'U' && receive[i + 2] == 'C') {
                    CLIENT_register(getCharacterize());
                    CLIENT_count++;
                }
            }
            break;
        case 2:
            for (i = 0; i < 50; i++) {
                if (receive[i] == 'S' && receive[i + 1] == 'U' && receive[i + 2] == 'C') {
                    CLIENT_count++;
                }
                if (receive[i] == 'F' && receive[i + 1] == 'A' && receive[i + 2] == 'I') {
                    GPRS_close();
                    return 2;
                }
            }
            break;
        case 3:
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

            //Ãö³¬³s½u
            GPRS_close();
            CLIENT_count++;
            return 1;
            break;
        case 4:
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