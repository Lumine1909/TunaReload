package io.github.lumine1909.base;

import io.github.lumine1909.settings.PlayerSettings;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static io.github.lumine1909.Tuna.pl;

public class TunaListener implements Listener {
    public TunaListener() {
        Bukkit.getPluginManager().registerEvents(this, pl);
    }
    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getClickedInventory().getHolder() instanceof BaseGUI) {
            e.setCancelled(true);
            ((BaseGUI) e.getClickedInventory().getHolder()).handleClick(e);
        }
    }
    @EventHandler
    public void onInvClick(InventoryDragEvent e) {
        if (e.getInventory().getHolder() instanceof BaseGUI) {
            e.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Player player = e.getPlayer();
        ItemStack main = player.getInventory().getItemInMainHand();
        ItemStack off = player.getInventory().getItemInOffHand();
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!PlayerSettings.get(player).ENABLE_INST || !player.hasPermission("tuna.blockisnt")) {
                return;
            }
            if (!pl.nms.isTunaStick(main) && !pl.nms.isTunaStick(off)) {
                return;
            }
            if (e.getClickedBlock().getType() != Material.NOTE_BLOCK) {
                return;
            }
            e.setCancelled(true);
            PlayerSettings.get(player).block = e.getClickedBlock();
            player.openInventory(pl.ig.inv);
        } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (!PlayerSettings.get(player).ENABLE_NOTE || !player.hasPermission("tuna.blocknote")) {
                return;
            }
            if (!pl.nms.isTunaStick(main) && !pl.nms.isTunaStick(off)) {
                return;
            }
            if (e.getClickedBlock().getType() != Material.NOTE_BLOCK) {
                return;
            }
            e.setCancelled(true);
            PlayerSettings.get(player).block = e.getClickedBlock();
            player.openInventory(pl.ng.inv);
        }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getItemInHand().getType() == Material.NOTE_BLOCK) {
            Instrument ins = pl.ih.getIns(e.getItemInHand());
            int note = pl.ih.getNote(e.getItemInHand());
            if (ins != null) {
                pl.bh.setBlockIns(e.getBlock(), ins, PlayerSettings.get(e.getPlayer()).SYNC_INST);
            }
            System.out.println(note);
            pl.bh.setBlockNote(e.getBlock(), note);
        }
    }
}
