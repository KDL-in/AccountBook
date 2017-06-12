package listener;

import panel.OperatePanel;
import util.GUIUtil;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by KundaLin on 17/6/5.
 */
public class CostFieldListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField cost = OperatePanel.getInstance().cost;
        //数字检测，错误提示
        if (GUIUtil.checkEmpty(cost,"COST")||GUIUtil.checkNumber(cost,"COST")
                ||GUIUtil.checkZero(cost,"COST"))return;
        OperatePanel.getInstance().note.grabFocus();
    }
}
