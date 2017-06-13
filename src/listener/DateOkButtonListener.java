package listener;

import frame.DateSelectFrame;
import panel.JListPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DateOkButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DateSelectFrame instance = DateSelectFrame.getInstance();
            if (instance.checkDate()) {
                JOptionPane.showMessageDialog(null, "该日期没有数据");
                instance.updateDataUI();
                return;
            }
            JListPanel.getInstance().updateDataAndUI();
        }

    }