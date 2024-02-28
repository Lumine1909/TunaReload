package io.github.lumine1909.settings;

import org.bukkit.configuration.file.FileConfiguration;

import static io.github.lumine1909.util.Mappings.plugin;


public class ConfigSettings {
    public boolean SYNC_INSTRUMENT;
    public ConfigSettings() {
    }
    public void reload() {
        FileConfiguration cfg = plugin.getConfig();
        SYNC_INSTRUMENT = cfg.getBoolean("sync-instrument", false);
    }
}
