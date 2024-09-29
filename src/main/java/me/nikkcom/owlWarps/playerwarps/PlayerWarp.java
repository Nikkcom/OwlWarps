package me.nikkcom.owlWarps.playerwarps;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.UUID;

public class PlayerWarp {

    private UUID playerWarpUUID;
    private Material icon;
    private Location location;



    public PlayerWarp(UUID playerWarpUUID) {
        this.playerWarpUUID = playerWarpUUID;
        this.icon = Material.PLAYER_HEAD;
    }
}
