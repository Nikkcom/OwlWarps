package me.nikkcom.owlWarps.GUI.events;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Represents a click action in the menu for a specific slot.
 * This interface handles the behavior when a player clicks on an item
 * inside the menu.
 */
public interface MenuClick {
    /**
     * Executes when a player clicks an item in the menu.
     *
     * @param player the player who clicked the item.
     * @param event  the click event containing details about the click.
     */
    void click(Player player, InventoryClickEvent event);
}
