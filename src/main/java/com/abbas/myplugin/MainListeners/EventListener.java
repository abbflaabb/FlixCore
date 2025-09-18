package com.abbas.myplugin.MainListeners;

import com.abbas.myplugin.MyPlugin;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

@Getter
public class EventListener implements Listener {

    private final MyPlugin instance;
    public EventListener(MyPlugin instance) {
        this.instance = instance;
    }

    @EventHandler
    public void PlayerJ(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        player.sendMessage(ChatColor.GOLD + "[+1 ] Join To Server " + player.getName() + " Address Player " + player.getAddress() +  " !");
        Location loc = player.getLocation();
        player.playSound(loc , Sound.LEVEL_UP, 1.0f, 1.0f);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000,2322, true, true));
    }
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        event.setLeaveMessage(null);
        event.setReason(ChatColor.RED + "[Disconnected] " + ChatColor.GOLD + "You have been kicked from the server!");
    }

    @EventHandler
    public void LeaveBed(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.YELLOW + "[important] You Leave Bed " + player.getName() + "!");
        player.damage(2.0);
        Location loc = player.getLocation();
        player.playSound(loc , Sound.BAT_TAKEOFF, 1.0f, 1.0f);
    }
    @EventHandler
    public void LeaveS(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(null);
        player.sendMessage(ChatColor.RED + "[-1] Leave From Server " + player.getName() +  " Address Player " + player.getAddress());
        Location loc = player.getLocation();
        player.playSound(loc , Sound.VILLAGER_NO, 1.0f,1.0f);
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!player.isOp()) {
            String configMessage = instance.getMessagesConfig().getString("Block-Break-Message");
            if (configMessage != null && !configMessage.isEmpty()) {
                String message = configMessage
                        .replace("{Player}", player.getName())
                        .replace("{Block}", event.getBlock().getType().toString());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0f, 1.0f);

            event.setCancelled(true);
        } else {
            event.setCancelled(false);
        }
    }
    @EventHandler
    public void EXP(ExpBottleEvent event) {
        event.setExperience(0);
        event.setShowEffect(false);
        event.getEntity().getWorld().playEffect(
                event.getEntity().getLocation(),
                Effect.MOBSPAWNER_FLAMES,
                0

        );
        event.getEntity().getWorld().playSound(
                event.getEntity().getLocation(),
                Sound.BAT_HURT,
                1.0f,
                1.0f
        );
    }
    @EventHandler
    public void ShearSheep(PlayerShearEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.SHEEP) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "[Error] You Can't Shear Sheep Now!");

        } else {
            player.sendMessage(ChatColor.GREEN + "[Success] You Shear " + entity.getType() + "!");
        }
    }
    /**
     * @deprecated Use #Chat(PlayerChatEvent) instead
     *
     */
    @EventHandler
    public void Chat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String configMessage = instance.getMessagesConfig().getString("Chat-Format");
        if (configMessage != null && !configMessage.isEmpty()) {
            String message = configMessage.replace("{Player}", player.getName())
                    .replace("{Message}", event.getMessage());
            event.setFormat(ChatColor.translateAlternateColorCodes('&', message));
        }
        event.setCancelled(false);
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String cmd = event.getMessage().toLowerCase();
        String commandError = instance.getMessagesConfig().getString("Error-Commands");
        if (commandError == null || commandError.isEmpty()) {
            return;
        }
        String message = ChatColor.translateAlternateColorCodes('&',
                commandError.replace("{Player}", player.getName()));
        String[] BlockedCommands = {
                "/op", "/?", "/pl", "/plugins",
                "/bukkit:pl", "/bukkit:plugins", "/bukkit:ver", "/bukkit:version",
                "/bukkit:about", "/ver", "/version", "/about"
        };
        for (String blockedCmd : BlockedCommands) {
            if (cmd.startsWith(blockedCmd)) {
                if (player.hasPermission("Plugin.bypass")) {
                    return;
                }
                event.setCancelled(true);
                player.sendMessage(message);
                break;
            }
        }
    }
    @EventHandler
    public void PlayerAchievement(PlayerAchievementAwardedEvent event) {
        event.setCancelled(true);
    }
    @EventHandler
    public void DamageL(EntityDamageEvent event) {
        boolean DamageC = instance.getMessagesConfig().getBoolean("Damage-Event", true);
        if (DamageC) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void DropIE(PlayerDropItemEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();

        String DropIM = instance.getMessagesConfig().getString("Drop-Item");
        if (DropIM != null && !DropIM.isEmpty()) {
            String message = DropIM.replace("{Player}", player.getName())
                    .replace("{Item}", event.getItemDrop().getItemStack().getType().name());
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }
    @EventHandler
    public void BedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(true);
        String BedEnter = instance.getMessagesConfig().getString("Bed-Enter");
        if (BedEnter != null && !BedEnter.isEmpty()) {
            String BedEnterMessage = BedEnter.replace("{Player}", player.getName());
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', BedEnterMessage));
        }
    }
    @EventHandler
    public void EventMenu(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Menu")) {
            event.setCancelled(true);
            if (event.isRightClick()) return;
            Player player = (Player) event.getWhoClicked();
            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || clicked.getType() == Material.AIR) return;
            if (Objects.requireNonNull(clicked.getType()) == Material.BED) {
                CnctSM(player, "BedWars");
                player.closeInventory();
            }
        }
    }

    /// @param servername
    private void CnctSM(Player player, String servername) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(servername);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        player.sendPluginMessage(MyPlugin.getPlugin(MyPlugin.class), "BungeeCord", b.toByteArray());
    }

    @EventHandler
    public void dj(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        player.getWorld().playSound(location, Sound.LEVEL_UP, 1.0f,1.0f);
    }
    @EventHandler
    public void HSS(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.setDeathMessage(null);
        String dththos = instance.getMessagesConfig().getString("Death-Message");

        if (dththos != null) {
           String Mhbsb = dththos
                    .replace("{player}", player.getName())
                    .replace("{HowDeath}", player.getLastDamageCause().getCause().toString());

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Mhbsb));

        }
    }

}

