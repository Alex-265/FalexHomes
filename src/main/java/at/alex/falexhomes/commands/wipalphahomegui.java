package at.alex.falexhomes.commands;

import at.alex.falexhomes.utils.Chatter;
import at.alex.falexhomes.utils.FileHandler;
import at.alex.falexhomes.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class wipalphahomegui implements CommandExecutor {
    Chatter chatter = new Chatter();
    FileHandler fileHandler = new FileHandler();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(chatter.getMessageString("NotAPlayer"));
            return true;
        }
        Player player = (Player) sender;
        List<String> homes = new ArrayList<>(fileHandler.GetHomesFromPlayer(player));
        if (homes.contains("default")) {
            homes.remove("default");
            homes.add(0, "default");
        }
        int rows = (int) Math.ceil(homes.size() / 9.0);

        Inventory inventory = Bukkit.createInventory(null, rows*9,"Homes");
        for (int i = 0; i < homes.size(); i++){
            Location homelocation = fileHandler.GetLocationHome(player, homes.get(i));
            String locationString = "§f Location: X=" + homelocation.getBlockX() + " Y=" + homelocation.getBlockY() + " Z=" + homelocation.getBlockZ();
            if (homes.get(i).equals("default")) {
                inventory.setItem(i, new ItemBuilder(Material.RED_BED).setDisplayname(homes.get(i)).setLore(locationString).build());
            } else {
                inventory.setItem(i, new ItemBuilder(Material.PAPER).setDisplayname(homes.get(i)).setLore(locationString).build());
            }
        }
        player.openInventory(inventory);
        return false;
    }
}
