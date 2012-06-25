#include <string.h>
#include "lcd1602.h"

void LCD_initial(void) {
    unsigned char i;
    LCD_Data = 0;
    for (i = 0; i < 3; i++) {
        LCD_command(0x38, 0);
        delay_ms(5);
    }
    LCD_command(0x38, 1);
    LCD_command(LCD_OFF, 1);
    LCD_command(LCD_CLEAR, 1);
    LCD_command(LCD_CURSOR, 1);
    LCD_command(LCD_ON, 1);
}

unsigned char LCD_flush() {
    unsigned char result;
    LCD_EN = 0;
    delay_ms(1);
    LCD_EN = 1;
    delay_ms(1);
    result = LCD_Data;
    LCD_EN = 0;
    return result;
}

bit LCD_isBusy() {
    LCD_RS = 0;
    LCD_RW = 1;
    return (bit) (LCD_flush() & LCD_BUSY);
}

void LCD_command(unsigned char lcd_data, bit BuysC) {
    if (BuysC) while (LCD_isBusy());
    LCD_Data = lcd_data;
    LCD_RS = 0;
    LCD_RW = 0;
    LCD_flush();
}

void LCD_write(unsigned char lcd_data) {
    while (LCD_isBusy());
    LCD_Data = lcd_data;
    LCD_RS = 1;
    LCD_RW = 0;
    LCD_flush();
}

unsigned char LCD_read(void) {
    LCD_RS = 1;
    LCD_RW = 1;
    return (LCD_flush());
}

void LCD_setLocation(unsigned char x, unsigned char y) {
    LCD_command(0x80 | (x & 0x0F) | (y == 0 ? 0x00 : 0x40), 1);
}

void LCD_print(char *str) {
    unsigned char length = strlen(str);
    while (*str >= 0x20 && length-- > 0) {
        LCD_write(*str++);
    }
}

void LCD_show(char *str_0, char *str_1) {
    LCD_command(LCD_CLEAR, 1);
    LCD_setLocation(0, 0);
    LCD_print(str_0);
    LCD_setLocation(0, 1);
    LCD_print(str_1);
}

unsigned char hex_ascii(unsigned char hex) {
    return (hex > 9 ? hex + 0x37 : hex + 0x30);
}

void hex_ascii_string(unsigned char *hex, unsigned char *ascii, unsigned char length) {
    //unsigned char *ptr = ascii;
    while (length-- > 0) {
        *ascii++ = hex_ascii((*hex >> 4) & 0x0F);
        *ascii++ = hex_ascii(*hex++ & 0x0F);
    }

    //return &ascii;
}