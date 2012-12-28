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
 * 里程
 *
 * @author 98405084
 */
public class Miles implements Table {

    public final SQLObject[] sqlObject = {
        new INT("REGISTER_A", false, true, false, false, false, false, false),
        new INT("REGISTER_B", false, true, false, false, false, false, false),
        new INT("METER", false, true, false, false, false, false, false),};

    public Miles() {
    }

    public Miles(int register_a, int register_b, int meter) {
        this.setREGISTER_A(register_a);
        this.setREGISTER_B(register_b);
        this.setMETER(meter);
    }

    public Miles(int register_a, int register_b) {
        this.setREGISTER_A(register_a);
        this.setREGISTER_B(register_b);
    }

    /**
     * 設定登錄站A
     *
     * @param data 登錄站A
     */
    public void setREGISTER_A(int data) {
        this.sqlObject[0].setData(data);
    }

    /**
     * 取得登錄站A
     *
     * @return 登錄站A
     */
    public int getREGISTER_A() {
        return (Integer) this.sqlObject[0].getData();
    }

    /**
     * 取得登錄站物件A
     *
     * @return 登錄站物件A
     */
    public SQLObject objectREGISTER_A() {
        return this.sqlObject[0];
    }

    /**
     * 設定登錄站B
     *
     * @param data 登錄站B
     */
    public void setREGISTER_B(int data) {
        this.sqlObject[1].setData(data);
    }

    /**
     * 取得登錄站B
     *
     * @return 登錄站B
     */
    public int getREGISTER_B() {
        return (Integer) this.sqlObject[1].getData();
    }

    /**
     * 取得登錄站B物件
     *
     * @return 登錄站B物件
     */
    public SQLObject objectREGISTER_B() {
        return this.sqlObject[1];
    }

    /**
     * 設定登錄站A
     *
     * @param data 登錄站A
     */
    public void setMETER(int data) {
        this.sqlObject[2].setData(data);
    }

    /**
     * 取得登錄站A
     *
     * @return 登錄站A
     */
    public int getMETER() {
        return (Integer) this.sqlObject[2].getData();
    }

    /**
     * 取得登錄站物件A
     *
     * @return 登錄站物件A
     */
    public SQLObject objectMETER() {
        return this.sqlObject[2];
    }

    @Override
    public String name() {
        return Miles.class.getSimpleName();
    }

    @Override
    public SQLObject[] getSQLObject() {
        return this.sqlObject;
    }

    @Override
    public String primary_key() {
        return null;
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
