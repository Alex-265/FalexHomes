package at.alex.falexhomes.utils;

import at.alex.falexhomes.FalexHomes;
import org.bukkit.Bukkit;

public class Chatter {
    Boolean argsa;
    FalexHomes plugin = FalexHomes.getPlugin(FalexHomes.class);
    private final boolean DebugMode = plugin.getConfig().getBoolean("Advanced.Debug");

    /*
    Used to get a Message from config.yml
    Param: String name, Boolean
    True = With Prefix (Standart)
    False = Remove Prefix
     */
    public String getMessageString(String name, Boolean... b) {
        if (b.length == 0) {
            argsa = true;
        } else if (b.length == 1) {
            if (!b[0]) {
                argsa = false;
            } else {
                argsa = true;
            }
        }
        if (argsa) {
            return "[" + plugin.getConfig().getString("Language.Prefix") + "] " + plugin.getConfig().getString("Language." + name);
        }
        return plugin.getConfig().getString("Language." + name);
    }
    public void DebugLogger(String message) {
        if (DebugMode) {
            Bukkit.getLogger().info(getMessageString("Prefix", false) + " " + message);
        }
    }
}
