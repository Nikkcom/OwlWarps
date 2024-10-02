package me.nikkcom.owlWarps;

import me.nikkcom.owlWarps.GUI.events.MenuEvents;
import me.nikkcom.owlWarps.commands.pw.PwManager;
import me.nikkcom.owlWarps.commands.pwarp.OwlWarpsManager;
import me.nikkcom.owlWarps.commands.pwarp.PwarpManager;
import me.nikkcom.owlWarps.configuration.LocaleLoader;
import me.nikkcom.owlWarps.playerwarps.PlayerWarpManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class OwlWarps extends JavaPlugin {

    private LocaleLoader localeLoader;

    private PlayerWarpManager playerWarpManager;

    @Override
    public void onEnable() {

        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {

            getLogger().warning("Could not find PlaceholderAPI! This plugin is required."); //


            Bukkit.getPluginManager().disablePlugin(this);

        }

        testRegex();



        // Initializes the plugin messages loader.
        localeLoader = new LocaleLoader(this);
        localeLoader.loadMessages("locale/en_US.json");

        playerWarpManager = new PlayerWarpManager(this);


        // Registering event classes
        getServer().getPluginManager().registerEvents(new MenuEvents(), this);

        getCommand("pwarp").setExecutor(new PwarpManager(this));
        getCommand("pwarp").setTabCompleter(new PwarpManager(this));

        getCommand("owlwarps").setExecutor(new OwlWarpsManager(this));
        getCommand("owlwarps").setTabCompleter(new OwlWarpsManager(this));

        getCommand("pw").setExecutor(new PwManager(this));
        getCommand("pw").setTabCompleter(new PwManager(this));
        // Example of retrieving a message
        String versionMessage = localeLoader.getMessage("version-message");
        getLogger().info(versionMessage != null ? versionMessage : "Message not found.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public PlayerWarpManager getPlayerWarpManager() {
        return playerWarpManager;
    }
    private void testRegex() {
        String string = "No match! FIrst string&7Legacy Colorcode Match&#000000Normal Hex Match&{#ffffff:#000000}Gradient Match";
        String regex = "(&#[0-9A-Fa-f]{6})|(&\\{#[0-9A-Fa-f]{6}:#[0-9A-Fa-f]{6}})|(&[0-9a-fA-F])";

        Map<String, String> results = new LinkedHashMap<>();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);

        int lastEnd = 0;

        while (matcher.find()) {

            int start = matcher.start();
            int end = matcher.end();


            // Extract the value from the last end index to the start of the current match
            String value = string.substring(lastEnd, start);

            // Only add to map if value is not empty
            if (!value.isEmpty()) {
                // Instead of using regexKey, use the last matched key
                // You can decide which key to use based on your logic or store it in the last match variable
                // For this example, we can just use the last matched key
                String lastKey = results.isEmpty() ? null : results.keySet().toArray(new String[0])[results.size() - 1];
                results.put(lastKey != null ? lastKey : regex, value);
            }

            lastEnd = end;
        }
        if (lastEnd < string.length()) {
            String value = string.substring(lastEnd).trim();
            if (!value.isEmpty()) {
                results.put(regex, value);
            }
        }

        // Print the result map
        for (Map.Entry<String, String> entry : results.entrySet()) {
            getLogger().info("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }
}
