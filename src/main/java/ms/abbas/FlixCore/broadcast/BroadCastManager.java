package ms.abbas.FlixCore.broadcast;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.Utiles.PAPIUTILS;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BroadCastManager {
    private final FlixCore instance;
    public BroadCastManager(FlixCore instance) {
        this.instance = instance;
    }

    public void broadcast(String message, BroadCastMessageEvent.BroadCastType type, Player sender) {
        BroadCastMessageEvent event = new BroadCastMessageEvent(sender, message, type);
        instance.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        String formatted = event.getMessage();
        if (sender != null) formatted = PAPIUTILS.apply(sender, formatted);
        formatted = ColorUtils.colorize(getPrefix(type) + formatted);
        for (Player p : instance.getServer().getOnlinePlayers()) {
            p.sendMessage(formatted);
        }
        String prefix = ColorUtils.colorize(instance.getMessagesConfig().getString("Broadcast-Prefix"));
        instance.getLogger().info(prefix + " " + ChatColor.stripColor(formatted));
    }

    private String getPrefix(BroadCastMessageEvent.BroadCastType type) {
        return switch (type) {
            case INFO -> "&7&l[&bInfo&7&l] &f";
            case ALERT -> "&7&l[&cAlert&7&l] &f";
            case ANNOUNCEMENT -> "&7&l[&eAnnouncement&7&l] &f";
        };
    }
}
