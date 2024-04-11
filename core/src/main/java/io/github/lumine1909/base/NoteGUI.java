package io.github.lumine1909.base;

import io.github.lumine1909.settings.PlayerSettings;
import io.github.lumine1909.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Set;

import static io.github.lumine1909.Tuna.pl;
import static io.github.lumine1909.util.Mappings.snote;
import static io.github.lumine1909.util.Mappings.trans;

public class NoteGUI implements BaseGUI {

    final Inventory inv;
    public NoteGUI() {
        inv = Bukkit.createInventory(this, 27, trans("note-gui-name"));
        inv.setItem(0, ItemBuilder.init(Material.NOTE_BLOCK, 1).name(trans("base-noteblock") + " (0, " + snote(0) + ")").build());
        for (int i=1; i<25; i++) {
            inv.setItem(i, ItemBuilder.init(Material.NOTE_BLOCK, i).name(trans("base-noteblock") + " (" + i + ", " +  snote(i) + ")").build());
        }
    }
    @Override
    public Inventory getInventory() {
        return inv;
    }

    @Override
    public void handleClick(InventoryClickEvent e) {
        int note = e.getRawSlot();
        Player player = (Player) e.getWhoClicked();
        Block block = PlayerSettings.get(player).block;
        if (block == null) {
            return;
        }
        if (note < 0 || note > 24) {
            return;
        }
        pl.bh.setBlockNote(block, note);
        player.closeInventory();
    }
}
