package listener;

import frame.MFrame;
import panel.JChartPanel;
import util.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by KundaLin on 17/5/16.
 */
public class PieLabelListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        JChartPanel.getInstance().isBar = false;
        JChartPanel.getInstance().pieLabel.setBackground(ColorUtil.BORDER);
        JChartPanel.getInstance().barLabel.setBackground(ColorUtil.BG);
        JChartPanel.getInstance().updateChart();
    }
}
