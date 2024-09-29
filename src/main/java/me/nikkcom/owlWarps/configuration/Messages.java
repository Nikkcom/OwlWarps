package me.nikkcom.owlWarps.configuration;

import java.util.HashMap;
import java.util.Map;

public class Messages {

    private static Map<String, String> messages = new HashMap<>();

    public static String getMessage(String key) {
        if (messages.containsKey(key)) return messages.get(key);
        return String.format("Could not find the json key '%s'. Please review the locale files!", key);
    }

    public static void setMessage(String key, String message) {
        messages.put(key, message);
    }

    public static void initMessages(Map<String, String> initialMessages) {
        messages.putAll(initialMessages);
    }
}
