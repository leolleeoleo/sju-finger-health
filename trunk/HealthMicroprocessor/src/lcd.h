/* 
 * File:   lcd.h
 *
 * Created on 2012/03/08
 */

#ifndef __LCD_H
#define	__LCD_H


//REG [00h] Whole Chip LCD Controller Register (WLCR)***************************								
extern void LCD_TextMode(unsigned char);
extern void LCD_Inverse(unsigned char);

//REG [01h] Misc. Register (MISC)***********************************************
extern void LCD_NoFlicker(unsigned char);

//REG [0Fh] Interrupt Setup and Status Register (INTR)**************************
void LCD_TouchPanelInterrupt(unsigned char);

//REG [10h] Whole Chip Cursor Control Register (WCCR)***************************
extern void LCD_ReversedData(unsigned char);
extern void LCD_BoldFont(unsigned char);
extern void LCD_CursorDisplay(unsigned char);
extern void LCD_CursorBlinking(unsigned char);

//REG [12h] Memory Access Mode Register (MAMR)**********************************
#define LCD_DisplaySelection_GRAY 0x00
#define LCD_DisplaySelection_DDRAM1 0x01
#define LCD_DisplaySelection_DDRAM2 0x02
#define LCD_DisplaySelection_TWOLAYER 0x03
extern void LCD_DisplaySelection(unsigned char);

#define LCD_ModeSelection_OR 0x00
#define LCD_ModeSelection_XOR 0x01
#define LCD_ModeSelection_NOR 0x02
#define LCD_ModeSelection_AND 0x03
extern void LCD_ModeSelection(unsigned char);

#define LCD_AccessSelection_CGRAM 0x00
#define LCD_AccessSelection_DDRAM1 0x01
#define LCD_AccessSelection_DDRAM2 0x02
#define LCD_AccessSelection_BOTHDDRAM 0x03
extern void LCD_AccessSelection(unsigned char);

//REG [60h] Cursor Position X Register (CURX)***********************************
extern void LCD_CursorPositionX(unsigned char);

//REG [70h] Cursor Position Y Register (CURY)***********************************
extern void LCD_CursorPositionY(unsigned char);

//REG [C0h] Touch Panel Control Register 1 (TPCR1)******************************
extern void LCD_TouchPanelEnable(unsigned char);

//REG [C1h] Touch Panel X High Byte Data Register (TPXR)************************
extern unsigned int LCD_TouchPanelX();

//REG [C2h] Touch Panel Y High Byte Data Register (TPYR)************************
extern unsigned int LCD_TouchPanelY();

//REG [C4h] Touch Panel Control Register 2 (TPCR2)******************************
extern void LCD_TouchPanelManual(unsigned char);

//REG [E0h] Pattern Data Register (PNTR)****************************************
extern void LCD_PatternData(unsigned char);

//REG [F0h] Font Control Register (FNCR)****************************************
extern void LCD_MemoryClear(unsigned char);

//REG [F1h] Font Size Control Register (FVHT)***********************************
extern void LCD_FontSize(unsigned char, unsigned char);

//******************************************************************************

extern void LCD_CursorPosition(unsigned char, unsigned char);
extern void LCD_Display(unsigned char);

extern void LCD_PositionString(unsigned char, unsigned char, unsigned char *);
extern void LCD_PositionGraphic(unsigned char, unsigned char, unsigned char *);

extern bit LCD_interrupt();
extern void LCD_interruptWait();
extern void LCD_TPclean();
extern unsigned char LCD_TPcheck();
extern unsigned int LCD_getX();
extern unsigned int LCD_getY();

//====================
extern void LCD_cleanAll();
extern void LCD_setDefault();
//====================
extern void LCD_initial();

#endif	/* LCD_H */