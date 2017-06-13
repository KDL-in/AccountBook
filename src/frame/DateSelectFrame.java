package frame;

import dao.RecordsDAO;
import listener.DateOkButtonListener;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import panel.JListPanel;
import util.DateUtil;
import util.FontUtil;
import util.GUIUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class DateSelectFrame extends JFrame {
    private static DateSelectFrame instance;

    private SpinnerNumberModel yearModel = new SpinnerNumberModel(2017, 1, 6000, 1);
    private String[] monthNames = new DateFormatSymbols().getMonths();
    HashMap<String,Integer> monthToNumber;
    private SpinnerListModel monthModel =new SpinnerListModel(Arrays.asList(monthNames).subList(0, 12));

    private JSpinner jSpinnerYear = new JSpinner(yearModel);
    private JSpinner jSpinnerMonth = new JSpinner(monthModel);
    private JButton dateOkButton = new JButton("OK");
    private int maxYear, maxMonth,minYear,minMonth;

    private Date maxDay,minDay;
    public static DateSelectFrame getInstance() {
        if(instance==null)instance = new DateSelectFrame();
        return instance;
    }

    private DateSelectFrame() throws HeadlessException {
        //style
        FontUtil.setFont(FontUtil.yahei15Font, jSpinnerYear, jSpinnerMonth);
        jSpinnerYear.setPreferredSize(new Dimension(75,30));
        jSpinnerMonth.setPreferredSize(new Dimension(75,30));
        dateOkButton.setPreferredSize(new Dimension(75,30));
        dateOkButton.setForeground(Color.white);
        dateOkButton.setFont(FontUtil.yaheiBlod15Font);
        dateOkButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
        //init
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        jPanel.add(jSpinnerYear);
        jPanel.add(jSpinnerMonth);
        jPanel.add(dateOkButton);
        jPanel.setBorder(new EmptyBorder(10,20,10,20));
        setContentPane(jPanel);
        setTitle("设置年份和月份");
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //default data
        readData();
        updateDataUI();
        //add listener
        dateOkButton.addActionListener(new DateOkButtonListener());
        //month to number
        monthToNumber = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            monthToNumber.put(monthNames[i], i);
        }
    }
    public void updateDataUI() {
        yearModel.setValue(maxYear);
        monthModel.setValue(monthNames[maxMonth]);
    }

    private void readData() {
        //read data
        maxDay = RecordsDAO.getLastAdd();
        minDay = RecordsDAO.getFirstAdd();
        maxYear = DateUtil.getYear(maxDay);
        maxMonth = DateUtil.getMonth(maxDay);
        minYear = DateUtil.getYear(minDay);
        minMonth = DateUtil.getMonth(minDay);
    }

    public static void main(String[] args) {
        GUIUtil.useSkin();
        DateSelectFrame dateSelectFrame = DateSelectFrame.getInstance();

    }

    public boolean checkDate() {
        int curYear = (Integer) jSpinnerYear.getValue();
        int curMonth = monthToNumber.get((String) jSpinnerMonth.getValue());
        int cur = curYear*100+curMonth;
        int max = maxYear*100+maxMonth;
        int min = minYear * 100 + minMonth;
        if(cur<=max&&cur>=min) return false;
        return true;

    }

}