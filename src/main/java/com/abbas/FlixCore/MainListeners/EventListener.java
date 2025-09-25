package com.abbas.FlixCore.MainListeners;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.BowUtils;
import com.abbas.FlixCore.Utiles.ColorUtils;
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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

import static com.comphenix.protocol.PacketType.Play.Server.SPAWN_ENTITY_LIVING;

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
        String joinMessage = FlixCore.getInstance().getMessagesConfig().getString("Join-Message");
        if (joinMessage != null && !joinMessage.isEmpty()) {
            player.sendMessage(ColorUtils.colorize(joinMessage.replace("{Player}", player.getName())));
        }
        boolean hasBow = false;
        String teleportBowName = FlixCore.getInstance().getTeleportBow().getString("name-bow", "");
        for (ItemStack item : player.getInventory().getContents()) {if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {if (item.getItemMeta().getDisplayName().contains(ColorUtils.colorize(teleportBowName))) {hasBow = true;break;}}}
        if (!hasBow) {ItemStack teleportBow = BowUtils.CreateTeleportBow(FlixCore.getInstance());player.getInventory().addItem(teleportBow);player.getInventory().addItem(new ItemStack(Material.ARROW, 1));}
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1, true, true));
    }
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        event.setLeaveMessage(null);
        Player player = event.getPlayer();
        //Can you now Change Message From Config
        //Add Bow if player join first time for server
        String disconnectedMessage =  instance.getMessagesConfig().getString("Disconnected-Message");
        if (disconnectedMessage != null && !disconnectedMessage.isEmpty()) {
            String message =  disconnectedMessage
                    .replace("{Player}", player.getName())
                    .replace("{Reason}", event.getReason());
            player.sendMessage(ColorUtils.colorize(message));
        }
    }

    @EventHandler
    public void LeaveBed(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(ColorUtils.colorize( "&e&l[important] You Leave Bed ") + player.getName() + "!");
        player.damage(2.0);
        Location loc = player.getLocation();
        player.playSound(loc , Sound.BAT_TAKEOFF, 1.0f, 1.0f);
    }
    @EventHandler
    public void LeaveS(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(null);
        String leaveMessage = instance.getMessagesConfig().getString("Leave-Message");
        if (leaveMessage != null && !leaveMessage.isEmpty()) {
            String leavem = leaveMessage
                    .replace("{Player}", player.getName());
            player.sendMessage(ColorUtils.colorize(leavem));
            Location loc = player.getLocation();
            player.playSound(loc , Sound.VILLAGER_NO, 1.0f,1.0f);
        }
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
                player.sendMessage(ColorUtils.colorize(message));
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
        //Default Effect Remove
        event.setShowEffect(false);
        //PlayEffect
        event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
        //PlaySound
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
            player.sendMessage(ColorUtils.colorize( "&c[Error] You Can't Shear Sheep Now!"));

        } else {
            player.sendMessage(ColorUtils.colorize( "&a[Success] You Shear " + entity.getType() + "!"));
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
            String message = configMessage.replace("{Player}", player.getName()).
                    replace("{Message}", event.getMessage());event.setFormat(ColorUtils.colorize(message));
        }
        event.setCancelled(false);
    }
    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String cmd = event.getMessage().toLowerCase();
        String commandError = instance.getMessagesConfig().getString("Error-Commands");
        if (commandError == null || commandError.isEmpty()) {return;}
        String message = ColorUtils.colorize(commandError.replace("{Player}", player.getName()));
        String[] BlockedCommands = {
                "/op", "/?", "/pl", "/plugins",
                "/bukkit:pl", "/bukkit:plugins", "/bukkit:ver", "/bukkit:version",
                "/bukkit:about", "/ver", "/version", "/about"};
        for (String blockedCmd : BlockedCommands) {
            if (cmd.startsWith(blockedCmd)) {
                if (player.hasPermission("Plugin.bypass")) {return;}
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
    public void DamageEvent(EntityDamageEvent event) {
        boolean DamageEvent = instance.getMessagesConfig().getBoolean("Damage-Event", true);
        if (DamageEvent) {
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
            player.sendMessage(ColorUtils.colorize(message));
        }
    }
    @EventHandler
    public void BedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(true);
        String BedEnter = instance.getMessagesConfig().getString("Bed-Enter");
        if (BedEnter != null && !BedEnter.isEmpty()) {
            String BedEnterMessage = BedEnter.replace("{Player}", player.getName());
            player.sendMessage(ColorUtils.colorize(BedEnterMessage));
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
    private void CnctSM(Player player, String servername) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(servername);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        player.sendPluginMessage(FlixCore.getPlugin(FlixCore.class), "BungeeCord", b.toByteArray());
    }
    @EventHandler
    public void ChangeWorldEvent(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        player.getWorld().playSound(location, Sound.LAVA_POP, 1.0f,1.0f);
    }
    @EventHandler
    public void DeathPlayerEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.setDeathMessage(null);
        String DeathMessage = instance.getMessagesConfig().getString("Death-Message");
        if (DeathMessage != null) {
           String sendMessage = DeathMessage
                    .replace("{player}", player.getName())
                    .replace("{HowDeath}", player.getLastDamageCause().getCause().toString());
            player.sendMessage(ColorUtils.colorize(sendMessage));
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("spawn.Entity")) {
            Entity entity = player.getWorld().spawnEntity(player.getLocation(), EntityType.COW);
            entity.setCustomName(ColorUtils.colorize("&d&lMy CCOWW"));
        }
    }
}
