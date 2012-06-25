#include "base.h"
#include <intrins.h>

void delay_us(unsigned int s) {
    while (s-- > 0) {
        _nop_();
        _nop_();
        _nop_();
        _nop_();
    }
}

void delay_ms(unsigned int s) {
    unsigned char time;
    while (s-- > 0) {
        time = 198;
        while (time-- > 0);
    }
}

unsigned char hex_ascii(unsigned char hex) {
    return (hex > 9 ? hex + 0x37 : hex + 0x30);
}

unsigned int hex_ascii_string(unsigned char hex) {
    return (hex_ascii((hex >> 4) & 0x0F) << 8) | hex_ascii(hex & 0x0F);
}

unsigned char ascii_hex(unsigned char ascii) {
    return (ascii > 0x40 ? ascii - 0x37 : ascii - 0x30);
}

unsigned char ascii_hex_string(unsigned int ascii) {
    return (ascii_hex((ascii >> 8) & 0xFF) << 4) | ascii_hex(ascii & 0xFF);
}

xdata unsigned char dec_string[3];

unsigned char * dec_ascii(unsigned char dec) {
    dec_string[0] = 0x30 + (dec / 100);
    dec_string[1] = 0x30 + ((dec / 10) % 10);
    dec_string[2] = 0x30 + (dec % 10);
    return dec_string;
}

