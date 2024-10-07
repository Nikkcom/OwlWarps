package me.nikkcom.owlWarps.GUI.menus;

import me.nikkcom.owlWarps.GUI.MenuImpl;
import me.nikkcom.owlWarps.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public class PwarpMainMenu extends MenuImpl {
    public PwarpMainMenu() {
        super(54, "%player_name%", "PwarpMainMenu");
        ItemStack PURPLE_GLASS = ItemCreator.create(Material.PURPLE_STAINED_GLASS_PANE, "");
        ItemStack MAGENTA_GLASS = ItemCreator.create(Material.MAGENTA_STAINED_GLASS_PANE, "");

        Set<Integer> PURPLE_GLASS_SLOTS = Set.of(0, 2, 6, 8, 18, 26, 27, 35, 45, 47, 51, 53);
        Set<Integer> MAGENTA_GLASS_SLOTS = Set.of(1, 3, 5, 7, 9, 17, 36, 44, 46, 48, 50, 52);


        ItemStack playerHead = ItemCreator.create(Material.PLAYER_HEAD, "&8&lTitle &r&3&o%player_name%",
                List.of("&8&lTitle &r&3&o%player_name%", "Empty hand? %player_has_empty_slot%", "More lore"));

    }
}
