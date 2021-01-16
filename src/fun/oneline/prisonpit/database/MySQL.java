package fun.oneline.prisonpit.database;

import fun.oneline.prisonpit.clans.Clan;
import fun.oneline.prisonpit.clans.ClanManager;
import ru.dream.network.core.api.donate.DonateUtil;
import ru.dream.network.core.service.account.IAccountService;
import ru.dream.network.core.service.account.group.DonateGroup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MySQL {
    public static Connection conn;
    public static Statement statmt;
    public static String prisonpit_players = "prisonpit_players";
    public static String clans_table = "clans_table";
    public static String timer_table = "group_timer";
    public static String donate_table = "Auth.VKAuthorization";
    public static String clan_league_prises = "clan_league_prises";
    private static PreparedStatement preparedStatement = null;
    private String createTableLeaguePrises = "CREATE TABLE IF NOT EXISTS clan_league_prises (player_name varchar(20) PRIMARY KEY, prise INT DEFAULT '0')";
    private String createTable = "CREATE TABLE IF NOT EXISTS prisonpit_players (player_name varchar(20) PRIMARY KEY,money DOUBLE DEFAULT '0',moneypersec DOUBLE DEFAULT '0', timetostorm INT DEFAULT '0',blocks INT DEFAULT '0',shards INT DEFAULT '0', points INT DEFAULT '0',tokens INT DEFAULT '0',goldblocks DOUBLE DEFAULT '0',nonspendmoney DOUBLE DEFAULT '0',pickaxe_level INT DEFAULT '0',boosterMoney_level INT DEFAULT '0',shardsFromBlock_level INT DEFAULT '0',doubleblock_level INT DEFAULT '0',boosterMoneyII_level INT DEFAULT '0',storm_level INT DEFAULT '0',levelBoost_level INT DEFAULT '0',boostershards_level INT DEFAULT '0',boostergoldblocks_level INT DEFAULT '0',boosterprocentgoldblocks_level INT DEFAULT '0',morestorms_level INT DEFAULT '0',token_level INT DEFAULT '0',star1 INT DEFAULT '0',star2 INT DEFAULT '0',star3 INT DEFAULT '0',star4 INT DEFAULT '0',star5 INT DEFAULT '0',star6 INT DEFAULT '0',star7 INT DEFAULT '0',star8 INT DEFAULT '0',star9 INT DEFAULT '0',star10 INT DEFAULT '0',activeBonus varchar(20), timeToEndBonus INT DEFAULT '0', maxBoosterMoney_level INT DEFAULT '0',totalStars INT DEFAULT '0',goldblockroom_level INT DEFAULT '0',goldblockroom_points INT DEFAULT '0',fragments DOUBLE DEFAULT '0',bricks DOUBLE DEFAULT '0',brickspersec DOUBLE DEFAULT '0',bricklevel INT DEFAULT '0',moreFragments_level INT DEFAULT '0',moreMagnets_level INT DEFAULT '0',GBDiscound_level INT DEFAULT '0',moreMagnetsII_level INT DEFAULT '0',moreMoney_level INT DEFAULT '0',moreMagnetsIII_level INT DEFAULT '0',moreBricks_level INT DEFAULT '0',stageq INT DEFAULT '0')";
    private String createClansTable = "CREATE TABLE IF NOT EXISTS clans_table (ClanName varchar(20) PRIMARY KEY, ClanTag varchar(20), Owner varchar(20),  Soruks varchar(500),  Staricks varchar(500),  Members varchar(500), clanJoinMessage varchar (200), clanStarsEnter INT DEFAULT '1', blocks DOUBLE DEFAULT '0', totalClanStars int DEFAULT '0')";

    public MySQL(String url, String dbName, String user, String pass) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://" + url + "/" + dbName + "?useUnicode=true&characterEncoding=utf8&autoReconnect=true",
                    user, pass);
            statmt = conn.createStatement();
            statmt.execute(createTable);
            statmt.execute(createClansTable);
            statmt.execute(createTableLeaguePrises);
            statmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return conn;
    }

    public static void insert(String name) {
        try {
            PreparedStatement e = conn.prepareStatement(
                    "INSERT INTO " + prisonpit_players + " (player_name,money,moneypersec, timetostorm, blocks,shards,points,tokens,goldblocks,nonspendmoney,pickaxe_level,boosterMoney_level,shardsFromBlock_level,doubleblock_level,boosterMoneyII_level,storm_level,levelboost_level,boostershards_level,boostergoldblocks_level,boosterprocentgoldblocks_level,morestorms_level,token_level,star1,star2,star3,star4,star5,star6,star7,star8,star9,star10,activeBonus,timeToEndBonus,maxBoosterMoney_level,totalStars,goldblockroom_level,goldblockroom_points,fragments,bricks,brickspersec,bricklevel,moreFragments_level,moreMagnets_level,GBDiscound_level,moreMagnetsII_level,moreMoney_level,moreMagnetsIII_level,moreBricks_level,stageq) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
            e.setString(1, name);
            e.setDouble(2, 0);
            e.setDouble(3, 0);
            e.setInt(4, 360);
            for (int i = 5; i < 9; i++) {
                e.setInt(i, 0);
            }
            e.setDouble(9, 0);
            e.setDouble(10, 0);
            for (int i = 11; i <= 32; i++) {
                e.setInt(i, 0);
            }
            e.setString(33, "");
            e.setInt(34, 0);
            e.setInt(35, 0);
            e.setInt(36, 0);
            e.setInt(37, 0);
            e.setInt(38, 0);
            e.setDouble(39, 0);
            e.setDouble(40, 0);
            e.setDouble(41, 0);
            e.setInt(42, 0);
            e.setInt(43, 0);
            e.setInt(44, 0);
            e.setInt(45, 0);
            e.setInt(46, 0);
            e.setInt(47, 0);
            e.setInt(48, 0);
            e.setInt(49, 0);
            e.setInt(50, 0);

            e.executeUpdate();
            e.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SavePrise(String name,int prise) {
        try {
            PreparedStatement e = conn.prepareStatement(
                    "INSERT INTO " + clan_league_prises + " (player_name, prise) VALUES (?,?);");
            e.setString(1, name);
            e.setInt(2, prise);
            e.executeUpdate();
            e.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object select(String table, String user, String column) {
        Object object = 1;
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM " + table + " WHERE player_name LIKE ?;");
            preparedStatement.setString(1, user);
            ResultSet e = preparedStatement.executeQuery();
            if (e.next()) {
                object = e.getObject(column);
            }
            e.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public static void selectClans() {
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM " + MySQL.clans_table);
            ResultSet e = preparedStatement.executeQuery();
            while (e.next()) {
                ClanManager.getClanList().add(new Clan(e.getString(1), e.getString(2), e.getString(3), e.getString(4).equals("") ? new ArrayList<>() : new ArrayList<>(Arrays.asList(e.getString(4).split(","))), e.getString(5).equals("") ? new ArrayList<>() : new ArrayList<>(Arrays.asList(e.getString(5).split(","))), e.getString(6).equals("") ? new ArrayList<>() : new ArrayList<>(Arrays.asList(e.getString(6).split(","))), e.getString(7), e.getInt(8), e.getDouble(9)));
            }
            e.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearPrises(){
        try {
            preparedStatement = conn.prepareStatement("TRUNCATE TABLE " + MySQL.clan_league_prises);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void selectPrises() {
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM " + MySQL.clan_league_prises);
            ResultSet e = preparedStatement.executeQuery();
            while (e.next()) {
                ClanManager.priseHashMap.put(e.getString(1), e.getInt(2));
            }
            e.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean clanExists(String ClanName) {
        boolean is = false;
        try {
            PreparedStatement statement = conn
                    .prepareStatement("SELECT * FROM " + clans_table + " WHERE ClanName LIKE ?");
            statement.setString(1, ClanName);

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                is = true;
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return is;
    }

    public static void updateClans(String table, String user, String column, Object value) {
        try {
            PreparedStatement e = conn.prepareStatement("UPDATE " + table + " SET " + column + "=? WHERE ClanName LIKE ?");
            e.setObject(1, value);
            e.setString(2, user);
            e.executeUpdate();
            e.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertClan(String ClanName) {
        try {
            PreparedStatement e = conn.prepareStatement(
                    "INSERT INTO " + clans_table + " (ClanName,Owner,ClanTag,Soruks,Staricks,Members,clanJoinMessage,clanStarsEnter,blocks) VALUES (?,?,?,?,?,?,?,?,?);");
            e.setString(1, ClanName);
            e.setString(2, "");
            e.setString(3, "");
            e.setString(4, "");
            e.setString(5, "");
            e.setString(6, "");
            e.setString(7, "");
            e.setInt(8, 1);
            e.setDouble(9, 0);
            e.executeUpdate();
            e.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(String table, String user, String column, Object value) {
        try {
            PreparedStatement e = conn.prepareStatement("UPDATE " + table + " SET " + column + "=? WHERE player_name LIKE ?");
            e.setObject(1, value);
            e.setString(2, user);
            e.executeUpdate();
            e.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean playerExists(String table, String name) {
        boolean is = false;
        try {
            PreparedStatement statement = conn
                    .prepareStatement("SELECT * FROM " + table + " WHERE player_name LIKE ?");
            statement.setString(1, name);

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                is = true;
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return is;
    }
}
