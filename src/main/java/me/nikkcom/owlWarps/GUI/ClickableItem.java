package me.nikkcom.owlWarps.GUI;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.awt.*;

public class ClickableItem {
    private final ItemStack itemStack;
    private final Menu.MenuClick action;

    public ClickableItem(ItemStack itemStack, Menu.MenuClick action) {
        this.itemStack = itemStack;
        this.action = action;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void execute(Player player, InventoryClickEvent event) {
        if (action != null) {
            action.click(player, event);
        }
    }
}
