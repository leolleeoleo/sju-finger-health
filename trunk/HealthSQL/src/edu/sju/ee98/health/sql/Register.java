/*
 * Copyright (c) 2012, St. John's University and/or its affiliates. All rights reserved.
 * 
 */
package edu.sju.ee98.health.sql;

import edu.sju.ee98.sql.Table;
import edu.sju.ee98.sql.lang.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 登錄站
 *
 * @author 98405067
 */
public class Register implements Table {

    public final SQLObject[] sqlObject = {
        new INT("RID", true, true, false, false, false, false, false),
        new VARCHAR(20, "ACCOUNT", false, true, false, false, false, false, false),
        new PASSWORD("PASSWORD", false, true, false, false, false, false, false),
        new VARCHAR(20, "REGION", false, true, false, false, false, false, false),
        new VARCHAR(20, "NAME", false, true, false, false, false, false, false)};

    public Register() {
    }

    public Register(int rid) {
        this.setRID(rid);
    }

    public Register(int rid, String account, String password, String region, String name) {
        this.setRID(rid);
        this.setACCOUNT(account);
        this.setPASSWORD(password);
        this.setREGION(region);
        this.setNAME(name);
    }

    /**
     * 設定登錄站編號
     *
     * @param data 登錄站編號
     */
    public void setRID(int data) {
        this.sqlObject[0].setData(data);
    }

    /**
     * 取得登錄站編號
     *
     * @return 登錄站編號
     */
    public int getRID() {
        return (Integer) this.sqlObject[0].getData();
    }

    /**
     * 取得登錄站編號物件
     *
     * @return 登錄站編號物件
     */
    public SQLObject objectRID() {
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
     * 設定地區
     *
     * @param data 地區
     */
    public void setREGION(String data) {
        this.sqlObject[3].setData(data);
    }

    /**
     * 取得地區
     *
     * @return 地區
     */
    public String getREGION() {
        return (String) this.sqlObject[3].getData();
    }

    /**
     * 取得地區物件
     *
     * @return 地區物件
     */
    public SQLObject objectREGION() {
        return this.sqlObject[3];
    }

    /**
     * 設定帳號
     *
     * @param data 帳號
     */
    public void setNAME(String data) {
        this.sqlObject[4].setData(data);
    }

    /**
     * 取得帳號
     *
     * @return 帳號
     */
    public String getNAME() {
        return (String) this.sqlObject[4].getData();
    }

    /**
     * 取得帳號物件
     *
     * @return 帳號物件
     */
    public SQLObject objectNAME() {
        return this.sqlObject[4];
    }

    @Override
    public String name() {
        return Register.class.getSimpleName();
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
