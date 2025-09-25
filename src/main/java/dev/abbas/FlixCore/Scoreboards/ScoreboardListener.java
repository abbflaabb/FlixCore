package dev.abbas.FlixCore.Scoreboards;

import com.abbas.FlixCore.Utiles.ColorUtils;
import net.Abbas.FlixCore.ActionBar.ActionBarUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
public class ScoreboardListener implements Listener {

    private final ScoreboardManager scoreboardManager;
    public ScoreboardListener(ScoreboardManager scoreboardManager) {
        this.scoreboardManager = scoreboardManager;
    }
    /**
     *  {rank}
     *  Initialize ScoreBoardFlixCore For Player Join
     */
    @EventHandler
    public void OnPlayerJoinScoreBoard(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        scoreboardManager.updateScoreboard(player);
        ActionBarUtil.sendActionBar(player, ColorUtils.colorize("&d&lWelcome for Plugin"));
    }
}
