/* 
 * File:   ra8806.h
 *
 * Created on 2012/03/08
 */

#ifndef __RA8806_H
#define	__RA8806_H
	
extern void RA8806_reset();
extern void RA8806_writeCmd(unsigned char);
extern void RA8806_writeData(unsigned char);
extern unsigned char RA8806_read(unsigned char);
extern void RA8806_write(unsigned char, unsigned char);
extern void RA8806_bit(unsigned char, unsigned char, unsigned char);

#endif	/* RA8806_H */
