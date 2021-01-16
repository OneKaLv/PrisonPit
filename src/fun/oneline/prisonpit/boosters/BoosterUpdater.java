package fun.oneline.prisonpit.boosters;

import org.bukkit.scheduler.BukkitRunnable;

public class BoosterUpdater extends BukkitRunnable {
    @Override
    public void run() {
        Booster.update();
    }
}
