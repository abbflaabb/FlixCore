package com.abbas.FlixCore.MainListeners;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.Utiles.PAPIUTILS;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceEvent implements Listener {

    private final FlixCore instance;

    public PlaceEvent(FlixCore instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        boolean blockPlaceEnabled = instance.getMessagesConfig().getBoolean("Block-Place", true);
        if (blockPlaceEnabled) {
            event.setCancelled(true);
            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0f, 1.0f);
            String blockMessage = instance.getMessagesConfig().getString("Block-Place-Message");
            if (blockMessage != null && !blockMessage.isEmpty()) {
                String message = blockMessage
                        .replace("{buildblock}", event.getBlock().getType().name());
                player.sendMessage(formatMessage(player, message));
            }
        }
    }
    private String formatMessage(Player player, String message) {
        if (message == null || message.isEmpty()) return "";
        message = message.replace("{player}", player.getName());
        message = PAPIUTILS.apply(player, message);
        return ColorUtils.colorize(message);
    }
}
