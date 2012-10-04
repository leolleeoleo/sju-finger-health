/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.web.beans;

import edu.sju.ee98.health.sql.Miles;
import edu.sju.ee98.health.web.Manager;
import edu.sju.ee98.sql.Table;
import java.util.ArrayList;

/**
 *
 * @author Leo
 */
public class MileBean {

    private int ridA;
    private int ridB;
    private int meter;

    public int getRidA() {
        return ridA;
    }

    public int getRidB() {
        return ridB;
    }

    public int getMeter() {
        return meter;
    }

    public void setRidA(int ridA) {
        this.ridA = ridA;
    }

    public void setRidB(int ridB) {
        this.ridB = ridB;
    }

    public void setMeter(int meter) {
        this.meter = meter;
    }

    public void setMileByid(int ridA, int ridB) {
        Miles mile = Manager.SQL().getMiles(ridA, ridB);
        if (mile != null) {
            this.setMile(mile);
        }
    }

    public void setMile(Miles miles) {
        this.ridA = miles == null ? 0 : miles.getREGISTER_A();
        this.ridB = miles == null ? 0 : miles.getREGISTER_B();
        this.meter = miles == null ? 0 : miles.getMETER();
    }

    public String getModify() {
        if (this.ridA == 0 || this.ridB == 0 || this.meter == 0) {
            return "";
        } else if (Manager.SQL().getMiles(ridA, ridB) != null) {
            Miles updateMiles = Manager.SQL().updateMiles(new Miles(this.ridA, this.ridB, this.meter));
            if (updateMiles != null) {
                this.setMile(null);
                return "更新成功";
            } else {
                return "更新失敗";
            }
        } else {
            ArrayList<Table> createRegister = Manager.SQL().createMiles(this.ridA, this.ridB, this.meter);
            if (createRegister.size() > 0) {
                this.setMile(null);
                return "建立成功";
            } else {
                return "建立失敗";
            }
        }
    }
}
