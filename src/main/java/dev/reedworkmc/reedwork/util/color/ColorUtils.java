package dev.reedworkmc.reedwork.util.color;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

public class ColorUtils {

    @Deprecated
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    @Deprecated
    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

    public static Component generateGradientText(@NotNull String txt, @NotNull Color c1, @NotNull Color c2) {
        // Convert the Color objects to TextColor
        TextColor startColor = TextColor.color(c1.getRed(), c1.getGreen(), c1.getBlue());
        TextColor endColor = TextColor.color(c2.getRed(), c2.getGreen(), c2.getBlue());

        // Create a list to hold the individual characters with gradient colors
        Component gradientText = Component.text("");

        // Calculate the number of characters in the text
        int length = txt.length();

        // Generate the gradient text
        for (int i = 0; i < length; i++) {
            // Calculate the interpolation factor
            float ratio = (float) i / (length - 1);

            // Interpolate between the start and end colors
            TextColor interpolatedColor = TextColor.lerp(ratio, startColor, endColor);

            // Create a component for the current character with the interpolated color
            Component characterComponent = Component.text(txt.charAt(i)).color(interpolatedColor);

            // Add the character component to the list
            gradientText = gradientText.append(characterComponent);
        }
        return gradientText;
    }

    @Deprecated
    public static @NotNull URI getPlayerUrl(@NotNull Player player) {
        return URI.create("https://sessionserver.mojang.com/session/minecraft/profile/" + player.getUniqueId());
    }

    @Deprecated
    public static @Nullable BufferedImage getPlayerTexture(@NotNull Player player, JavaPlugin plugin) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(getPlayerUrl(player))
                    .GET()
                    .build();

            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200)
                return null;

            JsonObject json = JsonParser.parseString(response.body())
                    .getAsJsonObject();

            String img_str = json.getAsJsonArray("properties")
                    .get(0)
                    .getAsJsonObject()
                    .get("value")
                    .getAsString();

            String textureJsonString = new String(BASE64_DECODER.decode(img_str), StandardCharsets.UTF_8);
            String skin_texture_url = JsonParser.parseString(textureJsonString)
                    .getAsJsonObject()
                    .getAsJsonObject("textures")
                    .getAsJsonObject("SKIN")
                    .get("url")
                    .getAsString();

            return ImageIO.read(URI.create(skin_texture_url).toURL());

        } catch (IOException | InterruptedException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed loading player texture", e);
        }

        return null;
    }

    public static Color @NotNull [] extractTwoColors(@NotNull BufferedImage image) {
        Set<Color> colors = new HashSet<>();

        // Iterate through the pixels of the image
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixel = image.getRGB(x, y);
                Color color = Color.fromARGB(pixel);
                // Filter out transparent pixels
                int rgb_sum = color.getRed() + color.getGreen() + color.getBlue();
                if (color.getAlpha() != 0 && rgb_sum > 200 && rgb_sum < 400) {
                    colors.add(color);
                }
            }
        }

        // Check if we found exactly two colors
        if (colors.size() < 2) {
            Random r = new Random();
            for (int i = 0; i < 5; i++) {
                Color rand_color = Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256));
                colors.add(rand_color);
            }
        }

        // Convert the set to an array and return exactly two colors
        return ColorfulnessFinder.getTwoSignalColors(colors.toArray(Color[]::new));
    }

    public static @NotNull String colorToHex(@NotNull Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    public static @NotNull String colorToHex(java.awt.@NotNull Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    public static @NotNull Color randomColor() {
        Random r = new Random();
        return Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256));
    }
}
