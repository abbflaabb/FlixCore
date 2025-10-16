package ms.abbas.FlixCore.broadcast;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.api.APICommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBroadCast implements APICommands {
    private final FlixCore instance;
    public CommandBroadCast(FlixCore instance) {
        this.instance = instance;
    }
    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("FlixCore.BroadCast")) {
            sender.sendMessage(ColorUtils.colorize(instance.getMessagesConfig().getString("Error-Commands")));
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage("§cUsage: /broadcast <message> [type]");
            sender.sendMessage("§7Types: §bINFO§7, §cALERT§7, §eANNOUNCEMENT");
            return true;
        }
        BroadCastMessageEvent.BroadCastType type = BroadCastMessageEvent.BroadCastType.INFO;
        String lastArg = args[args.length - 1].toUpperCase();
        try {
            type = BroadCastMessageEvent.BroadCastType.valueOf(lastArg);
            String[] msgArgs = new String[args.length - 1];
            System.arraycopy(args, 0, msgArgs, 0, args.length - 1);
            args = msgArgs;
        } catch (IllegalArgumentException ignored) {}
        String message = String.join(" ", args);
        Player player = sender instanceof Player ? (Player) sender : null;
        instance.getBroadcastManager().broadcast(message, type, player);
        return true;
    }
}
