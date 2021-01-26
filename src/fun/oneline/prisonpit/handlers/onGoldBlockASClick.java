package fun.oneline.prisonpit.handlers;

import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.player.PrisonPitPlayer;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import fun.oneline.prisonpit.utils.Utils;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class onGoldBlockASClick implements Listener {

    public static HashMap<Player, EntityLiving> levelEntitiesHashMap = new HashMap<>();
    public static List<ArmorStand> armorStandList = new ArrayList<>();
    public static HashMap<Player, EntityLiving> v2EntitiesHashMap = new HashMap<>();

    public onGoldBlockASClick(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public static void summonGBAS(Location location) {
        ArmorStand as = location.getWorld().spawn(location, ArmorStand.class);
        as.setCustomName("§eПКМ чтобы улучшить");
        as.setCustomNameVisible(true);
        as.setGravity(false);
        as.setVisible(false);
        as.setInvulnerable(true);
        as.setCanPickupItems(false);
        as.setHelmet(new ItemStack(Material.GOLD_BLOCK));

        new BukkitRunnable() {
            int i = -50;

            @Override
            public void run() {
                if (i < 0) {
                    Location loc = as.getLocation();
                    loc.setY(loc.getY() - 0.01D);
                    as.setHeadPose(as.getHeadPose().add(0, 0.01, 0));
                    as.teleport(loc);
                    i++;
                } else {
                    if (i >= 50) {
                        i = -50;
                        return;
                    } else {
                        i++;
                    }
                    Location loc = as.getLocation();
                    loc.setY(loc.getY() + 0.01D);
                    as.setHeadPose(as.getHeadPose().add(0, 0.01, 0));
                    as.teleport(loc);
                }
            }
        }.runTaskTimer(Main.instance, 1, 1);
        armorStandList.add(as);
    }

    public static void sendV2(Player p){
        Location location = new Location(Bukkit.getWorlds().get(0), 120.5, 56.4, 3.5);
        WorldServer s = ((CraftWorld) location.getWorld()).getHandle();
        EntityArmorStand stand = new EntityArmorStand(s);
        stand.setLocation(location.getX(),location.getY(),location.getZ(),location.getYaw(),location.getPitch());
        stand.setCustomNameVisible(true);
        if(PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).getTotalStars()>= 10) {
            stand.setCustomName("§6§lДвор блоков V2");
        } else {
            stand.setCustomName("§6§lДвор блоков");
        }
        stand.setBasePlate(true);
        stand.setNoGravity(true);
        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setMarker(true);
        PacketPlayOutSpawnEntityLiving spawnP = new PacketPlayOutSpawnEntityLiving(stand);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(spawnP);
        v2EntitiesHashMap.put(p, stand);
    }

    public static void removeGBAS() {
        if (!armorStandList.isEmpty()) {
            for (ArmorStand as : armorStandList) {
                as.remove();
            }
        }
    }

    public static void sendCountArmorStand(Player p, String price) {

        Location loc = new Location(Bukkit.getWorlds().get(0), 120.5, 53.5, 3.5);
        WorldServer s = ((CraftWorld) loc.getWorld()).getHandle();
        EntityArmorStand stand = new EntityArmorStand(s);
        Random random = new Random();
        stand.setLocation(120 + random.nextDouble(), 53.5, 3 + random.nextDouble(), 0, 0);
        stand.setCustomNameVisible(true);
        stand.setCustomName("§c-" + price + "$");
        stand.setBasePlate(true);
        stand.setNoGravity(true);
        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setMarker(true);
        PacketPlayOutSpawnEntityLiving spawnP = new PacketPlayOutSpawnEntityLiving(stand);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(spawnP);
        new BukkitRunnable(){
            int i = 0;
            @Override
            public void run() {
                if (i < 35){
                    stand.setLocation(stand.getBukkitEntity().getLocation().getX(),stand.getBukkitEntity().getLocation().getY() + 0.01D,stand.getBukkitEntity().getLocation().getZ(),0,0);
                    PacketPlayOutEntityTeleport packetPlayOutEntityTeleport = new PacketPlayOutEntityTeleport(stand);
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetPlayOutEntityTeleport);
                    i++;
                } else {
                    PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(stand.getId());
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetPlayOutEntityDestroy);
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.instance,1,1);
    }

    public static void sendLevelArmorStand(Player p) {
        Location loc = new Location(Bukkit.getWorlds().get(0), 120.5, 56.2, 3.5);
        WorldServer s = ((CraftWorld) loc.getWorld()).getHandle();
        EntityArmorStand stand = new EntityArmorStand(s);
        stand.setLocation(120.5, 56.2, 3.5, 0, 0);
        stand.setCustomNameVisible(true);
        stand.setCustomName("§a" + PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).getGoldblockroom_level() + " Уровень");
        stand.setBasePlate(true);
        stand.setNoGravity(true);
        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setMarker(true);
        PacketPlayOutSpawnEntityLiving spawnP = new PacketPlayOutSpawnEntityLiving(stand);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(spawnP);
        levelEntitiesHashMap.put(p, stand);
    }

    @EventHandler
    public void onArmorStandClick(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof ArmorStand) {
            if (((ArmorStand) event.getRightClicked()).getHelmet().equals(new ItemStack(Material.GOLD_BLOCK))) {
                Player player = event.getPlayer();
                PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
                if (prisonPitPlayer.getTotalStars() >= 4) {
                    if (prisonPitPlayer.getMoney() >= (prisonPitPlayer.getGoldblockroom_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getGoldblockroom_level()) / (prisonPitPlayer.getGBDiscound_level()==0? 1: Math.pow(4,prisonPitPlayer.getGBDiscound_level())))) {
                        player.playSound(player.getLocation(), Sound.ENTITY_SLIME_JUMP, 1 , 2);
                        prisonPitPlayer.setMoney(prisonPitPlayer.getMoney() - (prisonPitPlayer.getGoldblockroom_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getGoldblockroom_level()) / (prisonPitPlayer.getGBDiscound_level()==0? 1: Math.pow(4,prisonPitPlayer.getGBDiscound_level()))));
                        prisonPitPlayer.setGoldblockroom_points(prisonPitPlayer.getGoldblockroom_points() + 1);
                        sendCountArmorStand(player, Utils.getMoney(prisonPitPlayer.getGoldblockroom_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getGoldblockroom_level()) / (prisonPitPlayer.getGBDiscound_level()==0? 1: Math.pow(4,prisonPitPlayer.getGBDiscound_level()))));
                        if (prisonPitPlayer.getGoldblockroom_points() > 9) {
                            prisonPitPlayer.setGoldblockroom_level(prisonPitPlayer.getGoldblockroom_level() + 1);
                            prisonPitPlayer.setGoldblockroom_points(0);
                            player.sendTitle("§e§lУровень двора повышен!", "§6" + prisonPitPlayer.getGoldblockroom_level() + " уровень ",20,60,20);
                            player.playSound(player.getLocation(), Sound.ENTITY_EGG_THROW, 1, 4);
                            if(levelEntitiesHashMap.containsKey(player)) {
                                PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(levelEntitiesHashMap.get(player).getId());
                                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutEntityDestroy);
                            }
                            sendLevelArmorStand(player);
                        }
                    } else {
                        event.getPlayer().sendMessage("§7[§c!§7]§e Недостаточно денег!");
                    }
                } else {
                    event.getPlayer().sendMessage("§7[§e!§7]§e Откроется с 4 звёзд!");
                }
                event.setCancelled(true);
            }
        }
    }
}
