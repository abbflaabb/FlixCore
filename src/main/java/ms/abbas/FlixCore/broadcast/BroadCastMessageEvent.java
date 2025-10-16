package ms.abbas.FlixCore.broadcast;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BroadCastMessageEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled;
    @Getter
    @Setter
    private String message;
    @Getter
    private final Player sender;
    @Getter
    private final BroadCastType type;

    public BroadCastMessageEvent(Player sender, String message, BroadCastType type) {
        this.sender = sender;
        this.message = message;
        this.type = type;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public enum BroadCastType {
        INFO,
        ALERT,
        ANNOUNCEMENT
    }
}
