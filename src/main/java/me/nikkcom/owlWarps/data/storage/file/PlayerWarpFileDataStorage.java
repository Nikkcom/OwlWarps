package me.nikkcom.owlWarps.data.storage.file;

import me.nikkcom.owlWarps.OwlWarps;
import me.nikkcom.owlWarps.playerwarps.PlayerWarp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;

public class PlayerWarpFileDataStorage extends FileDataStorage<PlayerWarp> {

    public PlayerWarpFileDataStorage(OwlWarps owlWarps) {
        super(owlWarps, "playerwarps.yml");
    }

    @Override
    public void save(PlayerWarp playerWarp) {
        OwlWarps.log(Level.FINE, String.format("Attempting to save playerwarp with name '%s'", playerWarp.getRawName()));
        String warpId = playerWarp.getUUID().toString();
        ConfigurationSection warpSection = yamlConfig.createSection("warps." + warpId);
        warpSection.set("name", playerWarp.getName());
        warpSection.set("rawname", playerWarp.getRawName());
        warpSection.set("owner", playerWarp.getOwnerUUID().toString());
        warpSection.set("location", playerWarp.getLocation().serialize());
        warpSection.set("icon", playerWarp.getIcon().toString());
        warpSection.set("ispublic", playerWarp.isPublic());
        warpSection.set("timecreated", playerWarp.getTimeCreated().toString());
        warpSection.set("lastactive", playerWarp.getLastActive().toString());

        ConfigurationSection ratingsSection = warpSection.createSection("ratings");
        for (Map.Entry<UUID, Integer> entry : playerWarp.getPlayerRatings().entrySet()) {
            ratingsSection.set(entry.getKey().toString(), entry.getValue());
        }

        ConfigurationSection visitingPlayersSection = warpSection.createSection("visitingplayers");
        for (Map.Entry<UUID, LocalDateTime> entry : playerWarp.getVisitingPlayers().entrySet()) {
            visitingPlayersSection.set(entry.getKey().toString(), entry.getValue().toString());
        }
        saveConfig();
    }

    @Override
    public PlayerWarp load(PlayerWarp playerWarp) {
        String warpId = playerWarp.getUUID().toString();
        OwlWarps.log(Level.FINE, String.format("Attempting to load playerwarp with UUID: %s", warpId));
        if (!yamlConfig.contains("warps." + warpId) || !yamlConfig.isConfigurationSection("warps." + warpId)) {
            OwlWarps.log(Level.WARNING, String.format("YAML file does not contain section for warp with ID: %s", warpId));
            return null;
        }

        ConfigurationSection warpSection = yamlConfig.createSection("warps." + warpId);
        if (!yamlConfig.isConfigurationSection("warps." + warpId)) {
            OwlWarps.log(Level.WARNING, String.format("Failed to retrieve configuration section for warp ID: %s", warpId));
            return null;
        }
        OwlWarps.log(Level.INFO, String.format("Loading configuration for warp ID: %s", warpId));
        return loadFromSection(warpSection, playerWarp.getUUID());
    }

    @Override
    public void delete(PlayerWarp playerWarp) {
        String warpId = playerWarp.getUUID().toString();
        if (!yamlConfig.contains("warps." + warpId)) {
            OwlWarps.log(Level.WARNING, String.format("Could not delete playerwarp. File does not contain id '%s'", warpId));
            return;
        }
        if (yamlConfig.isConfigurationSection("warps." + warpId)) {
            yamlConfig.set("warps." + warpId, null);
            saveConfig();
            OwlWarps.log(Level.INFO, String.format("Successfully deleted playerwarp with id '%s'", warpId));
        }
    }

    @Override
    public List<PlayerWarp> loadAll() {
        OwlWarps.log(Level.FINE, "Attempting to load all playerwarps.");
        List<PlayerWarp> playerWarps = new ArrayList<>();
        ConfigurationSection warpSection = yamlConfig.getConfigurationSection("warps");
        if (!yamlConfig.contains("warps")) {
            OwlWarps.log(Level.INFO, String.format("'%s' does not contain 'warps' section. No playerwarps to load.", file.getName()));
            return Collections.emptyList();
        }
        if (warpSection.getKeys(false).isEmpty()) {
            OwlWarps.log(Level.INFO, String.format("No playerwarps found in file '%s'.", file.getName()));
            return Collections.emptyList();
        }

        Set<String> warpUuidStringSet = warpSection.getKeys(false);

        for (String warpUuidString : warpUuidStringSet) {
            if (!isValidUuid(warpUuidString)) {
                OwlWarps.log(Level.WARNING, String.format(
                        "The playerwarp id is not valid. Skipping loading playerwarp with id '%s'", warpUuidString));
                continue;
            }
            PlayerWarp playerwarp = loadFromSection(warpSection.getConfigurationSection(warpUuidString), UUID.fromString(warpUuidString)
            );
            if (playerwarp == null) {
                OwlWarps.log(Level.WARNING, String.format("Playerwarp object is null! With id '%s'", warpUuidString));
                continue;
            }
            playerWarps.add(playerwarp);
        }
        OwlWarps.log(Level.INFO, String.format("A total of %d playerwarps were loaded.", playerWarps.size()));
        return playerWarps;
    }

    @Override
    public void saveAll(List<PlayerWarp> playerWarps) {
        OwlWarps.log(Level.FINE, "Attempting to save all playerwarps...");
        for (PlayerWarp playerWarp : playerWarps) {
            save(playerWarp);
        }
    }

    private boolean isValidUuid(String uuidString) {
        if (uuidString == null) {
            return false;
        }
        try {
            UUID uuid = UUID.fromString(uuidString);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    private PlayerWarp loadFromSection(ConfigurationSection warpSection, UUID warpUuid) {
        PlayerWarp playerWarp = new PlayerWarp(warpUuid);
        String rawName = warpSection.getString("rawname");
        String name = warpSection.getString("name");

        // Logic to validate owner is valid UUID.
        String ownerString = warpSection.getString("owner");
        if (!isValidUuid(ownerString)) {
            OwlWarps.log(Level.WARNING, String.format(
                    "The owner of warp '%s' (%s) is not a valid UUID.", warpUuid, rawName));
            return null;
        }
        UUID owner = UUID.fromString(ownerString);

        // Logic to validate valid location.
        if (!warpSection.contains("location") || !warpSection.isConfigurationSection("location")) {
            OwlWarps.log(Level.WARNING, String.format(
                    "Location data for warp '%s' (%s) is not found in file.", warpUuid, rawName));
            return null;
        }

        ConfigurationSection locationSection = warpSection.getConfigurationSection("location");
        if (locationSection.getValues(false).isEmpty()) {
            OwlWarps.log(Level.WARNING, String.format(
                    "Location data for warp '%s' (%s) is empty.", warpUuid, rawName));
            return null;
        }
        Location location = Location.deserialize(
                locationSection.getValues(false));


        String iconString = warpSection.getString("icon");
        if (iconString == null) {
            iconString = Material.STONE.toString();
            OwlWarps.log(Level.WARNING, String.format(
                    "Playerwarp icon for warp '%s' (%s) could not be found in file. Defaulting to STONE.", warpUuid, rawName));
        }

        Material icon;
        try {
            icon = Material.getMaterial(iconString);
        } catch (IllegalArgumentException exception) {
            OwlWarps.log(Level.WARNING, String.format(
                    "Playerwarp icon for warp '%s' (%s) is not valid. Defaulting to STONE.", warpUuid, rawName));
            icon = Material.STONE;
        }


        boolean ispublic = warpSection.getBoolean("ispublic", false);

        // Logic to validate stored time of warp creation.

        String timeCreatedString = warpSection.getString("timecreated");

        if (timeCreatedString == null) {
            timeCreatedString = LocalDateTime.now().toString();
            OwlWarps.log(Level.WARNING, String.format(
                    "Missing 'timecreated' for warp '%s' (name: '%s'). Defaulting to current time: %s.",
                    warpUuid, rawName, timeCreatedString));
        }

        LocalDateTime timeCreated;
        try {
            timeCreated = LocalDateTime.parse(timeCreatedString);
        } catch (IllegalArgumentException exception) {
            timeCreated = LocalDateTime.now();
            OwlWarps.log(Level.WARNING, String.format(
                    "Invalid 'timecreated' format for warp '%s' (name: '%s'). " +
                            "Defaulting to current time: %s. Error: %s",
                    warpUuid, rawName, timeCreated, exception.getMessage()));
        }


        // Logic to validate the time the owner were last active.

        String lastActiveString = warpSection.getString("lastactive");

        if (lastActiveString == null) {
            lastActiveString = LocalDateTime.now().toString();
            OwlWarps.log(Level.INFO, String.format(
                    "No 'lastActive' value found for warp '%s' (name: '%s'). " +
                            "Defaulting to current time: %s",
                    warpUuid, rawName, lastActiveString));
        }

        LocalDateTime lastActive;

        try {
            lastActive = LocalDateTime.parse(lastActiveString);
        } catch (IllegalArgumentException exception) {
            lastActive = LocalDateTime.now();
            OwlWarps.log(Level.WARNING, String.format(
                    "Invalid 'lastActive' format for warp '%s' (name: '%s'). " +
                            "Defaulting to current time: %s. Error: %s",
                    warpUuid, rawName, lastActive, exception.getMessage()));
        }


        // Logic to deserialize ratings.
        Map<UUID, Integer> playerRatings = new HashMap<>();
        if (warpSection.contains("ratings") && warpSection.isConfigurationSection("ratings")) {
            // The stored warp data containt ratings.
            ConfigurationSection ratingsSection = warpSection.getConfigurationSection("ratings");
            if (!ratingsSection.getKeys(false).isEmpty()) {
                OwlWarps.log(Level.INFO, String.format(
                        "Loading ratings for warp '%s' (name: '%s').",
                        warpUuid, rawName));
                for (String uuidString : ratingsSection.getKeys(false)) {
                    if (!isValidUuid(uuidString)) {
                        OwlWarps.log(Level.WARNING, String.format(
                                "Skipping invalid UUID '%s' for ratings in warp '%s' (name: '%s').",
                                uuidString, warpUuid, rawName));
                        continue;
                    }
                    UUID uuid = UUID.fromString(uuidString);
                    int rating = ratingsSection.getInt(uuidString);
                    playerRatings.put(uuid, rating);
                    OwlWarps.log(Level.FINE, String.format(
                            "Loaded rating '%d' for UUID '%s' in warp '%s' (name: '%s').",
                            rating, uuidString, warpUuid, rawName));
                }
            } else {
                OwlWarps.log(Level.INFO, String.format(
                        "No ratings found for warp '%s' (name: '%s').",
                        warpUuid, rawName));
            }
        } else {
            OwlWarps.log(Level.FINE, String.format(
                    "No ratings section found for warp '%s' (name: '%s').",
                    warpUuid, rawName));
        }

        // Logic to validate lastVisiting Players map
        Map<UUID, LocalDateTime> playerVisits = new HashMap<>();
        if (warpSection.contains("visitingplayers") && warpSection.isConfigurationSection("visitingplayers")) {

            ConfigurationSection visitingPlayersSection = warpSection.getConfigurationSection("visitingplayers");

            if (!visitingPlayersSection.getKeys(false).isEmpty()) {
                OwlWarps.log(Level.INFO, String.format(
                        "Loading visiting players for warp '%s' (name: '%s').",
                        warpUuid, rawName));
                for (String uuidString : visitingPlayersSection.getKeys(false)) {
                    if (!isValidUuid(uuidString)) {
                        OwlWarps.log(Level.WARNING, String.format(
                                "Invalid UUID '%s' found in visiting players for warp '%s' (name: '%s'). Skipping this entry.",
                                uuidString, warpUuid, rawName));
                        continue;
                    }
                    UUID uuid = UUID.fromString(uuidString);
                    String lastVisitString = visitingPlayersSection.getString(uuidString);
                    LocalDateTime lastVisit;
                    try {
                        lastVisit = LocalDateTime.parse(lastVisitString);
                        OwlWarps.log(Level.FINE, String.format(
                                "Loaded last visit '%s' for player '%s' in warp '%s' (name: '%s').",
                                lastVisit, uuidString, warpUuid, rawName));
                    } catch (IllegalArgumentException exception) {
                        lastVisit = LocalDateTime.now();
                        OwlWarps.log(Level.WARNING, String.format(
                                "Invalid last visit time for player '%s' in warp '%s' (name: '%s'). Defaulting to current time.",
                                uuidString, warpUuid, rawName));
                        OwlWarps.log(Level.FINE, String.format(
                                "Error parsing last visit time for player '%s' in warp '%s' (name: '%s'): %s",
                                uuidString, warpUuid, rawName, exception.getMessage()));
                    }
                    playerVisits.put(uuid, lastVisit);
                }
            } else {
                OwlWarps.log(Level.INFO, String.format(
                        "No visiting players found for warp '%s' (name: '%s').",
                        warpUuid, rawName));
            }
        } else {
            OwlWarps.log(Level.FINE, String.format(
                    "No visiting players section found for warp '%s' (name: '%s').",
                    warpUuid, rawName));
        }


        playerWarp.setName(name);
        playerWarp.setRawName(rawName);
        playerWarp.setOwner(owner);
        playerWarp.setLocation(location);
        playerWarp.setIcon(icon);
        playerWarp.setPublic(ispublic);
        playerWarp.setTimeCreated(timeCreated);
        playerWarp.setLastActive(lastActive);

        playerWarp.setPlayerRatings(playerRatings);
        playerWarp.setPlayerVisits(playerVisits);
        Bukkit.getLogger().info(playerWarp.getIcon().toString());
        return playerWarp;
    }
}
