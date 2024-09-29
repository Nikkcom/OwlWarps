package me.nikkcom.owlWarps;

import me.nikkcom.owlWarps.GUI.events.MenuEvents;
import me.nikkcom.owlWarps.commands.pwarp.OwlWarpsManager;
import me.nikkcom.owlWarps.commands.pwarp.PwarpManager;
import me.nikkcom.owlWarps.configuration.LocaleLoader;
import org.bukkit.plugin.java.JavaPlugin;

public final class OwlWarps extends JavaPlugin {

    private LocaleLoader localeLoader;

    @Override
    public void onEnable() {

        // Initializes the plugin messages loader.
        localeLoader = new LocaleLoader(this);
        localeLoader.loadMessages("locale/en_US.json");
        // Registering event classes
        getServer().getPluginManager().registerEvents(new MenuEvents(), this);

        getCommand("pwarp").setExecutor(new PwarpManager(this));
        getCommand("pwarp").setTabCompleter(new PwarpManager(this));

        getCommand("owlwarps").setExecutor(new OwlWarpsManager(this));
        getCommand("owlwarps").setTabCompleter(new OwlWarpsManager(this));

        // Example of retrieving a message
        String versionMessage = localeLoader.getMessage("version-message");
        getLogger().info(versionMessage != null ? versionMessage : "Message not found.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
