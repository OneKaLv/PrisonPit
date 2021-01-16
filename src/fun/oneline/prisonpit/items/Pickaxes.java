package fun.oneline.prisonpit.items;

import fun.oneline.api.inventory.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pickaxes {
    private static HashMap<Integer, ItemStack> pickaxesHashMap = new HashMap<>();

    public static void LoadPickAxes(){
        List<String> lore = new ArrayList<>();

        lore.add("");
        lore.add("§eУровень 1");
        ItemStack itemStack = new ItemStack(Material.WOOD_PICKAXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§a§lДеревянная кирка");
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        pickaxesHashMap.put(0,itemStack);
        lore.clear();

        lore.add("");
        lore.add("§eУровень 2");
        itemStack = new ItemStack(Material.WOOD_PICKAXE);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§a§lДеревянная кирка");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 1, true);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        pickaxesHashMap.put(1,itemStack);
        lore.clear();

        lore.add("");
        lore.add("§eУровень 3");
        itemStack = new ItemStack(Material.WOOD_PICKAXE);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§a§lДеревянная кирка");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 2, true);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        pickaxesHashMap.put(2,itemStack);
        lore.clear();

        lore.add("");
        lore.add("§eУровень 4");
        itemStack = new ItemStack(Material.WOOD_PICKAXE);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§a§lДеревянная кирка");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 3, true);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        pickaxesHashMap.put(3,itemStack);
        lore.clear();

        lore.add("");
        lore.add("§eУровень 5");
        itemStack = new ItemStack(Material.STONE_PICKAXE);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§a§lКаменная кирка");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 2, true);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        pickaxesHashMap.put(4,itemStack);
        lore.clear();

        lore.add("");
        lore.add("§eУровень 6");
        itemStack = new ItemStack(Material.STONE_PICKAXE);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§a§lКаменная кирка");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 3, true);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        pickaxesHashMap.put(5,itemStack);
        lore.clear();

        lore.add("");
        lore.add("§eУровень 7");
        itemStack = new ItemStack(Material.IRON_PICKAXE);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§a§lЖелезная кирка");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 2, true);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        pickaxesHashMap.put(6,itemStack);
        lore.clear();

        lore.add("");
        lore.add("§eУровень 8");
        itemStack = new ItemStack(Material.IRON_PICKAXE);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§a§lЖелезная кирка");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 3, true);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        pickaxesHashMap.put(7,itemStack);
        lore.clear();

        lore.add("");
        lore.add("§eУровень 9");
        itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§a§lАлмазная кирка");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 1, true);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        pickaxesHashMap.put(8,itemStack);
        lore.clear();

        lore.add("");
        lore.add("§eУровень 10");
        itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§a§lАлмазная кирка");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 2, true);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        pickaxesHashMap.put(9,itemStack);
        lore.clear();

        lore.add("");
        lore.add("§eУровень 11");
        itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§a§lАлмазная кирка");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 3, true);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        pickaxesHashMap.put(10,itemStack);
        lore.clear();

        lore.add("");
        lore.add("§eУровень 12");
        itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§a§lАлмазная кирка");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 4, true);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        pickaxesHashMap.put(11,itemStack);
        lore.clear();

        lore.add("");
        lore.add("§eУровень 13");
        itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§a§lАлмазная кирка");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 5, true);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        pickaxesHashMap.put(12,itemStack);
        lore.clear();

        lore.add("");
        lore.add("§eУровень 14");
        itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§a§lАлмазная кирка");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 6, true);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        pickaxesHashMap.put(13,itemStack);
        lore.clear();

        lore.add("");
        lore.add("§eУровень 15");
        itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§a§lАлмазная кирка");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 7, true);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        pickaxesHashMap.put(14,itemStack);
        lore.clear();

        lore.add("");
        lore.add("§eУровень 16");
        itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§a§lАлмазная кирка");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 8, true);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        pickaxesHashMap.put(15,itemStack);
        lore.clear();

        lore.add("");
        lore.add("§eУровень 17");
        itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§a§lАлмазная кирка");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 9, true);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        pickaxesHashMap.put(16,itemStack);
        lore.clear();

        lore.add("");
        lore.add("§eУровень 18");
        itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§a§lАлмазная кирка");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 10, true);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        pickaxesHashMap.put(17,itemStack);
        lore.clear();
    }

    public static ItemStack getPickaxe(int level){
        return pickaxesHashMap.get(level);
    }
}
