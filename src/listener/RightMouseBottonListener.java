package listener;

import panel.JListPanel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by KundaLin on 17/6/8.
 */
public class RightMouseBottonListener extends MouseAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if (SwingUtilities.isRightMouseButton(e)) {
            JListPanel jListPanel = JListPanel.getInstance();
            JTable table = jListPanel.jTable;
            int row = table.rowAtPoint(e.getPoint());
            table.setRowSelectionInterval(row, row);
            jListPanel.jPopupMenu.show(e.getComponent(),e.getX(),e.getY());
        }
    }
}
