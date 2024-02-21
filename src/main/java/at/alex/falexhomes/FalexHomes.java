package at.alex.falexhomes;

import at.alex.falexhomes.commands.delhome;
import at.alex.falexhomes.commands.home;
import at.alex.falexhomes.commands.homelist;
import at.alex.falexhomes.commands.sethome;
import at.alex.falexhomes.tabcompleter.delHomeCompleter;
import at.alex.falexhomes.tabcompleter.homeCompleter;
import at.alex.falexhomes.utils.Chatter;
import at.alex.falexhomes.utils.PlayerTime;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class FalexHomes extends JavaPlugin implements Listener {
    private File customConfigFile;
    private FileConfiguration customConfig;
    public static List<PlayerTime> homeCooldown = new ArrayList<>();
    public static List<Player> tpWait = new ArrayList<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, (Plugin)this);
        createCustomConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        getCommand("home").setExecutor(new home());
        getCommand("home").setTabCompleter(new homeCompleter());
        getCommand("sethome").setExecutor(new sethome());
        getCommand("homelist").setExecutor(new homelist());
        getCommand("delhome").setExecutor(new delhome());
        getCommand("delhome").setTabCompleter(new delHomeCompleter());
        // Plugin startup logic
    }
    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "Homes.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("Homes.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveCustomConfig() {
        try {
            getPlugin(this.getClass()).getCustomConfig().save(customConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onMoveEvent(PlayerMoveEvent event) {
        if (tpWait.contains(event.getPlayer())) {
            event.getPlayer().sendMessage("You have moved!");
            tpWait.remove(event.getPlayer());
        }
    }
}