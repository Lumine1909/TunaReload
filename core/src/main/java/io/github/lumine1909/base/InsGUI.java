package io.github.lumine1909.base;

import io.github.lumine1909.settings.PlayerSettings;
import io.github.lumine1909.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Set;

import static io.github.lumine1909.Tuna.pl;
import static io.github.lumine1909.util.Mappings.*;

public class InsGUI implements BaseGUI {
    Inventory inv;
    public InsGUI() {
        inv = Bukkit.createInventory(this, 27, trans("inst-gui-name"));
        sminsMap.forEach((k, v) -> inv.addItem(ItemBuilder.init(v, 1).name(trans("instrument-" + k)).build()));
    }
    @Override
    public Inventory getInventory() {
        return inv;
    }
    @Override
    public void handleClick(InventoryClickEvent e) {
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        Material click = e.getCurrentItem().getType();
        if (click == Material.AIR || click == null) {
            return;
        }
        Block block = PlayerSettings.get(player).block;
        if (block == null) {
            return;
        }
        Instrument ins = toBkIns(minsMap.get(click));
        int result = pl.bh.setBlockIns(block, ins, PlayerSettings.get(player).SYNC_INST);
        switch (result) {
            case 2: {
                player.sendMessage(trans("bad-noteblock-pos"));
                break;
            }
            case 3: {
                player.sendMessage(trans("sync-not-enable"));
                break;
            }
        }
        player.closeInventory();
    }
}
