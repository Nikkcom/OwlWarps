package me.nikkcom.owlWarps.GUI;

import me.nikkcom.owlWarps.GUI.events.MenuDrag;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * PaginatedMenu class that extends MenuImpl to support pagination with specified slots.
 */
public class PaginatedMenu extends MenuImpl {

    private final List<MenuItem> rawDynamicItems; // All items to display
    private final List<MenuItem> dynamicItems; // All items to display
    private final List<Integer> paginatingSlots; // List of slots for items
    private int currentPage; // Current page index

    /**
     * Constructs a new PaginatedMenu.
     *
     * @param size  The size of the inventory.
     * @param title The title of the menu
     */
    public PaginatedMenu(int size, String title) {
        this(size, title, null);
    }

    /**
     * Constructs a new PaginatedMenu.
     *
     * @param size     The size of the inventory.
     * @param title    The title of the menu
     * @param viewerId The viewerId of the menu.
     */
    public PaginatedMenu(int size, String title, String viewerId) {
        super(size, title, viewerId);
        this.currentPage = 0;

        rawDynamicItems = new ArrayList<>();
        dynamicItems = new ArrayList<>();
        paginatingSlots = new ArrayList<>();

        for (int i = 0; i < (size - 9); i++) {
            addPaginationSlot(i);
        }
    }

    /**
     * Open the menu for a player.
     *
     * @param player The player to open the menu for
     */
    @Override
    public void open(Player player) {
        super.open(player);
        render(player);
    }

    @Override
    public void setGeneralDragAction(MenuDrag generalDragAction) {

    }

    public void render(Player player) {
        inventory.clear();
        for (int i : paginatingSlots) {
            menuClickActions.remove(i);
        }

        this.updateStaticItems(player);
        this.updateDynamicItems(player);
    }

    public void setPaginationSlots(List<Integer> slots) {
        this.paginatingSlots.clear();
        addPaginationSlots(slots);
    }

    public void addPaginationSlots(List<Integer> slots) {
        this.paginatingSlots.addAll(slots);
    }

    public void addPaginationSlot(int slot) {
        this.paginatingSlots.add(slot);
    }

    public void addDynamicItem(MenuItem item) {
        rawDynamicItems.add(item);
    }

    public void addDynamicItems(List<MenuItem> items) {
        this.rawDynamicItems.addAll(items);
    }

    public void updateDynamicItems(Player player) {
        // Parse all raw dynamic menu items and set them in the inventory
        List<MenuItem> parsedDynamicItems = new ArrayList<>();
        for (MenuItem dynamicItem : rawDynamicItems) {
            ItemStack parsedItem = parseItem(dynamicItem.getItem(), player);
            parsedDynamicItems.add(new MenuItemImpl(parsedItem, dynamicItem.getAction()));
        }

        dynamicItems.clear();
        dynamicItems.addAll(parsedDynamicItems);

        int itemsPerPage = paginatingSlots.size();
        int startIndex = currentPage * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, parsedDynamicItems.size());

        Iterator<Integer> slotIterarot = paginatingSlots.iterator();


        for (int i = startIndex; i < endIndex; i++) {
            if (!slotIterarot.hasNext()) break;

            int slot = slotIterarot.next();
            inventory.setItem(slot, dynamicItems.get(i).getItem());


            if (dynamicItems.get(i).getAction() == null) menuClickActions.remove(slot);
            else menuClickActions.put(slot, dynamicItems.get(i).getAction());
        }
    }

    /**
     * Switch to the next page.
     */
    public void nextPage() {
        if (currentPage < getTotalPages() - 1) {
            currentPage++;
            render(getPlayer());
        }
    }

    /**
     * Switch to the previous page.
     */
    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            render(getPlayer());
        }
    }

    /**
     * Get the total number of pages.
     *
     * @return The total page count based on the items and available slots.
     */
    private int getTotalPages() {
        return (int) Math.ceil((double) dynamicItems.size() / paginatingSlots.size());
    }
}
