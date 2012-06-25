#include <stdio.h>
#include "base.h"
#include "sc16is752.h"
#include "sc16is752reg.h"

void UART_initial(unsigned int bps) {
    unsigned int time = 65536 - (XTAL / 32 / bps);
    SCON = 0x50;
    T2CON = 0X34;
    RCAP2L = time;
    RCAP2H = time >> 8;
    TI = 1;
}

//UART1+++++++++++++++++++++++++++++++++++++++++++++++
idata unsigned char *UART1_addr;
idata unsigned char UART1_size;

void UART1_initial(unsigned long baud) {
    SC16IS752_initial(baud, 0);
}

void UART1_put(unsigned put) {
    SC16IS752_write(THR, 0, put);
}

void UART1_print(unsigned char *str, unsigned char length) {
    while (length-- > 0) {
        UART1_put(*str++);
    }
}

void UART1_receive(unsigned char *receive, unsigned char size) {
    unsigned char i;
    UART1_addr = receive;
    UART1_size = size;
    for (i = 0; i < size; i++) {
        receive[i] = 0;
    }
}

//UART2+++++++++++++++++++++++++++++++++++++++++++++++
idata unsigned char *UART2_addr;
idata unsigned char UART2_size;

void UART2_initial(unsigned long baud) {
    SC16IS752_initial(baud, 1);
}

void UART2_put(unsigned char put) {
    SC16IS752_write(THR, 1, put);
}

void UART2_print(unsigned char *str, unsigned char length) {
    while (length-- > 0) {
        UART2_put(*str++);
    }
}

void UART2_receive(unsigned char *receive, unsigned char size) {
    unsigned char i;
    UART2_addr = receive;
    UART2_size = size;
    for (i = 0; i < size; i++) {
        receive[i] = 0;
    }
}

//++++++++++++++++++++++++++++++++++++++++++

void UART_interrupt() interrupt 0 {
    if (SC16IS752_read(IIR, 0) & 0x04) {
        if (UART1_size-- > 0) {
            *UART1_addr++ = SC16IS752_read(RHR, 0);
        } else {
            SC16IS752_read(RHR, 0);
        }
    }
    if (SC16IS752_read(IIR, 1) & 0x04) {
        if (UART2_size-- > 0) {
            *UART2_addr++ = SC16IS752_read(RHR, 1);
        } else {
            SC16IS752_read(RHR, 1);
        }
    }
}