package fun.oneline.prisonpit.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandsUtil {

    public static boolean isPlayer(CommandSender sender) {
        if (sender instanceof Player) {
            return true;
        }
        return false;
    }
}
