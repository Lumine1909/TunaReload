package io.github.lumine1909.nms;

import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NMS_1_7 {// extends Reflection implements NMSBase {
    /*

    Class<?> nmsBlockC;
    Class<?> nmsBlocksC;
    Class<?> nmsBlockDataC;
    Class<?> nmsMaterialC;
    Method getMaterialM;
    Method getNMSBlockM;
    Method blocksGetM;
    Field mStoneF;
    Field mSandF;
    Field mShatF;
    Field mWoodF;
    Field blockDataF;

    public NMS_1_7() {
        nmsBlockC = nmsClass("Block");
        nmsBlocksC = nmsClass("Blocks");
        nmsBlockDataC = nmsClass("BlockStateList$BlockData");
        nmsMaterialC = nmsClass("Material");
        getMaterialM = method(nmsBlockDataC, "getMaterial");
        blocksGetM = method(nmsBlocksC, "get", String.class);
        getNMSBlockM = method(obcBlockC, "getNMSBlock");
        mStoneF = field(nmsMaterialC, "STONE");
        mSandF = field(nmsMaterialC, "SAND");
        mWoodF = field(nmsMaterialC, "WOOD");
        mShatF = field(nmsMaterialC, "SHATTERABLE");
        blockDataF = field(nmsBlockC, "blockData");
    }

    @Override
    public void addArmor(Player player, Location loc, String name) {

    }

    @Override
    public void removeArmor(Player player) {

    }

    @Override
    public boolean isTunaStick(ItemStack is) {
        return false;
    }

    @Override
    public ItemStack createTunaStick() {
        return null;
    }

    @Override
    public int getNote(ItemStack is) {
        return 0;
    }

    @Override
    public void setNote(ItemStack is, int note) {

    }

    @Override
    public Instrument getIns(ItemStack is) {
        return null;
    }

    @Override
    public void setIns(ItemStack is, Instrument ins) {

    }

    @Override
    public Byte getNoteByte(Block block) {
        Object nmsBlock = invoke(getNMSBlockM, block);
        Object blockData = get(blockDataF, nmsBlock);
        Object material = invoke(getMaterialM, blockData);
        Object stone = get(mStoneF, null), sand = get(mSandF, null), shat = get(mShatF, null), wood = get(mWoodF, null);
        byte b0 = 0;
        if (material.equals(stone)) {
            b0 = 1;
        }

        if (material.equals(sand)) {
            b0 = 2;
        }

        if (material.equals(shat)) {
            b0 = 3;
        }

        if (material.equals(wood)) {
            b0 = 4;
        }
        Object clay = invoke(blocksGetM, "clay"), gb = invoke(blocksGetM, "gold_block"), wool = invoke(blocksGetM, "wool"), pi = invoke(blocksGetM, "packed_ice"), bb = invoke(blocksGetM, "bone_block");
        if (nmsBlock.equals(clay)) {
            b0 = 5;
        }

        if (nmsBlock.equals(gb)) {
            b0 = 6;
        }

        if (nmsBlock.equals(wool)) {
            b0 = 7;
        }

        if (nmsBlock.equals(pi)) {
            b0 = 8;
        }

        if (nmsBlock.equals(bb)) {
            b0 = 9;
        }
        return b0;
    }

    @Override
    protected Class<?> nmsClass(String name) {
        return clazz("net.minecraft.server." + sv + "." + name);
    }

     */
}
