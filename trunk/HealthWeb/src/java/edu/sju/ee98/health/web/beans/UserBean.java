/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.web.beans;

import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author Leo
 */
public class UserBean implements Serializable {

    private String info;
    private String account;
    private String password;
    private String name;

    public UserBean() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return "ABC";
    }

    public String login() {
        if (account == null || password == null) {
            return "LOGIN";
        } else if (account.equals("aaa") && password.equals("bbb")) {
            this.info = "";
            return "LOGON";
        } else {
            this.info = "帳號或密碼錯誤";
            return "LOGIN";
        }
    }
}
