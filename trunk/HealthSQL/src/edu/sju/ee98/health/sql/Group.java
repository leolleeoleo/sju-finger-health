/*
 * Copyright (c) 2012, St. John's University and/or its affiliates. All rights reserved.
 */
package edu.sju.ee98.health.sql;

import edu.sju.ee98.sql.Table;
import edu.sju.ee98.sql.lang.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 群組
 *
 * @author 98405067
 */
public class Group implements Table {

    public final SQLObject[] sqlObject = {
        new INT("GID", true, true, false, false, false, false, false),
        new VARCHAR(20, "NAME", false, true, false, false, false, false, false),};

    public Group() {
    }

    public Group(int gid, String name) {
        this.setGID(gid);
        this.setNAME(name);
    }

    /**
     * 設定群組編號
     *
     * @param data 群組編號
     */
    public void setGID(int data) {
        this.sqlObject[0].setData(data);
    }

    /**
     * 取得群組編號
     *
     * @return 群組編號
     */
    public int getGID() {
        return (Integer) this.sqlObject[0].getData();
    }

    /**
     * 取得群組編號物件
     *
     * @return 群組編號物件
     */
    public SQLObject objectGID() {
        return this.sqlObject[0];
    }

    /**
     * 設定群組名稱
     *
     * @param data 群組名稱
     */
    public void setNAME(String data) {
        this.sqlObject[1].setData(data);
    }

    /**
     * 取得群組名稱
     *
     * @return 群組名稱
     */
    public String getNAME() {
        return (String) this.sqlObject[1].getData();
    }

    /**
     * 取得群組名稱物件
     *
     * @return 群組名稱物件
     */
    public SQLObject objectNAME() {
        return this.sqlObject[1];
    }

    @Override
    public String name() {
        return Group.class.getSimpleName();
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
                this.sqlObject[i].setData(rs.getObject(this.sqlObject[i].getField()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Group.class.getName()).log(Level.SEVERE, null, ex);
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
