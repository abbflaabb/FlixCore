package com.abbas.FlixCore;

import com.abbas.FlixCore.MainCommands.Commands;
import com.abbas.FlixCore.MainCommands.Farte;
import com.abbas.FlixCore.MainCommands.FlyCommand;
import com.abbas.FlixCore.MainCommands.HelpCommand;
import com.abbas.FlixCore.MainCommands.SupportCommand;
import com.abbas.FlixCore.MainListeners.EventListener;
import com.abbas.FlixCore.MainListeners.PlaceEvent;
import com.abbas.FlixCore.TeleportBow.GiveCommand;
import com.abbas.FlixCore.TeleportBow.TeleportListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
@Getter
public final class FlixCore extends JavaPlugin {
    @Getter
    private static FlixCore instance;
    private FileConfiguration messagesConfig;
    private FileConfiguration TeleportBow;

    @Override
    public void onEnable() {
        instance = this;
        saveResource("Messages.yml", false);
        saveResource("TeleportBow.yml", false);

        loadMessagesConfig();
        loadTeleportBow();
        getCommand("gmc").setExecutor(new Commands());
        getCommand("gms").setExecutor(new Commands());
        getCommand("heal").setExecutor(new Commands());
        getCommand("backBed").setExecutor(new Commands());
        getCommand("help").setExecutor(new HelpCommand(instance));
        getCommand("food").setExecutor(new Commands());
        getCommand("Farte").setExecutor(new Farte());
        getCommand("Menu").setExecutor(new Commands());
        getCommand("plugins").setExecutor(new Commands());
        getCommand("about").setExecutor(new Commands());

        //StartUp
        startupPlugin();
        registerEvents();
        registerCommandsWithAPI();
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            Bukkit.getServer().getLogger().info("ProtocolLib loaded!");
        } else {
            Bukkit.getServer().getLogger().warning("ProtocolLib not found!");
        }
    }
    private void startupPlugin() {
        String authors = String.join(", ", instance.getDescription().getAuthors());
        Bukkit.getServer().getLogger().info("----------------------");
        Bukkit.getServer().getLogger().info("Plugin Made By " + authors);
        Bukkit.getServer().getLogger().info("Plugin Version: " + instance.getDescription().getVersion());
        Bukkit.getServer().getLogger().info("Plugin Work Server Version: " + instance.getServer().getVersion());
        Bukkit.getServer().getLogger().info("----------------------");
    }


    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new EventListener(), this);
        pluginManager.registerEvents(new PlaceEvent(instance), this);
        pluginManager.registerEvents(new TeleportListener(instance), this);
    }
    private void registerCommandsWithAPI() {
        SupportCommand supportCommand = new SupportCommand();
        FlyCommand flyCommand =  new FlyCommand(instance);
   // For Give TeleportBow Just A test Command.⬇️
        GiveCommand giveCommand =  new GiveCommand(instance);
        getCommand("support").setExecutor(supportCommand::CMD);
        getCommand("Fly").setExecutor(flyCommand::CMD);
        getCommand("giveT").setExecutor(giveCommand::CMD);
    }
    public void loadMessagesConfig() {
        File messagesFile = new File(getDataFolder(), "Messages.yml");
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }
    private void loadTeleportBow() {
        File fileTele = new File(getDataFolder(), "TeleportBow.yml");
        TeleportBow = YamlConfiguration.loadConfiguration(fileTele);
    }
}
