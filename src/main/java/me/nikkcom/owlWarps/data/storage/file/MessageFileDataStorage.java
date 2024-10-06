package me.nikkcom.owlWarps.data.storage.file;

import me.nikkcom.owlWarps.OwlWarps;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class MessageFileDataStorage extends FileDataStorage<String> {

    private final HashMap<String, String> messages;


    public MessageFileDataStorage(OwlWarps owlWarps) {
        super(owlWarps, "messages.yml");
        this.messages = new HashMap<>();

        if (!file.exists()) {
            try {
                // Create the parent directory if it doesn't exist
                file.getParentFile().mkdirs();

                // Load the default messages.yml from resources
                try (InputStream inputStream = owlWarps.getClass().getResourceAsStream("/data/messages.yml")) {
                    if (inputStream != null) {
                        // Copy the resource file to the data folder
                        Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        OwlWarps.log(Level.INFO, String.format("Copied default messages.yml to %s", file.getPath()));
                    } else {
                        OwlWarps.log(Level.SEVERE, "Default messages.yml resource not found in JAR.");
                    }
                }
            } catch (IOException e) {
                OwlWarps.log(Level.SEVERE, String.format("Could not create messages.yml: %s", e.getMessage()));
                e.printStackTrace();
            }
        }
        loadMessages();
    }

    private void loadMessages() {
        for (String key : yamlConfig.getKeys(false)) {
            messages.put(key, yamlConfig.getString(key));
        }
        OwlWarps.log(Level.INFO, "Messages loaded successfully from " + file.getName());
    }

    @Override
    public void save(String messageKey) {
        String message = messages.get(messageKey);
        if (message != null) {
            yamlConfig.set(messageKey, message);
            saveConfig();
        }
    }

    public String load(String messageKey) {
        return messages.getOrDefault(messageKey, "Missing Message: " + messageKey);
    }

    public void delete(String messageKey) {
        messages.remove(messageKey);
        yamlConfig.set(messageKey, null);
        saveConfig();
    }

    @Override
    public List<String> loadAll() {
        return List.copyOf(messages.values());
    }

    @Override
    public void saveAll(List<String> messageKeys) {
        for (String messageKey : messageKeys) {
            save(messageKey);
        }
    }
}
