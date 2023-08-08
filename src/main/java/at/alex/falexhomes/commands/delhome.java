package at.alex.falexhomes.commands;

import at.alex.falexhomes.utils.Chatter;
import at.alex.falexhomes.utils.FileHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class delhome implements CommandExecutor {
    Chatter chatter = new Chatter();
    FileHandler fileHandler = new FileHandler();

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(chatter.getMessageString("NotAPlayer"));
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            if (!fileHandler.HomeExists(player, "default")) {
                sender.sendMessage(chatter.getMessageString("HomeNotFound"));
                return true;
            }
        }
        if (!fileHandler.HomeExists(player, args[0])) {
            sender.sendMessage(chatter.getMessageString("HomeNotFound"));
            return true;
        }
        if (args.length == 0) {
            fileHandler.DeleteHome(player, "default");
        }
        fileHandler.DeleteHome(player, args[0]);
        sender.sendMessage(chatter.getMessageString("HomeDeleted").replace("%HomeName%", args[0]));
        return false;
    }
}


