package ru.ezhov.app;

import ru.ezhov.app.components.BasicFrameMini;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Денис
 */
public class Tasks {

    /**
     * программа для ведения задач
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                Logger.getLogger(Tasks.class.getName()).log(Level.SEVERE, null, ex);
            }
            BasicFrameMini basicFrame = BasicFrameMini.INSTANCE;
            basicFrame.setUndecorated(true);
            basicFrame.setAlwaysOnTop(true);
            basicFrame.setSize(new Dimension(800, 150));
            basicFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            basicFrame.setLocationRelativeTo(null);
            basicFrame.setOpacity(0.5f);
            basicFrame.setVisible(true);
        });
    }
}
