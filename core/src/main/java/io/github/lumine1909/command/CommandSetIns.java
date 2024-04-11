package io.github.lumine1909.command;

import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

import static io.github.lumine1909.Tuna.pl;
import static io.github.lumine1909.util.Mappings.*;

public class CommandSetIns implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("tuna.inst")) {
            player.sendMessage(trans("no-perms"));
            return true;
        }
        if (args.length < 1) {
            player.sendMessage(trans("bad-command-format"));
            return true;
        }
        Instrument ins = toBkIns(args[0]);
        if (ins == null) {
            player.sendMessage(trans("bad-instrument"));
            return true;
        }
        ItemStack is = player.getInventory().getItemInMainHand();
        if (is.getType() != Material.NOTE_BLOCK) {
            player.sendMessage(trans("not-handing-noteblock"));
            return true;
        }
        player.getInventory().setItemInMainHand(pl.ih.setIns(is, ins));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length <= 1) {
            return sinstList;
        }
        return Collections.emptyList();
    }
}
