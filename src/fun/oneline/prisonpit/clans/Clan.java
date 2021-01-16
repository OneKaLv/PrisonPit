package fun.oneline.prisonpit.clans;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;

public class Clan {
    private String ClanName;
    private String ClanTag;
    private String Owner;
    private List<String> Soruks;
    private List<String> Staricks;
    private List<String> Members;
    private String clanJoinMessage;
    private int clanStarsEnter;
    private double blocks;

    public Clan(String ClanName, String ClanTag, String Owner, List<String> Soruks, List<String> Staricks, List<String> Members, String clanJoinMessage,int clanStarsEnter,double blocks) {
        this.ClanName = ClanName;
        this.ClanTag = ClanTag;
        this.Owner = Owner;
        this.Soruks = Soruks;
        this.Staricks = Staricks;
        this.Members = Members;
        this.clanJoinMessage = clanJoinMessage;
        this.clanStarsEnter = clanStarsEnter;
        this.blocks = blocks;
    }

    public void setBlocks(double blocks) {
        this.blocks = blocks;
    }

    public double getBlocks() {
        return blocks;
    }

    public int getClanStarsEnter() {
        return clanStarsEnter;
    }

    public void setClanStarsEnter(int clanStarsEnter) {
        this.clanStarsEnter = clanStarsEnter;
    }

    public String getClanJoinMessage() {
        return clanJoinMessage;
    }

    public void setClanJoinMessage(String clanJoinMessage) {
        this.clanJoinMessage = clanJoinMessage;
    }

    public int getClanCount() {
        return 1 + Soruks.size() + Staricks.size() + Members.size();
    }

    public void updateInventories() {
        if (Bukkit.getServer().getPlayer(Owner) != null && Bukkit.getServer().getPlayer(Owner).isOnline()) {
            if (Bukkit.getServer().getPlayer(Owner).getOpenInventory() != null && Bukkit.getServer().getPlayer(Owner).getOpenInventory().getTitle().contains(ChatColor.translateAlternateColorCodes('&', ClanName)) || Bukkit.getServer().getPlayer(Owner).getOpenInventory().getTitle().contains("игрок")) {
                ClanManager.openClans(Bukkit.getServer().getPlayer(Owner));
            }
        }
        if (!Soruks.isEmpty()) {
            for (String name : Soruks) {
                if (Bukkit.getServer().getPlayer(name) != null && Bukkit.getServer().getPlayer(name).isOnline()) {
                    if (Bukkit.getServer().getPlayer(name).getOpenInventory() != null && Bukkit.getServer().getPlayer(name).getOpenInventory().getTitle().contains(ChatColor.translateAlternateColorCodes('&', ClanName)) || Bukkit.getServer().getPlayer(name).getOpenInventory().getTitle().contains("игрок")) {
                        ClanManager.openClans(Bukkit.getServer().getPlayer(name));
                    }
                }
            }
        }
        if (!Staricks.isEmpty()) {
            for (String name : Staricks) {
                if (Bukkit.getServer().getPlayer(name) != null && Bukkit.getServer().getPlayer(name).isOnline()) {
                    if (Bukkit.getServer().getPlayer(name).getOpenInventory() != null && Bukkit.getServer().getPlayer(name).getOpenInventory().getTitle().contains(ChatColor.translateAlternateColorCodes('&', ClanName)) || Bukkit.getServer().getPlayer(name).getOpenInventory().getTitle().contains("игрок")) {
                        ClanManager.openClans(Bukkit.getServer().getPlayer(name));
                    }
                }
            }
        }
        if (!Members.isEmpty()) {
            for (String name : Members) {
                if (Bukkit.getServer().getPlayer(name) != null && Bukkit.getServer().getPlayer(name).isOnline()) {
                    if (Bukkit.getServer().getPlayer(name).getOpenInventory() != null && Bukkit.getServer().getPlayer(name).getOpenInventory().getTitle().contains(ChatColor.translateAlternateColorCodes('&', ClanName)) || Bukkit.getServer().getPlayer(name).getOpenInventory().getTitle().contains("игрок")) {
                        ClanManager.openClans(Bukkit.getServer().getPlayer(name));
                    }
                }
            }
        }
    }

    public void broadCastNotify(String notify) {
        if (Bukkit.getServer().getPlayer(Owner) != null && Bukkit.getServer().getPlayer(Owner).isOnline()) {
            Bukkit.getServer().getPlayer(Owner).sendMessage(notify);
        }
        if (!Soruks.isEmpty()) {
            for (String name : Soruks) {
                if (Bukkit.getServer().getPlayer(name) != null && Bukkit.getServer().getPlayer(name).isOnline()) {
                    Bukkit.getServer().getPlayer(name).sendMessage(notify);
                }
            }
        }
        if (!Staricks.isEmpty()) {
            for (String name : Staricks) {
                if (Bukkit.getServer().getPlayer(name) != null && Bukkit.getServer().getPlayer(name).isOnline()) {
                    Bukkit.getServer().getPlayer(name).sendMessage(notify);
                }
            }
        }
        if (!Members.isEmpty()) {
            for (String name : Members) {
                if (Bukkit.getServer().getPlayer(name) != null && Bukkit.getServer().getPlayer(name).isOnline()) {
                    Bukkit.getServer().getPlayer(name).sendMessage(notify);
                }
            }
        }
    }

    public String getClanName() {
        return ClanName;
    }

    public void setClanName(String clanName) {
        ClanName = clanName;
    }

    public String getClanTag() {
        return ClanTag;
    }

    public void setClanTag(String clanTag) {
        ClanTag = clanTag;
    }

    public List<String> getMembers() {
        return Members;
    }

    public void setMembers(List<String> members) {
        Members = members;
    }

    public List<String> getSoruks() {
        return Soruks;
    }

    public void setSoruks(List<String> soruks) {
        Soruks = soruks;
    }

    public List<String> getStaricks() {
        return Staricks;
    }

    public void setStaricks(List<String> staricks) {
        Staricks = staricks;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }
}
