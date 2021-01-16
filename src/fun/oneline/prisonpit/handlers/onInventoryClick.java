package fun.oneline.prisonpit.handlers;

import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.items.Menu;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class onInventoryClick implements Listener {
    public onInventoryClick(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCompassClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            ItemStack item = event.getPlayer().getItemInHand();
            Player player = event.getPlayer();
            if (item != null && item.getType().equals(Material.COMPASS) && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase("§e§lМеню")) {
                Menu.showGuiMenu(player);
            }
        }
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent event) {
        if (!(event.getWhoClicked().getGameMode() == GameMode.CREATIVE)) {
            if (event.getClickedInventory() != null && event.getClickedInventory().getType() != null && event.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                event.setCancelled(true);
            }
            switch (event.getAction()) {
                case HOTBAR_MOVE_AND_READD: {
                    event.setCancelled(true);
                    break;
                }
                case NOTHING: {
                    event.setCancelled(true);
                    break;
                }
                case HOTBAR_SWAP: {
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void InventoryClickEvent(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void InventoryClickEvent(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}
