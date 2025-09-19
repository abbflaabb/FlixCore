package com.abbas.FlixCore.MainCommands;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Farte implements CommandExecutor {
    private final HashMap<UUID, Long> cooldown;

    public Farte() {
        this.cooldown = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            long cooldownTime = 10000;
            if (!this.cooldown.containsKey(p.getUniqueId())) {
                this.cooldown.put(p.getUniqueId(), System.currentTimeMillis());
            } else {long timeElapsed = System.currentTimeMillis() - cooldown.get(p.getUniqueId());
                if (timeElapsed >= cooldownTime) {
                    this.cooldown.put(p.getUniqueId(), System.currentTimeMillis());} else {
                    long timeLeft = (cooldownTime - timeElapsed) / 1000;
                    String cdMessage = FlixCore.getInstance().getMessagesConfig().getString("cooldown-message");
                    if (cdMessage != null) {sender.sendMessage(ChatColor.translateAlternateColorCodes('&', cdMessage.replace("{time}", String.valueOf(timeLeft))));
                    }return true;}
            }if (args.length == 0){
                p.sendMessage(ColorUtils.colorize("&cYou Are so nasty. you have Just farted all over yourSelf."));
                p.setHealth(0.0);
            }else {
                String name =  args[0];
                Player target = Bukkit.getServer().getPlayerExact(name);
                if (target == null) {
                    p.sendMessage(ColorUtils.colorize( "&c[Error] " + ColorUtils.colorize("&7This Player Are Not Online Please.Try Letter")));
                } else {
                    p.sendMessage(ColorUtils.colorize("&bSuccess Farted Your Harts " + target.getDisplayName()));
                    target.sendMessage(ColorUtils.colorize("&2You Have just Been Farted on " + target.getDisplayName() + ". That Make you feel?"));
                    target.setHealth(0.0);}}}
        return true;
    }
}
