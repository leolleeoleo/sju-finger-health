

#ifndef __SIM300_H__
#define __SIM300_H__

extern unsigned char *GPRS_getReceive();
extern unsigned char GPRS_initial(unsigned char);
extern unsigned char GPRS_connect(unsigned char);
extern unsigned char GPRS_send(unsigned char, unsigned char *, unsigned char);
extern void GPRS_close();



#endif