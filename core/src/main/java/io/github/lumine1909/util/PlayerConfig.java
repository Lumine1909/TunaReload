package io.github.lumine1909.util;

import io.github.lumine1909.settings.PlayerSettings;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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
        Bukkit.getScheduler().runTaskAsynchronously(pl, () -> {
            try {
                File pFile = new File(playerFolder, s.player.getUniqueId() + ".yml");
                if (!pFile.exists()) {
                    pFile.createNewFile();
                }
                FileConfiguration cfg = YamlConfiguration.loadConfiguration(pFile);
                cfg.set("enable-note", s.ENABLE_NOTE);
                cfg.set("enable-instrument", s.ENABLE_INST);
                cfg.set("sync-instrument", s.SYNC_INST);
                cfg.set("scroll-item", s.SCROLL_ITEM);
                cfg.set("scroll-block", s.SCROLL_BLOCK);
                cfg.save(pFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
            boolean b4 = cfg.getBoolean("scroll-item", false);
            boolean b5 = cfg.getBoolean("scroll-block", false);
            return new PlayerSettings(player, b1, b2, b3, b4, b5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}