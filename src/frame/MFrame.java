package frame;

import panel.JChartPanel;
import panel.MainPanel;
import panel.OperatePanel;
import panel.ProgressBarPanel;
//import util.AddPanel;
import util.DBUtil;
import util.GUIUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Created by KundaLin on 17/5/11.
 */
public class MFrame extends JFrame {
    private static MFrame mFrame;
    private MainPanel mainPanel;

    public static MFrame getInstance() {
        if (mFrame == null) {
            mFrame = new MFrame();
        }
        return mFrame;
    }
    private MFrame() throws HeadlessException {
//        OperatePanel.getInstance().readDefaultData();
    }
//设置主面板
    public void setMainPanel() {
        mainPanel = MainPanel.getInstance();
        MFrame.getInstance().setContentPane(mainPanel);
    }



    public static void main(String[] args) {
        //封装成启动类
//        GUIUtil.useSkin();

    }
}
