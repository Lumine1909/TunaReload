package io.github.lumine1909.command;

import io.github.lumine1909.object.Instrument;
import io.github.lumine1909.settings.PlayerSettings;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.github.lumine1909.Tuna.pl;
import static io.github.lumine1909.util.Mappings.*;

public class CommandSetBlockIns implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("tuna.blockinst")) {
            player.sendMessage(trans("no-perms"));
            return true;
        }
        if (args.length < 1) {
            player.sendMessage(trans("bad-command-format"));
            return true;
        }
        io.github.lumine1909.object.Instrument ins = new Instrument(args[0]);
        if (ins.checkError() || ins.isNull()) {
            player.sendMessage(trans("bad-instrument"));
            return true;
        }
        Block b = player.getTargetBlock(null, 5);
        if (!(b.getType() == Material.NOTE_BLOCK)) {
            player.sendMessage(trans("not-facing-noteblock"));
            return true;
        }
        pl.bh.setBlockIns(b, ins.getInstrument(), PlayerSettings.get(player).SYNC_INST);
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
