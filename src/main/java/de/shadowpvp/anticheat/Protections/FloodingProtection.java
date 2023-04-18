package de.shadowpvp.anticheat.Protections;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

public class FloodingProtection implements Listener {

    private final int maxBlocksPerSecond = 10;
    private final int maxBlocksPerInterval = 100;
    private final int intervalTime = 5;
    private final int warningThreshold = 3;
    private final HashMap<UUID, Integer> blockCounts = new HashMap<>();
    private final HashMap<UUID, Integer> warnings = new HashMap<>();

    public FloodingProtection() {
        Plugin plugin = null;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        Bukkit.getScheduler().runTaskTimer(plugin, () -> blockCounts.clear(), 0L, intervalTime * 20L);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        int blockCount = blockCounts.getOrDefault(uuid, 0);
        blockCounts.put(uuid, blockCount + 1);

        if (blockCount > maxBlocksPerSecond) {
            int warningCount = warnings.getOrDefault(uuid, 0);
            if (warningCount < warningThreshold) {
                player.sendMessage("§3Guard §8»§f §cDu baust zu schnell! Bitte verlangsame dein Tempo.");
                warnings.put(uuid, warningCount + 1);
            } else {
                Bukkit.getOnlinePlayers().stream()
                        .filter(p -> p.hasPermission("notify.flooding"))
                        .forEach(p -> p.sendMessage("§3Guard §8»§f §c" + player.getName() + " wurde als 'Flooding' markiert!"));
                player.kickPlayer("§3Guard §8» §f§cDu wurdest als 'Flooding' markiert!");
            }
        }

        if (blockCount > maxBlocksPerInterval) {
            Bukkit.getOnlinePlayers().stream()
                    .filter(p -> p.hasPermission("notify.flooding"))
                    .forEach(p -> p.sendMessage("§3Guard §8» §f§c" + player.getName() + " wurde als 'Flooding' markiert!"));
            player.kickPlayer("§3Guard §8» §f§cDu wurdest als 'Flooding' markiert!");
        }
    }
}