package fun.oneline.prisonpit.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Box {

    private double minX;
    private double minY;
    private double minZ;
    private double maxX;
    private double maxY;
    private double maxZ;

    public Box(Location location){
        this.minX = Math.min(location.getX() - 0.5, location.clone().getX() + 0.5);
        this.maxX = Math.max(location.getX() - 0.5, location.clone().getX() + 0.5);

        this.minY = location.getY() - 0.8;
        this.maxY = location.getY() + 2.0;

        this.minZ = Math.min(location.getZ() - 0.5, location.clone().getZ() + 0.5);
        this.maxZ = Math.max(location.getZ() - 0.5, location.clone().getZ() + 0.5);
    }

    public boolean isInBox(Location l) {
        return  (l.getX() >= minX && l.getX() <= maxX)
                && (l.getY() >= minY && l.getY() <= maxY)
                && (l.getZ() >= minZ && l.getZ() <= maxZ);
    }
}
