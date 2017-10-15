package ru.ezhov.app.components;

import ru.ezhov.app.connection.TreatmentQuerys;
import ru.ezhov.app.domain.Task;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Денис
 */
public class TaskComponent extends JPanel {

    private static final Logger LOG = Logger.getLogger(TaskComponent.class.getName());

    private final Border borderCommon = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3), BorderFactory.createLineBorder(Color.GRAY));
    private final Border borderSelect = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3), BorderFactory.createLineBorder(Color.RED));
    private final static Image IMAGE_BACKGROUND = new ImageIcon(TaskComponent.class.getResource("/note1.jpg")).getImage();

    private JLabel labelText = new JLabel();
    private JPanel panelCenter = new JPanel(new GridLayout(1, 1)) {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(IMAGE_BACKGROUND, 0, 0, null);
        }
    };

    private JToggleButton toggleButtonCloseTask = new JToggleButton(new ActionToggleButtonCloseTask());
    //private JToggleButton buttonEdit = new JToggleButton(new ActionButtonEdit());
    private JButton buttonDelete = new JButton(new ActionButtonDelete());
    private JPanel panelHelper = new JPanel();
    private Dimension dimension = new Dimension(100, 100);
    private Dimension dimensionLittle = new Dimension(25, 25);
    private final Icon close = new ImageIcon(BasicFrameMini.class.getResource("/good_mark.png"));
    private final Icon notClose = new ImageIcon(BasicFrameMini.class.getResource("/bad_mark.png"));
    private final OwnPopupMenu ownPopupMenu = new OwnPopupMenu();

    private Task task;

    public TaskComponent(Task task) {
        this.task = task;
        init();

    }

    private void init() {
        repaintTaskComponent();
        labelText.setOpaque(true);
        labelText.setVerticalTextPosition(JLabel.CENTER);
        panelCenter.add(labelText);

        setLayout(new BorderLayout(5, 5));
        add(panelCenter, BorderLayout.CENTER);
        panelHelper.setLayout(new BoxLayout(panelHelper, BoxLayout.X_AXIS));
        panelHelper.add(toggleButtonCloseTask);
        panelHelper.add(Box.createHorizontalGlue());
        //panelHelper.add(buttonEdit);
        panelHelper.add(buttonDelete);

        add(panelHelper, BorderLayout.SOUTH);

        setSize(dimension);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);

        toggleButtonCloseTask.setPreferredSize(dimensionLittle);
        //buttonEdit.setPreferredSize(dimensionLittle);
        buttonDelete.setPreferredSize(dimensionLittle);

        setBorder();

        labelText.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent e) {
                TaskComponent.this.setBorder(borderCommon);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                TaskComponent.this.setBorder(borderSelect);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    try {
                        Color color = JColorChooser.showDialog(labelText, "Выбор подсветки для заметки", Color.yellow);
                        String colorStr =
                                String.valueOf(
                                        color.getRed()) + ";" +
                                        String.valueOf(color.getGreen()) + ";" +
                                        String.valueOf(color.getBlue()
                                        );
                        TreatmentQuerys.updateColor(colorStr, task.getId());
                        labelText.setBackground(color);
                        task.setColor(color);
                    } catch (SQLException ex) {
                        Logger.getLogger(TaskComponent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (e.getButton() == MouseEvent.BUTTON1) {
                    ownPopupMenu.setVisible(true);
                }

            }

        });
    }

    private void setBorder() {
        setBorder(borderCommon);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
        repaintTaskComponent();
    }

    private void repaintTaskComponent() {
        //buttonCenter.setText("<html><font size=\"2\">" + task.getTextTask() + "</font>");
        labelText.setText("<html><p align=\"center\"><b>" + task.getTextTask().replaceAll("\n", "<br>" + "</b></p>"));
        labelText.setBackground(task.getColor());
        if (task.isCloseNotClose()) {
            toggleButtonCloseTask.setIcon(close);
            toggleButtonCloseTask.setSelected(true);
        } else {
            toggleButtonCloseTask.setIcon(notClose);
            toggleButtonCloseTask.setSelected(false);
        }
    }

    /**
     * класс, обрабатывает метку как выполненное или не выполненное
     */
    private class ActionToggleButtonCloseTask extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (toggleButtonCloseTask.isSelected()) {
                toggleButtonCloseTask.setIcon(close);
            } else {
                toggleButtonCloseTask.setIcon(notClose);
            }
            task.setCloseNotClose(toggleButtonCloseTask.isSelected());
            updateStatus(toggleButtonCloseTask.isSelected(), task.getId());
            TaskComponent.this.repaintTaskComponent();

        }

        /**
         * обновляем статус по задаче
         */
        private void updateStatus(boolean isClose, int idTask) {
            try {
                TreatmentQuerys.updateCloseNotClose(isClose, idTask);
            } catch (SQLException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
    }

    private class ActionButtonDelete extends AbstractAction {

        {
            putValue(AbstractAction.SMALL_ICON, new ImageIcon(BasicFrameMini.class.getResource("/delete_task.png")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            updateDelete(true, task.getId());

        }

        /**
         * удаляем задачу
         */
        private void updateDelete(boolean isDelete, int idTask) {
            try {
                TreatmentQuerys.updateDelete(isDelete, idTask);
                BasicFrameMini.INSTANCE.loadList();
                BasicFrameMini.INSTANCE.repaint();
            } catch (SQLException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * отображаем меню для редактирования
     */
    private class ActionButtonEdit extends AbstractAction {

        {
            putValue(AbstractAction.SMALL_ICON, new ImageIcon(BasicFrameMini.class.getResource("/edit.png")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ownPopupMenu.setVisible(true);
        }

    }

    private class OwnPopupMenu extends JPopupMenu {

        private final JTextPane textPane = new JTextPane();
        private final JButton button = new JButton("Редактировать");

        public OwnPopupMenu() {
            init();
        }

        private void init() {
            setLayout(new BorderLayout());
            add(new JScrollPane(textPane), BorderLayout.CENTER);
            JPanel panel = new JPanel();
            panel.add(button);
            add(panel, BorderLayout.SOUTH);
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        TreatmentQuerys.updateText(textPane.getText(), task.getId());
                        task.setTextTask(textPane.getText());
                        repaintTaskComponent();
                    } catch (SQLException ex) {
                        LOG.log(Level.SEVERE, null, ex);
                    }
                }
            });
        }

        public void setVisible(boolean b) {
            JScrollPane scrollPane = BasicFrameMini.INSTANCE.getScrollPanePanelTask();
            ownPopupMenu.setPreferredSize(scrollPane.getSize());
            ownPopupMenu.setLocation(scrollPane.getLocationOnScreen().x, scrollPane.getLocationOnScreen().y + scrollPane.getHeight());
            ownPopupMenu.setInvoker(scrollPane);

            textPane.setText(task.getTextTask());
            super.setVisible(b);
        }

    }
}
