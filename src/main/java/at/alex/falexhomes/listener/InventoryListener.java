package at.alex.falexhomes.listener;

import at.alex.falexhomes.utils.FileHandler;
import at.alex.falexhomes.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {
    FileHandler fileHandler = new FileHandler();

    @EventHandler
    public void onInvClick(InventoryClickEvent event){
        if(event.getCurrentItem() == null) return;
        if(event.getView().getTitle() == "Homes"){
            Player player = (Player) event.getWhoClicked();
            event.setCancelled(true);
            if (event.getClick().isLeftClick()) {
                player.teleport(fileHandler.GetLocationHome(player, event.getCurrentItem().getItemMeta().getDisplayName()));
            } else if (event.getClick().isRightClick()) {
                String homename = event.getCurrentItem().getItemMeta().getDisplayName();
                Inventory configInventory = Bukkit.createInventory(null, 1*9,"Configure " + homename);
                configInventory.setItem(8, new ItemBuilder(Material.BARRIER).setDisplayname("Delete " + homename).setLore("§7LMB to Delete").build());
                player.openInventory(configInventory);
            }
        }

    }

}
