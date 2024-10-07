package me.nikkcom.owlWarps;

import me.nikkcom.owlWarps.GUI.events.MenuEvents;
import me.nikkcom.owlWarps.commands.pwarp.PwarpManager;
import me.nikkcom.owlWarps.configuration.ConfigManager;
import me.nikkcom.owlWarps.configuration.Messages;
import me.nikkcom.owlWarps.data.storage.DataStorage;
import me.nikkcom.owlWarps.data.storage.file.MessageFileDataStorage;
import me.nikkcom.owlWarps.data.storage.file.PlayerWarpFileDataStorage;
import me.nikkcom.owlWarps.playerwarps.PlayerWarp;
import me.nikkcom.owlWarps.playerwarps.PlayerWarpManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class OwlWarps extends JavaPlugin {

    private static int debugValue;
    private static String loggerPrefix;
    private final String localePath = "locale/en_US.json";
    private ConfigManager configManager;
    private DataStorage<PlayerWarp> playerWarpStorage;
    private DataStorage<String> messagesStorage;

    public static void log(Level level, String message) {
        if (debugValue <= level.intValue()) {
            Bukkit.getLogger().log(level, loggerPrefix + message);
        }
    }

    @Override
    public void onDisable() {
        log(Level.INFO, "Plugin is being disabled...");
        PlayerWarpManager.getInstance().unloadPlayerWarps();
        log(Level.INFO, "Playerwarps have been unloaded.");
        log(Level.INFO, "Plugin has been disabled successfully.");
    }

    @Override
    public void onEnable() {
        log(Level.INFO, "Starting OwlWarps plugin...");
        saveDefaultConfig();
        this.configManager = new ConfigManager(this);
        debugValue = configManager.getDebugValue();
        loggerPrefix = configManager.getLoggingPrefix();
        log(Level.INFO, String.format("Debug level set to %d", debugValue));

        // Hook to placeholderAPI
        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            log(Level.SEVERE, "Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        playerWarpStorage = new PlayerWarpFileDataStorage(this);
        PlayerWarpManager.initialize(this, playerWarpStorage);
        log(Level.INFO, "PlayerWarpFileDataStorage initialized and PlayerWarpManager set up.");

        messagesStorage = new MessageFileDataStorage(this);
        Messages.initialize(messagesStorage);

        log(Level.INFO, "Registering plugin events...");
        log(Level.FINER, "Registering MenuEvents.class");
        getServer().getPluginManager().registerEvents(new MenuEvents(), this);
        log(Level.INFO, "Successfully registered all plugin events.");

        log(Level.INFO, "Setting plugin commands...");
        getCommand("pwarp").setExecutor(new PwarpManager(this));
        getCommand("pwarp").setTabCompleter(new PwarpManager(this));

        log(Level.INFO, "Completed setting plugin commands!");
    }

    public void reloadPluginConfig() {
        // Reload config from file
        reloadConfig();
        log(Level.INFO, "Config reloaded successfully!");
    }
}
