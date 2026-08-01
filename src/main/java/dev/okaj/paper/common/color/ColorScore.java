package dev.okaj.paper.common.color;

import org.bukkit.Color;

public class ColorScore {
    Color color;
    float colorfulness;

    ColorScore(Color color, float colorfulness) {
        this.color = color;
        this.colorfulness = colorfulness;
    }

    public float getColorfulness() {
        return colorfulness;
    }
}
