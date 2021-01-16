package fun.oneline.prisonpit.commands;

import fun.oneline.api.commands.AbstractCommand;
import fun.oneline.api.inventory.CustomInventory;
import fun.oneline.api.inventory.item.CustomItem;
import fun.oneline.prisonpit.boosters.Booster;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import fun.oneline.prisonpit.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.dream.network.core.service.account.IAccountService;
import ru.dream.network.core.service.account.group.DonateGroup;

import java.util.ArrayList;
import java.util.List;

public class BoostersCommand extends AbstractCommand {
    public BoostersCommand() {
        super("boosters");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){
        Player player = (Player) commandSender;
        CustomInventory boostersInv = new CustomInventory("§d§lВаши бустеры");
        List<String> lore = new ArrayList<>();
        int slot = 0;
        if(IAccountService.get().getProfile(player.getName()).getDonateGroup() != DonateGroup.player) {
            CustomItem privItem = new CustomItem(Material.CONCRETE);
            privItem.setName(IAccountService.get().getProfile(player.getName()).getDonateGroup().getDisplayName());
            lore.add("");
            lore.add("§aБустер денег: " + Utils.getBoosterMoneyFromPriv(PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getNumpriv()) + "x");
            privItem.setLore(lore);
            boostersInv.addItem(slot++,privItem.build());
        }
        lore.clear();
        for(Booster booster : Booster.active){
            switch (booster.getType()){
                case MONEY : {
                    CustomItem customItem = new CustomItem(Material.EMERALD);
                    customItem.setName("§aГлобальный бустер");
                    lore.add("");
                    lore.add("§aГлобальный бустер денег " + booster.getMultiplier() + "x " + "от игрока " + booster.getOwner());
                    lore.add("§7До конца: " + booster.timeConvert(booster.getDuration()));
                    customItem.setLore(lore);
                    lore.clear();
                    boostersInv.addItem(slot++,customItem.build());
                    break;
                }
                case SHARDS :   {
                    CustomItem customItem = new CustomItem(Material.PRISMARINE_SHARD);
                    customItem.setName("§dГлобальный бустер");
                    lore.add("");
                    lore.add("§dГлобальный бустер осколков " + booster.getMultiplier() + "x " + "от игрока " + booster.getOwner());
                    lore.add("§7До конца: " + booster.timeConvert(booster.getDuration()));
                    customItem.setLore(lore);
                    lore.clear();
                    boostersInv.addItem(slot++,customItem.build());
                    break;
                }
            }
        }
        boostersInv.open(player);
        return true;
    }
}
