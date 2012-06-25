#include <stdio.h>
#include "base.h"
#include "uart.h"

#define SIZE 8

code unsigned char characterize_cmd[SIZE] = {0xF5, 0x23, 0x00, 0x00, 0x00, 0x00, 0x23, 0xF5};
xdata unsigned char characterize_data[207];

void FINGER_action() {
    UART2_receive(characterize_data, 207);
    UART2_print(characterize_cmd, SIZE);
}

unsigned char FINGER_check() {
    return (characterize_data[206] == 0xF5);
}

unsigned char *getCharacterize() {
    return &characterize_data;
} 