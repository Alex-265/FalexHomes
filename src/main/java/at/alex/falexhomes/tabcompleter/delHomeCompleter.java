package at.alex.falexhomes.tabcompleter;

import at.alex.falexhomes.utils.Chatter;
import at.alex.falexhomes.utils.FileHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class delHomeCompleter implements TabCompleter {
    Chatter chatter = new Chatter();
    FileHandler fileHandler = new FileHandler();
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //chatter.DebugLogger(Arrays.stream(args).toList().toString());
        //chatter.DebugLogger(String.valueOf(Arrays.stream(args).count()));
        List<String> homes;
        Player player = (Player) sender;
        if (fileHandler.GetHomesFromPlayer(player) == null) {
            //chatter.DebugLogger("Player has no Homes");
            return new ArrayList<>();
        }
        if (Arrays.stream(args).count() > 1) {
            return new ArrayList<>();
        }
        homes = new ArrayList<>(fileHandler.GetHomesFromPlayer(player));
        homes.remove("default");
        //chatter.DebugLogger(homes.toString());
        String input = args[0].toLowerCase();

        List<String> completions = null;
        for (String s : homes) {
            if (s.startsWith(input)) {

                if (completions == null) {
                    completions = new ArrayList<>();
                }
                completions.add(s);
            }
        }
        if (completions != null)
            Collections.sort(completions);
        else {
            return new ArrayList<>();
        }
        return completions;
    }
 }
