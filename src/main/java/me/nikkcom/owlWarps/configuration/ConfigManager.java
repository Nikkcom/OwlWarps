package me.nikkcom.owlWarps.configuration;

import me.nikkcom.owlWarps.OwlWarps;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private final FileConfiguration config;

    public ConfigManager(OwlWarps owlWarps) {
        this.config = owlWarps.getConfig();
    }

    public int getDebugValue() {
        return config.getInt("debug", 900);
    }

    public String getLoggingPrefix() {
        return config.getString("loggerPrefix");
    }
    
    // Returns the storage type for the plugin.
    public String getStorageType() {
        return config.getString("storage.type", "file");
    }

    // MySQL settings from config.yml
    public String getMySQLHost() {
        return config.getString("storage.mysql.host");
    }

    public String getMySQLUsername() {
        return config.getString("storage.mysql.username");
    }

    public String getMySQLPassword() {
        return config.getString("storage.mysql.password");
    }

    public String getMySQLDatabase() {
        return config.getString("storage.mysql.database");
    }

    // Get file path for flat-file storage
    public String getFilePath() {
        return config.getString("storage.file.path", "data/playerwarps.yml");
    }
}
