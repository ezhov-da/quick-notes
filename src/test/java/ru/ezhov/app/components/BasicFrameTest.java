package ru.ezhov.app.components;

import ru.ezhov.app.hook.HookThread;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Денис
 */
public class BasicFrameTest {

    /**
     * Тестируем основную форму
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new HookThread());

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                    Logger.getLogger(BasicFrameTest.class.getName()).log(Level.SEVERE, null, ex);
                }

                BasicFrame basicFrame = BasicFrame.INSTANCE;
                basicFrame.setAlwaysOnTop(true);
                basicFrame.setSize(new Dimension(400, 800));
                basicFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                basicFrame.setOpacity();
                basicFrame.setVisible(true);
            }
        });
    }

}
