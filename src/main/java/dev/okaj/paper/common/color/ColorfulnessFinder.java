package dev.okaj.paper.common.color;

import org.bukkit.Color;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ColorfulnessFinder {

    public static Color @NonNull [] getTwoSignalColors(Color @NonNull [] colors) {
        // List to hold colorfulness scores
        List<ColorScore> colorScores = new ArrayList<>();

        // Calculate colorfulness for each color
        for (Color color : colors) {
            float[] hsl = rgbToHsl(color.getRed(), color.getGreen(), color.getBlue());
            float colorfulness = hsl[1] * hsl[2]; // Saturation * Lightness
            colorScores.add(new ColorScore(color, colorfulness));
        }

        // Sort colors by colorfulness in descending order
        colorScores.sort(Comparator.comparingDouble(ColorScore::getColorfulness).reversed());

        // Get the two most colorful colors
        ColorScore mostColorful = colorScores.get(0);
        ColorScore secondMostColorful = colorScores.get(1);

        Color[] result = new Color[2];
        result[0] = mostColorful.color;
        result[1] = secondMostColorful.color;
        return result;
    }

    private static float @NonNull [] rgbToHsl(int r, int g, int b) {
        float rNorm = r / 255f;
        float gNorm = g / 255f;
        float bNorm = b / 255f;

        float max = Math.max(rNorm, Math.max(gNorm, bNorm));
        float min = Math.min(rNorm, Math.min(gNorm, bNorm));
        float h, s, l = (max + min) / 2;

        if (max == min) {
            h = s = 0; // achromatic
        } else {
            float d = max - min;
            s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
            if (max == rNorm) {
                h = (gNorm - bNorm) / d + (gNorm < bNorm ? 6 : 0);
            } else if (max == gNorm) {
                h = (bNorm - rNorm) / d + 2;
            } else {
                h = (rNorm - gNorm) / d + 4;
            }
            h /= 6;
        }

        return new float[]{h, s, l};
    }
}
