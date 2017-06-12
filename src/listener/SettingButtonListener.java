package listener;

import frame.MFrame;
import panel.JChartPanel;
import panel.SettingPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by KundaLin on 17/6/3.
 */
public class SettingButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        SettingPanel.getInstance().updateUIData();
        MFrame.getInstance().setContentPane(SettingPanel.getInstance());
        MFrame.getInstance().setVisible(true);
    }
}
