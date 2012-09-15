/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.web.beans;

import edu.sju.ee98.health.sql.User;
import edu.sju.ee98.health.web.Manager;
import edu.sju.ee98.sql.Table;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leo
 */
public class AccountBean implements java.io.Serializable {

    private String uid;
    private int gid;
    private String account;
    private String password;
    private String lastName;
    private String firstName;
    private Date birthday;
    private String address;
    private String email;
    private String phone;

    public String getUid() {
        return uid == null ? "" : uid;
    }

    public int getGid() {
        return gid;
    }

    public String getAccount() {
        return account == null ? "" : account;
    }

    public String getPassword() {
        return password == null ? "" : password;
    }

    public String getLastName() {
        return lastName == null ? "" : lastName;
    }

    public String getFirstName() {
        return firstName == null ? "" : firstName;
    }

    public String getBirthday() {
        return birthday == null ? "" : birthday.toString();
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public String getEmail() {
        return email == null ? "" : email;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setBirthday(String birthday) {
        try {
            this.birthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
        } catch (ParseException ex) {
            Logger.getLogger(AccountBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUserByid(String uid) {
        User user = Manager.SQL().getUser(uid);
        if (user != null) {
            this.setUser(user);
        }
    }

    public void setUser(User user) {
        this.uid = user == null ? null : user.getUID();
        this.gid = user == null ? 0 : user.getGROUP();
        this.account = user == null ? null : user.getACCOUNT();
        this.password = user == null ? null : user.getPASSWORD();
        this.lastName = user == null ? null : user.getLAST_NAME();
        this.firstName = user == null ? null : user.getFIRST_NAME();
        this.birthday = user == null ? null : user.getBIRTHDAY();
        this.address = user == null ? null : user.getADDRESS();
        this.email = user == null ? null : user.getEmail();
        this.phone = user == null ? null : user.getPHONE();
    }

    public String getRegister() {
        if (this.uid == null || this.account == null || this.password == null
                || this.lastName == null || this.firstName == null || this.birthday == null
                || this.address == null || this.email == null || this.phone == null) {
            return "";
        } else if (Manager.SQL().getUser(uid) != null) {
            ArrayList<Table> updateUser = Manager.SQL().updateUser(uid, account, password, gid, lastName, firstName, birthday, address, email, phone);
            if (updateUser.size() > 0) {
                this.setUser(null);
                return "更新成功";
            } else {
                return "更新失敗";
            }
        } else {
            ArrayList<Table> createUser = Manager.SQL().createUser(uid, account, password, gid, lastName, firstName, birthday, address, email, phone);
            if (createUser.size() > 0) {
                this.setUser(null);
                return "建立成功";
            } else {
                return "建立失敗";
            }
        }
    }

    @Override
    public String toString() {
        return "AccountBean{" + "uid=" + uid + ", gid=" + gid + ", account=" + account + ", password=" + password + ", lastName=" + lastName + ", firstName=" + firstName + ", birthday=" + birthday + ", address=" + address + ", email=" + email + ", phone=" + phone + '}';
    }
}
