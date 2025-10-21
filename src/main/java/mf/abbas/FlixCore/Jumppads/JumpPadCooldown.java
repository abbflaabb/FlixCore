package mf.abbas.FlixCore.Jumppads;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class JumpPadCooldown {
    private static final HashMap<UUID, Long> cooldowns = new HashMap<>();

    public static boolean hasCooldown(Player player) {
        if (!cooldowns.containsKey(player.getUniqueId())) return false;
        long last = cooldowns.get(player.getUniqueId());
        return System.currentTimeMillis() - last < 700; // 700ms
    }

    public static void addCooldown(Player player, long timeMs) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    }
}
