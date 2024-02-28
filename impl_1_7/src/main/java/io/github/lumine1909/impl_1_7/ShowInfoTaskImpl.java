package io.github.lumine1909.impl_1_7;

import io.github.lumine1909.base.ShowInfoTask;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static io.github.lumine1909.util.Mappings.*;

public class ShowInfoTaskImpl extends ShowInfoTask {
    @Override
    protected int getUpDate(Player player) {
        Block b = player.getTargetBlock(null, 5);
        if (!(b.getState() instanceof NoteBlock)) {
            return 1;
        }
        ItemStack main = player.getInventory().getItemInMainHand();
        ItemStack off = player.getInventory().getItemInOffHand();
        if (!(main.getType() == Material.NOTE_BLOCK || off.getType() == Material.NOTE_BLOCK || nms.isTunaStick(main) || nms.isTunaStick(off))) {
            return 1;
        }
        if (!bufferMap.containsKey(player.getUniqueId())) {
            return 0;
        }
        NoteBlock nb = (NoteBlock) b.getState();
        Buffer bf = bufferMap.get(player.getUniqueId());
        return (!bf.nb.equals(nb) || !bf.b.equals(b)) ? 0 : 2;
    }
    @Override
    protected void update(Player player) {
        Block b = player.getTargetBlock(null, 5);
        if (!(b.getState() instanceof NoteBlock)) {
            return;
        }
        NoteBlock nb = (NoteBlock) b.getState();
        String name = ChatColor.AQUA + trans("instrument-" + toKey(bh.getBlockIns(b))) + ", " + nb.getNote().getId();
        nms.addArmor(player, b.getLocation().add(0.5, 1, 0.5), name);
        bufferMap.put(player.getUniqueId(), new Buffer(nb, b));
    }
    @Override
    protected void delete(Player player) {
        nms.removeArmor(player);
    }
}
