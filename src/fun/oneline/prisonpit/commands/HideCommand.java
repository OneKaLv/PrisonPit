package fun.oneline.prisonpit.commands;

import fun.oneline.api.commands.AbstractCommand;
import fun.oneline.prisonpit.utils.Hider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HideCommand extends AbstractCommand {
    public HideCommand() {
        super("hide");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Hider.toggle(player);
        }
        return true;
    }
}
