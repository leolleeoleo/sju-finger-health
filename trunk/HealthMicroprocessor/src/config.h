/* 
 * File:   config.h
 * Author: Leo
 *
 * Created on 2012/02/29
 */

#ifndef __CONFIG_H
#define	__CONFIG_H

#define CONFIG_DATA_SIZE 0x10

extern void CONFIG_read();
extern void CONFIG_write();

extern void CONFIG_setAccount(unsigned char *);
extern unsigned char *CONFIG_getAccount();

extern void CONFIG_setPassword(unsigned char *);
extern unsigned char *CONFIG_getPassword();

extern void CONFIG_setServer(unsigned char *);
extern unsigned char *CONFIG_getServer();


#endif	/* CONFIG_H */