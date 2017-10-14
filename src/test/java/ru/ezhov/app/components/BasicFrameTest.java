package ru.ezhov.app.components;

import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import ru.ezhov.app.components.BasicFrame;
import ru.ezhov.app.hook.HookThread;

/**
 *
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
                //basicFrame.pack();
                basicFrame.setSize(new Dimension(400, 800));
                basicFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                basicFrame.setOpacity();
                basicFrame.setVisible(true);
            }
        });
    }
    
}
