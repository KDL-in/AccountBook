package panel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by KundaLin on 17/5/10.
 */
public class CenterPanel extends JPanel {

    private double rate;
    private JComponent c;
    private boolean strech;

    private CenterPanel(double rate, boolean strech) {
        this.setLayout(null);
        this.rate = rate;
        this.strech = strech;
    }

    public CenterPanel(double rate) {
        this(rate,true);
    }

    public void repaint() {
        if (null != c) {
            Dimension contaninerSize = this.getSize();
            Dimension componentSize = c.getPreferredSize();
            /*
               简单讲就是在没有使用layout manager的时候用setSize，在使用了layout manager 的时候用setPreferredSize
               并且setPreferredSize通常和setMinimumSize、setMaximumSize联系起来使用
             */
            if (strech) {
                c.setSize((int)(contaninerSize.width*rate),(int)(contaninerSize.height*rate));
            } else {
                c.setSize(componentSize);
            }
            c.setLocation(contaninerSize.width/2-c.getSize().width/2,contaninerSize.height/2-c.getSize().height/2);
        }
        super.repaint();
    }

    public void show(JComponent p) {
        this.c = p;
        Component[] cs = getComponents();
        for (Component c : cs) {
            remove(c);
        }
        add(p);
        this.updateUI();
    }

/*    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(200, 200);
        f.setLocationRelativeTo(null);
        CenterPanel cp = new CenterPanel(0.8,true);
        f.setContentPane(cp);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        JButton b = new JButton("abc");
        cp.show(b);

    }*/
}
