package me.nikkcom.owlWarps.GUI.menus;

import me.nikkcom.owlWarps.GUI.ClickableItem;
import me.nikkcom.owlWarps.GUI.PaginatedMenu;
import me.nikkcom.owlWarps.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class OwnedPlayerWarpsMenu extends PaginatedMenu {
    public OwnedPlayerWarpsMenu(int size, String name, String viewerID) {
        super(size, name, viewerID);


        ItemStack GRAY_GLASS = ItemCreator.create(Material.GRAY_STAINED_GLASS_PANE, "");

        for (int i = 0; i < 9; i++) {
            setItem((size - 1) - i, new ClickableItem(GRAY_GLASS));
        }
        getPlayer();
    }
}
