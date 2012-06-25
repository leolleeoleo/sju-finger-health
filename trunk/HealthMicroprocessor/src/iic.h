#ifndef	_IIC_H
#define	_IIC_H


//void Start_I2c();
//void Stop_I2c();	 
//void  SendByte(unsigned char c);
//unsigned char RcvByte();
//void Ack_I2c(bit a);
bit ISendByte(unsigned char,unsigned char);
bit ISendStr(unsigned char,unsigned char,unsigned char *,unsigned char);
bit IRcvByte(unsigned char,unsigned char *);
bit IRcvStr(unsigned char,unsigned char,unsigned char *,unsigned char);

#endif