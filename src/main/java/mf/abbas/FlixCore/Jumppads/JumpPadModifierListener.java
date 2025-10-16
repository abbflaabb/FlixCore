package mf.abbas.FlixCore.Jumppads;

import com.abbas.FlixCore.Utiles.ColorUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

public class JumpPadModifierListener implements Listener {
    @EventHandler
    public void onjump(JumpPadEvent event) {
        if (event.getPlayer().hasPermission("Flixcore.JumpMode")) {
            Vector booster =  event.getVelocity().multiply(1.5);
            event.setVelocity(booster);
            event.getPlayer().sendMessage(ColorUtils.colorize("§bVIP Jump Boost!"));
        }
        if (!event.getPlayer().hasPermission("Flixcore.JumpMode")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ColorUtils.colorize("§cYou do not have permission to use the jump pad!"));
        }
        if (event.getPlayer().isSneaking()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ColorUtils.colorize("§cYou cannot use the jump pad while sneaking!"));
        }
    }
}
