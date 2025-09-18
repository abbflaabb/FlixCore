package com.abbas.myplugin;

import com.abbas.myplugin.MainCommands.Commands;
import com.abbas.myplugin.MainCommands.Farte;
import com.abbas.myplugin.MainCommands.Fly.FlyCommand;
import com.abbas.myplugin.MainCommands.HelpCommand;
import com.abbas.myplugin.MainCommands.SupportCommand;
import com.abbas.myplugin.MainListeners.EventListener;
import lombok.Getter;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;


@Getter
public final class MyPlugin extends JavaPlugin {

    @Getter
    private static MyPlugin instance;
    private FileConfiguration messagesConfig;

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("@ MyPlugin has been enabled!");
        PluginManager pm = getServer().getPluginManager();

        saveResource("Messages.yml", false);
        loadMessagesConfig();
        pm.registerEvents(new EventListener(instance), this);
        getCommand("gmc").setExecutor(new Commands());
        getCommand("gms").setExecutor(new Commands());
        getCommand("heal").setExecutor(new Commands());
        getCommand("backBed").setExecutor(new Commands());
        getCommand("help").setExecutor(new HelpCommand());
        getCommand("food").setExecutor(new Commands());
        getCommand("Farte").setExecutor(new Farte());
        getCommand("Menu").setExecutor(new Commands());

        registerCommandsWithAPI();
    }

    private void registerCommandsWithAPI() {
        SupportCommand supportCommand = new SupportCommand();
        FlyCommand flyCommand =  new FlyCommand(instance);
        getCommand("support").setExecutor(supportCommand::CMD);
        getCommand("Fly").setExecutor(flyCommand::CMD);
    }

    @Override
    public void onDisable() {
        getLogger().info("@ MyPlugin has been disabled!");
    }

    public void loadMessagesConfig() {
        File messagesFile = new File(getDataFolder(), "Messages.yml");
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }
}
