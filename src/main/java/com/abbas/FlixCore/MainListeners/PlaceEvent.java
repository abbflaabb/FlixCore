package com.abbas.FlixCore.MainListeners;

import com.abbas.FlixCore.FlixCore;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceEvent implements Listener {
    private final FlixCore instance;
    public PlaceEvent() {
        this.instance = new FlixCore();

    }
    @EventHandler
    public void OnPlace(BlockPlaceEvent event ) {
        Player player = event.getPlayer();
        boolean BlockPlace = instance.getMessagesConfig().getBoolean("Block-Place", true);
        if (BlockPlace) {
            event.setCancelled(true);
            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0f, 1.0f);
            String BlockMessage = instance.getMessagesConfig().getString("Block-Place-Message");
            if (BlockMessage != null && !BlockMessage.isEmpty()) {
                String Message = BlockMessage.replace("{Player}", player.getName()).replace("{BuidlBlock}", event.getBlock().toString());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Message));
            }
        }
    }
}
