package com.group_9.project;

import java.awt.*;
import java.io.InputStream;

public class FontUtil {
    private static Font outfitRegular;
    private static Font outfitBold;
    private static Font interRegular;

    static {
        try {
            InputStream regularStream = FontUtil.class.getClassLoader().getResourceAsStream("fonts/OutfitRegular.ttf");
            InputStream boldStream = FontUtil.class.getClassLoader().getResourceAsStream("fonts/OutfitBold.ttf");
            InputStream interRegularStream = FontUtil.class.getClassLoader().getResourceAsStream("fonts/InterRegular.otf");

            if (regularStream == null || boldStream == null || interRegularStream == null)
                throw new RuntimeException("Font files not found in Resource folder.");

            outfitRegular = Font.createFont(Font.TRUETYPE_FONT, regularStream);
            outfitBold = Font.createFont(Font.TRUETYPE_FONT, boldStream);
            interRegular = Font.createFont(Font.TRUETYPE_FONT, interRegularStream);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(outfitRegular);
            ge.registerFont(outfitBold);
            ge.registerFont(interRegular);
        } catch (Exception e) {
            e.printStackTrace();
            outfitRegular = new Font("SansSerif", Font.PLAIN, 14); // fallback
            outfitBold = new Font("SansSerif", Font.BOLD, 14);     // fallback
            interRegular = new Font("SansSerif", Font.PLAIN, 14);  // fallback
        }
    }

    public static Font getOutfitFont(float size) {
        return outfitRegular.deriveFont(size);
    }

    public static Font getOutfitBoldFont(float size) {
        return outfitBold.deriveFont(size);
    }

    public static Font getInterFont(float size) {
        return interRegular.deriveFont(size);
    }
}
