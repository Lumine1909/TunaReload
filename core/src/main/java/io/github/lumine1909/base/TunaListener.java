package io.github.lumine1909.base;

import io.github.lumine1909.object.Instrument;
import io.github.lumine1909.settings.PlayerSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

import static io.github.lumine1909.Tuna.pl;
import static io.github.lumine1909.util.Mappings.trans;

public class TunaListener implements Listener {
    public TunaListener() {
        Bukkit.getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {
            return;
        }
        if (e.getClickedInventory().getHolder() instanceof BaseGUI) {
            e.setCancelled(true);
            ((BaseGUI) e.getClickedInventory().getHolder()).handleClick(e);
        }
    }

    @EventHandler
    public void onInvClick(InventoryDragEvent e) {
        if (e.getInventory() == null) {
            return;
        }
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
            if (!ins.isNull() && !ins.checkError()) {
                pl.bh.setBlockIns(e.getBlock(), ins.getInstrument(), PlayerSettings.get(e.getPlayer()).SYNC_INST);
            }
            pl.bh.setBlockNote(e.getBlock(), note);
        }
    }

    @EventHandler
    public void onItemSwap(PlayerSwapHandItemsEvent e) {
        if (e.getOffHandItem().getType() == Material.NOTE_BLOCK) {
            boolean bl = !PlayerSettings.get(e.getPlayer()).SCROLL_ITEM;
            PlayerSettings.get(e.getPlayer()).SCROLL_ITEM = bl;
            e.getPlayer().sendMessage(bl ? trans("scroll-item-activate") : trans("scroll-item-deactivate"));
            e.setCancelled(true);
        }
        if (pl.ih.isTunaStick(e.getOffHandItem())) {
            boolean bl = !PlayerSettings.get(e.getPlayer()).SCROLL_BLOCK;
            PlayerSettings.get(e.getPlayer()).SCROLL_BLOCK = bl;
            e.getPlayer().sendMessage(bl ? trans("scroll-block-activate") : trans("scroll-block-deactivate"));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onScrollItem(PlayerItemHeldEvent e) {
        if (!PlayerSettings.get(e.getPlayer()).SCROLL_ITEM || e.getPlayer().getInventory().getItem(e.getPreviousSlot()).getType() != Material.NOTE_BLOCK) {
            return;
        }
        ItemStack noteBlock = e.getPlayer().getInventory().getItem(e.getPreviousSlot());
        int next = Math.floorMod(pl.ih.getNote(noteBlock) + calcDiff(e.getPreviousSlot(), e.getNewSlot()), 25);
        e.getPlayer().getInventory().setItemInMainHand(pl.ih.setNote(noteBlock, next));
        e.setCancelled(true);
    }

    @EventHandler
    public void onScrollBlock(PlayerItemHeldEvent e) {
        if (!PlayerSettings.get(e.getPlayer()).SCROLL_BLOCK || !pl.ih.isTunaStick(e.getPlayer().getInventory().getItem(e.getPreviousSlot()))) {
            return;
        }
        Block block = e.getPlayer().getTargetBlock(null, 5);
        if (!(block.getBlockData() instanceof NoteBlock)) {
            return;
        }
        int next = Math.floorMod(pl.bh.getBlockNote(block) + calcDiff(e.getPreviousSlot(), e.getNewSlot()), 25);
        pl.bh.setBlockNote(block, next);
        e.setCancelled(true);
    }

    private int calcDiff(int prev, int next) {
        if (prev - next > 4) {
            return prev - next - 9;
        }
        if (next - prev > 4) {
            return prev - next + 9;
        }
        return prev - next;
    }
}
