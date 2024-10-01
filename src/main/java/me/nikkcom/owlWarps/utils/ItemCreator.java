package me.nikkcom.owlWarps.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class ItemCreator {

    /**
     * Creates a new ItemStack with a specified material and display name.
     *
     * @param material The material of the item.
     * @param name The display name of the item.
     * @return The created ItemStack.
     */
    public static ItemStack create(Material material, String name) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        return item;
    }

    /**
     * Creates a new ItemStack with a specified material, display name, and lore.
     *
     * @param material The material of the item.
     * @param name The display name of the item.
     * @param lore A list of lore lines for the item.
     * @return The created ItemStack.
     */
    public static ItemStack create(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    /**
     * Creates a new ItemStack with durability.
     *
     * @param material The material of the item.
     * @param name The display name of the item.
     * @param durability The durability of the item.
     * @return The created ItemStack.
     */
    public static ItemStack create(Material material, String name, short durability) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        return item;
    }

    /**
     * Creates a new ItemStack with an enchantment.
     *
     * @param material The material of the item.
     * @param name The display name of the item.
     * @param enchantment The enchantment to apply to the item.
     * @param level The level of the enchantment.
     * @return The created ItemStack.
     */
    public static ItemStack create(Material material, String name, Enchantment enchantment, int level) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
            item.addUnsafeEnchantment(enchantment, level); // Add enchantment
        }
        return item;
    }

    /**
     * Creates a new ItemStack with a specified material, display name, and lore, along with enchantments.
     *
     * @param material The material of the item.
     * @param name The display name of the item.
     * @param lore A list of lore lines for the item.
     * @param enchantment The enchantment to apply to the item.
     * @param level The level of the enchantment.
     * @return The created ItemStack.
     */
    public static ItemStack create(Material material, String name, List<String> lore, Enchantment enchantment, int level) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(lore);
            item.setItemMeta(meta);
            item.addUnsafeEnchantment(enchantment, level); // Add enchantment
        }
        return item;
    }

    /**
     * Creates a player skull item with the given player's name.
     *
     * @param playerName The name of the player whose skull to create.
     * @param displayName The display name for the skull item.
     * @return The created player skull ItemStack.
     */
    public static ItemStack createPlayerSkull(String playerName, String displayName) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        if (skullMeta != null) {
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(playerName));
            skullMeta.setDisplayName(displayName);
            skull.setItemMeta(skullMeta);
        }
        return skull;
    }

    /**
     * Creates a player skull item with the given player's name, display name, and lore.
     *
     * @param playerName The name of the player whose skull to create.
     * @param displayName The display name for the skull item.
     * @param lore A list of lore lines for the skull item.
     * @return The created player skull ItemStack.
     */
    public static ItemStack createPlayerSkull(String playerName, String displayName, List<String> lore) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        if (skullMeta != null) {
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(playerName));
            skullMeta.setDisplayName(displayName);
            skullMeta.setLore(lore);
            skull.setItemMeta(skullMeta);
        }
        return skull;
    }
}
