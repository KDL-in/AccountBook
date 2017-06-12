package listener;

import panel.SettingPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by KundaLin on 17/6/9.
 */
public class DeleteButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        int row = SettingPanel.getInstance().jTable.getSelectedRow();
        if (row >= 0) {
            SettingPanel.getInstance().settingTableModel.removeRow(row);
            SettingPanel.getInstance().changeTable();
        }

    }
}
