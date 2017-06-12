package listener;

import frame.MFrame;
import panel.JChartPanel;
import panel.JListPanel;
import util.ImageUtil;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by KundaLin on 17/6/10.
 */
public class ListBackLabelListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        //撤销选择
        JListPanel.getInstance().jComboBox.setSelectedIndex(JListPanel.getInstance().jComboBox.getSelectedIndex());
        MFrame.getInstance().setMainPanel();
        MFrame.getInstance().setVisible(true);
    }
}
