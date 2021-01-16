package fun.oneline.prisonpit.clans;

import fun.oneline.api.inventory.ClickAction;
import fun.oneline.api.inventory.CustomInventory;
import fun.oneline.api.inventory.item.CustomItem;
import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.database.MySQL;
import fun.oneline.prisonpit.player.PrisonPitPlayer;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import net.minecraft.server.v1_12_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClanManager {

    public static List<String> clanDonate = new ArrayList<>();
    public static List<Clan> clanList = new ArrayList<>();
    public static List<String> nameChangeList = new ArrayList<>();
    public static List<String> tagChangeList = new ArrayList<>();
    public static List<String> clanMessageChangeList = new ArrayList<>();
    public static List<String> clanStatsEnterChangeList = new ArrayList<>();
    public static List<String> tagRenameList = new ArrayList<>();
    public static HashMap<String, String> nameClanHashMap = new HashMap<>();
    public static HashMap<String, String> tagClanHashMap = new HashMap<>();
    public static HashMap<String, Integer> priseHashMap = new HashMap<>();
    private static Item entityItem;
    private static ArmorStand entityText;
    private static int hoursLeagueEnd;

    public static void loadClans() {
        MySQL.selectClans();
        summonItem();
        hoursLeagueEnd = (int) MySQL.select(MySQL.timer_table, "pit", "time");
        startTimer();
    }

    public static void startTimer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                hoursLeagueEnd -= 1;
                if (hoursLeagueEnd < 0) {
                    hoursLeagueEnd = 168;
                    endClanLeague();
                }
            }
        }.runTaskTimer(Main.instance, 72000, 72000);
    }

    public static void endClanLeague() {
        int prize = 0;
        for (Clan clan : clanList) {
            if (clan.getBlocks() >= 5000000) {
                prize = 12;
            } else if (clan.getBlocks() >= 4000000) {
                prize = 11;
            } else if (clan.getBlocks() >= 3000000) {
                prize = 10;
            } else if (clan.getBlocks() >= 2500000) {
                prize = 9;
            } else if (clan.getBlocks() >= 2000000) {
                prize = 8;
            } else if (clan.getBlocks() >= 1500000) {
                prize = 7;
            } else if (clan.getBlocks() >= 1000000) {
                prize = 6;
            } else if (clan.getBlocks() >= 500000) {
                prize = 5;
            } else if (clan.getBlocks() >= 200000) {
                prize = 4;
            } else if (clan.getBlocks() >= 100000) {
                prize = 3;
            } else if (clan.getBlocks() >= 10000) {
                prize = 2;
            } else if (clan.getBlocks() >= 2000) {
                prize = 1;
            }
            if (prize > 0) {
                if (Bukkit.getServer().getPlayer(clan.getOwner()) != null && Bukkit.getServer().getPlayer(clan.getOwner()).isOnline()) {
                    priseHashMap.put(clan.getOwner(), prize);
                    givePriseForLeague(clan.getOwner());
                } else {
                    priseHashMap.put(clan.getOwner(), prize);
                }
                if (!clan.getSoruks().isEmpty()) {
                    for (String name : clan.getSoruks()) {
                        if (Bukkit.getServer().getPlayer(name) != null && Bukkit.getServer().getPlayer(name).isOnline()) {
                            priseHashMap.put(name, prize);
                            givePriseForLeague(name);
                        } else {
                            priseHashMap.put(name, prize);
                        }
                    }
                }
                if (!clan.getStaricks().isEmpty()) {
                    for (String name : clan.getStaricks()) {
                        if (Bukkit.getServer().getPlayer(name) != null && Bukkit.getServer().getPlayer(name).isOnline()) {
                            priseHashMap.put(name, prize);
                            givePriseForLeague(name);
                        } else {
                            priseHashMap.put(name, prize);
                        }
                    }
                }
                if (!clan.getMembers().isEmpty()) {
                    for (String name : clan.getMembers()) {
                        if (Bukkit.getServer().getPlayer(name) != null && Bukkit.getServer().getPlayer(name).isOnline()) {
                            priseHashMap.put(name, prize);
                            givePriseForLeague(name);
                        } else {
                            priseHashMap.put(name, prize);
                        }
                    }
                }
            }
            clan.setBlocks(0);
        }
    }

    public static void givePriseForLeague(String name) {
        if (priseHashMap.containsKey(name)) {
            Player player = Bukkit.getServer().getPlayer(name);
            int prize = priseHashMap.get(name);
            int rank = 0;
            int stars = PrisonPitPlayerManager.getPrisonPitPlayer(name).getTotalStars();
            while (stars >= 10) {
                rank++;
                stars -= 5;
            }
            switch (prize) {
                case 1: {
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Ваш клан " + ChatColor.translateAlternateColorCodes('&', getClanPlayer(player).getClanName()) + " §eполучил лигу Бронза III в этом сезоне. Ваш приз: §d" + (int) (5 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков§e.");
                    PrisonPitPlayerManager.getPrisonPitPlayer(name).setShards(PrisonPitPlayerManager.getPrisonPitPlayer(name).getShards() + (int) (5 * (rank == 0 ? 1 : Math.pow(1.1, rank))));
                    break;
                }
                case 2: {
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Ваш клан " + ChatColor.translateAlternateColorCodes('&', getClanPlayer(player).getClanName()) + " §eполучил лигу Бронза II в этом сезоне. Ваш приз: §d" + (int) (10 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков§e.");
                    PrisonPitPlayerManager.getPrisonPitPlayer(name).setShards(PrisonPitPlayerManager.getPrisonPitPlayer(name).getShards() + (int) (10 * (rank == 0 ? 1 : Math.pow(1.1, rank))));
                    break;
                }
                case 3: {
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Ваш клан " + ChatColor.translateAlternateColorCodes('&', getClanPlayer(player).getClanName()) + " §eполучил лигу Бронза I в этом сезоне. Ваш приз: §d" + (int) (25 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков§e.");
                    PrisonPitPlayerManager.getPrisonPitPlayer(name).setShards(PrisonPitPlayerManager.getPrisonPitPlayer(name).getShards() + (int) (25 * (rank == 0 ? 1 : Math.pow(1.1, rank))));
                    break;
                }
                case 4: {
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Ваш клан " + ChatColor.translateAlternateColorCodes('&', getClanPlayer(player).getClanName()) + " §eполучил лигу Серебро III в этом сезоне. Ваш приз: §d" + (int) (100 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков§e.");
                    PrisonPitPlayerManager.getPrisonPitPlayer(name).setShards(PrisonPitPlayerManager.getPrisonPitPlayer(name).getShards() + (int) (100 * (rank == 0 ? 1 : Math.pow(1.1, rank))));
                    break;
                }
                case 5: {
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Ваш клан " + ChatColor.translateAlternateColorCodes('&', getClanPlayer(player).getClanName()) + " §eполучил лигу Серебро II в этом сезоне. Ваш приз: §d" + (int) (200 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков§e.");
                    PrisonPitPlayerManager.getPrisonPitPlayer(name).setShards(PrisonPitPlayerManager.getPrisonPitPlayer(name).getShards() + (int) (200 * (rank == 0 ? 1 : Math.pow(1.1, rank))));
                    break;
                }
                case 6: {
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Ваш клан " + ChatColor.translateAlternateColorCodes('&', getClanPlayer(player).getClanName()) + " §eполучил лигу Серебро I в этом сезоне. Ваш приз: §d" + (int) (500 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков§e.");
                    PrisonPitPlayerManager.getPrisonPitPlayer(name).setShards(PrisonPitPlayerManager.getPrisonPitPlayer(name).getShards() + (int) (500 * (rank == 0 ? 1 : Math.pow(1.1, rank))));
                    break;
                }
                case 7: {
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Ваш клан " + ChatColor.translateAlternateColorCodes('&', getClanPlayer(player).getClanName()) + " §eполучил лигу Золото III в этом сезоне. Ваш приз: §d" + (int) (800 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков§e.");
                    PrisonPitPlayerManager.getPrisonPitPlayer(name).setShards(PrisonPitPlayerManager.getPrisonPitPlayer(name).getShards() + (int) (800 * (rank == 0 ? 1 : Math.pow(1.1, rank))));
                    break;
                }
                case 8: {
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Ваш клан " + ChatColor.translateAlternateColorCodes('&', getClanPlayer(player).getClanName()) + " §eполучил лигу Золото II в этом сезоне. Ваш приз: §d" + (int) (1250 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков§e.");
                    PrisonPitPlayerManager.getPrisonPitPlayer(name).setShards(PrisonPitPlayerManager.getPrisonPitPlayer(name).getShards() + (int) (1250 * (rank == 0 ? 1 : Math.pow(1.1, rank))));
                    break;
                }
                case 9: {
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Ваш клан " + ChatColor.translateAlternateColorCodes('&', getClanPlayer(player).getClanName()) + " §eполучил лигу Золото I в этом сезоне. Ваш приз: §d" + (int) (2500 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков§e.");
                    PrisonPitPlayerManager.getPrisonPitPlayer(name).setShards(PrisonPitPlayerManager.getPrisonPitPlayer(name).getShards() + (int) (2500 * (rank == 0 ? 1 : Math.pow(1.1, rank))));
                    break;
                }
                case 10: {
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Ваш клан " + ChatColor.translateAlternateColorCodes('&', getClanPlayer(player).getClanName()) + " §eполучил лигу Эмеральд III в этом сезоне. Ваш приз: §d" + (int) (3000 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков§e.");
                    PrisonPitPlayerManager.getPrisonPitPlayer(name).setShards(PrisonPitPlayerManager.getPrisonPitPlayer(name).getShards() + (int) (3000 * (rank == 0 ? 1 : Math.pow(1.1, rank))));
                    break;
                }
                case 11: {
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Ваш клан " + ChatColor.translateAlternateColorCodes('&', getClanPlayer(player).getClanName()) + " §eполучил лигу Эмеральд II в этом сезоне. Ваш приз: §d" + (int) (3500 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков§e.");
                    PrisonPitPlayerManager.getPrisonPitPlayer(name).setShards(PrisonPitPlayerManager.getPrisonPitPlayer(name).getShards() + (int) (3500 * (rank == 0 ? 1 : Math.pow(1.1, rank))));
                    break;
                }
                case 12: {
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Ваш клан " + ChatColor.translateAlternateColorCodes('&', getClanPlayer(player).getClanName()) + " §eполучил лигу Эмеральд I в этом сезоне. Ваш приз: §d" + (int) (5000 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков§e.");
                    PrisonPitPlayerManager.getPrisonPitPlayer(name).setShards(PrisonPitPlayerManager.getPrisonPitPlayer(name).getShards() + (int) (5000 * (rank == 0 ? 1 : Math.pow(1.1, rank))));
                    break;
                }
            }
            priseHashMap.remove(name);
        }
    }

    public static boolean hasClan(Player player) {
        for (Clan clan : clanList) {
            if (player.getName().equals(clan.getOwner())) {
                return true;
            }
            if (clan.getSoruks().contains(player.getName())) {
                return true;
            }
            if (clan.getStaricks().contains(player.getName())) {
                return true;
            }
            if (clan.getMembers().contains(player.getName())) {
                return true;
            }
        }
        return false;
    }

    public static Clan getClanPlayer(Player player) {
        for (Clan clan : clanList) {
            if (player.getName().equals(clan.getOwner())) {
                return clan;
            }
            if (clan.getSoruks().contains(player.getName())) {
                return clan;
            }
            if (clan.getStaricks().contains(player.getName())) {
                return clan;
            }
            if (clan.getMembers().contains(player.getName())) {
                return clan;
            }
        }
        return null;
    }

    public static void openClans(Player p) {
        if (hasClan(p)) {
            Clan clan = getClanPlayer(p);
            CustomInventory customInventory = new CustomInventory(ChatColor.translateAlternateColorCodes('&', clan.getClanName()));
            List<String> lore = new ArrayList<>();
            for (int i = 0; i < 54; i++) {
                customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
                customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                    public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                        return false;
                    }
                });
            }

            customInventory.addItem(10, new CustomItem(Material.CONCRETE, 1, (byte) 3).setName("§b§lЛига команды").setLore(lore).build());
            customInventory.addMenuClickHandler(10, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    openClanLeague(p.getName(), clan);
                    return false;
                }
            });

            lore.add("");
            lore.add("§cГлава");
            if (Bukkit.getServer().getPlayer(clan.getOwner()) != null && Bukkit.getServer().getPlayer(clan.getOwner()).isOnline()) {
                lore.add(" §aОнлайн");
            } else {
                lore.add(" §cОффлайн");
            }
            customInventory.addItem(13, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName(ChatColor.GREEN + clan.getOwner()).setSkullOwner(clan.getOwner()).setLore(lore).build());
            customInventory.addMenuClickHandler(13, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
            lore.clear();

            customInventory.addItem(16, new CustomItem(Material.BANNER).setName("§d§lКомандный вызов").setLore(lore).build());
            customInventory.addMenuClickHandler(16, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    p.closeInventory();
                    p.sendMessage("§csoon...");
                    return false;
                }
            });
            if (!clan.getSoruks().isEmpty()) {
                for (int i = 21; i < 21 + clan.getSoruks().size(); i++) {
                    lore.add("");
                    lore.add("§eСоруководитель");
                    if (Bukkit.getServer().getPlayer(clan.getSoruks().get(i - 21)) != null && Bukkit.getServer().getPlayer(clan.getSoruks().get(i - 21)).isOnline()) {
                        lore.add(" §aОнлайн");
                    } else {
                        lore.add(" §cОффлайн");
                    }
                    customInventory.addItem(i, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName(ChatColor.GREEN + clan.getSoruks().get(i - 21)).setSkullOwner(clan.getSoruks().get(i - 21)).setLore(lore).build());
                    int finalI = i;
                    customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (!p.getName().equals(clan.getSoruks().get(finalI - 21))) {
                                if (arg3.isRightClicked()) {
                                    if (clan.getStaricks().size() < 7) {
                                        if (clan.getOwner().equals(p.getName())) {
                                            deraisePlayer(clan.getSoruks().get(finalI - 21), p.getName(), clan);
                                        }
                                    } else {
                                        p.closeInventory();
                                        p.sendMessage("§7[§e§lPrison §6§lPit§7]§e В клане максимальное количество старейшин, для понижения необходимо иметь хотя-бы один слот старейшины");
                                    }
                                } else {
                                    if (clan.getOwner().equals(p.getName())) {
                                        transferClan(clan.getSoruks().get(finalI - 21), p.getName(), clan);
                                    }
                                }
                            } else {
                                leaveClan(clan.getSoruks().get(finalI - 21), clan);
                            }
                            return false;
                        }
                    });
                    lore.clear();
                }
            }

            if (!clan.getStaricks().isEmpty()) {
                for (int i = 28; i < 28 + clan.getStaricks().size(); i++) {
                    lore.add("");
                    lore.add("§9Старейшина");
                    if (Bukkit.getServer().getPlayer(clan.getStaricks().get(i - 28)) != null && Bukkit.getServer().getPlayer(clan.getStaricks().get(i - 28)).isOnline()) {
                        lore.add(" §aОнлайн");
                    } else {
                        lore.add(" §cОффлайн");
                    }
                    customInventory.addItem(i, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName(ChatColor.GREEN + clan.getStaricks().get(i - 28)).setSkullOwner(clan.getStaricks().get(i - 28)).setLore(lore).build());
                    int finalI = i;
                    customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (!p.getName().equals(clan.getStaricks().get(finalI - 28))) {
                                if (arg3.isRightClicked()) {
                                    if (clan.getMembers().size() < 9) {
                                        if (clan.getOwner().equals(p.getName()) || clan.getSoruks().contains(p.getName())) {
                                            deraisePlayer(clan.getStaricks().get(finalI - 28), p.getName(), clan);
                                        }
                                    } else {
                                        p.closeInventory();
                                        p.sendMessage("§7[§e§lPrison §6§lPit§7]§e В клане максимальное количество учасников, для понижения необходимо иметь хотя-бы один слот учасника");
                                    }
                                } else {
                                    if (clan.getSoruks().size() < 3) {
                                        if (clan.getOwner().equals(p.getName())) {
                                            raisePlayer(clan.getStaricks().get(finalI - 28), p.getName(), clan);
                                        }
                                    } else {
                                        p.closeInventory();
                                        p.sendMessage("§7[§e§lPrison §6§lPit§7]§e В клане максимальное количество соруководителей, для повышения необходимо иметь хотя-бы один слот соруководителя");
                                    }
                                }
                            } else {
                                leaveClan(clan.getStaricks().get(finalI - 28), clan);
                            }
                            return false;
                        }
                    });
                    lore.clear();
                }
            }

            if (!clan.getMembers().isEmpty()) {
                for (int i = 36; i < 36 + clan.getMembers().size(); i++) {
                    lore.add("");
                    lore.add("§7Участник");
                    if (Bukkit.getServer().getPlayer(clan.getMembers().get(i - 36)) != null && Bukkit.getServer().getPlayer(clan.getMembers().get(i - 36)).isOnline()) {
                        lore.add(" §aОнлайн");
                    } else {
                        lore.add(" §cОффлайн");
                    }
                    customInventory.addItem(i, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName(ChatColor.GREEN + clan.getMembers().get(i - 36)).setSkullOwner(clan.getMembers().get(i - 36)).setLore(lore).build());
                    int finalI = i;
                    customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (!p.getName().equals(clan.getMembers().get(finalI - 36))) {
                                if (arg3.isRightClicked()) {
                                    kickMember(clan.getMembers().get(finalI - 36), p.getName(), clan);
                                } else {
                                    if (clan.getOwner().equals(p.getName()) || clan.getSoruks().contains(p.getName())) {
                                        raisePlayer(clan.getMembers().get(finalI - 36), p.getName(), clan);
                                    }
                                }
                            } else {
                                leaveClan(clan.getMembers().get(finalI - 36), clan);
                            }
                            return false;
                        }
                    });
                    lore.clear();
                }
            }
            customInventory.addItem(49, new CustomItem(Material.DIODE).setName("§f§lНастройки клана").setLore(lore).build());
            customInventory.addMenuClickHandler(49, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    if (clan.getOwner().equals(p.getName()) || clan.getSoruks().contains(p.getName())) {
                        clanSettings(p.getName(), clan);
                    } else {
                        p.closeInventory();
                        p.sendMessage("§7[§e§lPrison §6§lPit§7]§c Только соруководители и глава клана могут изменять настройки клана.");
                    }
                    return false;
                }
            });
            customInventory.open(p);
        } else {
            PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(p.getName());
            CustomInventory customInventory = new CustomInventory("§b§lКланы");
            List<String> lore = new ArrayList<>();
            for (int i = 36; i < 45; i++) {
                customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
                customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                    public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                        return false;
                    }
                });
            }
            int i = 0;
            for (Clan clan : clanList) {
                if (prisonPitPlayer.getTotalStars() >= clan.getClanStarsEnter()) {
                    if (i < 36) {
                        lore.add("");
                        lore.add("§e§l Вступить в клан " + ChatColor.translateAlternateColorCodes('&', clan.getClanName()));
                        customInventory.addItem(i, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName(ChatColor.translateAlternateColorCodes('&', clan.getClanName()) + " §7(§e" + clan.getClanCount() + "/20§7)").setLore(lore).setSkullOwner(clan.getOwner()).build());
                        customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                                if (prisonPitPlayer.getTotalStars() >= clan.getClanStarsEnter()) {
                                    if (clan.getMembers().size() < 9) {
                                        p.closeInventory();
                                        clan.broadCastNotify("§7[" + ChatColor.translateAlternateColorCodes('&', clan.getClanName()) + "§7]§e Игрок " + p.getName() + " вступил в клан.");
                                        clan.updateInventories();
                                        p.sendMessage("§7[§e§lPrison §6§lPit§7]§e Вы вступили в клан " + clan.getClanName() + "§e!");
                                        if (!clan.getClanJoinMessage().equals("")) {
                                            sendClanMessage(p.getName(), clan);
                                        }
                                        clan.getMembers().add(p.getName());
                                    } else {
                                        p.closeInventory();
                                        p.sendMessage("§7[§e§lPrison §6§lPit§7]§e В клане " + ChatColor.translateAlternateColorCodes('&', clan.getClanName()) + " §eмаксимальное количество учасников, если занятых слотов не 20, попросите лидера клана повысить одного из учасников.");
                                    }
                                } else {
                                    p.closeInventory();
                                    p.sendMessage("§7[§e§lPrison §6§lPit§7]§e Клан " + ChatColor.translateAlternateColorCodes('&', clan.getClanName()) + " §eизменил необходимое количество звёзд для входа до " + clan.getClanStarsEnter() + ".");
                                }
                                return false;
                            }
                        });
                        lore.clear();
                        i++;
                    }
                }
            }
            customInventory.addItem(47, new CustomItem(Material.HOPPER).setName("§e§lНайти клан").setLore(lore).build());
            customInventory.addMenuClickHandler(47, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
            customInventory.addItem(49, new CustomItem(Material.FEATHER).setName("§e§lСоздать клан").setLore(lore).build());
            customInventory.addMenuClickHandler(49, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    if (!(prisonPitPlayer.getTotalStars() < 10)) {
                        if (!hasClan(p)) {
                            createClanMenu(p, "", "");
                        } else {
                            p.closeInventory();
                            p.sendMessage("§7[§e§lPrison §6§lPit§7]§c Вы уже состоите в клане!");
                        }
                    } else {
                        p.closeInventory();
                        p.sendMessage("§7[§e§lPrison §6§lPit§7]§c Создание клана доступно с 10 звезды!");
                    }
                    return false;
                }
            });
            customInventory.addItem(51, new CustomItem(Material.NETHER_STAR).setName("§e§lЛучшие кланы").setLore(lore).build());
            customInventory.addMenuClickHandler(51, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
            customInventory.open(p);
        }
    }

    public static void sendClanMessage(String p, Clan clan) {
        if (Bukkit.getServer().getPlayer(p) != null && Bukkit.getServer().getPlayer(p).isOnline()) {
            Player player = Bukkit.getServer().getPlayer(p);
            player.sendMessage("                                §7[" + ChatColor.translateAlternateColorCodes('&', clan.getClanName()) + "§7]");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', clan.getClanJoinMessage()));
            player.sendMessage("");
        }
    }

    public static void clanSettings(String p, Clan clan) {
        CustomInventory customInventory = new CustomInventory("§f§lНастройки клана " + clan.getClanName());
        List<String> lore = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }

        customInventory.addItem(10, new CustomItem(Material.PAPER).setName("§e§lИзменить приветственное сообщение").setLore(lore).build());
        customInventory.addMenuClickHandler(10, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                clanMessageChangeList.add(p);
                Bukkit.getServer().getPlayer(p).closeInventory();
                Bukkit.getServer().getPlayer(p).sendMessage("§7[§e§lPrison §6§lPit§7]§e Клановое сообщение должно содержать от 30 до 200 символов. Введите в чат клановое сообщение.");
                return false;
            }
        });

        customInventory.addItem(13, new CustomItem(Material.NETHER_STAR).setName("§e§lСменить необходимое кол-во звёзд для вступления в клан").setLore(lore).build());
        customInventory.addMenuClickHandler(13, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                clanStatsEnterChangeList.add(p);
                Bukkit.getServer().getPlayer(p).closeInventory();
                Bukkit.getServer().getPlayer(p).sendMessage("§7[§e§lPrison §6§lPit§7]§e Введите в чат количество звёзд.");
                return false;
            }
        });

        customInventory.addItem(16, new CustomItem(Material.ITEM_FRAME).setName("§e§lСменить тэг клана").setLore(lore).build());
        customInventory.addMenuClickHandler(16, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                if (clan.getOwner().equals(p)) {
                    tagRenameList.add(p);
                    Bukkit.getServer().getPlayer(p).closeInventory();
                    Bukkit.getServer().getPlayer(p).sendMessage("§7[§e§lPrison §6§lPit§7]§e Введите в чат тэг клана от 3 до 7 символов. Пример: &c&lOS - " + ChatColor.translateAlternateColorCodes('&', " &c&lOS"));
                } else {
                    Bukkit.getServer().getPlayer(p).closeInventory();
                    Bukkit.getServer().getPlayer(p).sendMessage("§7[§e§lPrison §6§lPit§7]§c Клановый тэг может менять только глава клана!");
                }
                return false;
            }
        });
        customInventory.open(Bukkit.getServer().getPlayer(p));
    }

    public static String ConvertTimeLeague(int hours) {
        return hours / 24 + "дн. " + hours % 24 + "ч.";
    }

    public static void openClanLeague(String p, Clan clan) {
        if (Bukkit.getServer().getPlayer(p) != null && Bukkit.getServer().getPlayer(p).isOnline()) {
            Player player = Bukkit.getServer().getPlayer(p);
            CustomInventory customInventory = new CustomInventory("§b§lЛига команды | §6§lДо конца §e§l- §a§l" + ConvertTimeLeague(hoursLeagueEnd));
            int rank = 0;
            int stars = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTotalStars();
            while (stars >= 10) {
                rank++;
                stars -= 5;
            }
            List<String> lore = new ArrayList<>();
            for (int i = 0; i < 54; i++) {
                customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
                customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                    public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                        return false;
                    }
                });
            }
            lore.clear();

            lore.add("");
            lore.add("§7Лига команды - сезонный ивент по командной добыче блоков.");
            lore.add("§7Сезон длится 7 дней. По окончанию сезона всем учасникам клана");
            lore.add("§7будут выданы призы в зависимости от того, сколько блоков накопал клан");
            customInventory.addItem(10, new CustomItem(Material.PAPER).setName("§f§lИнформация").setLore(lore).build());
            customInventory.addMenuClickHandler(10, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
            lore.clear();

            lore.add("");
            lore.add("§7Каждые 5 звёзд, начиная от 10 вы будете получать 1.1х бустер к наградам за лигу команды.");
            customInventory.addItem(37, new CustomItem(Material.NETHER_STAR).setName("§e§lДостигните " + (10 + (rank == 0 ? 0 : rank * 5)) + " звёзд чтобы получить в х1.1 раз больше осколков!").setLore(lore).build());
            customInventory.addMenuClickHandler(37, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
            lore.clear();

            lore.add("");
            lore.add("§6Необходимо - §92000 блоков");
            lore.add("§6Награда - §d" + (int) (5 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков");
            if (clan.getBlocks() >= 2000) {
                customInventory.addItem(50, new CustomItem(Material.CLAY_BRICK).setName("§eБронза III").setLore(lore).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
            } else {
                customInventory.addItem(50, new CustomItem(Material.CLAY_BRICK).setName("§eБронза III").setLore(lore).build());
            }
            customInventory.addMenuClickHandler(50, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
            lore.clear();

            lore.add("");
            lore.add("§6Необходимо - §910000 блоков");
            lore.add("§6Награда - §d" + (int) (10 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков");
            if (clan.getBlocks() >= 10000) {
                customInventory.addItem(41, new CustomItem(Material.CLAY_BRICK).setName("§eБронза II").setLore(lore).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
            } else {
                customInventory.addItem(41, new CustomItem(Material.CLAY_BRICK).setName("§eБронза II").setLore(lore).build());
            }
            customInventory.addMenuClickHandler(41, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
            lore.clear();

            lore.add("");
            lore.add("§6Необходимо - §9100000 блоков");
            lore.add("§6Награда - §d" + (int) (25 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков");
            if (clan.getBlocks() >= 100000) {
                customInventory.addItem(32, new CustomItem(Material.CLAY_BRICK).setName("§eБронза I").setLore(lore).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
            } else {
                customInventory.addItem(32, new CustomItem(Material.CLAY_BRICK).setName("§eБронза I").setLore(lore).build());
            }
            customInventory.addMenuClickHandler(32, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
            lore.clear();

            lore.add("");
            lore.add("§6Необходимо - §9200000 блоков");
            lore.add("§6Награда - §d" + (int) (100 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков");
            if (clan.getBlocks() >= 200000) {
                customInventory.addItem(23, new CustomItem(Material.IRON_INGOT).setName("§eСеребро III").setLore(lore).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
            } else {
                customInventory.addItem(23, new CustomItem(Material.IRON_INGOT).setName("§eСеребро III").setLore(lore).build());
            }
            customInventory.addMenuClickHandler(23, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
            lore.clear();

            lore.add("");
            lore.add("§6Необходимо - §9500000 блоков");
            lore.add("§6Награда - §d" + (int) (200 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков");
            if (clan.getBlocks() >= 500000) {
                customInventory.addItem(14, new CustomItem(Material.IRON_INGOT).setName("§eСеребро II").setLore(lore).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
            } else {
                customInventory.addItem(14, new CustomItem(Material.IRON_INGOT).setName("§eСеребро II").setLore(lore).build());
            }
            customInventory.addMenuClickHandler(14, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
            lore.clear();

            lore.add("");
            lore.add("§6Необходимо - §91000000 блоков");
            lore.add("§6Награда - §d" + (int) (500 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков");
            if (clan.getBlocks() >= 1000000) {
                customInventory.addItem(5, new CustomItem(Material.IRON_INGOT).setName("§eСеребро I").setLore(lore).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
            } else {
                customInventory.addItem(5, new CustomItem(Material.IRON_INGOT).setName("§eСеребро I").setLore(lore).build());
            }
            customInventory.addMenuClickHandler(5, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
            lore.clear();

            lore.add("");
            lore.add("§6Необходимо - §91500000 блоков");
            lore.add("§6Награда - §d" + (int) (800 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков");
            if (clan.getBlocks() >= 1500000) {
                customInventory.addItem(52, new CustomItem(Material.GOLD_INGOT).setName("§eЗолото III").setLore(lore).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
            } else {
                customInventory.addItem(52, new CustomItem(Material.GOLD_INGOT).setName("§eЗолото III").setLore(lore).build());
            }
            customInventory.addMenuClickHandler(52, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
            lore.clear();

            lore.add("");
            lore.add("§6Необходимо - §92000000 блоков");
            lore.add("§6Награда - §d" + (int) (1250 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков");
            if (clan.getBlocks() >= 2000000) {
                customInventory.addItem(43, new CustomItem(Material.GOLD_INGOT).setName("§eЗолото II").setLore(lore).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
            } else {
                customInventory.addItem(43, new CustomItem(Material.GOLD_INGOT).setName("§eЗолото II").setLore(lore).build());
            }
            customInventory.addMenuClickHandler(43, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
            lore.clear();

            lore.add("");
            lore.add("§6Необходимо - §92500000 блоков");
            lore.add("§6Награда - §d" + (int) (2500 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков");
            if (clan.getBlocks() >= 2500000) {
                customInventory.addItem(34, new CustomItem(Material.GOLD_INGOT).setName("§eЗолото I").setLore(lore).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
            } else {
                customInventory.addItem(34, new CustomItem(Material.GOLD_INGOT).setName("§eЗолото I").setLore(lore).build());
            }
            customInventory.addMenuClickHandler(34, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
            lore.clear();

            lore.add("");
            lore.add("§6Необходимо - §93000000 блоков");
            lore.add("§6Награда - §d" + (int) (3000 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков");
            if (clan.getBlocks() >= 3000000) {
                customInventory.addItem(25, new CustomItem(Material.EMERALD).setName("§eЭмеральд III").setLore(lore).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
            } else {
                customInventory.addItem(25, new CustomItem(Material.EMERALD).setName("§eЭмеральд III").setLore(lore).build());
            }
            customInventory.addMenuClickHandler(25, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
            lore.clear();

            lore.add("");
            lore.add("§6Необходимо - §94000000 блоков");
            lore.add("§6Награда - §d" + (int) (3500 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков");
            if (clan.getBlocks() >= 4000000) {
                customInventory.addItem(16, new CustomItem(Material.EMERALD).setName("§eЭмеральд II").setLore(lore).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
            } else {
                customInventory.addItem(16, new CustomItem(Material.EMERALD).setName("§eЭмеральд II").setLore(lore).build());
            }
            customInventory.addMenuClickHandler(16, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
            lore.clear();

            lore.add("");
            lore.add("§6Необходимо - §95000000 блоков");
            lore.add("§6Награда - §d" + (int) (5000 * (rank == 0 ? 1 : Math.pow(1.1, rank))) + " осколков");
            if (clan.getBlocks() >= 5000000) {
                customInventory.addItem(7, new CustomItem(Material.EMERALD).setName("§eЭмеральд I").setLore(lore).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
            } else {
                customInventory.addItem(7, new CustomItem(Material.EMERALD).setName("§eЭмеральд I").setLore(lore).build());
            }
            customInventory.addMenuClickHandler(7, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
            customInventory.open(player);
        }
    }


    public static void leaveClan(String p, Clan clan) {
        CustomInventory customInventory = new CustomInventory("§c§lПокинуть клан " + clan.getClanName() + "?");
        List<String> lore = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }

        customInventory.addItem(11, new CustomItem(Material.REDSTONE_BLOCK).setName("§cОтклонить").setLore(lore).build());
        customInventory.addMenuClickHandler(11, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                openClans(Bukkit.getServer().getPlayer(p));
                return false;
            }
        });

        customInventory.addItem(13, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName(ChatColor.GREEN + p).setSkullOwner(clan.getOwner()).setLore(lore).build());
        customInventory.addMenuClickHandler(13, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                return false;
            }
        });

        customInventory.addItem(15, new CustomItem(Material.EMERALD_BLOCK).setName("§aПодтвердить").setLore(lore).build());
        customInventory.addMenuClickHandler(15, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                clan.broadCastNotify("§7[" + ChatColor.translateAlternateColorCodes('&', clan.getClanName()) + "§7]§c Игрок " + p + " покинул клан.");
                if (clan.getStaricks().contains(p)) {
                    clan.getStaricks().remove(p);
                } else if (clan.getSoruks().contains(p)) {
                    clan.getSoruks().remove(p);
                } else if (clan.getMembers().contains(p)) {
                    clan.getMembers().remove(p);
                }
                clan.updateInventories();
                openClans(Bukkit.getServer().getPlayer(p));
                return false;
            }
        });
        customInventory.open(Bukkit.getServer().getPlayer(p));
    }

    public static void kickMember(String p, String deraiser, Clan clan) {
        CustomInventory customInventory = new CustomInventory("§c§lВыгнать игрока " + p + "?");
        List<String> lore = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }

        customInventory.addItem(11, new CustomItem(Material.REDSTONE_BLOCK).setName("§cОтклонить").setLore(lore).build());
        customInventory.addMenuClickHandler(11, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                openClans(Bukkit.getServer().getPlayer(deraiser));
                return false;
            }
        });

        customInventory.addItem(13, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName(ChatColor.GREEN + p).setSkullOwner(p).setLore(lore).build());
        customInventory.addMenuClickHandler(13, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                return false;
            }
        });

        customInventory.addItem(15, new CustomItem(Material.EMERALD_BLOCK).setName("§aПодтвердить").setLore(lore).build());
        customInventory.addMenuClickHandler(15, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                clan.broadCastNotify("§7[" + ChatColor.translateAlternateColorCodes('&', clan.getClanName()) + "§7]§c Игрок " + deraiser + " выгнал игрока " + p + " из клана.");
                clan.getMembers().remove(p);
                clan.updateInventories();
                openClans(Bukkit.getServer().getPlayer(deraiser));
                return false;
            }
        });
        customInventory.open(Bukkit.getServer().getPlayer(deraiser));
    }

    public static void transferClan(String p, String deraiser, Clan clan) {
        CustomInventory customInventory = new CustomInventory("§e§lПередать клан игроку " + p + "?");
        List<String> lore = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }

        customInventory.addItem(11, new CustomItem(Material.REDSTONE_BLOCK).setName("§cОтклонить").setLore(lore).build());
        customInventory.addMenuClickHandler(11, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                openClans(Bukkit.getServer().getPlayer(deraiser));
                return false;
            }
        });

        customInventory.addItem(13, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName(ChatColor.GREEN + p).setSkullOwner(p).setLore(lore).build());
        customInventory.addMenuClickHandler(13, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                return false;
            }
        });

        customInventory.addItem(15, new CustomItem(Material.EMERALD_BLOCK).setName("§aПодтвердить").setLore(lore).build());
        customInventory.addMenuClickHandler(15, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                clan.setOwner(p);
                clan.getSoruks().remove(p);
                clan.getSoruks().add(deraiser);
                clan.broadCastNotify("§7[" + ChatColor.translateAlternateColorCodes('&', clan.getClanName()) + "§7]§e Глава клана " + deraiser + " передал клан игроку " + p + ".");
                clan.updateInventories();
                openClans(Bukkit.getServer().getPlayer(deraiser));
                return false;
            }
        });
        customInventory.open(Bukkit.getServer().getPlayer(deraiser));
    }

    public static void deraisePlayer(String p, String deraiser, Clan clan) {
        CustomInventory customInventory = new CustomInventory("§c§lПонизить игрока " + p + "?");
        List<String> lore = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }

        customInventory.addItem(11, new CustomItem(Material.REDSTONE_BLOCK).setName("§cОтклонить").setLore(lore).build());
        customInventory.addMenuClickHandler(11, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                openClans(Bukkit.getServer().getPlayer(deraiser));
                return false;
            }
        });

        customInventory.addItem(13, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName(ChatColor.GREEN + p).setSkullOwner(p).setLore(lore).build());
        customInventory.addMenuClickHandler(13, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                return false;
            }
        });

        customInventory.addItem(15, new CustomItem(Material.EMERALD_BLOCK).setName("§aПодтвердить").setLore(lore).build());
        customInventory.addMenuClickHandler(15, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                if (clan.getSoruks().contains(p)) {
                    clan.getSoruks().remove(p);
                    clanStatsEnterChangeList.remove(p);
                    clanMessageChangeList.remove(p);
                    clan.getStaricks().add(p);
                } else if (clan.getStaricks().contains(p)) {
                    clan.getStaricks().remove(p);
                    clan.getMembers().add(p);
                }
                clan.broadCastNotify("§7[" + ChatColor.translateAlternateColorCodes('&', clan.getClanName()) + "§7]§c Игрок " + deraiser + " понизил игрока " + p + ".");
                clan.updateInventories();
                openClans(Bukkit.getServer().getPlayer(deraiser));
                return false;
            }
        });
        customInventory.open(Bukkit.getServer().getPlayer(deraiser));
    }

    public static void raisePlayer(String p, String raiser, Clan clan) {
        CustomInventory customInventory = new CustomInventory("§a§lПовысить игрока " + p + "?");
        List<String> lore = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }

        customInventory.addItem(11, new CustomItem(Material.REDSTONE_BLOCK).setName("§cОтклонить").setLore(lore).build());
        customInventory.addMenuClickHandler(11, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                openClans(Bukkit.getServer().getPlayer(raiser));
                return false;
            }
        });

        customInventory.addItem(13, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName(ChatColor.GREEN + p).setSkullOwner(p).setLore(lore).build());
        customInventory.addMenuClickHandler(13, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                return false;
            }
        });

        customInventory.addItem(15, new CustomItem(Material.EMERALD_BLOCK).setName("§aПодтвердить").setLore(lore).build());
        customInventory.addMenuClickHandler(15, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                if (clan.getMembers().contains(p)) {
                    clan.getMembers().remove(p);
                    clan.getStaricks().add(p);
                } else if (clan.getStaricks().contains(p)) {
                    clan.getStaricks().remove(p);
                    clan.getSoruks().add(p);
                }
                clan.updateInventories();
                clan.broadCastNotify("§7[" + ChatColor.translateAlternateColorCodes('&', clan.getClanName()) + "§7]§a Игрок " + raiser + " повысил игрока " + p + ".");
                openClans(Bukkit.getServer().getPlayer(raiser));
                return false;
            }
        });
        customInventory.open(Bukkit.getServer().getPlayer(raiser));
    }

    public static void LoadPrises() {
        MySQL.selectPrises();
        MySQL.clearPrises();
    }

    public static void SavePrises() {
        for (String name : priseHashMap.keySet()) {
            MySQL.SavePrise(name,priseHashMap.get(name));
        }
    }

    public static void createClanMenu(Player player, String name, String tag) {
        CustomInventory customInventory = new CustomInventory("§a§lСоздание клана");
        List<String> lore = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }
        if (!name.isEmpty()) {
            lore.add(name);
        }
        customInventory.addItem(2, new CustomItem(Material.SIGN).setName("§e§lЗадать имя клана").setLore(lore).build());
        customInventory.addMenuClickHandler(2, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                player.closeInventory();
                nameChangeList.add(player.getName());
                player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Введите в чат имя клана от 5 до 15 символов. Пример: &c&lOneSqd - " + ChatColor.translateAlternateColorCodes('&', "&c&lOneSqd"));
                return false;
            }
        });
        lore.clear();

        if (!tag.isEmpty()) {
            lore.add(tag);
        }
        customInventory.addItem(6, new CustomItem(Material.ITEM_FRAME).setName("§e§lЗадать тег клана").setLore(lore).build());
        customInventory.addMenuClickHandler(6, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                player.closeInventory();
                tagChangeList.add(player.getName());
                player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Введите в чат тэг клана от 3 до 7 символов. Пример: &c&lOS - " + ChatColor.translateAlternateColorCodes('&', "&c&lOS"));
                return false;
            }
        });
        lore.clear();

        lore.add("");
        if(clanDonate.contains(player.getName())){
            lore.add("§7Цена - §b§lБесплатно");
        }
        lore.add("§7Цена - §d§l10000 осколков");
        customInventory.addItem(22, new CustomItem(Material.FEATHER).setName("§e§lСоздать клан").setLore(lore).build());
        customInventory.addMenuClickHandler(22, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                if(clanDonate.contains(player.getName())){
                    createClan(player, name, tag);
                    player.closeInventory();
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Клан " + ChatColor.translateAlternateColorCodes('&', name) + "§e успешно создан!");
                    player.getInventory().setItem(4,new ItemStack(Material.AIR));
                    clanDonate.remove(player.getName());
                } else {
                    if (PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getShards() >= 10000) {
                        createClan(player, name, tag);
                        PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setShards(PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getShards() - 10000);
                        player.closeInventory();
                        player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Клан " + ChatColor.translateAlternateColorCodes('&', name) + "§e успешно создан!");
                    } else {
                        player.closeInventory();
                        player.sendMessage("§7[§e§lPrison §6§lPit§7]§c Недостаточно осколков!");
                    }
                }
                return false;
            }
        });
        lore.clear();
        customInventory.open(player);
    }

    public static void summonItem() {
        Location loc = new Location(Bukkit.getWorlds().get(0), 206.5, 53.5, -52.5);
        Location loctext = loc.clone();
        loctext.setY(53.3);
        WorldServer s = ((CraftWorld) loc.getWorld()).getHandle();
        entityText = loc.getWorld().spawn(loctext, ArmorStand.class);
        entityText.setCustomName("§b§lКланы");
        entityText.setVisible(false);
        entityText.setCustomNameVisible(true);
        entityText.setSmall(true);
        entityText.setGravity(false);
        entityItem = loc.getWorld().dropItem(loc, new ItemStack(Material.DIAMOND_SWORD));
        entityItem.setGravity(false);
        entityItem.setPickupDelay(32767);
        entityItem.setTicksLived(50000000);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entityItem.getLocation().equals(loc)) {
                    entityItem.teleport(loc);
                }
            }
        }.runTaskTimer(Main.instance, 1, 1);
    }

    public static void removeItem() {
        entityItem.remove();
        entityText.remove();
    }

    public static List<Clan> getClanList() {
        return clanList;
    }

    public static void createClan(Player p, String ClanName, String ClanTag) {
        clanList.add(new Clan(ClanName, ClanTag, p.getName(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "", 1, 0));
    }

    public static void saveClans() {
        for (Clan clan : clanList) {
            if (!MySQL.clanExists(clan.getClanName())) {
                MySQL.insertClan(clan.getClanName());
            }
            int totalClanStars = 0;
            totalClanStars += (int)MySQL.select(MySQL.prisonpit_players,clan.getOwner(),"totalStars");
            MySQL.updateClans(MySQL.clans_table, clan.getClanName(), "ClanTag", clan.getClanTag());
            MySQL.updateClans(MySQL.clans_table, clan.getClanName(), "Owner", clan.getOwner());
            MySQL.updateClans(MySQL.clans_table, clan.getClanName(), "clanJoinMessage", clan.getClanJoinMessage());
            MySQL.updateClans(MySQL.clans_table, clan.getClanName(), "clanStarsEnter", clan.getClanStarsEnter());
            MySQL.updateClans(MySQL.clans_table, clan.getClanName(), "blocks", clan.getBlocks());
            if (!(clan.getSoruks().isEmpty())) {
                StringBuilder soruks = new StringBuilder();
                for (String soruk : clan.getSoruks()) {
                    totalClanStars += (int)MySQL.select(MySQL.prisonpit_players,soruk,"totalStars");
                    soruks.append(soruk);
                    soruks.append(",");
                }
                MySQL.updateClans(MySQL.clans_table, clan.getClanName(), "Soruks", soruks.toString());
            } else {
                MySQL.updateClans(MySQL.clans_table, clan.getClanName(), "Soruks", "");
            }
            if (!(clan.getStaricks().isEmpty())) {
                StringBuilder staricks = new StringBuilder();
                for (String starick : clan.getStaricks()) {
                    totalClanStars += (int)MySQL.select(MySQL.prisonpit_players,starick,"totalStars");
                    staricks.append(starick);
                    staricks.append(",");
                }
                MySQL.updateClans(MySQL.clans_table, clan.getClanName(), "Staricks", staricks.toString());
            } else {
                MySQL.updateClans(MySQL.clans_table, clan.getClanName(), "Staricks", "");
            }
            if (!(clan.getMembers().isEmpty())) {
                StringBuilder members = new StringBuilder();
                for (String member : clan.getMembers()) {
                    totalClanStars += (int)MySQL.select(MySQL.prisonpit_players,member,"totalStars");
                    members.append(member);
                    members.append(",");
                }
                MySQL.updateClans(MySQL.clans_table, clan.getClanName(), "Members", members.toString());
            } else {
                MySQL.updateClans(MySQL.clans_table, clan.getClanName(), "Members", "");
            }
            MySQL.updateClans(MySQL.clans_table,clan.getClanName(),"totalClanStars", totalClanStars);
        }
        MySQL.update(MySQL.timer_table, "pit", "time", hoursLeagueEnd);
    }
}
