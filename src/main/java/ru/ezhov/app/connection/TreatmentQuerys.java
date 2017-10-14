package ru.ezhov.app.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Денис
 */
public class TreatmentQuerys {

    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private TreatmentQuerys() {
    }

    public static synchronized void insertTask(String text, Date date) throws SQLException {
        if (!checkInsertDate(date)) {
            insertDate(date);
        }
        String dt = dateFormat.format(date);
        Connection connection = ConnectionH2DB.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Querys.INSERT_TASK);
        preparedStatement.setDate(1, new java.sql.Date(System.currentTimeMillis()));
        preparedStatement.setString(2, text);
        preparedStatement.setBoolean(3, false);
        preparedStatement.setInt(4, 1);
        preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
        preparedStatement.execute();
        preparedStatement.close();
    }

    /**
     * проверяем наличие вносимой даты true - есть, false - нет
     */
    private static boolean checkInsertDate(Date date) throws SQLException {
        String dt = dateFormat.format(date);
        Connection connection = ConnectionH2DB.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Querys.SELECT_CHECK_DATE);
        preparedStatement.setString(1, dt);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean flag = false;
        while (resultSet.next()) {
            flag = true;
        }
        preparedStatement.close();
        return flag;
    }

    /**
     * вносим строку выбранной даты, если ее вдруг нет
     */
    private static void insertDate(Date date) throws SQLException {
        String dt = dateFormat.format(date);
        Connection connection = ConnectionH2DB.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Querys.INSERT_TASK);
        preparedStatement.setDate(1, new java.sql.Date(date.getTime()));
        preparedStatement.setString(2, dt);
        preparedStatement.setBoolean(3, false);
        preparedStatement.setInt(4, 0);
        preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
        preparedStatement.execute();
        preparedStatement.close();
    }

    public static synchronized void updateCloseNotClose(boolean isClose, int idTask) throws SQLException {
        Connection connection = ConnectionH2DB.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Querys.UPDATE_CLOSE);
        preparedStatement.setBoolean(1, isClose);
        preparedStatement.setInt(2, idTask);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public static synchronized void updateDelete(boolean isDelete, int idTask) throws SQLException {
        Connection connection = ConnectionH2DB.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Querys.UPDATE_DELETE);
        preparedStatement.setBoolean(1, isDelete);
        preparedStatement.setInt(2, idTask);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public static synchronized void updateText(String text, int idTask) throws SQLException {
        Connection connection = ConnectionH2DB.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Querys.UPDATE_TEXT);
        preparedStatement.setString(1, text);
        preparedStatement.setInt(2, idTask);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public static synchronized void updateColor(String color, int idTask) throws SQLException {
        Connection connection = ConnectionH2DB.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Querys.UPDATE_COLOR);
        preparedStatement.setString(1, color);
        preparedStatement.setInt(2, idTask);
        preparedStatement.execute();
        preparedStatement.close();
    }

}
