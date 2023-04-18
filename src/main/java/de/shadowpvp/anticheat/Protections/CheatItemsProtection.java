package de.shadowpvp.anticheat.Protections;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheatItemsProtection implements Listener {
    private Plugin plugin;

    private HashMap<Player, List<ItemStack>> playerItemMap;

    public CheatItemsProtection() {
        this.plugin = plugin;
        this.playerItemMap = new HashMap<>();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.playerItemMap.put(player, new ArrayList<>());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        PlayerInventory inventory = player.getInventory();
        List<ItemStack> illegalItems = new ArrayList<>();

        for (ItemStack item : inventory.getContents()) {
            if (item != null && isIllegalItem(item)) {
                illegalItems.add(item);
            }
        }

        if (illegalItems.size() > 0) {
            String message = String.format("§3Guard §8» §fÜberprüfe diesen Spieler (CheatItems) §c" + player.getName());
            for (Player admin : Bukkit.getOnlinePlayers()) {
                if (admin.hasPermission("shadowpvp.notify.banneditems")) {
                    admin.sendMessage(message);
                }
            }

            this.playerItemMap.put(player, illegalItems);
        }
    }

    private boolean isIllegalItem(ItemStack item) {
        Material itemType = item.getType();
        return itemType == Material.COMMAND_BLOCK || itemType == Material.BARRIER || itemType == Material.END_PORTAL_FRAME;
    }

    private class TeleportRequest {
        private Player sender;
        private Player receiver;


        }

    }

