package com.abbas.FlixCore.TeleportBow;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.BowUtils;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.api.APICommands;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand implements APICommands {
    private final FlixCore instance;

    public GiveCommand(FlixCore instance) {
        this.instance = instance;
    }


    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("Player.TeleportBow")) {
                if (args.length == 0) {
                    ItemStack bow = BowUtils.CreateTeleportBow(FlixCore.getInstance());
                    player.getInventory().addItem(bow);
                    player.getInventory().addItem(new ItemStack(Material.ARROW, 1));
                    player.sendMessage(ColorUtils.colorize("&d&lGive To You Bow"));
                }else {
                    Player target = Bukkit.getPlayerExact(args[0]);

                    if (target == null) {
                        player.sendMessage(ColorUtils.colorize(instance.getTeleportBow().getString("Player-NotFound")));
                        return true;
                    }
                    ItemStack bow = BowUtils.CreateTeleportBow(FlixCore.getInstance());
                    target.getInventory().addItem(bow);
                    target.getInventory().addItem(new ItemStack(Material.ARROW, 1));
                    target.sendMessage(ColorUtils.colorize("&d&lGive Teleport Bow"));
                }
            }
        }
        return true;
    }
}
