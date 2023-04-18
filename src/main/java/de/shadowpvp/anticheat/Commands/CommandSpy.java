package de.shadowpvp.anticheat.Commands;

import de.shadowpvp.anticheat.AntiCheat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class CommandSpy implements CommandExecutor {
    private Plugin plugin = null;
    private final List<Player> spyList;

    public CommandSpy() {
        this.plugin = plugin;
        spyList = new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Nur Spieler können diesen Befehl nutzen!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            toggleSpy(player);
            return true;
        }

        if (!player.hasPermission("shadowpvp.command.commandspy")) {
            player.sendMessage(AntiCheat.prefix + "§cDazu hast du keine Rechte.");
            return true;
        }

        if (args[0].equalsIgnoreCase("on")) {
            if (spyList.contains(player)) {
                player.sendMessage(AntiCheat.prefix + "§7Du hast den CommandSpy bereits §aaktiviert§7!");
                return true;
            }

            spyList.add(player);
            player.sendMessage(AntiCheat.prefix + "§7CommandSpy §aaktiviert§7!");

        } else if (args[0].equalsIgnoreCase("off")) {
            if (!spyList.contains(player)) {
                player.sendMessage(AntiCheat.prefix + "§cDu hast den CommandSpy nicht aktiviert!");
                return true;
            }

            spyList.remove(player);
            player.sendMessage(AntiCheat.prefix + "§7CommandSpy §cdeaktiviert§7!");

        } else {
            player.sendMessage(AntiCheat.prefix + "§cUngültiges Argument! Verwende: /commandspy [on/off]");
        }

        return true;
    }

    private void toggleSpy(Player player) {
        if (spyList.contains(player)) {
            spyList.remove(player);
            player.sendMessage(AntiCheat.prefix + "§7CommandSpy §cdeaktiviert§7!");
        } else {
            spyList.add(player);
            player.sendMessage(AntiCheat.prefix+ "§7CommandSpy §aaktiviert§7!");
        }
    }

    public void spyCommand(String command, Player sender) {
        for (Player player : spyList) {
            if (!player.equals(sender)) {
                player.sendMessage(AntiCheat.prefix + "§7" + sender.getName() + ": §a" + command);
            }
        }
    }
}
