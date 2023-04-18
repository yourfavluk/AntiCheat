package de.shadowpvp.anticheat.Chat;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class ChatSpamProtection2 implements Listener {

    private Plugin plugin;
    private Map<Player, Long> lastMessageTimes = new HashMap<>();
    private long messageInterval = 3000; // in milliseconds
    private String bypassPermission = "spam.bypass";

    public ChatSpamProtection2() {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission(bypassPermission)) {
            return;
        }

        if (lastMessageTimes.containsKey(player)) {
            long lastMessageTime = lastMessageTimes.get(player);
            long currentTime = System.currentTimeMillis();

            if (currentTime - lastMessageTime < messageInterval) {
                event.setCancelled(true);
                player.sendMessage("§cDu hast den Chat zu schnell benutzt. Bitte warte " + (messageInterval - (currentTime - lastMessageTime)) / 1000 + " Sekunden.");
            }
        }

        lastMessageTimes.put(player, System.currentTimeMillis());
    }
}