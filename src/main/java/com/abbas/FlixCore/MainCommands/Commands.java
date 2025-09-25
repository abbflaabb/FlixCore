package com.abbas.FlixCore.MainCommands;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import e.com.abbas.LuckPermsAPI.Perms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class Commands implements CommandExecutor {
    private final HashMap<UUID, Long> cooldown;
    public Commands() {
        this.cooldown = new HashMap<>();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("Plugin.Admin")) {
            sender.sendMessage(ColorUtils.colorize("&cYou Not Allowed Use This Command"));
            return true;
        }
        if (sender instanceof Player p) {
            long cooldownTime = 10000;
            if (!this.cooldown.containsKey(p.getUniqueId())) {
                this.cooldown.put(p.getUniqueId(), System.currentTimeMillis());
            } else {
                long timeElapsed = System.currentTimeMillis() - cooldown.get(p.getUniqueId());
                if (timeElapsed >= cooldownTime) {
                    this.cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                } else {
                    long timeLeft = (cooldownTime - timeElapsed) / 1000;
                    String cdMessage = FlixCore.getInstance().getMessagesConfig().getString("cooldown-message");
                    if (cdMessage != null) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', cdMessage.replace("{time}", String.valueOf(timeLeft))));
                    }return true;
                }
            }
            if (command.getName().equalsIgnoreCase("gmc")) {
                p.setGameMode(org.bukkit.GameMode.CREATIVE);
                p.sendMessage(ColorUtils.colorize("&a&lYour Game Mode Has Been Changed To Creative"));}
            if (command.getName().equalsIgnoreCase("gms")) {
                p.setGameMode(org.bukkit.GameMode.SURVIVAL);
                p.sendMessage(ColorUtils.colorize("&a&lYour Game Mode Has Been Changed To Survival"));}
            if (command.getName().equalsIgnoreCase("heal")) {
                p.setHealth(20);
                p.sendMessage(ColorUtils.colorize("&a&lYou Have Been Healed"));}
            if (command.getName().equalsIgnoreCase("backBed")) {
                if (p.getBedSpawnLocation() != null) {
                    p.teleport(p.getBedSpawnLocation());
                    p.sendMessage(ColorUtils.colorize("&a&lTeleported to your bed spawn location."));
                } else {p.sendMessage(ColorUtils.colorize("&c&lYou don't have a bed spawn location set."));}}
            if (command.getName().equalsIgnoreCase("food")) {
                p.setFoodLevel(20);
                p.sendMessage(ColorUtils.colorize("&a&lChange Food Level to Max"));
            }
            //ForShow PluginsServer
            if (command.getName().equalsIgnoreCase("pp")) {
                Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
                StringBuilder listPluginsInServer =
                        new StringBuilder(ColorUtils.colorize("&d&lPlugins: "));
                for (Plugin plugin : plugins) {
                    listPluginsInServer.append(plugin.getName()).append(", ");
                }
                if (!listPluginsInServer.isEmpty()) listPluginsInServer.setLength(listPluginsInServer.length() -2 );
                p.sendMessage(listPluginsInServer.toString());
            }
            //New Design For About Server
            if (command.getName().equalsIgnoreCase("about")) {
                p.sendMessage(ColorUtils.colorize("&d&lVersion: ") + Bukkit.getVersion());
                p.sendMessage(ColorUtils.colorize("&c&lPlugins: "));
                for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                    p.sendMessage(ColorUtils.colorize("&d&l - " + plugin.getName() + " v" + plugin.getDescription().getVersion()));
                }
                return true;
            }
        if (command.getName().equalsIgnoreCase("Rank")) {
            String rank = Perms.getPlayerRank(p);
            p.sendMessage(ColorUtils.colorize("&a&lYour Rank is:" + rank));
            return true;
        } else {
            sender.sendMessage(ColorUtils.colorize("&c&lCommand executed by player"));
        }
        if (command.getName().equalsIgnoreCase("menu")) {
            String menuTitle = FlixCore.getInstance().getMessagesConfig().getString("Menu-Title");
            String TitleColor = ChatColor.translateAlternateColorCodes('&', menuTitle);
            Inventory inventory = Bukkit.createInventory(p, 9, TitleColor);
            ItemStack bed = new ItemStack(Material.BED);
            ItemMeta bedMeta = bed.getItemMeta();
            bedMeta.setDisplayName(ColorUtils.colorize("&f&lBed") + ColorUtils.colorize("&c&lWars"));
            bedMeta.setLore(List.of(
                    ColorUtils.colorize("&9Protect this bed at all costs!"),
                    ColorUtils.colorize("&7&lYour team respawns as long as this bed is intact."),
                    ColorUtils.colorize("&e&lDestroy enemy beds to win the game!")));
            bed.setItemMeta(bedMeta);
            inventory.setItem(4, bed);
            p.openInventory(inventory); return true;}
        } else if (sender instanceof ConsoleCommandSender) {sender.sendMessage(ColorUtils.colorize("&c&lYou cant use command in Console"));}return true;
    }
}