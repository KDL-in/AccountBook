package listener;

import frame.MFrame;
import panel.JChartPanel;
import panel.OperatePanel;
import panel.SettingPanel;
import util.ImageUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by KundaLin on 17/6/3.
 */
public class CancelButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        SettingPanel.getInstance().resetAllFlag();
        OperatePanel.getInstance().updateDataUI();
        MFrame.getInstance().setMainPanel();
        MFrame.getInstance().setVisible(true);
    }
}
