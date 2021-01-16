package fun.oneline.prisonpit.commands;

import fun.oneline.api.commands.AbstractCommand;
import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.boosters.Booster;
import fun.oneline.prisonpit.player.PrisonPitPlayer;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ThxCommand extends AbstractCommand {
    public ThxCommand() {
        super("thx");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        if (CommandsUtil.isPlayer(sender)){
            Player player = (Player) sender;
            if (Booster.active.size() > 0){

                int size = 0;

                for (Booster booster : Booster.active){

                    if (booster.isThxPlayer(player)){
                        //player.sendMessage(ChatColor.YELLOW + "вы уже поблагодарили этого игрока за бустер");
                    } else {
                        size++;
                        if (booster.getOwner().equalsIgnoreCase(player.getName())){
                            player.sendMessage("§cВы не можете поблагодарить самого себя!");
                        } else {
                            booster.addPlayersThx(player);
                            int count = 0;
                            if (PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTotalStars() == 0) {
                                PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setShards(PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getShards() + 5);
                                count = 5;
                            } else {
                                PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).setShards((int) (PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getShards() + (5 * Math.pow(1.2, PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTotalStars()))));
                                count = (int) (5 * Math.pow(1.2, PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTotalStars()));
                            }
                            Player thePlayer = Bukkit.getPlayer(booster.getOwner());
                            if (thePlayer != null){
                                player.sendMessage("§aВы поблагодарили игрока " + thePlayer.getName() +" §aза активацию глобального бустера. §d+" + count + " осколков.");
                                thePlayer.sendMessage("§aВас поблагодарил игрок " + player.getName() + " §aза активацию глобального бустера. §d+" + count + " осколков.");
                                PrisonPitPlayerManager.getPrisonPitPlayer(thePlayer.getName()).setShards(PrisonPitPlayerManager.getPrisonPitPlayer(thePlayer.getName()).getShards() + count);
                            }
                        }
                    }
                }

                if (size == 0){
                    player.sendMessage("§cВы уже поблагодарили игроков за активацию глобальных бустеров!");
                }
            } else {
                player.sendMessage(ChatColor.YELLOW + "в данный момент нет активных бустеров");
            }
        }
        return false;
    }
}
