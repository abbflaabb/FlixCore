package com.abbas.FlixCore.manager;

import com.abbas.FlixCore.api.StaffChat;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StaffChatManager {
    private final Map<UUID, StaffChat> staffChatPlayers;

    public StaffChatManager() {
        this.staffChatPlayers = new HashMap<>();
    }

    /**
     * Toggle staff chat mode for a player
     * @param player The player to toggle
     * @return true if player is now in staff chat, false if they left
     */
    public boolean toggleStaffChat(Player player) {
        if (player == null) {
            return false;
        }

        UUID uuid = player.getUniqueId();
        if (staffChatPlayers.containsKey(uuid)) {
            staffChatPlayers.remove(uuid);
            return false;
        } else {
            StaffChat type = StaffChat.getStaffChatType(player);
            staffChatPlayers.put(uuid, type);
            return true;
        }
    }

    /**
     * Check if a player is in staff chat mode
     * @param player The player to check
     * @return true if player is in staff chat mode
     */
    public boolean isInStaffChat(Player player) {
        if (player == null || staffChatPlayers == null) {
            return false;
        }
        return staffChatPlayers.containsKey(player.getUniqueId());
    }

    /**
     * Get the staff chat type for a player
     * @param player The player to check
     * @return The StaffChat type, or null if not in staff chat
     */
    public StaffChat getStaffChatType(Player player) {
        if (player == null || staffChatPlayers == null) {
            return null;
        }
        return staffChatPlayers.get(player.getUniqueId());
    }

    /**
     * Remove a player from staff chat
     * @param player The player to remove
     */
    public void removePlayer(Player player) {
        if (player != null && staffChatPlayers != null) {
            staffChatPlayers.remove(player.getUniqueId());
        }
    }

    /**
     * Clear all players from staff chat
     */
    public void clearAll() {
        if (staffChatPlayers != null) {
            staffChatPlayers.clear();
        }
    }

    /**
     * Get the number of players in staff chat
     * @return The count of players in staff chat mode
     */
    public int getStaffChatCount() {
        if (staffChatPlayers == null) {
            return 0;
        }
        return staffChatPlayers.size();
    }
}