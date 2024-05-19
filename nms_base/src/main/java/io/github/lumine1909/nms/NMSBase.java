package io.github.lumine1909.nms;

import io.github.lumine1909.object.Instrument;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface NMSBase {
    void addArmor(Player player, Location loc, String name);
    void removeArmor(Player player);
    boolean isTunaStick(ItemStack is);
    ItemStack createTunaStick();
    int getNote(ItemStack is);
    ItemStack setNote(ItemStack is, int note);
    Instrument getIns(ItemStack is);
    ItemStack setIns(ItemStack is, Instrument ins);
    Byte getNoteByte(Block block);
}
