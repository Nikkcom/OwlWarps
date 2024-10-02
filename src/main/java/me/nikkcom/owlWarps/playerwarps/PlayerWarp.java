package me.nikkcom.owlWarps.playerwarps;

import me.nikkcom.owlWarps.configuration.Message;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.HashMap;
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
    private HashMap<UUID, LocalDateTime> playerLastVisit;
    private HashMap<UUID, Integer> playerTotalVisits;
    private PlayerWarpType playerWarpType;
    private HashMap<UUID, AccessGroup> accessGroups;

    public PlayerWarp() {
        this.warpUUID = UUID.randomUUID();
        this.icon = Material.PLAYER_HEAD;
        this.timeCreated = LocalDateTime.now();
        playerRatings = new HashMap<>();
        playerLastVisit = new HashMap<>();
        playerTotalVisits = new HashMap<>();
        this.playerWarpType = PlayerWarpType.OTHER;
        this.isPublic = true;
    }

    public PlayerWarp(UUID playerWarpUUID) {
        this.warpUUID = playerWarpUUID;
        this.icon = Material.PLAYER_HEAD;
        this.timeCreated = LocalDateTime.now();
    }
    public void setTimeCreated(LocalDateTime time) {
        this.timeCreated = time;
    }
    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }
    public UUID getUUID() {
        return warpUUID;
    }
    public void setIcon(Material icon) {
        this.icon = icon;
    }
    public Material getIcon() {
        return icon;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public Location getLocation() {
        return location;
    }
    public boolean hasVisited(Player player) {
        return this.playerLastVisit.containsKey(player.getUniqueId());
    }
    public HashMap<UUID, Integer> getPlayerTotalVisits() {
        return playerTotalVisits;
    }
    public void setPlayerTotalVisits(HashMap<UUID, Integer> playerTotalVisits) {
        this.playerTotalVisits = playerTotalVisits;
    }
    public HashMap<UUID, LocalDateTime> getPlayerLastVisitMap() {
        return playerLastVisit;
    }
    public void setPlayerLastVisitMap(HashMap<UUID, LocalDateTime> playerLastVisit) {
        this.playerLastVisit = playerLastVisit;
    }
    public void setPlayerVisited(Player player) {
        this.playerLastVisit.put(player.getUniqueId(), LocalDateTime.now());
    }
    public void setPlayerLastVisit(Player player, LocalDateTime time) {
        this.playerLastVisit.put(player.getUniqueId(), time);
    }
    public void removePlayerLastVisit(Player player) {
        this.playerLastVisit.remove(player.getUniqueId());
    }


    // Player rating
    public HashMap<UUID, Integer> getPlayerRatings() {
        return playerRatings;
    }
    public void setPlayerRatings(HashMap<UUID, Integer> playerRatings) {
        this.playerRatings = playerRatings;
    }
    public void addPlayerWarpRating(Player player, int rating) {
        this.playerRatings.put(player.getUniqueId(), rating);
    }
    public void removePlayerWarpRating(Player player, int rating) {
        this.playerRatings.remove(player.getUniqueId());
    }
    public boolean hasRated(Player player) {
        return this.playerRatings.containsKey(player.getUniqueId());
    }


    public String getWarpName() {
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

    public PlayerWarpType getPlayerWarpType() {
        return playerWarpType;
    }

    public void setPlayerWarpType(PlayerWarpType playerWarpType) {
        this.playerWarpType = playerWarpType;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public void setAccess(Player player, AccessGroup group) {
        this.accessGroups.put(player.getUniqueId(), group);
    }
    public void removeAccess(Player player) {
        this.accessGroups.remove(player.getUniqueId());
    }

    public LocalDateTime getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }

    public void teleport(Player player) {
        player.teleport(getLocation());
        player.sendMessage(Message.PWARP_TELEPORT_SUCCESS.papiColor(player, getWarpName()));
    }
}
