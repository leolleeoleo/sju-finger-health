/* 
 * File:   spi.h
 * Author: Leo Hsueh
 *
 * Date 2012/05/06
 */

#ifndef __SPI_H__
#define __SPI_H__

extern void SPI_initial();
extern void SPI_write(unsigned char,unsigned char);
extern unsigned char SPI_read(unsigned char);

#endif