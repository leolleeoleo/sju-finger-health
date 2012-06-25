/********************************************************************
                       VIIC_C51.C
  ���{�ǬOI2C�ާ@���x�]�D�覡���n�󥭥x�^�����h��C�l�{��,�p�o�e�ƾ�
�α����ƾ�,������o�e,�ô��ѤF�X�Ӫ������ﾹ�󪺾ާ@��ơA���ܤ�K��
�P�Τ�{�ǳs�����X�i.....  
  
    �`�N:��ƬO���γn�󩵮ɪ���k����SCL�ߨR,�T�ﰪ�����W�v�n�@ 
�@�w���ק�....(���ҬO1us�����P��,�Y�����W�v�n�p��12MHZ)

 ********************************************************************/

#include "base.h"

/* �`,�ܶq�w�q�� */

/*�ݤf��w�q*/
sbit SDA = P1^0; /*����I2C�ƾڶǰe��*/
sbit SCL = P1^1; /*����I2C���鱱���*/
/*���A�Ч�*/
bit ack; /*�����ЧӦ�*/

/**
 * IIC start
 */
void IIC_start() {
    SDA = 1;
    delay_us(1);
    SCL = 1;
    delay_us(1);
    SDA = 0;
    delay_us(1);
    SCL = 0;
    delay_us(1);
}

/**
 * IIC stop
 */
void IIC_stop() {
    SDA = 0;
    delay_us(1);
    SCL = 1;
    delay_us(1);
    SDA = 1;
    delay_us(1);
}

/*******************************************************************
                 �r�`�ƾڶǰe���               
��ƭ쫬: void  SendByte(uchar c);
�\��:  �N�ƾ�c�o�e�X�h,�i�H�O�a�},�]�i�H�O�ƾ�,�o���ᵥ������,�ù�
     �����A��i��ާ@.(�������ΫD��������ack=0 ��)     
     �o�e�ƾڥ��`�Aack=1; ack=0��ܳQ�����L�����ηl�a�C
 ********************************************************************/

void SendByte(unsigned char c) {
    unsigned char i;
    for (i = 0; i < 8; i++) {
        SDA = ((c << i)&0x80 ? 1 : 0);
        delay_us(1);
        SCL = 1;
        delay_us(1);
        SCL = 0;
        delay_us(1);
    }
    SDA = 1;
    delay_us(1);
    SCL = 1;
    delay_us(1);
    ack = (SDA ? 0 : 1);
    SCL = 0;
    delay_us(2);
}

/*******************************************************************
                 �r�`�ƾڶǰe���               
��ƭ쫬: uchar  RcvByte();
�\��:  �Ψӱ����q����ǨӪ��ƾ�,�çP�_�`�u���~(���o�����H��)�A
     �o����Х�������ơC  
 ********************************************************************/
unsigned char RcvByte() {
    unsigned char buff = 0;
    unsigned char i;
    SDA = 1;
    for (i = 0; i < 8; i++) {
        delay_us(1);
        SCL = 0;
        delay_us(1);
        SCL = 1;
        delay_us(1);
        buff <<= 1;
        buff += (SDA ? 1 : 0);
        delay_us(1);
    }
    SCL = 0;
    delay_us(2);
    return (buff);
}

/********************************************************************
                     �����l���
�쫬:  void IIC_ack(bit a);
 
�\��:�D�����i�������H��,(�i�H�O�����ΫD�����H��)
 ********************************************************************/
void IIC_ack(bit a) {
    SDA = (a ? 1 : 0);
    delay_us(1);
    SCL = 1;
    delay_us(1);
    SCL = 0;
    delay_us(1);
}

/*******************************************************************
                    �V�L�l�a�}����o�e�r�`�ƾڨ��               
��ƭ쫬: bit  ISendByte(uchar sla,ucahr c);  
�\��:     �q�Ұ��`�u��o�e�a�}�A�ƾڡA�����`�u�����L�{,�q����a�}sla.
           �p�G��^1��ܾާ@���\�A�_�h�ާ@���~�C
�`�N�G    �ϥΫe�����w�����`�u�C
 ********************************************************************/
bit ISendByte(unsigned char sla, unsigned char c) {
    IIC_start(); /*�Ұ��`�u*/
    SendByte(sla); /*�o�e����a�}*/
    if (!ack)
        return 0;
    SendByte(c); /*�o�e�ƾ�*/
    if (!ack)
        return 0;
    IIC_stop(); /*�����`�u*/
    return 1;
}

/*******************************************************************
                    �V���l�a�}����o�e�h�r�`�ƾڨ��               
��ƭ쫬: bit  ISendStr(uchar sla,uchar suba,ucahr *s,uchar no);  
�\��:     �q�Ұ��`�u��o�e�a�}�A�l�a�},�ƾڡA�����`�u�����L�{,�q����
          �a�}sla�A�l�a�}suba�A�o�e���e�Os���V�����e�A�o�eno�Ӧr�`�C
           �p�G��^1��ܾާ@���\�A�_�h�ާ@���~�C
�`�N�G    �ϥΫe�����w�����`�u�C
 ********************************************************************/
bit ISendStr(unsigned char sla, unsigned char suba, unsigned char *s, unsigned char length) {
    unsigned char i;

    IIC_start(); /*�Ұ��`�u*/
    SendByte(sla); /*�o�e����a�}*/
    if (ack == 0)
        return (0);
    SendByte(suba); /*�o�e����l�a�}*/
    if (ack == 0)
        return (0);

    for (i = 0; i < length; i++) {
        SendByte(*s++); /*�o�e�ƾ�*/
        if (ack == 0)return (0);
    }
    IIC_stop();
    return (1);
}

/*******************************************************************
                    �V�L�l�a�}����Ū�r�`�ƾڨ��               
��ƭ쫬: bit  IRcvByte(uchar sla,ucahr *c);  
�\��:     �q�Ұ��`�u��o�e�a�}�AŪ�ƾڡA�����`�u�����L�{,�q����a
          �}sla�A��^�Ȧbc.
           �p�G��^1��ܾާ@���\�A�_�h�ާ@���~�C
�`�N�G    �ϥΫe�����w�����`�u�C
 ********************************************************************/
bit IRcvByte(unsigned char sla, unsigned char *c) {
    IIC_start(); /*�Ұ��`�u*/
    SendByte(sla + 1); /*�o�e����a�}*/
    if (ack == 0)return (0);
    *c = RcvByte(); /*Ū���ƾ�*/
    IIC_ack(1); /*�o�e�D�N����*/
    IIC_stop(); /*�����`�u*/
    return (1);
}

/*******************************************************************
                    �V���l�a�}����Ū���h�r�`�ƾڨ��               
��ƭ쫬: bit  ISendStr(uchar sla,uchar suba,ucahr *s,uchar no);  
�\��:     �q�Ұ��`�u��o�e�a�}�A�l�a�},Ū�ƾڡA�����`�u�����L�{,�q����
          �a�}sla�A�l�a�}suba�AŪ�X�����e��Js���V���s�x�ϡAŪno�Ӧr�`�C
           �p�G��^1��ܾާ@���\�A�_�h�ާ@���~�C
�`�N�G    �ϥΫe�����w�����`�u�C
 ********************************************************************/
bit IRcvStr(unsigned char sla, unsigned char suba, unsigned char *s, unsigned char length) {
    unsigned char i;

    IIC_start(); /*�Ұ��`�u*/
    SendByte(sla); /*�o�e����a�}*/
    if (ack == 0)
        return (0);
    SendByte(suba); /*�o�e����l�a�}*/
    if (ack == 0)
        return (0);

    IIC_start();
    SendByte(sla + 1);
    if (ack == 0)
        return (0);

    for (i = 0; i < length - 1; i++) {
        *s = RcvByte(); /*�o�e�ƾ�*/
        IIC_ack(0); /*�o�e�N����*/
        s++;
    }
    *s = RcvByte();
    IIC_ack(1); /*�o�e�D����*/
    IIC_stop(); /*�����`�u*/
    return (1);
}




