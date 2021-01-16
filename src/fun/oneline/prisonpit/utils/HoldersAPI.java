package fun.oneline.prisonpit.utils;

import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.clans.ClanManager;
import fun.oneline.prisonpit.leaderboards.LeaderBoard;
import fun.oneline.prisonpit.leaderboards.LeaderBoards;
import fun.oneline.prisonpit.player.PrisonPitPlayer;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class HoldersAPI extends PlaceholderExpansion {
    private Main plugin;

    public HoldersAPI(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "prisonpit";
    }

    @Override
    public String getAuthor() {
        return "OneKa";
    }

    @Override
    public String getVersion() {
        return "1.0.2";
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        if(identifier.equals("clanTag")) {
            if (ClanManager.hasClan((Player) player)) {
                return ChatColor.translateAlternateColorCodes('&', ClanManager.getClanPlayer((Player) player).getClanTag());
            } else {
                return "";
            }
        }
        if (identifier.equals("stars")) {
            if (PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getStars()[0] > 0) {
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
                return "§e" + minstar + "§e✯";
            } else {
                return "";
            }
        }

        if(identifier.equals("starswithbrackets")){
            if (PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getStars()[0] > 0) {
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
                return "§7[§e" + minstar + "§e✯§7]";
            } else {
                return "";
            }
        }

        if (identifier.equals("top_clanStars_1_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(1, "clanStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanStars_2_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(2, "clanStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanStars_3_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(3, "clanStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanStars_4_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(4, "clanStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanStars_5_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(5, "clanStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanStars_6_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(6, "clanStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanStars_7_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(7, "clanStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanStars_8_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(8, "clanStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanStars_9_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(9, "clanStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanStars_10_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(10, "clanStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanStars_1_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(1, "clanStars");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanStars_2_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(2, "clanStars");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanStars_3_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(3, "clanStars");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanStars_4_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(4, "clanStars");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanStars_5_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(5, "clanStars");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanStars_6_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(6, "clanStars");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanStars_7_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(7, "clanStars");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanStars_8_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(8, "clanStars");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanStars_9_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(9, "clanStars");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanStars_10_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(10, "clanStars");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanBlocks_1_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(1, "clanBlocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanBlocks_2_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(2, "clanBlocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanBlocks_3_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(3, "clanBlocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanBlocks_4_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(4, "clanBlocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanBlocks_5_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(5, "clanBlocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanBlocks_6_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(6, "clanBlocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanBlocks_7_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(7, "clanBlocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanBlocks_8_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(8, "clanBlocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanBlocks_9_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(9, "clanBlocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanBlocks_10_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(10, "clanBlocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_clanBlocks_1_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(1, "clanBlocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanBlocks_2_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(2, "clanBlocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanBlocks_3_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(3, "clanBlocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanBlocks_4_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(4, "clanBlocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanBlocks_5_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(5, "clanBlocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanBlocks_6_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(6, "clanBlocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanBlocks_7_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(7, "clanBlocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanBlocks_8_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(8, "clanBlocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanBlocks_9_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(9, "clanBlocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_clanBlocks_10_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(10, "clanBlocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_goldblocks_1_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(1, "goldblocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_goldblocks_2_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(2, "goldblocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_goldblocks_3_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(3, "goldblocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_goldblocks_4_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(4, "goldblocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_goldblocks_5_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(5, "goldblocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_goldblocks_6_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(6, "goldblocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_goldblocks_7_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(7, "goldblocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_goldblocks_8_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(8, "goldblocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_goldblocks_9_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(9, "goldblocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_goldblocks_10_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(10, "goldblocks");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_goldblocks_1_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(1, "goldblocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_goldblocks_2_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(2, "goldblocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_goldblocks_3_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(3, "goldblocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_goldblocks_4_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(4, "goldblocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_goldblocks_5_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(5, "goldblocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_goldblocks_6_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(6, "goldblocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_goldblocks_7_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(7, "goldblocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_goldblocks_8_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(8, "goldblocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_goldblocks_9_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(9, "goldblocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_goldblocks_10_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(10, "goldblocks");
            return Utils.getMoney(leaderBoard.getCount());
        }

        if (identifier.equals("top_totalStars_1_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(1, "totalStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_totalStars_2_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(2, "totalStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_totalStars_3_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(3, "totalStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_totalStars_4_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(4, "totalStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_totalStars_5_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(5, "totalStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_totalStars_6_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(6, "totalStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_totalStars_7_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(7, "totalStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_totalStars_8_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(8, "totalStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_totalStars_9_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(9, "totalStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_totalStars_10_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(10, "totalStars");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_totalStars_1_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(1, "totalStars");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_totalStars_2_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(2, "totalStars");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_totalStars_3_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(3, "totalStars");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_totalStars_4_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(4, "totalStars");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_totalStars_5_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(5, "totalStars");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_totalStars_6_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(6, "totalStars");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_totalStars_7_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(7, "totalStars");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_totalStars_8_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(8, "totalStars");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_totalStars_9_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(9, "totalStars");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_totalStars_10_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(10, "totalStars");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_maxBlock_1_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(1, "maxBoosterMoney_level");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_maxBlock_2_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(2, "maxBoosterMoney_level");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_maxBlock_3_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(3, "maxBoosterMoney_level");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_maxBlock_4_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(4, "maxBoosterMoney_level");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_maxBlock_5_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(5, "maxBoosterMoney_level");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_maxBlock_6_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(6, "maxBoosterMoney_level");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_maxBlock_7_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(7, "maxBoosterMoney_level");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_maxBlock_8_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(8, "maxBoosterMoney_level");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_maxBlock_9_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(9, "maxBoosterMoney_level");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_maxBlock_10_name")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(10, "maxBoosterMoney_level");
            return leaderBoard.getName();
        }

        if (identifier.equals("top_maxBlock_1_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(1, "maxBoosterMoney_level");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_maxBlock_2_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(2, "maxBoosterMoney_level");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_maxBlock_3_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(3, "maxBoosterMoney_level");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_maxBlock_4_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(4, "maxBoosterMoney_level");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_maxBlock_5_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(5, "maxBoosterMoney_level");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_maxBlock_6_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(6, "maxBoosterMoney_level");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_maxBlock_7_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(7, "maxBoosterMoney_level");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_maxBlock_8_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(8, "maxBoosterMoney_level");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_maxBlock_9_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(9, "maxBoosterMoney_level");
            return String.valueOf(leaderBoard.getCount());
        }

        if (identifier.equals("top_maxBlock_10_count")) {
            LeaderBoard leaderBoard = LeaderBoards.getLeaderBoardStats(10, "maxBoosterMoney_level");
            return String.valueOf(leaderBoard.getCount());
        }
        return null;
    }
}
