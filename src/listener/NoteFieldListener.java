package listener;

import panel.OperatePanel;
import util.GUIUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by KundaLin on 17/6/5.
 */
public class NoteFieldListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        OperatePanel instance = OperatePanel.getInstance();
        if (GUIUtil.checkEmpty(instance.cost, "花费")) {
            return;
        }
        instance.updateSpendToData(Integer.parseInt(instance.cost.getText()));
        instance.addNewRecord();
        instance.updateDataUI();


    }
}
