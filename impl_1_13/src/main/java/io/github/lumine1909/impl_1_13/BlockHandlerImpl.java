package io.github.lumine1909.impl_1_13;

import io.github.lumine1909.base.BlockHandler;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;

import static io.github.lumine1909.util.Mappings.*;

public class BlockHandlerImpl implements BlockHandler {
    @Override
    public int setBlockNote(Block block, int note) {
        if (!(block.getBlockData() instanceof NoteBlock)) {
            return 1;
        }
        NoteBlock nb = (NoteBlock) block.getBlockData();
        nb.setNote(new Note(note));
        block.setBlockData(nb);
        return 0;
    }

    @Override
    public int getBlockNote(Block block) {
        if (!(block.getBlockData() instanceof NoteBlock)) {
            return -1;
        }
        NoteBlock nb = (NoteBlock) block.getBlockData();
        return nb.getNote().getId();
    }

    @Override
    public Instrument getBlockIns(Block block) {
        if (!(block.getBlockData() instanceof NoteBlock)) {
            return null;
        }
        NoteBlock nb = (NoteBlock) block.getBlockData();
        return nb.getInstrument();
    }

    @Override
    public int setBlockIns(Block block, Instrument ins, boolean sync) {
        if (!(block.getBlockData() instanceof NoteBlock)) {
            return 1;
        }
        NoteBlock nb = (NoteBlock) block.getBlockData();
        nb.setInstrument(ins);
        block.setBlockData(nb);
        if (settings.SYNC_INSTRUMENT && sync) {
            Block b = block.getLocation().add(0, -1, 0).getBlock();
            b.setType(getMaterial(toKey(ins)));
            if (b.getType() == Material.VOID_AIR) {
                return 2;
            }
        }
        return 0;
    }
}
