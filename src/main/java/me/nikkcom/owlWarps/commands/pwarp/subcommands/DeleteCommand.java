package me.nikkcom.owlWarps.commands.pwarp.subcommands;

import me.nikkcom.owlWarps.OwlWarps;
import me.nikkcom.owlWarps.commands.SubCommand;
import me.nikkcom.owlWarps.configuration.Message;
import me.nikkcom.owlWarps.configuration.Messages;
import me.nikkcom.owlWarps.playerwarps.PlayerWarp;
import me.nikkcom.owlWarps.playerwarps.PlayerWarpManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DeleteCommand extends SubCommand {

    private final OwlWarps owlWarps;
    private final PlayerWarpManager manager;

    public DeleteCommand(OwlWarps owlWarps) {
        this.owlWarps = owlWarps;
        manager = owlWarps.getPlayerWarpManager();
    }

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "Deletes the specified playerwarp.";
    }

    @Override
    public String getSyntax() {
        return "/pwarp delete <warp>";
    }

    @Override
    public void perform(Player player, String[] args) {
        // Check permission

        PlayerWarpManager manager = owlWarps.getPlayerWarpManager();
        if (args.length != 2) {
            player.sendMessage(Message.PWARP_DELETE_HELP.papiColor(player));
            return;
        }

        String warpName = args[1];

        if (!manager.isWarp(warpName)) {
            player.sendMessage(Message.PWARP_DELETE_NOEXIST.papiColor(player, warpName));
            return;
        }
        PlayerWarp warp = manager.getPlayerWarpsByName().get(warpName.toLowerCase());
        if (warp.getOwnerUUID() != player.getUniqueId()) {
            player.sendMessage(Message.PWARP_DELETE_NOPERM.papiColor(player, warpName));
            return;
        }

        player.sendMessage(Message.PWARP_DELETE_SUCCESS.papiColor(player, warpName));
        manager.removeWarp(warp);
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {

        List<String> completions = new ArrayList<>();
        List<PlayerWarp> warps = owlWarps.getPlayerWarpManager().getPlayerWarpsByOwner().get(player.getUniqueId());
        for (PlayerWarp warp : warps) completions.add(warp.getRawName());
        if (args.length == 1) {
            return completions;
        } else if (args.length == 2) {

            for (String warp : completions) {
                if (!warp.startsWith(args[1].toLowerCase())){
                    completions.remove(warp);
                }
            }
            Collections.sort(completions);
            return completions;
        }

        return Collections.emptyList();
    }
}
