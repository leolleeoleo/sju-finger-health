/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.web;

import edu.sju.ee98.health.sql.SQL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leo
 */
public class Manager {

    private static SQL sql = null;

    public static SQL SQL() {
        if (sql == null) {
            sql = new SQL("163.21.76.50", "health_test", "finger", "health");
        }
        return sql;
    }
}
