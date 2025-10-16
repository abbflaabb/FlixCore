package mf.abbas.FlixCore.Jumppads;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;

@Getter
@Setter
public class JumpPadEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private Vector velocity;
    private boolean cancelled;
    public JumpPadEvent(Player player, Vector velocity) {
        this.player = player;
        this.velocity = velocity;
        this.cancelled = false;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
