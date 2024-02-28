package io.github.lumine1909.command;

import io.github.lumine1909.base.TunaGUI;
import io.github.lumine1909.settings.PlayerSettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

import static io.github.lumine1909.util.Mappings.trans;

public class CommandTunaGUI implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("tuna.gui")) {
            player.sendMessage(trans("no-perms"));
            return true;
        }
        player.openInventory(new TunaGUI(PlayerSettings.get(player)).getInventory());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
