// java
package dev.abbas.FlixCore.Scoreboards;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import e.com.abbas.LuckPermsAPI.Perms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;

public class ScoreboardManager {

    private final FlixCore instance;
    private final Perms perm;

    public ScoreboardManager(FlixCore instance) {
        this.instance = instance;
        this.perm = new Perms();
    }


    public void Updater() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    updateScoreboard(player);
                }
            }
        }.runTaskTimer(instance, 0L, 20L);
    }
    public void updateScoreboard(Player player) {
        Scoreboard bsc = Bukkit.getScoreboardManager().getNewScoreboard();
        String title = instance.getScoreboard() != null ? instance.getScoreboard().getString("scoreboard.title") : null;
        if (title == null) title = "&aServer Info";
        Objective objective = bsc.registerNewObjective("server", "dummy");
        objective.setDisplayName(ColorUtils.colorize(title));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        List<String> lines = instance.getScoreboard() != null ? instance.getScoreboard().getStringList("scoreboard.lines") : null;
        if (lines == null) return;
        int score = lines.size();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line == null) continue;
            String formatted = line
                    .replace("{player}", player.getName())
                    .replace("{online}", String.valueOf(Bukkit.getOnlinePlayers().size()))
                    .replace("{rank}", perm.getPlayerRank(player));
            String colorized = ColorUtils.colorize(formatted);
            if (stripColorCodes(colorized).length() <= 16) {
                objective.getScore(colorized).setScore(score--);
                continue;
            }
            String prefix = colorized.substring(0, Math.min(16, colorized.length()));
            String suffix = colorized.length() > 16 ? colorized.substring(16, Math.min(32, colorized.length())) : "";
            String teamName = "line" + i;
            Team team = bsc.registerNewTeam(teamName);
            team.setPrefix(prefix);
            team.setSuffix(suffix);
            String entry = ChatColor.values()[i % ChatColor.values().length].toString() + i;
            team.addEntry(entry);
            objective.getScore(entry).setScore(score--);
        }
        player.setScoreboard(bsc);
    }private String stripColorCodes(String input) {
        if (input == null) return "";
        return input.replaceAll("(?i)(&[0-9A-FK-OR])|(§[0-9A-FK-OR])", "");
    }
}
