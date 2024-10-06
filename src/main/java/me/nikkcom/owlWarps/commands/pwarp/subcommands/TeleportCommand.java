package me.nikkcom.owlWarps.commands.pwarp.subcommands;

import me.nikkcom.owlWarps.OwlWarps;
import me.nikkcom.owlWarps.commands.SubCommand;
import me.nikkcom.owlWarps.playerwarps.PlayerWarpManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TeleportCommand extends SubCommand {

    private final OwlWarps owlWarps;

    public TeleportCommand(OwlWarps owlWarps) {
        this.owlWarps = owlWarps;
    }

    @Override
    public String getName() {
        return "teleport";
    }

    @Override
    public String getDescription() {
        return "Teleports the player to the specified warp";
    }

    @Override
    public String getSyntax() {
        return "/pwarp teleport <warp>";
    }

    @Override
    public void perform(Player player, String[] args) {
        PlayerWarpManager manager = PlayerWarpManager.getInstance();

        if (args.length != 2) {
            player.sendMessage(getSyntax());
            return;
        }
        String warpName = args[1];

        if (!manager.isWarp(warpName)) {
            player.sendMessage("NIKO - warp is not a valid warp");
            return;
        }

        manager.getPlayerWarpsByName().get(warpName).teleport(player);
    }


    public List<String> onTabComplete(Player player, String[] args) {
        List<String> completions = new ArrayList<>();
        Set<String> warps = PlayerWarpManager.getInstance().getPlayerWarpsByName().keySet();
        completions.addAll(warps);
        if (args.length == 1) {
            return completions;
        } else if (args.length == 2) {

            for (String warp : completions) {
                if (!warp.startsWith(args[1].toLowerCase())) {
                    completions.remove(warp);
                }
            }
            Collections.sort(completions);
            return completions;
        }

        return Collections.emptyList();
    }
}
