package fun.oneline.prisonpit.tokens;

import fun.oneline.prisonpit.boosters.Booster;
import fun.oneline.prisonpit.boosters.BoosterType;
import fun.oneline.prisonpit.clans.ClanManager;
import fun.oneline.prisonpit.player.PrisonPitPlayer;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

import static fun.oneline.prisonpit.handlers.onBreak.*;

public class AutoBlocks extends BukkitRunnable {
    Random random = new Random();

    @Override
    public void run() {
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            if(TokenManager.autoBlocks.contains(player)){
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
                    if(ClanManager.hasClan(player)){
                        ClanManager.getClanPlayer(player).setBlocks(ClanManager.getClanPlayer(player).getBlocks() + 1);
                    }
                    prisonPitPlayer.setBlocks(prisonPitPlayer.getBlocks() + 1);

                    prisonPitPlayer.setMoneypersec(prisonPitPlayer.getMoneypersec() + ((1.0 * (getAmountBoosterMoney(prisonPitPlayer.getBoosterMoney_level()) * getAmountBoosterMoneyII(prisonPitPlayer.getBoosterMoneyII_level()) * (1.0+((prisonPitPlayer.getGoldblocks() * (prisonPitPlayer.getBoosterprocentgoldblocks_level()+1))/100.0)))* boosterStar)));
                    if (random.nextInt(100000) < prisonPitPlayer.getShardsFromBlock_level() * 5) {
                        if(TokenManager.moreShards.contains(player)){
                            prisonPitPlayer.setShards((int) (prisonPitPlayer.getShards() + (2* Booster.getMultiplier(player.getName(), BoosterType.SHARDS))));
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                            player.sendMessage("§d§lВы нашли 2 осколка!");
                        } else {
                            prisonPitPlayer.setShards((int) (prisonPitPlayer.getShards() + (1* Booster.getMultiplier(player.getName(), BoosterType.SHARDS))));
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                            player.sendMessage("§d§lВы нашли осколок!");
                        }
                    }

                    if (random.nextInt(10000) < prisonPitPlayer.getDoubleBlock_level() * 5) {
                        if(ClanManager.hasClan(player)){
                            ClanManager.getClanPlayer(player).setBlocks(ClanManager.getClanPlayer(player).getBlocks() + 1);
                        }
                        prisonPitPlayer.setBlocks(prisonPitPlayer.getBlocks() + 1);
                        prisonPitPlayer.setMoneypersec(prisonPitPlayer.getMoneypersec() + ((1.0 * (getAmountBoosterMoney(prisonPitPlayer.getBoosterMoney_level()) * getAmountBoosterMoneyII(prisonPitPlayer.getBoosterMoneyII_level()) * (1.0+((prisonPitPlayer.getGoldblocks() * (prisonPitPlayer.getBoosterprocentgoldblocks_level()+1))/100.0)))* boosterStar)));
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
    }
}
