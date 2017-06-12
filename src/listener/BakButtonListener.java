package listener;

import panel.SettingPanel;
import util.DBUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by KundaLin on 17/6/10.
 */
public class BakButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        SettingPanel instance = SettingPanel.getInstance();
        instance.showBakPathChooser();
        if (instance.isBakPathIllegal()) {
            JOptionPane.showMessageDialog(null, "未选择目录");
            return;
        }
        DBUtil.backup(instance.getBakPath());
    }
}
