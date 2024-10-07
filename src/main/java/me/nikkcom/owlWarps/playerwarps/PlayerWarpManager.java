package me.nikkcom.owlWarps.playerwarps;

import me.nikkcom.owlWarps.OwlWarps;
import me.nikkcom.owlWarps.data.storage.DataStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerWarpManager {

    private static PlayerWarpManager instance;
    private final OwlWarps owlWarps;
    private final DataStorage<PlayerWarp> dataStorage;
    private final List<PlayerWarp> playerWarps;
    private final HashMap<UUID, List<PlayerWarp>> playerWarpsByOwner;
    private final HashMap<UUID, PlayerWarp> playerWarpByUUID;
    private final HashMap<String, PlayerWarp> playerWarpByName;


    public PlayerWarpManager(OwlWarps owlWarps, DataStorage<PlayerWarp> dataStorage) {
        this.owlWarps = owlWarps;
        this.dataStorage = dataStorage;
        playerWarps = new ArrayList<>();
        playerWarpsByOwner = new HashMap<>();
        playerWarpByUUID = new HashMap<>();
        playerWarpByName = new HashMap<>();
        loadPlayerWarps();

    }

    public static PlayerWarpManager getInstance() {
        return instance;
    }

    public static void initialize(OwlWarps owlWarps, DataStorage<PlayerWarp> dataStorage) {
        if (instance == null) {
            instance = new PlayerWarpManager(owlWarps, dataStorage);
        }
    }

    public void loadPlayerWarps() {
        addWarps(dataStorage.loadAll());
    }

    public void savePlayerWarps() {
        Bukkit.getLogger().info("SavePlayerWarps are called breh");
        dataStorage.saveAll(playerWarps);
    }

    public void unloadPlayerWarps() {
        Bukkit.getLogger().info("UnLoadPlayerWarps in manager class is called");
        savePlayerWarps();
        clearAllPlayerWarps();
    }

    public void reload() {
        // BUkkit log reload
        clearAllPlayerWarps();
        addWarps(dataStorage.loadAll());

    }

    public void addWarp(PlayerWarp warp) {
        playerWarps.add(warp);

        // Add to warps by owner
        List<PlayerWarp> byOwner = playerWarpsByOwner.computeIfAbsent(warp.getOwnerUUID(), k -> new ArrayList<>());
        byOwner.add(warp);

        playerWarpByUUID.put(warp.getUUID(), warp);

        playerWarpByName.put(warp.getRawName(), warp);
    }

    public void addWarps(List<PlayerWarp> playerWarpList) {
        for (PlayerWarp playerWarp : playerWarpList) {
            addWarp(playerWarp);
        }
    }

    public void removeWarp(PlayerWarp warp) {
        playerWarps.remove(warp);

        List<PlayerWarp> byOwner = playerWarpsByOwner.get(warp.getOwnerUUID());
        if (byOwner != null) {
            byOwner.remove(warp);
            if (byOwner.isEmpty()) {
                playerWarpsByOwner.remove(warp.getOwnerUUID());
            }
        }

        playerWarpByUUID.remove(warp.getUUID());

        playerWarpByName.remove(warp.getRawName(), warp);
        dataStorage.delete(warp);
    }

    public void teleport(Player player, String name) {
        teleport(player, playerWarpByName.get(name).getUUID());
    }

    public void teleport(Player player, UUID warpUUID) {
        player.teleport(playerWarpByUUID.get(warpUUID).getLocation());
    }


    public List<PlayerWarp> getWarps() {
        return this.playerWarps;
    }

    public boolean isWarp(UUID playerWarpUUID) {
        return playerWarpByUUID.containsKey(playerWarpUUID);
    }

    public boolean isWarp(String warpName) {
        return playerWarpByName.containsKey(warpName);
    }

    public HashMap<UUID, List<PlayerWarp>> getPlayerWarpsByOwner() {
        return playerWarpsByOwner;
    }

    public HashMap<String, PlayerWarp> getPlayerWarpsByName() {
        return playerWarpByName;
    }

    private void clearAllPlayerWarps() {
        playerWarps.clear();
        playerWarpsByOwner.clear();
        playerWarpByUUID.clear();
        playerWarpByName.clear();
    }
}
