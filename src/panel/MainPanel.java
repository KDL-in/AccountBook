package panel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by KundaLin on 17/5/14.
 */
public class MainPanel extends JPanel {
    private static MainPanel instance;
    private MainPanel() {
        setLayout(new BorderLayout());
        init();
    }

    public static MainPanel getInstance() {
        if (instance == null) {
            instance = new MainPanel();
        }
        return instance;
    }

    private void init() {
        addProgressBarPanel();
        addInputPanel();
    }
    //输入面板
    private   void addInputPanel() {
        OperatePanel operatePanel = OperatePanel.getInstance();
        add(operatePanel, BorderLayout.SOUTH);
    }
    //进度条面板
    private   void addProgressBarPanel() {
        ProgressBarPanel progressBarPanel = ProgressBarPanel.getInstance();
        add(progressBarPanel,BorderLayout.CENTER);
    }
}
