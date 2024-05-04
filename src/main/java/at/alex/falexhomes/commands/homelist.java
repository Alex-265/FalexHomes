package at.alex.falexhomes.commands;

import at.alex.falexhomes.utils.Chatter;
import at.alex.falexhomes.utils.FileHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class homelist implements CommandExecutor {
    Chatter chatter = new Chatter();
    FileHandler fileHandler = new FileHandler();

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(chatter.getMessageString("NotAPlayer"));
            return true;
        }

        Player player = (Player) sender;
        if (fileHandler.GetHomesFromPlayer(player) == null) {
            player.sendMessage(chatter.getMessageString("NoHomesFound"));
            return false;
        }
        String message = listToMessage(fileHandler.GetHomesFromPlayer(player));
        player.sendMessage(chatter.getMessageString("HomeListMessage").replace("%listhomes%", message));
        String statisticmessage = "(" + fileHandler.CheckHomeCount(player) + "/" + fileHandler.MaxHomes + ")";
        player.sendMessage(chatter.getMessageString("HomeListStatistic").replace("%Statistic%", statisticmessage));

        return false;
    }

    private String listToMessage(List<String> list) {
        if (list.isEmpty()) {
            return "No homes";
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            StringBuilder message = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                message.append(list.get(i));
                if (i < list.size() - 1) {
                    message.append(", ");
                }
            }
            return message.toString();
        }
    }
}