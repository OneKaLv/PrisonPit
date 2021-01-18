package fun.oneline.prisonpit.shaft;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.ChunkCoordIntPair;
import com.comphenix.protocol.wrappers.MultiBlockChangeInfo;
import com.comphenix.protocol.wrappers.WrappedBlockData;
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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<Chunk, List<Location>> chunkListMap = new HashMap<>();
        for (int y = maxY; y >= minY; y--) {
            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Location location = new Location(Bukkit.getWorlds().get(0) , x , y , z);
                    if (location.getBlock().getType() == Material.CONCRETE) {
                        if(!chunkListMap.containsKey(location.getChunk())) chunkListMap.put(location.getChunk() , new ArrayList<>());
                        chunkListMap.get(location.getChunk()).add(location);
                    }
                }
            }
        }
        int finalRank = rank;
        chunkListMap.forEach((chunk, locations) -> {
            updateBlocks(player , chunk.getX() , chunk.getZ() , locations , (short) finalRank);
        });
    }


    public static void updateBlocks(Player player , int x , int z , List<Location> blocks , short data) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.MULTI_BLOCK_CHANGE);
        MultiBlockChangeInfo[] info = new MultiBlockChangeInfo[blocks.size()];
        for(int i = 0; i < blocks.size(); i++) {
            info[i] = new MultiBlockChangeInfo(blocks.get(i), WrappedBlockData.createData(Material.CONCRETE, data));
        }
        packet.getChunkCoordIntPairs().write(0, new ChunkCoordIntPair(x , z));
        packet.getMultiBlockChangeInfoArrays().write(0, info);
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
