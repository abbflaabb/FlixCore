package com.abbas.FlixCore.MainListeners;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.api.StaffChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class StaffChatListener implements Listener {
    private final FlixCore instance;

    public StaffChatListener(FlixCore instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        try {
            Player player = event.getPlayer();

            // Null check for player
            if (player == null) {
                return;
            }

            // Null check for manager
            if (instance.getStaffChatManager() == null) {
                instance.getLogger().severe("StaffChatManager is null in listener!");
                return;
            }

            // Check if player is in staff chat mode
            if (instance.getStaffChatManager().isInStaffChat(player)) {
                event.setCancelled(true);

                // Get the staff chat type for this player
                StaffChat chatType = instance.getStaffChatManager().getStaffChatType(player);
                if (chatType == null) {
                    chatType = StaffChat.getStaffChatType(player);
                }

                // Format and send to staff only
                String message = event.getMessage();
                String formattedMessage = chatType.format(player.getName(), message);

                // Send to all online staff members with permission
                for (Player staff : Bukkit.getOnlinePlayers()) {
                    if (StaffChat.hasAnyPermission(staff)) {
                        staff.sendMessage(formattedMessage);
                    }
                }

                // Send to console
                Bukkit.getConsoleSender().sendMessage(formattedMessage);
            }
        } catch (Exception e) {
            instance.getLogger().severe("Error in StaffChatListener: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        try {
            // Null checks
            if (event.getPlayer() == null || instance.getStaffChatManager() == null) {
                return;
            }

            // Remove player from staff chat when they leave
            instance.getStaffChatManager().removePlayer(event.getPlayer());
        } catch (Exception e) {
            instance.getLogger().severe("Error removing player from staff chat: " + e.getMessage());
        }
    }
}