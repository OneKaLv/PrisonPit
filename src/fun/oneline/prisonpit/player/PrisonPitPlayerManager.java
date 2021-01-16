package fun.oneline.prisonpit.player;

import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.boosters.Booster;
import fun.oneline.prisonpit.boosters.BoosterType;
import fun.oneline.prisonpit.database.MySQL;
import fun.oneline.prisonpit.handlers.onBrickASClick;
import fun.oneline.prisonpit.handlers.onGoldBlockASClick;
import fun.oneline.prisonpit.items.Menu;
import fun.oneline.prisonpit.shaft.ShaftUpdaterForSinglePlayer;
import fun.oneline.prisonpit.storm.ShardArmorStand;
import fun.oneline.prisonpit.tokens.TokenManager;
import fun.oneline.prisonpit.utils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityLiving;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PrisonPitPlayerManager {
    public static HashMap<String, PrisonPitPlayer> prisonPitPlayerHashMap = new HashMap<>();
    public static List<String> onStorm = new ArrayList();

    public static PrisonPitPlayer getPrisonPitPlayer(String name) {
        if (prisonPitPlayerHashMap.containsKey(name)) {
            return prisonPitPlayerHashMap.get(name);
        }
        return null;
    }

    public static void LoadPrisonPitPlayer(Player player) {
        String name = player.getName();
        if (!MySQL.playerExists(MySQL.prisonpit_players, name)) {
            MySQL.insert(name);
        }
        double money = (double) MySQL.select(MySQL.prisonpit_players, name, "money");
        double moneypersec = (double) MySQL.select(MySQL.prisonpit_players, name, "moneypersec");
        int timetostorm = (int) MySQL.select(MySQL.prisonpit_players, name, "timetostorm");
        int blocks = (int) MySQL.select(MySQL.prisonpit_players, name, "blocks");
        int shards = (int) MySQL.select(MySQL.prisonpit_players, name, "shards");
        int points = (int) MySQL.select(MySQL.prisonpit_players, name, "points");
        int tokens = (int) MySQL.select(MySQL.prisonpit_players, name, "tokens");
        double goldblocks = (double) MySQL.select(MySQL.prisonpit_players, name, "goldblocks");
        double nonspendmoney = (double) MySQL.select(MySQL.prisonpit_players, name, "nonspendmoney");

        int pickaxe_level = (int) MySQL.select(MySQL.prisonpit_players, name, "pickaxe_level");
        int boosterMoney_level = (int) MySQL.select(MySQL.prisonpit_players, name, "boosterMoney_level");
        int shardsFromBlock_level = (int) MySQL.select(MySQL.prisonpit_players, name, "shardsFromBlock_level");
        int doubleBlock_level = (int) MySQL.select(MySQL.prisonpit_players, name, "doubleBlock_level");
        int boosterMoneyII_level = (int) MySQL.select(MySQL.prisonpit_players, name, "boosterMoneyII_level");
        int storm_level = (int) MySQL.select(MySQL.prisonpit_players, name, "storm_level");

        int levelBoost_level = (int) MySQL.select(MySQL.prisonpit_players, name, "levelBoost_level");
        int boostershards_level = (int) MySQL.select(MySQL.prisonpit_players, name, "boostershards_level");
        int boostergoldblocks_level = (int) MySQL.select(MySQL.prisonpit_players, name, "boostergoldblocks_level");
        int boosterprocentgoldblocks_level = (int) MySQL.select(MySQL.prisonpit_players, name, "boosterprocentgoldblocks_level");
        int morestorms_level = (int) MySQL.select(MySQL.prisonpit_players, name, "morestorms_level");
        int token_level = (int) MySQL.select(MySQL.prisonpit_players, name, "token_level");

        int star1 = (int) MySQL.select(MySQL.prisonpit_players, name, "star1");
        int star2 = (int) MySQL.select(MySQL.prisonpit_players, name, "star2");
        int star3 = (int) MySQL.select(MySQL.prisonpit_players, name, "star3");
        int star4 = (int) MySQL.select(MySQL.prisonpit_players, name, "star4");
        int star5 = (int) MySQL.select(MySQL.prisonpit_players, name, "star5");
        int star6 = (int) MySQL.select(MySQL.prisonpit_players, name, "star6");
        int star7 = (int) MySQL.select(MySQL.prisonpit_players, name, "star7");
        int star8 = (int) MySQL.select(MySQL.prisonpit_players, name, "star8");
        int star9 = (int) MySQL.select(MySQL.prisonpit_players, name, "star9");
        int star10 = (int) MySQL.select(MySQL.prisonpit_players, name, "star10");

        int[] stars = new int[]{star1, star2, star3, star4, star5, star6, star7, star8, star9, star10};

        String activeBonus = MySQL.select(MySQL.prisonpit_players, name, "activeBonus").toString();
        int timeToEndBonus = (int) MySQL.select(MySQL.prisonpit_players, name, "timeToEndBonus");

        int maxBoosterMoney_level = (int) MySQL.select(MySQL.prisonpit_players, name, "maxBoosterMoney_level");

        int totalStars = (int) MySQL.select(MySQL.prisonpit_players, name, "totalStars");

        int goldblockroom_level = (int) MySQL.select(MySQL.prisonpit_players, name, "goldblockroom_level");
        int goldblockroom_points = (int) MySQL.select(MySQL.prisonpit_players, name, "goldblockroom_points");

        double fragments = (double) MySQL.select(MySQL.prisonpit_players, name, "fragments");

        double bricks = (double) MySQL.select(MySQL.prisonpit_players, name, "bricks");
        double brickspersec = (double) MySQL.select(MySQL.prisonpit_players, name, "brickspersec");
        int bricklevel = (int) MySQL.select(MySQL.prisonpit_players, name, "bricklevel");

        int moreFragments_level = (int) MySQL.select(MySQL.prisonpit_players, name, "moreFragments_level");
        int moreMagnets_level = (int) MySQL.select(MySQL.prisonpit_players, name, "moreMagnets_level");
        int GBDiscound_level = (int) MySQL.select(MySQL.prisonpit_players, name, "GBDiscound_level");
        int moreMagnetsII_level = (int) MySQL.select(MySQL.prisonpit_players, name, "moreMagnetsII_level");
        int moreMoney_level = (int) MySQL.select(MySQL.prisonpit_players, name, "moreMoney_level");
        int moreMagnetsIII_level = (int) MySQL.select(MySQL.prisonpit_players, name, "moreMagnetsIII_level");
        int moreBricks_level = (int) MySQL.select(MySQL.prisonpit_players, name, "moreBricks_level");
        int stageq = (int) MySQL.select(MySQL.prisonpit_players, name, "stageq");

        prisonPitPlayerHashMap.put(player.getName(), new PrisonPitPlayer(player, money, moneypersec, timetostorm, blocks, shards, points, tokens, goldblocks, nonspendmoney, pickaxe_level, boosterMoney_level, shardsFromBlock_level, doubleBlock_level, boosterMoneyII_level, storm_level, levelBoost_level, boostershards_level, boostergoldblocks_level, boosterprocentgoldblocks_level, morestorms_level, token_level, stars, activeBonus, timeToEndBonus, maxBoosterMoney_level, totalStars, goldblockroom_level, goldblockroom_points, fragments, bricks, brickspersec, bricklevel, moreFragments_level, moreMagnets_level, GBDiscound_level, moreMagnetsII_level, moreMoney_level, moreMagnetsIII_level, moreBricks_level, stageq));
    }

    public static void save(Player player) {
        String name = player.getName();
        PrisonPitPlayer prisonPitPlayer = getPrisonPitPlayer(name);
        MySQL.update(MySQL.prisonpit_players, name, "money", prisonPitPlayer.getMoney());
        MySQL.update(MySQL.prisonpit_players, name, "moneypersec", prisonPitPlayer.getMoneypersec());
        MySQL.update(MySQL.prisonpit_players, name, "timetostorm", prisonPitPlayer.getTimetostorm());
        MySQL.update(MySQL.prisonpit_players, name, "blocks", prisonPitPlayer.getBlocks());
        MySQL.update(MySQL.prisonpit_players, name, "shards", prisonPitPlayer.getShards());
        MySQL.update(MySQL.prisonpit_players, name, "points", prisonPitPlayer.getPoints());
        MySQL.update(MySQL.prisonpit_players, name, "tokens", prisonPitPlayer.getTokens());
        MySQL.update(MySQL.prisonpit_players, name, "goldblocks", prisonPitPlayer.getGoldblocks());
        MySQL.update(MySQL.prisonpit_players, name, "nonspendmoney", prisonPitPlayer.getNonspendmoney());
        MySQL.update(MySQL.prisonpit_players, name, "pickaxe_level", prisonPitPlayer.getPickaxe_level());
        MySQL.update(MySQL.prisonpit_players, name, "boosterMoney_level", prisonPitPlayer.getBoosterMoney_level());
        MySQL.update(MySQL.prisonpit_players, name, "shardsFromBlock_level", prisonPitPlayer.getShardsFromBlock_level());
        MySQL.update(MySQL.prisonpit_players, name, "doubleBlock_level", prisonPitPlayer.getDoubleBlock_level());
        MySQL.update(MySQL.prisonpit_players, name, "boosterMoneyII_level", prisonPitPlayer.getBoosterMoneyII_level());
        MySQL.update(MySQL.prisonpit_players, name, "storm_level", prisonPitPlayer.getStorm_level());
        MySQL.update(MySQL.prisonpit_players, name, "levelBoost_level", prisonPitPlayer.getLevelBoost_level());
        MySQL.update(MySQL.prisonpit_players, name, "boostershards_level", prisonPitPlayer.getBoostershards_level());
        MySQL.update(MySQL.prisonpit_players, name, "boostergoldblocks_level", prisonPitPlayer.getBoostergoldblocks_level());
        MySQL.update(MySQL.prisonpit_players, name, "boosterprocentgoldblocks_level", prisonPitPlayer.getBoosterprocentgoldblocks_level());
        MySQL.update(MySQL.prisonpit_players, name, "morestorms_level", prisonPitPlayer.getMorestorms_level());
        MySQL.update(MySQL.prisonpit_players, name, "token_level", prisonPitPlayer.getToken_level());
        MySQL.update(MySQL.prisonpit_players, name, "star1", prisonPitPlayer.getStars()[0]);
        MySQL.update(MySQL.prisonpit_players, name, "star2", prisonPitPlayer.getStars()[1]);
        MySQL.update(MySQL.prisonpit_players, name, "star3", prisonPitPlayer.getStars()[2]);
        MySQL.update(MySQL.prisonpit_players, name, "star4", prisonPitPlayer.getStars()[3]);
        MySQL.update(MySQL.prisonpit_players, name, "star5", prisonPitPlayer.getStars()[4]);
        MySQL.update(MySQL.prisonpit_players, name, "star6", prisonPitPlayer.getStars()[5]);
        MySQL.update(MySQL.prisonpit_players, name, "star7", prisonPitPlayer.getStars()[6]);
        MySQL.update(MySQL.prisonpit_players, name, "star8", prisonPitPlayer.getStars()[7]);
        MySQL.update(MySQL.prisonpit_players, name, "star9", prisonPitPlayer.getStars()[8]);
        MySQL.update(MySQL.prisonpit_players, name, "star10", prisonPitPlayer.getStars()[9]);
        MySQL.update(MySQL.prisonpit_players, name, "activeBonus", prisonPitPlayer.getActiveBonus());
        MySQL.update(MySQL.prisonpit_players, name, "timeToEndBonus", prisonPitPlayer.getTimeToEndBonus());
        MySQL.update(MySQL.prisonpit_players, name, "maxBoosterMoney_level", prisonPitPlayer.getMaxBoosterMoney_level());
        MySQL.update(MySQL.prisonpit_players, name, "goldblockroom_level", prisonPitPlayer.getGoldblockroom_level());
        MySQL.update(MySQL.prisonpit_players, name, "goldblockroom_points", prisonPitPlayer.getGoldblockroom_points());
        MySQL.update(MySQL.prisonpit_players, name, "fragments", prisonPitPlayer.getFragments());
        MySQL.update(MySQL.prisonpit_players, name, "bricks", prisonPitPlayer.getBricks());
        MySQL.update(MySQL.prisonpit_players, name, "brickspersec", prisonPitPlayer.getBrickspersec());
        MySQL.update(MySQL.prisonpit_players, name, "bricklevel", prisonPitPlayer.getBricklevel());
        MySQL.update(MySQL.prisonpit_players, name, "moreFragments_level", prisonPitPlayer.getMoreFragments_level());
        MySQL.update(MySQL.prisonpit_players, name, "moreMagnets_level", prisonPitPlayer.getMoreMagnets_level());
        MySQL.update(MySQL.prisonpit_players, name, "GBDiscound_level", prisonPitPlayer.getGBDiscound_level());
        MySQL.update(MySQL.prisonpit_players, name, "moreMagnetsII_level", prisonPitPlayer.getMoreMagnetsII_level());
        MySQL.update(MySQL.prisonpit_players, name, "moreMoney_level", prisonPitPlayer.getMoreMoney_level());
        MySQL.update(MySQL.prisonpit_players, name, "moreMagnetsIII_level", prisonPitPlayer.getMoreMagnetsIII_level());
        MySQL.update(MySQL.prisonpit_players, name, "moreBricks_level", prisonPitPlayer.getMoreBricks_level());
        int[] stars = prisonPitPlayer.getStars();
        int minstar = 0;
        for (int j = 0; j < 10; j++) {
            if (PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getStars()[j] > 0) {
                minstar++;
            }
        }
        if (minstar >= 10) {
            minstar = stars[0];
            for (int j : stars) {
                if (j < minstar) {
                    minstar = j;
                }
            }
        }
        MySQL.update(MySQL.prisonpit_players, name, "totalStars", minstar);
        MySQL.update(MySQL.prisonpit_players, name, "stageq", prisonPitPlayer.getStageq());
    }

    public static void unLoadPrisonPitPlayer(Player player) {
        String name = player.getName();
        save(player);
        prisonPitPlayerHashMap.remove(name);
    }

    public static void paidMoneyEverySec() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (String playername : prisonPitPlayerHashMap.keySet()) {
                    PrisonPitPlayer prisonPitPlayer = getPrisonPitPlayer(playername);
                    Random random = new Random();
                    if (TokenManager.moreMoney.contains(Bukkit.getServer().getPlayer(playername))) {
                        if (random.nextInt(100) < prisonPitPlayer.getMoreMoney_level()) {
                            prisonPitPlayer.setMoney(prisonPitPlayer.getMoney() + (prisonPitPlayer.getMoneypersec() * 100 * 2) * Booster.getMultiplier(playername, BoosterType.MONEY) * Utils.getBoosterMoneyFromPriv(prisonPitPlayer.getNumpriv()));
                            prisonPitPlayer.setNonspendmoney(prisonPitPlayer.getNonspendmoney() + (prisonPitPlayer.getMoneypersec() * 100 * 2) * Booster.getMultiplier(playername, BoosterType.MONEY) * Utils.getBoosterMoneyFromPriv(prisonPitPlayer.getNumpriv()));
                            Bukkit.getServer().getPlayer(playername).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+§c" + Utils.getMoney((prisonPitPlayer.getMoneypersec() * 100) * 2 * Booster.getMultiplier(playername, BoosterType.MONEY) * Utils.getBoosterMoneyFromPriv(prisonPitPlayer.getNumpriv())) + "$"));
                        } else {
                            prisonPitPlayer.setMoney(prisonPitPlayer.getMoney() + (prisonPitPlayer.getMoneypersec() * 100) * Booster.getMultiplier(playername, BoosterType.MONEY) * Utils.getBoosterMoneyFromPriv(prisonPitPlayer.getNumpriv()));
                            prisonPitPlayer.setNonspendmoney(prisonPitPlayer.getNonspendmoney() + (prisonPitPlayer.getMoneypersec() * 100) * Booster.getMultiplier(playername, BoosterType.MONEY) * Utils.getBoosterMoneyFromPriv(prisonPitPlayer.getNumpriv()));
                            Bukkit.getServer().getPlayer(playername).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+§c" + Utils.getMoney(prisonPitPlayer.getMoneypersec() * 100 * Booster.getMultiplier(playername, BoosterType.MONEY) * Utils.getBoosterMoneyFromPriv(prisonPitPlayer.getNumpriv())) + "$"));
                        }
                    } else {
                        if (random.nextInt(100) < prisonPitPlayer.getMoreMoney_level()) {
                            prisonPitPlayer.setMoney(prisonPitPlayer.getMoney() + (prisonPitPlayer.getMoneypersec() * 2) * Booster.getMultiplier(playername, BoosterType.MONEY) * Utils.getBoosterMoneyFromPriv(prisonPitPlayer.getNumpriv()));
                            prisonPitPlayer.setNonspendmoney(prisonPitPlayer.getNonspendmoney() + (prisonPitPlayer.getMoneypersec() * 2) * Booster.getMultiplier(playername, BoosterType.MONEY) * Utils.getBoosterMoneyFromPriv(prisonPitPlayer.getNumpriv()));
                            Bukkit.getServer().getPlayer(playername).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+§a" + Utils.getMoney(prisonPitPlayer.getMoneypersec() * 2 * Booster.getMultiplier(playername, BoosterType.MONEY) * Utils.getBoosterMoneyFromPriv(prisonPitPlayer.getNumpriv())) + "$"));
                        } else {
                            prisonPitPlayer.setMoney(prisonPitPlayer.getMoney() + prisonPitPlayer.getMoneypersec() * Booster.getMultiplier(playername, BoosterType.MONEY) * Utils.getBoosterMoneyFromPriv(prisonPitPlayer.getNumpriv()));
                            prisonPitPlayer.setNonspendmoney(prisonPitPlayer.getNonspendmoney() + prisonPitPlayer.getMoneypersec() * Booster.getMultiplier(playername, BoosterType.MONEY) * Utils.getBoosterMoneyFromPriv(prisonPitPlayer.getNumpriv()));
                            Bukkit.getServer().getPlayer(playername).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+§a" + Utils.getMoney(prisonPitPlayer.getMoneypersec() * Booster.getMultiplier(playername, BoosterType.MONEY) * Utils.getBoosterMoneyFromPriv(prisonPitPlayer.getNumpriv())) + "$"));
                        }
                    }
                }
            }
        }.runTaskTimer(Main.instance, 20, 20);
    }

    public static void paidShardsFromBoosterShards() {
        Random random = new Random();
        for (Player player : Bukkit.getOnlinePlayers()) {
            PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
            if (random.nextInt(1000) < 2 * prisonPitPlayer.getBoostershards_level()) {
                if (TokenManager.moreShards.contains(player)) {
                    prisonPitPlayer.setShards((int) (prisonPitPlayer.getShards() + (2 * Booster.getMultiplier(player.getName(), BoosterType.SHARDS))));
                    player.sendMessage("§d§l+2 осколка");
                } else {
                    prisonPitPlayer.setShards((int) (prisonPitPlayer.getShards() + (1 * Booster.getMultiplier(player.getName(), BoosterType.SHARDS))));
                    player.sendMessage("§d§l+1 осколок");
                }
            }
        }
    }

    public static void updateStormBossbar() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (String playername : prisonPitPlayerHashMap.keySet()) {
                    PrisonPitPlayer prisonPitPlayer = getPrisonPitPlayer(playername);
                    if (prisonPitPlayer.getTimetostorm() < 1) {
                        prisonPitPlayer.getStormbossbar().setVisible(false);
                        startStorm(Bukkit.getServer().getPlayer(playername));
                    } else {
                        if (!Menu.stormDisable.contains(playername)) {
                            prisonPitPlayer.setTimetostorm(prisonPitPlayer.getTimetostorm() - 1);
                        }
                    }
                    prisonPitPlayer.getStormbossbar().setTitle(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "До шторма " + String.format("%d:%02d", prisonPitPlayer.getTimetostorm() / 60, prisonPitPlayer.getTimetostorm() % 60));
                    prisonPitPlayer.getStormbossbar().addPlayer(prisonPitPlayer.getPlayer());
                    prisonPitPlayer.getStormbossbar().setProgress(Math.max(Math.min(1.0 - ((double) prisonPitPlayer.getTimetostorm() / (360.0 - (prisonPitPlayer.getMorestorms_level() * 6.0))), 1.0), 0.0));
                }
            }
        }.runTaskTimer(Main.instance, 20, 20);
    }

    public static void startStorm(Player player) {
        if (!(onStorm.contains(player.getName()))) {
            onStorm.add(player.getName());
            Location loc = player.getLocation();
            player.teleport(new Location(Bukkit.getWorlds().get(0), 500.5, 50, 500.5, 180, 0));
            player.sendTitle(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Шторм!", ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Собирай осколки падающие с неба!", 30, 90, 30);
            player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 1, 1);
            new BukkitRunnable() {
                int i = 0;

                @Override
                public void run() {
                    if (Bukkit.getServer().getPlayer(player.getName()) != null && player.isOnline()) {
                        if (i <= 20 + PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getStorm_level()) {
                            ShardArmorStand shardArmorStand = new ShardArmorStand();
                            shardArmorStand.spawn(player.getLocation(), player);
                            i++;
                        } else {
                            new BukkitRunnable() {
                                Player finalPlayer = player;

                                @Override
                                public void run() {
                                    if (finalPlayer != null && finalPlayer.isOnline()) {
                                        getPrisonPitPlayer(player.getName()).getStormbossbar().setVisible(true);
                                        getPrisonPitPlayer(player.getName()).setTimetostorm(360 - (getPrisonPitPlayer(player.getName()).getMorestorms_level() * 6));
                                        onStorm.remove(player.getName());
                                        if (Bukkit.getWorlds().get(0).getBlockAt(loc).getType() != Material.AIR) {
                                            Location safeLoc = loc;
                                            safeLoc.setY(50.5);
                                            player.teleport(safeLoc);
                                        } else {
                                            player.teleport(loc);
                                        }
                                        new BukkitRunnable() {
                                            @Override
                                            public void run() {
                                                ShaftUpdaterForSinglePlayer.update(player);
                                                restorePacketsAS(player);
                                            }
                                        }.runTaskLater(Main.instance, 6);
                                    }
                                }
                            }.runTaskLater(Main.instance, 200);
                            this.cancel();
                        }
                    }
                }
            }.runTaskTimer(Main.instance, 30, 30);
        }
    }

    public static void restorePacketsAS(Player player) {
        if (onBrickASClick.balancePerSecEntitiesHashMap.containsKey(player)) {
            EntityLiving stand = onBrickASClick.balancePerSecEntitiesHashMap.get(player);
            PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(stand.getId());
            PacketPlayOutSpawnEntityLiving spawnP = new PacketPlayOutSpawnEntityLiving(stand);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(destroy);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(spawnP);
        }
        if (onGoldBlockASClick.v2EntitiesHashMap.containsKey(player)) {
            EntityLiving stand = onGoldBlockASClick.v2EntitiesHashMap.get(player);
            PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(stand.getId());
            PacketPlayOutSpawnEntityLiving spawnP = new PacketPlayOutSpawnEntityLiving(stand);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(destroy);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(spawnP);
        }
        if (onGoldBlockASClick.levelEntitiesHashMap.containsKey(player)) {
            EntityLiving stand = onGoldBlockASClick.levelEntitiesHashMap.get(player);
            PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(stand.getId());
            PacketPlayOutSpawnEntityLiving spawnP = new PacketPlayOutSpawnEntityLiving(stand);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(destroy);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(spawnP);
        }
    }
}
