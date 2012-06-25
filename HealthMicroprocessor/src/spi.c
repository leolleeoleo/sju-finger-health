#include "base.h"							 

//P89V51RD2 SPI PIN
sfr SPCR = 0xD5;
sfr SPSR = 0xAA;
sfr SPDR = 0x86;
sbit SPI_SS = P1^4;

/**
 * SPI interface initial
 * EA and ES will be set.
 *
 */
void SPI_initial() {
    SPCR = 0xD3;
}

/**
 * SPI fifo shift
 *
 * @param send send
 * @return receive
 *
 */
unsigned char SPI_shift(unsigned char send) {
    SPSR &= ~0x80;
    SPDR = send;
    while (!(SPSR & 0x80));
    return SPDR;
}

/**
 * SPI wrire
 *
 * @param reg reg
 * @param send send
 *
 */
void SPI_write(unsigned char reg, unsigned char send) {
    SPI_SS = 0;
    SPI_shift(reg);
    SPI_shift(send);
    SPI_SS = 1;
}

/**
 * SPI read
 *
 * @param reg reg
 * @return receive
 *
 */
unsigned char SPI_read(unsigned char reg) {
    SPI_SS = 0;
    SPI_shift(reg);
    reg = SPI_shift(0);
    SPI_SS = 1;
    return reg;
}

