package fun.oneline.prisonpit.handlers;

import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class onPlayerQuit implements Listener {

    public onPlayerQuit(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Player player = event.getPlayer();
        if (PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTimetostorm() < 1) {
            PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setTimetostorm(360);
        }
        onGoldBlockASClick.levelEntitiesHashMap.remove(event.getPlayer());
        onGoldBlockASClick.v2EntitiesHashMap.remove(event.getPlayer());
        PrisonPitPlayerManager.unLoadPrisonPitPlayer(player);
    }
}
