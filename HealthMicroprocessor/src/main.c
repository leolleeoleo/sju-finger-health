#include <stdio.h>
#include "base.h"
#include "uart.h"
#include "interrupt.h"
#include "lcd.h"
#include "flash.h"
#include "sim300.h"
#include "state.h"

main() {
    delay_ms(5000);
    UART_initial(19200);
    printf("UART initial\r\n");
    delay_ms(1000);
    INT_initial();
    printf("INTERRUPT initial\r\n");
    delay_ms(1000);
    UART1_initial(115200);
    UART2_initial(19200);
    printf("UART1 UART2 initial\r\n");
    delay_ms(1000);
    LCD_initial();
    printf("LCD initial\r\n");
    delay_ms(1000);

    state_initial();
    printf("STATE initial\r\n");

    while (1) {
        if (INT_X1_check()) {
            LCD_interrupt();
            if (LCD_TPcheck()) {
                state_down(LCD_getX(), LCD_getY());
                LCD_interruptWait();
                state_up(0);
            }
            LCD_TPclean();
            LCD_interruptWait();
            INT_X1_clean();
        }
        if (INT_T0_check()) {
            INT_T0_clean();
            state_up(1);
        }
    }
}
