package listener;

import panel.SettingPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by KundaLin on 17/6/9.
 */
public class ApplyButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        SettingPanel instance = SettingPanel.getInstance();
        if(instance.isBudgetChange()){
            instance.budgetSettingField.setText(instance.getNewBudget()+ ".00");

        }
    }
}
