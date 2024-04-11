package io.github.lumine1909.impl_1_7;

import io.github.lumine1909.base.BlockHandler;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.block.Block;
import org.bukkit.block.NoteBlock;

import static io.github.lumine1909.util.Mappings.*;

public class BlockHandlerImpl implements BlockHandler {
    @Override
    public int setBlockNote(Block block, int note) {
        if (!(block.getState() instanceof NoteBlock)) {
            return 1;
        }
        NoteBlock nb = (NoteBlock) block.getState();
        nb.setNote(new Note(note));
        return 0;
    }

    @Override
    public int getBlockNote(Block block) {
        if (!(block.getState() instanceof NoteBlock)) {
            return -1;
        }
        return  ((NoteBlock) block.getState()).getNote().getId();
    }

    @Override
    public Instrument getBlockIns(Block block) {
        if (!(block.getState() instanceof NoteBlock)) {
            return null;
        }
        return toBkIns(getKey(block));
    }

    @Override
    public int setBlockIns(Block block, Instrument ins, boolean sync) {
        if (!settings.SYNC_INSTRUMENT || !sync) {
            return 3;
        }
        if (!(block.getState() instanceof NoteBlock)) {
            return 1;
        }
        if (block.getLocation().getY() == 0) {
            return 2;
        }
        Block b = block.getLocation().add(0, -1, 0).getBlock();
        b.setType(getMaterial(toKey(ins)));
        return 0;
    }
}
