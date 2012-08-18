#include "lcd.h"

void KEYBOARD_show() {
    LCD_DisplaySelection(LCD_DisplaySelection_TWOLAYER);
    LCD_ModeSelection(LCD_ModeSelection_OR);
    LCD_AccessSelection(LCD_DisplaySelection_DDRAM2);
    LCD_Display(0x00);
    LCD_setDefault();
    LCD_FontSize(1, 1);
    LCD_PositionString(0x00, 0x50, "��������������������");
    LCD_PositionString(0x00, 0x70, "�ߢ�Ӣ����עݢ�");
    LCD_PositionString(0x02, 0x90, "�Ϣ�ҢԢբ֢آ٢�");
    LCD_PositionString(0x04, 0xB0, "���Ѣ�ТܢۡDBS");
    LCD_PositionString(0x10, 0xD0, "����");
    LCD_AccessSelection(LCD_DisplaySelection_DDRAM1);
}

unsigned char KEYBOARD_key(unsigned int x, unsigned int y) {
    unsigned char key = '\0';
    LCD_AccessSelection(LCD_DisplaySelection_DDRAM2);
    LCD_BoldFont(0);
    LCD_Inverse(0);
    LCD_ReversedData(1);
    LCD_FontSize(1, 1);
    if (y > 0x50 && y < 0x70) {
        if (x > 0x00 && x < 0x20) {
            LCD_PositionString(0x00, 0x50, "��");
            key = '1';
        } else if (x > 0x20 && x < 0x40) {
            LCD_PositionString(0x04, 0x50, "��");
            key = '2';
        } else if (x > 0x40 && x < 0x60) {
            LCD_PositionString(0x08, 0x50, "��");
            key = '3';
        } else if (x > 0x60 && x < 0x80) {
            LCD_PositionString(0x0C, 0x50, "��");
            key = '4';
        } else if (x > 0x80 && x < 0xA0) {
            LCD_PositionString(0x10, 0x50, "��");
            key = '5';
        } else if (x > 0xA0 && x < 0xC0) {
            LCD_PositionString(0x14, 0x50, "��");
            key = '6';
        } else if (x > 0xC0 && x < 0xE0) {
            LCD_PositionString(0x18, 0x50, "��");
            key = '7';
        } else if (x > 0xE0 && x < 0x100) {
            LCD_PositionString(0x1C, 0x50, "��");
            key = '8';
        } else if (x > 0x100 && x < 0x120) {
            LCD_PositionString(0x20, 0x50, "��");
            key = '9';
        } else if (x > 0x120 && x < 0x140) {
            LCD_PositionString(0x24, 0x50, "��");
            key = '0';
        }
    } else if (y > 0x70 && y < 0x90) {
        if (x > 0x00 && x < 0x20) {
            LCD_PositionString(0x00, 0x70, "��");
            key = 'Q';
        } else if (x > 0x20 && x < 0x40) {
            LCD_PositionString(0x04, 0x70, "��");
            key = 'W';
        } else if (x > 0x40 && x < 0x60) {
            LCD_PositionString(0x08, 0x70, "��");
            key = 'E';
        } else if (x > 0x60 && x < 0x80) {
            LCD_PositionString(0x0C, 0x70, "��");
            key = 'R';
        } else if (x > 0x80 && x < 0xA0) {
            LCD_PositionString(0x10, 0x70, "��");
            key = 'T';
        } else if (x > 0xA0 && x < 0xC0) {
            LCD_PositionString(0x14, 0x70, "��");
            key = 'Y';
        } else if (x > 0xC0 && x < 0xE0) {
            LCD_PositionString(0x18, 0x70, "��");
            key = 'U';
        } else if (x > 0xE0 && x < 0x100) {
            LCD_PositionString(0x1C, 0x70, "��");
            key = 'I';
        } else if (x > 0x100 && x < 0x120) {
            LCD_PositionString(0x20, 0x70, "��");
            key = 'O';
        } else if (x > 0x120 && x < 0x140) {
            LCD_PositionString(0x24, 0x70, "��");
            key = 'P';
        }
    } else if (y > 0x90 && y < 0xB0) {
        if (x > 0x10 && x < 0x30) {
            LCD_PositionString(0x02, 0x90, "��");
            key = 'A';
        } else if (x > 0x30 && x < 0x50) {
            LCD_PositionString(0x06, 0x90, "��");
            key = 'S';
        } else if (x > 0x50 && x < 0x70) {
            LCD_PositionString(0x0A, 0x90, "��");
            key = 'D';
        } else if (x > 0x70 && x < 0x90) {
            LCD_PositionString(0x0E, 0x90, "��");
            key = 'F';
        } else if (x > 0x90 && x < 0xB0) {
            LCD_PositionString(0x12, 0x90, "��");
            key = 'G';
        } else if (x > 0xB0 && x < 0xD0) {
            LCD_PositionString(0x16, 0x90, "��");
            key = 'H';
        } else if (x > 0xD0 && x < 0xF0) {
            LCD_PositionString(0x1A, 0x90, "��");
            key = 'J';
        } else if (x > 0xF0 && x < 0x110) {
            LCD_PositionString(0x1E, 0x90, "��");
            key = 'K';
        } else if (x > 0x110 && x < 0x130) {
            LCD_PositionString(0x22, 0x90, "��");
            key = 'L';
        }
    } else if (y > 0xB0 && y < 0xD0) {
        if (x > 0x20 && x < 0x40) {
            LCD_PositionString(0x04, 0xB0, "��");
            key = 'Z';
        } else if (x > 0x40 && x < 0x60) {
            LCD_PositionString(0x08, 0xB0, "��");
            key = 'X';
        } else if (x > 0x60 && x < 0x80) {
            LCD_PositionString(0x0C, 0xB0, "��");
            key = 'C';
        } else if (x > 0x80 && x < 0xA0) {
            LCD_PositionString(0x10, 0xB0, "��");
            key = 'V';
        } else if (x > 0xA0 && x < 0xC0) {
            LCD_PositionString(0x14, 0xB0, "��");
            key = 'B';
        } else if (x > 0xC0 && x < 0xE0) {
            LCD_PositionString(0x18, 0xB0, "��");
            key = 'N';
        } else if (x > 0xE0 && x < 0x100) {
            LCD_PositionString(0x1C, 0xB0, "��");
            key = 'M';
        } else if (x > 0x100 && x < 0x120) {
            LCD_PositionString(0x20, 0xB0, "�D");
            key = '.';
        } else if (x > 0x120 && x < 0x140) {
            LCD_PositionString(0x24, 0xB0, "BS");
            key = 0x08;
        }
    } else if (y > 0xD0 && y < 0xF0) {
        if (x > 0x80 && x < 0xA0) {
            LCD_PositionString(0x10, 0xD0, "��");
            key = 0x11;
        } else if (x > 0xA0 && x < 0xC0) {
            LCD_PositionString(0x14, 0xD0, "��");
            key = 0x10;
        }
    }
    return key;
}
