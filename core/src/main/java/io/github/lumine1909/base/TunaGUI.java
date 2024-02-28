package io.github.lumine1909.base;

import io.github.lumine1909.settings.PlayerSettings;
import io.github.lumine1909.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static io.github.lumine1909.Tuna.pl;
import static io.github.lumine1909.util.Mappings.trans;

public class TunaGUI implements BaseGUI {
    Inventory inv;
    PlayerSettings ps;
    Player player;
    @Override
    public Inventory getInventory() {
        return inv;
    }
    public TunaGUI(PlayerSettings ps) {
        this.ps = ps;
        this.player = ps.player;
        inv = Bukkit.createInventory(this, 9, trans("settings-gui-name"));
        ItemStack is3 = ItemBuilder.init(Material.NOTE_BLOCK, 1).name(trans("settings-note")).lore(ps.ENABLE_NOTE ? "&a已开启, 点击禁用" : "&c已禁用, 点击开启").fakeEnch(ps.ENABLE_NOTE).build();
        ItemStack is4 = ItemBuilder.init(Material.IRON_BLOCK, 1).name(trans("settings-instrument")).lore(ps.ENABLE_INST ? "&a已开启, 点击禁用" : "&c已禁用, 点击开启").fakeEnch(ps.ENABLE_INST).build();
        ItemStack is5 = ItemBuilder.init(Material.NOTE_BLOCK, 1).name(trans("settings-sync-instrument")).lore(ps.SYNC_INST ? "&a已开启, 点击禁用" : "&c已禁用, 点击开启").fakeEnch(ps.SYNC_INST).build();
        ItemStack isb = ItemBuilder.init(Material.BARRIER, 1).name(trans("settings-noperm-name")).build();
        if (player.hasPermission("tuna.stick.note")) {
            inv.setItem(3, is3);
        } else {
            inv.setItem(3, isb);
        }
        if (player.hasPermission("tuna.stick.inst")) {
            inv.setItem(4, is4);
        } else {
            inv.setItem(4, isb);
        }
        if (player.hasPermission("tuna.syncinst") && pl.settings.SYNC_INSTRUMENT) {
            inv.setItem(5, is5);
        } else {
            inv.setItem(5, isb);
        }
    }
    @Override
    public void handleClick(InventoryClickEvent e) {
        int slot = e.getRawSlot();
        switch (slot) {
            case 3: {
                if (player.hasPermission("tuna.stick.note")) {
                    ps.ENABLE_NOTE = !ps.ENABLE_NOTE;
                    ItemStack is3 = ItemBuilder.init(Material.NOTE_BLOCK, 1).name(trans("settings-note")).lore(ps.ENABLE_NOTE ? "&a已开启, 点击禁用" : "&c已禁用, 点击开启").fakeEnch(ps.ENABLE_NOTE).build();
                    inv.setItem(3, is3);
                }
                break;
            }
            case 4: {
                if (player.hasPermission("tuna.stick.inst")) {
                    ps.ENABLE_INST = !ps.ENABLE_INST;
                    ItemStack is4 = ItemBuilder.init(Material.IRON_BLOCK, 1).name(trans("settings-instrument")).lore(ps.ENABLE_INST ? "&a已开启, 点击禁用" : "&c已禁用, 点击开启").fakeEnch(ps.ENABLE_INST).build();
                    inv.setItem(4, is4);
                }
                break;
            }
            case 5: {
                if (player.hasPermission("tuna.syncinst") && pl.settings.SYNC_INSTRUMENT) {
                    ps.SYNC_INST = !ps.SYNC_INST;
                    ItemStack is5 = ItemBuilder.init(Material.NOTE_BLOCK, 1).name(trans("settings-sync-instrument")).lore(ps.SYNC_INST ? "&a已开启, 点击禁用" : "&c已禁用, 点击开启").fakeEnch(ps.SYNC_INST).build();
                    inv.setItem(5, is5);
                }
                break;
            }
        }
    }
}
