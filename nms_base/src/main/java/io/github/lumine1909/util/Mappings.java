package io.github.lumine1909.util;

import io.github.lumine1909.base.BlockHandler;
import io.github.lumine1909.nms.NMSBase;
import io.github.lumine1909.settings.ConfigSettings;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class Mappings {
    public static NMSBase nms;
    public static JavaPlugin plugin;
    public static ConfigSettings settings;
    public static BlockHandler bh;
    private static final Map<String, String> trans = new HashMap<>();
    private static final Map<String, Integer> noteMap = new HashMap<>();
    private static final Map<Instrument, String> insMap = new HashMap<>();
    private static final Map<String, Instrument> sinsMap = new HashMap<>();


    public static final Map<Material, String> minsMap = new HashMap<>();
    public static final Map<String, Material> sminsMap = new HashMap<>();

    public static List<String> snoteList = new ArrayList<>();
    public static List<String> sinstList;
    public static void init(JavaPlugin pl) {
        plugin = pl;
        snoteList = Arrays.asList("FS-", "G-", "GS-", "A-", "AS-", "B-", "C",
                "CS", "D", "DS", "E", "F", "FS", "G", "GS", "A", "AS", "B",
                "C+", "CS+", "D+", "DS+", "E+", "F+", "FS+");
        for (int i = 0; i <= 24; i++) {
            noteMap.put(snoteList.get(i), i);
        }
        putIns("PIANO", "harp");
        putIns("BASS_DRUM", "basedrum");
        putIns("SNARE_DRUM", "snare");
        putIns("STICKS", "hat");
        putIns("BASS_GUITAR", "bass");
        putIns("FLUTE", "flute");
        putIns("BELL", "bell");
        putIns("GUITAR", "guitar");
        putIns("CHIME", "chime");
        putIns("XYLOPHONE", "xylophone");
        putIns("IRON_XYLOPHONE", "iron_xylophone");
        putIns("COW_BELL", "cow_bell");
        putIns("DIDGERIDOO", "didgeridoo");
        putIns("BIT", "bit");
        putIns("BANJO", "banjo");
        putIns("PLING", "pling");
        /* Next Version
        putIns("ZOMBIE", "zombie");
        putIns("SKELETON", "skeleton");
        putIns("CREEPER", "creeper");
        putIns("DRAGON", "ender_dragon");
        putIns("WITHER_SKELETON", "wither_skeleton");
        putIns("PIGLIN", "piglin");
         */
        loadMaterials();
        sinstList = new ArrayList<>(sinsMap.keySet());

        trans.put("note-gui-name", color("&2&l音高调节"));

        loadConfig();
    }
    private static void putIns(String str, String key) {
        try {
            Instrument ins = Instrument.valueOf(str);
            insMap.put(ins, key);
            sinsMap.put(key, ins);
        } catch (Exception ignored) {
        }
    }
    private static void putSBIns(String key, String str) {
        try {
            Material m = Material.valueOf(str);
            Instrument ins = toBkIns(key);
            sminsMap.put(key, m);
        } catch (Exception ignored) {
        }
    }
    private static void loadMaterials() {
        putSBIns("harp", "DIRT");
        putSBIns("snare", "SNAD");
        putSBIns("hat", "GLASS");
        putSBIns("basedrum", "STONE");
        putSBIns("bell", "GOLD_BLOCK");
        putSBIns("flute", "CLAY");
        putSBIns("chime", "PACKED_ICE");
        putSBIns("guitar", "WOOL");
        putSBIns("guitar", "WHITE_WOOL");
        putSBIns("xylophone", "BONE_BLOCK");
        putSBIns("iron_xylophone", "IRON_BLOCK");
        putSBIns("cow_bell", "SOUL_SAND");
        putSBIns("didgeridoo", "PUMPKIN");
        putSBIns("bit", "EMERALD_BLOCK");
        putSBIns("banjo", "HAY_BLOCK");
        putSBIns("pling", "GLOWSTONE");
        sminsMap.forEach((k, v) -> minsMap.put(v, k));
    }
    private static String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
    private static void loadConfig() {
        File transFile = new File(plugin.getDataFolder(), "translation.yml");
        if (transFile.isDirectory()) {
            transFile.delete();
        }
        if (!transFile.exists()) {
            plugin.saveResource("translation.yml", false);
        }
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(transFile);
        for (String key : cfg.getKeys(false)) {
            trans.put(key, color(cfg.getString(key)));
        }
    }
    public static String trans(String key) {
        return trans.get(key);
    }
    public static String snote(int note) {
        if (note > 24 || note < 0) {
            return null;
        }
        return snoteList.get(note).replaceAll("S", "#");
    }
    public static int note(String snote) {
        return noteMap.getOrDefault(snote, -1);
    }
    public static String toKey(Instrument ins) {
        return insMap.get(ins);
    }
    public static Instrument toBkIns(String key) {
        return sinsMap.getOrDefault(key.toLowerCase(), null);
    }
    public static Material getMaterial(String key) {
        return sminsMap.get(key);
    }
    public static String getKey(Block block) {
        return toKey(Instrument.getByType(nms.getNoteByte(block)));
    }
}