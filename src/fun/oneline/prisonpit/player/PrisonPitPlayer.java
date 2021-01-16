package fun.oneline.prisonpit.player;

import fun.oneline.prisonpit.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class PrisonPitPlayer {
    private Player player;
    private String name;
    private double money;
    private double moneypersec;
    private int timetostorm;
    private int blocks;
    private int shards;
    private int points;
    private int tokens;
    private double goldblocks;
    private double nonspendmoney;


    private int pickaxe_level;
    private int boosterMoney_level;
    private int shardsFromBlock_level;
    private int doubleBlock_level;
    private int boosterMoneyII_level;
    private int storm_level;

    private int levelBoost_level;
    private int boostershards_level;
    private int boostergoldblocks_level;
    private int boosterprocentgoldblocks_level;
    private int morestorms_level;
    private int token_level;
    private BossBar stormbossbar;

    private int[] stars;

    private String activeBonus;
    private int timeToEndBonus;

    private int maxBoosterMoney_level;
    private int totalStars;

    private int goldblockroom_level;
    private int goldblockroom_points;

    private double fragments;

    private double bricks;
    private double brickspersec;
    private int bricklevel;

    private int moreFragments_level;
    private int moreMagnets_level;
    private int GBDiscound_level;
    private int moreMagnetsII_level;
    private int moreMoney_level;
    private int moreMagnetsIII_level;
    private int moreBricks_level;

    private int stageq;
    private int numpriv;

    public PrisonPitPlayer(Player player, double money, double moneypersec, int timetostorm, int blocks, int shards, int points, int tokens, double goldblocks, double nonspendmoney, int pickaxe_level, int boosterMoney_level, int shardsFromBlock_level, int doubleBlock_level, int boosterMoneyII_level, int storm_level, int levelBoost_level, int boostershards_level, int boostergoldblocks_level, int boosterprocentgoldblocks_level, int morestorms_level, int token_level, int[] stars, String activeBonus, int timeToEndBonus, int maxBoosterMoney_level, int totalStars, int goldblockroom_level, int goldblockroom_points, double fragments, double bricks, double brickspersec, int bricklevel, int moreFragments_level, int moreMagnets_level, int GBDiscound_level, int moreMagnetsII_level, int moreMoney_level, int moreMagnetsIII_level, int moreBricks_level, int stageq) {
        this.player = player;
        this.name = player.getName();
        this.money = money;
        this.moneypersec = moneypersec;
        this.timetostorm = timetostorm;
        this.blocks = blocks;
        this.shards = shards;
        this.points = points;
        this.tokens = tokens;
        this.goldblocks = goldblocks;
        this.nonspendmoney = nonspendmoney;
        this.pickaxe_level = pickaxe_level;
        this.boosterMoney_level = boosterMoney_level;
        this.shardsFromBlock_level = shardsFromBlock_level;
        this.doubleBlock_level = doubleBlock_level;
        this.boosterMoneyII_level = boosterMoneyII_level;
        this.storm_level = storm_level;
        this.levelBoost_level = levelBoost_level;
        this.boostershards_level = boostershards_level;
        this.boostergoldblocks_level = boostergoldblocks_level;
        this.boosterprocentgoldblocks_level = boosterprocentgoldblocks_level;
        this.morestorms_level = morestorms_level;
        this.stormbossbar = Bukkit.createBossBar((ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "До шторма " + String.format("%d:%02d", timetostorm / 60, timetostorm % 60)), BarColor.BLUE, BarStyle.SOLID);
        this.stormbossbar.setProgress(Math.max(Math.min(1.0 - ((double) timetostorm / (360.0 - (morestorms_level * 6.0))), 1.0), 0.0));
        this.token_level = token_level;
        this.stars = stars;
        this.activeBonus = activeBonus;
        this.timeToEndBonus = timeToEndBonus;
        this.maxBoosterMoney_level = maxBoosterMoney_level;
        this.totalStars = totalStars;
        this.goldblockroom_level = goldblockroom_level;
        this.goldblockroom_points = goldblockroom_points;
        this.fragments = fragments;
        this.bricks = bricks;
        this.brickspersec = brickspersec;
        this.bricklevel = bricklevel;
        this.moreFragments_level = moreFragments_level;
        this.moreMagnets_level = moreMagnets_level;
        this.GBDiscound_level = GBDiscound_level;
        this.moreMagnetsII_level = moreMagnetsII_level;
        this.moreMoney_level = moreMoney_level;
        this.moreMagnetsIII_level = moreMagnetsIII_level;
        this.moreBricks_level = moreBricks_level;
        this.stageq = stageq;
        this.numpriv = Utils.getDonateNum(name);
    }

    public int getNumpriv() {
        return numpriv;
    }

    public int getStageq() {
        return stageq;
    }

    public void setStageq(int stageq) {
        this.stageq = stageq;
    }

    public int getGBDiscound_level() {
        return GBDiscound_level;
    }

    public void setGBDiscound_level(int GBDiscound_level) {
        this.GBDiscound_level = GBDiscound_level;
    }

    public int getMoreBricks_level() {
        return moreBricks_level;
    }

    public void setMoreBricks_level(int moreBricks_level) {
        this.moreBricks_level = moreBricks_level;
    }

    public int getMoreFragments_level() {
        return moreFragments_level;
    }

    public void setMoreFragments_level(int moreFragments_level) {
        this.moreFragments_level = moreFragments_level;
    }

    public int getMoreMagnets_level() {
        return moreMagnets_level;
    }

    public void setMoreMagnets_level(int moreMagnets_level) {
        this.moreMagnets_level = moreMagnets_level;
    }

    public int getMoreMagnetsII_level() {
        return moreMagnetsII_level;
    }

    public void setMoreMagnetsII_level(int moreMagnetsII_level) {
        this.moreMagnetsII_level = moreMagnetsII_level;
    }

    public int getMoreMagnetsIII_level() {
        return moreMagnetsIII_level;
    }

    public void setMoreMagnetsIII_level(int moreMagnetsIII_level) {
        this.moreMagnetsIII_level = moreMagnetsIII_level;
    }

    public int getMoreMoney_level() {
        return moreMoney_level;
    }

    public void setMoreMoney_level(int moreMoney_level) {
        this.moreMoney_level = moreMoney_level;
    }

    public double getBricks() {
        return bricks;
    }

    public void setBricks(double bricks) {
        this.bricks = bricks;
    }

    public double getBrickspersec() {
        return brickspersec;
    }

    public void setBrickspersec(double brickspersec) {
        this.brickspersec = brickspersec;
    }

    public int getBricklevel() {
        return bricklevel;
    }

    public void setBricklevel(int bricklevel) {
        this.bricklevel = bricklevel;
    }

    public double getFragments() {
        return fragments;
    }

    public void setFragments(double fragments) {
        this.fragments = fragments;
    }

    public int getGoldblockroom_level() {
        return goldblockroom_level;
    }

    public void setGoldblockroom_level(int goldblockroom_level) {
        this.goldblockroom_level = goldblockroom_level;
    }

    public int getGoldblockroom_points() {
        return goldblockroom_points;
    }

    public void setGoldblockroom_points(int goldblockroom_points) {
        this.goldblockroom_points = goldblockroom_points;
    }

    public int getTotalStars() {
        return totalStars;
    }

    public void setTotalStars(int totalStars) {
        this.totalStars = totalStars;
    }

    public int getMaxBoosterMoney_level() {
        return maxBoosterMoney_level;
    }

    public void setMaxBoosterMoney_level(int maxBoosterMoney_level) {
        this.maxBoosterMoney_level = maxBoosterMoney_level;
    }

    public int getTimeToEndBonus() {
        return timeToEndBonus;
    }

    public void setTimeToEndBonus(int timeToEndBonus) {
        this.timeToEndBonus = timeToEndBonus;
    }

    public String getActiveBonus() {
        return activeBonus;
    }

    public void setActiveBonus(String activeBonus) {
        this.activeBonus = activeBonus;
    }

    public int[] getStars() {
        return stars;
    }

    public void setStars(int[] stars) {
        this.stars = stars;
    }

    public double getGoldblocks() {
        return goldblocks;
    }

    public void setGoldblocks(double goldblocks) {
        this.goldblocks = goldblocks;
    }

    public double getNonspendmoney() {
        return nonspendmoney;
    }

    public void setNonspendmoney(double nonspendmoney) {
        this.nonspendmoney = nonspendmoney;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public int getToken_level() {
        return token_level;
    }

    public void setToken_level(int token_level) {
        this.token_level = token_level;
    }

    public BossBar getStormbossbar() {
        return stormbossbar;
    }

    public void setStormbossbar(BossBar stormbossbar) {
        this.stormbossbar = stormbossbar;
    }

    public int getTimetostorm() {
        return timetostorm;
    }

    public void setTimetostorm(int timetostorm) {
        this.timetostorm = timetostorm;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getShards() {
        return shards;
    }

    public void setShards(int shards) {
        this.shards = shards;
    }

    public double getMoneypersec() {
        return moneypersec;
    }

    public void setMoneypersec(double moneypersec) {
        this.moneypersec = moneypersec;
    }

    public int getBlocks() {
        return blocks;
    }

    public void setBlocks(int blocks) {
        this.blocks = blocks;
    }

    public int getBoostergoldblocks_level() {
        return boostergoldblocks_level;
    }

    public void setBoostergoldblocks_level(int boostergoldblocks_level) {
        this.boostergoldblocks_level = boostergoldblocks_level;
    }

    public int getBoosterMoney_level() {
        return boosterMoney_level;
    }

    public void setBoosterMoney_level(int boosterMoney_level) {
        this.boosterMoney_level = boosterMoney_level;
    }

    public int getBoosterMoneyII_level() {
        return boosterMoneyII_level;
    }

    public void setBoosterMoneyII_level(int boosterMoneyII_level) {
        this.boosterMoneyII_level = boosterMoneyII_level;
    }

    public int getBoosterprocentgoldblocks_level() {
        return boosterprocentgoldblocks_level;
    }

    public void setBoosterprocentgoldblocks_level(int boosterprocentgoldblocks_level) {
        this.boosterprocentgoldblocks_level = boosterprocentgoldblocks_level;
    }

    public int getBoostershards_level() {
        return boostershards_level;
    }

    public void setBoostershards_level(int boostershards_level) {
        this.boostershards_level = boostershards_level;
    }

    public int getDoubleBlock_level() {
        return doubleBlock_level;
    }

    public void setDoubleBlock_level(int doubleBlock_level) {
        this.doubleBlock_level = doubleBlock_level;
    }

    public int getLevelBoost_level() {
        return levelBoost_level;
    }

    public void setLevelBoost_level(int levelBoost_level) {
        this.levelBoost_level = levelBoost_level;
    }

    public int getMorestorms_level() {
        return morestorms_level;
    }

    public void setMorestorms_level(int morestorms_level) {
        this.morestorms_level = morestorms_level;
    }

    public int getPickaxe_level() {
        return pickaxe_level;
    }

    public void setPickaxe_level(int pickaxe_level) {
        this.pickaxe_level = pickaxe_level;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getShardsFromBlock_level() {
        return shardsFromBlock_level;
    }

    public void setShardsFromBlock_level(int shardsFromBlock_level) {
        this.shardsFromBlock_level = shardsFromBlock_level;
    }

    public int getStorm_level() {
        return storm_level;
    }

    public void setStorm_level(int storm_level) {
        this.storm_level = storm_level;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
