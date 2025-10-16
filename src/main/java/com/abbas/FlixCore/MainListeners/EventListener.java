package com.abbas.FlixCore.MainListeners;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.BowUtils;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.Utiles.PAPIUTILS;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
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
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.HashSet;
import java.util.UUID;

import static com.comphenix.protocol.PacketType.Play.Server.SPAWN_ENTITY_LIVING;
import static org.bukkit.GameMode.ADVENTURE;

@Getter
public class EventListener implements Listener {
    private final FlixCore instance;
    private final HashSet<UUID> givePlayer = new HashSet<>();
    private final ProtocolManager protocolManager;

    public EventListener() {
        this.instance = FlixCore.getInstance();
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(instance, SPAWN_ENTITY_LIVING) {
            @Override
            public void onPacketSending(PacketEvent event) {
                Player receiver = event.getPlayer();
                if (!receiver.hasPermission("spawn.Entity")) {
                    event.setCancelled(true);
                }
            }
        });
    }
    @EventHandler
    public void PlayerJ(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        String joinMessage = instance.getMessagesConfig().getString("Join-Message");
        if (joinMessage != null && !joinMessage.isEmpty()) {
            String formattedMessage = formatMessage(player, joinMessage);
            Bukkit.broadcastMessage(formattedMessage);
        }
        boolean hasBow = false;
        String teleportBowName = FlixCore.getInstance().getTeleportBow().getString("name-bow", "");
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                if (item.getItemMeta().getDisplayName().contains(ColorUtils.colorize(teleportBowName))) {
                    hasBow = true;
                    break;}}}
        if (!hasBow) {
            ItemStack teleportBow = BowUtils.CreateTeleportBow(FlixCore.getInstance());player.getInventory().addItem(teleportBow);player.getInventory().addItem(new ItemStack(Material.ARROW, 1));}
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1, true, true));
        player.setGameMode(ADVENTURE);
        if (instance.getTabListener() != null) {
            instance.getTabListener().sendTo(player);
        }
    }
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        event.setLeaveMessage(null);
        Player player = event.getPlayer();
        String disconnectedMessage = instance.getMessagesConfig().getString("Disconnected-Message");
        if (disconnectedMessage != null && !disconnectedMessage.isEmpty()) {
            String message = disconnectedMessage.replace("{Reason}", event.getReason());
            String formattedMessage = formatMessage(player, message);
            Bukkit.broadcastMessage(formattedMessage);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(null);

        String leaveMessage = instance.getMessagesConfig().getString("Leave-Message");
        if (leaveMessage != null && !leaveMessage.isEmpty()) {
            String formattedMessage = formatMessage(player, leaveMessage);
            Bukkit.broadcastMessage(formattedMessage);
        }

        player.getWorld().playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
    }
    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        String message = formatMessage(player, "&e&l[Important] You left the bed!");
        player.sendMessage(message);
        player.damage(2.0);
        player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 1.0f);
    }
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setFoodLevel(20);
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!player.isOp()) {
            String configMessage = instance.getMessagesConfig().getString("Block-Break-Message");
            if (configMessage != null && !configMessage.isEmpty()) {
                String message = configMessage.replace("{Block}",
                        event.getBlock().getType().toString());
                String formattedMessage = formatMessage(player, message);
                player.sendMessage(formattedMessage);
            }
            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0f, 1.0f);event.setCancelled(true);
        }
    }

    @EventHandler
    public void onExpBottle(ExpBottleEvent event) {
        event.setExperience(0);
        event.setShowEffect(false);
        event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
        event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.BAT_HURT, 1.0f, 1.0f);
    }
    @EventHandler
    public void onShearSheep(PlayerShearEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.SHEEP) {
            event.setCancelled(true);
            String message = formatMessage(player, "&c[Error] You can't shear sheep right now!");
            player.sendMessage(message);
        } else {
            String message = formatMessage(player, "&a[Success] You sheared " + entity.getType() + "!");
            player.sendMessage(message);
        }
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String configMessage = instance.getMessagesConfig().getString("Chat-Format");
        if (configMessage != null && !configMessage.isEmpty()) {
            String message = configMessage.replace("{Message}", event.getMessage());
            String formattedMessage = formatMessage(player, message);
            event.setFormat(formattedMessage);
        }
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String cmd = event.getMessage().toLowerCase();
        String commandError = instance.getMessagesConfig().getString("Error-Commands");
        if (commandError == null || commandError.isEmpty()) {
            return;
        }
        String[] blockedCommands = {
                "/op", "/?", "/pl", "/plugins", "/instances", "/bukkit:pl",
                "/bukkit:plugins", "/bukkit:instances", "/bukkit:ver",
                "/bukkit:version", "/bukkit:about", "/ver", "/version", "/about"};
        for (String blockedCmd : blockedCommands) {
            if (cmd.startsWith(blockedCmd)) {
                if (player.hasPermission("Plugin.bypass")) {return;}
                event.setCancelled(true);
                String formattedMessage = formatMessage(player, commandError);
                player.sendMessage(formattedMessage);
                break;
            }
        }
    }
    @EventHandler
    public void onPlayerAchievement(PlayerAchievementAwardedEvent event) {
        event.setCancelled(true);
    }
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        boolean damageDisabled = instance.getMessagesConfig().getBoolean("Damage-Event", true);
        if (damageDisabled) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        String dropMessage = instance.getMessagesConfig().getString("Drop-Item");
        if (dropMessage != null && !dropMessage.isEmpty()) {
            String message = dropMessage.replace("{Item}", event.getItemDrop().getItemStack().getType().name());
            String formattedMessage = formatMessage(player, message);
            player.sendMessage(formattedMessage);
        }
    }
    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(true);
        String bedEnter = instance.getMessagesConfig().getString("Bed-Enter");
        if (bedEnter != null && !bedEnter.isEmpty()) {
            String formattedMessage = formatMessage(player, bedEnter);
            player.sendMessage(formattedMessage);
        }
    }
    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        player.getWorld().playSound(player.getLocation(), Sound.LAVA_POP, 1.0f, 1.0f);
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.setDeathMessage(null);

        String deathMessage = instance.getMessagesConfig().getString("Death-Message");
        if (deathMessage != null) {
            String cause = player.getLastDamageCause() != null ? player.getLastDamageCause().getCause().toString() : "UNKNOWN";
            String message = deathMessage.replace("{HowDeath}", cause);
            String formattedMessage = formatMessage(player, message);
            Bukkit.broadcastMessage(formattedMessage);
        }
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (isSpawnConfigured()) {
            World world = Bukkit.getWorld(instance.getSpawnLoc().getString("spawn.world"));
            double x = instance.getSpawnLoc().getDouble("spawn.x");
            double y = instance.getSpawnLoc().getDouble("spawn.y");
            double z = instance.getSpawnLoc().getDouble("spawn.z");
            float yaw = (float) instance.getSpawnLoc().getDouble("spawn.yaw");
            float pitch = (float) instance.getSpawnLoc().getDouble("spawn.pitch");
            if (world != null) {
                Location spawnLocation = new Location(world, x, y, z, yaw, pitch);
                event.setRespawnLocation(spawnLocation);
            } else {
                String message = formatMessage(event.getPlayer(), "&c[Error] Spawn world not found!");
                event.getPlayer().sendMessage(message);
            }
        } else {
            String message = formatMessage(event.getPlayer(), "&c[Error] Spawn is not set! Please contact an admin.");
            event.getPlayer().sendMessage(message);
        }
    }
    private String formatMessage(Player player, String message) {
        if (message == null || message.isEmpty()) return "";
        message = message.replace("{player}", player.getName());
        message = PAPIUTILS.apply(player, message);
        message = ColorUtils.colorize(message);
        return message;
    }
    private boolean isSpawnConfigured() {
        return instance.getSpawnLoc().getString("spawn.world") != null &&
                instance.getSpawnLoc().contains("spawn.x") &&
                instance.getSpawnLoc().contains("spawn.y") &&
                instance.getSpawnLoc().contains("spawn.z") &&
                instance.getSpawnLoc().contains("spawn.yaw") &&
                instance.getSpawnLoc().contains("spawn.pitch");
    }
}