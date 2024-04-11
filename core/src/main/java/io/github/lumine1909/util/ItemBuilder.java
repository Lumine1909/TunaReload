package io.github.lumine1909.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ItemBuilder {
    ItemStack is;
    ItemMeta im;
    private ItemBuilder(Material material, int count) {
        is = new ItemStack(material, count);
        im = is.getItemMeta();
    }
    public static ItemBuilder init(Material material, int count) {
        return new ItemBuilder(material, count);
    }
    public ItemBuilder name(String str) {
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', str));
        return this;
    }
    public ItemBuilder lore(String... lore) {
        im.setLore(Arrays.stream(lore).map(l -> ChatColor.translateAlternateColorCodes('&', l)).collect(Collectors.toList()));
        return this;
    }
    public ItemBuilder fakeEnch(boolean flag) {
        if (!flag) {
            return this;
        }
        im.addEnchant(Enchantment.DURABILITY, 0, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }
    public ItemStack build() {
        is.setItemMeta(im);
        return is;
    }
}
