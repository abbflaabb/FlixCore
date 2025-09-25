package e.com.abbas.LuckPermsAPI;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Perms {
    private static LuckPerms luckPerms;
    public Perms() {
        this.luckPerms = LuckPermsProvider.get();
    }
    /**
     * Get the primary rank of the player
     */
    public static String getPlayerRank(Player player) {
        UUID uuid = player.getUniqueId();
        User user = luckPerms.getUserManager().getUser(uuid);
        if (user != null) return user.getPrimaryGroup();
        return "default";
    }
    /**
     * Get the prefix of player rank if any
     */
    public static String getPlayerPrefix(Player player) {
        UUID uuid = player.getUniqueId();
        User user = luckPerms.getUserManager().getUser(uuid);
        if (user != null && user.getCachedData().getMetaData().getPrefix() != null) {
            return user.getCachedData().getMetaData().getPrefix();
        }
        return "";
    }
}
