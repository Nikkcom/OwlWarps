package me.nikkcom.owlWarps.commands.pwarp.subcommands;

import me.nikkcom.owlWarps.OwlWarps;
import me.nikkcom.owlWarps.commands.SubCommand;
import me.nikkcom.owlWarps.playerwarps.PlayerWarp;
import me.nikkcom.owlWarps.playerwarps.PlayerWarpManager;
import me.nikkcom.owlWarps.utils.StringUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListCommand extends SubCommand {

    private final OwlWarps owlWarps;
    public ListCommand(OwlWarps owlWarps) {
        this.owlWarps = owlWarps;
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Lists all playerwarps.";
    }

    @Override
    public String getSyntax() {
        return "/pwarp list";
    }

    @Override
    public void perform(Player player, String[] args) {
        // Check permission
        PlayerWarpManager manager = owlWarps.getPlayerWarpManager();
        player.sendMessage("Playerwarps:");
        for (PlayerWarp warp : manager.getWarps()) {
            player.sendMessage(warp.getRawName());
        }






    }



    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return Collections.emptyList();
    }
}
