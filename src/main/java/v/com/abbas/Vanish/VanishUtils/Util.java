package v.com.abbas.Vanish.VanishUtils;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public record Util(FlixCore instance) {
    public void hidePlayer(Player targ) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!online.equals(targ)) {
                online.hidePlayer(targ);
            }
        }
        if (!instance.getInvasible_list().contains(targ)) {
            instance.getInvasible_list().add(targ);
        }
        targ.sendMessage(ColorUtils.colorize("&a&lYou Are now invisible"));
    }

    public void seePlayre(Player targ) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.showPlayer(targ);
        }
        instance.getInvasible_list().remove(targ);
        targ.sendMessage(ColorUtils.colorize("&c&lYou are now visible!"));
    }

    /**
     * hi im abbas now im add toggleVanish To feature
     */
    public void toggleVanish(Player player) {
        if (isVanished(player)) {
            player.showPlayer(player);
        } else {
            hidePlayer(player);
        }
    }

    public boolean isVanished(Player p) {
        return instance.getInvasible_list().contains(p);
    }

    public void JoinServerVanish(Player p) {
        List<Player> vanishPlayers = instance.getInvasible_list();
        for (Player vanished : vanishPlayers) {
            p.hidePlayer(vanished);
        }
    }
}
