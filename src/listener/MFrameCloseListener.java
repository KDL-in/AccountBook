package listener;

import util.DBUtil;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by KundaLin on 17/6/14.
 */
public class MFrameCloseListener extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        DBUtil.close();
        System.exit(0);
    }
}
