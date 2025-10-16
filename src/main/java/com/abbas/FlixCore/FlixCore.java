package com.abbas.FlixCore;

import com.abbas.FlixCore.MainCommands.*;
import com.abbas.FlixCore.MainListeners.EventListener;
import com.abbas.FlixCore.MainListeners.PlaceEvent;
import com.abbas.FlixCore.TeleportBow.GiveCommand;
import com.abbas.FlixCore.TeleportBow.TeleportListener;
import com.abbas.FlixCore.TeleportToPlayer.Teleport;
import com.abbas.FlixCore.TeleportToPlayer.TeleportAllForYou;
import com.abbas.FlixCore.Utiles.PAPIUTILS;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import dev.abbas.FlixCore.Scoreboards.ScoreBoardChangeWorld;
import dev.abbas.FlixCore.Scoreboards.ScoreboardListener;
import dev.abbas.FlixCore.Scoreboards.ScoreboardManager;
import lombok.Getter;
import ma.abbas.FlixCore.SetSpawn.SetSpawnCommand;
import ma.abbas.FlixCore.SetSpawn.SpawnCommand;
import me.abbas.FlixCore.tab.TabListener;
import me.abbas.FlixCore.tab.placeholdermain.MainPlaceholders;
import mf.abbas.FlixCore.Jumppads.JumpPadListener;
import mf.abbas.FlixCore.Jumppads.JumpPadModifierListener;
import ms.abbas.FlixCore.broadcast.BroadCastManager;
import ms.abbas.FlixCore.broadcast.CommandBroadCast;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitTask;
import v.com.abbas.Vanish.VanishCommand;

import java.io.File;
import java.util.ArrayList;

@Getter
public final class FlixCore extends JavaPlugin {
    @Getter
    private static FlixCore instance;
    private FileConfiguration messagesConfig;
    private FileConfiguration TeleportBow;
    private FileConfiguration Scoreboard;
    private FileConfiguration SpawnLoc;
    private FileConfiguration TabSettings;
    private ScoreboardManager scoreboardManager;
    ArrayList<Player> invasible_list = new ArrayList<>();
    private LuckPerms luckPerms;
    private ProtocolManager managerpro;
    private TabListener tabListener;
    BukkitTask tabtask;
    private BroadCastManager broadcastManager;

    @Override
    public void onEnable() {
        instance = this;
        luckPerms = LuckPermsProvider.get();
        saveResource("Messages.yml", false);
        saveResource("TeleportBow.yml", false);
        saveResource("Scoreboard.yml", false);
        saveResource("Spawn.yml", false);
        saveResource("TabSettings.yml", false);
        loadMessagesConfig();
        loadTeleportBow();
        loadScoreboard();
        loadSpawnLoc();
        loadTabSettings();
        getCommand("gmc").setExecutor(new Commands());
        getCommand("gms").setExecutor(new Commands());
        getCommand("heal").setExecutor(new Commands());
        getCommand("backBed").setExecutor(new Commands());
        getCommand("help").setExecutor(new HelpCommand(instance));
        getCommand("food").setExecutor(new Commands());
        getCommand("Farte").setExecutor(new Farte());
        getCommand("pp").setExecutor(new Commands());
        getCommand("about").setExecutor(new Commands());
        getCommand("Rank").setExecutor(new RankCommand(instance)::CMD);


        this.scoreboardManager = new ScoreboardManager(this);
        this.scoreboardManager.Updater();
        this.broadcastManager = new BroadCastManager(this);

        getServer().getPluginManager().registerEvents(
                new ScoreboardListener(scoreboardManager), this
        );
        //StartUp
        startupPlugin();
        registerEvents();
        registerCommandsWithAPI();
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            Bukkit.getServer().getLogger().info("ProtocolLib loaded!");
        } else {
            Bukkit.getServer().getLogger().warning("ProtocolLib not found!");
        }
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new MainPlaceholders().register();
            getLogger().info("FlixCore placeholders registered!");
        }
        // Luckperms Loaded
        if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) {
            Bukkit.getServer().getLogger().info("LuckPerms Has Been Loaded");
        } else {
            Bukkit.getServer().getLogger().warning("LuckPerms not Found");
        }

        //Tab with main placeholderapi
        PAPIUTILS.init();
        ProtocolManager managerpro = ProtocolLibrary.getProtocolManager();
        if (TabSettings != null && managerpro != null) {
            tabListener = new TabListener(this, managerpro);
            int tabDelay = TabSettings.getInt("Tab-Settings.Update-Delay", 20);
            tabtask = Bukkit.getScheduler().runTaskTimer(this, tabListener::tick, 0L, tabDelay);
            getLogger().info("✅ TabList enabled successfully!");
        } else {
            getLogger().severe("❌ TabSettings or ProtocolLib missing — TabList disabled!");
        }
    }

    @Override
    public void onDisable(){
        if (tabListener != null) tabListener.clearAll();
        getLogger().info("FlixCore has been disabled safely.");
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
        pluginManager.registerEvents(new ScoreBoardChangeWorld(instance), this);
        pluginManager.registerEvents(new JumpPadListener(), instance);
        pluginManager.registerEvents(new JumpPadModifierListener(), instance);
    }
    private void registerCommandsWithAPI() {
        SupportCommand supportCommand = new SupportCommand(instance);
        FlyCommand flyCommand =  new FlyCommand(instance);
        VanishCommand vanishCommand = new VanishCommand(this);
        Teleport teleport = new Teleport();
        TeleportAllForYou teleportAllForYou = new TeleportAllForYou();
   // For Give TeleportBow Just A test Command.⬇️
        GiveCommand giveCommand =  new GiveCommand(instance);
        CommandBroadCast commandBroadCast = new CommandBroadCast(instance);
        getCommand("broadcast").setExecutor(commandBroadCast::CMD);
        //SpawnCommands;
        SetSpawnCommand setSpawnCommand = new SetSpawnCommand(instance);
        SpawnCommand spawnCommand = new SpawnCommand(instance);
        getCommand("SetSpawn").setExecutor(setSpawnCommand::CMD);
        getCommand("Spawn").setExecutor(spawnCommand::CMD);



        getCommand("support").setExecutor(supportCommand::CMD);
        getCommand("Fly").setExecutor(flyCommand::CMD);
        getCommand("giveT").setExecutor(giveCommand::CMD);
        getCommand("Vanish").setExecutor(vanishCommand::CMD);
        getCommand("Tp").setExecutor(teleport::CMD);
        getCommand("TpAll").setExecutor(teleportAllForYou::CMD);
        ClearChat clearChat = new ClearChat(instance);
        getCommand("ClearChat").setExecutor(clearChat::CMD);
        PingCommand pingCommand = new PingCommand(instance);
        getCommand("Ping").setExecutor(pingCommand::CMD);
        RulesCommand rules = new RulesCommand(instance);
        getCommand("Rules").setExecutor(rules::CMD);
    }
    public void loadMessagesConfig() {
        File messagesFile = new File(getDataFolder(), "Messages.yml");
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }
    private void loadTeleportBow() {
        File fileTele = new File(getDataFolder(), "TeleportBow.yml");
        TeleportBow = YamlConfiguration.loadConfiguration(fileTele);
    }
    private void loadScoreboard() {
        File fileScore = new File(getDataFolder(), "Scoreboard.yml");
        Scoreboard = YamlConfiguration.loadConfiguration(fileScore);
    }
    private void loadSpawnLoc() {
        File Spawnloc = new File(getDataFolder(), "Spawn.yml");
        SpawnLoc = YamlConfiguration.loadConfiguration(Spawnloc);
    }
    public void loadTabSettings() {
        File file = new File(getDataFolder(), "TabSettings.yml");
        if (!file.exists()) saveResource("TabSettings.yml", false);
        TabSettings = YamlConfiguration.loadConfiguration(file);
    }
}
