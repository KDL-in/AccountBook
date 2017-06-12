package util;

import javax.swing.*;
import java.awt.*;

/**
 * Created by KundaLin on 17/5/14.
 */
public class FontUtil {

    public static Font heiti20Font = new Font("黑体", Font.PLAIN, 20);
    public static Font heiti15Font = new Font("黑体", Font.PLAIN, 15);
    public static Font yahei15Font = new Font("Microsoft YaHei", Font.PLAIN, 15);
    public static Font yaheiBlod15Font = new Font("Microsoft YaHei", Font.BOLD, 15);
    public static Font yaheiBlod30Font = new Font("Microsoft YaHei", Font.BOLD, 30);

    public static void setFont(Font font, JComponent...jComponents ) {
        for (JComponent p :
                jComponents) {
            p.setFont(font);
        }
    }
}
