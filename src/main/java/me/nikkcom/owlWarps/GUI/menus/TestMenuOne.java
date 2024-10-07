package me.nikkcom.owlWarps.GUI.menus;

import me.nikkcom.owlWarps.GUI.MenuImpl;
import me.nikkcom.owlWarps.GUI.MenuItemImpl;
import me.nikkcom.owlWarps.utils.ItemCreator;
import org.bukkit.Material;

public class TestMenuOne extends MenuImpl {
    public TestMenuOne() {
        super(54, "Hi baby");


        setItem(7, new MenuItemImpl(
                ItemCreator.create(Material.ACACIA_BUTTON, "Health: %player_health%"),
                (player, event) -> {
                    if (event.getClick().isLeftClick()) {
                        player.sendMessage("You leftclick baby");
                    } else {
                        player.sendMessage("Right click baby gurl");
                    }
                }));


        setItem(0, new MenuItemImpl(
                ItemCreator.create(Material.ACACIA_BUTTON, "Refresh"),
                (player, event) -> {
                    if (event.getClick().isLeftClick()) {
                        player.sendMessage("Right click to refresh the items!");
                    } else {
                        player.sendMessage("You reloaded??");
                        this.updateStaticItems(player);
                    }
                }));
    }
}
