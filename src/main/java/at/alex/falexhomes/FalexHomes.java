package at.alex.falexhomes;

import at.alex.falexhomes.commands.*;
import at.alex.falexhomes.tabcompleter.homeCompleter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class FalexHomes extends JavaPlugin {
    private File customConfigFile;
    private FileConfiguration customConfig;

    @Override
    public void onEnable() {
        createCustomConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        getCommand("home").setExecutor(new home());
        getCommand("home").setTabCompleter(new homeCompleter());
        getCommand("sethome").setExecutor(new sethome());
        getCommand("homelist").setExecutor(new homelist());
        getCommand("delhome").setExecutor(new delhome());
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
}
