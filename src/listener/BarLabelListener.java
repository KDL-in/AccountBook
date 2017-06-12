package listener;

import panel.JChartPanel;
import util.ColorUtil;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by KundaLin on 17/5/16.
 */
public class BarLabelListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        JChartPanel.getInstance().isBar = true;
        JChartPanel.getInstance().barLabel.setBackground(ColorUtil.BORDER);
        JChartPanel.getInstance().pieLabel.setBackground(ColorUtil.BG);
        JChartPanel.getInstance().updateChart();

    }
}
