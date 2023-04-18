package de.shadowpvp.anticheat.Protections;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class AntiFlyProtection implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
            if (!player.hasPermission("shadowpvp.command.fly"));
            if (player.getLocation().subtract(0, 1, 0).getBlock().getType().isSolid()) {
                player.setAllowFlight(false);
                player.sendMessage("");
            }
        }
    }
}