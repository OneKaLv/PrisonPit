package fun.oneline.prisonpit.commands;

import fun.oneline.api.commands.AbstractCommand;
import fun.oneline.prisonpit.player.PrisonPitPlayer;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PayCommand extends AbstractCommand {
    public PayCommand() {
        super("pay");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        if (args.length == 0) {
            player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Для передачи осколков используй комманду /pay <Ник игрока> <Сумма осколков>");
        } else if (args.length == 1) {
            player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Для передачи осколков используй комманду /pay <Ник игрока> <Сумма осколков>");
        } else if (args.length == 2) {
            if (Bukkit.getServer().getPlayer(args[0]) != null && Bukkit.getServer().getPlayer(args[0]).isOnline()) {
                if (Bukkit.getServer().getPlayer(args[0]).getName() != player.getName()) {
                    if (args[1].matches("[-+]?\\d+") && Integer.parseInt(args[1]) <= PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getShards() && Integer.parseInt(args[1]) > 0) {
                        PrisonPitPlayer sender = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
                        PrisonPitPlayer reciver = PrisonPitPlayerManager.getPrisonPitPlayer(Bukkit.getServer().getPlayer(args[0]).getName());

                        sender.setShards(sender.getShards() - Integer.parseInt(args[1]));
                        reciver.setShards(reciver.getShards() + Integer.parseInt(args[1]));
                        player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Отправленно §d" + Integer.parseInt(args[1]) + " осколков§e игроку " + Bukkit.getServer().getPlayer(args[0]).getName() + ".");
                        Bukkit.getServer().getPlayer(args[0]).sendMessage("§7[§e§lPrison §6§lPit§7]§e Получено §d" + Integer.parseInt(args[1]) + " осколков§e от игрока " + player.getName() + ".");
                    } else {
                        player.sendMessage("§7[§e§lPrison §6§lPit§7]§c Недостаточно осколков!");
                    }
                } else {
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§c Ты не можешь отправить осколки самому себе!");
                }
            } else {
                player.sendMessage("§7[§e§lPrison §6§lPit§7]§c Игрок не найден!");
            }
        } else {
            player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Для передачи осколков используй комманду /pay <Ник игрока> <Сумма осколков>");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<String>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                list.add(p.getName());
            }
            return list;
        }
        return null;
    }
}
