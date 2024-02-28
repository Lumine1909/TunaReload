package io.github.lumine1909.base;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class ShowInfoTask extends BukkitRunnable {
    static protected class Buffer {
        public Object nb;
        public Block b;
        public Buffer(Object nb, Block b) {
            this.nb = nb;
            this.b = b;
        }
    }
    protected final Map<UUID, Buffer> bufferMap = new HashMap<>();
    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            int i = getUpDate(p);
            if (i == 0) {
                update(p);
            } else if (i == 1) {
                if (bufferMap.containsKey(p.getUniqueId())) {
                    delete(p);
                    bufferMap.remove(p.getUniqueId());
                }
            }
        }
    }
    protected abstract int getUpDate(Player player);
    protected abstract void update(Player player);
    protected abstract void delete(Player player);
}
