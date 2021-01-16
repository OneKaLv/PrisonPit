package fun.oneline.prisonpit.handlers;

import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.clans.Clan;
import fun.oneline.prisonpit.clans.ClanManager;
import net.citizensnpcs.npc.ai.speech.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onPlayerChat implements Listener {
    public onPlayerChat(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (ClanManager.nameChangeList.contains(event.getPlayer().getName())) {
            if (event.getMessage().length() >= 5 && event.getMessage().length() <= 15) {
                boolean clanNameCreated =false;
                for(Clan clan : ClanManager.clanList) {
                    if(clan.getClanName().equals(event.getMessage())){
                        clanNameCreated = true;
                    }
                }
                if(!clanNameCreated) {
                    ClanManager.createClanMenu(event.getPlayer(), event.getMessage(), ClanManager.tagClanHashMap.getOrDefault(event.getPlayer().getName(), ""));
                    ClanManager.nameClanHashMap.put(event.getPlayer().getName(), event.getMessage());
                    ClanManager.nameChangeList.remove(event.getPlayer().getName());
                } else {
                    event.getPlayer().sendMessage("§7[§e§lPrison §6§lPit§7]§c Такое название клана уже существует, пожалуйста, введите другое!");
                }
            } else {
                event.getPlayer().sendMessage("§7[§e§lPrison §6§lPit§7]§c Название клана должно содержать от 5 до 15 символов!");
                ClanManager.nameChangeList.remove(event.getPlayer().getName());
            }
            event.setCancelled(true);
        } else if (ClanManager.tagChangeList.contains(event.getPlayer().getName())) {
            if (event.getMessage().length() >= 3 && event.getMessage().length() <= 7) {
                ClanManager.createClanMenu(event.getPlayer(), ClanManager.nameClanHashMap.getOrDefault(event.getPlayer().getName(), ""), event.getMessage());
                ClanManager.tagClanHashMap.put(event.getPlayer().getName(), event.getMessage());
                ClanManager.tagChangeList.remove(event.getPlayer().getName());
            } else {
                event.getPlayer().sendMessage("§7[§e§lPrison §6§lPit§7]§c Тэг клана должно содержать от 3 до 7 символов!");
                ClanManager.tagChangeList.remove(event.getPlayer().getName());
            }
            event.setCancelled(true);
        } else if (ClanManager.clanMessageChangeList.contains(event.getPlayer().getName())) {
            if (event.getMessage().length() >= 30 && event.getMessage().length() <= 200) {
                ClanManager.getClanPlayer(event.getPlayer()).setClanJoinMessage(event.getMessage());
                ClanManager.clanMessageChangeList.remove(event.getPlayer().getName());
                event.getPlayer().sendMessage("§7[§e§lPrison §6§lPit§7]§a Сообщение клана установлено.");
                ClanManager.getClanPlayer(event.getPlayer()).broadCastNotify("§7[" + ChatColor.translateAlternateColorCodes('&', ClanManager.getClanPlayer(event.getPlayer()).getClanName()) + "§7]§e Игрок " + event.getPlayer().getName() + " сменил приветственное сообщение клана на: " + ChatColor.translateAlternateColorCodes('&', event.getMessage()));
            } else {
                event.getPlayer().sendMessage("§7[§e§lPrison §6§lPit§7]§c Сообщение клана допустимо от 30 до 200 символов!");
                ClanManager.clanMessageChangeList.remove(event.getPlayer().getName());
            }
            event.setCancelled(true);
        } else if (ClanManager.clanStatsEnterChangeList.contains(event.getPlayer().getName())) {
            if (event.getMessage().matches("[-+]?\\d+") && Integer.parseInt(event.getMessage()) > 0) {
                ClanManager.getClanPlayer(event.getPlayer()).setClanStarsEnter(Integer.parseInt(event.getMessage()));
                ClanManager.clanStatsEnterChangeList.remove(event.getPlayer().getName());
                event.getPlayer().sendMessage("§7[§e§lPrison §6§lPit§7]§a Необходимое количество звёзд для входа в клан изменено.");
                ClanManager.getClanPlayer(event.getPlayer()).broadCastNotify("§7[" + ChatColor.translateAlternateColorCodes('&', ClanManager.getClanPlayer(event.getPlayer()).getClanName()) + "§7]§e Игрок " + event.getPlayer().getName() + " сменил необходимое количество звёзд для вступления на " + event.getMessage());
            } else {
                event.getPlayer().sendMessage("§7[§e§lPrison §6§lPit§7]§c Недопустимое число!");
                ClanManager.clanStatsEnterChangeList.remove(event.getPlayer().getName());
            }
            event.setCancelled(true);
        } else if (ClanManager.tagRenameList.contains(event.getPlayer().getName())) {
            if (event.getMessage().length() >= 3 && event.getMessage().length() <= 7) {
                ClanManager.getClanPlayer(event.getPlayer()).setClanTag(event.getMessage());
                ClanManager.tagRenameList.remove(event.getPlayer().getName());
                event.getPlayer().sendMessage("§7[§e§lPrison §6§lPit§7]§a Клановый тэг изменён.");
                ClanManager.getClanPlayer(event.getPlayer()).broadCastNotify("§7[" + ChatColor.translateAlternateColorCodes('&', ClanManager.getClanPlayer(event.getPlayer()).getClanName()) + "§7]§e Игрок " + event.getPlayer().getName() + " сменил клановый тэг на " + ChatColor.translateAlternateColorCodes('&',event.getMessage()));
            } else {
                event.getPlayer().sendMessage("§7[§e§lPrison §6§lPit§7]§c Тэг клана должно содержать от 3 до 7 символов!");
                ClanManager.tagRenameList.remove(event.getPlayer().getName());
            }
            event.setCancelled(true);
        }
    }
}
