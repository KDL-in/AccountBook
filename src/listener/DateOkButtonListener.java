package listener;

import frame.DateSelectFrame;
import panel.JChartPanel;
import panel.JListPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DateOkButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DateSelectFrame instance = DateSelectFrame.getInstance();
            int curYear = (Integer) instance.jSpinnerYear.getValue();
            int curMonth = instance.monthToNumber.get(instance.jSpinnerMonth.getValue());
            if (instance.checkDate(curYear,curMonth)) {
                JOptionPane.showMessageDialog(null, "该日期没有数据");
                instance.updateDataUI();
                return;
            }
            //面板更新
            JListPanel.getInstance().updateDataAndUI(curYear, curMonth);
            JChartPanel.getInstance().setChange();
            JChartPanel.getInstance().updateDataUI(curYear,curMonth);

            instance.updateDataUI();//重置月份
            instance.setVisible(false);
        }

    }