package panel;

import dao.CategoryDAO;
import dao.TempCategorysDAO;
import entity.Category;
import entity.TempCategory;
import listener.*;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import util.ColorUtil;
import util.FontUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.File;

/**
 * Created by KundaLin on 17/6/3.
 */
public class SettingPanel extends JPanel {
    private static SettingPanel instance;

    public JTextField budgetSettingField;

    private JButton okButton;
    public JButton applyButton;
    public JButton cancelButton;
    public JTable jTable;

    private JButton addButton, resetButton, deleButton;
    private JButton bakButton, recButton;

    //data
    public DefaultTableModel settingTableModel;
    private int budgetChanged;
    private boolean typesChanged;

    private String currentDirectoryPath = "F:\\学校\\个人理财\\bak";


    private String bakPath;
    private String recPath;

    public static SettingPanel getInstance() {
        if (instance == null) {
            instance = new SettingPanel();
        }
        return instance;
    }

    private SettingPanel() {
        setLayout(new BorderLayout());
        initSettingLabelPanel();
        initCategoryPanel();
        updateUIData();
        addListener();
    }

    public void updateUIData() {
        java.util.List<TempCategory> tempCategoryList = TempCategorysDAO.getList();
        settingTableModel.getDataVector().clear();
        for (TempCategory t :
                tempCategoryList) {
            Object[] objects = {t.tcname, t.delta};
            settingTableModel.addRow(objects);
        }
    }

    private void addListener() {
        budgetSettingField.addActionListener(new BudgetSettingFieldListener());
        applyButton.addActionListener(new ApplyButtonListener());
        okButton.addActionListener(new OkButtonListener());
        addButton.addActionListener(new AddButtonListener());
        deleButton.addActionListener(new DeleteButtonListener());
        resetButton.addActionListener(new ResetButtonListener());
        settingTableModel.addTableModelListener(new SettingTableModelListener());
        bakButton.addActionListener(new BakButtonListener());
        recButton.addActionListener(new RecButtonListener());
    }

    private void initCategoryPanel() {
        JPanel jPanel = new JPanel(new BorderLayout());
        TitledBorder titledBorder = new TitledBorder(new EmptyBorder(20, 35, 20, 35), "类型标签");
        titledBorder.setTitleFont(FontUtil.heiti20Font);
        jPanel.setBorder(titledBorder);

        //分类列表
        Object[] colNames = {"消费类别", "自动增长"};
        settingTableModel = new MySettingTableModel(colNames, 0);
        jTable = new JTable(settingTableModel);
        jTable.setFont(FontUtil.yahei15Font);//表格字体
        jTable.setShowVerticalLines(true);//显示分列线
        JTableHeader jTableHeader = jTable.getTableHeader();//表头设置
        jTableHeader.setResizingAllowed(false);//不可拖动，不可改变大小
        jTableHeader.setReorderingAllowed(false);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ((DefaultTableCellRenderer) jTableHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        jTableHeader.setFont(FontUtil.yahei15Font);

        JScrollPane jScrollPane = new JScrollPane(jTable);
        jTable.setPreferredScrollableViewportSize(new Dimension(300, 270));
//        jTable.getColumn("消费类别").setCellRenderer(new HeaderRenderer());
        //按钮
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("新增");
        resetButton = new JButton("重置");
        deleButton = new JButton("删除");
        FontUtil.setFont(FontUtil.yahei15Font, addButton, resetButton, deleButton);
        buttonPanel.add(addButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(deleButton);
        //add
        jPanel.add(jScrollPane, BorderLayout.CENTER);
        jPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(jPanel, BorderLayout.NORTH);
    }

    private void initSettingLabelPanel() {
        JPanel settingLabelPanel = new JPanel(new BorderLayout());
        TitledBorder titledBorder = new TitledBorder(new EmptyBorder(20, 35, 20, 35), "本月预算");
        titledBorder.setTitleFont(FontUtil.heiti20Font);
        settingLabelPanel.setBorder(titledBorder);
        //预算设置
        JPanel costSettingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel costSettingLabel = new JLabel("本月预算：");
        budgetSettingField = new JTextField(15);
        budgetSettingField.setText("800.00");
        budgetSettingField.setHorizontalAlignment(JTextField.CENTER);
        costSettingPanel.add(costSettingLabel);
        costSettingPanel.add(budgetSettingField);

        budgetSettingField.setBorder(new MatteBorder(0, 0, 2, 0, ColorUtil.BORDER));
        budgetSettingField.setEditable(true);
        budgetSettingField.setBackground(ColorUtil.BG);

        //备份恢复
        JPanel bakAndRecPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 0));
        TitledBorder bakTitledBorder = new TitledBorder(new EmptyBorder(50, -5, 0, 0),
                "备份与还原");
        bakTitledBorder.setTitleFont(FontUtil.heiti20Font);
        bakAndRecPanel.setBorder(bakTitledBorder);
        bakButton = new JButton("备份");
        recButton = new JButton("恢复");
        bakButton.setPreferredSize(new Dimension(125, 30));
        recButton.setPreferredSize(new Dimension(125, 30));
        bakAndRecPanel.add(bakButton);
        bakAndRecPanel.add(recButton);
        //确认取消
        JPanel confirmPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        okButton = new JButton("OK");
        applyButton = new JButton("Apply");
        cancelButton = new JButton("Cancel");
        okButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
        okButton.setForeground(Color.white);
        FontUtil.setFont(FontUtil.yaheiBlod15Font, okButton);
        FontUtil.setFont(FontUtil.yahei15Font, cancelButton, applyButton, bakButton, recButton, costSettingLabel);
        okButton.setPreferredSize(new Dimension(75, 30));
        cancelButton.setPreferredSize(new Dimension(75, 30));
        applyButton.setPreferredSize(new Dimension(75, 30));
        confirmPanel.add(okButton);
        confirmPanel.add(cancelButton);
        confirmPanel.add(applyButton);
        //add listener
        cancelButton.addActionListener(new CancelButtonListener());
        //add
        settingLabelPanel.add(costSettingPanel, BorderLayout.NORTH);
        settingLabelPanel.add(bakAndRecPanel, BorderLayout.CENTER);
        settingLabelPanel.add(confirmPanel, BorderLayout.SOUTH);
        this.add(settingLabelPanel, BorderLayout.CENTER);

    }

    public void updateTempCategorys() {
        TempCategorysDAO.truncate();

        TempCategory newTemCategory = new TempCategory();
        for (int i = 0; i < jTable.getRowCount(); i++) {
            newTemCategory.tcname = (String) jTable.getValueAt(i, 0);
            newTemCategory.delta = Integer.parseInt(jTable.getValueAt(i, 1).toString().trim());
            TempCategorysDAO.add(newTemCategory);
        }
    }

    //控制更新的一些标记
    public boolean isBudgetChange() {
        return budgetChanged > 0;
    }

    public boolean isTableChange() {
        return typesChanged;
    }

    public void resetAllFlag() {
        budgetChanged = 0;
        typesChanged = false;
        bakPath = null;
        recPath = null;
    }

    public void changeTable() {
        typesChanged = true;
    }

    public void budgetChange(int budget) {
        this.budgetChanged = budget;
    }

    public int getNewBudget() {
        return budgetChanged;
    }

    public void resetTempCategory() {
        java.util.List<Category> categories = CategoryDAO.getList();
        settingTableModel.getDataVector().clear();
        for (Category c :
                categories) {
            Object[] objects = {c.cname, 0};
            settingTableModel.addRow(objects);
        }
    }
//备份还原相关
    public void showBakPathChooser() {
        File file = new File(currentDirectoryPath);
        if (!file.exists()) file.mkdir();

        JFileChooser jFileChooser = new JFileChooser(currentDirectoryPath);
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.setDialogTitle("选择备份目录");
        jFileChooser.showDialog(new JLabel(), "选择");
        File bakPathFile = jFileChooser.getSelectedFile();
        bakPath = bakPathFile == null ? null : bakPathFile.getAbsolutePath();

    }

    public boolean isBakPathIllegal() {
        return bakPath == null;
    }

    public String getBakPath() {
        return bakPath;
    }

    public void showRecBakPathChooser() {
        File file = new File(currentDirectoryPath);
        if(!file.exists()) file.mkdir();

        JFileChooser jFileChooser = new JFileChooser(currentDirectoryPath);
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setDialogTitle("选择以前的备份文件(BD.bak)");
        jFileChooser.showDialog(new JLabel(), "选择");
        File recPathFile = jFileChooser.getSelectedFile();
        recPath = recPathFile == null ? null : recPathFile.getAbsolutePath();

    }

    public boolean isRecPathIllegal() {
        if(recPath==null) return true;
        String name = recPath.substring(recPath.length()-6);
        return name.compareTo("DB.bak") != 0;
    }

    public String getRecPath() {
        return recPath;
    }

/*    public static void main(String[] args) {
//        GUIUtil.useSkin();
        GUIUtil.showPanel(new SettingPanel(),1);
    }*/


}

class MySettingTableModel extends DefaultTableModel {
    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 1;
    }

    MySettingTableModel(Object[] colNames, int i) {
        super(colNames, i);
    }
}
