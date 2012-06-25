#include "base.h"

#ifndef __LCD1602_H
#define	__LCD1602_H

//pin
sbit LCD_EN = P3^4;
sbit LCD_RS = P3^5;
sbit LCD_RW = P3^6;
#define LCD_Data P0

//指令
#define LCD_ON 0x0C
#define LCD_OFF 0x08
#define LCD_CLEAR 0x01
#define LCD_CURSOR 0x06
#define LCD_SHIFTL 0x18
#define LCD_BUSY 0x80

extern void LCD_initial(void);
extern void LCD_command(unsigned char, bit);
extern void LCD_write(unsigned char);
extern unsigned char LCD_read(void);
extern void LCD_setLocation(unsigned char, unsigned char);
extern void LCD_print(unsigned char *);
extern void LCD_show(unsigned char *, unsigned char *);


extern unsigned char hex_ascii(unsigned char);
extern void hex_ascii_string(unsigned char *, unsigned char *, unsigned char);

#endif
