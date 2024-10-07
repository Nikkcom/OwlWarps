package me.nikkcom.owlWarps.GUI.events;

import org.bukkit.entity.Player;

/**
 * Represents the action when a menu is closed by a player.
 * This interface handles any logic that needs to occur when a player
 * closes a menu.
 */
public interface MenuClose {
    /**
     * Executes when a player closes the menu.
     *
     * @param player the player who closed the menu.
     */
    void close(Player player);
}
