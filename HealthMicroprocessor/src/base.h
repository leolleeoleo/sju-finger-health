/* 
 * File:   base.h
 * Author: MA780G
 *
 * Created on 2011年12月22日, 下午 8:20
 */

#ifndef __BASE_H
#define	__BASE_H

#ifndef __REG52_H
#define	__REG52_H
#include <REG52.H>
#endif

#define XTAL 24000000

extern void delay_us(unsigned int);
extern void delay_ms(unsigned int);
extern unsigned char hex_ascii(unsigned char);
extern unsigned int hex_ascii_string(unsigned char);
extern unsigned char ascii_hex(unsigned char);
extern unsigned char ascii_hex_string(unsigned int);
extern unsigned char * dec_ascii(unsigned char dec);

#endif	/* BASE_H */

