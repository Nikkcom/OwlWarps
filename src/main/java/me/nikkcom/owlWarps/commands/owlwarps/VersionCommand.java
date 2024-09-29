package me.nikkcom.owlWarps.commands.owlwarps;

import me.nikkcom.owlWarps.commands.SubCommand;
import me.nikkcom.owlWarps.configuration.Messages;
import org.bukkit.entity.Player;

public class VersionCommand extends SubCommand {
    @Override
    public String getName() {
        return "version";
    }

    @Override
    public String getDescription() {
        return "Displays the current version of the plugin";
    }

    @Override
    public String getSyntax() {
        return "/pwarp version";
    }

    @Override
    public void perform(Player player, String[] args) {
        player.sendMessage(Messages.getMessage("version-message"));
    }
}
