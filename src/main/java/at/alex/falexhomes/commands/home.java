package at.alex.falexhomes.commands;

import at.alex.falexhomes.utils.Chatter;
import at.alex.falexhomes.utils.FileHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class home implements CommandExecutor {
    private String homeName;
    Chatter chatter = new Chatter();
    FileHandler fileHandler = new FileHandler();

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(chatter.getMessageString("NotAPlayer"));
            return true;
        }

        Player player = (Player) sender;
        Bukkit.getLogger().info(String.valueOf(args.length));
        if (args.length == 0) {
            Bukkit.getLogger().info("No Args");
            if (!fileHandler.HomeExists(player, "default")) {
                Bukkit.getLogger().info("Home doesnt exist");
                sender.sendMessage(chatter.getMessageString("HomeNotFound"));
                return true;
            }
            homeName = "default";
        } else {
            Bukkit.getLogger().info("Args");
            if (!fileHandler.HomeExists(player, args[0])) {
                sender.sendMessage(chatter.getMessageString("HomeNotFound"));
                return true;
            }
            homeName = args[0];
        }



        player.teleport(fileHandler.GetLocationHome(player, homeName));

        String teleportmessage = chatter.getMessageString("ToHomeTeleported");

        sender.sendMessage(teleportmessage.replace("%HomeName%", homeName));
        return false;
    }
}

