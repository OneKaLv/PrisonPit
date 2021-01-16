package fun.oneline.prisonpit.handlers;

import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

public class onBlockDamage implements Listener {
    public onBlockDamage(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDamage(BlockDamageEvent event){
        if(event.getPlayer().isOnGround()){
            return;
        }
        if(event.getPlayer().isFlying()){
            return;
        }
        Block block = event.getBlock();
        int rank = 0;
        int level = PrisonPitPlayerManager.getPrisonPitPlayer(event.getPlayer().getName()).getBoosterMoney_level();
        while (level >= 18) {
            level = level - 18;
            rank++;
        }
        if(block.getType() == Material.CONCRETE) {
            PacketPlayOutBlockChange packetPlayOutBlockChange = new PacketPlayOutBlockChange(((CraftWorld) Bukkit.getWorlds().get(0)).getHandle(), new BlockPosition(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ()));
            packetPlayOutBlockChange.block = net.minecraft.server.v1_12_R1.Block.getByCombinedId(Material.CONCRETE.getId() + (rank << 12));
            ((CraftPlayer) event.getPlayer()).getHandle().playerConnection.sendPacket(packetPlayOutBlockChange);
        }
    }
}
