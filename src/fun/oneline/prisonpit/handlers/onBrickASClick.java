package fun.oneline.prisonpit.handlers;

import fun.oneline.api.inventory.ClickAction;
import fun.oneline.api.inventory.CustomInventory;
import fun.oneline.api.inventory.item.CustomItem;
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

public class onBrickASClick implements Listener {

    public static List<ArmorStand> armorStandList = new ArrayList<>();
    public static HashMap<Player, EntityLiving> balanceEntitiesHashMap = new HashMap<>();
    public static HashMap<Player, EntityLiving> balancePerSecEntitiesHashMap = new HashMap<>();

    private static int stage = 0;

    public onBrickASClick(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public static void summonBAS(Location location) {
        ArmorStand as = location.getWorld().spawn(location, ArmorStand.class);
        as.setGravity(false);
        as.setVisible(false);
        as.setInvulnerable(true);
        as.setCanPickupItems(false);
        as.setHelmet(new ItemStack(Material.BRICK));
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
                stage = i;
            }
        }.runTaskTimer(Main.instance, 1, 1);
        summonBAStext(location);
        armorStandList.add(as);
    }

    public static void sendbrickspersec(Player player){
        PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
        Location loc = new Location(Bukkit.getWorlds().get(0), 143.5, 55.9 - (stage > 0?((50-stage)/100.0):((50+stage)/100.0)), -126.5);
        WorldServer s = ((CraftWorld) loc.getWorld()).getHandle();
        EntityArmorStand stand = new EntityArmorStand(s);
        stand.setLocation(143.5, 55.9 - (stage > 0?(50-stage)/100.0:(50+stage)/100.0), -126.5, 0, 0);
        stand.setCustomNameVisible(true);
        int blockslevel = prisonPitPlayer.getBoosterMoney_level();
        int rank = 0;
        while (blockslevel >= 102) {
            rank++;
            blockslevel -= 6;
        }
        stand.setCustomName("§6§l+" + Utils.getMoney(prisonPitPlayer.getBrickspersec() * (prisonPitPlayer.getBricklevel()==0?1:Math.pow(2,prisonPitPlayer.getBricklevel())) * (prisonPitPlayer.getTotalStars()==15?1:Math.pow(4,prisonPitPlayer.getTotalStars()-15)) * (prisonPitPlayer.getMoreBricks_level()==0?1:Math.pow(1.25,prisonPitPlayer.getMoreBricks_level())) * (prisonPitPlayer.getBoosterMoney_level()>=102?(4 * (Math.pow(8, rank - 1) == 0 ? 1 : Math.pow(8, rank - 1))):1)));
        stand.setBasePlate(true);
        stand.setNoGravity(true);
        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setMarker(true);
        PacketPlayOutSpawnEntityLiving spawnP = new PacketPlayOutSpawnEntityLiving(stand);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(spawnP);
        new BukkitRunnable() {
            int i = stage;
            @Override
            public void run() {
                int blockslevel = prisonPitPlayer.getBoosterMoney_level();
                int rank = 0;
                while (blockslevel >= 102) {
                    rank++;
                    blockslevel -= 6;
                }
                if (!(stand.getCustomName().equals("§6§l+" + Utils.getMoney(prisonPitPlayer.getBrickspersec() * (prisonPitPlayer.getBricklevel()==0?1:Math.pow(2,prisonPitPlayer.getBricklevel())) * (prisonPitPlayer.getTotalStars()==15?1:Math.pow(4,prisonPitPlayer.getTotalStars()-15)) * (prisonPitPlayer.getMoreBricks_level()==0?1:Math.pow(1.25,prisonPitPlayer.getMoreBricks_level())) * (prisonPitPlayer.getBoosterMoney_level()>=102?(4 * (Math.pow(8, rank - 1) == 0 ? 1 : Math.pow(8, rank - 1))):1))))){
                    stand.setCustomName("§6§l+" + Utils.getMoney(prisonPitPlayer.getBrickspersec() * (prisonPitPlayer.getBricklevel()==0?1:Math.pow(2,prisonPitPlayer.getBricklevel())) * (prisonPitPlayer.getTotalStars()==15?1:Math.pow(4,prisonPitPlayer.getTotalStars()-15)) * (prisonPitPlayer.getMoreBricks_level()==0?1:Math.pow(1.25,prisonPitPlayer.getMoreBricks_level())) * (prisonPitPlayer.getBoosterMoney_level()>=102?(4 * (Math.pow(8, rank - 1) == 0 ? 1 : Math.pow(8, rank - 1))):1)));
                    PacketPlayOutSpawnEntityLiving spawnP = new PacketPlayOutSpawnEntityLiving(stand);
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(spawnP);
                }
                if (i < 0) {
                    Location loc = stand.getBukkitEntity().getLocation();
                    loc.setY(loc.getY() - 0.01D);
                    stand.setLocation(loc.getX(),loc.getY(),loc.getZ(),loc.getYaw(),loc.getPitch());
                    PacketPlayOutEntityTeleport packetPlayOutEntityTeleport = new PacketPlayOutEntityTeleport(stand);
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutEntityTeleport);
                    i++;
                } else {
                    if (i >= 50) {
                        i = -50;
                        return;
                    } else {
                        i++;
                    }
                    Location loc = stand.getBukkitEntity().getLocation();
                    loc.setY(loc.getY() + 0.01D);
                    stand.setLocation(loc.getX(),loc.getY(),loc.getZ(),loc.getYaw(),loc.getPitch());
                    PacketPlayOutEntityTeleport packetPlayOutEntityTeleport = new PacketPlayOutEntityTeleport(stand);
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutEntityTeleport);
                }
            }
        }.runTaskTimer(Main.instance, 1, 1);
        balancePerSecEntitiesHashMap.put(player, stand);
    }

    public static void sendbricksbalance(Player player){
        PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
        Location loc = new Location(Bukkit.getWorlds().get(0), 143.5, 55.25, -126.5);
        WorldServer s = ((CraftWorld) loc.getWorld()).getHandle();
        EntityArmorStand stand = new EntityArmorStand(s);
        stand.setLocation(143.5, 56.3, -126.5, 0, 0);
        stand.setCustomNameVisible(true);
        stand.setCustomName("§6§lКирпичей - " + Utils.getMoney(prisonPitPlayer.getBricks()));
        stand.setBasePlate(true);
        stand.setNoGravity(true);
        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setMarker(true);
        new BukkitRunnable(){
            @Override
            public void run() {
                stand.setCustomName("§6§lКирпичей - " + Utils.getMoney(prisonPitPlayer.getBricks()));
                PacketPlayOutSpawnEntityLiving spawnP = new PacketPlayOutSpawnEntityLiving(stand);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(spawnP);
            }
        }.runTaskTimer(Main.instance,20,20);
        balanceEntitiesHashMap.put(player, stand);
    }

    public static void summonBAStext(Location location){
        location.setY(location.getY()+2.5);
        ArmorStand as = location.getWorld().spawn(location, ArmorStand.class);
        as.setCustomNameVisible(true);
        as.setCustomName("§e§lДвор кирпичей");
        as.setGravity(false);
        as.setVisible(false);
        as.setInvulnerable(true);
        as.setCanPickupItems(false);
        as.setMarker(true);
        armorStandList.add(as);
    }

    public static void paidBricks(){
        new BukkitRunnable(){
            @Override
            public void run() {
                for(Player player : Bukkit.getServer().getOnlinePlayers()){
                    PrisonPitPlayer pitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
                    if(pitPlayer.getTotalStars() >= 15) {
                        int blockslevel = pitPlayer.getBoosterMoney_level();
                        int rank = 0;
                        while (blockslevel >= 102) {
                            rank++;
                            blockslevel -= 6;
                        }
                        pitPlayer.setBricks(pitPlayer.getBricks() + (pitPlayer.getBrickspersec() * (pitPlayer.getBricklevel()==0?1:Math.pow(2,pitPlayer.getBricklevel())) * (pitPlayer.getTotalStars()==15?1:Math.pow(4,pitPlayer.getTotalStars()-15)) * (pitPlayer.getMoreBricks_level()==0?1:Math.pow(1.25,pitPlayer.getMoreBricks_level())) * (pitPlayer.getBoosterMoney_level()>=102?(4 * (Math.pow(8, rank - 1) == 0 ? 1 : Math.pow(8, rank - 1))):1)));
                    }
                }
            }
        }.runTaskTimer(Main.instance,20,20);
    }

    public static void removeBAS() {
        if (!armorStandList.isEmpty()) {
            for (ArmorStand as : armorStandList) {
                as.remove();
            }
        }
    }

    public static void openBAS(Player player) {
        PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
        CustomInventory customInventory = new CustomInventory("§e§lДвор кирпичей");
        List<String> lore = new ArrayList<>();
        for (int i = 0; i < 45; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }
        lore.add("");
        lore.add("§7Кирпичи - новая валюта, которую можно тратить");
        lore.add("§7на улучшения в соответствующей вкладке.");
        lore.add("§7С каждым новым уровнем 'Двора кирпичей'");
        lore.add("§7вы будете получать в 2 раза больше кирпичей");
        lore.add("§7в секунду. Так же новая звезда будет давать вам");
        lore.add("§7в 4 раза больше кирпичей, начиная от 15 звезды.");
        customInventory.addItem(4, new CustomItem(Material.PAPER).setName("§f§lИнформация").setLore(lore).build());
        customInventory.addMenuClickHandler(4, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                return false;
            }
        });
        lore.clear();
        customInventory.addItem(11, new CustomItem(Material.EXP_BOTTLE).setName("§b§lВаш текущий уровень - " + player.getLevel()).setLore(lore).build());
        customInventory.addMenuClickHandler(11, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                return false;
            }
        });
        customInventory.addItem(15, new CustomItem(Material.EXP_BOTTLE).setName("§b§lНеобходимый уровень - " + (prisonPitPlayer.getBricklevel() + 4)).setLore(lore).build());
        customInventory.addMenuClickHandler(15, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                return false;
            }
        });
        customInventory.addItem(31, new CustomItem(Material.BRICK).setName("§a§lУлучшить").setLore(lore).build());
        customInventory.addMenuClickHandler(31, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                if(player.getLevel() >= prisonPitPlayer.getBricklevel()+4){
                    prisonPitPlayer.setPoints(0);
                    prisonPitPlayer.setBricklevel(prisonPitPlayer.getBricklevel()+1);
                    player.setLevel(0);
                    player.setExp(0);
                    player.closeInventory();
                    player.sendTitle("§e§lДвор кирпичей улучшен!", "" , 30 ,90 , 30);
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 1, 2);
                }
                return false;
            }
        });
        customInventory.open(player);
    }

    @EventHandler
    public void onArmorStandClick(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof ArmorStand) {
            if (((ArmorStand) event.getRightClicked()).getHelmet().equals(new ItemStack(Material.BRICK))) {
                Player player = event.getPlayer();
                PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
                if (prisonPitPlayer.getTotalStars() >= 15) {
                    openBAS(player);
                } else {
                    player.sendMessage("§7[§e!§7]§e Откроется с 15 звёзд!");
                }
                event.setCancelled(true);
            }
        }
    }
}
