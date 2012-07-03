/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.web.beans;

import edu.sju.ee98.health.sql.SQL;
import edu.sju.ee98.health.sql.User;
import edu.sju.ee98.health.web.Manager;
import edu.sju.ee98.sql.Table;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Leo
 */
public class UserBean implements Serializable {

    private String info;
    private User user;

    public UserBean() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return this.user.getLAST_NAME() + this.user.getFIRST_NAME();
    }

    public String getId() {
        return this.user.getUID();
    }

    public String getBirth() {
        return new SimpleDateFormat("yyyy/MM/dd").format(this.user.getBIRTHDAY());
    }

    public String getAddress() {
        return this.user.getADDRESS();
    }

    public String getEmail() {
        return this.user.getEmail();
    }

    public String getPhone() {
        return this.user.getPHONE();
    }

    public boolean login(String account, String password) {
        if (this.user != null) {
            return true;
        }
        if (account == null || password == null) {
            return false;
        } else {
            ArrayList<Table> logInUser = Manager.SQL().logInUser(account, password);
            if (logInUser.size() > 0) {
                this.user = (User) logInUser.get(0);
                this.info = "";
                return true;
            } else {
                this.info = "帳號或密碼錯誤";
                return false;
            }
        }
    }
}
