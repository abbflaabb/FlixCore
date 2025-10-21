package mf.abbas.FlixCore.Jumppads;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class JumpPadEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private Vector velocity;
    private boolean cancelled;

    public JumpPadEvent(Player player, Vector velocity) {
        this.player = player;
        this.velocity = velocity;
    }

    public Player getPlayer() {
        return player;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
