package me.nikkcom.owlWarps.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.List;

public class MenuLoader {
    private final File menuDirectory;



    public MenuLoader(File menuDirectory){
        this.menuDirectory = menuDirectory;
    }


    public Menu loadMenu(String viewerID) {
        File menuFile = new File(menuDirectory, viewerID + ".yml");
        if (!menuFile.exists()) {

            // Her må du fikse
            Bukkit.getLogger().severe("Menu not found");
            return null;
        }

        FileConfiguration conf = YamlConfiguration.loadConfiguration(menuFile);
        ConfigurationSection menuSection = conf.getConfigurationSection("menu");

        if (menuSection == null) return null;

        String name = menuSection.getString("name");
        int size = menuSection.getInt("size");

        Menu menu = new Menu(size, name, viewerID);

        ConfigurationSection itemsSection = menuSection.getConfigurationSection("items");
        if (itemsSection != null) {

            for (String itemKey : itemsSection.getKeys(false)) {
                ConfigurationSection itemConfig = itemsSection.getConfigurationSection(itemKey);

                if (itemConfig != null) {

                    String slot = itemConfig.getString("slot");
                    String material = itemConfig.getString("material");
                    String itemname = itemConfig.getString("name");
                    List<String> lore = itemConfig.getStringList("lore");

                    ItemStack item = new ItemStack(Material.valueOf(material));
                    ItemMeta meta = item.getItemMeta();
                    if (meta != null) {
                        meta.setDisplayName(itemname);
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                    }
                }
            }
        }
        return null;
    }
}
