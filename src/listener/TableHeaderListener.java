package listener;

import frame.DateSelectFrame;
import panel.JListPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by KundaLin on 17/6/12.
 */
public class TableHeaderListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        JListPanel instance = JListPanel.getInstance();
        int col = instance.jTable.columnAtPoint(e.getPoint());
        super.mouseClicked(e);
        if (e.getClickCount() == 2&& col==0) {
            DateSelectFrame dateSelectFrame = DateSelectFrame.getInstance();
        }
    }
}
