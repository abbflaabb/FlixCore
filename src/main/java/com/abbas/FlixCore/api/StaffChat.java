package com.abbas.FlixCore.api;

import com.abbas.FlixCore.Utiles.ColorUtils;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public enum StaffChat {
    OWNER("&7[&4&lOWNER&7] &c{player} &7: &f{message}", "FlixCore.StaffChat.OWNER"),
    MANAGER("&7[&4&lManager&7] &c{player} &7: &f{message}", "FlixCore.StaffChat.Manager"),
    ADMIN("&7[&4&lAdmin&7] &c{player} &7: &f{message}", "FlixCore.StaffChat.Admin"),
    ADMINJR("&7[&4&lAdmin-JR&7] &c{player} &7: &f{message}", "FlixCore.StaffChat.Admin-JR"),
    STAFF("&7[&b&lSTAFF&7] &c{player} &7: &f{message}", "FlixCore.StaffChat.STAFF");

    private final String format;
    private final String permission;

    StaffChat(String format, String permission) {
        this.format = format;
        this.permission = permission;
    }
    public String format(String name, String msg) {
        return ColorUtils.colorize(format
                .replace("{player}", name)
                .replace("{message}", msg)
        );
    }
    /**
     * get Staff Chat Type.
     */
    public static StaffChat getStaffChatType(Player player) {
        if (player.hasPermission(OWNER.getPermission())) {
            return OWNER;
        }
        if (player.hasPermission(MANAGER.getPermission())) {
            return MANAGER;
        }
        if (player.hasPermission(ADMIN.getPermission())) {
            return ADMIN;
        }
        if (player.hasPermission(ADMINJR.getPermission())) {
            return ADMINJR;
        }
        return STAFF;//default
    }


    /**
     * Check if player has staff chat perms.
     */
    public static boolean hasAnyPermission(Player player) {
        return player.hasPermission(OWNER.getPermission()) ||
                player.hasPermission(MANAGER.getPermission()) ||
                player.hasPermission(ADMIN.getPermission()) ||
                player.hasPermission(ADMINJR.getPermission());
    }
}
