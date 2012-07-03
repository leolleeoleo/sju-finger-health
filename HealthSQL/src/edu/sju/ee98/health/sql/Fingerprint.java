/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.sql;

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
public class Fingerprint implements Table {

    public final SQLObject[] sqlObject = {
        new CHAR(10, "UID", true, true, false, false, false, false, false),
        new TINYINT("FP1", false, true, false, false, false, false, false),
        new TINYINT("FP2", false, true, false, false, false, false, false),
        new TINYINT("FP3", false, true, false, false, false, false, false),
        new TINYINT("FP4", false, true, false, false, false, false, false),
        new TINYINT("FP5", false, true, false, false, false, false, false),
        new TINYINT("FP6", false, true, false, false, false, false, false),
        new TINYINT("FP7", false, true, false, false, false, false, false),
        new TINYINT("FP8", false, true, false, false, false, false, false),
        new TINYINT("FP9", false, true, false, false, false, false, false),
        new TINYINT("FP10", false, true, false, false, false, false, false),
        new TINYINT("FP11", false, true, false, false, false, false, false),
        new TINYINT("FP12", false, true, false, false, false, false, false),
        new TINYINT("FP13", false, true, false, false, false, false, false),
        new TINYINT("FP14", false, true, false, false, false, false, false),
        new TINYINT("FP15", false, true, false, false, false, false, false),
        new TINYINT("FP16", false, true, false, false, false, false, false),
        new TINYINT("FP17", false, true, false, false, false, false, false),
        new TINYINT("FP18", false, true, false, false, false, false, false),
        new TINYINT("FP19", false, true, false, false, false, false, false),
        new TINYINT("FP20", false, true, false, false, false, false, false),
        new TINYINT("FP21", false, true, false, false, false, false, false),
        new TINYINT("FP22", false, true, false, false, false, false, false),
        new TINYINT("FP23", false, true, false, false, false, false, false),
        new TINYINT("FP24", false, true, false, false, false, false, false),
        new TINYINT("FP25", false, true, false, false, false, false, false),
        new TINYINT("FP26", false, true, false, false, false, false, false),
        new TINYINT("FP27", false, true, false, false, false, false, false),
        new TINYINT("FP28", false, true, false, false, false, false, false),
        new TINYINT("FP29", false, true, false, false, false, false, false),
        new TINYINT("FP30", false, true, false, false, false, false, false),
        new TINYINT("FP31", false, true, false, false, false, false, false),
        new TINYINT("FP32", false, true, false, false, false, false, false),
        new TINYINT("FP33", false, true, false, false, false, false, false),
        new TINYINT("FP34", false, true, false, false, false, false, false),
        new TINYINT("FP35", false, true, false, false, false, false, false),
        new TINYINT("FP36", false, true, false, false, false, false, false),
        new TINYINT("FP37", false, true, false, false, false, false, false),
        new TINYINT("FP38", false, true, false, false, false, false, false),
        new TINYINT("FP39", false, true, false, false, false, false, false),
        new TINYINT("FP40", false, true, false, false, false, false, false),
        new TINYINT("FP41", false, true, false, false, false, false, false),
        new TINYINT("FP42", false, true, false, false, false, false, false),
        new TINYINT("FP43", false, true, false, false, false, false, false),
        new TINYINT("FP44", false, true, false, false, false, false, false),
        new TINYINT("FP45", false, true, false, false, false, false, false),
        new TINYINT("FP46", false, true, false, false, false, false, false),
        new TINYINT("FP47", false, true, false, false, false, false, false),
        new TINYINT("FP48", false, true, false, false, false, false, false),
        new TINYINT("FP49", false, true, false, false, false, false, false),
        new TINYINT("FP50", false, true, false, false, false, false, false),
        new TINYINT("FP51", false, true, false, false, false, false, false),
        new TINYINT("FP52", false, true, false, false, false, false, false),
        new TINYINT("FP53", false, true, false, false, false, false, false),
        new TINYINT("FP54", false, true, false, false, false, false, false),
        new TINYINT("FP55", false, true, false, false, false, false, false),
        new TINYINT("FP56", false, true, false, false, false, false, false),
        new TINYINT("FP57", false, true, false, false, false, false, false),
        new TINYINT("FP58", false, true, false, false, false, false, false),
        new TINYINT("FP59", false, true, false, false, false, false, false),
        new TINYINT("FP60", false, true, false, false, false, false, false),
        new TINYINT("FP61", false, true, false, false, false, false, false),
        new TINYINT("FP62", false, true, false, false, false, false, false),
        new TINYINT("FP63", false, true, false, false, false, false, false),
        new TINYINT("FP64", false, true, false, false, false, false, false),
        new TINYINT("FP65", false, true, false, false, false, false, false),
        new TINYINT("FP66", false, true, false, false, false, false, false),
        new TINYINT("FP67", false, true, false, false, false, false, false),
        new TINYINT("FP68", false, true, false, false, false, false, false),
        new TINYINT("FP69", false, true, false, false, false, false, false),
        new TINYINT("FP70", false, true, false, false, false, false, false),
        new TINYINT("FP71", false, true, false, false, false, false, false),
        new TINYINT("FP72", false, true, false, false, false, false, false),
        new TINYINT("FP73", false, true, false, false, false, false, false),
        new TINYINT("FP74", false, true, false, false, false, false, false),
        new TINYINT("FP75", false, true, false, false, false, false, false),
        new TINYINT("FP76", false, true, false, false, false, false, false),
        new TINYINT("FP77", false, true, false, false, false, false, false),
        new TINYINT("FP78", false, true, false, false, false, false, false),
        new TINYINT("FP79", false, true, false, false, false, false, false),
        new TINYINT("FP80", false, true, false, false, false, false, false),
        new TINYINT("FP81", false, true, false, false, false, false, false),
        new TINYINT("FP82", false, true, false, false, false, false, false),
        new TINYINT("FP83", false, true, false, false, false, false, false),
        new TINYINT("FP84", false, true, false, false, false, false, false),
        new TINYINT("FP85", false, true, false, false, false, false, false),
        new TINYINT("FP86", false, true, false, false, false, false, false),
        new TINYINT("FP87", false, true, false, false, false, false, false),
        new TINYINT("FP88", false, true, false, false, false, false, false),
        new TINYINT("FP89", false, true, false, false, false, false, false),
        new TINYINT("FP90", false, true, false, false, false, false, false),
        new TINYINT("FP91", false, true, false, false, false, false, false),
        new TINYINT("FP92", false, true, false, false, false, false, false),
        new TINYINT("FP93", false, true, false, false, false, false, false),
        new TINYINT("FP94", false, true, false, false, false, false, false),
        new TINYINT("FP95", false, true, false, false, false, false, false),
        new TINYINT("FP96", false, true, false, false, false, false, false),
        new TINYINT("FP97", false, true, false, false, false, false, false),
        new TINYINT("FP98", false, true, false, false, false, false, false),
        new TINYINT("FP99", false, true, false, false, false, false, false),
        new TINYINT("FP100", false, true, false, false, false, false, false),
        new TINYINT("FP101", false, true, false, false, false, false, false),
        new TINYINT("FP102", false, true, false, false, false, false, false),
        new TINYINT("FP103", false, true, false, false, false, false, false),
        new TINYINT("FP104", false, true, false, false, false, false, false),
        new TINYINT("FP105", false, true, false, false, false, false, false),
        new TINYINT("FP106", false, true, false, false, false, false, false),
        new TINYINT("FP107", false, true, false, false, false, false, false),
        new TINYINT("FP108", false, true, false, false, false, false, false),
        new TINYINT("FP109", false, true, false, false, false, false, false),
        new TINYINT("FP110", false, true, false, false, false, false, false),
        new TINYINT("FP111", false, true, false, false, false, false, false),
        new TINYINT("FP112", false, true, false, false, false, false, false),
        new TINYINT("FP113", false, true, false, false, false, false, false),
        new TINYINT("FP114", false, true, false, false, false, false, false),
        new TINYINT("FP115", false, true, false, false, false, false, false),
        new TINYINT("FP116", false, true, false, false, false, false, false),
        new TINYINT("FP117", false, true, false, false, false, false, false),
        new TINYINT("FP118", false, true, false, false, false, false, false),
        new TINYINT("FP119", false, true, false, false, false, false, false),
        new TINYINT("FP120", false, true, false, false, false, false, false),
        new TINYINT("FP121", false, true, false, false, false, false, false),
        new TINYINT("FP122", false, true, false, false, false, false, false),
        new TINYINT("FP123", false, true, false, false, false, false, false),
        new TINYINT("FP124", false, true, false, false, false, false, false),
        new TINYINT("FP125", false, true, false, false, false, false, false),
        new TINYINT("FP126", false, true, false, false, false, false, false),
        new TINYINT("FP127", false, true, false, false, false, false, false),
        new TINYINT("FP128", false, true, false, false, false, false, false),
        new TINYINT("FP129", false, true, false, false, false, false, false),
        new TINYINT("FP130", false, true, false, false, false, false, false),
        new TINYINT("FP131", false, true, false, false, false, false, false),
        new TINYINT("FP132", false, true, false, false, false, false, false),
        new TINYINT("FP133", false, true, false, false, false, false, false),
        new TINYINT("FP134", false, true, false, false, false, false, false),
        new TINYINT("FP135", false, true, false, false, false, false, false),
        new TINYINT("FP136", false, true, false, false, false, false, false),
        new TINYINT("FP137", false, true, false, false, false, false, false),
        new TINYINT("FP138", false, true, false, false, false, false, false),
        new TINYINT("FP139", false, true, false, false, false, false, false),
        new TINYINT("FP140", false, true, false, false, false, false, false),
        new TINYINT("FP141", false, true, false, false, false, false, false),
        new TINYINT("FP142", false, true, false, false, false, false, false),
        new TINYINT("FP143", false, true, false, false, false, false, false),
        new TINYINT("FP144", false, true, false, false, false, false, false),
        new TINYINT("FP145", false, true, false, false, false, false, false),
        new TINYINT("FP146", false, true, false, false, false, false, false),
        new TINYINT("FP147", false, true, false, false, false, false, false),
        new TINYINT("FP148", false, true, false, false, false, false, false),
        new TINYINT("FP149", false, true, false, false, false, false, false),
        new TINYINT("FP150", false, true, false, false, false, false, false),
        new TINYINT("FP151", false, true, false, false, false, false, false),
        new TINYINT("FP152", false, true, false, false, false, false, false),
        new TINYINT("FP153", false, true, false, false, false, false, false),
        new TINYINT("FP154", false, true, false, false, false, false, false),
        new TINYINT("FP155", false, true, false, false, false, false, false),
        new TINYINT("FP156", false, true, false, false, false, false, false),
        new TINYINT("FP157", false, true, false, false, false, false, false),
        new TINYINT("FP158", false, true, false, false, false, false, false),
        new TINYINT("FP159", false, true, false, false, false, false, false),
        new TINYINT("FP160", false, true, false, false, false, false, false),
        new TINYINT("FP161", false, true, false, false, false, false, false),
        new TINYINT("FP162", false, true, false, false, false, false, false),
        new TINYINT("FP163", false, true, false, false, false, false, false),
        new TINYINT("FP164", false, true, false, false, false, false, false),
        new TINYINT("FP165", false, true, false, false, false, false, false),
        new TINYINT("FP166", false, true, false, false, false, false, false),
        new TINYINT("FP167", false, true, false, false, false, false, false),
        new TINYINT("FP168", false, true, false, false, false, false, false),
        new TINYINT("FP169", false, true, false, false, false, false, false),
        new TINYINT("FP170", false, true, false, false, false, false, false),
        new TINYINT("FP171", false, true, false, false, false, false, false),
        new TINYINT("FP172", false, true, false, false, false, false, false),
        new TINYINT("FP173", false, true, false, false, false, false, false),
        new TINYINT("FP174", false, true, false, false, false, false, false),
        new TINYINT("FP175", false, true, false, false, false, false, false),
        new TINYINT("FP176", false, true, false, false, false, false, false),
        new TINYINT("FP177", false, true, false, false, false, false, false),
        new TINYINT("FP178", false, true, false, false, false, false, false),
        new TINYINT("FP179", false, true, false, false, false, false, false),
        new TINYINT("FP180", false, true, false, false, false, false, false),
        new TINYINT("FP181", false, true, false, false, false, false, false),
        new TINYINT("FP182", false, true, false, false, false, false, false),
        new TINYINT("FP183", false, true, false, false, false, false, false),
        new TINYINT("FP184", false, true, false, false, false, false, false),
        new TINYINT("FP185", false, true, false, false, false, false, false),
        new TINYINT("FP186", false, true, false, false, false, false, false),
        new TINYINT("FP187", false, true, false, false, false, false, false),
        new TINYINT("FP188", false, true, false, false, false, false, false),
        new TINYINT("FP189", false, true, false, false, false, false, false),
        new TINYINT("FP190", false, true, false, false, false, false, false),
        new TINYINT("FP191", false, true, false, false, false, false, false),
        new TINYINT("FP192", false, true, false, false, false, false, false),
        new TINYINT("FP193", false, true, false, false, false, false, false),
        new TINYINT("FP194", false, true, false, false, false, false, false),
        new TINYINT("FP195", false, true, false, false, false, false, false),
        new TINYINT("FP196", false, true, false, false, false, false, false)
    };

    public Fingerprint() {
    }

    public Fingerprint(String uid) {
        this.setUID(uid);
    }

    Fingerprint(String uid, byte[] fp) {
        this.setUID(uid);
        this.setFINGERPRINT(fp);
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
    public int getUID() {
        return (Integer) this.sqlObject[0].getData();
    }

    /**
     * 取得身分證物件
     *
     * @return 身分證物件
     */
    public SQLObject objectUID() {
        return this.sqlObject[0];
    }

    public void setFINGERPRINT(byte[] data) {
        if (data.length == 196) {
            for (int i = 0; i < 196; i++) {
                this.sqlObject[i + 1].setData(data[i]);
            }
        }
    }

    public byte[] getFINGERPRINT() {
        byte[] data = new byte[196];
        for (int i = 0; i < 196; i++) {
            data[i] = (Byte) this.sqlObject[i + 1].getData();
        }
        return data;
    }

    @Override
    public String name() {
        return Fingerprint.class.getSimpleName();
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
