package listener;

import dao.RecordsDAO;
import entity.Record;
import panel.JListPanel;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by KundaLin on 17/6/8.
 */
public class noteItemListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JListPanel jListPanel = JListPanel.getInstance();
        int row = jListPanel.jTable.getSelectedRow();
        if (row >= 0) {
            int rid = (Integer) JListPanel.getInstance().jTable.getValueAt(row, 3);
            Record r = RecordsDAO.queryRid(rid);
            String newNote = JOptionPane.showInputDialog(r.note+" ->");
            jListPanel.change(rid,3,newNote);
            jListPanel.updateDataAndUI();
        }
    }
}
