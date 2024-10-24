package ru.ezhov.app.connection;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Денис
 */
public class ConnectionH2DB {

    private static final Logger LOG = Logger.getLogger(ConnectionH2DB.class.getName());

    public static final ConnectionH2DB INSTANCE = new ConnectionH2DB();

    public Connection getConnection() {
        try {
            Class.forName("org.h2.Driver");
            File file = new File("tasks_db");
            LOG.log(Level.INFO, "путь к бд: {0}", file.getAbsolutePath());
            return DriverManager.getConnection("jdbc:h2:" + file.getAbsolutePath(), "tasks", "tasks");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "ошибка подключения к базе с задачами", ex);
            throw new RuntimeException("Ошибка подключения к базе с задачами", ex);
        }
    }
}
