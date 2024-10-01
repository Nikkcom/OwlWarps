package me.nikkcom.owlWarps.GUI.menus;

import me.nikkcom.owlWarps.GUI.Menu;
import me.nikkcom.owlWarps.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.Set;

public class PwarpMainMenu extends Menu {
    public PwarpMainMenu() {
        super(54, "Pwarp Main Menu", "PwarpMainMenu");
        ItemStack PURPLE_GLASS = ItemCreator.create(Material.PURPLE_STAINED_GLASS_PANE, "");
        ItemStack MAGENTA_GLASS = ItemCreator.create(Material.MAGENTA_STAINED_GLASS_PANE, "");

        Set<Integer> PURPLE_GLASS_SLOTS = Set.of(0, 2, 6, 8, 18, 26, 27, 35, 45, 47, 51, 53);
        Set<Integer> MAGENTA_GLASS_SLOTS = Set.of(1, 3, 5, 7, 9, 17, 36, 44, 46, 48, 50, 52);

        for (Integer i : PURPLE_GLASS_SLOTS) setItem(i, PURPLE_GLASS);
        for (Integer i : MAGENTA_GLASS_SLOTS) setItem(i, MAGENTA_GLASS);


        ItemStack playerHead = ItemCreator.create(Material.PLAYER_HEAD, "§9%Player_head%",
                List.of("Lore of item %player_name%", "Empty hand? %player_has_empty_slot%", "More lore"));
        setItem(4, playerHead);
        setPlayerHeadSlot(4);
    }
}
