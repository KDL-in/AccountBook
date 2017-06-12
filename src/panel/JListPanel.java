package panel;

import dao.CategoryDAO;
import dao.RecordsDAO;
import dao.TempCategorysDAO;
import entity.Category;
import entity.Record;
import entity.TempCategory;
import listener.*;
import util.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by KundaLin on 17/5/19.
 */
public class JListPanel extends JPanel {
    private static JListPanel instance;
    private JLabel backLabel;

    public MyTableModel listTableModel;
    public JTable jTable;
    public JPopupMenu jPopupMenu;
    public JMenuItem deleteItem,noteItem;

    List<Record> recordList;
    public JComboBox jComboBox;

    HashMap<String, Integer> cnameTocid;

    private Object[] namesOfHeader = {"时间", "花费", "类型","编号"};


    public static JListPanel getInstance() {
        if (instance == null)
            instance = new JListPanel();
        return instance;
    }
    private JListPanel() {
        setLayout(new BorderLayout(0, 0));
        setTitlePanel();
        scrollPanel();
        addListener();
    }

    private void addListener() {
        backLabel.addMouseListener(new ListBackLabelListener());
        listTableModel.addTableModelListener(new ListTableModelListener());
        //右键菜单
        jTable.addMouseListener(new RightMouseBottonListener());
        deleteItem.addActionListener(new DeleteItemListener());
        noteItem.addActionListener(new noteItemListener());
        //改变提示后更新提示

    }
    public void change(int rid, int column, Object valueAt) {
        Record newRecord =new Record();
        newRecord.rid = rid;
        switch (column) {
            case 1:
                newRecord.spend = (Integer) valueAt;
                break;
            case 2:
                newRecord.cid = cnameTocid.get(valueAt);
                break;
            case 3:
                newRecord.note =(String)valueAt;
                break;
        }
        RecordsDAO.alter(newRecord);
    }
    private void scrollPanel() {
        //数据
        listTableModel = new MyTableModel(namesOfHeader,0);

        jTable = new JTable(listTableModel);
        updateDayInfoData();
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jScrollPane.setBackground(ColorUtil.BG);
        //样式
        jTable.setDefaultRenderer(Integer.class, new MyRender());//居中
        jTable.setDefaultRenderer(java.sql.Date.class, new MyRender());
        jTable.setDefaultRenderer(String.class, new MyRender());
        JTableHeader jTableHeader = jTable.getTableHeader();//表头
        ((DefaultTableCellRenderer) jTableHeader.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        jTableHeader.setFont(FontUtil.yaheiBlod15Font);
        jTableHeader.setReorderingAllowed(false);//不可拖动不可改变大小
        jTableHeader.setResizingAllowed(false);
        jTableHeader.setOpaque(false);
        jTable.setBackground(ColorUtil.BG);
        jTable.setFont(FontUtil.yahei15Font);//表格字体
        jTable.setRowHeight(40);//单元高度
        jTable.setShowVerticalLines(true);//网格设置
        jTable.setGridColor(Color.white);
        jTable.getColumnModel().getColumn(3).setMaxWidth(0);//隐藏id列
        jTable.getColumnModel().getColumn(3).setMinWidth(0);
        jTable.getColumnModel().getColumn(3).setPreferredWidth(0);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(jScrollPane, BorderLayout.CENTER);
        //右键菜单
        jPopupMenu = new JPopupMenu();
        deleteItem = new JMenuItem("删除");
        noteItem = new JMenuItem("修改备注");
        jPopupMenu.add(deleteItem);
        jPopupMenu.add(noteItem);


    }
    private void setTitlePanel() {//明细标题栏
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backLabel = new JLabel(ImageUtil.backWhiteIcon);
        JLabel titleLabel = new JLabel("明细");
        titlePanel.setBackground(ColorUtil.BORDER);
        FontUtil.setFont(FontUtil.yaheiBlod30Font, titleLabel);
        ColorUtil.setForeColor(Color.white, titleLabel);
        titleLabel.setPreferredSize(new Dimension(100, 100));

        titlePanel.add(backLabel);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
    }
    public void updateDayInfoData() {//更新数据
        recordList = RecordsDAO.getThisMonthList();
        Object[][] data = new Object[recordList.size()][4];

        //cid to cname
        List<Category> typeList = CategoryDAO.getList();
        HashMap<Integer, String> cidTocname = new HashMap<>();
        cnameTocid = new HashMap<>();
        for (Category c :
                typeList) {
            cidTocname.put(c.cid, c.cname);
            cnameTocid.put(c.cname, c.cid);
        }
        listTableModel.getDataVector().clear();
        //数据更新到界面中
        for (int i = recordList.size() - 1; i >= 0; i--) {
            int j = recordList.size() - 1 - i;
            Record r = recordList.get(i);
            data[j][3] = r.rid;
            data[j][2] = cidTocname.get(r.cid);
            data[j][1] = r.spend;
            data[j][0] = r.rdate;
            listTableModel.addRow(data[j]);
        }
//        listTableModel.setDataVector(data,namesOfHeader);
        //类型选择
        jComboBox = new JComboBox();
        updateTempCagorys();

        jComboBox.setFont(FontUtil.yahei15Font);
        TableColumn types = jTable.getColumnModel().getColumn(2);
        types.setCellEditor(new DefaultCellEditor(jComboBox));


    }

    public void updateTempCagorys() {
        List<TempCategory> tempCategories = TempCategorysDAO.getList();
        jComboBox.removeAllItems();
        for (TempCategory t :
                tempCategories) {
            jComboBox.addItem(t.tcname);
        }
    }

}

class MyRender extends DefaultTableCellRenderer {
    //居中，隔行变色，设置提示
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(table.getValueAt(row,column).getClass()==String.class){//提示
            List<Record> r = JListPanel.getInstance().recordList;
            ((JLabel)cell).setToolTipText(r.get(r.size()-1-row).note);
        }
        this.setColor(cell, table, isSelected, hasFocus, row, column);
        setHorizontalAlignment(CENTER);
        return cell;
    }

    private void setColor(Component component, JTable table, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            component.setBackground(ColorUtil.BORDER);
        } else {
            if (row % 2 == 0) {
                component.setBackground(ColorUtil.LIGHTBULE);
            } else {
                component.setBackground(ColorUtil.BG);
            }
        }

    }
}

class MyTableModel extends DefaultTableModel {
    //重写不可编辑
    public MyTableModel() {
        super();
    }

    public MyTableModel(Object[] namesOfHeader, int i) {
        super(namesOfHeader,i);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return getColumnClass(column) != java.sql.Date.class;
    }
}