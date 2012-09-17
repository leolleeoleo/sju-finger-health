#include "lcd.h"
#include "keyboard.h"

#define CONFIGURE_LENGTH 0x10
xdata unsigned char CONFIGURE_DATA[CONFIGURE_LENGTH];
idata unsigned char *CONFIGURE_SET;
idata unsigned char CONFIGURE_CURSOR;

void CONFIGURE_set(unsigned char *set) {
    unsigned char i;
    CONFIGURE_SET = set;
    for (i = 0; i < CONFIGURE_LENGTH; i++) {
        CONFIGURE_DATA[i] = set[i];
    }
    CONFIGURE_CURSOR = 0;
}

void CONFIGURE_write() {
    unsigned char i;
    for (i = 0; i < CONFIGURE_LENGTH; i++) {
        CONFIGURE_SET[i] = CONFIGURE_DATA[i];
    }
}

void CONFIGURE_show(unsigned char *str) {
    if (CONFIGURE_CURSOR >= CONFIGURE_LENGTH - 1) {
        CONFIGURE_CURSOR = 0;
    }
    KEYBOARD_show();
    LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
    LCD_Display(0x00);
    LCD_setDefault();
    LCD_PositionString(0x10, 0x00, "設定");
    LCD_PositionString(0x14, 0x00, str);
    LCD_PositionString(0x0E, 0x20, CONFIGURE_DATA);
    LCD_PositionString(0x06, 0xE0, "確定");
    LCD_PositionString(0x1E, 0xE0, "取消");
    LCD_CursorPosition(0x0E + CONFIGURE_CURSOR, 0x20);
    LCD_CursorDisplay(1);
    LCD_CursorBlinking(1);
}

unsigned char CONFIGURE_action(unsigned int x, unsigned int y) {
    unsigned char key;
    key = KEYBOARD_key(x, y);
    if (key != '\0') {
        LCD_CursorDisplay(0);
        if (key == 0x10) {
            CONFIGURE_CURSOR++;
        } else if (key == 0x11) {
            CONFIGURE_CURSOR--;
        } else if (key == 0x08) {
            CONFIGURE_DATA[CONFIGURE_CURSOR--] = '\0';
        } else {
            CONFIGURE_DATA[CONFIGURE_CURSOR++] = key;
        }
        return 0x0A;
    }
    LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
    LCD_FontSize(0, 0);
    LCD_ReversedData(1);
    if (y > 208 && y < 240) {
        if (x > 32 && x < 96) {
            LCD_PositionString(0x06, 0xE0, "確定");
            CONFIGURE_write();
            return 0x0D;
        } else if (x > 224 && x < 288) {
            LCD_PositionString(0x1E, 0xE0, "取消");
            return 0x1B;
        }
    }
    return 0x00;
}