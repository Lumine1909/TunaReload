package io.github.lumine1909.settings;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

import static io.github.lumine1909.util.PlayerConfig.loadSettings;
import static io.github.lumine1909.util.PlayerConfig.saveSettings;

public class PlayerSettings {
    public static Map<Player, PlayerSettings> settingsMap = new HashMap<>();
    public Player player;
    public boolean ENABLE_NOTE;
    public boolean ENABLE_INST;
    public boolean SYNC_INST;
    public Block block;

    public PlayerSettings(Player player, boolean b1, boolean b2, boolean b3) {
        this.player = player;
        this.ENABLE_NOTE = b1;
        this.ENABLE_INST = b2;
        this.SYNC_INST = b3;
    }

    public static PlayerSettings get(Player player) {
        if (!settingsMap.containsKey(player)) {
            settingsMap.put(player, loadSettings(player));
        }
        return settingsMap.get(player);
    }

    public static void remove(Player player) {
        saveSettings(settingsMap.get(player));
        settingsMap.remove(player);
    }

    public static void saveAll() {
        settingsMap.forEach((k, v) -> saveSettings(v));
    }
}