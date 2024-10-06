package me.nikkcom.owlWarps.GUI;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PaginatedMenu extends Menu {

    private final List<ClickableItem> menuItems = new ArrayList<>();
    private final int itemsPerPage;
    private int currentPage;


    public PaginatedMenu(int size, String name, String viewerID) {
        super(size, name, viewerID);
        this.itemsPerPage = size - 9;
    }

    public void addItem(ClickableItem item) {
        menuItems.add(item);
    }

    public void addItems(List<ClickableItem> items) {
        menuItems.addAll(items);
    }
}
