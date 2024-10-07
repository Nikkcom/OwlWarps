package me.nikkcom.owlWarps.commands.pwarp.subcommands;

import me.nikkcom.owlWarps.GUI.Menu;
import me.nikkcom.owlWarps.GUI.menus.TestMenuOne;
import me.nikkcom.owlWarps.commands.SubCommand;
import org.bukkit.entity.Player;

import java.util.List;

public class TestMenuOneCommand extends SubCommand {
    @Override
    public String getName() {
        return "testmenuone";
    }

    @Override
    public String getDescription() {
        return "hua";
    }

    @Override
    public String getSyntax() {
        return "syntax";
    }

    @Override
    public void perform(Player player, String[] args) {
        Menu menu = new TestMenuOne();
        menu.open(player);
        player.sendMessage("Menu clickactions: " + menu.clickaction());
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return List.of();
    }
}
