package v.com.abbas.Vanish;


import com.abbas.FlixCore.FlixCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import v.com.abbas.Vanish.VanishUtils.Util;

public class EventJoin implements Listener {
   private final FlixCore instance;
    private final Util vanishUtil;

    public EventJoin(FlixCore plugin, FlixCore instance, Util vanishUtil) {
        this.instance = instance;
        this.vanishUtil = vanishUtil;
    }

    @EventHandler
    public void OnVanishPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        vanishUtil.JoinServerVanish(p);
    }
}
