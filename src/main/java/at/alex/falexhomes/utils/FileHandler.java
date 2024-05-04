package at.alex.falexhomes.utils;

import at.alex.falexhomes.FalexHomes;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.util.List;
import java.util.Set;

public class FileHandler {
    int homecount;
    Chatter chatter = new Chatter();
    FalexHomes plugin = FalexHomes.getPlugin(FalexHomes.class);
    FileConfiguration homeConfig = plugin.getCustomConfig();
    public int MaxHomes = plugin.getConfig().getInt("General.MaxHomes");
    public void setHome(Location loc, Player player, String name) {
        homeConfig.set("Homes." + player.getUniqueId() + "." + name, loc);
        chatter.DebugLogger(String.valueOf(MaxHomes) + " " + String.valueOf(CheckHomeCount(player)));
        plugin.saveCustomConfig();
    }
    public int CheckHomeCount(Player player) {
        ConfigurationSection selection = plugin.getCustomConfig().getConfigurationSection("Homes." + player.getUniqueId());
        if (selection == null) {
            return 0;
        }
        if (selection.getKeys(false) == null) {
            return 0;
        }
        Set<String> playerhomes = selection.getKeys(false);
        if (homeConfig.contains("Homes." + player.getUniqueId() + ".default")) {
            return playerhomes.stream().toArray().length - 1;
        }
        return playerhomes.stream().toArray().length;

    }
    public List<String> GetHomesFromPlayer(Player player) {
        Set<String> playerhomes;
        try {
            ConfigurationSection selection = plugin.getCustomConfig().getConfigurationSection("Homes." + player.getUniqueId());
            playerhomes = selection.getKeys(false);
        } catch (Exception e) {
            return null;
        }
        chatter.DebugLogger(playerhomes.stream().toString());
        return playerhomes.stream().toList();
    }

    public void DeleteHome(Player player, String name) {
        plugin.getCustomConfig().set("Homes." + player.getUniqueId() + "." + name, null);
        plugin.saveCustomConfig();
    }

    public Location GetLocationHome(Player player, String name) {
        return homeConfig.getLocation("Homes." + player.getUniqueId() + "." + name);
    }

    public boolean HomeExists(Player player, String name) {
        return homeConfig.contains("Homes." + player.getUniqueId() + "." + name);
    }
    public boolean HasDefaultHome(Player player) {
        return (homeConfig.contains("Homes." + player.getUniqueId() + ".default"));
    }
    public int GetTotalHomeCount(Player player) {
        homecount = homecount + CheckHomeCount(player);
        if (HasDefaultHome(player)) {
            homecount++;
        }
        return homecount;
    }

    public String TeleportEffect = plugin.getConfig().getString("Teleportation-Effect.type");
    public int TeleportEffectDuartion = plugin.getConfig().getInt("Teleportation-Effect.duration");
    public int TelepeortEffectAmplifier = plugin.getConfig().getInt("Teleportation-Effect.amplifier");

    public String TeleportSound = plugin.getConfig().getString("Teleportation-Sound.type");
    public String PreTeleportSound = plugin.getConfig().getString("Teleportation-Sound.pre");
    public int teleportCooldown = plugin.getConfig().getInt("Teleportation.cooldown");
    public int teleportWaitTime = plugin.getConfig().getInt("Teleportation.waitTime");


}
