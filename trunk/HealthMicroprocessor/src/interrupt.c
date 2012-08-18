#include "base.h"
//#include "interrupt.h"

//extern void INT_T1_initial();

data unsigned char INT_STATE;

void INT_initial() {
    EA = 1;
    //	ES = 1;
    IT0 = 0;
    IT1 = 1;
    PX0 = 1;
    PX1 = 1;
    EX1 = 1;
    //	INT_T1_initial();
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
    TMOD = (TMOD & 0xF0) | 0x01;
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
    TR0 = 0;
}
/*
sbit INT_PWM = P2^7;
unsigned char INTERRUPT_PWM_set = 192;
unsigned char INTERRUPT_PWM_count = 0;

void INT_setPWM(unsigned char pwm) {
    INTERRUPT_PWM_set = pwm;
}

void INT_T1_initial(unsigned char set) {
    TMOD = (TMOD | 0x0F) & 0x01;
    TR1 = 0;
    ET1 = 1;
    TH1 = (65536 - (set * 200)) / 256;
    TL1 = (65536 - (set * 200)) % 256;
    TR1 = 1;
}

void INT_T1_int() interrupt 3 {
 //   INTERRUPT_PWM_count++;
//	if (INTERRUPT_PWM_count == INTERRUPT_PWM_set || INTERRUPT_PWM_count == 0) {
            INT_PWM = !INT_PWM;
//	}
        INT_T1_initial(50);
}
//*/