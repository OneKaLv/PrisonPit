package fun.oneline.prisonpit.shaft;

import fun.oneline.prisonpit.player.PrisonPitPlayer;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShaftUpdater extends BukkitRunnable {

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
        Map<Chunk , List<Location>> locations = new HashMap<>();
        for (int y = maxY; y >= minY; y--) {
            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Location location = new Location(Bukkit.getWorlds().get(0) , x , y , z);
                    if (location.getBlock().getType() == Material.CONCRETE) {
                        if(!locations.containsKey(location.getChunk())) locations.put(location.getChunk() , new ArrayList<>());
                        locations.get(location.getChunk()).add(location);
                    }
                }
            }
        }
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
            int rank = 0;
            int level = prisonPitPlayer.getBoosterMoney_level();
            while (level >= 18) {
                level = level - 18;
                rank++;
            }
            if(rank != 0) {
                int finalRank = rank;
                locations.forEach((chunk, location) -> {
                    ShaftUpdaterForSinglePlayer.updateBlocks(player ,chunk.getX() , chunk.getZ() , location , (short) finalRank);
                });
            }
        }
    }
}

//package fun.oneline.prisonpit.shaft;
//
//import fun.oneline.prisonpit.player.PrisonPitPlayer;
//import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
//import net.minecraft.server.v1_12_R1.*;
//import org.bukkit.Bukkit;
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
//import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
//import org.bukkit.entity.Entity;
//import org.bukkit.entity.Player;
//import org.bukkit.scheduler.BukkitRunnable;
//
//public class ShaftUpdater extends BukkitRunnable {
//
//    private static Location loc1 = new Location(Bukkit.getWorlds().get(0), -39, 49, 24);
//    private static Location loc2 = new Location(Bukkit.getWorlds().get(0), -87, 24, -24);
//
//    @Override
//    public void run() {
//        int minX = (int) Math.min(loc1.getX(), loc2.getX());
//        int minY = (int) Math.min(loc1.getY(), loc2.getY());
//        int minZ = (int) Math.min(loc1.getZ(), loc2.getZ());
//        int maxX = (int) Math.max(loc1.getX(), loc2.getX());
//        int maxY = (int) Math.max(loc1.getY(), loc2.getY());
//        int maxZ = (int) Math.max(loc1.getZ(), loc2.getZ());
//
//        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
//            World world = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
//            Chunk chunk = world.getChunkAt(-60 >> 4, 5 >> 4);
//            PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
//            int rank = 0;
//            int level = prisonPitPlayer.getBoosterMoney_level();
//            while (level >= 18) {
//                level = level - 18;
//                rank++;
//            }
//            for (int x = minX; x <= maxX; x++) {
//                for (int y = minY; y <= maxY; y++) {
//                    for (int z = minZ; z <= maxZ; z++) {
//                        if (Bukkit.getWorlds().get(0).getBlockAt(x, y, z).getType() == Material.CONCRETE) {
//                            chunk.a(new BlockPosition(x, y, z), net.minecraft.server.v1_12_R1.Block.getByCombinedId(Material.CONCRETE.getId() + (rank << 12)));
//                        }
//                    }
//                }
//            }
//            PacketPlayOutMapChunk packetPlayOutMapChunk = new PacketPlayOutMapChunk(chunk, 0);
//            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutMapChunk);
//            Bukkit.getWorlds().get(0).refreshChunk(-4,0);
//        }
//    }
//}

