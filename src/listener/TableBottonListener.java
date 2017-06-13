package listener;

import frame.MFrame;
import panel.JChartPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by KundaLin on 17/5/18.
 */
public class TableBottonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JChartPanel.getInstance().setChange();//面板更新
        JChartPanel.getInstance().updateDataUI();
        MFrame.getInstance().setContentPane(JChartPanel.getInstance());
        MFrame.getInstance().setVisible(true);
    }
}
