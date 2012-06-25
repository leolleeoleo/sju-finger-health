/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.server.web;

import edu.sju.ee98.health.server.sql.Record;
import edu.sju.ee98.health.server.sql.Register;
import edu.sju.ee98.health.server.sql.User;

/**
 *
 * @author Leo
 */
public interface WebService {

    public User login(String account, String password);

    public User createUser(User user);

    public Record Query(User user);
    
    public void cost(User user);

    public Register createRegister(Register register);
}
