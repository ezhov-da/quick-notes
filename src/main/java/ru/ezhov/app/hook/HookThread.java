package ru.ezhov.app.hook;

import ru.ezhov.app.connection.ConnectionH2DB;

import java.sql.SQLException;

/**
 * @author Денис
 */
public class HookThread extends Thread {

    @Override
    public void run() {
        try {
            ConnectionH2DB.INSTANCE.getConnection().close();
        } catch (SQLException ex) {
            //
        }
    }

}
