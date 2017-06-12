package listener;

import panel.JListPanel;
import panel.OperatePanel;
import util.DBUtil;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * Created by KundaLin on 17/6/8.
 */
public class ListTableModelListener implements TableModelListener {
    @Override
    public void tableChanged(TableModelEvent e) {
        if(e.getColumn()==-1)return;//读取数据的时候会调用，要避免，此时为-1
        int row = e.getFirstRow();
        int rid = (Integer) JListPanel.getInstance().jTable.getValueAt(row, 3);
        JListPanel.getInstance().change(rid, e.getColumn(),JListPanel.getInstance().jTable.getValueAt(row, e.getColumn()));
        OperatePanel.getInstance().readDefaultData();
        OperatePanel.getInstance().updateUIData();
    }
}
