package ru.ezhov.app.models;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import ru.ezhov.app.domain.Task;

/**
 *
 * @author Денис
 */
public class TableRenderer extends DefaultTableCellRenderer {

    private final Icon check = new ImageIcon(TableRenderer.class.getResource("/ru/ezhov/tasks/src/check.png"));
    private final Icon unCheck = new ImageIcon(TableRenderer.class.getResource("/ru/ezhov/tasks/src/uncheck.png"));

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Task task = (Task) value;

        switch (column) {
            case 0:
                label.setText("");
                if (task.getWho() != 0) {
                    if (task.isCloseNotClose()) {
                        label.setIcon(check);
                    } else {
                        label.setIcon(unCheck);
                    }
                }
                return label;
            case 1:
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

}
