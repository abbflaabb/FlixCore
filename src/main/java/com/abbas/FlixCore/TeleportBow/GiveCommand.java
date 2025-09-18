package com.abbas.FlixCore.TeleportBow;

import com.abbas.FlixCore.Utiles.BowUtils;
import com.abbas.FlixCore.api.APICommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand implements APICommands {
    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("Player.TeleportBow")) {
                if (args.length == 0) {
                    ItemStack bow = BowUtils.CreateTeleportBow();
                    player.getInventory().addItem(bow);
                    player.getInventory().addItem(new ItemStack(Material.ARROW, 1));
                    player.sendMessage(ChatColor.GREEN + "You give YourSelf Teleport Bow");
                }else {
                    Player target = Bukkit.getPlayerExact(args[0]);

                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "this Player Not Found");
                        return true;
                    }
                    ItemStack bow = BowUtils.CreateTeleportBow();
                    target.getInventory().addItem(bow);
                    target.getInventory().addItem(new ItemStack(Material.ARROW, 1));
                    target.sendMessage(ChatColor.GREEN + "You Have Add Bow");
                }
            }
        }
        return true;
    }
}
