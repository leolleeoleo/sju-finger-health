/*
 * Copyright (c) 2012, St. John's University and/or its affiliates. All rights reserved.
 */
package edu.sju.ee98.health.sql;

import edu.sju.ee98.sql.Table;
import edu.sju.ee98.sql.lang.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 使用者
 *
 * @author 98405067
 */
public class User implements Table {

    public final SQLObject[] sqlObject = {
        new CHAR(10, "UID", true, true, false, false, false, false, false),
        new VARCHAR(20, "ACCOUNT", false, true, false, false, false, false, false),
        new PASSWORD("PASSWORD", false, true, false, false, false, false, false),
        new INT("GROUP", false, true, false, false, false, false, false),
        new VARCHAR(20, "LAST_NAME", false, true, false, false, false, false, false),
        new VARCHAR(20, "FIRST_NAME", false, true, false, false, false, false, false),
        new DATE("BIRTHDAY", false, true, false, false, false, false, false),
        new VARCHAR(50, "ADDRESS", false, true, false, false, false, false, false),
        new VARCHAR(50, "EMAIL", false, true, false, false, false, false, false),
        new VARCHAR(50, "PHONE", false, true, false, false, false, false, false),
        new INT("POINTS", false, true, false, false, false, false, false)
    };

    /**
     * 使用者
     *
     */
    public User() {
    }

    /**
     * 使用者
     *
     * @param uid 使用者編號
     * @param account 帳號
     * @param password 密碼
     * @param group 群組
     * @param last_name 姓氏
     * @param first_name 名子
     * @param birthday 生日
     * @param address 地址
     * @param email 郵件
     * @param phone 電話
     * @param points 點數(未使用)
     */
    public User(String uid, String account, String password, int group, String last_name,
            String first_name, Date birthday, String address, String email, String phone, int points) {
        this.setUID(uid);
        this.setACCOUNT(account);
        this.setPASSWORD(password);
        this.setGROUP(group);
        this.setLAST_NAME(last_name);
        this.setFIRST_NAME(first_name);
        this.setBIRTHDAY(birthday);
        this.setADDRESS(address);
        this.setEmail(email);
        this.setPHONE(phone);
        this.setPOINTS(points);
    }

    /**
     * 設定使用者身分證號
     *
     * @param data 身分證號
     */
    public void setUID(String data) {
        this.sqlObject[0].setData(data);
    }

    /**
     * 取得使用者身分證號
     *
     * @return 身分證號
     */
    public String getUID() {
        return (String) this.sqlObject[0].getData();
    }

    /**
     * 取得身分證物件
     *
     * @return 身分證物件
     */
    public SQLObject objectUID() {
        return this.sqlObject[0];
    }

    /**
     * 設定帳號
     *
     * @param data 帳號
     */
    public void setACCOUNT(String data) {
        this.sqlObject[1].setData(data);
    }

    /**
     * 取得帳號
     *
     * @return 帳號
     */
    public String getACCOUNT() {
        return (String) this.sqlObject[1].getData();
    }

    /**
     * 取得帳號物件
     *
     * @return 帳號物件
     */
    public SQLObject objectACCOUNT() {
        return this.sqlObject[1];
    }

    /**
     * 設定密碼
     *
     * @param data 密碼
     */
    public void setPASSWORD(String data) {
        this.sqlObject[2].setData(data);
    }

    /**
     * 取得密碼
     *
     * @return 密碼
     */
    public String getPASSWORD() {
        return (String) this.sqlObject[2].getData();
    }

    /**
     * 取得密碼物件
     *
     * @return 密碼物件
     */
    public SQLObject objectPASSWORD() {
        return this.sqlObject[2];
    }

    /**
     * 設定群組
     *
     * @param data 群組
     */
    public void setGROUP(int data) {
        this.sqlObject[3].setData(data);
    }

    /**
     * 取得群組
     *
     * @return 群組
     */
    public int getGROUP() {
        return (Integer) this.sqlObject[3].getData();
    }

    /**
     * 取得群組物件
     *
     * @return 群組物件
     */
    public SQLObject objectGROUP() {
        return this.sqlObject[3];
    }

    /**
     * 設定姓
     *
     * @param data 姓
     */
    public void setLAST_NAME(String data) {
        this.sqlObject[4].setData(data);
    }

    /**
     * 取得姓氏
     *
     * @return 姓氏
     */
    public String getLAST_NAME() {
        return (String) this.sqlObject[4].getData();
    }

    /**
     * 取得姓氏物件
     *
     * @return 姓氏物件
     */
    public SQLObject objectLAST_NAME() {
        return this.sqlObject[4];
    }

    /**
     * 設定名字
     *
     * @param data 名字
     */
    public void setFIRST_NAME(String data) {
        this.sqlObject[5].setData(data);
    }

    /**
     * 取得名字
     *
     * @return 名字
     */
    public String getFIRST_NAME() {
        return (String) this.sqlObject[5].getData();
    }

    /**
     * 取得名字物件
     *
     * @return 名字物件
     */
    public SQLObject objectFIRST_NAME() {
        return this.sqlObject[5];
    }

    public String getNAME() {
        return this.getLAST_NAME() + this.getFIRST_NAME();
    }

    /**
     * 設定生日
     *
     * @param data 生日
     */
    public void setBIRTHDAY(java.util.Date data) {
        this.sqlObject[6].setData(data);
    }

    /**
     * 取得生日
     *
     * @return 生日
     */
    public java.util.Date getBIRTHDAY() {
        return (java.util.Date) this.sqlObject[6].getData();
    }

    /**
     * 取得生日物件
     *
     * @return 生日物件
     */
    public SQLObject objectBIRTHDAY() {
        return this.sqlObject[6];
    }

    /**
     * 設定地址
     *
     * @param data 地址
     */
    public void setADDRESS(String data) {
        this.sqlObject[7].setData(data);
    }

    /**
     * 取得地址
     *
     * @return 地址
     */
    public String getADDRESS() {
        return (String) this.sqlObject[7].getData();
    }

    /**
     * 取得地址物件
     *
     * @return 地址物件
     */
    public SQLObject objectADDRESS() {
        return this.sqlObject[7];
    }

    /**
     * 設定信箱
     *
     * @param data 信箱
     */
    public void setEmail(String data) {
        this.sqlObject[8].setData(data);
    }

    /**
     * 取得信箱
     *
     * @return 信箱
     */
    public String getEmail() {
        return (String) this.sqlObject[8].getData();
    }

    /**
     * 取得信箱物件
     *
     * @return 信箱物件
     */
    public SQLObject objectEmail() {
        return this.sqlObject[8];
    }

    /**
     * 設定電話
     *
     * @param data 電話
     */
    public void setPHONE(String data) {
        this.sqlObject[9].setData(data);
    }

    /**
     * 取得電話
     *
     * @return 電話
     */
    public String getPHONE() {
        return (String) this.sqlObject[9].getData();
    }

    /**
     * 取得電話物件
     *
     * @return 電話
     */
    public SQLObject objectPHONE() {
        return this.sqlObject[9];
    }

    /**
     * 設定點數
     *
     * @param data 點數
     */
    public void setPOINTS(int data) {
        this.sqlObject[10].setData(data);
    }

    /**
     * 取得點數
     *
     * @return 點數
     */
    public int getPOINTS() {
        return (Integer) this.sqlObject[10].getData();
    }

    /**
     * 取得點數物件
     *
     * @return 點數
     */
    public SQLObject objectPOINTS() {
        return this.sqlObject[10];
    }

    @Override
    public String name() {
        return User.class.getSimpleName();
    }

    @Override
    public SQLObject[] getSQLObject() {
        return this.sqlObject;
    }

    @Override
    public String primary_key() {
        return this.sqlObject[0].getField() + " = " + this.sqlObject[0].toString();
    }

    @Override
    public String values() {
        String sqlComm = "";
        int i = 0;
        while (true) {
            sqlComm += this.sqlObject[i].toString();
            i++;
            if (!(i < this.sqlObject.length)) {
                break;
            }
            sqlComm += " , ";
        }
        return sqlComm;
    }

    @Override
    public void sqlSetter(ResultSet rs) {
        try {
            for (int i = 0; i < this.sqlObject.length; i++) {
//                    System.out.print(i);
                this.sqlObject[i].setData(rs.getObject(this.sqlObject[i].getField()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void print() {
        System.out.print(this.name() + " - ");
        for (int i = 0; i < this.sqlObject.length; i++) {
            System.out.print(this.sqlObject[i].getData() + ", ");
        }
        System.out.println();
    }
}
