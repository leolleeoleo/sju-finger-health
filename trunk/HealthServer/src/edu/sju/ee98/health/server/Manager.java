/*
 * Copyright (c) 2012, St. John's University and/or its affiliates. All rights reserved.
 */
package edu.sju.ee98.health.server;

import edu.sju.ee98.health.server.finger.FingerModule;
import edu.sju.ee98.health.server.finger.SerialScanner;
import edu.sju.ee98.health.sql.SQL;
import edu.sju.ee98.health.server.station.HealthService;

/**
 * 管理員
 *
 * @author 98405067
 */
public class Manager {

    private static Manager manager = null;
    public static Command command = null;
    public static HealthService server = null;
    public static SQL sql = null;
    public static FingerModule module = null;

    private Manager() {
        Manager.server = new HealthService();
        Manager.command = new Command();
        Manager.sql = new SQL("127.0.0.1", "health_test", "finger", "health");
        Manager.module = SerialScanner.scan();
    }

    /**
     * 使用管理員
     *
     * @return 管理員
     */
    public static Manager manager() {
        if (Manager.manager == null) {
            Manager.manager = new Manager();
        }
        return Manager.manager;
    }
}
