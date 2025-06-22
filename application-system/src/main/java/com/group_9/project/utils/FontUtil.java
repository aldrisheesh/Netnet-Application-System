package com.group_9.project.utils;

import java.awt.*;
import java.io.InputStream;

public class FontUtil {
    private static Font fntOutfitRegular;
    private static Font fntOutfitBold;
    private static Font fntInterRegular;

    static {
        try {
            InputStream insRegular = FontUtil.class.getClassLoader().getResourceAsStream("fonts/OutfitRegular.ttf");
            InputStream insBold = FontUtil.class.getClassLoader().getResourceAsStream("fonts/OutfitBold.ttf");
            InputStream insInterRegular = FontUtil.class.getClassLoader().getResourceAsStream("fonts/InterRegular.otf");

            if (insRegular == null || insBold == null || insInterRegular == null)
                throw new RuntimeException("Font files not found in Resource folder.");

            fntOutfitRegular = Font.createFont(Font.TRUETYPE_FONT, insRegular);
            fntOutfitBold = Font.createFont(Font.TRUETYPE_FONT, insBold);
            fntInterRegular = Font.createFont(Font.TRUETYPE_FONT, insInterRegular);

            GraphicsEnvironment geEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            geEnv.registerFont(fntOutfitRegular);
            geEnv.registerFont(fntOutfitBold);
            geEnv.registerFont(fntInterRegular);
        } catch (Exception e) {
            e.printStackTrace();
            fntOutfitRegular = new Font("SansSerif", Font.PLAIN, 14); // fallback
            fntOutfitBold = new Font("SansSerif", Font.BOLD, 14);     // fallback
            fntInterRegular = new Font("SansSerif", Font.PLAIN, 14);  // fallback
        }
    }

    public static Font getOutfitFont(float size) {
        return fntOutfitRegular.deriveFont(size);
    }

    public static Font getOutfitBoldFont(float size) {
        return fntOutfitBold.deriveFont(size);
    }

    public static Font getInterFont(float size) {
        return fntInterRegular.deriveFont(size);
    }
}
