package fun.oneline.prisonpit.shaft;

import fun.oneline.prisonpit.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;


public class ShaftGenerator extends BukkitRunnable {

    private static Location loc1 = new Location(Bukkit.getWorlds().get(0), 139, 51, -82);
    private static Location loc2 = new Location(Bukkit.getWorlds().get(0), 173, 16, -48);

    @Override
    public void run() {
        int minX = (int) Math.min(loc1.getX(), loc2.getX());
        int minY = (int) Math.min(loc1.getY(), loc2.getY());
        int minZ = (int) Math.min(loc1.getZ(), loc2.getZ());
        int maxX = (int) Math.max(loc1.getX(), loc2.getX());
        int maxY = (int) Math.max(loc1.getY(), loc2.getY());
        int maxZ = (int) Math.max(loc1.getZ(), loc2.getZ());

        Location safeLoc;
        for (Entity ent : Bukkit.getWorlds().get(0).getEntities()) {
            if (ent.getLocation().getBlockX() >= minX && ent.getLocation().getBlockX() <= maxX
                    && ent.getLocation().getBlockY() >= minY && ent.getLocation().getBlockY() <= maxY
                    && ent.getLocation().getBlockZ() >= minZ && ent.getLocation().getBlockZ() <= maxZ) {
                if (ent instanceof Item || ent instanceof FallingBlock) {
                    ent.remove();
                } else {
                    safeLoc = ent.getLocation().clone();
                    safeLoc.setY(maxY + 1.5D);
                    ent.teleport(safeLoc);
                }
            }
        }
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    if(Bukkit.getWorlds().get(0).getBlockAt(x,y,z).getType() == Material.AIR) {
                        Bukkit.getWorlds().get(0).getBlockAt(x,y,z).setType(Material.CONCRETE);
                    }
                }
            }
        }
        new BukkitRunnable(){
            @Override
            public void run() {
                new ShaftUpdater().runTaskAsynchronously(Main.instance);
            }
        }.runTaskAsynchronously(Main.instance);
    }
}