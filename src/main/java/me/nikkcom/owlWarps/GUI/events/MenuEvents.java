package me.nikkcom.owlWarps.GUI.events;

import me.nikkcom.owlWarps.GUI.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;


/**
 * MenuEvents is a listener class that handles inventory-related events
 * for the GUI menus.
 * <p>
 * This class listens for drag, click, and close events in the inventory,
 * allowing for custom actions.
 * </p>
 */
public class MenuEvents implements Listener {


    /**
     * Handles the InventoryDragEvent, which is triggered when the player
     * drags items in the inventory.
     *
     * @param e The InventoryDragEvent containing information about the event.
     */
    @EventHandler
    public void inventoryDragEvent(InventoryDragEvent e) {
        Player p = (Player) e.getWhoClicked();
        Menu menu = Menu.getMenu(p);
        if (menu != null) {
            e.setCancelled(true);
            if (menu.getGeneralDragAction() != null) menu.getGeneralDragAction().drag(p, e);
        }
    }

    /**
     * Handles the InventoryClickEvent, which is triggered when the player
     * clicks on items in the inventory.
     *
     * @param e The InventoryClickEvent containing information about the event.
     */
    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Menu menu = Menu.getMenu(p);
        if (menu != null) {
            e.setCancelled(true);
            if (e.getClickedInventory() != null) {
                // When a player clicks an item in its own inventory.
                if (e.getRawSlot() > e.getClickedInventory().getSize()) {
                    if (menu.getGeneralInvClickAction() != null) menu.getGeneralInvClickAction().click(p, e);

                    // Handles when a player clicks a player in the opened inventory.
                }else if (menu.getGeneralClickAction() != null) menu.getGeneralClickAction().click(p, e);
            }
            Menu.MenuClick menuClick = menu.getAction(e.getRawSlot());
            if (menuClick != null) menuClick.click(p, e);
        }
    }
    /**
     * Handles the InventoryCloseEvent, which is triggered when the player
     * closes the inventory.
     *
     * @param e The InventoryCloseEvent containing information about the event.
     */
    @EventHandler
    public void inventoryClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        Menu menu = Menu.getMenu(p);
        if (menu != null) menu.remove();
    }
}
