package io.github.lumine1909.base;

import org.bukkit.Instrument;
import org.bukkit.inventory.ItemStack;

import static io.github.lumine1909.Tuna.pl;

public class ItemHandler {
    public boolean isTunaStick(ItemStack is) {
        return pl.nms.isTunaStick(is);
    }

    public ItemStack createTunaStick() {
        return pl.nms.createTunaStick();
    }

    public int getNote(ItemStack is) {
        return pl.nms.getNote(is);
    }

    public ItemStack setNote(ItemStack is, int note) {
        return pl.nms.setNote(is, note);
    }

    public Instrument getIns(ItemStack is) {
        return pl.nms.getIns(is);
    }

    public ItemStack setIns(ItemStack is, Instrument ins) {
        return pl.nms.setIns(is, ins);
    }
}
