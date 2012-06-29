/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.web;

import edu.sju.ee98.health.server.sql.SQL;

/**
 *
 * @author Leo
 */
public class Manager {

    public static final SQL sql = new SQL("163.21.76.50", "health_test", "finger", "health");
}
