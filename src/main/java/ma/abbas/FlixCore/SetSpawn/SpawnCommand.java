package ma.abbas.FlixCore.SetSpawn;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.api.APICommands;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements APICommands {
    private final FlixCore instance;
    public SpawnCommand(FlixCore instance) {
        this.instance = instance;
    }
    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ColorUtils.colorize("&c[Error],You Dont Have Perms"));return true;}
        Location Loc = getSpawnLocation();
        if (Loc == null) {player.sendMessage(ColorUtils.colorize("&a[Error],Spawn Dont Set Please Talk for admin for setspawn"));return true;}
        player.teleport(Loc);
        player.sendMessage(ColorUtils.colorize("&a[Success]Teleported to spawn"));
        return true;
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
