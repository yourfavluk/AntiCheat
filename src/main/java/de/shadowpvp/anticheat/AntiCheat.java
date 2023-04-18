package de.shadowpvp.anticheat;

import de.shadowpvp.anticheat.AntiCrash.AntiCrash;
import de.shadowpvp.anticheat.Chat.ChatSpamProtection;
import de.shadowpvp.anticheat.Chat.ChatSpamProtection2;
import de.shadowpvp.anticheat.Commands.CommandSpy;
import de.shadowpvp.anticheat.Protections.AntiFlyProtection;
import de.shadowpvp.anticheat.Protections.CheatItemsProtection;
import de.shadowpvp.anticheat.Protections.FloodingProtection;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AntiCheat extends JavaPlugin {

    private ChatSpamProtection2 spamProtection;

    public static String prefix = "§3Guard §8» §f";

    @Override
    public void onEnable() {
        spamProtection = new ChatSpamProtection2();
        getLogger().info("AntiCheat v1 wurde geladen...");
        Bukkit.getPluginManager().registerEvents(new CheatItemsProtection(), this);
        Bukkit.getPluginManager().registerEvents(new FloodingProtection(), this);
        Bukkit.getPluginManager().registerEvents(new AntiCrash(), this);
        Bukkit.getPluginManager().registerEvents(new ChatSpamProtection(), this);
        Bukkit.getPluginManager().registerEvents(new ChatSpamProtection2(), this);

        Bukkit.getPluginManager().registerEvents(new AntiFlyProtection(), this);



        getCommand("commandspy").setExecutor(new CommandSpy());
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        getLogger().info("AntiCheat v1 wurde entladen...");
        // Plugin shutdown logic
    }
}
