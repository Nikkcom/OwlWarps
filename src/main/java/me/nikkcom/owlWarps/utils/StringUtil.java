package me.nikkcom.owlWarps.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.nikkcom.owlWarps.playerwarps.PlayerWarp;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.awt.*;

public class StringUtil {

    /*
    *
    *
    * https://github.com/SpigotMC/BungeeCord/pull/2883#issuecomment-770429978
    * frengor
    * */
    public static String papiColor(final String textToParseAndColor, Player player, String warpName) {
        String parsed = PlaceholderAPI.setPlaceholders(player, textToParseAndColor);
        parsed = parsed.replace("{warp}", warpName);
        String colored = color(parsed);
        return color(colored);
    }

    public static String papiColor(final String textToParseAndColor, Player player) {
        return papiColor(textToParseAndColor, player, null);
    }

    public static String papiColor(final String textToParseAndColor) {
        return papiColor(textToParseAndColor, null);
    }

    public static String color(final String textToTranslate) {
        final char altColorChar = '&';
        final StringBuilder b = new StringBuilder();
        final char[] mess = textToTranslate.toCharArray();
        boolean color = false, hashtag = false, doubleTag = false, gradient = false;
        Color startColor = null, endColor = null;
        int gradientLength = 0, gradientIndex = 0;
        String activeStyles = ""; // Track active text styles for gradient chars.
        char tmp; // Used in loops

        for (int i = 0; i < mess.length; ) {
            final char c = mess[i];

            // Gradient coloring block
            if (gradient) {
                // Interpolate gradient color
                float ratio = (float) gradientIndex / gradientLength;
                Color currentColor = interpolateColor(startColor, endColor, ratio);
                b.append(convertToChatColor(currentColor));  // Add gradient color
                b.append(activeStyles);  // Apply active styles like &l, &o, etc.
                b.append(c);  // Append the actual character
                gradientIndex++;
                i++;
                if (gradientIndex >= gradientLength) gradient = false; // End gradient
                continue;
            }

            // Handle color and style tags
            if (c == '&' && i + 1 < mess.length) {
                final char next = mess[i + 1];

                // Handle gradient tags like &{#ffffff:#000000}
                if (next == '{') {
                    int endTagIndex = textToTranslate.indexOf('}', i);
                    if (endTagIndex > i) {
                        String gradientTag = textToTranslate.substring(i + 2, endTagIndex); // Extract tag
                        String[] colors = gradientTag.split(":");
                        if (colors.length == 2) {
                            try {
                                startColor = Color.decode(colors[0]);
                                endColor = Color.decode(colors[1]);

                                // Calculate how many characters to apply the gradient to
                                int j = endTagIndex + 1;
                                gradientLength = 0;
                                while (j < mess.length) {
                                    char nextChar = mess[j];
                                    if (nextChar == '&' || nextChar == '{') {
                                        break;
                                    }
                                    gradientLength++;
                                    j++;
                                }

                                // Start the gradient application
                                gradientIndex = 0;
                                gradient = true;
                                i = endTagIndex + 1; // Move i to end of tag and start gradient application
                                continue;
                            } catch (NumberFormatException e) {
                                // Invalid hex codes, treat as normal text
                            }
                        }
                    }
                }
                // Handle reset tag &r
                else if (next == 'r') {
                    activeStyles = "";  // Reset active text styles
                    i += 2;  // Move past the &r
                    continue;
                }
                // Handle text styles like &l, &o, etc.
                else if (next == 'l' || next == 'o' || next == 'n' || next == 'm' || next == 'k') {
                    activeStyles += altColorChar + next;  // Append the style to activeStyles
                    i += 2;  // Move past the style tag
                    continue;
                }
                // Handle hex color tags like &#ffffff
                else if (next == '#' && i + 7 < mess.length) {
                    String hexCode = textToTranslate.substring(i + 2, i + 8);  // Get the 6-character hex code
                    try {
                        Color hexColor = Color.decode("#" + hexCode);
                        b.append(convertToChatColor(hexColor));  // Append the hex color in ChatColor format
                        i += 8;  // Move past the hex color tag
                        continue;
                    } catch (NumberFormatException e) {
                        // Invalid hex code, treat as normal text
                    }
                }
                // Handle legacy color tags like &6, &f
                else if ((next >= '0' && next <= '9') || (next >= 'a' && next <= 'f')) {
                    b.append(ChatColor.COLOR_CHAR).append(next);  // Append the legacy color
                    i += 2;  // Move past the color tag
                    continue;
                }
            }

            // Continue processing normal characters
            b.append(c);
            i++;
        }

        return b.toString();
    }

    // Helper function to interpolate colors for gradient
    private static Color interpolateColor(Color start, Color end, float ratio) {
        int red = (int) (start.getRed() + (end.getRed() - start.getRed()) * ratio);
        int green = (int) (start.getGreen() + (end.getGreen() - start.getGreen()) * ratio);
        int blue = (int) (start.getBlue() + (end.getBlue() - start.getBlue()) * ratio);
        return new Color(red, green, blue);
    }

    // Convert a Java Color object to Minecraft's ChatColor format
    private static String convertToChatColor(Color color) {
        StringBuilder hex = new StringBuilder(Integer.toHexString(color.getRGB()).substring(2).toUpperCase());
        return ChatColor.COLOR_CHAR + "x" + ChatColor.COLOR_CHAR + hex.charAt(0) + ChatColor.COLOR_CHAR + hex.charAt(1)
                + ChatColor.COLOR_CHAR + hex.charAt(2) + ChatColor.COLOR_CHAR + hex.charAt(3)
                + ChatColor.COLOR_CHAR + hex.charAt(4) + ChatColor.COLOR_CHAR + hex.charAt(5);
    }
}
