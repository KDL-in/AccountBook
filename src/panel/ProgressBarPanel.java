package panel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by KundaLin on 17/5/11.
 */
public class ProgressBarPanel extends JPanel {
    private static ProgressBarPanel instance;
    private final int ARC_SIZE_K = 75;//圆的大小相关数k
    private int cw, ch, cx, cy;//圆的长宽,位置
    private Color backgroundColor;
    private Color foregroundColor;
    private int progress;
    private int maximumProgress;
    private int miniMumProgress;

    private int ix, iy;//背景图片右下角位置
    private double rate;//图片放大比
    private double delta;//控制变化增量
    private ImageIcon imageIcon = new ImageIcon("image/bg.jpg");
    private Image image = imageIcon.getImage();
    private String progressText;

    public static ProgressBarPanel getInstance() {
        if(instance==null) instance = new ProgressBarPanel(550, 450);
        return instance;
    }
    private ProgressBarPanel(int width, int height) {
        setLayout(null);
        setPreferredSize(new Dimension(width,height));
        setSize(new Dimension(width, height));

        maximumProgress = 100;
        miniMumProgress = 0;
        backgroundColor = new Color(237,238,239);
        foregroundColor = Color.white;
        //圆相关参数的计算
        ch = cw = this.getHeight() - ARC_SIZE_K * 2;
        cx = (getWidth() - getHeight()) / 2 + ARC_SIZE_K;
        cy = ARC_SIZE_K;
        progressText = "0%";
        //图片相关参数计算
        ix = getWidth();
        iy = getHeight();
        rate = 1;
        delta = -0.0025;
        new Thread(new DynamicScale()).start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //背景图片
        if (image != null) {
            g.drawImage(image, (int) (ix - ix * rate), (int) (iy - iy * rate), (int) (ix * rate), (int) (iy * rate), null);
        }
        //画圆
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//抗锯齿
        graphics2D.setStroke(new BasicStroke(12.0f));//笔画大小
        graphics2D.setColor(backgroundColor);//圆圈未走部分
        graphics2D.drawArc(cx, cy, cw, ch, 0, 360);//圆圈未走部分
        graphics2D.setColor(foregroundColor);
        graphics2D.drawArc(cx, cy, cw, ch, 90, -(int) (360 * (progress * 1.0) / (maximumProgress - miniMumProgress)));
        //百分比
        graphics2D.setFont(new Font("黑体",Font.PLAIN,75));
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int digitalWidth = fontMetrics.stringWidth(progressText);
        int digitalHeight = fontMetrics.getAscent();
        graphics2D.setColor(foregroundColor);
        graphics2D.drawString(progressText,ix/2-digitalWidth/2,iy/2+digitalHeight/2);
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
        repaint();
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        repaint();

    }

    public void setProgress(int progress) {
        if (progress >= miniMumProgress && progress <= maximumProgress) {
            this.progress = progress;
        }
        if (progress > maximumProgress) {
            this.progress = maximumProgress;
        }
        this.progressText = String.valueOf(progress + "%");
        this.repaint();
    }

    private class DynamicScale implements Runnable {
        @Override
        public void run() {
            while(true){
                delta = -delta;
                for(int i = 0; i < 300; i++){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    rate += delta;
                    repaint();
                }
            }
        }
    }
}
