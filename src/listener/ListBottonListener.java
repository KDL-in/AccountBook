package listener;

import frame.MFrame;
import panel.JChartPanel;
import panel.JListPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by KundaLin on 17/6/2.
 */
public class ListBottonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JListPanel.getInstance().updateDayInfoData();
        MFrame.getInstance().setContentPane(JListPanel.getInstance());
        MFrame.getInstance().setVisible(true);
    }
}
