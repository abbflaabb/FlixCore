package com.abbas.FlixCore.Utiles;

import com.abbas.FlixCore.FlixCore;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BowUtils {
    public static FlixCore instance;
    public BowUtils() {
        this.instance = new FlixCore();
    }

    public static ItemStack CreateTeleportBow() {
        ItemStack bow  = new ItemStack(Material.BOW, 1);
        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.setDisplayName(instance.getTeleportBow().getString("name-bow") + instance.getTeleportBow().getString("description-bow"));

        List<String> lore = instance.getTeleportBow().getStringList("lore-bow");
        bowMeta.setLore(ColorUtils.colorize(lore));
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
      bow.setItemMeta(bowMeta);
      return  bow;
    }
}
