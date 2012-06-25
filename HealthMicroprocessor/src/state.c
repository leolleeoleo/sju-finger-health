#include <stdio.h>
#include "base.h" 
#include "interrupt.h"
#include "client.h"
#include "sim300.h"
#include "config.h"
#include "lcd.h"
#include "tfs_m51.h"
#include "uart.h"
#include "flash.h"
#include "keyboard.h"

void STATE_menu();
void STATE_menu_down(unsigned int, unsigned int);
void STATE_roll();
void STATE_roll_down(unsigned int, unsigned int);
void STATE_config();
void STATE_config_down(unsigned int, unsigned int);

void STATE_initial() {
    unsigned char i = 0;
    INT_T0_clean();
    INT_T0_initial();
    GPRS_initial(1);
    delay_ms(1000);
    FLASH_startup(1);
    delay_ms(1000);
    do {
        if (INT_T0_check()) {
            INT_T0_clean();
            i |= FLASH_startup(0);
            i |= (GPRS_initial(0) << 1);
            INT_T0_initial();
        }
    } while (i != 0x03);
    INT_T0_clean();

    LCD_TouchPanelEnable(1);
    LCD_TouchPanelManual(0);
    LCD_TouchPanelInterrupt(1);
    STATE_menu();
    LCD_interruptWait();
    INT_X1_clean();

    CONFIG_read();
}

#define STATE_TYPE_MENU 0x00
#define STATE_TYPE_ROLL 0x01
#define STATE_TYPE_CONFIG 0x02
idata unsigned char STATE_STATE = 0x00;
idata unsigned char STATE_renew = 0x01;

void state_down(unsigned int x, unsigned int y) {
    printf("STATE down x:%i, y:%i\r\n", x, y);
    printf("STATE type = %u", STATE_STATE);
    switch (STATE_STATE) {
        case STATE_TYPE_MENU:
            STATE_menu_down(x, y);
            break;
        case STATE_TYPE_ROLL:
            STATE_roll_down(x, y);
            break;
        case STATE_TYPE_CONFIG:
            STATE_config_down(x, y);
            break;
        default:
            STATE_STATE = STATE_TYPE_MENU;
            STATE_menu_down(x, y);
    }
}

void state_up(unsigned char force) {
    if (STATE_renew || force) {
        switch (STATE_STATE) {
            case STATE_TYPE_MENU:
                STATE_menu();
                break;
            case STATE_TYPE_ROLL:
                STATE_roll();
                break;
            case STATE_TYPE_CONFIG:
                STATE_config();
                break;
            default:
                STATE_menu();
                break;
        }
        delay_ms(100);
        STATE_renew = 0x00;
    }
}


//**************************************************************************************

void STATE_menu() {
    LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
    LCD_Display(0x00);
    LCD_BoldFont(0);
    LCD_Inverse(0);
    LCD_ReversedData(0);
    LCD_FontSize(1, 1);
    LCD_PositionString(0x06, 0x00, "健康加值一點靈");
    LCD_PositionString(0x10, 0x60, "點名");
    LCD_DisplaySelection(LCD_DisplaySelection_DDRAM1);
    STATE_STATE = 0x00;
}

idata unsigned char configcheck = 0;

void STATE_menu_down(unsigned int x, unsigned int y) {
    LCD_ReversedData(1);
    if (y > 96 && y < 128) {
        if (x > 128 && x < 192) {
            LCD_PositionString(0x10, 0x60, "點名");
            STATE_STATE = STATE_TYPE_ROLL;
            STATE_renew = 0x01;
        }
    }
    switch (configcheck) {
        case 0:
            if (y > 0x00 && y < 0x40 && x > 0x100 && x < 0x140) {
                printf("1y\r\n");
                configcheck++;
            } else {
                printf("1n\r\n");
                configcheck = 0;
            }
            break;
        case 1:
            if (y > 0x00 && y < 0x40 && x > 0x00 && x < 0x40) {
                printf("2y\r\n");
                configcheck++;
            } else {
                printf("2n\r\n");
                configcheck = 0;
            }
            break;
        default:
            if (y > 0xB0 && y < 0xF0 && x > 0x00 && x < 0x40) {
                printf("3y\r\n");
                STATE_STATE = STATE_TYPE_CONFIG;
                STATE_renew = 0x01;
                configcheck = 0;
            } else {
                printf("3n\r\n");
                configcheck = 0;
            }
            break;
    }

    LCD_ReversedData(0);
}

//************************************************************************************

void STATE_roll_main();
void STATE_roll_main_action(unsigned int, unsigned int);
void STATE_roll_connect();
void STATE_roll_connect_action(unsigned int, unsigned int);
void STATE_roll_show();
void STATE_roll_show_action(unsigned int, unsigned int);
//void STATE_roll_error();
//void STATE_roll_error_action(unsigned int, unsigned int);

#define STATE_roll_type_main 0x00
#define STATE_roll_type_connect 0x01
#define STATE_roll_type_show 0x02
//#define STATE_roll_type_error 0x03

idata unsigned char STATE_roll_state = 0xFF;
idata unsigned char *finger;

code unsigned char aa[] = {0xB0, 0xB7, 0xB1, 0x64, 0xB0, 0x4F, 0xBF, 0xFD, 0x00};
code unsigned char bb[] = {0xA8, 0xBD, 0xB5, 0x7B, 0xB0, 0x4F, 0xBF, 0xFD, 0xA1, 0x47, 0x00};

void STATE_roll() {
    switch (STATE_roll_state) {
        case STATE_roll_type_main:
            STATE_roll_main();
            break;
        case STATE_roll_type_connect:
            STATE_roll_connect();
            break;
        case STATE_roll_type_show:
            STATE_roll_show();
            break;
            //		case STATE_roll_type_error:
            //            STATE_roll_error();
            //            break;
        default:
            FINGER_action();
            FLASH_finger(1);
            STATE_roll_state = STATE_roll_type_main;
            STATE_roll_main();
            break;
    }
}

void STATE_roll_down(unsigned int x, unsigned int y) {
    switch (STATE_roll_state) {
        case STATE_roll_type_main:
            STATE_roll_main_action(x, y);
            break;
        case STATE_roll_type_connect:
            STATE_roll_connect_action(x, y);
            break;
        case STATE_roll_type_show:
            STATE_roll_show_action(x, y);
            break;
            //		case STATE_roll_type_error:
            //            STATE_roll_error_action(x, y);
            //            break;
    }
}

void STATE_roll_main() {
    FLASH_finger(0);
    if (FINGER_check()) {
        STATE_roll_state = STATE_roll_type_connect;
        PROTOCOL_action(1);
    }
    INT_T0_initial();
}

void STATE_roll_main_action(unsigned int x, unsigned int y) {
    LCD_ReversedData(1);
    if (y > 208 && y < 240) {
        if (x > 224 && x < 288) {
            LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
            LCD_PositionString(0x1C, 0xD0, "返回");
            STATE_roll_state = 0xFF;
            INT_T0_clean();
            STATE_STATE = STATE_TYPE_MENU;
            STATE_renew = 0x01;
        }
    }
    LCD_ReversedData(0);
}

void STATE_roll_connect() {
    LCD_DisplaySelection(LCD_DisplaySelection_DDRAM1);
    LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
    LCD_Display(0x00);
    LCD_PositionString(0x07, 0x30, "連線中，請稍候");
    LCD_PositionString(0x1C, 0xD0, "返回");
    if (PROTOCOL_action(0)) {
        STATE_roll_state = STATE_roll_type_show;
    }
    INT_T0_initial();

}

void STATE_roll_connect_action(unsigned int x, unsigned int y) {
    LCD_ReversedData(1);
    if (y > 208 && y < 240) {
        if (x > 224 && x < 288) {
            LCD_PositionString(0x1C, 0xD0, "返回");
            GPRS_close();
            STATE_roll_state = 0xFF;
            INT_T0_clean();
            STATE_STATE = STATE_TYPE_MENU;
            STATE_renew = 0x01;
        }
    }
    LCD_ReversedData(0);
}

void STATE_roll_show() {
    LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
    LCD_Display(0x00);
    LCD_BoldFont(0);
    LCD_Inverse(0);
    LCD_ReversedData(0);
    LCD_FontSize(1, 1);
    if (PROTOCOL_action(0) == 1) {
        LCD_PositionString(0x0C, 0x00, aa);
        LCD_PositionString(0x08, 0x30, "姓名：");
        LCD_PositionString(0x14, 0x30, CLIENT_getName());
        LCD_PositionString(0x08, 0x90, "里程累計：");
        LCD_PositionString(0x1D, 0x90, CLIENT_getPoint());
        LCD_PositionString(0x1C, 0xD0, "返回");
    } else if (PROTOCOL_action(0) == 2) {
        LCD_PositionString(0x07, 0x30, "指紋辨識失敗");
        LCD_PositionString(0x07, 0x50, "，請重新點名");
        LCD_PositionString(0x1C, 0xD0, "返回");
    }
    LCD_DisplaySelection(LCD_DisplaySelection_DDRAM1);
}

void STATE_roll_show_action(unsigned int x, unsigned int y) {
    LCD_ReversedData(1);
    if (y > 208 && y < 240) {
        if (x > 224 && x < 288) {
            LCD_PositionString(0x1C, 0xD0, "返回");
            STATE_roll_state = 0xFF;
            STATE_STATE = STATE_TYPE_MENU;
            STATE_renew = 0x01;
        }
    }
    LCD_ReversedData(0);
}
/*
void STATE_roll_errer() {
    LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
    LCD_Display(0x00);
    LCD_BoldFont(0);
    LCD_Inverse(0);
    LCD_ReversedData(0);
    LCD_FontSize(1, 1);
        LCD_PositionString(0x07, 0x30, "指紋辨識失敗，請重新點名");
    LCD_PositionString(0x1C, 0xD0, "返回");
    LCD_DisplaySelection(LCD_DisplaySelection_DDRAM1);
}

void STATE_roll_error_action(unsigned int x, unsigned int y) {
    LCD_ReversedData(1);
    if (y > 208 && y < 240) {
        if (x > 224 && x < 288) {
            LCD_PositionString(0x1C, 0xD0, "返回");	
                        STATE_roll_state = 0xFF;
            STATE_STATE = STATE_TYPE_MENU;
            STATE_renew = 0x01;
        }
    }
    LCD_ReversedData(0);
}
 */
//**************************************************************************************

void STATE_config_main();
void STATE_config_main_action(unsigned int, unsigned int);
void STATE_config_account();
void STATE_config_account_action(unsigned int, unsigned int);
void STATE_config_password();
void STATE_config_password_action(unsigned int, unsigned int);
void STATE_config_server();
void STATE_config_server_action(unsigned int, unsigned int);

#define STATE_config_type_main 0x00
#define STATE_config_type_account 0x01
#define STATE_config_type_password 0x02
#define STATE_config_type_server 0x03

idata unsigned char STATE_config_state = 0x00;
idata unsigned char *pointer;
idata unsigned char cursor;

void STATE_config() {
    printf("STATE config");
    switch (STATE_config_state) {
        case STATE_config_type_main:
            STATE_config_main();
            break;
        case STATE_config_type_account:
            STATE_config_account();
            break;
        case STATE_config_type_password:
            STATE_config_password();
            break;
        case STATE_config_type_server:
            STATE_config_server();
            break;
        default:
            STATE_config_state = STATE_config_type_main;
            STATE_config_main();
            break;
    }
}

void STATE_config_down(unsigned int x, unsigned int y) {
    switch (STATE_config_state) {
        case STATE_config_type_main:
            STATE_config_main_action(x, y);
            break;
        case STATE_config_type_account:
            STATE_config_account_action(x, y);
            break;
        case STATE_config_type_password:
            STATE_config_password_action(x, y);
            break;
        case STATE_config_type_server:
            STATE_config_server_action(x, y);
            break;
        default:
            STATE_config_state = STATE_config_type_main;
            break;
    }
}

void STATE_config_main() {
    LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
    LCD_Display(0x00);
    LCD_BoldFont(0);
    LCD_Inverse(0);
    LCD_ReversedData(0);
    LCD_FontSize(0, 0);
    LCD_PositionString(0x10, 0x00, "組態設定");
    LCD_PositionString(0x04, 0x40, "帳號");
    LCD_PositionString(0x04, 0x60, "密碼");
    LCD_PositionString(0x04, 0x80, "伺服器");
    LCD_PositionString(0x0E, 0x40, CONFIG_getAccount());
    LCD_PositionString(0x0E, 0x60, CONFIG_getPassword());
    LCD_PositionString(0x0E, 0x80, CONFIG_getServer());
    LCD_PositionString(0x04, 0xD0, "儲存");
    LCD_PositionString(0x10, 0xD0, "重設");
    LCD_PositionString(0x1C, 0xD0, "返回");
    LCD_DisplaySelection(LCD_DisplaySelection_DDRAM1);
}

void STATE_config_main_action(unsigned int x, unsigned int y) {
    LCD_ReversedData(1);
    if (y > 64 && y < 96) {
        if (x > 32 && x < 96) {
            LCD_PositionString(0x04, 0x40, "帳號");
            pointer = CONFIG_getAccount();
            cursor = 0;
            STATE_config_state = STATE_config_type_account;
            STATE_renew = 0x01;
        }
    } else if (y > 96 && y < 128) {
        if (x > 32 && x < 96) {
            LCD_PositionString(0x04, 0x60, "密碼");
            pointer = CONFIG_getPassword();
            cursor = 0;
            STATE_config_state = STATE_config_type_password;
            STATE_renew = 0x01;
        }
    } else if (y > 128 && y < 160) {
        if (x > 32 && x < 96) {
            LCD_PositionString(0x04, 0x80, "伺服器");
            pointer = CONFIG_getServer();
            cursor = 0;
            STATE_config_state = STATE_config_type_server;
            STATE_renew = 0x01;
        }

    } else if (y > 208 && y < 240) {
        if (x > 32 && x < 96) {
            LCD_PositionString(0x04, 0xD0, "儲存");
            CONFIG_write();
            STATE_renew = 0x01;
        } else if (x > 128 && x < 192) {
            LCD_PositionString(0x10, 0xD0, "重設");
            CONFIG_read();
            STATE_renew = 0x01;
        } else if (x > 224 && x < 288) {
            LCD_PositionString(0x1C, 0xD0, "返回");
            STATE_config_state = 0xFF;
            STATE_STATE = STATE_TYPE_MENU;
            STATE_renew = 0x01;
        }
    }
    LCD_ReversedData(0);
}

//帳號----------------------

void STATE_config_account() {
    if (cursor > CONFIG_DATA_SIZE - 1) {
        cursor = 0;
    }
    KEYBOARD_show();
    LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
    LCD_Display(0x00);
    LCD_BoldFont(0);
    LCD_Inverse(0);
    LCD_ReversedData(0);
    LCD_FontSize(0, 0);
    LCD_PositionString(0x10, 0x00, "設定帳號");
    LCD_PositionString(0x0E, 0x20, pointer);
    LCD_PositionString(0x06, 0xE0, "確定");
    LCD_PositionString(0x1E, 0xE0, "取消");
    LCD_CursorPosition(0x0E + cursor, 0x20);
    LCD_CursorDisplay(1);
    LCD_CursorBlinking(1);
}

void STATE_config_account_action(unsigned int x, unsigned int y) {
    unsigned char key;
    key = KEYBOARD_key(x, y);
    if (key != '\0') {
        if (key == 0x10) {
            cursor++;
            LCD_CursorDisplay(0);
            STATE_renew = 0x01;
        } else if (key == 0x11) {
            cursor--;
            LCD_CursorDisplay(0);
            STATE_renew = 0x01;
        } else if (key == 0x08) {
            cursor--;
            pointer[cursor] = '\0';
            LCD_CursorDisplay(0);
            STATE_renew = 0x01;
        } else {
            pointer[cursor] = key;
            cursor++;
            LCD_CursorDisplay(0);
            STATE_renew = 0x01;
        }
    }
    LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
    LCD_FontSize(0, 0);
    LCD_ReversedData(1);
    if (y > 208 && y < 240) {
        if (x > 32 && x < 96) {
            LCD_PositionString(0x06, 0xE0, "確定");
            CONFIG_setAccount(pointer);
            STATE_config_state = STATE_config_type_main;
            LCD_CursorDisplay(0);
            STATE_renew = 0x01;
        } else if (x > 224 && x < 288) {
            LCD_PositionString(0x1E, 0xE0, "取消");
            STATE_config_state = STATE_config_type_main;
            LCD_CursorDisplay(0);
            STATE_renew = 0x01;
        }
    }
    LCD_ReversedData(0);
}

//密碼------------------------------

void STATE_config_password() {
    if (cursor > CONFIG_DATA_SIZE - 1) {
        cursor = 0;
    }
    KEYBOARD_show();
    LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
    LCD_Display(0x00);
    LCD_BoldFont(0);
    LCD_Inverse(0);
    LCD_ReversedData(0);
    LCD_FontSize(0, 0);
    LCD_PositionString(0x10, 0x00, "設定密碼");
    LCD_PositionString(0x0E, 0x20, pointer);
    LCD_PositionString(0x06, 0xE0, "確定");
    LCD_PositionString(0x1E, 0xE0, "取消");
    LCD_CursorPosition(0x0E + cursor, 0x20);
    LCD_CursorDisplay(1);
    LCD_CursorBlinking(1);
}

void STATE_config_password_action(unsigned int x, unsigned int y) {
    unsigned char key;
    key = KEYBOARD_key(x, y);
    if (key != '\0') {
        if (key == 0x10) {
            cursor++;
            LCD_CursorDisplay(0);
            STATE_renew = 0x01;
        } else if (key == 0x11) {
            cursor--;
            LCD_CursorDisplay(0);
            STATE_renew = 0x01;
        } else if (key == 0x08) {
            cursor--;
            pointer[cursor] = '\0';
            LCD_CursorDisplay(0);
            STATE_renew = 0x01;
        } else {
            pointer[cursor] = key;
            cursor++;
            LCD_CursorDisplay(0);
            STATE_renew = 0x01;
        }
    }
    LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
    LCD_FontSize(0, 0);
    LCD_ReversedData(1);
    if (y > 208 && y < 240) {
        if (x > 32 && x < 96) {
            LCD_PositionString(0x06, 0xE0, "確定");
            CONFIG_setPassword(pointer);
            STATE_config_state = STATE_config_type_main;
            LCD_CursorDisplay(0);
            STATE_renew = 0x01;
        } else if (x > 224 && x < 288) {
            LCD_PositionString(0x1E, 0xE0, "取消");
            STATE_config_state = STATE_config_type_main;
            LCD_CursorDisplay(0);
            STATE_renew = 0x01;
        }
    }
    LCD_ReversedData(0);
}
//伺服器-------------------

void STATE_config_server() {
    if (cursor > CONFIG_DATA_SIZE - 1) {
        cursor = 0;
    }
    KEYBOARD_show();
    LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
    LCD_Display(0x00);
    LCD_BoldFont(0);
    LCD_Inverse(0);
    LCD_ReversedData(0);
    LCD_FontSize(0, 0);
    LCD_PositionString(0x10, 0x00, "設定伺服器");
    LCD_PositionString(0x0E, 0x20, pointer);
    LCD_PositionString(0x06, 0xE0, "確定");
    LCD_PositionString(0x1E, 0xE0, "取消");
    LCD_CursorPosition(0x0E + cursor, 0x20);
    LCD_CursorDisplay(1);
    LCD_CursorBlinking(1);
}

void STATE_config_server_action(unsigned int x, unsigned int y) {
    unsigned char key;
    key = KEYBOARD_key(x, y);
    if (key != '\0') {
        if (key == 0x10) {
            cursor++;
            LCD_CursorDisplay(0);
            STATE_renew = 0x01;
        } else if (key == 0x11) {
            cursor--;
            LCD_CursorDisplay(0);
            STATE_renew = 0x01;
        } else if (key == 0x08) {
            cursor--;
            pointer[cursor] = '\0';
            LCD_CursorDisplay(0);
            STATE_renew = 0x01;
        } else {
            pointer[cursor] = key;
            cursor++;
            LCD_CursorDisplay(0);
            STATE_renew = 0x01;
        }
    }
    LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
    LCD_FontSize(0, 0);
    LCD_ReversedData(1);
    if (y > 208 && y < 240) {
        if (x > 32 && x < 96) {
            LCD_PositionString(0x06, 0xE0, "確定");
            CONFIG_setServer(pointer);
            STATE_config_state = STATE_config_type_main;
            LCD_CursorDisplay(0);
            STATE_renew = 0x01;
        } else if (x > 224 && x < 288) {
            LCD_PositionString(0x1E, 0xE0, "取消");
            STATE_config_state = STATE_config_type_main;
            LCD_CursorDisplay(0);
            STATE_renew = 0x01;
        }
    }
    LCD_ReversedData(0);
}
