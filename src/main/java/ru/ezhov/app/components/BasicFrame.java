package ru.ezhov.app.components;

import com.sun.awt.AWTUtilities;
import com.toedter.calendar.JDateChooser;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JWindow;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ru.ezhov.app.listeners.ListenerAddTask;
import ru.ezhov.app.models.TableRenderer;
import ru.ezhov.app.models.TaskModels;

/**
 * основное окно для работы
 *
 * @author Денис
 */
public class BasicFrame extends JFrame {

    public static final BasicFrame INSTANCE = new BasicFrame();
    protected JToggleButton buttonAdd = new JToggleButton("добавить");
    protected JButton buttonUpdate = new JButton("обновить");
    protected JPanel panel = new JPanel(new BorderLayout());
    protected OwnPopupMenuAdd ownPopupMenu = new OwnPopupMenuAdd(this);
    protected JTable table = new JTable();
    protected JScrollPane scrollPane = new JScrollPane();
    protected JSlider slider = new JSlider(2, 10);

    protected BasicFrame() {
        super("задачник");
        setModels();
        init();
        setListeners();
    }

    protected void init() {
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);

        slider.addChangeListener(new SliderChangeListener());
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);

        JPanel panelUp = new JPanel(new BorderLayout());
        panelUp.add(buttonAdd, BorderLayout.WEST);
        panelUp.add(buttonUpdate, BorderLayout.EAST);

        scrollPane.setViewportView(table);
        panel.add(panelUp, BorderLayout.NORTH);
        panel.add(table, BorderLayout.CENTER);
        panel.add(slider, BorderLayout.SOUTH);

        add(panel);
    }

    protected void setModels() {
        TaskModels.reloadTasks();
        table.setModel(TaskModels.tableModel);
        table.setDefaultRenderer(Object.class, new TableRenderer());
    }

    protected void setListeners() {
        buttonAdd.addActionListener(new ActionListenerShowHide());
    }

    private class OwnPopupMenuAdd extends JWindow {

        private final JPanel panelMenu = new JPanel(new GridBagLayout());
        private final JTextPane textPane = new JTextPane();
        private final JDateChooser dateChooser = new JDateChooser();
        private final JCheckBox checkBox = new JCheckBox("сегодня");
        private final JButton buttonAddTask = new JButton("добавить");
        private final Insets insets = new Insets(5, 5, 5, 5);

        public OwnPopupMenuAdd(Frame owner) {
            super(owner);
            panelMenu.add(new JScrollPane(textPane), new GridBagConstraints(0, 0, 1, 2, 1f, 1f, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, insets, 0, 0));
            panelMenu.add(dateChooser, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, insets, 0, 0));
            panelMenu.add(checkBox, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, insets, 0, 0));
            panelMenu.add(buttonAddTask, new GridBagConstraints(1, 1, 2, 1, 0, 0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, insets, 0, 0));
            add(panelMenu);

            buttonAddTask.addActionListener(new ListenerAddTask(textPane, dateChooser, checkBox));

        }

    }

    //слушатели
    private class ActionListenerShowHide implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (buttonAdd.isSelected()) {

                int width = table.getWidth();
                int height = 300;

                int heighthButton = buttonAdd.getHeight();
                ownPopupMenu.setSize(width, height);

                ownPopupMenu.setLocation(buttonAdd.getLocationOnScreen().x, buttonAdd.getLocationOnScreen().y + heighthButton);
                ownPopupMenu.setVisible(true);
            } else {
                ownPopupMenu.setVisible(false);
            }

        }
    }

    private class SliderChangeListener implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            BasicFrame.this.setOpacity();
        }

    }

    public void setOpacity() {
        float i = slider.getValue();
        float fl = i / 10f;
        AWTUtilities.setWindowOpacity(BasicFrame.this, fl);
    }

}
