package de.shadowpvp.anticheat.AntiCrash;

import de.shadowpvp.anticheat.AntiCheat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class AntiCrash implements Listener {

    private Plugin plugin = null;
    private final Map<Player, Integer> placeCount;
    private final Map<Player, Integer> clickCount;

    public AntiCrash() {
        this.plugin = plugin;
        this.placeCount = new HashMap<>();
        this.clickCount = new HashMap<>();
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (event.getBlock().getType() == Material.TNT) {
            int count = placeCount.getOrDefault(player, 0) + 1;
            placeCount.put(player, count);
            if (count >= 10) {
                Bukkit.getScheduler().runTask(plugin, () -> {
                    event.getBlock().setType(Material.AIR);
                    event.getBlock().getLocation().getWorld().createExplosion(event.getBlock().getLocation(), 4.0f);
                    player.sendMessage(AntiCheat.prefix + "§cDu darfst nicht soviel TNT setzen.");
                });
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        if (item != null && item.getType() == Material.WRITABLE_BOOK) {
            int count = clickCount.getOrDefault(player, 0) + 1;
            clickCount.put(player, count);
            if (count >= 20 && !player.hasPermission("shadowpvp.anticrash.bypass")) {
                Bukkit.getScheduler().runTask(plugin, () -> {
                    event.setCancelled(true);
                    player.sendMessage(AntiCheat.prefix + "§cUnterlasse das schnelle öffnen von Schreibbücher, dies ist ein Crash Versuch.");
                });
            }
        }
    }
}
