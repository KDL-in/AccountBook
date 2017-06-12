package listener;

import dao.RecordsDAO;
import frame.MFrame;
import panel.JListPanel;
import panel.OperatePanel;
import util.DBUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by KundaLin on 17/6/8.
 */
public class DeleteItemListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        int flag = JOptionPane.showConfirmDialog(MFrame.getInstance(), "确认删除？", "", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (flag == JOptionPane.YES_NO_OPTION) {
            JListPanel jListPanel = JListPanel.getInstance();
            int row = jListPanel.jTable.getSelectedRow();
            if (row >= 0) {
                RecordsDAO.delete((int)jListPanel.jTable.getValueAt(row,3));
                ((DefaultTableModel) jListPanel.jTable.getModel()).removeRow(row);
                OperatePanel.getInstance().readDefaultData();
                OperatePanel.getInstance().updateUIData();
            }

        }
    }
}
