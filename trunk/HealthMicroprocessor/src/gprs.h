
#ifndef __GPRS_H__
#define __GPRS_H__

extern unsigned char *GPRS_getData();

extern void GPRS_initial();

extern unsigned char *GPRS_getSignalQuality();
extern unsigned char *GPRS_getOperatorSlelction();
extern unsigned char *GPRS_getIPAddress();

extern void GPRS_initialTCPIP();

extern void GPRS_connect(unsigned char *, unsigned char *);
extern void GPRS_send(unsigned char);
extern unsigned char GPRS_checkSend();
extern void GPRS_write(unsigned char *, unsigned char);
extern unsigned char GPRS_checkWrite();
extern void GPRS_close();



#endif