/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.sql;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leo
 */
public class TestSQL {

    private static SQL sql = null;
    public static String a = "AAAAAAAAAABBBBBBBBBBAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    public static String b = "AAAAAAAAAABBBBBBBBBBCCCCCCCCCCAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    public static String c = "AAAAAAAAAABBBBBBBBBBCCCCCCCCCCDDDDDDDDDDAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

    public static void main(String[] args) {
        sql = new SQL("163.21.76.50", "health_test", "finger", "health");
        sql.dropTables();
        sql.createTables();
        insertExample();
        //insert code here
//        sql.dropTables();
    }

    public static void insertExample() {
        try {
            User r = sql.createUser("A123456789", "root1234", "12345678", 1, "張", "三", new java.text.SimpleDateFormat("yyyy-MM-dd").parse("1985-01-01"), "台北市內湖區成功路四段43號1樓", "ssdggwe@yahoo.com.tw", "0935123435");
            sql.createUser("B123456789", "store1234", "98765432", 2, "李", "四", new java.text.SimpleDateFormat("yyyy-MM-dd").parse("1982-05-13"), "台北市內湖區康樂街345巷23號", "dsgs12343@livemail.tw", "0953343775");
            sql.createUser("A232345478", "user1234", "09876543", 3, "王", "五", new java.text.SimpleDateFormat("yyyy-MM-dd").parse("1975-04-20"), "新北市汐止區新台五路一段45號6樓", "fhfh45467@yahoo.com.tw", "0922123435");
            sql.createUser("A127345553", "user2345", "12233445", 3, "李", "六", new java.text.SimpleDateFormat("yyyy-MM-dd").parse("1988-07-07"), "新北市淡水區淡金路五段555巷5號5樓", "ggg555@livemail.tw", "0933445566");
            sql.createUser("A111111111", "user0000", "23456789", 3, "林", "七", new java.text.SimpleDateFormat("yyyy-MM-dd").parse("1985-04-05"), "新北市汐止區新台五路一段45號5樓", "seven777@yahoo.com.tw", "0935775776");

//            try {
//                Manager.module.setTimeout(0);
//                Manager.module.getSerial().openPort();
//                Manager.module.getSerial().setParams(SerialPort.BAUDRATE_19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//                try {
//                    Manager.module.addUser((char) 1, (byte) 0);
//                    Manager.sql.createFingerprint("A232345478", Manager.module.getCharacterize((char) 1).getCharacterize());
//                    Manager.module.addUser((char) 2, (byte) 0);
//                    Manager.sql.createFingerprint("A232345478", Manager.module.getCharacterize((char) 2).getCharacterize());
//                    Manager.module.addUser((char) 3, (byte) 0);
//                    Manager.sql.createFingerprint("A232345478", Manager.module.getCharacterize((char) 3).getCharacterize());
//                } catch (ACKException ex) {
//                    Logger.getLogger(TestSQL.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IOException ex) {
//                    Logger.getLogger(TestSQL.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                Manager.module.getSerial().closePort();
//            } catch (SerialPortException ex) {
//                Logger.getLogger(TestSQL.class.getName()).log(Level.SEVERE, null, ex);
//            }

//            Manager.sql.createFingerprint("A232345478", a.getBytes());
//            Manager.sql.createFingerprint("A127345553", b.getBytes());
//            Manager.sql.createFingerprint("A111111111", c.getBytes());



            //Group
            sql.createGroup(1, "root");
            sql.createGroup(2, "store");
            sql.createGroup(3, "user");
            //Register
            sql.createRegister(123, "REGISTER0123", "000000000000", "台北市內湖區", "大鷲山1號登陸站");
            sql.createRegister(156, "REGISTER0156", "111111111111", "台北市內湖區", "大鷲山2號登陸站");
            sql.createRegister(178, "REGISTER0178", "222222222222", "台北市內湖區", "大鷲山3號登陸站");
//            //Miles
            sql.createMiles(123, 156, 15);
            sql.createMiles(156, 178, 20);
            sql.createMiles(123, 178, 35);
            //Record
            sql.createRecord(new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2012-04-22 13:11:45"), "A232345478", 178);
            sql.createRecord(new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2012-04-22 18:22:55"), "A232345478", 156);
            sql.createRecord(new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2012-04-25 12:00:00"), "A127345553", 123);
            sql.createRecord(new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2012-04-25 12:04:00"), "A127345553", 156);
            sql.createRecord(new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2012-04-25 12:06:00"), "A111111111", 156);
            sql.createRecord(new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2012-04-25 12:10:00"), "A111111111", 178);
            sql.createRecord(new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2012-04-25 12:05:01"), "A127345553", 156);
            sql.createRecord(new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2012-04-25 12:00:09"), "A127345553", 178);
            sql.createRecord(new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2012-04-25 12:07:00"), "A111111111", 156);
            sql.createRecord(new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2012-04-25 12:17:00"), "A111111111", 178);
            sql.createRecord(new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2012-04-25 12:01:00"), "A127345553", 178);

            //Cost
            sql.createCost(r, new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2012-04-22 12:12:12"), "B123456789", "A232345478", 10);
            sql.createCost(r, new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2012-04-26 14:20:12"), "B123456789", "A232345478", 10);
            sql.createCost(r, new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2012-04-26 15:22:12"), "B123456789", "A111111111", 10);
            sql.createCost(r, new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2012-04-26 16:10:12"), "B123456789", "A127345553", 60);



        } catch (ParseException ex) {
            Logger.getLogger(TestSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
