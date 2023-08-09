package at.alex.falexhomes.commands;

import at.alex.falexhomes.utils.Chatter;
import at.alex.falexhomes.utils.FileHandler;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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

        PotionEffectType effectType = PotionEffectType.getByName(fileHandler.TeleportEffect);
        PotionEffect teleportationEffect = new PotionEffect(effectType, fileHandler.TeleportEffectDuartion, fileHandler.TelpeortEffectAmplifier, true,false);



        player.teleport(fileHandler.GetLocationHome(player, homeName));
        String teleportmessage = chatter.getMessageString("ToHomeTeleported");
        Sound sound = Sound.valueOf(fileHandler.TeleportSound);
        player.playSound(player.getLocation(),sound,0.1f,0.1f );
        sender.sendMessage(teleportmessage.replace("%HomeName%", homeName));
        return false;
    }
}

