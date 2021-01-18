package fun.oneline.prisonpit;

import com.comphenix.protocol.ProtocolLibrary;
import fun.oneline.api.configs.Configs;
import fun.oneline.api.configs.CustomConfig;
import fun.oneline.prisonpit.board.Board;
import fun.oneline.prisonpit.board.BoardUpdater;
import fun.oneline.prisonpit.boosters.Booster;
import fun.oneline.prisonpit.boosters.BoosterType;
import fun.oneline.prisonpit.boosters.BoosterUpdater;
import fun.oneline.prisonpit.clans.ClanManager;
import fun.oneline.prisonpit.commands.*;
import fun.oneline.prisonpit.database.MySQL;
import fun.oneline.prisonpit.handlers.*;
import fun.oneline.prisonpit.items.Pickaxes;
import fun.oneline.prisonpit.leaderboards.LeaderBoards;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import fun.oneline.prisonpit.shaft.PacketListener;
import fun.oneline.prisonpit.shaft.ShaftGenerator;
import fun.oneline.prisonpit.tokens.AutoBlocks;
import fun.oneline.prisonpit.tokens.TokenManager;
import fun.oneline.prisonpit.utils.HoldersAPI;
import net.minecraft.server.v1_12_R1.PacketPlayInBlockDig;
import net.minecraft.server.v1_12_R1.PacketPlayInUseItem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.packetlistener.PacketListenerAPI;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;
import ru.dream.network.core.DreamNetworkCoreBukkit;

import java.io.File;
import java.sql.SQLException;

import static net.minecraft.server.v1_12_R1.PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK;

public class Main extends JavaPlugin {

    public static Main instance;
    private static Configs configs;

    @Override
    public void onEnable() {
        instance = this;
        configs = new Configs(this.getDataFolder().getParent() + File.separator + this.getName());
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new HoldersAPI(this).register();
        }
        new MySQL("localhost", "PrisonPit", "root", "E9wzPzAuc9bpEHgm"); // main
        loadListener(this);
        Board.setAnimText();
        Pickaxes.LoadPickAxes();
        PrisonPitPlayerManager.paidMoneyEverySec();
        PrisonPitPlayerManager.updateStormBossbar();
        onBrickASClick.paidBricks();
        TokenManager.startTimer();
        new BukkitRunnable() {
            @Override
            public void run() {
                PrisonPitPlayerManager.paidShardsFromBoosterShards();
            }
        }.runTaskTimer(this, 100, 100);
        new BoardUpdater().runTaskTimer(this, 0, 2);
        new ShaftGenerator().runTaskTimer(this, 5, 1200);
        new AutoBlocks().runTaskTimer(this, 3, 3);
        new BoosterUpdater().runTaskTimer(this, 0, 20);
        new LeaderBoards(this);
        unEnableUpdateBlocks();
        onGoldBlockASClick.summonGBAS(new Location(Bukkit.getWorlds().get(0), 120.5, 54, 3.5));
        onBrickASClick.summonBAS(new Location(Bukkit.getWorlds().get(0), 143.5, 54, -126.5));
        ClanManager.loadClans();
        ClanManager.LoadPrises();
        loadBoosters();
    }

    @Override
    public void onDisable() {
        for (Booster booster : Booster.active) {
            createBoosterFile(booster.getOwner(), booster.getType().toString(), (int) booster.formatTime(), booster.getMultiplier());
        }
        ClanManager.SavePrises();
        onGoldBlockASClick.removeGBAS();
        onBrickASClick.removeBAS();
        ClanManager.removeItem();
        ClanManager.saveClans();
        for (Player player : Bukkit.getOnlinePlayers()) {
            PrisonPitPlayerManager.save(player);
        }
        try {
            MySQL.conn.close();
            MySQL.statmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createBoosterFile(String name, String type, int time, double multiplier) {
        CustomConfig cfg = configs.get("boosters");
        System.out.println("Save Booster " + name);
        cfg.saveCfg();
        int uid = 0;
        for (int i = 0; i < 1000; ++i) {
            if (!cfg.getCfg().contains("boosters." + i)) {
                uid = i;
                break;
            }
        }

        cfg.getCfg().set("boosters." + uid + ".owner", name);
        cfg.getCfg().set("boosters." + uid + ".type", type);
        cfg.getCfg().set("boosters." + uid + ".minutes", time);
        cfg.getCfg().set("boosters." + uid + ".multiplier", multiplier);
        cfg.saveCfg();
    }

    public void loadListener(Main plugin) {
        new onPlayerChat(plugin);
        new onEntityInteractWithEntity(plugin);
        new onPlayerPickup(plugin);
        new onPlayerJoin(plugin);
        new onPlayerQuit(plugin);
        new onBreak(plugin);
        new onInventoryClick(plugin);
        new onNPCClick(plugin);
        new onGoldBlockASClick(plugin);
        new onBrickASClick(plugin);
        new onBlockDamage(plugin);
        new onInteractWithBlock(plugin);
        new onCraft(plugin);
        new onPlayerInteract(plugin);
        new TokenCommand().register();
        new StuckCommand().register();
        getCommand("pay").setExecutor(new PayCommand());
        new HideCommand().register();
        new ThxCommand().register();
        DreamNetworkCoreBukkit.getInstance().getCommand("donate").setExecutor(new DonateCommand());
        new BoostersCommand().register();
    }

    public void loadBoosters() {
        CustomConfig cfg = configs.get("boosters");
        if (cfg.getCfg().getConfigurationSection("boosters") != null) {
            for (String boost : cfg.getCfg().getConfigurationSection("boosters").getKeys(false)) {
                String owner = cfg.getCfg().getString("boosters." + boost + ".owner");
                BoosterType type = BoosterType.valueOf(cfg.getCfg().getString("boosters." + boost + ".type"));
                int minute = cfg.getCfg().getInt("boosters." + boost + ".minutes");
                double multiplier = cfg.getCfg().getDouble("boosters." + boost + ".multiplier");
                Booster booster = new Booster(type, owner, multiplier, minute);
                booster.active2();
                cfg.getCfg().set("boosters." + boost, null);
            }
            cfg.saveCfg();
        }
    }

    public void unEnableUpdateBlocks() {
        PacketListenerAPI.addPacketHandler(new PacketHandler() {
            @Override
            public void onSend(SentPacket packet) {
            }

            @Override
            public void onReceive(ReceivedPacket packet) {
                if (packet.getPlayer() != null) {
                    if (packet.getPlayer().getGameMode() != GameMode.CREATIVE) {
                        if (packet.getPacket() instanceof PacketPlayInBlockDig) {
                            PacketPlayInBlockDig p2 = (PacketPlayInBlockDig) packet.getPacket();
                            if (p2.c().equals(ABORT_DESTROY_BLOCK)) {
                                packet.setCancelled(true);
                            }
                        }
                        if (packet.getPacket() instanceof PacketPlayInUseItem) {
                            packet.setCancelled(true);
                        }
                    }
                }
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketListener());
    }
}
