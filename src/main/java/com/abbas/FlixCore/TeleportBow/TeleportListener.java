package com.abbas.FlixCore.TeleportBow;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.BowUtils;
import com.abbas.FlixCore.Utiles.ColorUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class TeleportListener implements Listener {
    private final FlixCore instance;

    public TeleportListener(FlixCore instance) {
        this.instance = instance;
    }
    @EventHandler
    public void onTeleportBow(ProjectileHitEvent e) {
        if (!(e.getEntity().getShooter() instanceof Player p)) return;
        ItemStack itemInHand = p.getItemInHand();
        if (itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasDisplayName()) {
            String name = itemInHand.getItemMeta().getDisplayName();
            String teleportBowName = this.instance.getTeleportBow().getString("name-bow", "");
            if (name.contains(ColorUtils.colorize(teleportBowName))) {
                p.teleport(e.getEntity().getLocation());
                e.getEntity().remove();
                String teleportMessage = this.instance.getTeleportBow().getString("Teleport-Bow");
                if (teleportMessage != null && !teleportMessage.isEmpty()) {
                    p.sendMessage(ColorUtils.colorize(teleportMessage));
                }
                p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }
        }
    }
}
