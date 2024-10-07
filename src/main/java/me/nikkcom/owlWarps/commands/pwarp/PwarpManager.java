package me.nikkcom.owlWarps.commands.pwarp;

import me.nikkcom.owlWarps.OwlWarps;
import me.nikkcom.owlWarps.commands.CommandManager;
import me.nikkcom.owlWarps.commands.SubCommand;
import me.nikkcom.owlWarps.commands.pwarp.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PwarpManager is handling pwarp commands.
 * <p>
 * This class manages subcommands associated
 * with the pwarp command.
 * </p>
 */
public class PwarpManager extends CommandManager implements CommandExecutor, TabCompleter {

    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    /**
     * Class constructor with the main class instance.
     *
     * @param owlWarps The instance of the main class plugin instance.
     */
    public PwarpManager(OwlWarps owlWarps) {
        super(owlWarps);

        subCommands.add(new MenuCommand());
        subCommands.add(new CreateCommand(owlWarps));
        subCommands.add(new DeleteCommand(owlWarps));
        subCommands.add(new ListCommand(owlWarps));
        subCommands.add(new TeleportCommand(owlWarps));
        subCommands.add(new ReloadCommand(owlWarps));
        subCommands.add(new TestMenuOneCommand());
        subCommands.add(new TestMenuTwoCommand());
    }

    /**
     * Handles the command execution for pwarp commands.
     *
     * @param sender  The command sender (player or console).
     * @param command The command that was executed.
     * @param label   The alias of the command that was used.
     * @param args    The arguments passed to the command.
     *
     * @return true if the command was successfully executed; false otherwise.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {

                MenuCommand menuCommand = new MenuCommand();
                menuCommand.perform(player, args);
            }
            if (args.length > 0) {
                for (int i = 0; i < subCommands.size(); i++) {
                    if (args[0].equalsIgnoreCase(subCommands.get(i).getName())) {
                        getSubCommands().get(i).perform(player, args);
                        return true;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }

        if (!command.getName().equalsIgnoreCase("pwarp")) {
            return Collections.emptyList();
        }

        Player player = (Player) sender;

        for (SubCommand subCommand : subCommands) {
            completions.add(subCommand.getName());
        }
        Collections.sort(completions);

        if (args.length == 0) {
            return completions;
        } else if (args.length == 1) {
            completions.removeIf(str -> !str.startsWith(args[0].toLowerCase()));
            Collections.sort(completions);
            return completions;
        } else if (args.length > 1) {

            for (SubCommand subCommand : subCommands) {
                if (subCommand.getName().equalsIgnoreCase(args[0])) {
                    return subCommand.onTabComplete(player, args);
                }
            }
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
