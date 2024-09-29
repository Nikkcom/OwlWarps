package me.nikkcom.owlWarps.GUI.menus;

import me.nikkcom.owlWarps.GUI.Menu;
import me.nikkcom.owlWarps.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;

public class PwarpMainMenu extends Menu {
    public PwarpMainMenu() {
        super(54, "Pwarp Main Menu", "PwarpMainMenu");

        setItem(5, ItemCreator.create(Material.REDSTONE, "Redstone breh"), (p, event) -> {

        });
    }
}
