package listener;

import frame.MFrame;
import panel.JChartPanel;
import util.ImageUtil;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by KundaLin on 17/5/19.
 */
public class ChartBackLabelListener extends MouseAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        JChartPanel.getInstance().backLabel.setIcon(ImageUtil.backPressedIcon);
        JChartPanel.getInstance().updateUI();


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        MFrame.getInstance().setMainPanel();
        JChartPanel.getInstance().backLabel.setIcon(ImageUtil.backIcon);
        MFrame.getInstance().setVisible(true);
    }
}
