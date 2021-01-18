package fun.oneline.prisonpit.handlers;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import fun.oneline.api.inventory.ClickAction;
import fun.oneline.api.inventory.CustomInventory;
import fun.oneline.api.inventory.item.CustomItem;
import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.boosters.Booster;
import fun.oneline.prisonpit.boosters.BoosterType;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import fun.oneline.prisonpit.tokens.TokenManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class onInteractWithBlock implements Listener {
    public onInteractWithBlock(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockClick(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(event.getClickedBlock().getType() == Material.CONCRETE){
                if(PrisonPitPlayerManager.getPrisonPitPlayer(event.getPlayer().getName()).getTotalStars() >= 10){
                    ApplicableRegionSet set = onBreak.manager.getApplicableRegions(event.getClickedBlock().getLocation());
                    if (set.queryState(null, DefaultFlag.BLOCK_BREAK) == StateFlag.State.DENY) {
                        return;
                    }
                    showDestroyBlockMenu(event.getPlayer(),event.getClickedBlock());
                }
            }
        }
    }

    public static void showDestroyBlockMenu(Player p, Block block){
        CustomInventory customInventory = new CustomInventory("§a§lПодтверждение разрушения блока");
        List<String> lore = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }
        customInventory.addItem(11, new CustomItem(Material.REDSTONE_BLOCK).setName("§c§lОтклонить").setLore(lore).build());
        customInventory.addMenuClickHandler(11, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                p.closeInventory();
                return false;
            }
        });
        customInventory.addItem(13, new CustomItem(Material.GHAST_TEAR).setName("§e§l+" + String.format("%.3f",(PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).getBoosterMoney_level()/1000.0) * (1.0 + (PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).getMoreFragments_level()/20.0))) + " фрагментов звёзд").setLore(lore).build());
        customInventory.addMenuClickHandler(13, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                return false;
            }
        });
        customInventory.addItem(15, new CustomItem(Material.EMERALD_BLOCK).setName("§a§lПодтвердить").setLore(lore).build());
        customInventory.addMenuClickHandler(15, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                if(block.getType() == Material.CONCRETE){
                    block.setType(Material.AIR);
                    p.playSound(p.getLocation(), Sound.BLOCK_STONE_BREAK,3,1);
                    p.spawnParticle(Particle.PORTAL,new Location(block.getWorld(),block.getX()+0.5,block.getY()+0.5,block.getZ()+0.5),50);
                    p.closeInventory();
                    PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).setFragments(PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).getFragments() + (PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).getBoosterMoney_level()/1000.0) * (1.0 + (PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).getMoreFragments_level()/20.0)));
                    Random random = new Random();
                    if(random.nextInt(100) < PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).getMoreMagnetsIII_level()){
                        if(TokenManager.moreShards.contains(p)){
                            PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).setShards((int) (PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).getShards() + (2 * Booster.getMultiplier(p.getName(), BoosterType.SHARDS))));
                        } else {
                            PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).setShards((int) (PrisonPitPlayerManager.getPrisonPitPlayer(p.getName()).getShards() + (1 * Booster.getMultiplier(p.getName(), BoosterType.SHARDS))));
                        }
                    }
                } else {
                    p.closeInventory();
                    p.sendMessage("§7[§c!§7]§e Не удалось найти блок");
                }
                return false;
            }
        });
        customInventory.open(p);
    }
}

