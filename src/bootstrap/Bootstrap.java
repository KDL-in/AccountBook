package bootstrap;

import frame.MFrame;
import listener.MFrameCloseListener;
import util.DBUtil;
import util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by KundaLin on 17/6/6.
 */
public class Bootstrap {
    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        GUIUtil.useSkin();
        DBUtil.checkDB();
        DBUtil.autoIncreasing();
        SwingUtilities.invokeAndWait(() -> {
            MFrame mFrame = MFrame.getInstance();
            mFrame.setLayout(new BorderLayout());
            mFrame.setResizable(false);
            mFrame.addWindowListener(new MFrameCloseListener());
            mFrame.setMainPanel();
            mFrame.pack();
            mFrame.setLocationRelativeTo(null);
            mFrame.setVisible(true);
        });
    }
}
