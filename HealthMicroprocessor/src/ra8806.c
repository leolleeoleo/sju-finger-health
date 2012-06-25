#include "base.h"
#include "ra8806.h"

//pin config
sbit RA_CS = P2^3;
sbit RA_RS = P2^0;
sbit RA_WR = P2^1;
sbit RA_RD = P2^2;
sbit RA_BUSY = P2^4;
sbit RA_RST = P2^5;
sbit RA_INT = P3^3;
#define RA_BUS P0

void RA8806_reset() {
    RA_CS = 0;
    RA_RD = 1;
    RA_RST = 0;
    delay_ms(5);
    RA_RST = 1;
    delay_ms(5);
    RA_CS = 1;
    delay_ms(100);
}

void RA8806_writeCmd(unsigned char d) {
    RA_CS = 0;
    RA_RS = 1;
    RA_RD = 1;
    RA_BUS = d;
    RA_WR = 0;
    RA_WR = 1;
    RA_CS = 1;
    delay_us(1);
}

void RA8806_writeData(unsigned char d) {
    while (RA_BUSY == 0);
    RA_CS = 0;
    RA_RS = 0;
    RA_RD = 1;
    RA_BUS = d;
    RA_WR = 0;
    RA_WR = 1;
    RA_CS = 1;
    delay_us(1);
}

unsigned char RA8806_readData() {
    unsigned char result;
    RA_BUS = 0XFF;
    RA_CS = 0;
    RA_RS = 0;
    RA_WR = 1;
    RA_RD = 1;
    RA_RD = 0;
    result = RA_BUS;
    RA_RD = 1;
    RA_RS = 1;
    RA_CS = 1;
    delay_us(1);
    return result;
}

unsigned char RA8806_read(unsigned char addr) {
    RA8806_writeCmd(addr);
    return RA8806_readData();
}

void RA8806_write(unsigned char RA_addr, unsigned char RA_data) {
    RA8806_writeCmd(RA_addr);
    RA8806_writeData(RA_data);
}

void RA8806_bit(unsigned char RA_addr, unsigned char RA_bit, unsigned char set) {
    RA_bit = 0x01 << RA_bit;
    RA8806_write(RA_addr, (set ? (RA8806_read(RA_addr) | (RA_bit)) : (RA8806_read(RA_addr) & (~RA_bit))));
}


