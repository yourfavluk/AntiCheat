package de.shadowpvp.anticheat.Chat;

import de.shadowpvp.anticheat.AntiCheat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;

public class ChatSpamProtection implements Listener {

    private Map<Player, String> lastMessages = new HashMap<>();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        String message = event.getMessage().trim();
        String lastMessage = lastMessages.get(player);

        if (lastMessage != null && message.equalsIgnoreCase(lastMessage)) {
            event.setCancelled(true);
            player.sendMessage(AntiCheat.prefix + "§cBitte vermeide das Spammen von Nachrichten!");
            return;
        }

        lastMessages.put(player, message);
    }
}
