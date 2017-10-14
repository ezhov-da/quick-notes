package ru.ezhov.app.components;

import com.sun.jna.platform.win32.Kernel32;
import com.toedter.calendar.JDateChooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;

import ru.ezhov.app.domain.Task;
import ru.ezhov.app.listeners.ListenerAddTask;
import ru.ezhov.app.models.TaskModels;

/**
 * @author ezhov-da
 */
public class BasicFrameMini extends JFrame {

    public static final BasicFrameMini INSTANCE = new BasicFrameMini();

    private JLabel labelEmptyForDrag =
            new JLabel("<html><font size=\"2\"><b>" +
                    "заметки v 0.01 [PID: " +
                    Kernel32.INSTANCE.GetCurrentProcessId() +
                    "]" +
                    "</b></font>");
    private JScrollPane scrollPane = new JScrollPane();
    private JPanel panel = new JPanel(new GridLayout(1, 1000));
    private JToggleButton buttonAdd = new JToggleButton(
            new ImageIcon(BasicFrameMini.class.getResource("/add.png")));
    private JButton buttonExit = new JButton(
            new ImageIcon(BasicFrameMini.class.getResource("/exit.png")));
    private PanelAdd panelAdd = new PanelAdd();
    private JPanel panelCenter = new JPanel(new BorderLayout());

    private BasicFrameMini() {
        init();
    }

    private void init() {
        loadList();

        labelEmptyForDrag.setPreferredSize(new Dimension(100, 10));
        labelEmptyForDrag.setOpaque(false);
        JPanel panelTop = new JPanel(new BorderLayout(5, 5));
        panelTop.setBackground(new Color(224, 228, 235));
        panelTop.add(labelEmptyForDrag, BorderLayout.CENTER);
        panelTop.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        scrollPane.setViewportView(panel);
        panelCenter.add(scrollPane, BorderLayout.CENTER);
        panelCenter.add(buttonAdd, BorderLayout.WEST);
        panelCenter.add(buttonExit, BorderLayout.EAST);
        add(panelTop, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        setListeners();
    }

    public void loadList() {
        panel.removeAll();
        panel.revalidate();
        TaskModels.reloadTasks();
        List<Task> tasks = TaskModels.getTasks();
        for (Task task : tasks) {
            panel.add(new TaskComponent(task));
        }
    }

    public JPanel getPanelTasks() {
        return panel;
    }

    public JScrollPane getScrollPanePanelTask() {
        return scrollPane;
    }

    private void setListeners() {
        buttonAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonAdd.isSelected()) {
                    buttonAdd.setIcon(
                            new ImageIcon(
                                    BasicFrameMini.class.getResource("/pen_write_edit.png")
                            )
                    );
                    panelCenter.remove(scrollPane);
                    panelCenter.add(panelAdd, BorderLayout.CENTER);
                    panelCenter.validate();
                    panelCenter.repaint();
                } else {
                    buttonAdd.setIcon(
                            new ImageIcon(
                                    BasicFrameMini.class.getResource("/add.png")
                            )
                    );
                    panelCenter.remove(panelAdd);
                    panelCenter.add(scrollPane, BorderLayout.CENTER);
                    panelCenter.validate();
                    panelCenter.repaint();
                }

            }
        });

        buttonExit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        DragAndDropeFrame dragAndDropeFrame = new DragAndDropeFrame();
        labelEmptyForDrag.addMouseMotionListener(dragAndDropeFrame);
        labelEmptyForDrag.addMouseListener(dragAndDropeFrame);
    }

    private class DragAndDropeFrame extends MouseAdapter implements MouseMotionListener {

        private Dimension dimension;

        @Override
        public void mouseDragged(MouseEvent e) {
            Point pointBasicFrameMini = BasicFrameMini.INSTANCE.getLocationOnScreen();
            Point pointMouse = e.getLocationOnScreen();

            if (dimension == null) {
                dimension = new Dimension(pointMouse.x - pointBasicFrameMini.x, pointMouse.y - pointBasicFrameMini.y);
            }

            BasicFrameMini.INSTANCE.setLocation(pointMouse.x - dimension.width, pointMouse.y - dimension.height);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            dimension = null;
        }

    }

    private class PanelAdd extends JPanel {

        private final JTextPane textPane = new JTextPane();
        private final JDateChooser dateChooser = new JDateChooser();
        private final JCheckBox checkBox = new JCheckBox("сегодня");
        private final JButton buttonAddTask = new JButton("добавить");
        private final Insets insets = new Insets(5, 5, 5, 5);

        public PanelAdd() {
            setLayout(new GridBagLayout());

            add(new JScrollPane(textPane), new GridBagConstraints(0, 0, 1, 2, 1f, 1f, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, insets, 0, 0));
            add(dateChooser, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, insets, 0, 0));
            add(checkBox, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, insets, 0, 0));
            add(buttonAddTask, new GridBagConstraints(1, 1, 2, 1, 0, 0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, insets, 0, 0));
            buttonAddTask.addActionListener(new ListenerAddTask(textPane, dateChooser, checkBox));
            checkBox.setSelected(true);
        }
    }
}
