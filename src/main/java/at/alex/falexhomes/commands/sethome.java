package at.alex.falexhomes.commands;

import at.alex.falexhomes.FalexHomes;
import at.alex.falexhomes.utils.Chatter;
import at.alex.falexhomes.utils.FileHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

public class sethome implements CommandExecutor {
    Chatter chatter = new Chatter();
    FileHandler fileHandler = new FileHandler();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String HomeName;
        if (!(sender instanceof Player)) {
            sender.sendMessage(chatter.getMessageString("NotAPlayer"));
            return true;
        }
        Player player = (Player) sender;
        int playerHomeCount = fileHandler.CheckHomeCount(player);
        int maxHomes = fileHandler.MaxHomes;
        if (args.length != 0) {
            if (playerHomeCount >= maxHomes){
                Bukkit.getLogger().info("Player has too many Homes");
                if (!player.hasPermission(new Permission("FalexHomes.admin.bypassHomeLimit", PermissionDefault.FALSE))) {
                    Bukkit.getLogger().info("Player does not have permission");
                    sender.sendMessage(chatter.getMessageString("TooManyHomes"));
                    return true;
                }
            }
        }


        if(args.length < 1) {
            HomeName = "default";
        } else if (args.length == 1) {
            if (args[0].equals("default")) {
                sender.sendMessage(chatter.getMessageString("InvalidName"));
                return true;
            }
            HomeName = args[0];
        } else {
            sender.sendMessage(chatter.getMessageString("InvalidArguments"));
            return true;
        }



        Location location = player.getLocation();

        fileHandler.setHome(location, player, HomeName);

        String createMessage = chatter.getMessageString("HomeCreated");
        sender.sendMessage(createMessage.replace("%HomeName%", HomeName));

        return false;
    }
}