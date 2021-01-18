package fun.oneline.prisonpit.handlers;

import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.board.Board;
import fun.oneline.prisonpit.clans.Clan;
import fun.oneline.prisonpit.clans.ClanManager;
import fun.oneline.prisonpit.items.Menu;
import fun.oneline.prisonpit.items.Pickaxes;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import fun.oneline.prisonpit.shaft.ShaftUpdaterForSinglePlayer;
import fun.oneline.prisonpit.tokens.TokenManager;
import fun.oneline.prisonpit.utils.Hider;
import fun.oneline.prisonpit.utils.Utils;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import ru.dream.network.core.service.account.PlayerProfileLoadedEvent;
import ru.dream.network.core.service.account.player.TagProfile;
import ru.dream.network.core.service.server.IServerService;

public class onPlayerJoin implements Listener {

    public onPlayerJoin(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private static void restoreItems(Player player) {
        if (player.getInventory().getItem(0) != null) {
            ItemStack pickaxe = player.getInventory().getItem(0);
            ItemMeta pickaxemeta = pickaxe.getItemMeta();
            String level = "";
            for (String str : pickaxemeta.getLore()) {
                if (str.contains("Уровень")) {
                    level = str.split(" ")[1];
                }
            }
            if (!String.valueOf(PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getPickaxe_level()).equals(level)) {
                player.getInventory().getItem(0).setType(Material.AIR);
                player.getInventory().setItem(0, Pickaxes.getPickaxe(PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getPickaxe_level()));
            }
        } else {
            player.getInventory().setItem(0, Pickaxes.getPickaxe(PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getPickaxe_level()));
        }

        if (player.getInventory().getItem(8) == null) {
            player.getInventory().setItem(8, Menu.getMenu());
        }
        ItemStack token = new ItemStack(Material.FIREWORK_CHARGE, PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTokens());
        ItemMeta meta = token.getItemMeta();
        meta.setDisplayName("§b§lТокен");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        FireworkEffectMeta metaFw = (FireworkEffectMeta) meta;
        FireworkEffect aa = FireworkEffect.builder().withColor(Color.AQUA).build();
        metaFw.setEffect(aa);
        token.setItemMeta(metaFw);

        player.getInventory().setItem(7, token);
    }

    private static void restoreBonuses(Player player) {
        switch (PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getActiveBonus()) {
            case "Больше денег": {
                TokenManager.moreMoney.add(player);
                TokenManager.TimerMap.put(player, PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTimeToEndBonus());
                PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setActiveBonus("Больше денег");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        TokenManager.moreMoney.remove(player);
                        TokenManager.TimerMap.remove(player);
                        PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setActiveBonus("");
                    }
                }.runTaskLater(Main.instance, PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTimeToEndBonus() * 20);
                break;
            }
            case "Автоматические блоки": {
                TokenManager.autoBlocks.add(player);
                TokenManager.TimerMap.put(player, PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTimeToEndBonus());
                PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setActiveBonus("Автоматические блоки");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        TokenManager.autoBlocks.remove(player);
                        TokenManager.TimerMap.remove(player);
                        PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setActiveBonus("");
                    }
                }.runTaskLater(Main.instance, PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTimeToEndBonus() * 20);
                break;
            }
            case "Больше осколков": {
                TokenManager.moreShards.add(player);
                TokenManager.TimerMap.put(player, PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTimeToEndBonus());
                PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setActiveBonus("Больше осколков");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        TokenManager.moreShards.remove(player);
                        TokenManager.TimerMap.remove(player);
                        PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setActiveBonus("");
                    }
                }.runTaskLater(Main.instance, PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTimeToEndBonus() * 20);
                break;
            }
            case "Быстрая кирка": {
                TokenManager.fastPickaxe.add(player);
                TokenManager.TimerMap.put(player, PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTimeToEndBonus());
                PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setActiveBonus("Быстрая кирка");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        TokenManager.fastPickaxe.remove(player);
                        TokenManager.TimerMap.remove(player);
                        PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setActiveBonus("");
                    }
                }.runTaskLater(Main.instance, PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTimeToEndBonus() * 20);
                break;
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if(e.getMessage().contains("/pay")) {
            e.setCancelled(true);
            e.getPlayer().chat(e.getMessage().replace("/pay" , "/prisonpit:pay"));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerProfileLoadedEvent event) {
        Hider.join(event.getPlayer());
        event.getJoinEvent().setJoinMessage(null);
        Player player = event.getPlayer();
        PrisonPitPlayerManager.LoadPrisonPitPlayer(player);
        restoreBonuses(player);
        restoreItems(player);
        Board.setScoreBoard(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                ShaftUpdaterForSinglePlayer.update(player);
            }
        }.runTaskLater(Main.instance, 40);
        new BukkitRunnable(){
            @Override
            public void run() {
                player.teleport(new Location(Bukkit.getWorlds().get(0), 117.5, 52.1, -103.5,-45,0));
                if(PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTotalStars() >= 15){
                    onBrickASClick.sendbricksbalance(player);
                    onBrickASClick.sendbrickspersec(player);
                }
                if(PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTotalStars() >= 4){
                    onGoldBlockASClick.sendLevelArmorStand(player);
                    if(PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTotalStars() >= 10){
                        onGoldBlockASClick.sendV2(player);
                    }
                }
            }
        }.runTaskLater(Main.instance,4);
        if(ClanManager.hasClan(player)){
            Clan clan = ClanManager.getClanPlayer(player);
            if(!clan.getClanJoinMessage().equals("")) {
                ClanManager.sendClanMessage(player.getName(), clan);
            }
        }
        if(ClanManager.priseHashMap.containsKey(event.getPlayer().getName())){
            ClanManager.givePriseForLeague(event.getPlayer().getName());
        }
        TagProfile profile = event.getProfile();
        profile.setHeader("\n§e§lPrison §6§lPit\n");
        profile.setFooter(p -> "\n§6§lСайт §7store.dreamnw.ru\n§6§lОбщий онлайн §7" +
                IServerService.get().getServer("bungee").getOnline());
        profile.setTagSuffix(p -> " " + Utils.getStars(p));
    }
}
