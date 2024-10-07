package me.nikkcom.owlWarps.GUI.menus;

import me.nikkcom.owlWarps.GUI.MenuItem;
import me.nikkcom.owlWarps.GUI.MenuItemImpl;
import me.nikkcom.owlWarps.GUI.PaginatedMenu;
import me.nikkcom.owlWarps.utils.ItemCreator;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class TestMenuTwo extends PaginatedMenu {
    public TestMenuTwo() {
        super(54, "Paginated menu breh");

        List<MenuItem> items = new ArrayList<>();
        for (int i = 1; i < 150; i++) {
            MenuItem item = new MenuItemImpl(ItemCreator.create(Material.RED_DYE, String.format("Item - %d", i)), (player, event) -> {
                player.sendMessage("You left clicked item breh");
            });
            items.add(item);
        }


        setItem(inventory.getSize() - 1, new MenuItemImpl(ItemCreator.create(Material.ARROW, "Next page"), (player, event) -> {
            nextPage();
        }));
        setItem(inventory.getSize() - 9, new MenuItemImpl(ItemCreator.create(Material.ARROW, "prev page"), (player, event) -> {
            previousPage();
        }));


        List<Integer> slots = List.of(11, 12, 13, 14, 15, 20, 21, 22, 23, 24, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42);
        setPaginationSlots(slots);

        addDynamicItems(items);
        updateDynamicItems(getPlayer());
    }


}
