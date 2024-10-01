package me.nikkcom.owlWarps.commands.pwarp.subcommands;

import me.nikkcom.owlWarps.OwlWarps;
import me.nikkcom.owlWarps.commands.SubCommand;
import me.nikkcom.owlWarps.configuration.Messages;
import me.nikkcom.owlWarps.playerwarps.PlayerWarp;
import me.nikkcom.owlWarps.playerwarps.PlayerWarpManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class CreateCommand extends SubCommand {

    private final OwlWarps owlWarps;

    public CreateCommand(OwlWarps owlWarps) {
        this.owlWarps = owlWarps;
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Creates a player warp at the players location";
    }

    @Override
    public String getSyntax() {
        return "/pwarp create <warp>";
    }

    @Override
    public void perform(Player player, String[] args) {
        // Check permissions to create.


        PlayerWarpManager manager = owlWarps.getPlayerWarpManager();
        if (args.length != 2) {
            player.sendMessage(getSyntax());
            return;
        }

        String warpName = args[1];

        if (manager.isWarp(warpName)) {
            player.sendMessage(Messages.getMessage("warp-already-exists"));
            return;
        }


        // Check permission to create additional warp. pwarp.6 limit.

        PlayerWarp warp = new PlayerWarp();
        warp.setLocation(player.getLocation());
        warp.setOwnerUUID(player.getUniqueId());
        warp.setLastActive(LocalDateTime.now());
        warp.setName(warpName);
        warp.setRawName(warpName.toLowerCase());

        manager.addWarp(warp);
        player.sendMessage(Messages.getMessage("warp-create-successful").replace("%warp%", warp.getRawName()));

    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return Collections.emptyList();
    }
}
