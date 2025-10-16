package v.com.abbas.Vanish;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.api.APICommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import v.com.abbas.Vanish.VanishUtils.Util;

import java.util.HashMap;
import java.util.UUID;

public class VanishCommand implements APICommands {
    private final FlixCore instance;
    private final Util vanishUtil;
    private final HashMap<UUID, Long> cooldown = new HashMap<>();
    public VanishCommand(FlixCore instance) {
        this.instance = instance;
        this.vanishUtil = new Util(instance);
    }
    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ColorUtils.colorize("&cOnly players can use this command!"));
            return true;
        }
        if (!sender.hasPermission("Plugin.Admin")) {
            String errorMessage = instance.getMessagesConfig().getString("Error-Commands");
            if (errorMessage != null) {
                sender.sendMessage(ColorUtils.colorize(errorMessage));
            }
            return true;
        }
        UUID uuid = player.getUniqueId();
        long cooldownTime = 10_000;
        long now = System.currentTimeMillis();
        if (cooldown.containsKey(uuid)) {
            long timePassed = now - cooldown.get(uuid);
            if (timePassed < cooldownTime) {
                long timeLeft = (cooldownTime - timePassed) / 1000;
                String cdMessage = instance.getMessagesConfig().getString("cooldown-message");
                if (cdMessage != null) {
                    sender.sendMessage(ColorUtils.colorize(cdMessage.replace("{time}", String.valueOf(timeLeft))));
                }return true;}}cooldown.put(uuid, now);vanishUtil.toggleVanish(player);
        return true;
    }
}
