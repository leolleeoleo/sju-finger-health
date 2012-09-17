/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.web.beans;

import edu.sju.ee98.health.sql.Register;
import edu.sju.ee98.health.web.Manager;
import edu.sju.ee98.sql.Table;
import java.util.ArrayList;

/**
 *
 * @author Leo
 */
public class RegisterBean {

    private int rid;
    private String account;
    private String password;
    private String region;
    private String name;

    public int getRid() {
        return rid;
    }

    public String getAccount() {
        return account == null ? "" : account;
    }

    public String getPassword() {
        return password == null ? "" : password;
    }

    public String getRegion() {
        return region == null ? "" : region;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegisterByid(int rid) {
        Register reg = Manager.SQL().getRegister(rid);
        if (reg != null) {
            this.setRegister(reg);
        }
    }

    public void setRegister(Register reg) {
        this.rid = reg == null ? 0 : reg.getRID();
        this.account = reg == null ? null : reg.getACCOUNT();
        this.password = reg == null ? null : reg.getPASSWORD();
        this.region = reg == null ? null : reg.getREGION();
        this.name = reg == null ? null : reg.getNAME();
    }

    public String getModify() {
        if (this.rid == 0 || this.account == null || this.password == null
                || this.region == null || this.name == null) {
            return "";
        } else if (Manager.SQL().getRegister(rid) != null) {
            Register updateRegister = Manager.SQL().updateRegister(rid, account, password, region, name);
            if (updateRegister != null) {
                this.setRegister(null);
                return "更新成功";
            } else {
                return "更新失敗";
            }
        } else {
            ArrayList<Table> createRegister = Manager.SQL().createRegister(rid, account, password, region, name);
            if (createRegister.size() > 0) {
                this.setRegister(null);
                return "建立成功";
            } else {
                return "建立失敗";
            }
        }
    }

    @Override
    public String toString() {
        return "RegisterBean{" + "rid=" + rid + ", account=" + account + ", password=" + password + ", region=" + region + ", name=" + name + '}';
    }
}
