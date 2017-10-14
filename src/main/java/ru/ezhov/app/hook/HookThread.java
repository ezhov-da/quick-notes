package ru.ezhov.app.hook;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.ezhov.app.connection.ConnectionH2DB;

/**
 *
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
