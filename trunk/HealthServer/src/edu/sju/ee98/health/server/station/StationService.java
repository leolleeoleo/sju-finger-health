/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sju.ee98.health.server.station;

import edu.sju.ee98.fingerprint.FingerCharacterize;
import edu.sju.ee98.health.server.sql.Record;
import edu.sju.ee98.health.server.sql.Register;
import edu.sju.ee98.health.server.sql.User;

/**
 *
 * @author Leo
 */
public interface StationService {

    public Register login(String account, String password);

    public User login(FingerCharacterize characterize);

    public Record register(User user, Register register);
}
