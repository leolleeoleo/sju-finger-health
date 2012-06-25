/*--------------------------------------------------------------------------
uartio.h

Prototypes for standard I/O functions.
Copyright (c) 1988-2002 Keil Elektronik GmbH and Keil Software, Inc.
All rights reserved.
--------------------------------------------------------------------------*/

#ifndef __UARTIO_H__
#define __UARTIO_H__

#ifndef EOF
#define EOF -1
#endif

#ifndef NULL
#define NULL ((void *) 0)
#endif

#ifndef _SIZE_T
#define _SIZE_T
typedef unsigned int size_t;
#endif

#pragma SAVE
#pragma REGPARMS


extern void UART_initial(unsigned int);

extern void UART1_initial(unsigned long);
extern void UART1_put(unsigned char);
extern void UART1_print(unsigned char *, unsigned char);
extern void UART1_receive(unsigned char *, unsigned char);

extern void UART2_initial(unsigned long);
extern void UART2_put(unsigned char);
extern void UART2_print(unsigned char *, unsigned char);
extern void UART2_receive(unsigned char *, unsigned char);

#pragma RESTORE

#endif

