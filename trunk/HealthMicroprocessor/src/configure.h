/* 
 * File:   configure.h
 * Author: Leo
 *
 * Created on 2012/08/06
 */

#ifndef __CONFIGURE_H
#define	__CONFIGURE_H

extern void CONFIGURE_set(unsigned char *);
extern void CONFIGURE_write();
extern void CONFIGURE_show(unsigned char *);
extern unsigned char CONFIGURE_action(unsigned int, unsigned int);

#endif	/* CONFIGURE_H */