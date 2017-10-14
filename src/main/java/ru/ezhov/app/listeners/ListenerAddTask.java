package ru.ezhov.app.listeners;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import ru.ezhov.app.components.BasicFrameMini;
import ru.ezhov.app.connection.TreatmentQuerys;
import ru.ezhov.app.models.TaskModels;

/**
 *
 * @author Денис
 */
public class ListenerAddTask implements ActionListener {

    private static final Logger LOG = Logger.getLogger(ListenerAddTask.class.getName());

    private final JTextPane textPane;
    private final JDateChooser dateChooser;
    private final JCheckBox checkBox;

    public ListenerAddTask(JTextPane textPane, JDateChooser dateChooser, JCheckBox checkBox) {
        this.textPane = textPane;
        this.dateChooser = dateChooser;
        this.checkBox = checkBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (checkBox.isSelected()) {
                TreatmentQuerys.insertTask(textPane.getText(), new Date());
                reloadTask();
            } else {
                TreatmentQuerys.insertTask(textPane.getText(), dateChooser.getDate());
                reloadTask();
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

    }

    private void reloadTask() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                BasicFrameMini.INSTANCE.loadList();
            }
        });
    }

}
