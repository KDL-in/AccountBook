package panel;

import dao.CategoryDAO;
import dao.RecordsDAO;
import entity.Category;
import entity.Record;
import listener.ChartBackLabelListener;
import listener.BarLabelListener;
import listener.PieLabelListener;
import listener.SettingDataListener;
import org.jfree.chart.*;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import util.ColorUtil;
import util.DateUtil;
import util.FontUtil;
import util.ImageUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

public class JChartPanel extends JPanel {
    public JLabel barLabel, pieLabel, backLabel;
    private static JChartPanel instance;
    public boolean isBar;//标记当前是否显示柱状图
    private boolean isChange;//标记当前数据是否更改
    private JPanel labelPanel;
    private ChartPanel barChartPanel, pieChartPane;
    //chart data
    private HashMap<Integer,String> cidTocname;
    private int[] dayToSpend;

    private HashMap<Integer,Integer> cidTospend;

    private List<Category> typeList;


    public static JChartPanel getInstance() {
        if (instance == null) {
            instance = new JChartPanel();
        }
        return instance;
    }

    public void updateDataUI() {
        Date date = DateUtil.today();
        updateDataUI(DateUtil.getYear(date),DateUtil.getMonth(date));
    }

    public void updateDataUI(int year,int month) {
        if (isChange) {// 判断是否需要更新图表
            readData(year,month);
            setBarChart();
            setPieChart();
            isChange = false;
        }
        if (this.getComponentCount() == 2) {//不断加入组件要把以前的删除才行
            remove(1);
        }
        if (isBar)
            add(barChartPanel, BorderLayout.CENTER);
        else
            add(pieChartPane, BorderLayout.CENTER);
        updateUI();
    }

    private void setPieChart() {
        //设置饼状图面板
        //data
        DefaultPieDataset defaultPieDataset = new DefaultPieDataset();
        for (Category c :
                typeList) {
            if(cidTospend.containsKey(c.cid)&&cidTospend.get(c.cid)>0)
             defaultPieDataset.setValue(cidTocname.get(c.cid),cidTospend.get(c.cid));
        }
        //build chart
        JFreeChart chart = ChartFactory.createPieChart("", defaultPieDataset, true, false, false);
        //图表样式设计
        PiePlot piePlot = (PiePlot) chart.getPlot();
        piePlot.setBackgroundPaint(ColorUtil.BG);
        piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}({2})",
                NumberFormat.getNumberInstance(), new DecimalFormat("0.0%")));//百分比
        piePlot.setBaseSectionOutlinePaint(Color.white);//轮廓颜色
//        piePlot.setSectionOutlineStroke(new BasicStroke(3));
        piePlot.setShadowPaint(ColorUtil.LIGHTBULE);//阴影颜色
        piePlot.setLabelBackgroundPaint(ColorUtil.BG);//以下标签设置
        piePlot.setLabelOutlinePaint(Color.white);
        piePlot.setLabelShadowPaint(ColorUtil.BG);
        piePlot.setLabelFont(FontUtil.yahei13Font);
        piePlot.setLabelPaint(Color.black);
//        piePlot.setLabelOutlineStroke(new BasicStroke(4));
        for(int i = 0; i < typeList.size();i++) {
            Category c = typeList.get(i);
            piePlot.setSectionPaint(cidTocname.get(c.cid),ColorUtil.pieColors[i%7]);
        }
        chart.getLegend().setFrame(new BlockBorder(ColorUtil.BORDER));//图例

        pieChartPane = new ChartPanel(chart);
        pieChartPane.setBorder(new EmptyBorder(0, 0, 35, 1));
    }


    private void setBarChart() {
        //set data
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        int len;
        for(len = dayToSpend.length-1;len>=1;len--)
            if(dayToSpend[len]>0)break;
        for (int i = 1; i <= (len>=15?len:15); i++) {
            dataSet.addValue(dayToSpend[i], "", "" + i);
        }
        //build chart
        JFreeChart chart = ChartFactory.createBarChart("", "", "", dataSet, PlotOrientation.HORIZONTAL, false, false, false);
        //设置样式
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);//背景颜色
        plot.setRangeGridlinePaint(ColorUtil.BORDER);//网格
        plot.setRangeGridlinesVisible(true);
        plot.setOutlineVisible(true);
        plot.setOutlinePaint(ColorUtil.BORDER);
        //柱子渲染
        BarRenderer barRenderer = new BarRenderer();
        barRenderer.setSeriesPaint(0, ColorUtil.BORDER);//color
        barRenderer.setBarPainter(new StandardBarPainter());//高光
        barRenderer.setShadowVisible(false);//阴影
        plot.setRenderer(barRenderer);

        barChartPanel = new ChartPanel(chart);
        barChartPanel.setBorder(new EmptyBorder(0, 20, 30, 20));
    }

    private void readData(int year,int month) {
    //柱状图
        typeList = CategoryDAO.getList();
        List<Record> recordList = RecordsDAO.getListBetweenTheMouth(year,month);
        //cid to cname
        cidTocname = new HashMap<>();
        for (Category c :
                typeList) {
            cidTocname.put(c.cid, c.cname);
        }
        //cid to spend
        cidTospend = new HashMap<>();
        for (Record r :
                recordList) {
            if(cidTospend.get(r.cid)==null) cidTospend.put(r.cid, r.spend);
            else cidTospend.put(r.cid, cidTospend.get(r.cid) + r.spend);
        }
        //住状图
        dayToSpend = new int[32];
        for (Record r :
                recordList) {
            dayToSpend[DateUtil.getDay(r.rdate)] += r.spend;
        }

    }

    private JChartPanel() {
        setChartTheme();//设置图表样式
        setLayout(new BorderLayout());
        setLabelPanel();
        isChange = isBar = true;
        updateDataUI();

    }

    private void setChartTheme() {
        StandardChartTheme chartTheme = new StandardChartTheme("CN");
        chartTheme.setChartBackgroundPaint(ColorUtil.BG);//背景
        chartTheme.setExtraLargeFont(FontUtil.yahei15Font);//标题字体
        chartTheme.setRegularFont(FontUtil.yahei15Font);//图例字体
        chartTheme.setLargeFont(FontUtil.yahei15Font);//轴向字体
        chartTheme.setSmallFont(FontUtil.yahei15Font);

        chartTheme.setPlotOutlinePaint(ColorUtil.BG);//背景边框
        chartTheme.setBarPainter(new StandardBarPainter());//柱状不高光

        ChartFactory.setChartTheme(chartTheme);
    }
    private void setLabelPanel() {
        labelPanel = new JPanel(new BorderLayout());

        backLabel = new JLabel(ImageUtil.backIcon);
        labelPanel.add(backLabel, BorderLayout.WEST);

        labelPanel.setToolTipText("双击设定时间");

        JPanel selectLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        selectLabelPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        barLabel = new JLabel("BAR", SwingConstants.CENTER);
        pieLabel = new JLabel("PIE", SwingConstants.CENTER);
        barLabel.setOpaque(true);//设置背景不透明
        pieLabel.setOpaque(true);
        barLabel.setBackground(ColorUtil.BORDER);
        barLabel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, ColorUtil.BORDER));//设置边框
        pieLabel.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 2, ColorUtil.BORDER));
        FontUtil.setFont(FontUtil.yaheiBlod20Font, barLabel, pieLabel);//设置字体
        ColorUtil.setForeColor(ColorUtil.BG, barLabel, pieLabel);//设置颜色
        addListener();


        barLabel.setPreferredSize(new Dimension(60, 36));//设置按钮大小
        pieLabel.setPreferredSize(new Dimension(60, 36));

        selectLabelPanel.add(barLabel);
        selectLabelPanel.add(pieLabel);
        add(labelPanel, BorderLayout.NORTH);
        labelPanel.add(selectLabelPanel, BorderLayout.CENTER);
    }

    private void addListener() {
        //add listener
        barLabel.addMouseListener(new BarLabelListener());//点击变色
        pieLabel.addMouseListener(new PieLabelListener());
        backLabel.addMouseListener(new ChartBackLabelListener());
        labelPanel.addMouseListener(new SettingDataListener());
    }

    public void setChange() {
        isChange = true;
    }
}
