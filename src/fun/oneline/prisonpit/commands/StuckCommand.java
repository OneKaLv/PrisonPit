package fun.oneline.prisonpit.commands;

import fun.oneline.api.commands.AbstractCommand;
import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.shaft.ShaftUpdaterForSinglePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class StuckCommand extends AbstractCommand {
    public StuckCommand() {
        super("stuck");
    }

    public static List<Player> coldown = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player p = (Player) commandSender;
        if(!coldown.contains(p)){
            ShaftUpdaterForSinglePlayer.update(p);
            coldown.add(p);
            p.sendMessage("§7[§a!§7]§e Вы были освобождены.");
            new BukkitRunnable(){
                @Override
                public void run() {
                    coldown.remove(p);
                }
            }.runTaskLater(Main.instance,200);
        } else {
            p.sendMessage("§7[§c!§7]§e Использование комманды доступно раз в 10 сек.");
        }
        return false;
    }
}
