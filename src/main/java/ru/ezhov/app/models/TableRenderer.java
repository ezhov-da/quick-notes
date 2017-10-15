package ru.ezhov.app.models;

import ru.ezhov.app.domain.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * @author Денис
 */
public class TableRenderer extends DefaultTableCellRenderer {

    private final Icon check = new ImageIcon(TableRenderer.class.getResource("/check.png"));
    private final Icon unCheck = new ImageIcon(TableRenderer.class.getResource("/uncheck.png"));

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
