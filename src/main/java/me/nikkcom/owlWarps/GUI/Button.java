package me.nikkcom.owlWarps.GUI;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class Button {

    private final ItemStack itemStack;
    private final Menu.MenuClick clickAction;

    public Button(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.clickAction = null;

    }

    public Button(ItemStack itemStack, Menu.MenuClick clickAction) {
        this.itemStack = itemStack;
        this.clickAction = clickAction;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
    
    public Menu.MenuClick getAction() {
        return clickAction;
    }
}
