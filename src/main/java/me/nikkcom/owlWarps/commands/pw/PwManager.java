package me.nikkcom.owlWarps.commands.pw;

import me.nikkcom.owlWarps.OwlWarps;
import me.nikkcom.owlWarps.commands.SubCommand;
import me.nikkcom.owlWarps.commands.owlwarps.VersionCommand;
import me.nikkcom.owlWarps.playerwarps.PlayerWarp;
import me.nikkcom.owlWarps.playerwarps.PlayerWarpManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PwManager implements CommandExecutor, TabCompleter {
    private final OwlWarps owlWarps;
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    /**
     * Class constructor with the main class instance.
     *
     * @param owlWarps The instance of the main class plugin instance.
     */
    public PwManager(OwlWarps owlWarps) {
        this.owlWarps = owlWarps;

        subCommands.add(new VersionCommand());
    }

    /**
     * Handles the command execution for pwarp commands.
     *
     * @param sender The command sender (player or console).
     * @param command The command that was executed.
     * @param label The alias of the command that was used.
     * @param args The arguments passed to the command.
     * @return true if the command was successfully executed; false otherwise.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length != 1) {
                player.sendMessage("/pw <warp>");
                return true;
            }
            PlayerWarpManager manager = owlWarps.getPlayerWarpManager();

            String warpName = args[0];

            if (!manager.isWarp(warpName)) {
                player.sendMessage("warp-no-exists");
                return true;
            }

            manager.teleport(player, warpName);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }

        if (!command.getName().equalsIgnoreCase("pw")){
            return Collections.emptyList();
        }

        PlayerWarpManager manager = owlWarps.getPlayerWarpManager();
        HashMap<String, PlayerWarp> warps = manager.getPlayerWarpsByName();
        completions.addAll(warps.keySet());
        Collections.sort(completions);
        // Player has not entered any characters
        if (args.length == 0) {
            return completions;

            // Player has started typing
        } else if(args.length == 1) {
            String typed = args[0].toLowerCase();
            for (String warpName : completions) {
                if (warpName.toLowerCase().startsWith(typed)) {
                    completions.add(warpName);
                }
            }
            return completions; // Return the filtered list
        }
        return Collections.emptyList();
    }

    /**
     * Retrieves the list of subcommands associated with the pwarp command.
     *
     * @return An ArrayList of SubCommand objects.
     */
    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }
}
