package me.nikkcom.owlWarps.configuration;

import me.nikkcom.owlWarps.utils.StringUtil;
import org.bukkit.entity.Player;

public enum Message {

    CONSOLE_ONLYPLAYER("console-onlyPlayer"),

    PWARP_DELETE_HELP("pwarp-delete-getHelp"),
    PWARP_DELETE_NOPERM("pwarp-delete-noPermission"),
    PWARP_DELETE_SUCCESS("pwarp-delete-success"),
    PWARP_DELETE_NOEXIST("pwarp-delete-noExist"),


    PWARP_CREATE_HELP("pwarp-create-getHelp"),
    PWARP_CREATE_NOPERM("pwarp-create-noPermission"),
    PWARP_CREATE_SUCCESS("pwarp-create-success"),
    PWARP_CREATE_ALREADYEXISTING("pwarp-create-alreadyExisting"),
    PWARP_CREATE_REACHEDLIMIT("pwarp-create-reachedLimit"),

    PWARP_TELEPORT_SUCCESS("pwarp-teleport-success"),
    PWARP_TELEPORT_FAIL("pwarp-teleport-fail");

    private final String messageKey;


    Message(String key) {
        this.messageKey = key;


    }

    public String get() {
        return Messages.getMessage(messageKey);
    }

    public String papiColor(Player player, String warpName) {
        String message = Messages.getMessage(messageKey);
        message = StringUtil.papiColor(message, player, warpName);
        return message;
    }

    public String papiColor(Player player) {
        return papiColor(player, null);
    }

    public String papiColor() {
        return papiColor(null);
    }

    @Override
    public String toString() {
        return Messages.getMessage(messageKey); // Return the message directly
    }
}
