package com.abbas.myplugin.api;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface APICommands {
    boolean CMD(CommandSender sender, Command command, String label, String[] args);
}
