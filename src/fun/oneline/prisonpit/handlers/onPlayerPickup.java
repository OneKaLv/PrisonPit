package fun.oneline.prisonpit.handlers;

import fun.oneline.prisonpit.Main;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class onPlayerPickup implements Listener {
    public onPlayerPickup(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerPickUp(EntityPickupItemEvent e){
        if(e.getEntity() instanceof EntityPlayer){
            if(e.getItem().equals(Material.DIAMOND_SWORD)){
                e.setCancelled(true);
            }
        }
    }
}
