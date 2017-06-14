package listener;

import dao.BudgetDAO;
import panel.SettingPanel;
import util.GUIUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by KundaLin on 17/6/9.
 */
public class BudgetSettingFieldListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField jTextField = SettingPanel.getInstance().budgetSettingField;
        if (GUIUtil.checkEmpty(jTextField, "预算") || GUIUtil.checkNumber(jTextField, "预算")
                || GUIUtil.checkZero(jTextField, "预算")) {
            jTextField.setText(BudgetDAO.getBudget()+".00");
            return;
        }
        SettingPanel.getInstance().budgetChange(Integer.parseInt(jTextField.getText().trim()));
        SettingPanel.getInstance().applyButton.grabFocus();
    }
}
