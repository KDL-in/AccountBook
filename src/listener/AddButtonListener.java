package listener;

import panel.SettingPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by KundaLin on 17/6/9.
 */
public class AddButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        SettingPanel instance = SettingPanel.getInstance();
        int row = instance.jTable.getSelectedRow();
        if(row>=instance.settingTableModel.getDataVector().size()||row<0) row = 0;
        if (row >= 0) {
            String newType = JOptionPane.showInputDialog("设置新标签");
            if(newType==null)return;
            if(newType.trim().length()==0){
                JOptionPane.showMessageDialog(null, "不能为空");
                return;
            }
            Object o[]={newType,0};
            instance.settingTableModel.insertRow(row,o);
            instance.changeTable();
        }
    }
}
