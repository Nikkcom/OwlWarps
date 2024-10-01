package me.nikkcom.owlWarps.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /*


    public static String color(String input) {
        // Count occurrences of the tag "&#"
        String[] strings = input.split("(&#[0-9A-Fa-f]{6})|(&\\{#[0-9A-Fa-f]{6}:#[0-9A-Fa-f]{6}})|(&[0-9a-fA-F])");
        return strings.toString();



        switch (count) {
            case 1: {
                String[] messages = input.split("(?=&)");
                String startTag = messages[1].substring(0, 6);
                String newMessage = input.replace("&#" + startTag, "");

                double r1 = parseRgb(startTag.substring(0, 2));
                double g1 = parseRgb(startTag.substring(2, 4));
                double b1 = parseRgb(startTag.substring(4, 6));

                if (r1 < 0 || g1 < 0 || b1 < 0) return input; // Error handling

                String returnMessage = "";

                returnMessage += ChatColor.of(new Color((int) r1, (int) g1, (int) b1));
                returnMessage += newMessage;

                return returnMessage;
            }
            case 2: {
                String[] messages = input.split("(?=&)");
                String startTag = messages[1].substring(0, 6);
                String endTag = messages[2].substring(0, 6);
                String newMessage = input.replace("&#" + startTag, "").replace("&#" + endTag, "");

                double r1 = parseRgb(startTag.substring(0, 2));
                double g1 = parseRgb(startTag.substring(2, 4));
                double b1 = parseRgb(startTag.substring(4, 6));

                double r2 = parseRgb(endTag.substring(0, 2));
                double g2 = parseRgb(endTag.substring(2, 4));
                double b2 = parseRgb(endTag.substring(4, 6));

                if (r1 < 0 || g1 < 0 || b1 < 0 || r2 < 0 || g2 < 0 || b2 < 0) return input; // Error handling

                double length = newMessage.length();

                double incrementR = (r1 - r2) / length;
                double incrementG = (g1 - g2) / length;
                double incrementB = (b1 - b2) / length;

                StringBuilder returnMessage = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    returnMessage.append(ChatColor.of(new Color((int) r1, (int) g1, (int) b1)));
                    returnMessage.append(newMessage.charAt(i));

                    r1 -= incrementR;
                    g1 -= incrementG;
                    b1 -= incrementB;
                }
                return returnMessage.toString();
            }
            default:
                return ChatColor.translateAlternateColorCodes('&', input);
        }
    }

    private static double parseRgb(String hex) {
        try {
            return Integer.parseInt(hex, 16);
        } catch (NumberFormatException e) {
            return -1; // Error handling
        }
    }*/
}
