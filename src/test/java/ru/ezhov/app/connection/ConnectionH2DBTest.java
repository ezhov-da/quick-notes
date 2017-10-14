package ru.ezhov.app.connection;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConnectionH2DBTest {
    @Test
    public void getConnection() throws Exception {
        ConnectionH2DB connectionH2DB = ConnectionH2DB.INSTANCE;
        connectionH2DB.getConnection();
        connectionH2DB.getConnection().close();
    }

}