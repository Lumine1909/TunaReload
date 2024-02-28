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
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.github.lumine1909.util.Mappings.*;

public class NMS_1_17 extends Reflection implements NMSBase {
    Class<?> packetC;
    Class<?> packAEC;
    Class<?> packREC;
    Class<?> nmsArC;
    Method getBkEM;
    Method setUidM;
    Method setIdM;
    Constructor<?> entiArCt;
    Constructor<?> packAECt;
    Constructor<?> packRECt;
    boolean isMeta2;
    public NMS_1_17() {
        super();
        try {
            packetC = clazz("net.minecraft.network.protocol.Packet");
            nmsWorldC = clazz("net.minecraft.world.level.World");
            pConnC = clazz("net.minecraft.server.network.PlayerConnection");
            nmsIsC = clazz("net.minecraft.world.item.ItemStack");
            packAEC = clazz("net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity");
            packREC = clazz("net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy");
            nmsPlayerC = clazz("net.minecraft.server.level.EntityPlayer");
            nmsEntityC = clazz("net.minecraft.world.entity.Entity");
            nbtTagC = clazz("net.minecraft.nbt.NBTTagCompound");
            nmsArC = clazz("net.minecraft.world.entity.decoration.EntityArmorStand");
            packEDataC = clazz("net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata");
            nmsDataC = clazz("net.minecraft.network.syncher.DataWatcher");
            getDataM = Arrays.stream(nmsEntityC.getDeclaredMethods()).filter(m -> m.getReturnType().equals(nmsDataC)).collect(Collectors.toList()).get(0);
            getDataM.setAccessible(true);
            getBkEM = method(nmsEntityC, "getBukkitEntity");
            asCbMM = method(obcIsC, "asCraftMirror", nmsIsC);
            setUidM = method(nmsEntityC, "a_", UUID.class);
            setIdM = method(nmsEntityC, "e", int.class);
            asNmsCM = method(obcIsC, "asNMSCopy", ItemStack.class);
            worldHandleM = method(obcWorldC, "getHandle");
            entiArCt = cons(nmsArC, nmsWorldC, double.class, double.class, double.class);
            if (ver < 2002) {
                pConnF = field(nmsPlayerC, "b");
            } else {
                pConnF = field(nmsPlayerC, "c");
            }
            if (ver > 1800) {
                packAllM = method(nmsDataC, "c");
                sPackM = method(pConnC, "a", packetC);
                setIntM = method(nbtTagC, "a", String.class, int.class);
                getIntM = method(nbtTagC, "h", String.class);
            } else {
                packAllM = method(nmsDataC, "packAll");
                sPackM = method(pConnC, "sendPacket", packetC);
                setIntM = method(nbtTagC, "setInt", String.class, int.class);
                getIntM = method(nbtTagC, "getInt", String.class);
            }
            if (ver > 2000) {
                pConnC = clazz("net.minecraft.server.network.ServerCommonPacketListenerImpl");
                sPackM = method(pConnC, "b", packetC);
            }
            packAECt = cons(packAEC, nmsEntityC);
            packRECt = cons(packREC, int[].class);
            packEDataCt = cons(packEDataC, int.class, nmsDataC, boolean.class);
            isMeta2 = false;
            if (packEDataCt == null) {
                packEDataCt = cons(packEDataC, int.class, List.class);//clazz("net.minecraft.network.syncher.DataWatcher.b"));
                isMeta2 = true;
            }
            if (ver < 1801) {
                nmsTagM = method(nmsIsC, "getOrCreateTag");
            } else if (ver < 1902) {
                nmsTagM = method(nmsIsC, "u");
            } else if (ver < 2002){
                nmsTagM = method(nmsIsC, "v");
            } else {
                nmsTagM = method(nmsIsC, "w");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object ws = invoke(worldHandleM, Bukkit.getWorlds().get(0));
        nmsArmor = newIns(entiArCt, ws, 0, 0, 0);
        invoke(setUidM, nmsArmor, uuid);
        invoke(setIdM, nmsArmor, id);
        bukkitArmor = (ArmorStand) invoke(getBkEM, nmsArmor);
        bukkitArmor.setMarker(true);
        bukkitArmor.setCustomNameVisible(true);
        bukkitArmor.setVisible(false);
        bukkitArmor.setGravity(false);
    }

    @Override
    protected Class<?> nmsClass(String name) {
        return null;
    }

    @Override
    public void addArmor(Player player, Location loc, String name) {
        bukkitArmor.teleport(loc);
        bukkitArmor.setCustomName(name);
        Object pc = get(pConnF, invoke(playerHandleM, player));
        Object packet = newIns(packAECt, nmsArmor);
        Object packet2 = buildData();
        invoke(sPackM, pc, packet);
        invoke(sPackM, pc, packet2);
    }

    public void removeArmor(Player player) {
        try {
            Object pc = get(pConnF, invoke(playerHandleM, player));
            Object packet = newIns(packRECt, new int[]{id});
            invoke(sPackM, pc, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ItemStack createTunaStick() {
        ItemStack ts = new ItemStack(Material.STICK);
        ts.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
        ItemMeta im = ts.getItemMeta();
        im.setDisplayName(ChatColor.AQUA + "Tuna调音棒");
        im.setLore(Arrays.asList("", ChatColor.GREEN + "放于手中, 点击音符盒即可调音"));
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ts.setItemMeta(im);
        Object nmsIs = invoke(asNmsCM, null, ts);
        Object nbt = invoke(nmsTagM, nmsIs);
        invoke(setIntM, nbt, "tunastick", 1);
        return (ItemStack) invoke(asCbMM, null, nmsIs);
    }

    @Override
    public int getNote(ItemStack is) {
        int note = 0;
        Object nmsIs = invoke(asNmsCM, null, is);
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
        Object nbt = invoke(nmsTagM, nmsIs);
        invoke(setIntM, nbt, "note", note);
        return (ItemStack) invoke(asCbMM, null, nmsIs);
    }

    @Override
    public Instrument getIns(ItemStack is) {
        byte ins = 0;
        Object nmsIs = invoke(asNmsCM, null, is);
        Object nbt = invoke(nmsTagM, nmsIs);
        Integer it = (Integer) invoke(getIntM, nbt, "inst");
        if (it != null) {
            ins = it.byteValue();
        }
        return Instrument.getByType(ins);
    }

    @Override
    public ItemStack setIns(ItemStack is, Instrument ins) {
        is.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
        ItemMeta im = is.getItemMeta();
        int note = getNote(is);
        int inst = ins.getType();
        im.setDisplayName(ChatColor.AQUA + trans("instrument-" + toKey(ins)) + " (" + note + ", " + snote(note) + ")");
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        Object nmsIs = invoke(asNmsCM, null, is);
        Object nbt = invoke(nmsTagM, nmsIs);
        invoke(setIntM, nbt, "inst", inst);
        return (ItemStack) invoke(asCbMM, null, nmsIs);
    }

    @Override
    public Byte getNoteByte(Block block) {
        // Not support in higher version
        return null;
    }

    @Override
    public boolean isTunaStick(ItemStack is) {
        Object nmsIs = invoke(asNmsCM, null, is);
        Object nbt = invoke(nmsTagM, nmsIs);
        Integer it = (Integer) invoke(getIntM, nbt, "tunastick");
        if (it == null) {
            return false;
        } else {
            return it == 1;
        }
    }

    private Object buildData() {
        try {
            if (isMeta2) {
                return  packEDataCt.newInstance(id, getDataList());
            } else {
                return packEDataCt.newInstance(id, getData(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private Object getData() {
        try {
            return getDataM.invoke(nmsArmor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private Object getDataList() {
        try {
            return packAllM.invoke(getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
