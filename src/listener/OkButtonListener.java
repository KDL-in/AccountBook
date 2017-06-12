package listener;

import dao.BudgetDAO;
import panel.JListPanel;
import panel.OperatePanel;
import panel.SettingPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by KundaLin on 17/6/9.
 */
public class OkButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        SettingPanel instance = SettingPanel.getInstance();
        if (instance.isBudgetChange()) {
            BudgetDAO.alter(instance.getNewBudget());
            OperatePanel.getInstance().readDefaultData();
            //相关更新
            OperatePanel.getInstance().updateUIData();
            instance.budgetSettingField.setText(OperatePanel.getInstance().budget+".00");
        }
        if(instance.isTableChange()){
            instance.updateTempCategorys();
            //相关更新
            OperatePanel.getInstance().updateTempCategorys();
            JListPanel.getInstance().updateTempCagorys();
        }

        instance.cancelButton.doClick();
    }
}
