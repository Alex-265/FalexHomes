package at.alex.falexhomes.commands;

import at.alex.falexhomes.FalexHomes;
import at.alex.falexhomes.utils.Chatter;
import at.alex.falexhomes.utils.FileHandler;
import at.alex.falexhomes.utils.PlayerTime;
import at.alex.falexhomes.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.security.KeyStore;

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
        chatter.DebugLogger("CURRENT TIME: " + String.valueOf(TimeUtils.getCurrentTime()));
        boolean playerExistsInCooldown = false;
        int indexPlayer = -1;
        for (PlayerTime i : FalexHomes.homeCooldown) {
            if (i.getPlayerUUID().equals(player.getUniqueId().toString())) {
                indexPlayer = FalexHomes.homeCooldown.indexOf(i);
            }
        }
        if (indexPlayer != -1) {
            PlayerTime timePlayer = FalexHomes.homeCooldown.get(indexPlayer);
            int timeSinceLast = TimeUtils.getCurrentTime() - timePlayer.getTime();
            if (timeSinceLast < fileHandler.teleportCooldown) {
                sender.sendMessage(chatter.getMessageString("Cooldown").replace("%time%", String.valueOf(20 - timeSinceLast)));
                return true;
            }
            FalexHomes.homeCooldown.remove(indexPlayer);
        }
        if (FalexHomes.tpWait.contains(player)) {
            sender.sendMessage(chatter.getMessageString("AlreadyTeleporting"));
            return true;
        }

        final PotionEffectType effectType = PotionEffectType.getByName(fileHandler.TeleportEffect.toUpperCase());
        final PotionEffect teleportationEffect = new PotionEffect(effectType, fileHandler.TeleportEffectDuartion, fileHandler.TelpeortEffectAmplifier, false,false);
        player.addPotionEffect(teleportationEffect);
        FalexHomes.homeCooldown.add(new PlayerTime(player.getUniqueId().toString(), TimeUtils.getCurrentTime()));
        final Location tpTo = fileHandler.GetLocationHome(player, homeName);
        final Player finalPlayer = player;
        //chatter.DebugLogger(homeName);
        final String teleportmessage = chatter.getMessageString("ToHomeTeleported");
        final Sound sound = Sound.valueOf(fileHandler.TeleportSound.toUpperCase());
        sender.sendMessage(chatter.getMessageString("GettingTeleportedIn").replace("%time%", String.valueOf(fileHandler.teleportWaitTime / 20)));
        player.playSound(player.getLocation(), Sound.valueOf(fileHandler.PreTeleportSound), 1.0f, 1.0f);
        FalexHomes.tpWait.add(player);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FalexHomes.getPlugin(FalexHomes.class
        ), new Runnable() {
                @Override
                public void run() {
                    if (FalexHomes.tpWait.contains(finalPlayer)) {
                        finalPlayer.teleport(tpTo);
                        finalPlayer.playSound(finalPlayer.getLocation(),sound,1.0f,1.0f );
                        finalPlayer.sendMessage(teleportmessage.replace("%HomeName%", homeName));
                    }
                    FalexHomes.tpWait.remove(finalPlayer);

                }
            }, fileHandler.teleportWaitTime);
        return false;
    }
}

