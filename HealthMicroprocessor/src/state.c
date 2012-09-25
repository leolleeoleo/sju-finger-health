#include <stdio.h>
#include "ascii.h"
#include "text.h"
#include "gprs.h"
#include "base.h" 
#include "interrupt.h"
#include "configuration.h"
#include "lcd.h"
#include "tfs_m51.h"
#include "uart.h"
#include "flash.h"
#include "configure.h"
#include "client.h"

void STATE_menu();
void STATE_menu_down(unsigned int, unsigned int);
void STATE_roll();
void STATE_roll_down(unsigned int, unsigned int);
void STATE_status();
void STATE_status_action(unsigned int, unsigned int);
void STATE_config();
void STATE_config_down(unsigned int, unsigned int);

void STATE_initial() {
    unsigned char i = 0;
    GPRS_initial();
    INT_T0_clean();
    INT_T0_initial();
    delay_ms(1000);
    FLASH_startup(1);
    delay_ms(1000);
    GPRS_initialTCPIP();
    do {
        if (INT_T0_check()) {
            INT_T0_clean();
            i |= FLASH_startup(0);
            //			i |= CLIENT_act(0) << 1;
            INT_T0_initial();
        }
    } while (i != 0x01);
    delay_ms(1000);
    INT_T0_clean();
    LCD_TouchPanelEnable(1);
    LCD_TouchPanelManual(0);
    LCD_TouchPanelInterrupt(1);
    delay_ms(1000);
    STATE_menu();
    LCD_interruptWait();
    INT_X1_clean();
    CONFIGURATION_read();
}

#define STATE_TYPE_MENU 0x00
#define STATE_TYPE_ROLL 0x01
#define STATE_TYPE_STATUS 0x02
#define STATE_TYPE_CONFIG 0x03
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
        case STATE_TYPE_STATUS:
            STATE_status_action(x, y);
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
            case STATE_TYPE_STATUS:
                STATE_status();
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
    INT_T0_clean();
    LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
    LCD_Display(0x00);
    LCD_setDefault();
    LCD_FontSize(1, 1);
    LCD_PositionString(0x0A, 0x00, TEXT_name);
    LCD_PositionString(0x10, 0x60, TEXT_roll);
    LCD_PositionString(0x04, 0xD0, "狀態");
    LCD_DisplaySelection(LCD_DisplaySelection_DDRAM1);
    STATE_STATE = 0x00;
}

idata unsigned char configcheck = 0;

void STATE_menu_down(unsigned int x, unsigned int y) {
    LCD_ReversedData(1);
    if (y > 96 && y < 128) {
        if (x > 128 && x < 192) {
            LCD_PositionString(0x10, 0x60, TEXT_roll);
            STATE_STATE = STATE_TYPE_ROLL;
            STATE_renew = 0x01;
        }
    } else if (y > 208 && y < 240) {
        if (x > 32 && x < 96) {
            LCD_PositionString(0x04, 0xD0, "狀態");
            STATE_STATE = STATE_TYPE_STATUS;
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

void STATE_roll_finger();
unsigned char STATE_roll_finger_action(unsigned int, unsigned int);
void STATE_roll_connect();
unsigned char STATE_roll_connect_action(unsigned int, unsigned int);
void STATE_roll_show();
unsigned char STATE_roll_show_action(unsigned int, unsigned int);

#define STATE_roll_type_finger 0x00
#define STATE_roll_type_connect 0x01
#define STATE_roll_type_show 0x02

idata unsigned char STATE_roll_state = STATE_roll_type_finger;

void STATE_roll() {
    switch (STATE_roll_state) {
        case STATE_roll_type_finger:
            STATE_roll_finger();
            break;
        case STATE_roll_type_connect:
            STATE_roll_connect();
            break;
        case STATE_roll_type_show:
            STATE_roll_show();
            break;
        default:
            STATE_roll_state = STATE_roll_type_finger;
            break;
    }
}

void STATE_roll_down(unsigned int x, unsigned int y) {
    unsigned char rs;
    switch (STATE_roll_state) {
        case STATE_roll_type_finger:
            rs = STATE_roll_finger_action(x, y);
            break;
        case STATE_roll_type_connect:
            rs = STATE_roll_connect_action(x, y);
            break;
        case STATE_roll_type_show:
            rs = STATE_roll_show_action(x, y);
            break;
    }
    if (rs == ASCII_ESC) {
        LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
        LCD_ReversedData(1);
        LCD_PositionString(0x1C, 0xD0, TEXT_back);
        STATE_roll_state = STATE_roll_type_finger;
        INT_T0_clean();
        STATE_STATE = STATE_TYPE_MENU;
        STATE_renew = 0x01;
    }
}

void STATE_roll_finger() {
    if (!INT_T0_check()) {
        FINGER_action();
        FLASH_finger(1);
    }
    if (FINGER_check()) {
        INT_T0_clean();
        STATE_roll_state = STATE_roll_type_connect;
        STATE_roll_connect();
    } else {
	    FLASH_finger(0);
	}
    INT_T0_initial();
}

unsigned char STATE_roll_finger_action(unsigned int x, unsigned int y) {
    if (y > 208 && y < 240) {
        if (x > 224 && x < 288) {
		    FINGER_deleteall();
            return ASCII_ESC;
        }
    }
    return ASCII_NULL;
}

void STATE_roll_connect() {
    if (!INT_T0_check()) {
        LCD_DisplaySelection(LCD_DisplaySelection_DDRAM1);
        LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
        LCD_Display(0x00);
        LCD_PositionString(0x07, 0x30, "連線中，請稍候");
        LCD_PositionString(0x1C, 0xD0, TEXT_back);
        PROTOCOL_action(1);
    }
    if (PROTOCOL_action(0) != 0) {
        STATE_roll_state = STATE_roll_type_show;
    }
    INT_T0_initial();
}

unsigned char STATE_roll_connect_action(unsigned int x, unsigned int y) {
    if (y > 208 && y < 240) {
        if (x > 224 && x < 288) {
            PROTOCOL_action(2);
            return ASCII_ESC;
        }
    }
    return ASCII_NULL;
}

void STATE_roll_show() {
    INT_T0_clean();
    LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
    LCD_Display(0x00);
    LCD_setDefault();
    LCD_FontSize(1, 1);
    if (PROTOCOL_action(0) == 1) {
        LCD_PositionString(0x0C, 0x00, TEXT_healthRecode);
        LCD_PositionString(0x08, 0x30, "姓名：");
        LCD_PositionString(0x14, 0x30, CLIENT_getName());
        LCD_PositionString(0x08, 0x90, "里程累計：");
        LCD_PositionString(0x1D, 0x90, CLIENT_getPoint());
        LCD_PositionString(0x1C, 0xD0, "返回");
    } else {
        LCD_PositionString(0x07, 0x30, "指紋辨識失敗");
        LCD_PositionString(0x07, 0x50, "，請重新點名");
        LCD_PositionString(0x1C, 0xD0, "返回");
    }
    LCD_DisplaySelection(LCD_DisplaySelection_DDRAM1);
}

unsigned char STATE_roll_show_action(unsigned int x, unsigned int y) {
    if (y > 208 && y < 240) {
        if (x > 224 && x < 288) {
            return ASCII_ESC;
        }
    }
    return ASCII_NULL;
}

//**************************************************************************************

void STATE_status() {
    LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
    LCD_Display(0x00);
    LCD_setDefault();
    LCD_PositionString(0x10, 0x00, "狀態");
    LCD_PositionString(0x04, 0x40, "訊號");
    LCD_PositionString(0x0E, 0x40, GPRS_getSignalQuality());
    LCD_PositionString(0x04, 0x60, "電信");
    LCD_PositionString(0x0E, 0x60, GPRS_getOperatorSlelction());
    LCD_PositionString(0x04, 0x80, "位址");
    LCD_PositionString(0x0E, 0x80, GPRS_getIPAddress());
    LCD_PositionString(0x04, 0xD0, "連線");
    LCD_PositionString(0x1C, 0xD0, TEXT_back);
    LCD_DisplaySelection(LCD_DisplaySelection_DDRAM1);
}

void STATE_status_action(unsigned int x, unsigned int y) {
    LCD_ReversedData(1);
    if (y > 208 && y < 240) {
        if (x > 32 && x < 96) {
            LCD_PositionString(0x04, 0xD0, "連線");
            GPRS_initialTCPIP();
            STATE_STATE = STATE_TYPE_MENU;
            STATE_renew = 0x01;
        } else if (x > 224 && x < 288) {
            LCD_PositionString(0x1C, 0xD0, TEXT_back);
            STATE_STATE = STATE_TYPE_MENU;
            STATE_renew = 0x01;
        }
    }
    LCD_ReversedData(0);
}

//**************************************************************************************

void STATE_config_main();
unsigned char STATE_config_main_action(unsigned int, unsigned int);

#define STATE_config_type_main 0xFF
#define STATE_config_type_account 0x00
#define STATE_config_type_password 0x01
#define STATE_config_type_server 0x02

idata unsigned char STATE_config_state = STATE_config_type_main;

void STATE_config() {
    printf("STATE config");
    switch (STATE_config_state) {
        case STATE_config_type_main:
            STATE_config_main();
            break;
        case STATE_config_type_account:
            CONFIGURE_show("帳號");
            break;
        case STATE_config_type_password:
            CONFIGURE_show("密碼");
            break;
        case STATE_config_type_server:
            CONFIGURE_show("伺服器");
            break;
        default:
            STATE_config_state = STATE_config_type_main;
            STATE_config_main();
            break;
    }
}

void STATE_config_down(unsigned int x, unsigned int y) {
    unsigned char rs = 0x00;
    if (STATE_config_state == STATE_config_type_main) {
        rs = STATE_config_main_action(x, y);
    } else {
        rs = CONFIGURE_action(x, y);
    }
    if (rs != 0x00) {
        if (rs == 0x1B) {
            STATE_config_state = STATE_config_type_main;
        } else if (rs == 0x0D) {
            STATE_config_state = STATE_config_type_main;
        }
        STATE_renew = 0x01;
    }
}

void STATE_config_main() {
    LCD_AccessSelection(LCD_AccessSelection_DDRAM1);
    LCD_Display(0x00);
    LCD_setDefault();
    LCD_PositionString(0x10, 0x00, "組態設定");
    LCD_PositionString(0x04, 0x40, "帳號");
    LCD_PositionString(0x04, 0x60, "密碼");
    LCD_PositionString(0x04, 0x80, "伺服器");
    LCD_PositionString(0x0E, 0x40, CONFIGURATION_get(STATE_config_type_account));
    LCD_PositionString(0x0E, 0x60, CONFIGURATION_get(STATE_config_type_password));
    LCD_PositionString(0x0E, 0x80, CONFIGURATION_get(STATE_config_type_server));
    LCD_PositionString(0x04, 0xD0, "儲存");
    LCD_PositionString(0x10, 0xD0, "重設");
    LCD_PositionString(0x1C, 0xD0, TEXT_back);
    LCD_DisplaySelection(LCD_DisplaySelection_DDRAM1);
}

unsigned char STATE_config_main_action(unsigned int x, unsigned int y) {
    LCD_ReversedData(1);
    if (y > 64 && y < 96) {
        if (x > 32 && x < 96) {
            LCD_PositionString(0x04, 0x40, "帳號");
            CONFIGURE_set(CONFIGURATION_get(STATE_config_type_account));
            STATE_config_state = STATE_config_type_account;
            return 0x0A;
        }
    } else if (y > 96 && y < 128) {
        if (x > 32 && x < 96) {
            LCD_PositionString(0x04, 0x60, "密碼");
            CONFIGURE_set(CONFIGURATION_get(STATE_config_type_password));
            STATE_config_state = STATE_config_type_password;
            return 0x0A;
        }
    } else if (y > 128 && y < 160) {
        if (x > 32 && x < 96) {
            LCD_PositionString(0x04, 0x80, "伺服器");
            CONFIGURE_set(CONFIGURATION_get(STATE_config_type_server));
            STATE_config_state = STATE_config_type_server;
            return 0x0A;
        }

    } else if (y > 208 && y < 240) {
        if (x > 32 && x < 96) {
            LCD_PositionString(0x04, 0xD0, "儲存");
            CONFIGURATION_write();
            return 0x0D;
        } else if (x > 128 && x < 192) {
            LCD_PositionString(0x10, 0xD0, "重設");
            CONFIGURATION_read();
            return 0x0A;
        } else if (x > 224 && x < 288) {
            LCD_PositionString(0x1C, 0xD0, TEXT_back);
            STATE_config_state = STATE_config_type_main;
            STATE_STATE = STATE_TYPE_MENU;
            return 0x1B;
        }
    }
    return 0x00;
}

