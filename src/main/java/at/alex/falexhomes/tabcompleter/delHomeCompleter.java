package at.alex.falexhomes.tabcompleter;

import at.alex.falexhomes.utils.FileHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class delHomeCompleter implements TabCompleter {
    FileHandler fileHandler = new FileHandler();
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
         if (fileHandler.GetHomesFromPlayer(player) == null) {
             Bukkit.getLogger().info("Player has no Homes");
             return null;
         } else {
             List<String> homes = new ArrayList<>(fileHandler.GetHomesFromPlayer(player));
             homes.remove("default");
             return homes;
         }
     }
 }
