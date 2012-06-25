#include "base.h"
#include "spi.h"
#include "sc16is752reg.h"

#define	Fclk 11059200

void SC16IS752_write(unsigned char reg, unsigned char ch, unsigned char c) {
    EX0 = 0;
    if (ch) {
        reg |= 0x02;
    }
    SPI_write(reg, c);
    EX0 = 1;
}

unsigned char SC16IS752_read(unsigned char reg, unsigned char ch) {
    EX0 = 0;
    reg |= 0x80;
    if (ch) {
        reg |= 0x02;
    }
    EX0 = 1;
    return SPI_read(reg);
}

void SC16IS752_initial(unsigned long baud, unsigned char channel) {
    unsigned int div;
    div = (Fclk >> 4) / baud;
    SPI_initial();
    SC16IS752_write(LCR, channel, 0x80);
    SC16IS752_write(DLL, channel, (div & 0xFF));
    SC16IS752_write(DLH, channel, (div >> 8));
    SC16IS752_write(LCR, channel, 0x03);
    SC16IS752_write(IER, channel, 0x01);
    SC16IS752_write(FCR, channel, 0x07);
}
