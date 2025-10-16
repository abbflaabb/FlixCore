package ma.abbas.FlixCore.SetSpawn;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.api.APICommands;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SetSpawnCommand implements APICommands {
    private final FlixCore instance;
    public SetSpawnCommand(FlixCore instance) {
        this.instance = instance;
    }
    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("FlixCore.setSpawn")) {
                player.sendMessage(ColorUtils.colorize("&c&l[Error] You don’t have permission to use this command."));return true;}
            Location loca = player.getLocation();
            instance.getSpawnLoc().set("spawn.world", loca.getWorld().getName());
            instance.getSpawnLoc().set("spawn.x", loca.getX());
            instance.getSpawnLoc().set("spawn.y", loca.getY());
            instance.getSpawnLoc().set("spawn.z", loca.getZ());
            instance.getSpawnLoc().set("spawn.yaw", loca.getYaw());
            instance.getSpawnLoc().set("spawn.pitch", loca.getPitch());
            try {
                instance.getSpawnLoc().save(new File(instance.getDataFolder(), "Spawn.yml"));
                player.sendMessage(ColorUtils.colorize("&d&l[Success] Spawn has been set successfully!"));
            } catch (IOException e) {
                player.sendMessage(ColorUtils.colorize("&c&l[Error] Could not save Spawn.yml!"));
                e.printStackTrace();
            }
            return true;
        }
        return true;
    }
}
