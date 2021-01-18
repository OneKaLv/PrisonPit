package fun.oneline.prisonpit.shaft;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.mysql.jdbc.util.Base64Decoder;
import com.sun.xml.internal.ws.api.message.Packet;
import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.player.PrisonPitPlayer;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftMagicNumbers;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;
import ru.dream.network.core.utils.primitive.IntWrapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PacketListener extends PacketAdapter {

    private final IBlockData data = CraftMagicNumbers.getBlock(Material.CONCRETE).getBlockData();

    public PacketListener() {
        super(Main.instance , ListenerPriority.NORMAL , PacketType.Play.Server.BLOCK_CHANGE);
    }


    @Override
    public void onPacketSending(PacketEvent event) {
        PacketPlayOutBlockChange packetPlayOutBlockChange = (PacketPlayOutBlockChange) event.getPacket().getHandle();
        if(packetPlayOutBlockChange.block.equals(data)) {
            PrisonPitPlayer player = PrisonPitPlayerManager.getPrisonPitPlayer(event.getPlayer().getName());
            if(player == null) return;
            int rank = player.getBoosterMoney_level() / 18;
            if(rank != 0) {
                packetPlayOutBlockChange.block = net.minecraft.server.v1_12_R1.Block
                        .getByCombinedId(Material.CONCRETE.getId() + (rank << 12));
            }
        }

    }
}
