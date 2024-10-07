package me.nikkcom.owlWarps.GUI.events;

import org.bukkit.entity.Player;

/**
 * Represents the action when a menu is opened by a player.
 * This interface handles any logic that needs to occur when a player
 * opens a menu.
 */
public interface MenuOpen {
    /**
     * Executes when a player opens the menu.
     *
     * @param player the player who opened the menu.
     */
    void open(Player player);
}
