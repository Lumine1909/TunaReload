package io.github.lumine1909.util;

import io.github.lumine1909.settings.PlayerSettings;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

import static io.github.lumine1909.Tuna.pl;

public class PlayerConfig {
    private static File playerFolder;
    public static void init() {
        playerFolder = new File(pl.getDataFolder(), "players");
        if (playerFolder.isFile()) {
            playerFolder.delete();
        }
        if (!playerFolder.exists()) {
            playerFolder.mkdirs();
        }
    }
    public static void saveSettings(PlayerSettings s) {
        try {
            File pFile = new File(playerFolder, s.player.getUniqueId() + ".yml");
            if (!pFile.exists()) {
                pFile.createNewFile();
            }
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(pFile);
            cfg.set("enable-note", s.ENABLE_NOTE);
            cfg.set("enable-instrument", s.ENABLE_INST);
            cfg.set("sync-instrument", s.SYNC_INST);
            cfg.save(pFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static PlayerSettings loadSettings(Player player) {
        try {
            File pFile = new File(playerFolder, player.getUniqueId() + ".yml");
            if (!pFile.exists()) {
                pFile.createNewFile();
            }
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(pFile);
            boolean b1 = cfg.getBoolean("enable-note", true);
            boolean b2 = cfg.getBoolean("enable-instrument", true);
            boolean b3 = cfg.getBoolean("sync-instrument", false);
            return new PlayerSettings(player, b1, b2, b3);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
