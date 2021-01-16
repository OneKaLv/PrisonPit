package fun.oneline.prisonpit.board;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BoardUpdater extends BukkitRunnable {
    @Override
    public void run() {
        Board.startanim();
        for (Player player : Bukkit.getOnlinePlayers()) {
            Board.updateScoreBoard(player);
        }
    }
}
