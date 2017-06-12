package listener;

import panel.SettingPanel;
import util.DBUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by KundaLin on 17/6/10.
 */
public class RecButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        SettingPanel instance = SettingPanel.getInstance();
        instance.showRecBakPathChooser();
        if (instance.isRecPathIllegal()) {
            JOptionPane.showMessageDialog(null, "不是备份文件");
            return;
        }
        DBUtil.recovery(instance.getRecPath());
        //更新界面数据
        instance.updateUIData();
        instance.changeTable();
    }
}
