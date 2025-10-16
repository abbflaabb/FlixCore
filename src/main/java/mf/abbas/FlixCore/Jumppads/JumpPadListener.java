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
    public void onJumpPad(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        Block block =  p.getLocation().subtract(0,1,0).getBlock();
        if (block.getType() == Material.SLIME_BLOCK) {
            Vector velocity = new Vector(0,1.5,0);
            JumpPadEvent e = new JumpPadEvent(p,velocity);
            Bukkit.getPluginManager().callEvent(e);
            if (e.isCancelled()) return;
            p.setVelocity(e.getVelocity());
            p.playSound(p.getLocation(), "entity.slime.jump", 1f, 1f);
        }
    }

}
