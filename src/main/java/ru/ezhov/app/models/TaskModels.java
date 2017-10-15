package ru.ezhov.app.models;

import ru.ezhov.app.connection.ConnectionH2DB;
import ru.ezhov.app.connection.Querys;
import ru.ezhov.app.domain.Task;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Денис
 */
public class TaskModels {

    private static final Logger LOG = Logger.getLogger(TaskModels.class.getName());
    private static List<Task> tasks;
    public final static AbstractTableModel tableModel = new TaskTreeModels();
    public final static AbstractListModel listModel = new TaskListModel();

    private TaskModels() {
    }

    public static synchronized void reloadTasks() {
        try {
            Connection connection = ConnectionH2DB.INSTANCE.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Querys.SELECT_TASKS);
            tasks = new ArrayList<Task>(100);
            ResultSet resultSet = preparedStatement.executeQuery();
            Task task;

            while (resultSet.next()) {
                task = new Task(
                        resultSet.getInt(1),
                        resultSet.getDate(2),
                        resultSet.getString(3),
                        resultSet.getBoolean(4),
                        resultSet.getInt(5),
                        resultSet.getTimestamp(6),
                        resultSet.getString(7));
                tasks.add(task);

            }

        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * класс реализует модель для таблицы
     */
    private static class TaskTreeModels extends AbstractTableModel {


        public int getRowCount() {
            return tasks.size();
        }


        public int getColumnCount() {
            return 2;
        }


        public Object getValueAt(int rowIndex, int columnIndex) {
            return tasks.get(rowIndex);

        }

    }

    /**
     * класс реализует модель списка
     */
    private static class TaskListModel extends AbstractListModel {


        public int getSize() {
            return tasks.size();
        }


        public Task getElementAt(int index) {
            return tasks.get(index);
        }
    }

    public synchronized static List<Task> getTasks() {
        return tasks;
    }

    /**
     * удаляем задачу из списка
     */
    public synchronized static void removeTask(int idTask) {
        for (Task t : tasks) {
            if (t.getId() == idTask) {
                tasks.remove(t);
                break;
            }
        }
    }

}
