/* 
 * File:   interrupt.h
 * Author: MA780G
 *
 * Created on 2011å¹?2??0?? ä¸?? 2:38
 */

#ifndef __INTERRUPT_H
#define	__INTERRUPT_H

extern void INT_initial();

extern unsigned char INT_X1_check();
extern void INT_X1_clean();

extern void INT_T0_initial();
extern unsigned char INT_T0_check();
extern void INT_T0_clean();

#endif	/* INTERRUPT_H */

