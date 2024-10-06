package me.nikkcom.owlWarps.configuration;

import me.nikkcom.owlWarps.OwlWarps;
import me.nikkcom.owlWarps.data.storage.IDataStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class Messages {

    private static Map<String, String> messages = new HashMap<>();
    private static IDataStorage<String> messageStorage;

    public static void initialize(IDataStorage<String> dataStorage) {
        messageStorage = dataStorage;
        loadMessages();
    }

    private static void loadMessages() {
        // Load all messages from the storage
        for (String key : messageStorage.loadAll()) {
            messages.put(key, messageStorage.load(key));
        }
        OwlWarps.log(Level.INFO, "All messages loaded successfully.");
    }

    public static String getMessage(String messageKey) {
        return messages.getOrDefault(messageKey, "Missing Message: " + messageKey);
    }

    public static void addMessage(String key, String message) {
        messages.put(key, message);
        messageStorage.save(key);
    }

    public static void removeMessage(String key) {
        messages.remove(key);
        messageStorage.delete(key);
    }
}
