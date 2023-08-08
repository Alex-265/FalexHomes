package at.alex.falexhomes.utils;

import at.alex.falexhomes.FalexHomes;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class FileHandler {
    FalexHomes plugin = FalexHomes.getPlugin(FalexHomes.class);
    FileConfiguration homeConfig = plugin.getCustomConfig();
    public int MaxHomes = plugin.getConfig().getInt("General.MaxHomes");
    public void setHome(Location loc, Player player, String name) {
        homeConfig.set("Homes." + player.getUniqueId() + "." + name, loc);
        Bukkit.getLogger().info(String.valueOf(MaxHomes) + " " + String.valueOf(CheckHomeCount(player)));
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
        Bukkit.getLogger().info(playerhomes.stream().toString());
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

}
