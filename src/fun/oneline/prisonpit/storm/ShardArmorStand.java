package fun.oneline.prisonpit.storm;

import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.boosters.Booster;
import fun.oneline.prisonpit.boosters.BoosterType;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import fun.oneline.prisonpit.tokens.TokenManager;
import fun.oneline.prisonpit.utils.Box;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class ShardArmorStand {
    public void spawn(Location loc, Player p) {
        WorldServer s = ((CraftWorld) loc.getWorld()).getHandle();
        EntityArmorStand stand = new EntityArmorStand(s);
        Random random = new Random();

        stand.setLocation(516 + random.nextInt(17), 69, 492 + random.nextInt(17), 0, 0);
        stand.setCustomNameVisible(false);
        stand.setBasePlate(true);
        stand.setNoGravity(true);
        stand.setInvisible(true);
        stand.setInvulnerable(true);
        PacketPlayOutSpawnEntityLiving spawnP = new PacketPlayOutSpawnEntityLiving(stand);
        PacketPlayOutEntityEquipment EquipP = new PacketPlayOutEntityEquipment(stand.getId(), EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(Material.PRISMARINE_SHARD)));
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(spawnP);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(EquipP);
        anim(stand, p);
    }

    private void anim(EntityLiving entityLiving, Player p) {
        new BukkitRunnable() {
            Box box = new Box((entityLiving.getBukkitEntity().getLocation()));
            boolean onground = true;
            @Override
            public void run() {
                Random random = new Random();
                entityLiving.setLocation(entityLiving.getBukkitEntity().getLocation().getX(), entityLiving.getBukkitEntity().getLocation().getY() - 0.10f, entityLiving.getBukkitEntity().getLocation().getZ(),entityLiving.getBukkitYaw() + 1f, 0);
                PacketPlayOutEntityTeleport packetPlayOutEntityTeleport = new PacketPlayOutEntityTeleport(entityLiving);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetPlayOutEntityTeleport);
                box = new Box(entityLiving.getBukkitEntity().getLocation());
                if(box.isInBox(p.getLocation())){
                    if(TokenManager.moreShards.contains(p)){
                        if(random.nextInt(100) < PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).getMoreMagnets_level()){
                            p.sendMessage("§d§l+6 осколков");
                            PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).setShards((int) (PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).getShards() + (6 * Booster.getMultiplier(p.getName(), BoosterType.SHARDS))));
                        } else {
                            p.sendMessage("§d§l+2 осколка");
                            PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).setShards((int) (PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).getShards() + (2 * Booster.getMultiplier(p.getName(), BoosterType.SHARDS))));
                        }
                    } else {
                        if(random.nextInt(100) < PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).getMoreMagnets_level()){
                            p.sendMessage("§d§l+3 осколка");
                            PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).setShards((int) (PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).getShards() + (3 * Booster.getMultiplier(p.getName(), BoosterType.SHARDS))));
                        } else {
                            p.sendMessage("§d§l+1 осколок");
                            PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).setShards((int) (PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).getShards() + (1 * Booster.getMultiplier(p.getName(), BoosterType.SHARDS))));
                        }
                    }
                    PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(entityLiving.getId());
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetPlayOutEntityDestroy);
                    this.cancel();
                }
                if (onground) {
                    Block block = entityLiving.getBukkitEntity().getLocation().clone().add(0, 3, 0).getBlock();
                    if (block.getType() != Material.AIR) {
                        PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(entityLiving.getId());
                        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetPlayOutEntityDestroy);
                        this.cancel();
                    }
                }
            }
        }.runTaskTimer(Main.instance, 0, 1);
    }
}
