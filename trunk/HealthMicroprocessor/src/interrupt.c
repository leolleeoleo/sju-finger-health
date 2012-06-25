#include "base.h"
//#include "interrupt.h"

data unsigned char INT_STATE;

void INT_initial() {
    EA = 1;
    //	ES = 1;
    IT0 = 0;
    IT1 = 1;
    PX0 = 1;
    PX1 = 1;
    EX1 = 1;
    INT_STATE = 0;
}

unsigned char INT_X1_check() {
    return INT_STATE & 0x02;
}

void INT_X1_clean() {
    INT_STATE = INT_STATE & 0xFD;
    EX1 = 1;
}

void INT_X1_int() interrupt 2 {
    INT_STATE = INT_STATE | 0x02;
    EX1 = 0;
}

void INT_T0_initial() {
    TMOD = 0x01;
    TR0 = 0;
    ET0 = 1;
    TH0 = (65536 - 50000) / 256;
    TL0 = (65536 - 50000) % 256;
    TR0 = 1;
}

unsigned char INT_T0_check() {
    return INT_STATE & 0x04;
}

void INT_T0_clean() {
    INT_STATE = INT_STATE & 0xFB;
    TR0 = 0;
}

void INT_T0_int() interrupt 1 {
    INT_STATE = INT_STATE | 0x04;
}

