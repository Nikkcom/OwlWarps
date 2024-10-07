package me.nikkcom.owlWarps.GUI.events;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryDragEvent;

/**
 * Represents a drag action in the menu.
 * This interface handles the behavior when a player drags an item
 * inside the menu.
 */
public interface MenuDrag {
    /**
     * Executes when a player drags items in the menu.
     *
     * @param player the player who dragged the items.
     * @param event  the drag event containing details about the drag.
     */
    void drag(Player player, InventoryDragEvent event);
}
