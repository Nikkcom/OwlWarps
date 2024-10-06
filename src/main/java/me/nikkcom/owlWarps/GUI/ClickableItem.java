package me.nikkcom.owlWarps.GUI;

import org.bukkit.inventory.ItemStack;

public class ClickableItem {

    private final ItemStack itemStack;
    private final Menu.MenuClick clickAction;

    public ClickableItem(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.clickAction = null;

    }

    public ClickableItem(ItemStack itemStack, Menu.MenuClick clickAction) {
        this.itemStack = itemStack;
        this.clickAction = clickAction;
    }

    public ItemStack getItem() {
        return itemStack;
    }

    public Menu.MenuClick getAction() {
        return clickAction;
    }
}
