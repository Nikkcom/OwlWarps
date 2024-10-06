package me.nikkcom.owlWarps.commands.pwarp.subcommands;

import me.nikkcom.owlWarps.OwlWarps;
import me.nikkcom.owlWarps.commands.SubCommand;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadCommand extends SubCommand {

    private final OwlWarps owlWarps;

    public ReloadCommand(OwlWarps owlWarps) {
        this.owlWarps = owlWarps;
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload";
    }

    @Override
    public String getSyntax() {
        return "/pwarp reload";
    }

    @Override
    public void perform(Player player, String[] args) {

    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return List.of();
    }
}
