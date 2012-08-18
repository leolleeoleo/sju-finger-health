#include "base.h"
#include "lcd.h"
#include "ra8806reg.h"
#include "ra8806.h"


//REG [00h] Whole Chip LCD Controller Register (WLCR)***************************

/**
 * Text Mode Selection
 * 0 : Graphical Mode. The written data will be treated as a bit-map pattern.
 * 1 : Text Mode. The written data will be treated as an ASCII, BIG5 code.
 *
 */
void LCD_TextMode(unsigned char set) {
    RA8806_bit(RA8806_WLCR, 3, set);
    LCD_NoFlicker(set ^ 0x01);
}

/**
 * Inverse Mode Selection
 * 0 : Normal Display.
 * 1 : Inverse Full Screen. It will cause the display inversed.
 * 
 */
void LCD_Inverse(unsigned char set) {
    RA8806_bit(RA8806_WLCR, 0, set);
}

//REG [01h] Misc. Register (MISC)***********************************************

/**
 * Eliminating Flicker
 * 1 : Eliminating flicker mode, scan will auto-pending when busy.
 * 0 : Normal mode.
 * 
 */
void LCD_NoFlicker(unsigned char set) {
    RA8806_bit(RA8806_MISC, 7, set);
}

//REG [0Fh] Interrupt Setup and Status Register (INTR)**************************

/**
 * Touch Panel Interrupt Mask
 * 1 : Generate interrupt output if touch panel was detected.
 * 0 : Don’t generate interrupt output if touch panel was detected.
 *
 */
void LCD_TouchPanelInterrupt(unsigned char set) {
    RA8806_bit(RA8806_INTR, 4, set);
}

//REG [10h] Whole Chip Cursor Control Register (WCCR)***************************

/**
 * Reversed Data Write mode
 * 0 : Store Current Data to DDRAM Directly.
 * 1 : Store Current Data to DDRAM Inversely.(i.e. 01101101 to 10010010)
 * 
 */
void LCD_ReversedData(unsigned char set) {
    RA8806_bit(RA8806_WCCR, 5, set);
}

/**
 * Bold Font (Character Mode Only)
 * 1 : Bold Font
 * 0 : Normal Font
 * 
 */
void LCD_BoldFont(unsigned char set) {
    RA8806_bit(RA8806_WCCR, 4, set);
}

/**
 * Cursor Display
 * 1 : Set Cursor Display On.
 * 0 : Set Cursor Display Off.
 * 
 */
void LCD_CursorDisplay(unsigned char set) {
    RA8806_bit(RA8806_WCCR, 2, set);
}

/**
 * Cursor Blinking
 * 1 : Blink Cursor. The blink time is determined by register BTMR.
 * 0 : Normal.
 * 
 */
void LCD_CursorBlinking(unsigned char set) {
    RA8806_bit(RA8806_WCCR, 1, set);
}

//REG [12h] Memory Access Mode Register (MAMR)**********************************

/**
 * Display Layer and Display Mode Selection
 * 0 0 0 : Gray Mode. In this mode, each pixel consists with 2 continuous bits
 * in memory data. With the FRC methodology, 4-level-gray mode is implemented.
 * The bit mapping is list as below.
 * bit1 bit0 Gray
 * ---------------------------------------------------
 * 0 0 Level1 (Lightest)
 * 0 1 Level2
 * 1 0 Level3
 * 1 1 Level4 (Darkest)
 * Note: Gray mode doesn’t support text-mode input.
 * 0 0 1 : Show DDRAM1 data on screen.
 * 0 1 0 : Show DRRAM2 data on screen.
 * 0 1 1 : Show Two Layer Mode. The display rule depends on Bit-3 and Bit-2 as
 *         following.
 * 
 */
void LCD_DisplaySelection(unsigned char select) {
    RA8806_bit(RA8806_MAMR, 5, (select & 0x02));
    RA8806_bit(RA8806_MAMR, 4, (select & 0x01));
}

/**
 * Two Layer Mode Selection
 * Combine the data of DDRAM1 and DDRAM2 on the screen when Bit[6:4] is set as
 * “011”.
 * 0 0 : DDRAM1 “OR” DDRAM2.
 * 0 1 : DDRAM1 “XOR” DDRAM2.
 * 1 0 : DDRAM1 “NOR” DDRAM2.
 * 1 1 : DDRAM1 “AND” DDRAM2.
 * 
 */
void LCD_ModeSelection(unsigned char select) {
    RA8806_bit(RA8806_MAMR, 3, (select & 0x02));
    RA8806_bit(RA8806_MAMR, 2, (select & 0x01));
}

/**
 * MPU Read/Write Layer Selection
 * 0 0 : Access CGRAM.(512Byte)
 * 0 1 : Access DDRAM1.
 * 1 0 : Access DDRAM2.
 * 1 1 : Access both DDRAM1 and DDRAM2 concurrently
 * 
 */
void LCD_AccessSelection(unsigned char select) {
    RA8806_bit(RA8806_MAMR, 1, (select & 0x02));
    RA8806_bit(RA8806_MAMR, 0, (select & 0x01));
}

//REG [60h] Cursor Position X Register (CURX)***********************************

/**
 * Cursor Position of Segment / RAM0 Address[4:0]
 * Define the cursor address of segment, a value from 0h ~ 27h(0 ~ 40 in decimal)
 * When CGRAM write mode is selected (REG[12h] Bit[1:0] = 00b), the Bit[4:0] is
 * the address for writing bit-map data. When create a full-size font, normally
 * set to 0. When create an odd half-size font, normally set to 0, and set 10h
 * for even font.
 * 
 */
void LCD_CursorPositionX(unsigned char set) {
    RA8806_write(RA8806_CURX, set);
}

//REG [70h] Cursor Position Y Register (CURY)***********************************

/**
 * Cursor Position of Common / RAM0 Address[8:5]
 * Define the cursor address of common, a value from 0h ~ EFh(0 ~ 239 in decimal).
 * When CGRAM write mode is selected (REG[12h] Bit[1:0] = 00b), the Bit[3:0] is
 * indicate which font will be created. And Bit[7:4] are not available.
 * 
 */
void LCD_CursorPositionY(unsigned char set) {
    RA8806_write(RA8806_CURY, set);
}

//REG [C0h] Touch Panel Control Register 1 (TPCR1)******************************

/**
 * Touch Panel Enable Bit
 * 1 : Enable.
 * 0 : Disable.
 * 
 */
void LCD_TouchPanelEnable(unsigned char set) {
    RA8806_bit(RA8806_TPCR1, 7, set);
}

//REG [C1h] Touch Panel X High Byte Data Register (TPXR)************************

unsigned int LCD_TouchPanelX() {
    return (RA8806_read(RA8806_TPXR) << 2) | (RA8806_read(RA8806_TPZR) & 0x03);
}

//REG [C2h] Touch Panel Y High Byte Data Register (TPYR)************************

unsigned int LCD_TouchPanelY() {
    return (RA8806_read(RA8806_TPYR) << 2) | ((RA8806_read(RA8806_TPZR) >> 2) & 0x03);
}

//REG [C3h] Touch Panel Segment/Common Low Byte Data Register (TPZR)************

//REG [C4h] Touch Panel Control Register 2 (TPCR2)******************************

/**
 * TP Manual Mode Enable
 * 1 : Using the manual mode.
 * 0 : Auto mode.
 */
void LCD_TouchPanelManual(unsigned char set) {
    RA8806_bit(RA8806_TPCR2, 7, set);
}


//REG [E0h] Pattern Data Register (PNTR)****************************************

/**
 * Data Written to DDRAM(Display Data RAM)
 * The pattern that will be filled to active window in memory clear function.
 * When REG[F0h] Bit-3 is ‘1’, the data in the REG[E0h] will be filled to the
 * whole active window.
 * 
 */
void LCD_PatternData(unsigned char set) {
    RA8806_write(RA8806_PNTR, set);
}

//REG [F0h] Font Control Register (FNCR)****************************************

/**
 * Memory Clear Function
 * Write Function
 * 0 : No Action.
 * 1 : Memory clear function active, fill the data of FNTR to Active window.
 * When this bit is set to “1”, RA8806 will automatically read PNTR data, and
 * fill it to Active window (Range: [AWLR, AWTR] ~ [AWRR, AWBR]), after clear
 * completed, this bit will be cleaned to “0”.
 * 
 */
void LCD_MemoryClear(unsigned char set) {
    RA8806_bit(RA8806_FNCR, 3, set);
}

//REG [F1h] Font Size Control Register (FVHT)***********************************

void LCD_FontSize(unsigned char width, unsigned char height) {
    RA8806_write(RA8806_FVHT, ((width & 0x03) << 6) | ((height & 0x03) << 4));

}

//******************************************************************************

void LCD_CursorPosition(unsigned char x, unsigned char y) {
    LCD_CursorPositionX(x);
    LCD_CursorPositionY(y);
}

void LCD_Display(unsigned char set) {
    LCD_TextMode(0);
    LCD_NoFlicker(0);
    LCD_PatternData(set);
    LCD_MemoryClear(1);
}

void LCD_PositionString(unsigned char x, unsigned char y, unsigned char *text) {
    LCD_TextMode(1);
    LCD_CursorPosition(x, y);
    RA8806_writeCmd(RA8806_MWCR);
    while (*text != '\0') {
        RA8806_writeData(*text++);
    }
}

void LCD_PositionGraphic(unsigned char x, unsigned char y, unsigned char *graphic) {
    unsigned int i = 0, ii = graphic[1], j = 0, jj = graphic[0], c = 2;
    LCD_TextMode(0);
    for (i = 0; i < ii; i++) {
        LCD_CursorPosition(x, y + i);
        RA8806_writeCmd(0xB0);
        for (j = 0; j < jj; j++) {
            RA8806_writeData(graphic[c++]);
        }
    }
}

idata unsigned int LCD_x, LCD_y;

bit LCD_interrupt() {
    if (RA8806_interrupt()) {
        unsigned char state = RA8806_read(0x0F);
        if (state & 0x01) {
            LCD_x = LCD_TouchPanelX();
            LCD_y = LCD_TouchPanelY();
        }
        return 1;
    }
    return 0;
}

void LCD_interruptWait() {
    while (RA8806_read(0x0F) & 0x01) {
        RA8806_bit(0x0F, 0, 0);
        delay_ms(5);
    }
}

void LCD_TPclean() {
    LCD_x = 0;
    LCD_y = 0;
}

unsigned char LCD_TPcheck() {
    return (LCD_x != 0 || LCD_y != 0);
}

unsigned int LCD_getX() {
    return (0x03C0 - LCD_x) * 0.37;
}

unsigned int LCD_getY() {
    return (0x03A0 - LCD_y) * 0.29;
}

//===========================================

void LCD_cleanAll() {
    LCD_AccessSelection(LCD_AccessSelection_DDRAM1); //存取DDRAM1
    LCD_Display(0x00);
    LCD_AccessSelection(LCD_DisplaySelection_DDRAM2); //存取DDRAM2
    LCD_Display(0x00);
}

void LCD_setDefault() {
    LCD_CursorBlinking(1);
    LCD_CursorDisplay(0);
    LCD_BoldFont(0);
    LCD_Inverse(0);
    LCD_ReversedData(0);
    LCD_FontSize(0, 0);

}

///////////////////////////////////////////

void LCD_initial() {
    RA8806_reset();
    RA8806_write(RA8806_WLCR, 0x04);
    RA8806_write(RA8806_MISC, 0x08);
    RA8806_write(RA8806_ADSR, 0x00);
    RA8806_write(RA8806_INTR, 0x00);
    RA8806_write(RA8806_WCCR, 0x00);
    RA8806_write(RA8806_CHWI, 0x00);
    RA8806_write(RA8806_MAMR, 0x11);
    RA8806_write(RA8806_AWRR, 0x27); //39
    RA8806_write(RA8806_DWWR, 0x27); //39
    RA8806_write(RA8806_AWBR, 0xEF); //239
    RA8806_write(RA8806_DWHR, 0xEF); //239
    RA8806_write(RA8806_AWLR, 0x00);
    RA8806_write(RA8806_AWTR, 0x00);
    RA8806_write(RA8806_CURX, 0x00);
    RA8806_write(RA8806_BGSG, 0x00);
    RA8806_write(RA8806_EDSG, 0x00);
    RA8806_write(RA8806_CURY, 0x00);
    RA8806_write(RA8806_BGCM, 0x00);
    RA8806_write(RA8806_EDCM, 0x00);
    RA8806_write(RA8806_BTMR, 0x10);
    RA8806_write(RA8806_ITCR, 0x63); //99
    RA8806_write(RA8806_PNTR, 0x00);
    RA8806_write(RA8806_FNCR, 0x00);
    RA8806_write(RA8806_FVHT, 0x00);
    LCD_cleanAll();
}

