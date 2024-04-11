package io.github.lumine1909.command;

import io.github.lumine1909.base.TunaGUI;
import io.github.lumine1909.settings.PlayerSettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

import static io.github.lumine1909.Tuna.pl;
import static io.github.lumine1909.util.Mappings.trans;

public class CommandTuna implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("tuna.admin")) {
            sender.sendMessage(trans("no-perms"));
            return true;
        }
        pl.callReload();
        sender.sendMessage(trans("plugin-reloaded"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("tuna.admin")) {
            return Collections.singletonList("reload");
        }
        return Collections.emptyList();
    }
}
