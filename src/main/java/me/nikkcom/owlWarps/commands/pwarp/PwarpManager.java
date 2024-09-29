package me.nikkcom.owlWarps.commands.pwarp;

import me.nikkcom.owlWarps.OwlWarps;
import me.nikkcom.owlWarps.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
/**
 * PwarpManager is handling pwarp commands.
 * <p>
 * This class manages subcommands associated
 * with the pwarp command.
 * </p>
 */
public class PwarpManager implements CommandExecutor {

    private final OwlWarps owlWarps;
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    /**
     * Class constructor with the main class instance.
     *
     * @param owlWarps The instance of the main class plugin instance.
     */
    public PwarpManager(OwlWarps owlWarps) {
        this.owlWarps = owlWarps;
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
            if (args.length == 0){

                // Basic usage here
            }
            if (args.length > 0) {
                for (int i = 0; i < subCommands.size(); i++){
                    if (args[0].equalsIgnoreCase(subCommands.get(i).getName())) {
                        getSubCommands().get(i).perform(player, args);
                        return true;
                    }
                }
            }
        }
        return true;
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
