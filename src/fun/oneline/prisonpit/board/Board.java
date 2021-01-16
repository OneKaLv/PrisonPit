package fun.oneline.prisonpit.board;

import fun.oneline.prisonpit.player.PrisonPitPlayer;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import fun.oneline.prisonpit.tokens.TokenManager;
import fun.oneline.prisonpit.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board {

    public static List<String> anim = new ArrayList<>();
    public static Iterator it = anim.iterator();
    public static Integer i = 0;

    public static void setScoreBoard(Player player) {
        PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective(ChatColor.GOLD + "" + ChatColor.BOLD + "Prison Pit", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        obj.getScore(ChatColor.GOLD + "╔═══════════════").setScore(16);

        Team profile = board.registerNewTeam("profile");
        profile.addEntry(ChatColor.ITALIC + "" + ChatColor.GOLD);
        profile.setPrefix(ChatColor.GOLD + "║ ");
        profile.setSuffix(ChatColor.AQUA + "" + ChatColor.BOLD + "Профиль");
        obj.getScore(ChatColor.ITALIC + "" + ChatColor.GOLD).setScore(15);

        Team money = board.registerNewTeam("money");
        money.addEntry(ChatColor.RED + "" + ChatColor.WHITE);
        money.setPrefix(ChatColor.GOLD + "║  " + ChatColor.BOLD + "" + ChatColor.GREEN + "Ⓜ " + ChatColor.YELLOW + "Бал");
        money.setSuffix(ChatColor.YELLOW + "анс ► " + Utils.getMoney(prisonPitPlayer.getMoney()));
        obj.getScore(ChatColor.RED + "" + ChatColor.WHITE).setScore(14);

        Team blocks = board.registerNewTeam("blocks");
        blocks.addEntry(ChatColor.GREEN + "" + ChatColor.WHITE);
        blocks.setPrefix(ChatColor.GOLD + "║  " + ChatColor.BOLD + "" + ChatColor.GRAY + "❒ " + ChatColor.YELLOW + "Бло");
        blocks.setSuffix(ChatColor.YELLOW + "ки ► " + prisonPitPlayer.getBlocks());
        obj.getScore(ChatColor.GREEN + "" + ChatColor.WHITE).setScore(13);

        Team shards = board.registerNewTeam("shards");
        shards.addEntry(ChatColor.GOLD + "" + ChatColor.WHITE);
        shards.setPrefix(ChatColor.GOLD + "║  " + ChatColor.BOLD + ChatColor.LIGHT_PURPLE + "❊ " + ChatColor.YELLOW + "Оск");
        shards.setSuffix(ChatColor.YELLOW + "олки ► " + prisonPitPlayer.getShards());
        obj.getScore(ChatColor.GOLD + "" + ChatColor.WHITE).setScore(12);

        obj.getScore(ChatColor.GOLD + "╠═══════════════").setScore(11);

        Team bonus = board.registerNewTeam("bonus");
        bonus.addEntry(ChatColor.LIGHT_PURPLE + "" + ChatColor.DARK_GREEN);
        bonus.setPrefix(ChatColor.GOLD + "║ " + ChatColor.AQUA + "" + ChatColor.BOLD + "Бонусы");
        obj.getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.DARK_GREEN).setScore(10);

        Team activebonus = board.registerNewTeam("activebonus");
        activebonus.addEntry(ChatColor.DARK_PURPLE + "" + ChatColor.GREEN);
        activebonus.setPrefix(ChatColor.GOLD + "║  " + ChatColor.GREEN);
        obj.getScore(ChatColor.DARK_PURPLE + "" + ChatColor.GREEN).setScore(9);
        player.setScoreboard(board);

        Team timer = board.registerNewTeam("timer");
        timer.addEntry(ChatColor.DARK_PURPLE + "" + ChatColor.YELLOW);
        timer.setPrefix(ChatColor.GOLD + "║  " + ChatColor.GREEN);
        obj.getScore(ChatColor.DARK_PURPLE + "" + ChatColor.YELLOW).setScore(8);
        player.setScoreboard(board);

        obj.getScore(ChatColor.GOLD + "╚═══════════════").setScore(7);
    }

    public static void updateScoreBoard(Player player) {
        Scoreboard board = player.getScoreboard();
        board.getObjective(DisplaySlot.SIDEBAR).setDisplayName(anim.get(i));
        PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
        if (board != null) {
            Team money = board.getTeam("money");
            if (money != null) {
                money.setSuffix(ChatColor.YELLOW + "анс ► " + Utils.getMoney(prisonPitPlayer.getMoney()));
            }

            Team blocks = board.getTeam("blocks");
            if (blocks != null) {
                blocks.setSuffix(ChatColor.YELLOW + "ки ► " + prisonPitPlayer.getBlocks());
            }

            Team shards = board.getTeam("shards");
            if (shards != null) {
                shards.setSuffix(ChatColor.YELLOW + "олки ► " + prisonPitPlayer.getShards());
            }

            Team activebonus = board.getTeam("activebonus");
            if (activebonus != null) {
                if (TokenManager.moreMoney.contains(player)) {
                    activebonus.setSuffix("Больше денег");
                } else if (TokenManager.fastPickaxe.contains(player)) {
                    activebonus.setSuffix("Быстрая кирка");
                } else if (TokenManager.moreShards.contains(player)) {
                    activebonus.setSuffix("Больше осколков");
                } else if (TokenManager.autoBlocks.contains(player)) {
                    activebonus.setSuffix("Авто-блоки");
                } else {
                    activebonus.setSuffix("");
                }
            }

            Team timer = board.getTeam("timer");
            if (timer != null) {
                if (TokenManager.TimerMap.containsKey(player)) {
                    timer.setSuffix(ChatColor.LIGHT_PURPLE + String.format("%d:%02d", TokenManager.TimerMap.get(player)/60, TokenManager.TimerMap.get(player)%60));
                } else {
                    timer.setSuffix("");
                }
            }
        }
    }

    public static void setAnimText() {
        anim.add("§8[§e§lPrison §6§lPit§8]");
        anim.add("§8[§e§lPrison §6§lPit§8]");
        anim.add("§8[§e§lPrison §6§lPit§8]");
        anim.add("§8[§e§lPrison §6§lPit§8]");
        anim.add("§8[§e§lPrison §6§lPit§8]");
        anim.add("§8[§e§lPrison §6§lPit§8]");
        anim.add("§8[§e§lPrison §6§lPit§8]");
        anim.add("§8[§e§lPrison §6§lPit§8]");
        anim.add("§8[§e§lPrison §6§lPit§8]");
        anim.add("§8[§e§lPrison §6§lPit§8]");
        anim.add("§8[§e§lPrison §6§lPit§8]");
        anim.add("§8[§e§lPrison §6§lPit§8]");
        anim.add("§8[§e§lPrison §6§lPit§8]");
        anim.add("§8[§e§lPrison §6§lPit§8]");
        anim.add("§8[§e§lPrison §6§lPit§8]");
        anim.add("§8[§e§lPrison §6§lPit§8]");
        anim.add("§8[§e§lPrison §6§lPit§8]");
        anim.add("§8[§e§lPrison §6§lPit§8]");
        anim.add("§8[§e§lPrison§6§lPit§8]");
        anim.add("§8[§e§lPriso§6§lPit§8]");
        anim.add("§8[§e§lPris§6§lPit§8]");
        anim.add("§8[§e§lPri§6§lPit§8]");
        anim.add("§8[§e§lPri§6§lit§8]");
        anim.add("§8[§e§lPr§6§lit§8]");
        anim.add("§8[§e§lPr§6§lt§8]");
        anim.add("§8[§e§lP§6§lt§8]");
        anim.add("§8[§e§lP§6§l§8]");
        anim.add("§8[§8]");
        anim.add("§8[§8]");
        anim.add("§8[§8]");
        anim.add("§8[§8]");
        anim.add("§8[§8]");
        anim.add("§8[§e§lP§6§l§8]");
        anim.add("§8[§e§lP§6§lt§8]");
        anim.add("§8[§e§lPr§6§lt§8]");
        anim.add("§8[§e§lPr§6§lit§8]");
        anim.add("§8[§e§lPri§6§lit§8]");
        anim.add("§8[§e§lPri§6§lPit§8]");
        anim.add("§8[§e§lPris§6§lPit§8]");
        anim.add("§8[§e§lPriso§6§lPit§8]");
        anim.add("§8[§e§lPrison§6§lPit§8]");
        anim.add("§8[§e§lPrison §6§lPit§8]");
    }

    public static void startanim() {
        if (i >= 41) {
            i = 0;
        }
        i++;
    }
}
