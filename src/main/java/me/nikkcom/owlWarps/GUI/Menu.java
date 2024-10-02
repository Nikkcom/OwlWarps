package me.nikkcom.owlWarps.GUI;

import me.clip.placeholderapi.PlaceholderAPI;
import me.nikkcom.owlWarps.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

// https://www.youtube.com/watch?v=R5ic5DNziAw&t=305s

public class Menu {

    private static final Map<UUID, Menu> openMenus = new HashMap<>();
    private static final Map<String, Set<UUID>> viewers = new HashMap<>();
    private final Map<Integer, MenuClick> menuClickActions = new HashMap<>();
    private final List<Integer> playerHeadSlots = new ArrayList<>();
    private final Map<Integer, ItemStack> rawItems = new HashMap<>();

    private MenuClick generalClickAction;
    private MenuClick generalInvClickAction;
    private MenuDrag generalDragAction;
    private MenuOpen openAction;
    private MenuClose closeAction;

    public final UUID uuid;
    private final Inventory inventory;
    private final String viewerID;

    public Menu(int size, String name) {
        uuid = UUID.randomUUID();
        String parsedTitle = PlaceholderAPI.setPlaceholders(null, name);
        inventory = Bukkit.createInventory(null, size, parsedTitle);
        viewerID = null;
    }
    public Menu(int size, String name, String viewerID) {
        uuid = UUID.randomUUID();
        String parsedTitle = PlaceholderAPI.setPlaceholders(null, name);
        inventory = Bukkit.createInventory(null, size, parsedTitle);
        this.viewerID = viewerID;
    }

    public static Menu getMenu(Player p) { return openMenus.getOrDefault(p.getUniqueId(), null); }

    public void open(Player p) {
        parseItems(p);
        updatePlayerHeads(p);
        p.openInventory(inventory);
        openMenus.put(p.getUniqueId(), this);
        if (viewerID != null) addViewer(p);
        if (openAction != null) openAction.open(p);
        if (!playerHeadSlots.isEmpty()) updatePlayerHeads(p);

    }

    public void remove() {
        openMenus.entrySet().removeIf(entry -> {
            if (entry.getValue().getUuid() == uuid) {
                Player p = Bukkit.getPlayer(entry.getKey());
                if (p != null) {
                    if (viewerID != null) removeViewer(p);
                    if (closeAction != null) closeAction.close(p);
                }
                return true;
            }
            return false;
        });
    }

    public UUID getUuid() { return uuid; }

    private void addViewer(Player p) {
        if (viewerID == null) return;
        Set<UUID> list = viewers.getOrDefault(viewerID, new HashSet<>());
        list.add(p.getUniqueId());
        viewers.put(viewerID, list);
    }
    private void removeViewer(Player p) {
        if (viewerID == null) return;
        Set<UUID> list = viewers.getOrDefault(viewerID, null);
        if (list == null) return;
        list.remove(p.getUniqueId());
        if (list.isEmpty()) viewers.remove(viewerID);
        else viewers.put(viewerID, list);
    }
    public Set<Player> getViewers() {
        if (viewerID == null) return new HashSet<>();
        Set<Player> viewerList = new HashSet<>();
        for (UUID uuid : viewers.getOrDefault(viewerID, new HashSet<>())) {
            Player p = Bukkit.getPlayer(uuid);
            if (p == null) continue;
            viewerList.add(p);
        }
        return viewerList;
    }
    public MenuClick getAction(int index) { return menuClickActions.getOrDefault(index, null); }

    public interface MenuClick { void click(Player p, InventoryClickEvent event); }
    public interface MenuDrag { void drag(Player p, InventoryDragEvent event); }
    public interface MenuOpen { void open(Player p); }
    public interface MenuClose { void close(Player p); }

    private void setRawItem(int index, ItemStack item) {
        rawItems.put(index, item);
    }
    public void setItem(int index, ItemStack item) {
        setRawItem(index, item);
    }
    public void setItem(int index, ClickableItem clickableItem) {
        setRawItem(index, clickableItem.getItemStack());
        if (clickableItem.getAction() == null) menuClickActions.remove(index);
        else menuClickActions.put(index, clickableItem.getAction());
    }
    public void setItem(int index, ItemStack item, MenuClick action) {
        inventory.setItem(index, item);
        if (action == null) menuClickActions.remove(index);
        else menuClickActions.put(index, action);
    }
    public MenuClick getGeneralClickAction() { return generalClickAction; }

    protected void setGeneralClickAction(MenuClick generalClickAction) { this.generalClickAction = generalClickAction; }

    public MenuClick getGeneralInvClickAction() { return generalInvClickAction; }

    public MenuDrag getGeneralDragAction() { return generalDragAction; }

    protected void setGeneralDragAction(MenuDrag generalDragAction) { this.generalDragAction = generalDragAction; }
    protected void setOpenAction(MenuOpen openAction) { this.openAction = openAction; }

    protected void setCloseAction(MenuClose closeAction) { this.closeAction = closeAction; }
    protected void setGeneralInvClickAction(MenuClick generalInvClickAction) {
        this.generalInvClickAction = generalInvClickAction;
    }

    /**
     * Adds a player head slot index to the list of player head slots.
     *
     * @param index the index of the slot where a player's head should be displayed
     */
    public void setPlayerHeadSlot(int index) {
        this.playerHeadSlots.add(index);
    }

    /**
     * Retrieves the list of indices for the player head slots.
     *
     * @return a list of integers representing the indices of player head slots
     */
    public List<Integer> getPlayerHeadSlots() {
        return this.playerHeadSlots;
    }

    /**
     * Updates the player heads in the inventory for the specified player.
     * This method iterates through the registered player head slots and updates
     * each corresponding item to represent the head of the specified player.
     *
     * @param p the player whose head should be set in the designated slots
     */
    private void updatePlayerHeads(Player p) {

        for (int i : getPlayerHeadSlots()) {
            ItemStack head = rawItems.get(i);
            if (head == null || head.getType() != Material.PLAYER_HEAD || !head.hasItemMeta()) {
                continue;
            }
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            if (meta != null) {
                meta.setOwningPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
                head.setItemMeta(meta);
            }
        }
    }

    private void parseItems(Player p) {
        for (Map.Entry<Integer, ItemStack> entry : rawItems.entrySet()) {
            ItemStack parsedItem = parseItem(entry.getValue(), p);
            inventory.setItem(entry.getKey(), parsedItem);
        }
    }
    private ItemStack parseItem(ItemStack item, Player p) {
        ItemStack parsedItem = item.clone(); // Clone the original item
        ItemMeta meta = parsedItem.getItemMeta(); // Get the ItemMeta

        if (meta != null) {
            String parsedName = PlaceholderAPI.setPlaceholders(p, meta.getDisplayName());
            parsedName = StringUtil.color(parsedName);
            meta.setDisplayName(parsedName);

            if (meta.getLore() != null && !meta.getLore().isEmpty()) {
                List<String> parsedLore = new ArrayList<>();
                for (String str : meta.getLore()) {
                    str = PlaceholderAPI.setPlaceholders(p, str);
                    str = StringUtil.color(str);
                    parsedLore.add(str);
                }
                meta.setLore(parsedLore);
            }
        }
        parsedItem.setItemMeta(meta);
        return parsedItem;
    }
}
