package me.nikkcom.owlWarps.GUI;

import me.nikkcom.owlWarps.GUI.events.MenuClick;
import org.bukkit.inventory.ItemStack;

public class MenuItemImpl implements MenuItem {

    private final ItemStack itemStack;
    private final MenuClick clickAction;

    public MenuItemImpl(ItemStack itemStack, MenuClick clickAction) {
        this.itemStack = itemStack;
        this.clickAction = clickAction;
    }

    @Override
    public ItemStack getItem() {
        return itemStack;
    }

    @Override
    public MenuClick getAction() {
        return clickAction;
    }
}
