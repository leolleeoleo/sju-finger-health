;------------------------------------------------------------------------------
;  Several IAP calls are available for use by an application program to permit
;  selective erasing, reading and programming of flash sectors, security bit,
;  configuration bytes, and device id. All calls are made through a common
;  interface, PGM_MTP. The programming functions are selected by setting up
;  the microcontroller’s registers before making a call to PGM_MTP at 1FF0H.
;------------------------------------------------------------------------------

/* special function registrs */
sfr FCF = 0xB1							 
							
/* PGM_MTP location */
PGM_MTP         EQU     0x1FF0

;------------------------------------------------------------------------------
;  Program User Code
;
;  Input parameters:
;  R1 = 02H
;  DPH = memory address MSB
;  DPL = memory address LSB
;  ACC = byte to program
;  Return parameter(s):
;  ACC = 00 = pass
;  ACC = !00 = fail
;------------------------------------------------------------------------------
?PR?_IAP_ProgramUserCode?IAP SEGMENT CODE
RSEG ?PR?_IAP_ProgramUserCode?IAP
PUBLIC _IAP_ProgramUserCode
_IAP_ProgramUserCode:
                PUSH    IE
                CLR     EA
                ANL     FCF,#0FCH
                MOV     DPH,R6
                MOV     DPL,R7
                MOV     A,R5
                MOV     R1,#02H
                CALL    PGM_MTP
                MOV     R7,A
                ORL     FCF,#001H
                POP     IE
                RET

;------------------------------------------------------------------------------
;  Erase sector
;
;  Input parameters:
;  R1 = 08H
;  DPH = sector address high byte
;  DPL = sector address low byte
;  Return parameter(s):
;  ACC = 00 = pass
;  ACC = !00 = fail
;------------------------------------------------------------------------------
?PR?_IAP_EraseSector?IAP  SEGMENT CODE
RSEG ?PR?_IAP_EraseSector?IAP
PUBLIC _IAP_EraseSector
_IAP_EraseSector:
                PUSH    IE
                CLR     EA
                ANL     FCF,#0FCH
                MOV     DPH,R6
                MOV     DPL,R7
                MOV     R1,#08H
                CALL    PGM_MTP
                MOV     R7,A
                ORL     FCF,#001H
                POP     IE
                RET
;------------------------------------------------------------------------------
                END
