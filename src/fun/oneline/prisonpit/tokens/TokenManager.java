package fun.oneline.prisonpit.tokens;

import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TokenManager {
    public static List<Player> moreMoney = new ArrayList<>();
    public static List<Player> autoBlocks = new ArrayList();
    public static List<Player> moreShards = new ArrayList();
    public static List<Player> fastPickaxe = new ArrayList();
    public static HashMap<Player, Integer> TimerMap = new HashMap<>();

    public static String getRandomToken() {
        Random random = new Random();
        switch (random.nextInt(4)) {
            case 0:
                return "Больше денег";
            case 1:
                return "Автоматические блоки";
            case 2:
                return "Больше осколков";
            case 3:
                return "Быстрая кирка";
        }
        return null;
    }

    public static void setToken(String string, Player player) {
        switch (string) {
            case "Больше денег": {
                moreMoney.add(player);
                TimerMap.put(player, 60);
                PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setActiveBonus("Больше денег");
                new BukkitRunnable() {
                    Player finalPlayer = player;
                    @Override
                    public void run() {
                        if (finalPlayer != null && player.isOnline()) {
                            moreMoney.remove(player);
                            TimerMap.remove(player);
                            PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setActiveBonus("");
                        }
                    }
                }.runTaskLater(Main.instance, 1200);
                break;
            }
            case "Автоматические блоки": {
                autoBlocks.add(player);
                TimerMap.put(player, 600);
                PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setActiveBonus("Автоматические блоки");
                new BukkitRunnable() {
                    Player finalPlayer = player;
                    @Override
                    public void run() {
                        if (finalPlayer != null && player.isOnline()) {
                            autoBlocks.remove(player);
                            TimerMap.remove(player);
                            PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setActiveBonus("");
                        }
                    }
                }.runTaskLater(Main.instance, 12000);
                break;
            }
            case "Больше осколков": {
                moreShards.add(player);
                TimerMap.put(player, 600);
                PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setActiveBonus("Больше осколков");
                new BukkitRunnable() {
                    Player finalPlayer = player;

                    @Override
                    public void run() {
                        if (finalPlayer != null && player.isOnline()) {
                            moreShards.remove(player);
                            TimerMap.remove(player);
                            PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setActiveBonus("");
                        }
                    }
                }.runTaskLater(Main.instance, 12000);
                break;
            }
            case "Быстрая кирка": {
                fastPickaxe.add(player);
                TimerMap.put(player, 600);
                PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setActiveBonus("Быстрая кирка");
                new BukkitRunnable() {
                    Player finalPlayer = player;

                    @Override
                    public void run() {
                        if (finalPlayer != null && player.isOnline()) {
                            fastPickaxe.remove(player);
                            TimerMap.remove(player);
                            PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setActiveBonus("");
                        }
                    }
                }.runTaskLater(Main.instance, 12000);
                break;
            }
        }
    }

    public static void startTimer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : TimerMap.keySet()) {
                    TimerMap.replace(p, TimerMap.get(p) - 1);
                    if (p != null && p.isOnline()) {
                        PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).setTimeToEndBonus(TokenManager.TimerMap.get(p));
                    }
                }
            }
        }.runTaskTimer(Main.instance, 20, 20);
    }
}
