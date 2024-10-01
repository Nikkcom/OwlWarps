package me.nikkcom.owlWarps.commands;

import me.nikkcom.owlWarps.OwlWarps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandManager implements CommandExecutor, TabCompleter {

    protected final OwlWarps owlWarps;

    public CommandManager(OwlWarps owlWarps) {
        this.owlWarps = owlWarps;
    }

    @Override
    public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

    @Override
    public abstract List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args);

    public abstract ArrayList<SubCommand> getSubCommands();
}
