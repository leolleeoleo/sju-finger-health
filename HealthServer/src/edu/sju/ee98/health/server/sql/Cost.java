/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.server.sql;

import edu.sju.ee98.sql.Table;
import edu.sju.ee98.sql.lang.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 張麟翔
 */
public class Cost implements Table {

    public final SQLObject[] sqlObject = {
        new DATETIME("TIME", true, true, false, false, false, false, false),
        new CHAR(10, "STORE", false, true, false, false, false, false, false),
        new CHAR(10, "USER", false, true, false, false, false, false, false),
        new INT("POINTS", false, true, false, false, false, false, false),};

    public Cost() {
    }

    public Cost(java.util.Date time, String store, String user, int points) {
        this.setTIME(time);
        this.setSTORE(store);
        this.setUSER(user);
        this.setPOINTS(points);
    }

    /**
     * 設定時間
     *
     * @param data 時間
     */
    public void setTIME(java.util.Date data) {
        this.sqlObject[0].setData(data);
    }

    /**
     * 取得時間
     *
     * @return 時間
     */
    public java.util.Date getTIME() {
        return (java.util.Date) this.sqlObject[0].getData();
    }

    /**
     * 取得時間
     *
     * @return 時間
     */
    public SQLObject objectTIME() {
        return this.sqlObject[0];
    }

    /**
     * 設定商店
     *
     * @param data 商店
     */
    public void setSTORE(String data) {
        this.sqlObject[1].setData(data);
    }

    /**
     * 取得商店
     *
     * @return 商店
     */
    public String getSTORE() {
        return (String) this.sqlObject[1].getData();
    }

    /**
     * 取得商店物件
     *
     * @return 商店者物件
     */
    public SQLObject objectSTORE() {
        return this.sqlObject[1];
    }

    /**
     * 設定使用者
     *
     * @param data 使用者
     */
    public void setUSER(String data) {
        this.sqlObject[2].setData(data);
    }

    /**
     * 取得使用者
     *
     * @return 使用者
     */
    public String getUSER() {
        return (String) this.sqlObject[2].getData();
    }

    /**
     * 取得使用者物件
     *
     * @return 使用者物件
     */
    public SQLObject objectUSER() {
        return this.sqlObject[2];
    }

    /**
     * 設定點數
     *
     * @param data 點數
     */
    public void setPOINTS(int data) {
        this.sqlObject[3].setData(data);
    }

    /**
     * 取得點數
     *
     * @return 點數
     */
    public int getPOINTS() {
        return (Integer) this.sqlObject[3].getData();
    }

    /**
     * 取得點數物件
     *
     * @return 點數
     */
    public SQLObject objectPOINTS() {
        return this.sqlObject[3];
    }

    @Override
    public String name() {
        return Cost.class.getSimpleName();
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
