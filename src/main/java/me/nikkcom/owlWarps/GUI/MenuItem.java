package me.nikkcom.owlWarps.GUI;

import me.nikkcom.owlWarps.GUI.events.MenuClick;
import org.bukkit.inventory.ItemStack;

/**
 * Represents an item within a menu, which may be a button or a list item.
 */
public interface MenuItem {

    /**
     * Retrieves the {@link ItemStack} associated with this menu item.
     *
     * @return The item stack for this menu item.
     */
    ItemStack getItem();

    /**
     * Retrieves the {@link me.nikkcom.owlWarps.GUI.events.MenuClick} associated with this menu item.
     *
     * @return The item click action for this menu item.
     */
    MenuClick getAction();
}
