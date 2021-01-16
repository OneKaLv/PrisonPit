package fun.oneline.prisonpit.handlers;

import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.clans.ClanManager;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class onEntityInteractWithEntity implements Listener {
    public onEntityInteractWithEntity(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof ArmorStand) {
            if (event.getRightClicked().getCustomName() != null && event.getRightClicked().getCustomName().equals("§b§lКланы")) {
                if(ClanManager.hasClan(event.getPlayer())){
                    ClanManager.openClans(event.getPlayer());
                }else {
                    if (PrisonPitPlayerManager.getPrisonPitPlayer(event.getPlayer().getName()).getTotalStars() > 0) {
                        ClanManager.openClans(event.getPlayer());
                    } else {
                        event.getPlayer().sendMessage("§7[§e!§7]§e Откроется с 1 звёзды!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void despawnItems(ItemDespawnEvent event){
            event.setCancelled(true);
    }

    @EventHandler
    public void onInteractWithOther(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if (((Player) event.getDamager()).getGameMode().equals(GameMode.SURVIVAL)) {
                if (event.getEntity() instanceof ItemFrame) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDestroyPainting(HangingBreakByEntityEvent e) {
        if (e.getEntity() instanceof Painting) {
            if (e.getRemover() instanceof Player) {
                if (((Player) e.getRemover()).getGameMode().equals(GameMode.SURVIVAL)) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
