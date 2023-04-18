package de.shadowpvp.anticheat.Chat;

import de.shadowpvp.anticheat.AntiCheat;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AntiAdvertising extends JavaPlugin implements Listener {

    private Pattern urlPattern = Pattern.compile("^(https?://)?([a-zA-Z0-9]+\\.)+[a-zA-Z]+(/\\S*)?$", Pattern.CASE_INSENSITIVE);

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Matcher matcher = urlPattern.matcher(message);
        if (matcher.find()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(AntiCheat.prefix + "§cDas Posten von Links ist nicht erlaubt.");
        }
    }
}
