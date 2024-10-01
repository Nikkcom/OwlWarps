package me.nikkcom.owlWarps.commands;

import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public abstract class SubCommand implements TabCompletable {

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract void perform(Player player, String[] args);
}
