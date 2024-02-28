package io.github.lumine1909.base;

import org.bukkit.Instrument;
import org.bukkit.block.Block;

public interface BlockHandler {
    int setBlockNote(Block block, int note);
    int getBlockNote(Block block);
    Instrument getBlockIns(Block block);
    int setBlockIns(Block block, Instrument ins, boolean sync);
}
