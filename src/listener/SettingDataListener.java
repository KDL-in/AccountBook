package listener;

import frame.DateSelectFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by KundaLin on 17/6/13.
 */
public class SettingDataListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (e.getClickCount() == 2) {
            DateSelectFrame dateSelectFrame = DateSelectFrame.getInstance();
            dateSelectFrame.setVisible(true);
        }
    }
}
