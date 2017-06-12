package panel;

import dao.BudgetDAO;
import dao.CategoryDAO;
import dao.RecordsDAO;
import dao.TempCategorysDAO;
import entity.Category;
import entity.Record;
import entity.TempCategory;
import listener.*;
import util.ColorUtil;
import util.DBUtil;
import util.DateUtil;
import util.FontUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by KundaLin on 17/5/14.
 */
public class OperatePanel extends JPanel {
    private static OperatePanel instance;

    private JPanel UPanel, DPanel,infoPanel,inputPanel;
    private JLabel costLabel,noteLabel,avgLabel,avg, remainLabel, remain,todayCostLabel,todayCost;
    public JTextField cost,note;
    public JComboBox type;
    public Record readRecord;
    private JButton table ,list ,setting;

    public int budget;
    private int daysToMonthEnd;
    private int thisMonthTotalSpend;
    private int remainBudget;
    private int todayTotalSpend;

    public static OperatePanel getInstance() {
        if(instance==null)instance= new OperatePanel();
        return instance;
    }

    private OperatePanel() {
        initCompents();
        addCompentsListener();
        setCompentsStyle();
        addAllCompents();

        setBorder(new EmptyBorder(10,30,10,30));
        setLayout(new BorderLayout(0,10));
        add(UPanel, BorderLayout.NORTH);
        add(DPanel, BorderLayout.SOUTH);
        //读取数据
        readRecord = new Record();
        readDefaultData();
        updateUIData();//更新UI数据
    }

    public void updateUIData() {
        todayCost.setText(todayTotalSpend + "¥");
        remain.setText(remainBudget+"¥");
        avg.setText((remainBudget / daysToMonthEnd)+"¥");
        ProgressBarPanel.getInstance().setProgress((int)(thisMonthTotalSpend*100.0/budget));
        updateTempCategorys();

    }

    public void updateTempCategorys() {
        java.util.List<TempCategory> typeList = TempCategorysDAO.getList();
        type.removeAllItems();
        for (TempCategory t :
                typeList) {
            type.addItem(t.tcname);
        }
    }

    private void addCompentsListener() {
        table.addActionListener(new TableBottonListener());
        list.addActionListener(new ListBottonListener());
        setting.addActionListener(new SettingButtonListener());
        cost.addActionListener(new CostFieldListener());
        note.addActionListener(new NoteFieldListener());

    }

    private void addAllCompents() {
        inputPanel.add(costLabel);
        inputPanel.add(cost);
        inputPanel.add(noteLabel);
        inputPanel.add(note);
        inputPanel.add(type);
        infoPanel.add(todayCostLabel);
        infoPanel.add(remainLabel);
        infoPanel.add(avgLabel);
        infoPanel.add(todayCost);
        infoPanel.add(remain);
        infoPanel.add(avg);

        UPanel.add(inputPanel, BorderLayout.SOUTH);
        UPanel.add(infoPanel, BorderLayout.NORTH);
//        DPanel.add(pen);
        DPanel.add(table);
        DPanel.add(list);
        DPanel.add(setting);
    }

    private void setCompentsStyle() {
        //消费类型,输入框样式
        TitledBorder titledBorder = new TitledBorder("Today");
        UPanel.setBorder(titledBorder);
        titledBorder.setTitleFont(FontUtil.yaheiBlod15Font);
        FontUtil.setFont(FontUtil.heiti20Font, type, costLabel,noteLabel);
        note.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, ColorUtil.BORDER));
        cost.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, ColorUtil.BORDER));
        note.setBackground(ColorUtil.BG);
        cost.setBackground(ColorUtil.BG);
        FontUtil.setFont(FontUtil.yahei15Font,cost,note);
        //本月消费信息样式
        infoPanel.setBorder(new EmptyBorder(0,50,0,20));
        FontUtil.setFont(FontUtil.heiti15Font,todayCostLabel, remainLabel, avgLabel);
        ColorUtil.setForeColor(ColorUtil.BORDER, todayCost, remain, avg);
        FontUtil.setFont(FontUtil.yahei15Font,todayCost,remain,avg);
        //底部按钮
        table.setPreferredSize(new Dimension(100,50));
        list.setPreferredSize(new Dimension(100,50));
        setting.setPreferredSize(new Dimension(100,50));
        table.setBorder(BorderFactory.createMatteBorder(1,1,1,0, ColorUtil.BORDER));
        list.setBorder(BorderFactory.createMatteBorder(1,0,1,0,ColorUtil.BORDER));
        setting.setBorder(BorderFactory.createMatteBorder(1,0,1,1,ColorUtil.BORDER));


    }

    private void initCompents() {
        costLabel = new JLabel("COST");
        cost = new JTextField(5);
        noteLabel = new JLabel("NOTE");
        note = new JTextField(10);
        type = new JComboBox();
        cost.setHorizontalAlignment(JTextField.CENTER);
        note.setHorizontalAlignment(JTextField.CENTER);
        table = new JButton(new ImageIcon("image/table.png"));
        list = new JButton(new ImageIcon("image/list.png"));
        setting = new JButton(new ImageIcon("image/setting.png"));
        avgLabel = new JLabel("日均可用");
        avg = new JLabel("50¥");
        remainLabel = new JLabel("剩余余额");
        remain = new JLabel("1500¥");
        todayCostLabel = new JLabel("今日消费");
        todayCost = new JLabel("35¥");


        infoPanel = new JPanel(new GridLayout(2,3,5,5));
        inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        UPanel = new JPanel(new BorderLayout());
        DPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
    }

    public void addNewRecord() {
        Record readRecord = instance.readRecord;
        readRecord.spend = Integer.parseInt(instance.cost.getText());
        readRecord.note = instance.note.getText().trim();
        readRecord.note = readRecord.note.length() == 0 ? null : readRecord.note;
        String cname =instance.type.getSelectedItem().toString();
        readRecord.cid = CategoryDAO.InquryCid(cname);
        if (readRecord.cid == -1) {
            Category newCategory = new Category();
            newCategory.cname = cname;
            CategoryDAO.add(newCategory);
            readRecord.cid = CategoryDAO.InquryCid(cname);
        }
        readRecord.rdate = DateUtil.utilToSQL(DateUtil.today());
        RecordsDAO.add(readRecord);
        //添加成功，清空
        instance.cost.setText("");
        instance.note.setText("");
        instance.cost.grabFocus();
    }

    public  void readDefaultData() {//启动时读取默认数据
        budget = BudgetDAO.getBudget();
        daysToMonthEnd = DateUtil.daysToMonthEnd();
        thisMonthTotalSpend = RecordsDAO.getThisMonthTotalSpend();
        remainBudget = budget- thisMonthTotalSpend;
        todayTotalSpend = RecordsDAO.getTodayTotalSpend();
//        System.out.println(todayTotalSpend);
    }

    public  void updateDefaultData(int spend) {//每次记录新消费后更新一些默认数据
        thisMonthTotalSpend += spend;
        remainBudget = budget - thisMonthTotalSpend;
        todayTotalSpend += spend;
    }
}
