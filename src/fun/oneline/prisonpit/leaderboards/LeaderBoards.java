package fun.oneline.prisonpit.leaderboards;

import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.clans.ClanManager;
import fun.oneline.prisonpit.database.MySQL;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LeaderBoards {

    public static Map<Integer, LeaderBoard> goldblocks;
    public static Map<Integer, LeaderBoard> maxBlock;
    public static Map<Integer, LeaderBoard> totalStars;
    public static Map<Integer, LeaderBoard> clanBlocks;
    public static Map<Integer, LeaderBoard> clanStars;

    public LeaderBoards(Main plugin) {
        goldblocks = new HashMap<>();
        maxBlock = new HashMap<>();
        totalStars = new HashMap<>();
        clanBlocks = new HashMap<>();
        clanStars = new HashMap<>();
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::update, 0, 24000);
    }

    public static LeaderBoard getLeaderBoardStats(int i, String type) {
        LeaderBoard leaderBoard;
        if (type.equalsIgnoreCase("goldblocks")) {
            if (goldblocks.containsKey(i)) {
                leaderBoard = goldblocks.get(i);
                return leaderBoard;
            }
        } else if (type.equalsIgnoreCase("maxBoosterMoney_level")) {
            if (maxBlock.containsKey(i)) {
                leaderBoard = maxBlock.get(i);
                return leaderBoard;
            }
        } else if (type.equalsIgnoreCase("totalStars")) {
            if (totalStars.containsKey(i)) {
                leaderBoard = totalStars.get(i);
                return leaderBoard;
            }
        } else if (type.equalsIgnoreCase("clanBlocks")) {
            if (clanBlocks.containsKey(i)) {
                leaderBoard = clanBlocks.get(i);
                return leaderBoard;
            }
        } else if (type.equalsIgnoreCase("clanStars")) {
            if (clanStars.containsKey(i)) {
                leaderBoard = clanStars.get(i);
                return leaderBoard;
            }
        }


        leaderBoard = new LeaderBoard("Loading...", 0);
        return leaderBoard;
    }

    public void update() {
        ClanManager.saveClans();
        for (Player player : Bukkit.getOnlinePlayers()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    PrisonPitPlayerManager.save(player);
                }
            }.runTaskAsynchronously(Main.instance);
        }

        Connection conn = MySQL.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + MySQL.prisonpit_players + " ORDER BY goldblocks DESC LIMIT 10");
            ResultSet resultSet = statement.executeQuery();
            int i = 1;
            while (resultSet.next()) {
                goldblocks.put(i, new LeaderBoard(resultSet.getString("player_name"), resultSet.getInt("goldblocks")));
                i++;
            }

            statement = conn.prepareStatement("SELECT * FROM " + MySQL.prisonpit_players + " ORDER BY maxBoosterMoney_level DESC LIMIT 10");
            resultSet = statement.executeQuery();
            i = 1;
            while (resultSet.next()) {
                maxBlock.put(i, new LeaderBoard(resultSet.getString("player_name"), resultSet.getInt("maxBoosterMoney_level")));
                i++;
            }

            statement = conn.prepareStatement("SELECT * FROM " + MySQL.prisonpit_players + " ORDER BY totalStars DESC LIMIT 10");
            resultSet = statement.executeQuery();
            i = 1;
            while (resultSet.next()) {
                totalStars.put(i, new LeaderBoard(resultSet.getString("player_name"), resultSet.getInt("totalStars")));
                i++;
            }

            statement = conn.prepareStatement("SELECT * FROM " + MySQL.clans_table + " ORDER BY blocks DESC LIMIT 10");
            resultSet = statement.executeQuery();
            i = 1;
            while (resultSet.next()) {
                clanBlocks.put(i, new LeaderBoard(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&',resultSet.getString("ClanName"))), resultSet.getInt("blocks")));
                i++;
            }

            statement = conn.prepareStatement("SELECT * FROM " + MySQL.clans_table + " ORDER BY totalClanStars DESC LIMIT 10");
            resultSet = statement.executeQuery();
            i = 1;
            while (resultSet.next()) {
                clanStars.put(i, new LeaderBoard(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&',resultSet.getString("ClanName"))), resultSet.getInt("totalClanStars")));
                i++;
            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
