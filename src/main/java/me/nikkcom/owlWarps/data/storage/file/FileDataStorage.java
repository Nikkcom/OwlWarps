package me.nikkcom.owlWarps.data.storage.file;

import me.nikkcom.owlWarps.OwlWarps;
import me.nikkcom.owlWarps.data.storage.DataStorage;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;

public abstract class FileDataStorage<T> implements DataStorage<T> {

    protected final File file;
    protected YamlConfiguration yamlConfig;

    public FileDataStorage(OwlWarps owlWarps, String filename) {
        this.file = new File(owlWarps.getDataFolder() + "/data/" + filename);

        if (!file.exists()) {
            OwlWarps.log(Level.FINE, String.format("Attempting to create file '%s'.", file.getName()));
            try {
                file.createNewFile();
                OwlWarps.log(Level.INFO, String.format("Created file '%s'.", file.getName()));

                // Load the default messages.yml from resources
                try (InputStream inputStream = owlWarps.getClass().getResourceAsStream("/data/" + filename)) {
                    if (inputStream != null) {
                        // Copy the resource file to the data folder
                        Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        OwlWarps.log(Level.INFO, String.format("Copied default to %s", file.getPath()));
                    } else {
                        OwlWarps.log(Level.SEVERE, "Default resource not found in JAR for. " + filename);
                    }
                }


            } catch (IOException exception) {
                OwlWarps.log(Level.SEVERE, String.format("Could not create file '%s'!", file.getName()));
                OwlWarps.log(Level.SEVERE, exception.getMessage());
                exception.printStackTrace();
            }
        }
        this.yamlConfig = YamlConfiguration.loadConfiguration(file);
    }

    protected void saveConfig() {
        try {
            yamlConfig.save(file);
            OwlWarps.log(Level.INFO, String.format("Saved data to file. '%s'", file.getName()));
        } catch (IOException exception) {
            OwlWarps.log(Level.SEVERE, String.format("Could not save to file '%s'!", file.getName()));
            OwlWarps.log(Level.SEVERE, exception.getMessage());
            exception.printStackTrace();
        }
    }
}
