package fun.oneline.prisonpit.commands;

import fun.oneline.api.commands.AbstractCommand;
import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.handlers.onNPCClick;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import fun.oneline.prisonpit.tokens.TokenManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class TokenCommand extends AbstractCommand {
    public TokenCommand() {
        super("token");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        if (args.length > 0) {
            if (args[0].equals("accept")) {
                if (onNPCClick.interactedPlayers.containsKey(player)) {
                    String token = onNPCClick.interactedPlayers.get(player);
                    onNPCClick.confirmedPlayers.remove(player.getName());
                    onNPCClick.interactedPlayers.remove(player);
                    onNPCClick.usedTokenPlayers.add(player.getName());
                    ItemStack tokens = player.getInventory().getItem(7);
                    if (tokens.getAmount() > 1) {
                        tokens.setAmount(tokens.getAmount() - 1);
                        player.getInventory().setItem(7, tokens);
                    } else {
                        player.getInventory().setItem(7, new ItemStack(Material.AIR));
                    }
                    PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setTokens(PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTokens()-1);
                    player.sendMessage("§7[§b§lЛегезо§7]§e Спасибо за обмен!");
                    player.sendTitle("§a§lБонус активирован!", "§b§l" + token, 30, 90, 30);
                    player.playSound(player.getLocation(), Sound.ENTITY_EVOCATION_ILLAGER_PREPARE_WOLOLO,1,0);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            onNPCClick.usedTokenPlayers.remove(player.getName());
                        }
                    }.runTaskLater(Main.instance, 18000);
                    TokenManager.setToken(token, player);
                }
            } else if (args[0].equals("deny")) {
                if (onNPCClick.interactedPlayers.containsKey(player)) {
                    onNPCClick.canceledTokenPlayers.add(player.getName());
                    onNPCClick.confirmedPlayers.remove(player.getName());
                    onNPCClick.interactedPlayers.remove(player);
                    player.sendMessage("§7[§b§lЛегезо§7]§e Очень жаль... Приходи позже, возможно у меня появятся новые бонусы");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            onNPCClick.canceledTokenPlayers.remove(player.getName());
                        }
                    }.runTaskLater(Main.instance, 2400);
                }
            }
            return false;
        }else {
            return false;
        }
    }
}
