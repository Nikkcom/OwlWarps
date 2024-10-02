package me.nikkcom.owlWarps.configuration;

import java.util.HashMap;
import java.util.Map;

public class Messages {

    private static Map<String, String> messages;

    public static void initMessages(Map<String, String> msg) {
        messages = msg;
    }

    public static String getMessage(String key) {
        return messages.getOrDefault(key, "Missing Message: " + key); // Provide a default message if not found
    }
}
