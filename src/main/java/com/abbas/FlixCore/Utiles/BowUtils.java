package com.abbas.FlixCore.Utiles;

import com.abbas.FlixCore.FlixCore;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BowUtils {
    public static ItemStack CreateTeleportBow(FlixCore instance) {
        ItemStack bow = new ItemStack(Material.BOW, 1);
        ItemMeta bowMeta = bow.getItemMeta();
        if (bowMeta != null) {
            String name = instance.getTeleportBow().getString("name-bow", "");
            String description = instance.getTeleportBow().getString("description-bow", "");
            bowMeta.setDisplayName(ColorUtils.colorize(name + " " + description));
            List<String> lore = instance.getTeleportBow().getStringList("lore-bow");
            if (lore != null && !lore.isEmpty()) {bowMeta.setLore(ColorUtils.colorize(lore));}
            bow.setItemMeta(bowMeta);
            bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
            bow.addEnchantment(Enchantment.DURABILITY, 3);
            bow.setDurability((short) 0);
        }return bow;
    }
}
