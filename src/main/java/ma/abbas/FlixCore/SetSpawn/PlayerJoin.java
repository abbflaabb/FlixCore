package ma.abbas.FlixCore.SetSpawn;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private final FlixCore instance;

    public PlayerJoin(FlixCore instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!instance.getMessagesConfig().getBoolean("Settings.Teleport-On-Join", true)) return;
        Location spawn = getSpawnLocation();
        if (spawn == null) {
            player.sendMessage(ColorUtils.colorize("&c[Error] Spawn location not set. Contact an admin."));
            return;
        }
        instance.getServer().getScheduler().runTaskLater(instance, () -> {
            if (player.isOnline()) {
                player.teleport(spawn);
                player.sendMessage(ColorUtils.colorize("&a[Success] Teleported to spawn!"));
            }
        }, 1L);
    }


    private Location getSpawnLocation() {
        if (!instance.getSpawnLoc().contains("spawn.world")) {return null;}
        String worldName = instance.getSpawnLoc().getString("spawn.world");
        double x = instance.getSpawnLoc().getDouble("spawn.x");
        double y = instance.getSpawnLoc().getDouble("spawn.y");
        double z = instance.getSpawnLoc().getDouble("spawn.z");
        float yaw = (float) instance.getSpawnLoc().getDouble("spawn.yaw");
        float pitch = (float) instance.getSpawnLoc().getDouble("spawn.pitch");
        return new Location(instance.getServer().getWorld(worldName), x, y, z, yaw, pitch);
    }
}
