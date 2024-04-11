package io.github.lumine1909;

import io.github.lumine1909.base.*;
import io.github.lumine1909.command.*;
import io.github.lumine1909.impl_1_7.BlockHandlerImpl;
import io.github.lumine1909.impl_1_7.ShowInfoTaskImpl;
import io.github.lumine1909.nms.NMSBase;
import io.github.lumine1909.nms.NMS_1_13;
import io.github.lumine1909.nms.NMS_1_17;
import io.github.lumine1909.settings.ConfigSettings;
import io.github.lumine1909.settings.PlayerSettings;
import io.github.lumine1909.util.Mappings;
import io.github.lumine1909.util.PlayerConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class Tuna extends JavaPlugin {
    public static Tuna pl;
    public ItemHandler ih;
    public BlockHandler bh;
    public InsGUI ig;
    public NoteGUI ng;
    public TunaListener lis;
    public NMSBase nms;
    public ConfigSettings settings;
    public BukkitTask task;
    @Override
    public void onLoad() {
        pl = this;
        saveDefaultConfig();
    }

    public static void callReload() {
        Mappings.init(pl);
        PlayerConfig.init();
        PlayerSettings.settingsMap = new HashMap<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerSettings.get(p);
        }
        pl.ig = new InsGUI();
        pl.ng = new NoteGUI();
    }
    @Override
    public void onDisable() {
        task.cancel();
    }

    @Override
    public void onEnable() {

        // 1.12.2 -> 1_12_R1
        String sv = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        // 1, XX, Rxx
        String[] strvers = sv.split("_");
        // 1_12_R1 -> 1201
        int ver = Integer.parseInt(strvers[1]) * 100 + Integer.parseInt(strvers[2].substring(1));

        Mappings.init(pl);
        PlayerConfig.init();
        settings = new ConfigSettings();
        Mappings.settings = settings;
        settings.reload();
        if (ver < 1300) {
            //nms = new NMS_1_7();
            nms = new NMS_1_13();
            bh = new BlockHandlerImpl();
            Mappings.nms = nms;
            Mappings.bh = bh;
            task = new ShowInfoTaskImpl().runTaskTimer(this, 0, 2);
        } else if (ver < 1700) {
            nms = new NMS_1_13();
            bh = new io.github.lumine1909.impl_1_13.BlockHandlerImpl();
            Mappings.nms = nms;
            Mappings.bh = bh;
            task = new io.github.lumine1909.impl_1_13.ShowInfoTaskImpl().runTaskTimer(this, 0, 2);
        } else {
            nms = new NMS_1_17();
            bh = new io.github.lumine1909.impl_1_13.BlockHandlerImpl();
            Mappings.nms = nms;
            Mappings.bh = bh;
            task = new io.github.lumine1909.impl_1_13.ShowInfoTaskImpl().runTaskTimer(this, 0, 2);
        }

        ig = new InsGUI();
        ng = new NoteGUI();
        ih = new ItemHandler();
        lis = new TunaListener();
        getCommand("note").setExecutor(new CommandSetNote());
        getCommand("blocknote").setExecutor(new CommandSetBlockNote());
        getCommand("instrument").setExecutor(new CommandSetIns());
        getCommand("blockinstrument").setExecutor(new CommandSetBlockIns());
        getCommand("tunastick").setExecutor(new CommandTunaStick());
        getCommand("tunagui").setExecutor(new CommandTunaGUI());
        getCommand("tuna").setExecutor(new CommandTuna());
    }
}
