package fun.oneline.prisonpit.boosters;

import fun.oneline.api.math.Calc;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Booster {
    public static List<Booster> active = new ArrayList<Booster>();
    public static long TimingSendMessage;

    BoosterType type;
    long minutes;
    String owner;
    double multiplier;
    Date timeout;
    boolean infinite;
    List<String> thxPlayers;

    public Booster(BoosterType type, String owner, double multiplier, long minutes) {
        this.type = type;
        this.minutes = minutes;
        this.multiplier = multiplier;
        this.owner = owner;
        this.timeout = new Date(System.currentTimeMillis() + minutes * 60 * 1000);
        this.infinite = false;
        thxPlayers = new ArrayList<>();
    }

    public static List<Booster> getBoosters(String player, BoosterType type) {
        update();
        List<Booster> boosters = new ArrayList<Booster>();

        for (Booster booster : active) {
            if (booster.getAppliedPlayers().contains(player) && booster.getType().equals(type)) boosters.add(booster);
        }
        return boosters;
    }

    public static int getBoosterSizeTypeAntPr(String player, BoosterType type) {
        int count = 0;
        for (Booster booster : getBoosters(player, type)) {
            count++;
        }
        return count;
    }

    public static double getMultiplier(String name, BoosterType type) {
        double multiplier = 1.0;
        for (Booster booster : getBoosters(name, type)) {
            multiplier = multiplier + (booster.getMultiplier() - 1);
        }
        return Calc.fixDouble(multiplier, 2);
    }

    public static Iterator<Booster> iterate() {
        return active.iterator();
    }

    public static void update() {
        Iterator<Booster> boosters = Booster.iterate();
        while (boosters.hasNext()) {
            Booster booster = boosters.next();
            if (new Date().after(booster.getDeadLine())) {
                boosters.remove();
                booster.deactive();
            }
        }
    }

    public List<String> getAppliedPlayers() {
        List<String> players = new ArrayList<String>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            players.add(p.getName());
        }
        return players;
    }

    public boolean isThxPlayer(Player player) {
        return thxPlayers.contains(player.getName());
    }

    public void addPlayersThx(Player player) {
        thxPlayers.add(player.getName());
    }

    public BoosterType getType() {
        return this.type;
    }

    public long formatTime() {
        return (getDeadLine().getTime() - System.currentTimeMillis()) / 60000L;
    }

    public void active2() {
        active.add(this);
    }

    public Double getMultiplier() {
        return this.multiplier;
    }

    public ChatColor getColor() {
        return type == BoosterType.SHARDS ? ChatColor.LIGHT_PURPLE : ChatColor.GREEN;
    }

    public long getBoosterSec() {
        return (getDeadLine().getTime() - System.currentTimeMillis()) / 1000L;
    }

    public void active() {
        active.add(this);
        Bukkit.broadcastMessage("§7[§e§lPrison §6§lPit§7]§e " + owner + " активировал глобальный бустер х" + multiplier + " " + getUniqName() + " на " + getDuration() + " минут!");
        Bukkit.broadcastMessage("§7[§e§lPrison §6§lPit§7]§a/thx поблагодарить");
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
        }
    }

    public void deactive() {
        Bukkit.broadcastMessage(ChatColor.YELLOW + "Глобальный бустер " + getUniqName() + " от игрока " + owner + " окончен!");
        active.remove(this);
    }

    public String getUniqName() {
        return type == BoosterType.MONEY ? "денег " : "осколков ";
    }

    public long getDuration() {
        return this.minutes;
    }

    public String getOwner() {
        return owner;
    }

    public String timeConvert(long time) {
        StringBuilder text = new StringBuilder();

        long j = time / (24 * 60);
        long h = (time % (24 * 60)) / 60;
        long m = (time % (24 * 60)) % 60;

        if (j != 0) {
            text.append(j + " д. ");
        }

        if (h != 0) {
            text.append(h + " ч. ");
        }

        if (m != 0) {
            text.append(m + " м.");
        }

        if (j == 0 && h == 0 && m == 0) {
            text.append("0 м.");
        }
        return text.toString();
    }

    public Date getDeadLine() {
        return this.timeout;
    }
}
