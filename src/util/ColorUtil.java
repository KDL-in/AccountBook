package util;

import javax.swing.*;
import java.awt.*;

/**
 * Created by KundaLin on 17/5/14.
 */
public class ColorUtil {
    public static Color BORDER = new Color(51, 153, 255);
    public static Color BG = new Color(250,250,250);
    public static Color LIGHTBULE = new Color(240, 244, 255);

    public static Color[] pieColors = {
            new Color(253, 236, 109),
            new Color(153, 158, 255),
            new Color(144, 237, 125),
            new Color(255, 188, 117),
            new Color(31, 129, 188),
            new Color(158, 90, 102),
            new Color(92, 92, 97),
    };

    public static void setForeColor(Color color, JComponent... jComponents) {
        for (JComponent component :
                jComponents) {
            component.setForeground(color);
        }
    }
}
