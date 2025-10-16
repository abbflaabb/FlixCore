package me.abbas.FlixCore.tab;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.Utiles.PAPIUTILS;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class TabListener {

    private final FlixCore instance;
    private final ProtocolManager protocolManager;

    private String header;
    private String footer;
    private boolean papiEnabled;

    public TabListener(FlixCore instance, ProtocolManager pm) {
        this.instance = instance;
        this.protocolManager = pm;
        reload();
    }

    public void reload() {
        FileConfiguration cfg = instance.getTabSettings();
        if (cfg == null) {
            Bukkit.getLogger().warning("[FlixCore] TabSettings.yml is null — using defaults!");
            header = "§aFlixCore!";
            footer = "§7Default Footer";
            papiEnabled = true;
            return;
        }

        List<String> headerFrames = cfg.getStringList("header-frames");
        List<String> footerFrames = cfg.getStringList("footer-frames");

        header = headerFrames.isEmpty() ? "§aFlixCore!" : String.join("\n", headerFrames);
        footer = footerFrames.isEmpty() ? "§7Default Footer" : String.join("\n", footerFrames);

        papiEnabled = cfg.getBoolean("Tab-Settings.holders", cfg.getBoolean("holders", true));

        Bukkit.getLogger().info("[FlixCore] TabList loaded successfully.");
    }

    public void tick() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendTo(player);
        }
    }

    public void sendTo(Player player) {
        String formattedHeader = format(header, player);
        String formattedFooter = format(footer, player);

        try {
            PacketContainer packet = protocolManager.createPacket(com.comphenix.protocol.PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
            packet.getChatComponents().write(0, WrappedChatComponent.fromJson(toJson(formattedHeader)));
            packet.getChatComponents().write(1, WrappedChatComponent.fromJson(toJson(formattedFooter)));
            protocolManager.sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String format(String text, Player player) {
        if (papiEnabled) text = PAPIUTILS.apply(player, text);
        text = ColorUtils.colorize(text);
        return text;
    }

    private String toJson(String text) {
        return "{\"text\":\"" + text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n") + "\"}";
    }

    public void clearAll() {
        try {
            PacketContainer packet = protocolManager.createPacket(com.comphenix.protocol.PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
            packet.getChatComponents().write(0, WrappedChatComponent.fromJson(toJson("")));
            packet.getChatComponents().write(1, WrappedChatComponent.fromJson(toJson("")));
            for (Player player : Bukkit.getOnlinePlayers()) {
                protocolManager.sendServerPacket(player, packet);
            }
        } catch (Exception ignored) {}
    }
}
