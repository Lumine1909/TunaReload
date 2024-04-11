package io.github.lumine1909.command;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

import static io.github.lumine1909.Tuna.pl;
import static io.github.lumine1909.util.Mappings.*;

public class CommandSetBlockNote implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("tuna.blocknote")) {
            player.sendMessage(trans("no-perms"));
            return true;
        }
        if (args.length < 1) {
            player.sendMessage(trans("bad-command-format"));
            return true;
        }
        int note;
        try {
            note = Integer.parseInt(args[0]);
        } catch (Exception e) {
            note = note(args[0].toUpperCase());
        }
        if (note > 24 || note < 0) {
            player.sendMessage(trans("bad-note"));
            return true;
        }
        Block b = player.getTargetBlock(null, 5);
        if (!(b.getType() == Material.NOTE_BLOCK)) {
            player.sendMessage(trans("not-facing-noteblock"));
            return true;
        }
        pl.bh.setBlockNote(b, note);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length <= 1) {
            return snoteList;
        }
        return Collections.emptyList();
    }
}
