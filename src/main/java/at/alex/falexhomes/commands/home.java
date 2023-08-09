package at.alex.falexhomes.commands;

import at.alex.falexhomes.utils.Chatter;
import at.alex.falexhomes.utils.FileHandler;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
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
        chatter.DebugLogger(String.valueOf(args.length));
        if (args.length == 0) {
            chatter.DebugLogger("No Args");
            if (!fileHandler.HomeExists(player, "default")) {
                chatter.DebugLogger("Home doesnt exist");
                sender.sendMessage(chatter.getMessageString("HomeNotFound"));
                return true;
            }
            homeName = "default";
        } else {
            if (!fileHandler.HomeExists(player, args[0])) {
                sender.sendMessage(chatter.getMessageString("HomeNotFound"));
                return true;
            }
            homeName = args[0];
        }

        PotionEffectType effectType = PotionEffectType.getByName(fileHandler.TeleportEffect.toUpperCase());
        PotionEffect teleportationEffect = new PotionEffect(effectType, fileHandler.TeleportEffectDuartion, fileHandler.TelpeortEffectAmplifier, false,false);
        player.addPotionEffect(teleportationEffect);



        chatter.DebugLogger(homeName);
        player.teleport(fileHandler.GetLocationHome(player, homeName));
        String teleportmessage = chatter.getMessageString("ToHomeTeleported");
        Sound sound = Sound.valueOf(fileHandler.TeleportSound.toUpperCase());
        player.playSound(player.getLocation(),sound,1.0f,1.0f );
        sender.sendMessage(teleportmessage.replace("%HomeName%", homeName));
        return false;
    }
}

