package fun.oneline.prisonpit.shaft;

import fun.oneline.prisonpit.player.PrisonPitPlayer;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ShaftUpdaterForSinglePlayer {

    private static Location loc1 = new Location(Bukkit.getWorlds().get(0), 139, 51, -82);
    private static Location loc2 = new Location(Bukkit.getWorlds().get(0), 173, 16, -48);

    public static void update(Player player) {
        int minX = (int) Math.min(loc1.getX(), loc2.getX());
        int minY = (int) Math.min(loc1.getY(), loc2.getY());
        int minZ = (int) Math.min(loc1.getZ(), loc2.getZ());
        int maxX = (int) Math.max(loc1.getX(), loc2.getX());
        int maxY = (int) Math.max(loc1.getY(), loc2.getY());
        int maxZ = (int) Math.max(loc1.getZ(), loc2.getZ());

        PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
        int rank = 0;
        int level = prisonPitPlayer.getBoosterMoney_level();
        while (level >= 18) {
            level = level - 18;
            rank++;
        }
        if(rank != 0) {
            for (int y = maxY; y >= minY; y--) {
                for (int x = minX; x <= maxX; x++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        if (Bukkit.getWorlds().get(0).getBlockAt(x, y, z).getType() == Material.CONCRETE) {
                            PacketPlayOutBlockChange packetPlayOutBlockChange = new PacketPlayOutBlockChange(((CraftWorld) Bukkit.getWorlds().get(0)).getHandle(), new BlockPosition(x, y, z));
                            packetPlayOutBlockChange.block = net.minecraft.server.v1_12_R1.Block.getByCombinedId(Material.CONCRETE.getId() + (rank << 12));
                            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutBlockChange);
                        }
                    }
                }
            }
        }
    }
}
