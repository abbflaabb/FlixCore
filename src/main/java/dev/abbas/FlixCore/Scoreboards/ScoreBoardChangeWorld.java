package dev.abbas.FlixCore.Scoreboards;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreBoardChangeWorld implements Listener {
    private final FlixCore instance;
    public ScoreBoardChangeWorld( FlixCore instance) {
        this.instance = instance;
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }

    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent event) {
        Player p = event.getPlayer();
        Scoreboard(p);
    }
    private void Scoreboard(Player player) {
        World world = player.getWorld();
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        String title = null;
        if (instance.getScoreboard() != null) {
            title = instance.getScoreboard().getString("scoreboard-change-world-title");
        }
        if (title == null) title = world.getName();
        Objective objective = scoreboard.registerNewObjective(world.getName(), "");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ColorUtils.colorize(title));

        switch (world.getName().toLowerCase()) {
            case "world_nether" -> {
                objective.getScore(ColorUtils.colorize("&c&l🔥 Nether World")).setScore(3);
                objective.getScore(ColorUtils.colorize("&7Player: &e" + player.getName())).setScore(2);
                objective.getScore(ColorUtils.colorize("&cWatch out for lava!")).setScore(1);
                player.setScoreboard(scoreboard);
            }
            case "world_the_end" -> {
                objective.getScore(ColorUtils.colorize("&5&l🐉 The End")).setScore(3);
                objective.getScore(ColorUtils.colorize("&7Player: &e" + player.getName())).setScore(2);
                objective.getScore(ColorUtils.colorize("&dThe Dragon awaits!")).setScore(1);
                player.setScoreboard(scoreboard);
            }
            default -> {
                instance.getScoreboardManager().updateScoreboard(player);
            }
        }
    }

}
