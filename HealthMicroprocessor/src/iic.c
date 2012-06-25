/********************************************************************
                       VIIC_C51.C
  此程序是I2C操作平台（主方式的軟件平台）的底層的C子程序,如發送數據
及接收數據,應答位發送,並提供了幾個直接面對器件的操作函數，它很方便的
與用戶程序連接並擴展.....  
  
    注意:函數是采用軟件延時的方法產生SCL脈沖,固對高晶振頻率要作 
一定的修改....(本例是1us機器周期,即晶振頻率要小於12MHZ)

 ********************************************************************/

#include "base.h"

/* 常,變量定義區 */

/*端口位定義*/
sbit SDA = P1^0; /*模擬I2C數據傳送位*/
sbit SCL = P1^1; /*模擬I2C時鍾控制位*/
/*狀態標志*/
bit ack; /*應答標志位*/

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
                 字節數據傳送函數               
函數原型: void  SendByte(uchar c);
功能:  將數據c發送出去,可以是地址,也可以是數據,發完後等待應答,並對
     此狀態位進行操作.(不應答或非應答都使ack=0 假)     
     發送數據正常，ack=1; ack=0表示被控器無應答或損壞。
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
                 字節數據傳送函數               
函數原型: uchar  RcvByte();
功能:  用來接收從器件傳來的數據,並判斷總線錯誤(不發應答信號)，
     發完後請用應答函數。  
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
                     應答子函數
原型:  void IIC_ack(bit a);
 
功能:主控器進行應答信號,(可以是應答或非應答信號)
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
                    向無子地址器件發送字節數據函數               
函數原型: bit  ISendByte(uchar sla,ucahr c);  
功能:     從啟動總線到發送地址，數據，結束總線的全過程,從器件地址sla.
           如果返回1表示操作成功，否則操作有誤。
注意：    使用前必須已結束總線。
 ********************************************************************/
bit ISendByte(unsigned char sla, unsigned char c) {
    IIC_start(); /*啟動總線*/
    SendByte(sla); /*發送器件地址*/
    if (!ack)
        return 0;
    SendByte(c); /*發送數據*/
    if (!ack)
        return 0;
    IIC_stop(); /*結束總線*/
    return 1;
}

/*******************************************************************
                    向有子地址器件發送多字節數據函數               
函數原型: bit  ISendStr(uchar sla,uchar suba,ucahr *s,uchar no);  
功能:     從啟動總線到發送地址，子地址,數據，結束總線的全過程,從器件
          地址sla，子地址suba，發送內容是s指向的內容，發送no個字節。
           如果返回1表示操作成功，否則操作有誤。
注意：    使用前必須已結束總線。
 ********************************************************************/
bit ISendStr(unsigned char sla, unsigned char suba, unsigned char *s, unsigned char length) {
    unsigned char i;

    IIC_start(); /*啟動總線*/
    SendByte(sla); /*發送器件地址*/
    if (ack == 0)
        return (0);
    SendByte(suba); /*發送器件子地址*/
    if (ack == 0)
        return (0);

    for (i = 0; i < length; i++) {
        SendByte(*s++); /*發送數據*/
        if (ack == 0)return (0);
    }
    IIC_stop();
    return (1);
}

/*******************************************************************
                    向無子地址器件讀字節數據函數               
函數原型: bit  IRcvByte(uchar sla,ucahr *c);  
功能:     從啟動總線到發送地址，讀數據，結束總線的全過程,從器件地
          址sla，返回值在c.
           如果返回1表示操作成功，否則操作有誤。
注意：    使用前必須已結束總線。
 ********************************************************************/
bit IRcvByte(unsigned char sla, unsigned char *c) {
    IIC_start(); /*啟動總線*/
    SendByte(sla + 1); /*發送器件地址*/
    if (ack == 0)return (0);
    *c = RcvByte(); /*讀取數據*/
    IIC_ack(1); /*發送非就答位*/
    IIC_stop(); /*結束總線*/
    return (1);
}

/*******************************************************************
                    向有子地址器件讀取多字節數據函數               
函數原型: bit  ISendStr(uchar sla,uchar suba,ucahr *s,uchar no);  
功能:     從啟動總線到發送地址，子地址,讀數據，結束總線的全過程,從器件
          地址sla，子地址suba，讀出的內容放入s指向的存儲區，讀no個字節。
           如果返回1表示操作成功，否則操作有誤。
注意：    使用前必須已結束總線。
 ********************************************************************/
bit IRcvStr(unsigned char sla, unsigned char suba, unsigned char *s, unsigned char length) {
    unsigned char i;

    IIC_start(); /*啟動總線*/
    SendByte(sla); /*發送器件地址*/
    if (ack == 0)
        return (0);
    SendByte(suba); /*發送器件子地址*/
    if (ack == 0)
        return (0);

    IIC_start();
    SendByte(sla + 1);
    if (ack == 0)
        return (0);

    for (i = 0; i < length - 1; i++) {
        *s = RcvByte(); /*發送數據*/
        IIC_ack(0); /*發送就答位*/
        s++;
    }
    *s = RcvByte();
    IIC_ack(1); /*發送非應位*/
    IIC_stop(); /*結束總線*/
    return (1);
}




