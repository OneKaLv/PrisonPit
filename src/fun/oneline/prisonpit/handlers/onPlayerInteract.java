package fun.oneline.prisonpit.handlers;

import fun.oneline.api.inventory.item.CustomItem;
import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.clans.ClanManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class onPlayerInteract implements Listener {
    public onPlayerInteract(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)){
            if(event.getPlayer().getItemInHand().equals(new CustomItem(Material.PAPER).setName("§e§lСоздание клана").build())){
                if(!ClanManager.hasClan(event.getPlayer())) {
                    ClanManager.clanDonate.add(event.getPlayer().getName());
                    ClanManager.createClanMenu(event.getPlayer(), "", "");
                } else {
                    event.getPlayer().sendMessage("§7[§e§lPrison §6§lPit§7]§c Вы уже состоите в клане!");
                }
            }
        }
    }
}
