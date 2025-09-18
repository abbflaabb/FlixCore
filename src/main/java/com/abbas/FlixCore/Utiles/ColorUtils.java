package com.abbas.FlixCore.Utiles;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class ColorUtils {
    public static String colorize(String text) {
        if (text ==null) return "";
        return ChatColor.translateAlternateColorCodes('&',text);
    }

    public static List<String> colorize(List<String> text) {
        return  text.stream()
                .map(ColorUtils::colorize)
                .collect(Collectors.toList());
    }
}

