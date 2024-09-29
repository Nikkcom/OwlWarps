package me.nikkcom.owlWarps.configuration;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import me.nikkcom.owlWarps.OwlWarps;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * A class for loading and managing plugin messages from json.
 */
public class LocaleLoader {
    private final OwlWarps owlWarps;
    private Map<String, String> messages;

    /**
     * Constructor
     *
     * @param owlWarps The main plugin instance.
     */
    public LocaleLoader(OwlWarps owlWarps) {
        this.owlWarps = owlWarps;
        messages = new HashMap<>();
    }

    /**
     * Loads plugin messages from a JSON file.
     *
     * @param filename The name of the JSON file.
     */
    public void loadMessages(String filename) {
        File userFile = new File(owlWarps.getDataFolder(), filename);

        if (!userFile.exists()) {
            owlWarps.getLogger().info(String.format("File '%s' not found, copying default.", userFile.getPath()));
            if (!copyDefaultFile(filename)) {
                owlWarps.getLogger().severe("Failed to copy the default messages file.");
            }
        }
        // Attempt to load messages from the user file
        if (!loadFromFile(userFile)) {
            owlWarps.getLogger().severe("Failed to load messages from the file.");
            owlWarps.getPluginLoader().disablePlugin(owlWarps);
        }

        Messages.initMessages(messages);
    }

    /**
     * Loads messages from a specified JSON file into the messages map.
     *
     * @param userFile The file to load messages from.
     * @return true if messages were loaded successfully, false otherwise.
     */
    private boolean loadFromFile(File userFile) {
        Gson gson = new Gson();
        Type messageMapType = new TypeToken<Map<String, String>>(){}.getType();

        try (InputStream inputStream = Files.newInputStream(userFile.toPath());
             InputStreamReader reader = new InputStreamReader(inputStream)) {
            messages = gson.fromJson(reader, messageMapType);
            return true;
        } catch (IOException e) {
            owlWarps.getLogger().severe(String.format("Could not load the '%s' file. Reason: %s",
                    userFile.getPath(), e.getMessage()));
            return false;
        }
    }

    /**
     * Copies the default JSON file from the plugin's resource folder to the user's data folder.
     *
     * @param filename The name of the default file to copy.
     * @return true if the default file was copied successfully, false otherwise.
     */
    private boolean copyDefaultFile(String filename) {

        try (InputStream inputStream = owlWarps.getResource(filename)) {

            if (inputStream == null) {
                owlWarps.getLogger().severe(String.format("Could not locate default file '%s'.", filename));
                return false;
            }

            File outputFile = new File(owlWarps.getDataFolder(), filename);
            outputFile.getParentFile().mkdirs();

            try (OutputStream outputStream = new FileOutputStream(outputFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            owlWarps.getLogger().info(String.format("Default file '%s' copied to '%s'.", filename, outputFile.getPath()));
            return true; // Successfully copied
        } catch (IOException e) {
            owlWarps.getLogger().severe(String.format("Could not locate default file '%s' in the plugin resource folder. Reason: %s", filename, e.getMessage()));
            return false; // Failed to copy
        }
    }

    /**
     * Retrieves a message by its key.
     *
     * @param key The key of the message.
     * @return The message associated with the key, or null if not found.
    */
    public String getMessage(String key) {
        return messages.get(key);
    }
}
