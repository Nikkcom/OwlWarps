package me.nikkcom.owlWarps.commands.pwarp;

import me.nikkcom.owlWarps.GUI.Menu;
import me.nikkcom.owlWarps.GUI.menus.PwarpMainMenu;
import me.nikkcom.owlWarps.commands.SubCommand;
import org.bukkit.entity.Player;

public class MenuCommand extends SubCommand {
    @Override
    public String getName() {
        return "menu";
    }

    @Override
    public String getDescription() {
        return "Opens the main Pwarp GUI";
    }

    @Override
    public String getSyntax() {
        return "/pwarp menu";
    }

    @Override
    public void perform(Player player, String[] args) {
        Menu menu = new PwarpMainMenu();
        menu.open(player);
    }
}
