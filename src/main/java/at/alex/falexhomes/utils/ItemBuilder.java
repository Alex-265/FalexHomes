package at.alex.falexhomes.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;

import java.util.Arrays;

public class ItemBuilder {
    private ItemMeta itemMeta;
    private ItemStack itemStack;
    public ItemBuilder(Material mat){
        itemStack = new ItemStack(mat);
        itemMeta = itemStack.getItemMeta();
    }
    public ItemBuilder setDisplayname(String s){
        itemMeta.displayName(Component.text(s));
        return this;
    }
    public ItemBuilder setLocalizedName(String s){
        itemMeta.displayName(Component.text(s));
        return this;
    }
    public ItemBuilder setLore(String... s) {
        itemMeta.lore(Arrays.stream(s).map(Component::text).toList());
        return this;
    }


    public ItemBuilder setUnbreakable(boolean s){
        itemMeta.setUnbreakable(s);
        return this;
    }
    public ItemBuilder addItemFlags(ItemFlag... s){
        itemMeta.addItemFlags(s);
        return this;
    }

    @Override
    public String toString() {
        return "ItemBuilder{" +
            "itemMeta=" + itemMeta +
            ", itemStack=" + itemStack +
            '}';
    }
    public ItemStack build(){
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
