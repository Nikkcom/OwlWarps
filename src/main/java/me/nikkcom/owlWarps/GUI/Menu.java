package me.nikkcom.owlWarps.GUI;

import me.nikkcom.owlWarps.GUI.events.MenuClick;
import me.nikkcom.owlWarps.GUI.events.MenuClose;
import me.nikkcom.owlWarps.GUI.events.MenuDrag;
import me.nikkcom.owlWarps.GUI.events.MenuOpen;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a general interface for handling a menu in the plugin.
 */
public interface Menu {

    /**
     * Opens the menu for a player.
     *
     * @param player The player to open the menu for.
     */
    void open(Player player);

    /**
     * Removes the menu from the system, typically called when the menu is closed.
     */
    void remove();

    /**
     * Retrieves the current inventory associated with this menu.
     *
     * @return The inventory displayed to the player.
     */
    Inventory getInventory();

    /**
     * Adds a button to a specific index in the inventory.
     *
     * @param index  The index where the button should be placed.
     * @param button The button to place in the menu.
     */
    void addMenuitem(int index, MenuItem button);


    /**
     * Gets the unique identifier of the menu.
     *
     * @return The UUID of the menu
     */
    UUID getUuid();

    /**
     * Updates all static items in the menu by re-parsing placeholders and setting them in their respective slots.
     *
     * @param player The player for whom the items are being updated.
     */
    void updateStaticItems(Player player);

    /**
     * Sets an item in the menu with an associated menu click action.
     *
     * @param index    The index of the slot.
     * @param menuItem The menu item to set.
     */
    void setItem(int index, MenuItem menuItem);

    /**
     * Retrieves the general click action for the menu.
     *
     * @return the general click action associated with the menu.
     */
    MenuClick getGeneralClickAction();

    /**
     * Sets the general click action for the menu.
     *
     * @param generalClickAction the action to be set as the general click action.
     */
    void setGeneralClickAction(MenuClick generalClickAction);

    /**
     * Retrieves the general inventory click action for the menu.
     *
     * @return the general inventory click action associated with the menu.
     */
    MenuClick getGeneralInvClickAction();

    /**
     * Sets the general inventory click action for the menu.
     *
     * @param generalInvClickAction the action to be set as the general inventory click action.
     */
    void setGeneralInvClickAction(MenuClick generalInvClickAction);

    /**
     * Retrieves the general drag action for the menu.
     *
     * @return the general drag action associated with the menu.
     */
    MenuDrag getGeneralDragAction();

    /**
     * Sets the general drag action for the menu.
     *
     * @param generalDragAction the action to be set as the general drag action.
     */
    void setGeneralDragAction(MenuDrag generalDragAction);

    /**
     * Sets the action to be executed when the menu is opened.
     *
     * @param openAction the action to be set as the open action.
     */
    void setOpenAction(MenuOpen openAction);

    /**
     * Sets the action to be executed when the menu is closed.
     *
     * @param closeAction the action to be set as the close action.
     */
    void setCloseAction(MenuClose closeAction);

    /**
     * Retrieves the current viewers of the menu.
     *
     * @return A set of players currently viewing this menu.
     */
    Set<Player> getViewers();

    /**
     * Gets the action associated with a specific slot index.
     *
     * @param index The index of the slot.
     *
     * @return The MenuClick action associated with the slot.
     */
    MenuClick getAction(int index);

    /**
     * Retrieves the player associated with the current menu.
     *
     * @return the player associated with this menu, or null if no player is found.
     */
    Player getPlayer();

    int clickaction();
}
