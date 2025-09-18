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
import org.bukkit.ChatColor;
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
        //StartUp
        startupPlugin();
        registerEvents();
        registerCommandsWithAPI();
    }
    private void startupPlugin() {
        String authors = String.join(", ", instance.getDescription().getAuthors());
        Bukkit.getServer().getLogger().info(
                ChatColor.BLUE + "----------------------\n" +
                        ChatColor.RED + "Plugin Made By " + authors + "\n" +
                        ChatColor.AQUA + "Plugin Version: " + instance.getDescription().getVersion() + "\n" +
                        ChatColor.LIGHT_PURPLE + "Plugin Work Server Version: " + instance.getServer().getVersion() + "\n" +
                        ChatColor.BLUE + "----------------------"
        );
    }
    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new EventListener(instance), this);
        pluginManager.registerEvents(new PlaceEvent(), this);
        pluginManager.registerEvents(new TeleportListener(instance), this);
    }
    private void registerCommandsWithAPI() {
        SupportCommand supportCommand = new SupportCommand();
        FlyCommand flyCommand =  new FlyCommand(instance);
   // For Give TeleportBow Just A test Command.⬇️
        GiveCommand giveCommand =  new GiveCommand();
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
