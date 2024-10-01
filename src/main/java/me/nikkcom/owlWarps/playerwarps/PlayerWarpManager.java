package me.nikkcom.owlWarps.playerwarps;

import me.nikkcom.owlWarps.OwlWarps;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerWarpManager {

    private final OwlWarps owlWarps;

    private List<PlayerWarp> playerWarps;
    private HashMap<UUID, List<PlayerWarp>> playerWarpsByOwner;
    private HashMap<UUID, PlayerWarp> playerWarpByUUID;
    private HashMap<String, PlayerWarp> playerWarpByName;


    public PlayerWarpManager(OwlWarps owlWarps) {
        this.owlWarps = owlWarps;
        playerWarps = new ArrayList<>();
        playerWarpsByOwner = new HashMap<>();
        playerWarpByUUID = new HashMap<>();
        playerWarpByName = new HashMap<>();
        // Load warps
    }

    public void addWarp(PlayerWarp warp) {
        playerWarps.add(warp);

        // Add to warps by owner
        List<PlayerWarp> byOwner = playerWarpsByOwner.computeIfAbsent(warp.getOwnerUUID(), k -> new ArrayList<>());
        byOwner.add(warp);

        playerWarpByUUID.put(warp.getUUID(), warp);

        playerWarpByName.put(warp.getRawName(), warp);
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
}
