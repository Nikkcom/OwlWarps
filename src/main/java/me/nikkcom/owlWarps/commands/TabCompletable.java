package me.nikkcom.owlWarps.commands;

import org.bukkit.entity.Player;

import java.util.List;

public interface TabCompletable {
    List<String> onTabComplete(Player player, String[] args);
}
