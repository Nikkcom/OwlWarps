package me.nikkcom.owlWarps.GUI;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MenuFactory {
    private final MenuLoader menuLoader;
    private final Map<String, Menu> menus;

    public MenuFactory(File menuDirectory) {
        this.menuLoader = new MenuLoader(menuDirectory);
        this.menus = new HashMap<>();
    }

}
