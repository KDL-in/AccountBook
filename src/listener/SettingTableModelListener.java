package listener;

import panel.SettingPanel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * Created by KundaLin on 17/6/9.
 */
public class SettingTableModelListener implements TableModelListener {
    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getColumn() == -1) return;
        try {
            Integer.parseInt(SettingPanel.getInstance().jTable.getValueAt(e.getFirstRow(), 1).toString().trim());
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(null, "应该输入整数");
            SettingPanel.getInstance().settingTableModel.setValueAt(0,e.getFirstRow(),1);
            return;
        }
        SettingPanel.getInstance().TableChange();

    }
}
