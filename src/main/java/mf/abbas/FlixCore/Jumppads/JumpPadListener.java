package mf.abbas.FlixCore.Jumppads;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class JumpPadListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Block block = player.getLocation().subtract(0, 1, 0).getBlock();
        if (block.getType() != Material.SLIME_BLOCK) return;
        if (JumpPadCooldown.hasCooldown(player)) return;
        JumpPadCooldown.addCooldown(player, 700);
        Vector direction = player.getLocation().getDirection().normalize();
        double forwardPower = 1.8;
        double upwardPower = 1.0;
        Vector velocity = direction.multiply(forwardPower);
        velocity.setY(upwardPower);
        JumpPadEvent event = new JumpPadEvent(player, velocity);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        player.setVelocity(event.getVelocity());
        player.setFallDistance(0);
        player.playSound(player.getLocation(), "entity.slime.jump", 1.0f, 1.2f);
        player.playSound(player.getLocation(), "block.note_block.pling", 1.0f, 1.8f);
    }
}
