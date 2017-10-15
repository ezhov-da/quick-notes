package ru.ezhov.app.components;

import com.sun.awt.AWTUtilities;
import ru.ezhov.app.hook.HookThread;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Денис
 */
public class BasicFrameMiniTest {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new HookThread());

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                    Logger.getLogger(BasicFrameMiniTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                BasicFrameMini basicFrame = BasicFrameMini.INSTANCE;
                basicFrame.setUndecorated(true);
                basicFrame.setAlwaysOnTop(true);
                basicFrame.setSize(new Dimension(800, 150));
                basicFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                basicFrame.setLocationRelativeTo(null);
                AWTUtilities.setWindowOpacity(basicFrame, 0.5f);
                basicFrame.setVisible(true);
            }
        });
    }
}
