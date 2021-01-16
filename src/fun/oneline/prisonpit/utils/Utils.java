package fun.oneline.prisonpit.utils;

import fun.oneline.prisonpit.clans.ClanManager;
import fun.oneline.prisonpit.player.PrisonPitPlayer;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.dream.network.core.api.donate.DonateUtil;
import ru.dream.network.core.service.account.IAccountService;

public class Utils {
    public static String getMoney(double amount) {
        int rank = 0;
        char secondLetter = '-';
        while (amount >= 1000) {
            if(rank >= 26){
                secondLetter = (char) ((rank/26)+0x60);
            }
            amount /= 1000;
            rank++;
        }
        String formatted = rank == 0 ? String.valueOf((int) amount) : String.format("%.2f", amount);
        char letter = (char) ((rank - 1) + 0x61);
        if(rank > 26){
            letter = (char) ((rank%26 - 1) + 0x61);
        }
        if (letter == 'a' && rank == 1) {
            letter = 'K';
        }
        if(secondLetter != '-'){
            formatted += secondLetter;
        }
        if (rank != 0) {
            formatted += letter;
        }
        return formatted;
    }

    public static int getDonateNum(String user) {
        switch (IAccountService.get().getProfile(user).getDonateGroup()){
            case vip : return 1;
            case vip_plus : return 2;
            case premium : return 3;
            case premium_plus: return 4;
            case elite: return 5;
            case elite_plus: return 6;
            case sponsor: return 7;
            case sponsor_plus: return 8;
        }
        return 0;
    }

    public static int getDonateCoin(String user) {
        return (int) DonateUtil.getBalance(user);
    }

    public static void buyDonate(String user, int value) {
        DonateUtil.withdrawBalance(user,value, "pit");
    }

    public static double getBoosterMoneyFromPriv(int priv){
        switch (priv){
            case 1:{
                return 1.1;
            }
            case 2:{
                return 1.25;
            }
            case 3:{
                return 1.4;
            }
            case 4:{
                return 1.7;
            }
            case 5:{
                return 2.0;
            }
            case 6:{
                return 2.25;
            }
            case 7:{
                return 2.50;
            }
            case 8:{
                return 2.75;
            }
        }
        return 1.0;
    }

    public static void calcTotalStars(Player p){
        PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(p.getName());
        int[] stars = prisonPitPlayer.getStars();
        int minstar = 0;
        for(int j = 0; j<10; j++){
            if(PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).getStars()[j] > 0){
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
        if(prisonPitPlayer.getTotalStars()!=minstar) {
            prisonPitPlayer.setTotalStars(minstar);
            if(ClanManager.hasClan(p)){
                ClanManager.getClanPlayer(p).broadCastNotify("§7[" + ChatColor.translateAlternateColorCodes('&', ClanManager.getClanPlayer(p).getClanName()) + "§7]§e Игрок " + p.getName() + " достиг " + minstar + " звёзд!");
            }
        }
    }

    public static String getStars(Player player){
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
}
