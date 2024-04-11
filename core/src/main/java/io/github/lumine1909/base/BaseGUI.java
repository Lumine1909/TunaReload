package io.github.lumine1909.base;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public interface BaseGUI extends InventoryHolder {
    void handleClick(InventoryClickEvent e);
}
