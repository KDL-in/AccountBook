package util;

import javax.swing.*;
import java.awt.*;

/**
 * Created by KundaLin on 17/5/14.
 */
public class ColorUtil {
    public static Color BORDER = new Color(51, 153, 255);
    public static Color BG = new Color(250,250,250);
    public static Color BLUE = new Color(68, 125, 215);
    public static Color LIGHTBULE = new Color(240, 244, 255);

    public static void setForeColor(Color color, JComponent... jComponents) {
        for (JComponent component :
                jComponents) {
            component.setForeground(color);
        }
    }
}
