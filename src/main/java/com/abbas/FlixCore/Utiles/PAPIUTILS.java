package com.abbas.FlixCore.Utiles;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PAPIUTILS {
    private static boolean PlaceEnable = false;
    public static void init() {
        PlaceEnable = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
    }
    public static String apply(Player player, String text) {
        if (PlaceEnable && player != null) {
            return PlaceholderAPI.setPlaceholders(player, text);
        }
        return text;
    }
    public static boolean isEnabled() {
        return PlaceEnable;
    }
}
