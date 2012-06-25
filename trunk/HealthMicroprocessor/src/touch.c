#include "base.h"
#include "lcd.h"


#define TOUCH_AX 0x04
#define TOUCH_AY 0x20
#define TOUCH_BX 0x24
#define TOUCH_BY 0xC0
#define TOUCH_CX 0x04
#define TOUCH_CY 0xC0
#define TOUCH_DX 0x24
#define TOUCH_DY 0xC0

xdata double touch_AX, touch_BX, touch_CX, touch_DX;
xdata double touch_AY, touch_BY, touch_CY, touch_DY;

double TOUCH_getX() {
    return ((LCD_getX() - touch_AX)*(TOUCH_BX - TOUCH_AX) / (touch_BX - touch_AX) + TOUCH_AX);
}

double TOUCH_getY() {
    return ((LCD_getY() - touch_AY)*(TOUCH_BY - TOUCH_AY) / (touch_BY - touch_AY) + TOUCH_AY);
}

void TOUCH_showA() {
    LCD_PositionString(TOUCH_AX, TOUCH_AY, "X");
    delay_ms(1000);
}

void TOUCH_showB() {
    LCD_PositionString(TOUCH_BX, TOUCH_BY, "X");
    delay_ms(1000);
}

void TOUCH_showC() {
    LCD_PositionString(TOUCH_CX, TOUCH_CY, "X");
    delay_ms(1000);
}

void TOUCH_showD() {
    LCD_PositionString(TOUCH_DX, TOUCH_DY, "X");
    delay_ms(1000);
}

void TOUCH_setA(unsigned int x, unsigned int y) {
    touch_AX = (double) x;
    touch_AY = (double) y;
}

void TOUCH_setB(unsigned int x, unsigned int y) {
    touch_BX = (double) x;
    touch_BY = (double) y;
}

void TOUCH_setC(unsigned int x, unsigned int y) {
    touch_CX = (double) x;
    touch_CY = (double) y;
}

void TOUCH_setD(unsigned int x, unsigned int y) {
    touch_DX = (double) x;
    touch_DY = (double) y;
}
