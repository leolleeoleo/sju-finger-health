/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.sql;

import edu.sju.ee98.sql.SQLConnector;
import edu.sju.ee98.sql.Table;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MA780G
 */
public class SQL extends SQLConnector {

    public SQL(String host, String name, String user, String passwd) {
        super(host, name + "?useUnicode=true&characterEncoding=utf8", user, passwd);
        //"127.0.0.1:3306/authtest02", "authser", "authpasswd"
    }

//    public static void main(String[] args) {
//        SQL sql = new SQL("127.0.0.1", "health_test", "root", "sql");
    //        User user = sql.logInUser(TestSQL.c.getBytes());
    //        User user = sql.logInUser(TestSQL.c.getBytes());
//    }
    public void test_Group() {
        try {
            //        this.createTable(new Group());
            Group g = new Group();
            g.setGID(100);
            g.setNAME("fdgdg");
            this.insert(g);

            this.select(g, g.objectGID());
            g.print();
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void test_Register() {
        try {
            //        this.createTable(new Register());
            Register r = new Register();
            r.setRID(101);
            r.setACCOUNT("123465");
            r.setPASSWORD("32434");
            r.setREGION("台北");
            r.setNAME("一二三");
            this.insert(r);
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void test_Miles() {
        try {
            //        this.createTable(new Miles());
            Miles m = new Miles();
            m.setREGISTER_A(1312313);
            m.setREGISTER_B(1234853);
            m.setMETER(1231);
            this.insert(m);

            this.select(m, m.objectREGISTER_A());
            m.print();
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void test_Record() {
        try {
            //        this.createTable(new Record());
            Record re = new Record();
            re.setUSER("");
            re.setTIME(new Date(Date.UTC(2012, 4, 5, 21, 2, 23)));
            re.setREGISTER(13243);
            this.insert(re);
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void test_Cost() {
        try {
            //        this.createTable(new Cost());
            Cost c = new Cost();
            c.setPOINTS(12);
            c.setSTORE("");
            c.setUSER("");
            c.setTIME(new Date(Date.UTC(2012, 4, 5, 6, 7, 8)));
            this.insert(c);
        } //END of Test Code==============================================================
        catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//END of Test Code==============================================================

//Tables++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void createTables() {
        try {
            this.createTable(new User());
            this.createTable(new Group());
            this.createTable(new Register());
            this.createTable(new Miles());
            this.createTable(new Record());
            this.createTable(new Cost());
            this.createTable(new Fingerprint());
            //...
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dropTables() {
        try {
            this.dropTable(new User());
            this.dropTable(new Group());
            this.dropTable(new Register());
            this.dropTable(new Miles());
            this.dropTable(new Record());
            this.dropTable(new Cost());
            this.dropTable(new Fingerprint());
            //...
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//Table User++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /**
     * 建立使用者
     *
     * @param uid 使用者編號
     * @param account 帳號
     * @param password 密碼
     * @param group 群組編號
     * @param last_name 姓
     * @param fist_name 名
     * @param birthday 生日
     * @param address 地址
     * @param email 信箱
     * @param phone 電話
     * @return 使用者資料
     */
    public ArrayList<Table> createUser(String uid, String account, String password, int group, String last_name,
            String fist_name, Date birthday, String address, String email, String phone) {
        User user = new User(uid, account, password, group, last_name, fist_name, birthday, address, email, phone, 0);
        try {
            this.insert(user);
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.select(user, user.objectUID());
    }

    /**
     * 修改使用者
     *
     * @param user 使用者
     * @param account 帳號
     * @param password 密碼
     * @param fingerpoint 指紋
     * @return
     */
    public User modifyUser(User user, String password) {
        user.setPASSWORD(password);
        try {
            this.update(user, user.objectPASSWORD());
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (User) this.select(user, user.objectUID()).get(0);
    }

    /**
     * 刪除使用者
     *
     * @param root
     * @param user 被刪除的使用者
     * @throws Exception
     */
    public void deleteUser(User user) throws SQLException {
        this.delete(user);
    }

    /**
     * 登入
     *
     * @param account 帳號
     * @param password 密碼
     * @return
     */
    public ArrayList<Table> logInUser(String account, String password) {
        User u = new User();
        u.setACCOUNT(account);
        u.setPASSWORD(password);
        return this.select(u, u.objectACCOUNT(), u.objectPASSWORD());
    }

    public ArrayList<Table> logInUser(byte[] fingerprint) {
        Fingerprint f = new Fingerprint();
        f.setFINGERPRINT(fingerprint);
        ArrayList<Table> select = this.select(f, Arrays.copyOfRange(f.sqlObject, 1, 196));
        if (select.size() > 0) {
            f = (Fingerprint) select.get(0);
            return this.select(new User(), f.objectUID());
        }
        return select;
    }

    public ArrayList<Table> logInRegister(String account, String password) {
        Register r = new Register();
        r.setACCOUNT(account);
        r.setPASSWORD(password);
        return this.select(r, r.objectACCOUNT(), r.objectPASSWORD());
    }

    public ArrayList<Table> listUser() {
        return this.select(new User());
    }

//Table Group+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /**
     * 建立群組
     *
     * @param gid 群組標號
     * @param name 名稱
     * @return
     */
    public Group createGroup(int gid, String name) {
        Group group = new Group(gid, name);
        try {
            this.insert(group);
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (Group) this.select(group, group.objectGID()).get(0);
    }

    /**
     * 修改群組
     *
     * @param table 群組
     * @param gid 群組編號
     * @param name 名稱
     * @return
     */
    public Group modifyGroup(Group table, String gid, String name) {
        return null;
    }

    /**
     *
     * @param group 刪除群組
     */
    public void deleteGroup(Group group) throws SQLException {
        this.delete(group);
    }

//Table Register++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /**
     * 建立登錄站
     *
     * @param account 帳號
     * @param password 密碼
     * @param rid 登錄站編號
     * @param name 名稱
     * @return
     */
    public ArrayList<Table> createRegister(int rid, String account, String password, String region, String name) throws SQLException {
        Register register = new Register(rid, account, password, region, name);
        this.insert(register);
        return this.select(register, register.objectRID());
    }

    /**
     * 修改登錄站
     *
     * @param root 管理員
     * @param table 登錄站
     * @param account 帳號
     * @param password 密碼
     * @param region 地區
     * @param rid 登錄站編號
     * @param name 名稱
     * @return
     */
    public Register modifyRegister(User root, int rid, String account, String password, String region, String name) {
        return null;
    }

    /**
     *
     * @param table 刪除登錄站
     * @return
     */
    public void deleteRegister(User root, Register register) throws SQLException {
        this.delete(register);
    }

    /**
     * 
     * @return
     */
    public ArrayList<Table> listRegister() {
        return this.select(new Register());
    }

//Table Miles+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /**
     * 建立里程
     *
     * @param register_a 登錄站A
     * @param register_b 登錄站B
     * @param meter 公尺
     * @return
     */
    public Miles createMiles(int register_a, int register_b, int meter) {
        Miles miles = new Miles(register_a, register_b, meter);
        try {
            this.insert(miles);
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (Miles) this.select(miles, miles.objectMETER()).get(0);
    }

    /**
     * 修改里程
     *
     * @param table 里程
     * @param register_a 登錄站A
     * @param register_b 登錄站B
     * @param meter 公尺
     * @return
     */
    public Miles modifyMiles(Miles table, String register_a, String register_b, String meter) {
        return null;
    }

    /**
     * 刪除里程
     *
     * @param table 里程
     */
    public void deleteMiles(User root, Miles miles) throws SQLException {
        this.delete(miles);
    }

    /**
     * 
     * @return
     */
    public ArrayList<Table> listMiles() {
        return this.select(new Miles());
    }

//Table Record++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /**
     * 建立紀錄
     *
     * @param root 管理員
     * @param time 時間
     * @param user 使用者
     * @param register 登錄站
     * @return
     */
    public Record createRecord(java.util.Date time, String user, int register) {
        Record record = new Record(time, user, register);
        try {
            this.insert(record);
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (record);

    }

    /**
     * 修改紀錄
     *
     * @param record
     * @param user 使用者
     * @param register 登錄站
     * @return
     */
    public Record modifyRecord(Record record, int user, int register) {
        return null;
    }

    /**
     * 刪除紀錄
     *
     * @param record
     * @throws SQLException
     */
    public void deleteRecord(Record record) throws SQLException {
        this.delete(record);
    }

    /**
     * 
     * @return
     */
    public ArrayList<Table> listRecord() {
        return this.select(new Record());
    }

//Table Cost+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /**
     * 建立消費
     *
     * @param root
     * @param time 時間
     * @param store 商店
     * @param user 使用者
     * @param points 點數
     * @return
     */
    public Cost createCost(java.util.Date time, String store, String user, int points) {
        User u = new User();
        u.setUID(user);
        int point = this.plusPoints(u) - this.costPoints(u);
        if (point < points) {
            Logger.getLogger(SQL.class.getName()).log(Level.WARNING, "point=" + point + ",need=" + points, "not enough");
            return null;
        }
        Cost cost = new Cost(time, store, user, points);
        try {
            this.insert(cost);
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (cost);
    }

    /**
     * 修改消費
     *
     * @param table 消費
     * @param store 商店
     * @param user 使用者
     * @param points 點數
     * @return
     */
    public Cost modifyCost(Cost table, String store, String user, int points) {
        return null;
    }

    /**
     * 刪除消費
     *
     * @param cost
     * @throws SQLException
     */
    public void deleteCost(Cost cost) throws SQLException {
        this.delete(cost);
    }

    /**
     * 
     * @return
     */
    public ArrayList<Table> listCost() {
        return this.select(new Cost());
    }

//Table finger+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public Fingerprint createFingerprint(String uid, byte[] fp) {
        System.out.println(fp.length);
        Fingerprint fingerprint = new Fingerprint(uid, fp);
        try {
            this.insert(fingerprint);
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (Fingerprint) this.select(fingerprint, fingerprint.objectUID()).get(0);
    }

    public ArrayList<Table> getFingerprint() {
        Fingerprint fingerprint = new Fingerprint();
        return this.select(fingerprint);
    }

    public ArrayList<Table> getFingerprint(String uid) {
        Fingerprint fingerprint = new Fingerprint(uid);
        return this.select(fingerprint, fingerprint.objectUID());
    }

    public void deleteFingerprint(String uid) {
        Fingerprint fingerprint = new Fingerprint(uid);
        try {
            this.delete(fingerprint);
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> selectRecord(User user) {
        ArrayList<String> result = new ArrayList();
        Record record = new Record();
        Register register = new Register();
        record.setUSER(user.getUID());
        ArrayList<Table> select = this.select(record, record.objectUSER());
        for (int i = 0; i < select.size(); i++) {
            record = (Record) select.get(i);
            register.setRID(record.getREGISTER());
            ArrayList<Table> reg = this.select(register, register.objectRID());
            if (reg.size() > 0) {
                register = (Register) reg.get(0);
            }
            result.add(record.getTIME() + "_" + register.getNAME());
        }
        return result;
    }

    public ArrayList<String> selectExpend(User user) {
        ArrayList<String> result = new ArrayList();
        Cost cost = new Cost();
        User store = new User();
        cost.setUSER(user.getUID());
        ArrayList<Table> select = this.select(cost, cost.objectUSER());
        for (int i = 0; i < select.size(); i++) {
            cost = (Cost) select.get(i);
            store.setUID(cost.getSTORE());
            ArrayList<Table> sto = this.select(store, store.objectUID());
            if (sto.size() > 0) {
                store = (User) sto.get(0);
            }
            result.add(cost.getTIME() + "_" + store.getLAST_NAME() + store.getFIRST_NAME() + "_" + cost.getPOINTS());
        }
        return result;
    }

//point++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public int plusPoints(User user) {
        int point = 0;
        Record record = new Record();
        record.setUSER(user.getUID());
        ArrayList<Table> list_record = this.select(record, record.objectUSER());
        for (int i = 1; i < list_record.size(); i++) {
            int reg_a = ((Record) list_record.get(i - 1)).getREGISTER();
            int reg_b = ((Record) list_record.get(i)).getREGISTER();
            Miles mile = new Miles();
            mile.setREGISTER_A(reg_a);
            mile.setREGISTER_B(reg_b);
            ArrayList list = this.select(mile, mile.objectREGISTER_A(), mile.objectREGISTER_B());
            if (list.isEmpty()) {
                mile.setREGISTER_A(reg_b);
                mile.setREGISTER_B(reg_a);
                list = this.select(mile, mile.objectREGISTER_A(), mile.objectREGISTER_B());
            }
            if (list.size() > 0) {
                point += ((Miles) list.get(0)).getMETER();
            }
        }
        return point;
    }

    public int costPoints(User user) {
        int point = 0;
        Cost cost = new Cost();
        cost.setUSER(user.getUID());
        ArrayList<Table> list_cost = this.select(cost, cost.objectUSER());
        for (int i = 0; i < list_cost.size(); i++) {
            point += ((Cost) list_cost.get(i)).getPOINTS();
        }
        return point;
    }
}
