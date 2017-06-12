package bootstrap;

import frame.MFrame;
import util.DBUtil;
import util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by KundaLin on 17/6/6.
 */
public class Bootstrap {
    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        GUIUtil.useSkin();
        DBUtil.autoIncreasing();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                MFrame mFrame = MFrame.getInstance();
                mFrame.setLayout(new BorderLayout());
                mFrame.setResizable(false);
                mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mFrame.setMainPanel();
                mFrame.pack();
                mFrame.setLocationRelativeTo(null);
                mFrame.setVisible(true);
            }
        });
    }
}
