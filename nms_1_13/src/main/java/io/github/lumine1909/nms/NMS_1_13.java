package io.github.lumine1909.nms;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

import static io.github.lumine1909.util.Mappings.*;

public class NMS_1_13 extends Reflection implements NMSBase {
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

    Field mClayF;
    Field mPackIceF;
    Field mGoldBF;
    Field mWoolF;
    Field mBoneBF;
    Field blockDataF;
    Class<?> nmsArmorC;
    Class<?> packetC;
    Method getBkEM;
    Method setUidM;
    Method hasTagM;
    Method setTagM;
    Field dataF;
    Field eIdF;
    Constructor<?> armorCt;

    Constructor<?> paeCt;

    Constructor<?> pedCt;
    Constructor<?> nbtTagCt;

    Constructor<?> preCt;
    public NMS_1_13() {
        super();
        if (ver < 1300) {
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

            mClayF = field(nmsBlocksC, "CLAY");
            mGoldBF = field(nmsBlocksC, "GOLD_BLOCK");
            mWoolF = field(nmsBlocksC, "WOOL");
            mPackIceF = field(nmsBlocksC, "PACKED_ICE");
            mBoneBF = field(nmsBlocksC, "di");
            blockDataF = field(nmsBlockC, "blockData");
        }

        packetC = nmsClass("Packet");
        nmsPlayerC = nmsClass("EntityPlayer");
        nmsDataC = nmsClass("DataWatcher");
        nmsWorldC = nmsClass("World");
        nmsArmorC = nmsClass("EntityArmorStand");
        nbtTagC = nmsClass("NBTTagCompound");
        nmsIsC = nmsClass("ItemStack");
        nmsEntityC = nmsClass("Entity");
        pConnC = nmsClass("PlayerConnection");
        packAddEC = nmsClass("PacketPlayOutSpawnEntity");
        packEDataC = nmsClass("PacketPlayOutEntityMetadata");
        packRemEC = nmsClass("PacketPlayOutEntityDestroy");
        getBkEM = method(nmsEntityC, "getBukkitEntity");
        worldHandleM = method(obcWorldC, "getHandle");
        playerHandleM = method(obcPlayerC, "getHandle");
        asCbMM = method(obcIsC, "asCraftMirror", nmsIsC);
        asNmsCM = method(obcIsC, "asNMSCopy", ItemStack.class);
        sPackM = method(pConnC, "sendPacket", packetC);
        nmsTagM = method(nmsIsC, "getTag");
        hasTagM = method(nmsIsC, "hasTag");
        setTagM = method(nmsIsC, "setTag", nbtTagC);
        setIntM = method(nbtTagC, "setInt", String.class, int.class);
        getIntM = method(nbtTagC, "getInt", String.class);
        setUidM = method(nmsEntityC, "a", UUID.class);
        if (setUidM == null) {
            setUidM = method(nmsEntityC, "a_", UUID.class);
        }
        pConnF = field(nmsPlayerC, "playerConnection");
        dataF = field(nmsEntityC, "datawatcher");
        isAliveF = field(nmsEntityC, "dead");
        isValidF = field(nmsEntityC, "valid");
        eIdF = field(nmsEntityC, "id");
        armorCt = cons(nmsArmorC, nmsWorldC, double.class, double.class, double.class);
        paeCt = cons(packAddEC, nmsEntityC, int.class);
        pedCt = cons(packEDataC, int.class, nmsDataC, boolean.class);
        preCt = cons(packRemEC, int[].class);
        nbtTagCt = cons(nbtTagC);
        Object nmsWorld = invoke(worldHandleM, Bukkit.getWorlds().get(0));
        nmsArmor = newIns(armorCt, nmsWorld, 0, 0, 0);
        set(eIdF, nmsArmor, id);
        invoke(setUidM, nmsArmor, uuid);
        //set(isAliveF, nmsArmor, false); // dead -> false
        //set(isValidF, nmsArmor, false);
        bukkitArmor = (ArmorStand) invoke(getBkEM, nmsArmor);
        bukkitArmor.setMarker(true);
        bukkitArmor.setCustomNameVisible(true);
        bukkitArmor.setVisible(false);
        bukkitArmor.setGravity(false);
    }
    /*
    @Override
    public void addArmor(Player player, Location loc, String name) {
        bukkitArmor.teleport(loc);
        Object pc = get(pConnF, invoke(playerHandleM, player));
        Object pcAdd = newIns(paeCt, nmsArmor, 0);
        Object pcDat = newIns(pedCt, id, get(dataF, nmsArmor), true);
        invoke(sPackM, pc, pcAdd);
        invoke(sPackM, pc, pcDat);
    }

     */
    @Override
    public void addArmor(Player player, Location loc, String name) {
        Object ws = invoke(worldHandleM, loc.getWorld());
        Object nmsArmor0 = newIns(armorCt, ws, 0, 0, 0);
        invoke(setUidM, nmsArmor0, uuid);
        set(eIdF, nmsArmor0, id);
        //set(isValidF, nmsArmor, true);
        //set(isAliveF, nmsArmor, null); // RemovalReason -> null
        ArmorStand bukkitArmor0 = (ArmorStand) invoke(getBkEM, nmsArmor0);
        bukkitArmor0.setMarker(true);
        bukkitArmor0.setCustomNameVisible(true);
        bukkitArmor0.setVisible(false);
        bukkitArmor0.setGravity(false);
        bukkitArmor0.setCustomName(name);
        bukkitArmor0.teleport(loc);
        Object pc = get(pConnF, invoke(playerHandleM, player));
        Object pcAdd = newIns(paeCt, nmsArmor0, 0);
        Object pcDat = newIns(pedCt, id, get(dataF, nmsArmor0), true);
        invoke(sPackM, pc, pcAdd);
        invoke(sPackM, pc, pcDat);
    }

    @Override
    public void removeArmor(Player player) {
        Object pc = get(pConnF, invoke(playerHandleM, player));
        Object pcRem = newIns(preCt, (Object) new int[]{id});
        invoke(sPackM, pc, pcRem);
    }

    @Override
    public boolean isTunaStick(ItemStack is) {
        Object nmsIs = invoke(asNmsCM, null, is);
        if (!(boolean) invoke(hasTagM, nmsIs)) {
            invoke(setTagM, nmsIs, newIns(nbtTagCt));
        }
        Object nbt = invoke(nmsTagM, nmsIs);
        Integer it = (Integer) invoke(getIntM, nbt, "tunastick");
        if (it == null) {
            return false;
        }
        return it == 1;
    }

    @Override
    public ItemStack createTunaStick() {
        ItemStack is = new ItemStack(Material.STICK);
        is.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.AQUA + "Tuna调音棒");
        im.setLore(Arrays.asList("", ChatColor.GREEN + "放于手中, 点击音符盒即可调音"));
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        Object nmsIs = invoke(asNmsCM, null, is);
        if (!(boolean) invoke(hasTagM, nmsIs)) {
            invoke(setTagM, nmsIs, newIns(nbtTagCt));
        }
        Object nbt = invoke(nmsTagM, nmsIs);
        invoke(setIntM, nbt, "tunastick", 1);
        return (ItemStack) invoke(asCbMM, null, nmsIs);
    }

    @Override
    public int getNote(ItemStack is) {
        int note = 0;
        Object nmsIs = invoke(asNmsCM, null, is);
        if (!(boolean) invoke(hasTagM, nmsIs)) {
            invoke(setTagM, nmsIs, newIns(nbtTagCt));
        }
        Object nbt = invoke(nmsTagM, nmsIs);
        Integer it = (Integer) invoke(getIntM, nbt, "note");
        if (it != null) {
            note = it;
        }
        return note;
    }

    @Override
    public ItemStack setNote(ItemStack is, int note) {
        is.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.AQUA + trans("instrument-" + toKey(getIns(is))) + " (" + note + ", " + snote(note) + ")");
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        Object nmsIs = invoke(asNmsCM, null, is);
        if (!(boolean) invoke(hasTagM, nmsIs)) {
            invoke(setTagM, nmsIs, newIns(nbtTagCt));
        }
        Object nbt = invoke(nmsTagM, nmsIs);
        invoke(setIntM, nbt, "note", note);
        return (ItemStack) invoke(asCbMM, null, nmsIs);
    }

    @Override
    public Instrument getIns(ItemStack is) {
        byte ins = 0;
        Object nmsIs = invoke(asNmsCM, null, is);
        if (!(boolean) invoke(hasTagM, nmsIs)) {
            invoke(setTagM, nmsIs, newIns(nbtTagCt));
        }
        Object nbt = invoke(nmsTagM, nmsIs);
        Integer it = (Integer) invoke(getIntM, nbt, "inst");
        if (it != null) {
            ins = it.byteValue();
        }
        return Instrument.getByType(ins);
    }

    @Override
    public ItemStack setIns(ItemStack is, Instrument ins) {
        int note = getNote(is);
        is.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.AQUA + trans("instrument-" + toKey(ins)) + " (" + note + ", " + snote(note) + ")");
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        Object nmsIs = invoke(asNmsCM, null, is);
        if (!(boolean) invoke(hasTagM, nmsIs)) {
            invoke(setTagM, nmsIs, newIns(nbtTagCt));
        }
        Object nbt = invoke(nmsTagM, nmsIs);
        invoke(setIntM, nbt, "inst", ins.getType());
        return (ItemStack) invoke(asCbMM, null, nmsIs);
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
        Object clay = get(mClayF, null), gb = get(mGoldBF, null), wool = get(mWoolF, null), pi = get(mPackIceF, null), bb = get(mBoneBF, null);
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
}
