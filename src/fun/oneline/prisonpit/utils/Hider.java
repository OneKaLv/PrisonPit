package fun.oneline.prisonpit.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Hider {

    public static List<Player> hider = new ArrayList<>();

    public static void add(Player player) {
        if (hider.contains(player)) return;

        hider.add(player);

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online != player) {
                player.hidePlayer(online);
            }
        }
    }

    public static void remove(Player player) {
        if (!hider.contains(player)) return;

        hider.remove(player);

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online != player) {
                player.showPlayer(online);
            }
        }
    }

    public static void toggle(Player player) {
        if (hider.contains(player)) {
            player.sendMessage("§2§lТеперь вы §c§lвидите §2§lигроков.");
            remove(player);
        } else {
            player.sendMessage("§2§lВы больше §c§lне видите §2§lигроков.");
            add(player);
        }
    }
}
