/* 
 * File:   sc16is752.h
 * Author: Leo Hsueh
 *
 * Date 2012/05/06
 */

#ifndef __SC16IS752_H__
#define __SC16IS752_H__

extern void SC16IS752_write(unsigned char, unsigned char, unsigned char);
extern unsigned char SC16IS752_read(unsigned char, unsigned char);
extern void SC16IS752_initial(unsigned long, unsigned char);

#endif
