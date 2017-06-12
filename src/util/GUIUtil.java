package util;

import frame.MFrame;
import org.jb2011.lnf.beautyeye.BeautyEyeLookAndFeelWin;
import panel.CenterPanel;
import panel.OperatePanel;

import javax.swing.*;

/**
 * Created by KundaLin on 17/5/10.
 */
public class GUIUtil {
    public static void showPanel(JPanel p,double strechRate) {
        GUIUtil.useSkin();
        JFrame f = new JFrame();
        f.setSize(500, 500);
        f.setLocationRelativeTo(null);
        CenterPanel cp = new CenterPanel(strechRate);
        /*
            运行可以看到，窗口出来了，但是我们并没有看到窗口的背景色被设置为了GREEN绿色。这里呢，JFrame并不能说是一个容器，它只是一个框架，那么我们怎么给JFrame设置背景色呢？
            方法一：我们可以使用getContentPane()方法得到JFrame的内容面板，设置该内容面板的背景色即可

            而更常见的一种方法呢，是先新建一个JPanel之类的容器，然后使用setContentPane()方法，
            把该容器设置为JFrame框架的内容面板，然后再在该中间容器上面添加其他组件即可。这里的JPanel就像中间介质，或者我们可以理解为JFrame为画板，
            JPanel之类的中间容器为画布，画布要绑定在画板上，然后我们在画布上面作画。
         */
        f.setContentPane(cp);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        cp.show(p);
    }

    public static void showPanel(JPanel p) {
        showPanel(p,0.85);
    }
    public static void useSkin() {
        try {
            UIManager.setLookAndFeel(new BeautyEyeLookAndFeelWin());
            UIManager.put("RootPane.setupButtonVisible", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkEmpty(JTextField field,String fieldName) {
        String text = field.getText().trim();//trim忽略前后空格
        if(text.length()==0) {
            JOptionPane.showMessageDialog(null,fieldName+"不能为空！");
            field.setText("");
            field.grabFocus();
            return true;
        }
        return false;
    }

    public static boolean checkNumber(JTextField field, String fieldName) {
        String text = field.getText().trim();
        try {
            Integer.parseInt(text);
            return false;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,fieldName+"应为整数！");
            field.setText("");
            field.grabFocus();
            return true;
        }
    }

    public static boolean checkZero(JTextField field, String fieldName) {
        String text = field.getText().trim();
        if(Integer.parseInt(text)<=0){
            JOptionPane.showMessageDialog(null, fieldName + "不能小于零！");
            field.setText("");
            field.grabFocus();
            return true;
        }
        return false;
    }
}
