package fun.oneline.prisonpit.handlers;

import fun.oneline.prisonpit.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class onCraft implements Listener {

    public onCraft(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void Craft(CraftItemEvent event) {
        event.setCancelled(true);
    }
}