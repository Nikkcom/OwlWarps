package me.nikkcom.owlWarps.playerwarps;

import me.nikkcom.owlWarps.configuration.Message;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerWarp {

    private final UUID warpUUID;
    private String warpNameRaw;
    private String warpName;
    private UUID ownerUUID;
    private boolean isPublic;
    private LocalDateTime timeCreated;
    private LocalDateTime lastActive;
    private Material icon;
    private Location location;
    private HashMap<UUID, Integer> playerRatings;
    private HashMap<UUID, LocalDateTime> lastVisitingPlayers;

    public PlayerWarp() {
        this.warpUUID = UUID.randomUUID();
        this.icon = Material.PLAYER_HEAD;
        this.timeCreated = LocalDateTime.now();
        playerRatings = new HashMap<>();
        lastVisitingPlayers = new HashMap<>();
        this.isPublic = true;
    }

    public PlayerWarp(UUID playerWarpUUID) {
        this.warpUUID = playerWarpUUID;
        this.icon = Material.PLAYER_HEAD;
        this.timeCreated = LocalDateTime.now();
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime time) {
        this.timeCreated = time;
    }

    public UUID getUUID() {
        return warpUUID;
    }

    public Material getIcon() {
        return icon;
    }

    public void setIcon(Material icon) {
        this.icon = icon;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean hasVisited(Player player) {
        return this.lastVisitingPlayers.containsKey(player.getUniqueId());
    }

    public Map<UUID, LocalDateTime> getVisitingPlayers() {
        return lastVisitingPlayers;
    }

    public void addPlayerVisits(Map<UUID, LocalDateTime> playerLastVisit) {
        this.lastVisitingPlayers.putAll(playerLastVisit);
    }

    public void setPlayerVisits(Map<UUID, LocalDateTime> playerLastVisit) {
        this.lastVisitingPlayers = new HashMap<>();
        this.lastVisitingPlayers.putAll(playerLastVisit);
    }

    public void addPlayerVisit(Player player) {
        this.lastVisitingPlayers.put(player.getUniqueId(), LocalDateTime.now());
    }

    public void addPlayerVisit(Player player, LocalDateTime time) {
        this.lastVisitingPlayers.put(player.getUniqueId(), time);
    }

    public void removePlayerVisit(Player player) {
        this.lastVisitingPlayers.remove(player.getUniqueId());
    }


    // Player rating
    public Map<UUID, Integer> getPlayerRatings() {
        return playerRatings;
    }

    public void setPlayerRatings(Map<UUID, Integer> playerRatings) {
        this.playerRatings = new HashMap<>();
        this.playerRatings.putAll(playerRatings);
    }

    public void addPlayerRatings(Map<UUID, Integer> playerRatings) {
        this.playerRatings.putAll(playerRatings);
    }

    public void addPlayerRating(Player player, int rating) {
        this.playerRatings.put(player.getUniqueId(), rating);
    }

    public void removePlayerRating(Player player, int rating) {
        this.playerRatings.remove(player.getUniqueId());
    }

    public boolean hasRated(Player player) {
        return this.playerRatings.containsKey(player.getUniqueId());
    }


    public String getName() {
        return warpName;
    }

    public void setName(String warpName) {
        this.warpName = warpName;
    }

    public String getRawName() {
        return warpNameRaw;
    }

    public void setRawName(String warpNameRaw) {
        this.warpNameRaw = warpNameRaw;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public void setOwner(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public LocalDateTime getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }

    public void teleport(Player player) {
        player.teleport(getLocation());
        player.sendMessage(Message.PWARP_TELEPORT_SUCCESS.papiColor(player, getName()));
    }
}
