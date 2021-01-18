package fun.oneline.prisonpit.handlers;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.boosters.Booster;
import fun.oneline.prisonpit.boosters.BoosterType;
import fun.oneline.prisonpit.clans.ClanManager;
import fun.oneline.prisonpit.player.PrisonPitPlayer;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import fun.oneline.prisonpit.tokens.TokenManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class onBreak implements Listener {

    public static RegionManager manager;

    public onBreak(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        manager = WGBukkit.getPlugin().getRegionManager(Bukkit.getWorlds().get(0));
    }


    public static void setPointsLevelBreak(PrisonPitPlayer prisonPitPlayer) {
        int points = prisonPitPlayer.getPoints();
        int rank = 0;
        while (points >= (800 - (prisonPitPlayer.getLevelBoost_level() * 25)) * (rank == 1 ? 1 : Math.pow(1.1, rank))) {
            points -= ((800 - (prisonPitPlayer.getLevelBoost_level() * 25)) * (rank == 1 ? 1 : Math.pow(1.1, rank)));
            rank++;
        }
        if (Bukkit.getServer().getPlayer(prisonPitPlayer.getName()).getLevel() != rank) {
            if (rank != 0) {
                if(TokenManager.moreShards.contains(Bukkit.getServer().getPlayer(prisonPitPlayer.getName()))){
                    Bukkit.getServer().getPlayer(prisonPitPlayer.getName()).sendTitle(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Уровень повышен!", ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Получено " + (int)((((5 + (5 * rank))*2)*(1.0 + prisonPitPlayer.getMoreMagnetsII_level()/50.0)* Booster.getMultiplier(prisonPitPlayer.getName(), BoosterType.SHARDS))) + " осколков!", 30, 90, 30);
                    prisonPitPlayer.setShards((int) (prisonPitPlayer.getShards() + ((5 + (5 * rank))*2)*(1.0 + prisonPitPlayer.getMoreMagnetsII_level()/50.0)* Booster.getMultiplier(prisonPitPlayer.getName(), BoosterType.SHARDS)));
                } else {
                    Bukkit.getServer().getPlayer(prisonPitPlayer.getName()).sendTitle(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Уровень повышен!", ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Получено " + (int)((5 + (5 * rank))*(1.0 + prisonPitPlayer.getMoreMagnetsII_level()/50.0)* Booster.getMultiplier(prisonPitPlayer.getName(), BoosterType.SHARDS)) + " осколков!", 30, 90, 30);
                    prisonPitPlayer.setShards((int) (prisonPitPlayer.getShards() + (5 + (5 * rank))*(1.0 + prisonPitPlayer.getMoreMagnetsII_level()/50.0) * Booster.getMultiplier(prisonPitPlayer.getName(), BoosterType.SHARDS)));
                }
                Bukkit.getServer().getPlayer(prisonPitPlayer.getName()).playSound(Bukkit.getServer().getPlayer(prisonPitPlayer.getName()).getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 2, 0);
            }
        }
        Bukkit.getServer().getPlayer(prisonPitPlayer.getName()).setLevel(rank);
        Bukkit.getServer().getPlayer(prisonPitPlayer.getName()).setExp((float) Math.max(0, Math.min(((float) points) / (rank == 1 ? (800 - (prisonPitPlayer.getLevelBoost_level() * 25)) : (800 - (prisonPitPlayer.getLevelBoost_level() * 25)) * Math.pow(1.1, rank)), 1)));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        ApplicableRegionSet set = manager.getApplicableRegions(event.getBlock().getLocation());
        if (set.queryState(null, new StateFlag[]{DefaultFlag.BLOCK_BREAK}) == StateFlag.State.DENY) {
            return;
        }
        Random random = new Random();
        Player player = event.getPlayer();
        event.setExpToDrop(0);
        event.setDropItems(false);
        if(TokenManager.autoBlocks.contains(player)){
            event.setCancelled(true);
            player.sendMessage("§7[§c!§7]§e У вас активен бонус авто-блоки! Вы не можете копать во время этого!");
            return;
        }
        PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
        int[] stars = prisonPitPlayer.getStars();
        int minstar = 0;
        for(int j = 0; j<10; j++){
            if(PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getStars()[j] > 0){
                minstar++;
            }
        }
        if(minstar >= 10){
            minstar = stars[0];
            for(int j : stars){
                if(j < minstar){
                    minstar = j;
                }
            }
        }
        double boosterStar = 1;
        for(int star : stars){
            if(star>0){
                boosterStar = boosterStar * star;
            }
        }
        if (prisonPitPlayer != null) {
            if(TokenManager.fastPickaxe.contains(player)){
                for(int j = 0; j<3;j++){
                    if(ClanManager.hasClan(player)){
                        ClanManager.getClanPlayer(player).setBlocks(ClanManager.getClanPlayer(player).getBlocks() + 1);
                    }
                    prisonPitPlayer.setBlocks(prisonPitPlayer.getBlocks() + 1);
                    prisonPitPlayer.setMoneypersec(prisonPitPlayer.getMoneypersec() + ((1.0 * (getAmountBoosterMoney(prisonPitPlayer.getBoosterMoney_level()) * getAmountBoosterMoneyII(prisonPitPlayer.getBoosterMoneyII_level()) * (1.0 + ((prisonPitPlayer.getGoldblocks() * (prisonPitPlayer.getBoosterprocentgoldblocks_level() + 1)) / 100.0))) * boosterStar)));
                    if (random.nextInt(100000) < prisonPitPlayer.getShardsFromBlock_level() * 5) {
                        if (TokenManager.moreShards.contains(player)) {
                            prisonPitPlayer.setShards((int) (prisonPitPlayer.getShards() + 2 * Booster.getMultiplier(prisonPitPlayer.getName(), BoosterType.SHARDS)));
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                            player.sendMessage("§d§lВы нашли"+ (2 * Booster.getMultiplier(prisonPitPlayer.getName(), BoosterType.SHARDS)) + " осколка!");
                        } else {
                            prisonPitPlayer.setShards((int) (prisonPitPlayer.getShards() + 1* Booster.getMultiplier(prisonPitPlayer.getName(), BoosterType.SHARDS)));
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                            player.sendMessage("§d§lВы нашли осколок!");
                        }
                    }

                    if (random.nextInt(10000) < prisonPitPlayer.getDoubleBlock_level() * 5) {
                        prisonPitPlayer.setBlocks(prisonPitPlayer.getBlocks() + 1);
                        prisonPitPlayer.setMoneypersec(prisonPitPlayer.getMoneypersec() + ((1.0 * (getAmountBoosterMoney(prisonPitPlayer.getBoosterMoney_level()) * getAmountBoosterMoneyII(prisonPitPlayer.getBoosterMoneyII_level()) * (1.0 + ((prisonPitPlayer.getGoldblocks() * (prisonPitPlayer.getBoosterprocentgoldblocks_level() + 1)) / 100.0))) * boosterStar)));
                    }
                    prisonPitPlayer.setPoints(prisonPitPlayer.getPoints() + 1);
                    setPointsLevelBreak(prisonPitPlayer);
                }
            } else {
                if(ClanManager.hasClan(player)){
                    ClanManager.getClanPlayer(player).setBlocks(ClanManager.getClanPlayer(player).getBlocks() + 1);
                }
                prisonPitPlayer.setBlocks(prisonPitPlayer.getBlocks() + 1);

                prisonPitPlayer.setMoneypersec(prisonPitPlayer.getMoneypersec() + ((1.0 * (getAmountBoosterMoney(prisonPitPlayer.getBoosterMoney_level()) * getAmountBoosterMoneyII(prisonPitPlayer.getBoosterMoneyII_level()) * (1.0 + ((prisonPitPlayer.getGoldblocks() * (prisonPitPlayer.getBoosterprocentgoldblocks_level() + 1)) / 100.0))) * boosterStar)));
                if (random.nextInt(100000) < prisonPitPlayer.getShardsFromBlock_level() * 5) {
                    if (TokenManager.moreShards.contains(player)) {
                        prisonPitPlayer.setShards((int) (prisonPitPlayer.getShards() + (2 * Booster.getMultiplier(prisonPitPlayer.getName(), BoosterType.SHARDS))));
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        player.sendMessage("§d§lВы нашли 2 осколка!");
                    } else {
                        prisonPitPlayer.setShards((int) (prisonPitPlayer.getShards() + (1 * Booster.getMultiplier(prisonPitPlayer.getName(), BoosterType.SHARDS))));
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        player.sendMessage("§d§lВы нашли осколок!");
                    }
                }

                if (random.nextInt(10000) < prisonPitPlayer.getDoubleBlock_level() * 5) {
                    if(ClanManager.hasClan(player)){
                        ClanManager.getClanPlayer(player).setBlocks(ClanManager.getClanPlayer(player).getBlocks() + 1);
                    }
                    prisonPitPlayer.setBlocks(prisonPitPlayer.getBlocks() + 1);
                    prisonPitPlayer.setMoneypersec(prisonPitPlayer.getMoneypersec() + ((1.0 * (getAmountBoosterMoney(prisonPitPlayer.getBoosterMoney_level()) * getAmountBoosterMoneyII(prisonPitPlayer.getBoosterMoneyII_level()) * (1.0 + ((prisonPitPlayer.getGoldblocks() * (prisonPitPlayer.getBoosterprocentgoldblocks_level() + 1)) / 100.0))) * boosterStar)));
                }

                if(random.nextInt(100000)< prisonPitPlayer.getToken_level()){
                    prisonPitPlayer.setTokens(prisonPitPlayer.getTokens()+1);
                    player.sendMessage("§b§lВы нашли токен!");
                    player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS,1,-2);

                    ItemStack token = new ItemStack( Material.FIREWORK_CHARGE, prisonPitPlayer.getTokens());
                    ItemMeta meta = token.getItemMeta();
                    meta.setDisplayName("§b§lТокен");
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                    FireworkEffectMeta metaFw = (FireworkEffectMeta) meta;
                    FireworkEffect aa = FireworkEffect.builder().withColor(Color.AQUA).build();
                    metaFw.setEffect(aa);
                    token.setItemMeta(metaFw);

                    player.getInventory().setItem(7,token);
                }
                prisonPitPlayer.setPoints(prisonPitPlayer.getPoints() + 1);
                setPointsLevelBreak(prisonPitPlayer);
            }
        }
    }

    public static Double getAmountBoosterMoney(int level) {
        if (level == 0) {
            return 1.0;
        }
        return Math.pow(3.0, level);
    }

    public static Double getAmountBoosterMoneyII(int level) {
        if (level == 0) {
            return 1.0;
        }
        Random rand = new Random();
        if (rand.nextInt(10000) <= level * 5) {
            return 3.0;
        } else {
            return 1.0;
        }
    }
}
