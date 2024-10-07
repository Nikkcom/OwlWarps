package me.nikkcom.owlWarps.GUI;

import me.clip.placeholderapi.PlaceholderAPI;
import me.nikkcom.owlWarps.GUI.events.MenuClick;
import me.nikkcom.owlWarps.GUI.events.MenuClose;
import me.nikkcom.owlWarps.GUI.events.MenuDrag;
import me.nikkcom.owlWarps.GUI.events.MenuOpen;
import me.nikkcom.owlWarps.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Menu class that represents a GUI menu for the game.
 */
public class MenuImpl implements Menu {

    private static final Map<UUID, Menu> openMenus = new HashMap<>();
    private static final Map<String, Set<UUID>> viewers = new HashMap<>();

    public final UUID uuid;

    protected final Inventory inventory;

    protected final Map<Integer, MenuClick> menuClickActions = new HashMap<>();
    private final Map<Integer, MenuItem> rawStaticMenuItems = new HashMap<>();
    private final String viewerId;

    private MenuClick generalClickAction;
    private MenuClick generalInvClickAction;
    private MenuDrag generalDragAction;
    private MenuOpen openAction;
    private MenuClose closeAction;

    /**
     * Constructs a Menu with a given size and title.
     *
     * @param size  The size of the menu (number of slots)
     * @param title The title of the menu
     */
    public MenuImpl(int size, String title) {
        this(size, title, null);
    }

    /**
     * Constructs a Menu with a given size, title, and viewer ID.
     *
     * @param size     The size of the menu (number of slots)
     * @param title    The title of the menu
     * @param viewerId The ID used to track viewers
     */
    public MenuImpl(int size, String title, String viewerId) {
        this.uuid = UUID.randomUUID();
        String parsedTitle = PlaceholderAPI.setPlaceholders(null, title);
        this.inventory = Bukkit.createInventory(null, size, parsedTitle);
        this.viewerId = viewerId;
    }

    /**
     * Retrieves the menu associated with a given player.
     *
     * @param player The player whose menu is to be retrieved
     *
     * @return The Menu associated with the player or null if none exists
     */
    public static Menu getMenu(Player player) {
        return openMenus.getOrDefault(player.getUniqueId(), null);
    }

    /**
     * Opens the menu for the specified player.
     *
     * @param player The player to open the menu for
     */
    @Override
    public void open(Player player) {
        // Parses the raw items and sets them in the inventory. Before it opens.
        updateStaticItems(player);

        // Opens the inventory for the player.
        player.openInventory(inventory);

        // Adds the player to the viewer list.
        openMenus.put(player.getUniqueId(), this);

        // Add the player to the viewers map. If viewerId is set.
        if (viewerId != null) addViewer(player);

        // Trigger the open action, if open action is set.
        if (openAction != null) openAction.open(player);
    }

    /**
     * Removes this menu from the list of open menus for all players.
     * If a player is currently viewing this menu, it will also trigger the
     * close action and remove the player from the viewers list.
     */
    @Override
    public void remove() {
        openMenus.entrySet().removeIf(entry -> {

            // Check if the menu UUID matches the current menu's UUID.
            if (!entry.getValue().getUuid().equals(uuid)) {
                return false; // Do not remove if the Ids don't match.
            }

            // Retrieves the player associated with the menu entry.
            Player player = Bukkit.getPlayer(entry.getKey());
            if (player != null) {

                // Remove the player from the viewers list if applicable.
                if (viewerId != null) removeViewer(player);

                // Trigger the close action if set.
                if (closeAction != null) closeAction.close(player); // Trigger close action
            }
            return true;
        });
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public void addMenuitem(int index, MenuItem menuItem) {
        setRawItem(index, menuItem);

        if (menuItem.getAction() == null) menuClickActions.remove(index);
        else menuClickActions.put(index, menuItem.getAction());
    }


    /**
     * Gets the unique identifier of the menu.
     *
     * @return The UUID of the menu
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Updates all static items in the menu by re-parsing placeholders and setting them in their respective slots.
     *
     * @param player The player for whom the items are being updated.
     */
    public void updateStaticItems(Player player) {
        // Parse all raw static menu items and set them in the inventory
        for (Map.Entry<Integer, MenuItem> entry : rawStaticMenuItems.entrySet()) {
            ItemStack parsedItem = parseItem(entry.getValue().getItem(), player);
            inventory.setItem(entry.getKey(), parsedItem);
        }
    }

    /**
     * Sets an item in the menu with an associated menuclick action.
     *
     * @param index    The index of the slot
     * @param menuItem The menu item to set
     */
    public void setItem(int index, MenuItem menuItem) {
        setRawItem(index, menuItem);

        // Removes the action if null.
        if (menuItem.getAction() == null) menuClickActions.remove(index);

            // Puts action if not null
        else menuClickActions.put(index, menuItem.getAction());
    }

    /**
     * Retrieves the general click action for the menu.
     *
     * @return the general click action associated with the menu.
     */
    public MenuClick getGeneralClickAction() {
        return generalClickAction;
    }

    /**
     * Sets the general click action for the menu.
     *
     * @param generalClickAction the action to be set as the general click action.
     */
    public void setGeneralClickAction(MenuClick generalClickAction) {
        this.generalClickAction = generalClickAction;
    }

    /**
     * Retrieves the general inventory click action for the menu.
     *
     * @return the general inventory click action associated with the menu.
     */
    public MenuClick getGeneralInvClickAction() {
        return generalInvClickAction;
    }

    /**
     * Sets the general inventory click action for the menu.
     *
     * @param generalInvClickAction the action to be set as the general inventory click action.
     */
    public void setGeneralInvClickAction(MenuClick generalInvClickAction) {
        this.generalInvClickAction = generalInvClickAction;
    }

    /**
     * Retrieves the general drag action for the menu.
     *
     * @return the general drag action associated with the menu.
     */
    public MenuDrag getGeneralDragAction() {
        return generalDragAction;
    }

    /**
     * Sets the general drag action for the menu.
     *
     * @param generalDragAction the action to be set as the general drag action.
     */
    public void setGeneralDragAction(MenuDrag generalDragAction) {
        this.generalDragAction = generalDragAction;
    }

    /**
     * Sets the action to be executed when the menu is opened.
     *
     * @param openAction the action to be set as the open action.
     */
    public void setOpenAction(MenuOpen openAction) {
        this.openAction = openAction;
    }

    /**
     * Sets the action to be executed when the menu is closed.
     *
     * @param closeAction the action to be set as the close action.
     */
    public void setCloseAction(MenuClose closeAction) {
        this.closeAction = closeAction;
    }

    /**
     * Retrieves the current viewers of the menu.
     *
     * @return A set of players currently viewing this menu
     */
    public Set<Player> getViewers() {
        // Return empty set if no viewerID is set
        if (viewerId == null) return new HashSet<>();

        Set<Player> viewerList = new HashSet<>();

        // Iterate through the UUIDs of viewers associated with the viewerId
        for (UUID uuid : viewers.getOrDefault(viewerId, new HashSet<>())) {

            // Get player by UUID
            Player player = Bukkit.getPlayer(uuid);

            // Add player to the list if they are online
            if (player != null) viewerList.add(player);
        }

        // Return the set of players
        return viewerList;
    }

    /**
     * Gets the action associated with a specific slot index.
     *
     * @param index The index of the slot
     *
     * @return The MenuClick action associated with the slot
     */
    public MenuClick getAction(int index) {
        return menuClickActions.getOrDefault(index, null); // Return the action for the index or null
    }

    /**
     * Retrieves the player associated with the current menu.
     * <p>
     * This method searches through the open menus to find the player
     * whose UUID matches the UUID of this menu instance. If a matching
     * player is found, the method returns the corresponding player
     * object; otherwise, it returns null.
     *
     * @return the player associated with this menu, or null if no player is found.
     */
    public Player getPlayer() {
        for (Map.Entry<UUID, Menu> entry : openMenus.entrySet()) {
            if (entry.getValue().getUuid().equals(this.getUuid())) {
                return Bukkit.getPlayer(entry.getKey());
            }
        }
        return null;
    }

    public int clickaction() {
        return menuClickActions.size();
    }

    /**
     * Sets a raw menu item in the specified slot.
     * Used to store items for easier colorizing and parsing of placeholders.
     *
     * @param index The index of the slot
     * @param item  The menu item to be set
     */
    private void setRawItem(int index, MenuItem item) {
        rawStaticMenuItems.put(index, item);
    }

    /**
     * Sets an item in the menu without a menuclick action.
     * Removes current clickaction associated with the inventory slot.
     *
     * @param index     The index of the slot
     * @param itemStack The item to set
     */
    public void setItem(int index, ItemStack itemStack) {
        MenuItem menuItem = new MenuItemImpl(itemStack, null);
        setItem(index, menuItem);
    }

    /**
     * Parses the given ItemStack for a specific player.
     * Parses placeholders in relation to the player, then colorizes the text.
     * It then returns the final item.
     *
     * @param item   The ItemStack to colorize and parse placeholders.
     * @param player The player for whom the item is being parsed, allowing for player-specific placeholders.
     *
     * @return The parsed ItemStack, which has been parsed and colorized in relation to the player.
     */
    protected ItemStack parseItem(ItemStack item, Player player) {
        ItemStack parsedItem = item.clone(); // Clone the original item
        ItemMeta meta = parsedItem.getItemMeta(); // Get the ItemMeta

        if (meta != null) {
            String parsedName = PlaceholderAPI.setPlaceholders(player, meta.getDisplayName());
            parsedName = StringUtil.color(parsedName);
            meta.setDisplayName(parsedName);

            if (meta.getLore() != null && !meta.getLore().isEmpty()) {
                List<String> parsedLore = new ArrayList<>();
                for (String str : meta.getLore()) {
                    str = PlaceholderAPI.setPlaceholders(player, str);
                    str = StringUtil.color(str);
                    parsedLore.add(str);
                }
                meta.setLore(parsedLore);
            }
        }
        parsedItem.setItemMeta(meta);
        return parsedItem;
    }

    /**
     * Adds a player to the list of viewers for this menu.
     *
     * @param player The player to add
     */
    public void addViewer(Player player) {
        // Does nothing if no viewerID is set
        if (viewerId == null) return;

        // Adds the player Uuid to the viewer set in the viewers hashmap.
        viewers.computeIfAbsent(viewerId, k -> new HashSet<>()).add(player.getUniqueId());
    }

    /**
     * Removes a player from the list of viewers for this menu.
     *
     * @param player The player to remove
     */
    public void removeViewer(Player player) {
        // Does nothing if no viewerID is set
        if (viewerId == null) return;

        Set<UUID> list = viewers.get(viewerId);
        if (list != null) {

            // Remove player UUID from viewer set
            list.remove(player.getUniqueId());

            // Remove viewerID from the map if empty
            if (list.isEmpty()) viewers.remove(viewerId);
        }
    }
}
