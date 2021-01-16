package fun.oneline.prisonpit.items;

import fun.oneline.api.inventory.ClickAction;
import fun.oneline.api.inventory.CustomInventory;
import fun.oneline.api.inventory.item.CustomItem;
import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.boosters.Booster;
import fun.oneline.prisonpit.boosters.BoosterType;
import fun.oneline.prisonpit.clans.ClanManager;
import fun.oneline.prisonpit.database.MySQL;
import fun.oneline.prisonpit.handlers.onGoldBlockASClick;
import fun.oneline.prisonpit.player.PrisonPitPlayer;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import fun.oneline.prisonpit.shaft.ShaftUpdater;
import fun.oneline.prisonpit.shaft.ShaftUpdaterForSinglePlayer;
import fun.oneline.prisonpit.utils.Utils;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Menu {
    public static List<String> stormDisable = new ArrayList<>();
    private static ItemStack Menu = new ItemStack(Material.COMPASS);
    private static HashMap<Player, String> choiseHashMap = new HashMap<>();
    private static HashMap<Player, String> prestigeChoiceHashMap = new HashMap<>();

    public static ItemStack getMenu() {
        ItemMeta itemMeta = Menu.getItemMeta();
        itemMeta.setDisplayName("§e§lМеню");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        Menu.setItemMeta(itemMeta);
        return Menu;
    }

    public static void showGuiMenu(Player player) {
        CustomInventory customInventory = new CustomInventory("§e§lМеню");
        List<String> lore = new ArrayList<>();
        customInventory.addMenuCloseHandler(new CustomInventory.MenuCloseHandler() {
            @Override
            public void onClose(Player player) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.updateInventory();
                    }
                }.runTaskLater(Main.instance, 2);
            }
        });
        customInventory.addItem(10, new CustomItem(Material.EMERALD, 1).setName("§a§lУровень блоков").setLore(lore).build());
        customInventory.addMenuClickHandler(10, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                showMenuBlocks(player, 0);
                return false;
            }
        });
        customInventory.addItem(12, new CustomItem(Material.GOLD_BLOCK, 1).setName("§6§lПрестиж").setLore(lore).build());
        customInventory.addMenuClickHandler(12, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                showMenuPrestige(player);
                return false;
            }
        });
        customInventory.addItem(14, new CustomItem(Material.TOTEM, 1).setName("§b§lУлучшения").setLore(lore).build());
        customInventory.addMenuClickHandler(14, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                showUpgradesMenu(player);
                return false;
            }
        });
        customInventory.addItem(16, new CustomItem(Material.FURNACE, 1).setName("§7§lПрочее").setLore(lore).build());
        customInventory.addMenuClickHandler(16, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                showOtherMenu(player);
                return false;
            }
        });
        customInventory.addItem(26, new CustomItem(Material.AIR, 1).build());
        lore.clear();
        customInventory.open(player);
    }

    public static void showOtherMenu(Player player) {
        CustomInventory customInventory = new CustomInventory("§7§lПрочее");
        List<String> lore = new ArrayList<>();
        customInventory.addMenuCloseHandler(new CustomInventory.MenuCloseHandler() {
            @Override
            public void onClose(Player player) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.updateInventory();
                    }
                }.runTaskLater(Main.instance, 2);
            }
        });

        for (int i = 0; i < 27; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }

        customInventory.addItem(11, new CustomItem(Material.DIAMOND, 1).setName("§b§lДонат").setLore(lore).build());
        customInventory.addMenuClickHandler(11, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                showDonateMenu(player);
                return false;
            }
        });

        customInventory.addItem(15, new CustomItem(Material.REDSTONE_COMPARATOR, 1).setName("§6§lНастройки").setLore(lore).build());
        customInventory.addMenuClickHandler(15, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                showOptionsMenu(player);
                return false;
            }
        });
        customInventory.addItem(18, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName("§bВернутся назад").setSkullOwner("MHF_ArrowLeft").build());
        customInventory.addMenuClickHandler(18, new CustomInventory.MenuClickHandler() {
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                showGuiMenu(player);
                return false;
            }
        });
        customInventory.open(player);
    }

    public static void showDonateMenu(Player player) {
        CustomInventory customInventory = new CustomInventory("§b§lДонат");
        List<String> lore = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }
        customInventory.addItem(12, new CustomItem(Material.GOLD_BLOCK).setName("§e§lБустеры").setLore(lore).build());
        customInventory.addMenuClickHandler(12, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                showDonateBoostersMenu(player);
                return false;
            }
        });

        customInventory.addItem(14, new CustomItem(Material.STICK).setName("§e§lПредметы").setLore(lore).build());
        customInventory.addMenuClickHandler(14, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                showDonateItemsMenu(player);
                return false;
            }
        });
        customInventory.addItem(18, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName("§bВернутся назад").setSkullOwner("MHF_ArrowLeft").build());
        customInventory.addMenuClickHandler(18, new CustomInventory.MenuClickHandler() {
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                showOtherMenu(player);
                return false;
            }
        });
        customInventory.addItem(26, new CustomItem(Material.DIAMOND_BLOCK).setName("§eБаланс: §d" + Utils.getDonateCoin(player.getName()) + " DC.").build());
        customInventory.addMenuClickHandler(26, new CustomInventory.MenuClickHandler() {
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                player.closeInventory();
                player.sendMessage("§6Пополнить баланс можно на нашем сайте в личном кабинете.");
                return false;
            }
        });
        customInventory.open(player);
    }

    public static void showDonateBoostersMenu(Player player) {
        CustomInventory customInventory = new CustomInventory("§e§lБустеры");
        List<String> lore = new ArrayList<>();

        for (int i = 0; i < 27; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }

        customInventory.addItem(11, new CustomItem(Material.EMERALD).setName("§a§lДеньги").setLore(lore).build());
        customInventory.addMenuClickHandler(11, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                showMoneyBoostersMenu(player);
                return false;
            }
        });

        customInventory.addItem(15, new CustomItem(Material.PRISMARINE_SHARD).setName("§d§lОсколки").setLore(lore).build());
        customInventory.addMenuClickHandler(15, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                showShardsBoostersMenu(player);
                return false;
            }
        });

        customInventory.addItem(18, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName("§bВернутся назад").setSkullOwner("MHF_ArrowLeft").build());
        customInventory.addMenuClickHandler(18, new CustomInventory.MenuClickHandler() {
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                showDonateMenu(player);
                return false;
            }
        });

        customInventory.addItem(26, new CustomItem(Material.DIAMOND_BLOCK).setName("§eБаланс: §d" + Utils.getDonateCoin(player.getName()) + " DC.").build());
        customInventory.addMenuClickHandler(26, new CustomInventory.MenuClickHandler() {
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                player.closeInventory();
                player.sendMessage("§6Пополнить баланс можно на нашем сайте в личном кабинете.");
                return false;
            }
        });

        customInventory.open(player);
    }

    public static void showMoneyBoostersMenu(Player player){
        CustomInventory customInventory = new CustomInventory("§a§lДеньги");
        List<String> lore = new ArrayList<>();

        for (int i = 0; i < 27; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }

        lore.add("§eЦена: §d39 DC");
        lore.add("");
        lore.add("§eПримечание: §7Активирует бустер денег для всех игроков на 1 час");
        customInventory.addItem(11, new CustomItem(Material.EMERALD).setName("§a§lГлобальный бустер денег х2").setLore(lore).build());
        customInventory.addMenuClickHandler(11, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                if (1 > Booster.getBoosterSizeTypeAntPr(player.getName(), BoosterType.MONEY)) {
                    if (Utils.getDonateCoin(player.getName()) >= 39) {
                        showDonateConfirm(player, 39, 4);
                    } else {
                        player.closeInventory();
                        player.sendMessage("§7[§e§lPrison §6§lPit§7]§c На вашем счету недостаточно DC!");
                    }
                } else {
                    player.closeInventory();
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Сейчас уже активен бустер денег, попробуйте позже!");
                }
                return false;
            }
        });
        lore.clear();

        lore.add("§eЦена: §d79 DC");
        lore.add("");
        lore.add("§eПримечание: §7Активирует бустер денег для всех игроков на 1 час");
        customInventory.addItem(13, new CustomItem(Material.EMERALD).setName("§a§lГлобальный бустер денег х3").setLore(lore).build());
        customInventory.addMenuClickHandler(13, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                if (1 > Booster.getBoosterSizeTypeAntPr(player.getName(), BoosterType.MONEY)) {
                    if (Utils.getDonateCoin(player.getName()) >= 79) {
                        showDonateConfirm(player, 79, 5);
                    } else {
                        player.closeInventory();
                        player.sendMessage("§7[§e§lPrison §6§lPit§7]§c На вашем счету недостаточно DC!");
                    }
                } else {
                    player.closeInventory();
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Сейчас уже активен бустер денег, попробуйте позже!");
                }
                return false;
            }
        });
        lore.clear();

        lore.add("§eЦена: §d119 DC");
        lore.add("");
        lore.add("§eПримечание: §7Активирует бустер денег для всех игроков на 1 час");
        customInventory.addItem(15, new CustomItem(Material.EMERALD).setName("§a§lГлобальный бустер денег х4").setLore(lore).build());
        customInventory.addMenuClickHandler(15, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                if (1 > Booster.getBoosterSizeTypeAntPr(player.getName(), BoosterType.MONEY)) {
                    if (Utils.getDonateCoin(player.getName()) >= 119) {
                        showDonateConfirm(player, 119, 6);
                    } else {
                        player.closeInventory();
                        player.sendMessage("§7[§e§lPrison §6§lPit§7]§c На вашем счету недостаточно DC!");
                    }
                } else {
                    player.closeInventory();
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Сейчас уже активен бустер денег, попробуйте позже!");
                }
                return false;
            }
        });
        customInventory.addItem(18, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName("§bВернутся назад").setSkullOwner("MHF_ArrowLeft").build());
        customInventory.addMenuClickHandler(18, new CustomInventory.MenuClickHandler() {
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                showDonateBoostersMenu(player);
                return false;
            }
        });
        customInventory.addItem(26, new CustomItem(Material.DIAMOND_BLOCK).setName("§eБаланс: §d" + Utils.getDonateCoin(player.getName()) + " DC.").build());
        customInventory.addMenuClickHandler(26, new CustomInventory.MenuClickHandler() {
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                player.closeInventory();
                player.sendMessage("§6Пополнить баланс можно на нашем сайте в личном кабинете.");
                return false;
            }
        });
        customInventory.open(player);
    }

    public static void showShardsBoostersMenu(Player player) {
        CustomInventory customInventory = new CustomInventory("§d§lОсколки");
        List<String> lore = new ArrayList<>();

        for (int i = 0; i < 27; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }
        lore.add("§eЦена: §d39 DC");
        lore.add("");
        lore.add("§eПримечание: §7Активирует бустер осколков для всех игроков на 1 час");
        customInventory.addItem(11, new CustomItem(Material.PRISMARINE_SHARD).setName("§d§lГлобальный бустер осколков х2").setLore(lore).build());
        customInventory.addMenuClickHandler(11, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                if (1 > Booster.getBoosterSizeTypeAntPr(player.getName(), BoosterType.SHARDS)) {
                    if (Utils.getDonateCoin(player.getName()) >= 39) {
                        showDonateConfirm(player, 39, 1);
                    } else {
                        player.closeInventory();
                        player.sendMessage("§7[§e§lPrison §6§lPit§7]§c На вашем счету недостаточно DC!");
                    }
                } else {
                    player.closeInventory();
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Сейчас уже активен бустер осколков, попробуйте позже!");
                }
                return false;
            }
        });
        lore.clear();

        lore.add("§eЦена: §d79 DC");
        lore.add("");
        lore.add("§eПримечание: §7Активирует бустер осколков для всех игроков на 1 час");
        customInventory.addItem(13, new CustomItem(Material.PRISMARINE_SHARD).setName("§d§lГлобальный бустер осколков х3").setLore(lore).build());
        customInventory.addMenuClickHandler(13, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                if (1 > Booster.getBoosterSizeTypeAntPr(player.getName(), BoosterType.SHARDS)) {
                    if (Utils.getDonateCoin(player.getName()) >= 79) {
                        showDonateConfirm(player, 79, 2);
                    } else {
                        player.closeInventory();
                        player.sendMessage("§7[§e§lPrison §6§lPit§7]§c На вашем счету недостаточно DC!");
                    }
                } else {
                    player.closeInventory();
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Сейчас уже активен бустер осколков, попробуйте позже!");
                }
                return false;
            }
        });
        lore.clear();

        lore.add("§eЦена: §d119 DC");
        lore.add("");
        lore.add("§eПримечание: §7Активирует бустер осколков для всех игроков на 1 час");
        customInventory.addItem(15, new CustomItem(Material.PRISMARINE_SHARD).setName("§d§lГлобальный бустер осколков х4").setLore(lore).build());
        customInventory.addMenuClickHandler(15, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                if (1 > Booster.getBoosterSizeTypeAntPr(player.getName(), BoosterType.SHARDS)) {
                    if (Utils.getDonateCoin(player.getName()) >= 119) {
                        showDonateConfirm(player, 119, 3);
                    } else {
                        player.closeInventory();
                        player.sendMessage("§7[§e§lPrison §6§lPit§7]§c На вашем счету недостаточно DC!");
                    }
                } else {
                    player.closeInventory();
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Сейчас уже активен бустер осколков, попробуйте позже!");
                }
                return false;
            }
        });
        customInventory.addItem(18, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName("§bВернутся назад").setSkullOwner("MHF_ArrowLeft").build());
        customInventory.addMenuClickHandler(18, new CustomInventory.MenuClickHandler() {
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                showDonateBoostersMenu(player);
                return false;
            }
        });
        customInventory.addItem(26, new CustomItem(Material.DIAMOND_BLOCK).setName("§eБаланс: §d" + Utils.getDonateCoin(player.getName()) + " DC.").build());
        customInventory.addMenuClickHandler(26, new CustomInventory.MenuClickHandler() {
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                player.closeInventory();
                player.sendMessage("§6Пополнить баланс можно на нашем сайте в личном кабинете.");
                return false;
            }
        });
        customInventory.open(player);
    }

    public static void showDonateItemsMenu(Player player) {
        CustomInventory customInventory = new CustomInventory("§e§lПредметы");
        List<String> lore = new ArrayList<>();
        lore.add("§eЦена: §d39 DC");
        lore.add("");
        lore.add("§eПримечание: §7Данный токен можно обменять на бонусы");
        lore.add("§7у §b§lЛегезо§7. Бонусы бывают: Автоматические блоки, Больше денег,");
        lore.add("§7Быстрая кирка, Больше осколков.");
        ItemStack token = new ItemStack(Material.FIREWORK_CHARGE);
        ItemMeta meta = token.getItemMeta();
        meta.setDisplayName("§b§lТокен");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.setLore(lore);
        FireworkEffectMeta metaFw = (FireworkEffectMeta) meta;
        FireworkEffect aa = FireworkEffect.builder().withColor(Color.AQUA).build();
        metaFw.setEffect(aa);
        token.setItemMeta(metaFw);
        lore.clear();

        for (int i = 0; i < 27; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }

        customInventory.addItem(12, token);
        customInventory.addMenuClickHandler(12, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                if (Utils.getDonateCoin(player.getName()) >= 39) {
                    showDonateConfirm(player, 39, 0);
                } else {
                    player.closeInventory();
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§c На вашем счету недостаточно DC!");
                }
                return false;
            }
        });
        lore.clear();

        lore.add("§eЦена: §d119 DC");
        lore.add("");
        lore.add("§eПримечание: §7Позволяет создать клан не имея 10 звёзд и 10000 осколков");
        customInventory.addItem(14, new CustomItem(Material.PAPER).setName("§e§lСоздание клана").setLore(lore).build());
        customInventory.addMenuClickHandler(14, new CustomInventory.MenuClickHandler() {
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                if (Utils.getDonateCoin(player.getName()) >= 119) {
                    showDonateConfirm(player, 119, 7);
                } else {
                    player.closeInventory();
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§c На вашем счету недостаточно DC!");
                }
                return false;
            }
        });

        customInventory.addItem(18, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName("§bВернутся назад").setSkullOwner("MHF_ArrowLeft").build());
        customInventory.addMenuClickHandler(18, new CustomInventory.MenuClickHandler() {
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                showDonateMenu(player);
                return false;
            }
        });
        customInventory.addItem(26, new CustomItem(Material.DIAMOND_BLOCK).setName("§eБаланс: §d" + Utils.getDonateCoin(player.getName()) + " DC.").build());
        customInventory.addMenuClickHandler(26, new CustomInventory.MenuClickHandler() {
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                player.closeInventory();
                player.sendMessage("§6Пополнить баланс можно на нашем сайте в личном кабинете.");
                return false;
            }
        });
        customInventory.open(player);
    }

    public static void showDonateConfirm(Player player, int pek, int donateNumber) {
        CustomInventory customInventory = new CustomInventory("§a§lПодтверждение покупки");
        List<String> lore = new ArrayList<>();
        PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());

        for (int i = 0; i < 27; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }

        customInventory.addItem(11, new CustomItem(Material.REDSTONE_BLOCK).setName("§cОтклонить").build());
        customInventory.addMenuClickHandler(11, new CustomInventory.MenuClickHandler() {
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                showDonateMenu(player);
                return false;
            }
        });

        customInventory.addItem(15, new CustomItem(Material.EMERALD_BLOCK).setName("§aПодтвердить").build());
        customInventory.addMenuClickHandler(15, new CustomInventory.MenuClickHandler() {
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                if (Utils.getDonateCoin(player.getName()) >= pek) {
                    switch (donateNumber) {
                        case 0: {
                            prisonPitPlayer.setTokens(prisonPitPlayer.getTokens() + 1);
                            Utils.buyDonate(player.getName(), pek);
                            ItemStack token = new ItemStack(Material.FIREWORK_CHARGE, PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getTokens());
                            ItemMeta meta = token.getItemMeta();
                            meta.setDisplayName("§b§lТокен");
                            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                            FireworkEffectMeta metaFw = (FireworkEffectMeta) meta;
                            FireworkEffect aa = FireworkEffect.builder().withColor(Color.AQUA).build();
                            metaFw.setEffect(aa);
                            token.setItemMeta(metaFw);

                            player.getInventory().setItem(7, token);
                            player.sendMessage("§7[§e§lPrison §6§lPit§7]§a Успешно! Спасибо за поддержку проекта!");
                            player.closeInventory();
                            break;
                        }
                        case 1: {
                            Booster booster = new Booster(BoosterType.SHARDS, player.getName(), 2, 60);
                            booster.active();
                            Utils.buyDonate(player.getName(), pek);
                            player.sendMessage("§7[§e§lPrison §6§lPit§7]§a Успешно! Спасибо за поддержку проекта!");
                            player.closeInventory();
                            break;
                        }
                        case 2: {
                            Booster booster = new Booster(BoosterType.SHARDS, player.getName(), 3, 60);
                            booster.active();
                            Utils.buyDonate(player.getName(), pek);
                            player.sendMessage("§7[§e§lPrison §6§lPit§7]§a Успешно! Спасибо за поддержку проекта!");
                            player.closeInventory();
                            break;
                        }
                        case 3: {
                            Booster booster = new Booster(BoosterType.SHARDS, player.getName(), 4, 60);
                            booster.active();
                            Utils.buyDonate(player.getName(), pek);
                            player.sendMessage("§7[§e§lPrison §6§lPit§7]§a Успешно! Спасибо за поддержку проекта!");
                            player.closeInventory();
                            break;
                        }
                        case 4: {
                            Booster booster = new Booster(BoosterType.MONEY, player.getName(), 2, 60);
                            booster.active();
                            Utils.buyDonate(player.getName(), pek);
                            player.sendMessage("§7[§e§lPrison §6§lPit§7]§a Успешно! Спасибо за поддержку проекта!");
                            player.closeInventory();
                            break;
                        }
                        case 5: {
                            Booster booster = new Booster(BoosterType.MONEY, player.getName(), 3, 60);
                            booster.active();
                            Utils.buyDonate(player.getName(), pek);
                            player.sendMessage("§7[§e§lPrison §6§lPit§7]§a Успешно! Спасибо за поддержку проекта!");
                            player.closeInventory();
                            break;
                        }
                        case 6: {
                            Booster booster = new Booster(BoosterType.MONEY, player.getName(), 4, 60);
                            booster.active();
                            Utils.buyDonate(player.getName(), pek);
                            player.sendMessage("§7[§e§lPrison §6§lPit§7]§a Успешно! Спасибо за поддержку проекта!");
                            player.closeInventory();
                            break;
                        }
                        case 7:{
                            player.getInventory().setItem(4,new CustomItem(Material.PAPER).setName("§e§lСоздание клана").build());
                            Utils.buyDonate(player.getName(), pek);
                            player.sendMessage("§7[§e§lPrison §6§lPit§7]§a Успешно! Спасибо за поддержку проекта!");
                            player.closeInventory();
                            break;
                        }
                    }
                } else {
                    player.closeInventory();
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§c На вашем счету недостаточно DC!");
                }
                return false;
            }
        });
        customInventory.open(player);
    }

    public static void showOptionsMenu(Player player) {
        CustomInventory customInventory = new CustomInventory("§6§lНастройки");
        List<String> lore = new ArrayList<>();
        customInventory.addMenuCloseHandler(new CustomInventory.MenuCloseHandler() {
            @Override
            public void onClose(Player player) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.updateInventory();
                    }
                }.runTaskLater(Main.instance, 2);
            }
        });

        for (int i = 0; i < 27; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }

        customInventory.addItem(13, new CustomItem((stormDisable.contains(player.getName()) ? Material.REDSTONE : Material.EMERALD), 1).setName(stormDisable.contains(player.getName()) ? "§c§lШторм отключен" : "§a§lШторм включён").setLore(lore).build());
        customInventory.addMenuClickHandler(13, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                if(PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getNumpriv() > 0) {
                    if (stormDisable.contains(player.getName())) {
                        stormDisable.remove(player.getName());
                    } else {
                        stormDisable.add(player.getName());
                    }
                    showOptionsMenu(player);
                } else {
                    player.closeInventory();
                    player.sendMessage("§7[§e§lPrison §6§lPit§7]§e Отключение шторма доступно с привелегии §aVIP§e.");
                }
                return false;
            }
        });
        customInventory.addItem(18, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName("§bВернутся назад").setSkullOwner("MHF_ArrowLeft").build());
        customInventory.addMenuClickHandler(18, new CustomInventory.MenuClickHandler() {
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                showOtherMenu(player);
                return false;
            }
        });
        customInventory.open(player);
    }

    public static void showUpgradesMenu(Player player) {
        PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
        CustomInventory customInventory = new CustomInventory("§b§lУлучшения");
        List<String> lore = new ArrayList<>();
        customInventory.addMenuCloseHandler(new CustomInventory.MenuCloseHandler() {
            @Override
            public void onClose(Player player) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.updateInventory();
                    }
                }.runTaskLater(Main.instance, 2);
            }
        });

        for (int i = 0; i < 36; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }
        if (choiseHashMap.containsKey(player)) {
            if (choiseHashMap.get(player).equals("Деньги")) {
                lore.add("");
                lore.add("§7Кирка копает немного быстрее");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getPickaxe_level() + "/17");
                if (prisonPitPlayer.getPickaxe_level() >= 17) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(19, new CustomItem(Material.DIAMOND_PICKAXE, 1).setName("§e§lБыстрая кирка").setLore(lore).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build());
                    customInventory.addMenuClickHandler(19, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                    lore.clear();
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getMoney(), 1000 * getPowPickaxe(prisonPitPlayer.getPickaxe_level())) + Utils.getMoney(1000 * getPowPickaxe(prisonPitPlayer.getPickaxe_level())) + "$");
                    lore.add("§eПКМ - прокачать на весь баланс");
                    customInventory.addItem(19, new CustomItem(Material.DIAMOND_PICKAXE, 1).setName("§e§lБыстрая кирка").setLore(lore).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build());
                    customInventory.addMenuClickHandler(19, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (arg3.isRightClicked()) {
                                while (prisonPitPlayer.getMoney() >= 1000 * getPowPickaxe(prisonPitPlayer.getPickaxe_level()) && prisonPitPlayer.getPickaxe_level() < 17) {
                                    prisonPitPlayer.setMoney(prisonPitPlayer.getMoney() - 1000 * getPowPickaxe(prisonPitPlayer.getPickaxe_level()));
                                    prisonPitPlayer.setPickaxe_level(prisonPitPlayer.getPickaxe_level() + 1);
                                }
                                player.getInventory().setItem(0, Pickaxes.getPickaxe(prisonPitPlayer.getPickaxe_level()));
                                showUpgradesMenu(player);
                            } else {
                                if (prisonPitPlayer.getMoney() >= 1000 * getPowPickaxe(prisonPitPlayer.getPickaxe_level())) {
                                    prisonPitPlayer.setMoney(prisonPitPlayer.getMoney() - 1000 * getPowPickaxe(prisonPitPlayer.getPickaxe_level()));
                                    prisonPitPlayer.setPickaxe_level(prisonPitPlayer.getPickaxe_level() + 1);
                                    player.getInventory().setItem(0, Pickaxes.getPickaxe(prisonPitPlayer.getPickaxe_level()));
                                    showUpgradesMenu(player);
                                }
                            }
                            return false;
                        }
                    });
                    lore.clear();
                }
                lore.add("");
                lore.add("§7Блоки вскапываются на уровень выше");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getBoosterMoney_level() + "/355");
                int rank = 0;
                int level = prisonPitPlayer.getBoosterMoney_level();
                while (level >= 18) {
                    level = level - 18;
                    rank++;
                }
                if (prisonPitPlayer.getPickaxe_level() >= 355) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(20, new CustomItem(Material.CONCRETE, 1, (byte) rank).setName("§e§lЛучшие блоки").setLore(lore).build());
                    customInventory.addMenuClickHandler(20, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                    lore.clear();
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getMoney(), 20000 * getPowBoosterBlocks(prisonPitPlayer.getBoosterMoney_level())) + Utils.getMoney(20000 * getPowBoosterBlocks(prisonPitPlayer.getBoosterMoney_level())) + "$");
                    lore.add("§eПКМ - прокачать на весь баланс");
                    customInventory.addItem(20, new CustomItem(Material.CONCRETE, 1, (byte) rank).setName("§e§lЛучшие блоки").setLore(lore).build());
                    customInventory.addMenuClickHandler(20, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (arg3.isRightClicked()) {
                                if (prisonPitPlayer.getMoney() >= 20000 * getPowBoosterBlocks(prisonPitPlayer.getBoosterMoney_level())) {
                                    int prevlvl = prisonPitPlayer.getBoosterMoney_level();
                                    int prevrank = 0;
                                    int prevlevel = prevlvl;
                                    while (prevlevel >= 18) {
                                        prevlevel = prevlevel - 18;
                                        prevrank++;
                                    }
                                    while (prisonPitPlayer.getMoney() >= 20000 * getPowBoosterBlocks(prisonPitPlayer.getBoosterMoney_level()) && prisonPitPlayer.getBoosterMoney_level() < 355) {
                                        prisonPitPlayer.setMoney(prisonPitPlayer.getMoney() - 20000 * getPowBoosterBlocks(prisonPitPlayer.getBoosterMoney_level()));
                                        prisonPitPlayer.setBoosterMoney_level(prisonPitPlayer.getBoosterMoney_level() + 1);
                                    }
                                    if (prisonPitPlayer.getMaxBoosterMoney_level() < prisonPitPlayer.getBoosterMoney_level()) {
                                        prisonPitPlayer.setMaxBoosterMoney_level(prisonPitPlayer.getBoosterMoney_level());
                                    }
                                    int rank = 0;
                                    int level = prisonPitPlayer.getBoosterMoney_level();
                                    while (level >= 18) {
                                        level = level - 18;
                                        rank++;
                                    }
                                    if(prevrank < rank) {
                                        ShaftUpdaterForSinglePlayer.update(player);
                                    }
                                    showUpgradesMenu(player);
                                }
                            } else {
                                if (prisonPitPlayer.getMoney() >= 20000 * getPowBoosterBlocks(prisonPitPlayer.getBoosterMoney_level())) {
                                    prisonPitPlayer.setMoney(prisonPitPlayer.getMoney() - 20000 * getPowBoosterBlocks(prisonPitPlayer.getBoosterMoney_level()));
                                    prisonPitPlayer.setBoosterMoney_level(prisonPitPlayer.getBoosterMoney_level() + 1);
                                    if (prisonPitPlayer.getMaxBoosterMoney_level() < prisonPitPlayer.getBoosterMoney_level()) {
                                        prisonPitPlayer.setMaxBoosterMoney_level(prisonPitPlayer.getBoosterMoney_level());
                                    }
                                    if (prisonPitPlayer.getBoosterMoney_level() % 18 == 0) {
                                        ShaftUpdaterForSinglePlayer.update(player);
                                    }
                                    showUpgradesMenu(player);
                                }
                            }
                            return false;
                        }
                    });
                    lore.clear();
                }

                lore.add("");
                lore.add("§7+0.005% к шансу получение Осколка при копании блока");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getShardsFromBlock_level() + "/175");
                if (prisonPitPlayer.getShardsFromBlock_level() >= 175) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(21, new CustomItem(Material.PRISMARINE_SHARD, 1).setName("§e§lБольше осколков").setLore(lore).build());
                    customInventory.addMenuClickHandler(21, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                    lore.clear();
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getMoney(), 250000 * getPowPickaxe(prisonPitPlayer.getShardsFromBlock_level())) + Utils.getMoney(250000 * getPowPickaxe(prisonPitPlayer.getShardsFromBlock_level())) + "$");
                    lore.add("§eПКМ - прокачать на весь баланс");
                    customInventory.addItem(21, new CustomItem(Material.PRISMARINE_SHARD, 1).setName("§e§lБольше осколков").setLore(lore).build());
                    customInventory.addMenuClickHandler(21, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (arg3.isRightClicked()) {
                                while (prisonPitPlayer.getMoney() >= 250000 * getPowPickaxe(prisonPitPlayer.getShardsFromBlock_level()) && prisonPitPlayer.getShardsFromBlock_level() < 175) {
                                    prisonPitPlayer.setMoney(prisonPitPlayer.getMoney() - 250000 * getPowPickaxe(prisonPitPlayer.getShardsFromBlock_level()));
                                    prisonPitPlayer.setShardsFromBlock_level(prisonPitPlayer.getShardsFromBlock_level() + 1);
                                }
                                showUpgradesMenu(player);
                            } else {
                                if (prisonPitPlayer.getMoney() >= 250000 * getPowPickaxe(prisonPitPlayer.getShardsFromBlock_level())) {
                                    prisonPitPlayer.setMoney(prisonPitPlayer.getMoney() - 250000 * getPowPickaxe(prisonPitPlayer.getShardsFromBlock_level()));
                                    prisonPitPlayer.setShardsFromBlock_level(prisonPitPlayer.getShardsFromBlock_level() + 1);
                                    showUpgradesMenu(player);
                                }
                            }
                            return false;
                        }
                    });
                    lore.clear();
                }

                lore.add("");
                lore.add("§7+0.05% к шансу, что вскопается 2 блока сразу.");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getDoubleBlock_level() + "/50");
                if (prisonPitPlayer.getDoubleBlock_level() >= 50) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(23, new CustomItem(Material.DISPENSER, 1).setName("§e§lДвойной блок").setLore(lore).build());
                    customInventory.addMenuClickHandler(23, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getMoney(), 800000 * getPowPickaxe(prisonPitPlayer.getDoubleBlock_level())) + Utils.getMoney(800000 * getPowPickaxe(prisonPitPlayer.getDoubleBlock_level())) + "$");
                    lore.add("§eПКМ - прокачать на весь баланс");
                    customInventory.addItem(23, new CustomItem(Material.DISPENSER, 1).setName("§e§lДвойной блок").setLore(lore).build());
                    customInventory.addMenuClickHandler(23, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (arg3.isRightClicked()) {
                                while (prisonPitPlayer.getMoney() >= 800000 * getPowPickaxe(prisonPitPlayer.getDoubleBlock_level()) && prisonPitPlayer.getDoubleBlock_level() < 50) {
                                    prisonPitPlayer.setMoney(prisonPitPlayer.getMoney() - 800000 * getPowPickaxe(prisonPitPlayer.getDoubleBlock_level()));
                                    prisonPitPlayer.setDoubleBlock_level(prisonPitPlayer.getDoubleBlock_level() + 1);
                                }
                                showUpgradesMenu(player);
                            } else {
                                if (prisonPitPlayer.getMoney() >= 800000 * getPowPickaxe(prisonPitPlayer.getDoubleBlock_level())) {
                                    prisonPitPlayer.setMoney(prisonPitPlayer.getMoney() - 800000 * getPowPickaxe(prisonPitPlayer.getDoubleBlock_level()));
                                    prisonPitPlayer.setDoubleBlock_level(prisonPitPlayer.getDoubleBlock_level() + 1);
                                    showUpgradesMenu(player);
                                }
                            }
                            return false;
                        }
                    });
                }
                lore.clear();

                lore.add("");
                lore.add("§7+0.05% к шансу что вскопается блок следующего уровня");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getBoosterMoneyII_level() + "/75");
                if (prisonPitPlayer.getBoosterMoneyII_level() >= 75) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(24, new CustomItem(Material.CONCRETE, 1, (byte) 2).setName("§e§lЛучшие блоки II").setLore(lore).build());
                    customInventory.addMenuClickHandler(24, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getMoney(), 1250000 * getPowBoosterMoneyII(prisonPitPlayer.getBoosterMoneyII_level())) + Utils.getMoney(1250000 * getPowBoosterMoneyII(prisonPitPlayer.getBoosterMoneyII_level())) + "$");
                    lore.add("§eПКМ - прокачать на весь баланс");
                    customInventory.addItem(24, new CustomItem(Material.CONCRETE, 1, (byte) 2).setName("§e§lЛучшие блоки II").setLore(lore).build());
                    customInventory.addMenuClickHandler(24, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (arg3.isRightClicked()) {
                                while (prisonPitPlayer.getMoney() >= 1250000 * getPowBoosterMoneyII(prisonPitPlayer.getBoosterMoneyII_level()) && prisonPitPlayer.getBoosterMoneyII_level() < 75) {
                                    prisonPitPlayer.setMoney(prisonPitPlayer.getMoney() - 1250000 * getPowBoosterMoneyII(prisonPitPlayer.getBoosterMoneyII_level()));
                                    prisonPitPlayer.setBoosterMoneyII_level(prisonPitPlayer.getBoosterMoneyII_level() + 1);
                                }
                                showUpgradesMenu(player);
                            } else {
                                if (prisonPitPlayer.getMoney() >= 1250000 * getPowBoosterMoneyII(prisonPitPlayer.getBoosterMoneyII_level())) {
                                    prisonPitPlayer.setMoney(prisonPitPlayer.getMoney() - 1250000 * getPowBoosterMoneyII(prisonPitPlayer.getBoosterMoneyII_level()));
                                    prisonPitPlayer.setBoosterMoneyII_level(prisonPitPlayer.getBoosterMoneyII_level() + 1);
                                    showUpgradesMenu(player);
                                }
                            }
                            return false;
                        }
                    });
                }
                lore.clear();

                lore.add("");
                lore.add("§7Увеличивает количество Осколков в шторме");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getStorm_level() + "/50");
                if (prisonPitPlayer.getStorm_level() >= 50) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(25, new CustomItem(Material.PRISMARINE_SHARD, 1).setName("§e§lЛучший шторм").setLore(lore).build());
                    customInventory.addMenuClickHandler(25, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getMoney(), 2250000 * getPowStormLevel(prisonPitPlayer.getStorm_level())) + Utils.getMoney(2250000 * getPowStormLevel(prisonPitPlayer.getStorm_level())) + "$");
                    lore.add("§eПКМ - прокачать на весь баланс");
                    customInventory.addItem(25, new CustomItem(Material.PRISMARINE_SHARD, 1).setName("§e§lЛучший шторм").setLore(lore).build());
                    customInventory.addMenuClickHandler(25, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (arg3.isRightClicked()) {
                                while (prisonPitPlayer.getMoney() >= 2250000 * getPowStormLevel(prisonPitPlayer.getStorm_level()) && prisonPitPlayer.getStorm_level() < 50) {
                                    prisonPitPlayer.setMoney(prisonPitPlayer.getMoney() - 2250000 * getPowStormLevel(prisonPitPlayer.getStorm_level()));
                                    prisonPitPlayer.setStorm_level(prisonPitPlayer.getStorm_level() + 1);
                                }
                                showUpgradesMenu(player);
                            } else {
                                if (prisonPitPlayer.getMoney() >= 2250000 * getPowStormLevel(prisonPitPlayer.getStorm_level())) {
                                    prisonPitPlayer.setMoney(prisonPitPlayer.getMoney() - 2250000 * getPowStormLevel(prisonPitPlayer.getStorm_level()));
                                    prisonPitPlayer.setStorm_level(prisonPitPlayer.getStorm_level() + 1);
                                    showUpgradesMenu(player);
                                }
                            }
                            return false;
                        }
                    });
                }
                lore.clear();
                if (prisonPitPlayer.getTotalStars() >= 15) {
                    customInventory.addItem(1, new CustomItem(Material.EMERALD, 1).setName("§a§lДеньги").setLore(lore).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
                    customInventory.addMenuClickHandler(1, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                    customInventory.addItem(4, new CustomItem(Material.PRISMARINE_SHARD, 1).setName("§d§lОсколки").setLore(lore).build());
                    customInventory.addMenuClickHandler(4, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            choiseHashMap.replace(player, "Осколки");
                            showUpgradesMenu(player);
                            return false;
                        }
                    });
                    customInventory.addItem(7, new CustomItem(Material.CLAY_BRICK, 1).setName("§e§lКирпичи").setLore(lore).build());
                    customInventory.addMenuClickHandler(7, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            choiseHashMap.replace(player, "Кирпичи");
                            showUpgradesMenu(player);
                            return false;
                        }
                    });
                } else {
                    customInventory.addItem(2, new CustomItem(Material.EMERALD, 1).setName("§a§lДеньги").setLore(lore).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
                    customInventory.addMenuClickHandler(2, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                    customInventory.addItem(6, new CustomItem(Material.PRISMARINE_SHARD, 1).setName("§d§lОсколки").setLore(lore).build());
                    customInventory.addMenuClickHandler(6, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            choiseHashMap.replace(player, "Осколки");
                            showUpgradesMenu(player);
                            return false;
                        }
                    });
                }
                customInventory.addItem(27, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName("§bВернутся назад").setSkullOwner("MHF_ArrowLeft").build());
                customInventory.addMenuClickHandler(27, new CustomInventory.MenuClickHandler() {
                    @Override
                    public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                        showGuiMenu(player);
                        return false;
                    }
                });
                lore.clear();
                customInventory.open(player);
            } else if (choiseHashMap.get(player).equals("Осколки")) {
                lore.add("");
                lore.add("§7Уровень повышается быстрее");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getLevelBoost_level() + "/20");
                if (prisonPitPlayer.getLevelBoost_level() >= 20) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(19, new CustomItem(Material.EXP_BOTTLE, 1).setName("§e§lПовышение уровня").setLore(lore).build());
                    customInventory.addMenuClickHandler(19, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getShards(), 20 + (20 * prisonPitPlayer.getLevelBoost_level())) + "" + (20 + (20 * prisonPitPlayer.getLevelBoost_level())) + " осколков");
                    customInventory.addItem(19, new CustomItem(Material.EXP_BOTTLE, 1).setName("§e§lПовышение уровня").setLore(lore).build());
                    customInventory.addMenuClickHandler(19, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getShards() >= 20 + (20 * prisonPitPlayer.getLevelBoost_level())) {
                                prisonPitPlayer.setShards(prisonPitPlayer.getShards() - (20 + (20 * prisonPitPlayer.getLevelBoost_level())));
                                prisonPitPlayer.setLevelBoost_level(prisonPitPlayer.getLevelBoost_level() + 1);
                                showUpgradesMenu(player);
                            }
                            return false;
                        }
                    });
                }
                lore.clear();

                lore.add("");
                lore.add("§7+0.2% к шансу получения осколка каждые 5 секунд");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getBoostershards_level() + "/20");
                if (prisonPitPlayer.getBoostershards_level() >= 20) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(20, new CustomItem(Material.PRISMARINE_SHARD, 1).setName("§e§lБольше осколков").setLore(lore).build());
                    customInventory.addMenuClickHandler(20, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getShards(), 30 + (20 * prisonPitPlayer.getBoostershards_level())) + "" + (30 + (20 * prisonPitPlayer.getBoostershards_level())) + " осколков");
                    customInventory.addItem(20, new CustomItem(Material.PRISMARINE_SHARD, 1).setName("§e§lБольше осколков").setLore(lore).build());
                    customInventory.addMenuClickHandler(20, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getShards() >= 30 + (20 * prisonPitPlayer.getBoostershards_level())) {
                                prisonPitPlayer.setShards(prisonPitPlayer.getShards() - (30 + (20 * prisonPitPlayer.getBoostershards_level())));
                                prisonPitPlayer.setBoostershards_level(prisonPitPlayer.getBoostershards_level() + 1);
                                showUpgradesMenu(player);
                            }
                            return false;
                        }
                    });
                }
                lore.clear();

                lore.add("");
                lore.add("§7Золотые блоки легче добыть на 5%");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getBoostergoldblocks_level() + "/40");
                if (prisonPitPlayer.getBoostergoldblocks_level() >= 40) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(21, new CustomItem(Material.GOLD_BLOCK, 1).setName("§e§lБольше золотых блоков").setLore(lore).build());
                    customInventory.addMenuClickHandler(21, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getShards(), 50 + (25 * prisonPitPlayer.getBoostergoldblocks_level())) + "" + (50 + (25 * prisonPitPlayer.getBoostergoldblocks_level())) + " осколков");
                    customInventory.addItem(21, new CustomItem(Material.GOLD_BLOCK, 1).setName("§e§lБольше золотых блоков").setLore(lore).build());
                    customInventory.addMenuClickHandler(21, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getShards() >= 50 + (25 * prisonPitPlayer.getBoostergoldblocks_level())) {
                                prisonPitPlayer.setShards(prisonPitPlayer.getShards() - (50 + (25 * prisonPitPlayer.getBoostergoldblocks_level())));
                                prisonPitPlayer.setBoostergoldblocks_level(prisonPitPlayer.getBoostergoldblocks_level() + 1);
                                showUpgradesMenu(player);
                            }
                            return false;
                        }
                    });
                }
                lore.clear();

                lore.add("");
                lore.add("§7+1% увеличения прибыли с Золотых блоков");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getBoosterprocentgoldblocks_level() + "/50");
                if (prisonPitPlayer.getBoosterprocentgoldblocks_level() >= 50) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(23, new CustomItem(Material.REDSTONE, 1).setName("§e§lЛучшее увеличение").setLore(lore).build());
                    customInventory.addMenuClickHandler(23, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getShards(), 300 + (50 * prisonPitPlayer.getBoosterprocentgoldblocks_level())) + "" + (300 + (50 * prisonPitPlayer.getBoosterprocentgoldblocks_level())) + " осколков");
                    customInventory.addItem(23, new CustomItem(Material.REDSTONE, 1).setName("§e§lЛучшее увеличение").setLore(lore).build());
                    customInventory.addMenuClickHandler(23, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getShards() >= (300 + (50 * prisonPitPlayer.getBoosterprocentgoldblocks_level()))) {
                                prisonPitPlayer.setShards(prisonPitPlayer.getShards() - (300 + (50 * prisonPitPlayer.getBoosterprocentgoldblocks_level())));
                                prisonPitPlayer.setBoosterprocentgoldblocks_level(prisonPitPlayer.getBoosterprocentgoldblocks_level() + 1);
                                showUpgradesMenu(player);
                            }
                            return false;
                        }
                    });
                }
                lore.clear();

                lore.add("");
                lore.add("§7Шторм возникает на 6 секунд быстрее!");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getMorestorms_level() + "/10");
                if (prisonPitPlayer.getMorestorms_level() >= 10) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(24, new CustomItem(Material.TIPPED_ARROW, 1).setName("§e§lБольше штормов").addItemFlag(ItemFlag.HIDE_POTION_EFFECTS).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).setLore(lore).build());
                    customInventory.addMenuClickHandler(24, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getShards(), 400 + (50 * prisonPitPlayer.getMorestorms_level())) + "" + (400 + (50 * prisonPitPlayer.getMorestorms_level())) + " осколков");
                    customInventory.addItem(24, new CustomItem(Material.TIPPED_ARROW, 1).setName("§e§lБольше штормов").addItemFlag(ItemFlag.HIDE_POTION_EFFECTS).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).setLore(lore).build());
                    customInventory.addMenuClickHandler(24, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getShards() >= 400 + (50 * prisonPitPlayer.getMorestorms_level())) {
                                prisonPitPlayer.setShards(prisonPitPlayer.getShards() - (400 + (50 * prisonPitPlayer.getMorestorms_level())));
                                prisonPitPlayer.setMorestorms_level(prisonPitPlayer.getMorestorms_level() + 1);
                                showUpgradesMenu(player);
                            }
                            return false;
                        }
                    });
                }
                lore.clear();

                lore.add("");
                lore.add("§7+0.001% шанс выпадения токена из блока");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getToken_level() + "/10");
                if (prisonPitPlayer.getToken_level() >= 10) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(25, new CustomItem(Material.FIREWORK_CHARGE, 1).setName("§e§lБольше токенов").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).addItemFlag(ItemFlag.HIDE_POTION_EFFECTS).setLore(lore).build());
                    customInventory.addMenuClickHandler(25, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getShards(), 500 + (100 * prisonPitPlayer.getToken_level())) + "" + (500 + (100 * prisonPitPlayer.getToken_level())) + " осколков");
                    customInventory.addItem(25, new CustomItem(Material.FIREWORK_CHARGE, 1).setName("§e§lБольше токенов").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).setLore(lore).addItemFlag(ItemFlag.HIDE_POTION_EFFECTS).build());
                    customInventory.addMenuClickHandler(25, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getShards() >= 500 + (100 * prisonPitPlayer.getToken_level())) {
                                prisonPitPlayer.setShards(prisonPitPlayer.getShards() - (500 + (100 * prisonPitPlayer.getToken_level())));
                                prisonPitPlayer.setToken_level(prisonPitPlayer.getToken_level() + 1);
                                showUpgradesMenu(player);
                            }
                            return false;
                        }
                    });
                }
                lore.clear();
                if (prisonPitPlayer.getTotalStars() >= 15) {
                    customInventory.addItem(1, new CustomItem(Material.EMERALD, 1).setName("§a§lДеньги").setLore(lore).build());
                    customInventory.addMenuClickHandler(1, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            choiseHashMap.replace(player, "Деньги");
                            showUpgradesMenu(player);
                            return false;
                        }
                    });
                    customInventory.addItem(4, new CustomItem(Material.PRISMARINE_SHARD, 1).setName("§a§lОсколки").setLore(lore).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
                    customInventory.addMenuClickHandler(4, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                    customInventory.addItem(7, new CustomItem(Material.CLAY_BRICK, 1).setName("§e§lКирпичи").setLore(lore).build());
                    customInventory.addMenuClickHandler(7, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            choiseHashMap.replace(player, "Кирпичи");
                            showUpgradesMenu(player);
                            return false;
                        }
                    });
                } else {
                    customInventory.addItem(2, new CustomItem(Material.EMERALD, 1).setName("§a§lДеньги").setLore(lore).build());
                    customInventory.addMenuClickHandler(2, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            choiseHashMap.replace(player, "Деньги");
                            showUpgradesMenu(player);
                            return false;
                        }
                    });
                    customInventory.addItem(6, new CustomItem(Material.PRISMARINE_SHARD, 1).setName("§a§lОсколки").setLore(lore).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
                    customInventory.addMenuClickHandler(6, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                }
                customInventory.addItem(27, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName("§bВернутся назад").setSkullOwner("MHF_ArrowLeft").build());
                customInventory.addMenuClickHandler(27, new CustomInventory.MenuClickHandler() {
                    @Override
                    public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                        showGuiMenu(player);
                        return false;
                    }
                });
                lore.clear();
                customInventory.open(player);
            } else if (choiseHashMap.get(player).equals("Кирпичи")) {
                lore.add("");
                lore.add("§7Получение фрагментов звёзд");
                lore.add("§7легче на 5%");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getMoreFragments_level() + "/100");
                if (prisonPitPlayer.getMoreFragments_level() >= 100) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(19, new CustomItem(Material.GHAST_TEAR, 1).setName("§e§lБольше фрагментов").setLore(lore).build());
                    customInventory.addMenuClickHandler(19, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getBricks(), 100 * (prisonPitPlayer.getMoreFragments_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreFragments_level()))) + "" + Utils.getMoney(100 * (prisonPitPlayer.getMoreFragments_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreFragments_level()))) + " кирпичей");
                    customInventory.addItem(19, new CustomItem(Material.GHAST_TEAR, 1).setName("§e§lБольше фрагментов").setLore(lore).build());
                    customInventory.addMenuClickHandler(19, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getBricks() >= 100 * (prisonPitPlayer.getMoreFragments_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreFragments_level()))) {
                                prisonPitPlayer.setBricks(prisonPitPlayer.getBricks() - (100 * (prisonPitPlayer.getMoreFragments_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreFragments_level()))));
                                prisonPitPlayer.setMoreFragments_level(prisonPitPlayer.getMoreFragments_level() + 1);
                                showUpgradesMenu(player);
                            }
                            return false;
                        }
                    });
                }
                lore.clear();

                lore.add("");
                lore.add("§7+1% К шансу получить");
                lore.add("§7х3 с осколка на шторме");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getMoreMagnets_level() + "/100");
                if (prisonPitPlayer.getMoreMagnets_level() >= 100) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(20, new CustomItem(Material.PRISMARINE_SHARD, 1).setName("§e§lБольше осколков").setLore(lore).build());
                    customInventory.addMenuClickHandler(20, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getBricks(), 10000 * (prisonPitPlayer.getMoreMagnets_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreMagnets_level()))) + "" + Utils.getMoney(10000 * (prisonPitPlayer.getMoreMagnets_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreMagnets_level()))) + " кирпичей");
                    customInventory.addItem(20, new CustomItem(Material.PRISMARINE_SHARD, 1).setName("§e§lБольше осколков").setLore(lore).build());
                    customInventory.addMenuClickHandler(20, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getBricks() >= 10000 * (prisonPitPlayer.getMoreMagnets_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreMagnets_level()))) {
                                prisonPitPlayer.setBricks(prisonPitPlayer.getBricks() - (10000 * (prisonPitPlayer.getMoreMagnets_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreMagnets_level()))));
                                prisonPitPlayer.setMoreMagnets_level(prisonPitPlayer.getMoreMagnets_level() + 1);
                                showUpgradesMenu(player);
                            }
                            return false;
                        }
                    });
                }
                lore.clear();

                lore.add("");
                lore.add("§7Снижает затраты двора");
                lore.add("§7блоков v2 в 4 раза");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getGBDiscound_level() + "/100");
                if (prisonPitPlayer.getGBDiscound_level() >= 100) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(21, new CustomItem(Material.GOLD_BLOCK, 1).setName("§e§lДешёвый двор").setLore(lore).build());
                    customInventory.addMenuClickHandler(21, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getBricks(), 1000000 * (prisonPitPlayer.getGBDiscound_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getGBDiscound_level()))) + "" + Utils.getMoney(1000000 * (prisonPitPlayer.getGBDiscound_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getGBDiscound_level()))) + " кирпичей");
                    customInventory.addItem(21, new CustomItem(Material.GOLD_BLOCK, 1).setName("§e§lДешёвый двор").setLore(lore).build());
                    customInventory.addMenuClickHandler(21, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getBricks() >= 1000000 * (prisonPitPlayer.getGBDiscound_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getGBDiscound_level()))) {
                                prisonPitPlayer.setBricks(prisonPitPlayer.getBricks() - (1000000 * (prisonPitPlayer.getGBDiscound_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getGBDiscound_level()))));
                                prisonPitPlayer.setGBDiscound_level(prisonPitPlayer.getGBDiscound_level() + 1);
                                showUpgradesMenu(player);
                            }
                            return false;
                        }
                    });
                }
                lore.clear();

                lore.add("");
                lore.add("§7+2% осколков при повышении уровня");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getMoreMagnetsII_level() + "/100");
                if (prisonPitPlayer.getMoreMagnetsII_level() >= 100) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(22, new CustomItem(Material.PRISMARINE_SHARD, 1).setName("§e§lБольше осколков II").setLore(lore).build());
                    customInventory.addMenuClickHandler(22, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getBricks(), 100000000 * (prisonPitPlayer.getMoreMagnetsII_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreMagnetsII_level()))) + "" + Utils.getMoney(100000000 * (prisonPitPlayer.getMoreMagnetsII_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreMagnetsII_level()))) + " кирпичей");
                    customInventory.addItem(22, new CustomItem(Material.PRISMARINE_SHARD, 1).setName("§e§lБольше осколков II").setLore(lore).build());
                    customInventory.addMenuClickHandler(22, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getBricks() >= 100000000 * (prisonPitPlayer.getMoreMagnetsII_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreMagnetsII_level()))) {
                                prisonPitPlayer.setBricks(prisonPitPlayer.getBricks() - (100000000 * (prisonPitPlayer.getMoreMagnetsII_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreMagnetsII_level()))));
                                prisonPitPlayer.setMoreMagnetsII_level(prisonPitPlayer.getMoreMagnetsII_level() + 1);
                                showUpgradesMenu(player);
                            }
                            return false;
                        }
                    });
                }
                lore.clear();

                lore.add("");
                lore.add("§7+1% к шансу получить х2");
                lore.add("§7от прибыли в секунду");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getMoreMoney_level() + "/100");
                if (prisonPitPlayer.getMoreMoney_level() >= 100) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(23, new CustomItem(Material.CONCRETE, 1, getBlockMaterial(prisonPitPlayer.getBoosterMoney_level())).setName("§e§lБольше денег").setLore(lore).build());
                    customInventory.addMenuClickHandler(23, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getBricks(), 10000000000.0 * (prisonPitPlayer.getMoreMoney_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreMoney_level()))) + "" + Utils.getMoney(10000000000.0 * (prisonPitPlayer.getMoreMoney_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreMoney_level()))) + " кирпичей");
                    customInventory.addItem(23, new CustomItem(Material.CONCRETE, 1, getBlockMaterial(prisonPitPlayer.getBoosterMoney_level())).setName("§e§lБольше денег").setLore(lore).build());
                    customInventory.addMenuClickHandler(23, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getBricks() >= 10000000000.0 * (prisonPitPlayer.getMoreMoney_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreMoney_level()))) {
                                prisonPitPlayer.setBricks(prisonPitPlayer.getBricks() - (10000000000.0 * (prisonPitPlayer.getMoreMoney_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreMoney_level()))));
                                prisonPitPlayer.setMoreMoney_level(prisonPitPlayer.getMoreMoney_level() + 1);
                                showUpgradesMenu(player);
                            }
                            return false;
                        }
                    });
                }
                lore.clear();

                lore.add("");
                lore.add("§7+1% к шансу получить");
                lore.add("§7осколок при уничтожении блока");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getMoreMagnetsIII_level() + "/100");
                if (prisonPitPlayer.getMoreMagnetsIII_level() >= 100) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(24, new CustomItem(Material.PRISMARINE_SHARD, 1).setName("§e§lБольше осколков III").setLore(lore).build());
                    customInventory.addMenuClickHandler(24, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getBricks(), 1000000000000.0 * (prisonPitPlayer.getMoreMagnetsIII_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreMagnetsIII_level()))) + "" + Utils.getMoney(1000000000000.0 * (prisonPitPlayer.getMoreMagnetsIII_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreMagnetsIII_level()))) + " кирпичей");
                    customInventory.addItem(24, new CustomItem(Material.PRISMARINE_SHARD, 1).setName("§e§lБольше осколков III").setLore(lore).build());
                    customInventory.addMenuClickHandler(24, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getBricks() >= 1000000000000.0 * (prisonPitPlayer.getMoreMagnetsIII_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreMagnetsIII_level()))) {
                                prisonPitPlayer.setBricks(prisonPitPlayer.getBricks() - (1000000000000.0 * (prisonPitPlayer.getMoreMagnetsIII_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreMagnetsIII_level()))));
                                prisonPitPlayer.setMoreMagnetsIII_level(prisonPitPlayer.getMoreMagnetsIII_level() + 1);
                                showUpgradesMenu(player);
                            }
                            return false;
                        }
                    });
                }
                lore.clear();

                lore.add("");
                lore.add("§7+1.25x кирпичей в секунду");
                lore.add("");
                lore.add("§6Уровень: §e" + prisonPitPlayer.getMoreBricks_level() + "/100");
                if (prisonPitPlayer.getMoreBricks_level() >= 100) {
                    lore.add("§e§lMAX");
                    customInventory.addItem(25, new CustomItem(Material.CLAY_BRICK, 1).setName("§e§lБольше кирпичей").setLore(lore).build());
                    customInventory.addMenuClickHandler(25, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                } else {
                    lore.add(getColorEnought(prisonPitPlayer.getBricks(), 100000000000000.0 * (prisonPitPlayer.getMoreBricks_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreBricks_level()))) + "" + Utils.getMoney(100000000000000.0 * (prisonPitPlayer.getMoreBricks_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreBricks_level()))) + " кирпичей");
                    customInventory.addItem(25, new CustomItem(Material.CLAY_BRICK, 1).setName("§e§lБольше кирпичей").setLore(lore).build());
                    customInventory.addMenuClickHandler(25, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getBricks() >= 100000000000000.0 * (prisonPitPlayer.getMoreBricks_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreBricks_level()))) {
                                prisonPitPlayer.setBricks(prisonPitPlayer.getBricks() - (100000000000000.0 * (prisonPitPlayer.getMoreBricks_level() == 0 ? 1 : Math.pow(4, prisonPitPlayer.getMoreBricks_level()))));
                                prisonPitPlayer.setMoreBricks_level(prisonPitPlayer.getMoreBricks_level() + 1);
                                showUpgradesMenu(player);
                            }
                            return false;
                        }
                    });
                }
                lore.clear();
                customInventory.addItem(1, new CustomItem(Material.EMERALD, 1).setName("§a§lДеньги").setLore(lore).build());
                customInventory.addMenuClickHandler(1, new CustomInventory.MenuClickHandler() {
                    public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                        choiseHashMap.replace(player, "Деньги");
                        showUpgradesMenu(player);
                        return false;
                    }
                });
                customInventory.addItem(4, new CustomItem(Material.PRISMARINE_SHARD, 1).setName("§a§lОсколки").setLore(lore).build());
                customInventory.addMenuClickHandler(4, new CustomInventory.MenuClickHandler() {
                    public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                        choiseHashMap.replace(player, "Осколки");
                        showUpgradesMenu(player);
                        return false;
                    }
                });
                customInventory.addItem(7, new CustomItem(Material.CLAY_BRICK, 1).setName("§e§lКирпичи").setLore(lore).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
                customInventory.addMenuClickHandler(7, new CustomInventory.MenuClickHandler() {
                    public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                        return false;
                    }
                });
                customInventory.addItem(27, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName("§bВернутся назад").setSkullOwner("MHF_ArrowLeft").build());
                customInventory.addMenuClickHandler(27, new CustomInventory.MenuClickHandler() {
                    @Override
                    public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                        showGuiMenu(player);
                        return false;
                    }
                });
                lore.clear();
                customInventory.open(player);
            }
        } else {
            choiseHashMap.put(player, "Деньги");
            showUpgradesMenu(player);
        }
    }

    public static ChatColor getColorEnought(double balance, double need) {
        if (balance >= need) {
            return ChatColor.GREEN;
        }
        return ChatColor.RED;
    }

    public static double getPowBoosterBlocks(int levelBoosterBlocks) {
        double count = 1;
        for (int i = 0; i < levelBoosterBlocks; i++) {
            if (i >= 20) {
                if (i >= 30 && i % 10 == 0) {
                    count = count * 32;
                } else if (i % 5 == 0) {
                    count = count * 8;
                } else {
                    count = count * 4;
                }
            } else {
                count = count * 4;
            }
        }
        return count;
    }

    public static double getPowPickaxe(int levelpickaxe) {
        double count = 1;
        for (int i = 0; i < levelpickaxe; i++) {
            count = count * 2;
        }
        return count;
    }

    public static double getPowBoosterMoneyII(int level) {
        double count = 1;
        for (int i = 0; i < level; i++) {
            count = count * 3;
        }
        return count;
    }

    public static double getPowStormLevel(int level) {
        double count = 1;
        for (int i = 0; i < level; i++) {
            count = count * 4;
        }
        return count;
    }

    public static byte getBlockMaterial(int level) {
        int rank = 0;
        while (level >= 18) {
            level = level - 18;
            rank++;
        }
        if (rank > 15) {
            rank = 15;
        }
        return (byte) rank;
    }

    public static void showMenuBlocks(Player player, int page) {
        CustomInventory customInventory = new CustomInventory("§a§lУровень блоков страница §e§l" + (page + 1));
        List<String> lore = new ArrayList<>();
        customInventory.addMenuCloseHandler(new CustomInventory.MenuCloseHandler() {
            @Override
            public void onClose(Player player) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.updateInventory();
                    }
                }.runTaskLater(Main.instance, 2);
            }
        });
        int startblock = 45 * page;
        PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
        int[] stars = prisonPitPlayer.getStars();
        int minstar = 0;
        double boosterStar = 1;
        for(int star : stars){
            if(star>0){
                boosterStar = boosterStar * star;
            }
        }
        for (int i = 0; i < 45; i++) {
            lore.add("§7§lБазовая прибыль - " + Utils.getMoney(startblock + i == 0 ? 1 : Math.pow(3, (startblock + i))));
            lore.add("§7§lТекущая прибыль - " + Utils.getMoney(startblock + i == 0 ? 1 * (1.0 + ((prisonPitPlayer.getGoldblocks() * (prisonPitPlayer.getBoosterprocentgoldblocks_level() + 1)) / 100.0)) * boosterStar : Math.pow(3, (startblock + i)) * (1.0 + ((prisonPitPlayer.getGoldblocks() * (prisonPitPlayer.getBoosterprocentgoldblocks_level() + 1)) / 100.0)) * boosterStar));
            if (startblock + i >= 48 && startblock + i < 102) {
                int l = startblock + i;
                int rank = 0;
                while (l >= 48) {
                    rank++;
                    l -= 6;
                }
                lore.add("§6§lЗолотые блоки - " + getBosterGB(rank) + "x");
            } else if (startblock + i >= 102) {
                int l = startblock + i;
                int rank = 0;
                while (l >= 102) {
                    rank++;
                    l -= 6;
                }
                lore.add("§e§lКирпичи - " + Utils.getMoney(4 * (Math.pow(8, rank - 1) == 0 ? 1 : Math.pow(8, rank - 1))) + "x");
            }

            if (prisonPitPlayer.getBoosterMoney_level() >= startblock + i) {
                customInventory.addItem(i, new CustomItem(Material.CONCRETE, 1, getBlockMaterial(i + startblock)).setName("§a§l" + (startblock + i) + " уровень").setLore(lore).build());
                customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                    public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                        return false;
                    }
                });
                lore.clear();
            } else {
                lore.clear();
                lore.add("§7§l???");
                if (prisonPitPlayer.getStars()[2] > 0) {
                    if (startblock + i >= 48 && startblock + i < 102) {
                        int l = startblock + i;
                        int rank = 0;
                        while (l >= 48) {
                            rank++;
                            l -= 6;
                        }
                        lore.add("§6§lЗолотые блоки - " + getBosterGB(rank) + "x");
                    } else if (startblock + i >= 102 && prisonPitPlayer.getTotalStars() >= 14) {
                        int l = startblock + i;
                        int rank = 0;
                        while (l >= 102) {
                            rank++;
                            l -= 6;
                        }
                        lore.add("§e§lКирпичи - " + Utils.getMoney(4 * (Math.pow(8, rank - 1) == 0 ? 1 : Math.pow(8, rank - 1))) + "x");
                    } else {
                        lore.add("§e§l???x");
                    }
                }
                customInventory.addItem(i, new CustomItem(Material.CONCRETE_POWDER, 1, (byte) 15).setName("???").setLore(lore).build());
                customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                    public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                        return false;
                    }
                });
                lore.clear();
            }
        }
        for (int i = 45; i < 54; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }
        if (page > 0) {
            customInventory.addItem(47, new CustomItem(Material.SIGN).setName("§a§lПредыдущая страница").build());
            customInventory.addMenuClickHandler(47, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    showMenuBlocks(player, page - 1);
                    return false;
                }
            });
        }
        if (page < 6) {
            customInventory.addItem(51, new CustomItem(Material.SIGN).setName("§a§lСледующая страница").build());
            customInventory.addMenuClickHandler(51, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    showMenuBlocks(player, page + 1);
                    return false;
                }
            });
        }
        customInventory.addItem(45, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName("§bВернутся назад").setSkullOwner("MHF_ArrowLeft").build());
        customInventory.addMenuClickHandler(45, new CustomInventory.MenuClickHandler() {
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                showGuiMenu(player);
                return false;
            }
        });
        customInventory.open(player);
    }

    public static int getBosterGB(int rank) {
        switch (rank) {
            case 1: {
                return 2;
            }
            case 2: {
                return 4;
            }
            case 3: {
                return 8;
            }
            case 4: {
                return 12;
            }
            case 5: {
                return 16;
            }
            case 6: {
                return 48;
            }
            case 7: {
                return 96;
            }
            case 8: {
                return 320;
            }
            case 9: {
                return 1024;
            }
        }
        return 1024;
    }

    public static void showMenuPrestige(Player player) {
        CustomInventory customInventory = new CustomInventory("§6§lПрестиж");
        PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
        List<String> lore = new ArrayList<>();
        customInventory.addMenuCloseHandler(new CustomInventory.MenuCloseHandler() {
            @Override
            public void onClose(Player player) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.updateInventory();
                    }
                }.runTaskLater(Main.instance, 2);
            }
        });
        if (prestigeChoiceHashMap.containsKey(player)) {
            if (prestigeChoiceHashMap.get(player).equals("Золотые блоки")) {
                for (int i = 0; i < 36; i++) {
                    customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
                    customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                }
                int GBAP = 0;
                double money = prisonPitPlayer.getNonspendmoney();
                while (money >= (100000000.0 * (1.0 - (prisonPitPlayer.getBoostergoldblocks_level() / 100.0))) * (GBAP == 0 ? 1 : Math.pow(1.05, GBAP))) {
                    money = money - (100000000.0 * (1.0 - (prisonPitPlayer.getBoostergoldblocks_level() / 100.0))) * (GBAP == 0 ? 1 : Math.pow(1.05, GBAP));
                    GBAP++;
                }
                int blockslevel = prisonPitPlayer.getBoosterMoney_level();
                if (blockslevel >= 48) {
                    int rank = 0;
                    while (blockslevel >= 48) {
                        rank++;
                        blockslevel -= 6;
                    }
                    lore.add("§6После престижа: §e+" + GBAP + " x" + getBosterGB(rank));
                } else {
                    lore.add("§6После престижа: §e+" + GBAP);
                }
                customInventory.addItem(12, new CustomItem(Material.GOLD_BLOCK).setName("§6§lЗолотые блоки - §e§l" + Utils.getMoney(prisonPitPlayer.getGoldblocks())).setLore(lore).build());
                customInventory.addMenuClickHandler(12, new CustomInventory.MenuClickHandler() {
                    public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                        return false;
                    }
                });
                lore.clear();

                lore.add("§7§lТекущее увеличение - §a§l" + prisonPitPlayer.getGoldblocks() * (1 + prisonPitPlayer.getBoosterprocentgoldblocks_level()) + "%");
                lore.add("§7§lУвеличение после престижа - §a§l" + (prisonPitPlayer.getGoldblocks() + GBAP) * (1 + prisonPitPlayer.getBoosterprocentgoldblocks_level()) + "%");
                customInventory.addItem(14, new CustomItem(Material.PAPER).setName("§b§lУвеличение за золотые блоки - §7§l" + Utils.getMoney(1 + prisonPitPlayer.getBoosterprocentgoldblocks_level()) + "%").setLore(lore).build());
                customInventory.addMenuClickHandler(14, new CustomInventory.MenuClickHandler() {
                    public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                        return false;
                    }
                });
                lore.clear();

                lore.add("");
                lore.add("§7§lПосле престижа вы потеряете:");
                lore.add("§7§lДеньги, обычные улучшения, прибыль и уровень.");
                customInventory.addItem(22, new CustomItem(Material.FEATHER).setName("§d§lПрестиж").setLore(lore).build());
                int finalGBAP = GBAP;
                customInventory.addMenuClickHandler(22, new CustomInventory.MenuClickHandler() {
                    public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                        if (finalGBAP > 0) {
                            confirmPrestige(player);
                        } else {
                            player.closeInventory();
                            player.sendMessage("§7[§c!§7]§e Делать престиж сейчас бессмысленно, для начала подкопите золотых блоков.");
                        }
                        return false;
                    }
                });
                lore.clear();

                customInventory.addItem(2, new CustomItem(Material.GOLD_BLOCK).setName("§6§lЗолотые блоки").setLore(lore).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
                customInventory.addMenuClickHandler(2, new CustomInventory.MenuClickHandler() {
                    public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                        return false;
                    }
                });

                customInventory.addItem(6, new CustomItem(Material.NETHER_STAR).setName("§e§lЗвёзды").setLore(lore).build());
                customInventory.addMenuClickHandler(6, new CustomInventory.MenuClickHandler() {
                    public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                        prestigeChoiceHashMap.replace(player, "Звёзды");
                        showMenuPrestige(player);
                        return false;
                    }
                });
                customInventory.addItem(27, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName("§bВернутся назад").setSkullOwner("MHF_ArrowLeft").build());
                customInventory.addMenuClickHandler(27, new CustomInventory.MenuClickHandler() {
                    @Override
                    public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                        showGuiMenu(player);
                        return false;
                    }
                });
                customInventory.open(player);
            } else if (prestigeChoiceHashMap.get(player).equals("Звёзды")) {
                int i = 0;
                for (int j = 0; j < 10; j++) {
                    if (prisonPitPlayer.getStars()[j] > 0) {
                        i++;
                    }
                }
                if (i >= 10) {
                    for (i = 0; i < 54; i++) {
                        customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
                        customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                                return false;
                            }
                        });
                    }
                    double boosterStar = 1;
                    for (int star : prisonPitPlayer.getStars()) {
                        if (star > 0) {
                            boosterStar = boosterStar * star;
                        }
                    }
                    lore.add("§aТекущее увеличение - §b§lx" + Utils.getMoney(boosterStar));
                    customInventory.addItem(13, new CustomItem(Material.GHAST_TEAR).setName("§e§lФрагменты звёзд - §f§l" + String.format("%.3f", prisonPitPlayer.getFragments())).setLore(lore).build());
                    customInventory.addMenuClickHandler(13, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            return false;
                        }
                    });
                    lore.clear();

                    lore.add("");
                    lore.add("§eДля улучшения необходимо:");
                    lore.add(" §6Золотые блоки - " + Utils.getMoney((getPrice4StarGB(prisonPitPlayer.getStars()[0]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))));
                    lore.add(" §dОсколки - " + (getPrice4StarShards(prisonPitPlayer.getStars()[0]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add(" §fФрагменты звёзд - " + (getPrice4StarFragments(prisonPitPlayer.getStars()[0]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add("");
                    lore.add("§aУлучшить");
                    customInventory.addItem(28, new CustomItem(Material.NETHER_STAR, prisonPitPlayer.getStars()[0]).setName("§6§lЗвёзда " + prisonPitPlayer.getStars()[0] + " уровня").setLore(lore).build());
                    customInventory.addMenuClickHandler(28, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getGoldblocks() >= (getPrice4StarGB(prisonPitPlayer.getStars()[0]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getShards() >= (getPrice4StarShards(prisonPitPlayer.getStars()[0]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getFragments() >= (getPrice4StarFragments(prisonPitPlayer.getStars()[0]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))) {
                                prisonPitPlayer.setGoldblocks(prisonPitPlayer.getGoldblocks() - (getPrice4StarGB(prisonPitPlayer.getStars()[0]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setShards(prisonPitPlayer.getShards() - (getPrice4StarShards(prisonPitPlayer.getStars()[0]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setFragments(prisonPitPlayer.getFragments() - (getPrice4StarFragments(prisonPitPlayer.getStars()[0]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                int[] stars = prisonPitPlayer.getStars();
                                stars[0] = stars[0] + 1;
                                prisonPitPlayer.setStars(stars);
                                showMenuPrestige(player);
                                player.sendMessage("§7[§a!§7]§e Звезда улучшена!");
                                Utils.calcTotalStars(player);
                                if (prisonPitPlayer.getTotalStars() >= 15 && prisonPitPlayer.getBrickspersec() < 1) {
                                    prisonPitPlayer.setBrickspersec(1.0);
                                }
                            } else {
                                player.closeInventory();
                                player.sendMessage("§7[§c!§7]§e Условия не выполнены!");
                            }
                            return false;
                        }
                    });
                    lore.clear();

                    lore.add("");
                    lore.add("§eДля улучшения необходимо:");
                    lore.add(" §6Золотые блоки - " + Utils.getMoney((getPrice4StarGB(prisonPitPlayer.getStars()[1]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))));
                    lore.add(" §dОсколки - " + (getPrice4StarShards(prisonPitPlayer.getStars()[1]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add(" §fФрагменты звёзд - " + (getPrice4StarFragments(prisonPitPlayer.getStars()[1]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add("");
                    lore.add("§aУлучшить");
                    customInventory.addItem(20, new CustomItem(Material.NETHER_STAR, prisonPitPlayer.getStars()[1]).setName("§6§lЗвёзда " + prisonPitPlayer.getStars()[1] + " уровня").setLore(lore).build());
                    customInventory.addMenuClickHandler(20, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getGoldblocks() >= (getPrice4StarGB(prisonPitPlayer.getStars()[1]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getShards() >= (getPrice4StarShards(prisonPitPlayer.getStars()[1]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getFragments() >= (getPrice4StarFragments(prisonPitPlayer.getStars()[1]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))) {
                                prisonPitPlayer.setGoldblocks(prisonPitPlayer.getGoldblocks() - (getPrice4StarGB(prisonPitPlayer.getStars()[1]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setShards(prisonPitPlayer.getShards() - (getPrice4StarShards(prisonPitPlayer.getStars()[1]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setFragments(prisonPitPlayer.getFragments() - (getPrice4StarFragments(prisonPitPlayer.getStars()[1]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                int[] stars = prisonPitPlayer.getStars();
                                stars[1] = stars[1] + 1;
                                prisonPitPlayer.setStars(stars);
                                showMenuPrestige(player);
                                player.sendMessage("§7[§a!§7]§e Звезда улучшена!");
                                Utils.calcTotalStars(player);
                                if (prisonPitPlayer.getTotalStars() >= 15 && prisonPitPlayer.getBrickspersec() < 1) {
                                    prisonPitPlayer.setBrickspersec(1.0);
                                }
                            } else {
                                player.closeInventory();
                                player.sendMessage("§7[§c!§7]§e Условия не выполнены!");
                            }
                            return false;
                        }
                    });
                    lore.clear();

                    lore.add("");
                    lore.add("§eДля улучшения необходимо:");
                    lore.add(" §6Золотые блоки - " + Utils.getMoney((getPrice4StarGB(prisonPitPlayer.getStars()[2]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))));
                    lore.add(" §dОсколки - " + (getPrice4StarShards(prisonPitPlayer.getStars()[2]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add(" §fФрагменты звёзд - " + (getPrice4StarFragments(prisonPitPlayer.getStars()[2]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add("");
                    lore.add("§aУлучшить");
                    customInventory.addItem(38, new CustomItem(Material.NETHER_STAR, prisonPitPlayer.getStars()[2]).setName("§6§lЗвёзда " + prisonPitPlayer.getStars()[2] + " уровня").setLore(lore).build());
                    customInventory.addMenuClickHandler(38, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getGoldblocks() >= (getPrice4StarGB(prisonPitPlayer.getStars()[2]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getShards() >= (getPrice4StarShards(prisonPitPlayer.getStars()[2]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getFragments() >= (getPrice4StarFragments(prisonPitPlayer.getStars()[2]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))) {
                                prisonPitPlayer.setGoldblocks(prisonPitPlayer.getGoldblocks() - (getPrice4StarGB(prisonPitPlayer.getStars()[2]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setShards(prisonPitPlayer.getShards() - (getPrice4StarShards(prisonPitPlayer.getStars()[2]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setFragments(prisonPitPlayer.getFragments() - (getPrice4StarFragments(prisonPitPlayer.getStars()[2]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                int[] stars = prisonPitPlayer.getStars();
                                stars[2] = stars[2] + 1;
                                prisonPitPlayer.setStars(stars);
                                showMenuPrestige(player);
                                player.sendMessage("§7[§a!§7]§e Звезда улучшена!");
                                Utils.calcTotalStars(player);
                                if (prisonPitPlayer.getTotalStars() >= 15 && prisonPitPlayer.getBrickspersec() < 1) {
                                    prisonPitPlayer.setBrickspersec(1.0);
                                }
                            } else {
                                player.closeInventory();
                                player.sendMessage("§7[§c!§7]§e Условия не выполнены!");
                            }
                            return false;
                        }
                    });
                    lore.clear();

                    lore.add("");
                    lore.add("§eДля улучшения необходимо:");
                    lore.add(" §6Золотые блоки - " + Utils.getMoney((getPrice4StarGB(prisonPitPlayer.getStars()[3]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))));
                    lore.add(" §dОсколки - " + (getPrice4StarShards(prisonPitPlayer.getStars()[3]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add(" §fФрагменты звёзд - " + (getPrice4StarFragments(prisonPitPlayer.getStars()[3]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add("");
                    lore.add("§aУлучшить");
                    customInventory.addItem(30, new CustomItem(Material.NETHER_STAR, prisonPitPlayer.getStars()[3]).setName("§6§lЗвёзда " + prisonPitPlayer.getStars()[3] + " уровня").setLore(lore).build());
                    customInventory.addMenuClickHandler(30, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getGoldblocks() >= (getPrice4StarGB(prisonPitPlayer.getStars()[3]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getShards() >= (getPrice4StarShards(prisonPitPlayer.getStars()[3]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getFragments() >= (getPrice4StarFragments(prisonPitPlayer.getStars()[3]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))) {
                                prisonPitPlayer.setGoldblocks(prisonPitPlayer.getGoldblocks() - (getPrice4StarGB(prisonPitPlayer.getStars()[3]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setShards(prisonPitPlayer.getShards() - (getPrice4StarShards(prisonPitPlayer.getStars()[3]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setFragments(prisonPitPlayer.getFragments() - (getPrice4StarFragments(prisonPitPlayer.getStars()[3]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                int[] stars = prisonPitPlayer.getStars();
                                stars[3] = stars[3] + 1;
                                prisonPitPlayer.setStars(stars);
                                showMenuPrestige(player);
                                player.sendMessage("§7[§a!§7]§e Звезда улучшена!");
                                Utils.calcTotalStars(player);
                                if (prisonPitPlayer.getTotalStars() >= 15 && prisonPitPlayer.getBrickspersec() < 1) {
                                    prisonPitPlayer.setBrickspersec(1.0);
                                }
                            } else {
                                player.closeInventory();
                                player.sendMessage("§7[§c!§7]§e Условия не выполнены!");
                            }
                            return false;
                        }
                    });
                    lore.clear();

                    lore.add("");
                    lore.add("§eДля улучшения необходимо:");
                    lore.add(" §6Золотые блоки - " + Utils.getMoney((getPrice4StarGB(prisonPitPlayer.getStars()[4]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))));
                    lore.add(" §dОсколки - " + (getPrice4StarShards(prisonPitPlayer.getStars()[4]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add(" §fФрагменты звёзд - " + (getPrice4StarFragments(prisonPitPlayer.getStars()[4]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add("");
                    lore.add("§aУлучшить");
                    customInventory.addItem(22, new CustomItem(Material.NETHER_STAR, prisonPitPlayer.getStars()[4]).setName("§6§lЗвёзда " + prisonPitPlayer.getStars()[4] + " уровня").setLore(lore).build());
                    customInventory.addMenuClickHandler(22, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getGoldblocks() >= (getPrice4StarGB(prisonPitPlayer.getStars()[4]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getShards() >= (getPrice4StarShards(prisonPitPlayer.getStars()[4]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getFragments() >= (getPrice4StarFragments(prisonPitPlayer.getStars()[4]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))) {
                                prisonPitPlayer.setGoldblocks(prisonPitPlayer.getGoldblocks() - (getPrice4StarGB(prisonPitPlayer.getStars()[4]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setShards(prisonPitPlayer.getShards() - (getPrice4StarShards(prisonPitPlayer.getStars()[4]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setFragments(prisonPitPlayer.getFragments() - (getPrice4StarFragments(prisonPitPlayer.getStars()[4]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                int[] stars = prisonPitPlayer.getStars();
                                stars[4] = stars[4] + 1;
                                prisonPitPlayer.setStars(stars);
                                showMenuPrestige(player);
                                player.sendMessage("§7[§a!§7]§e Звезда улучшена!");
                                Utils.calcTotalStars(player);
                                if (prisonPitPlayer.getTotalStars() >= 15 && prisonPitPlayer.getBrickspersec() < 1) {
                                    prisonPitPlayer.setBrickspersec(1.0);
                                }
                            } else {
                                player.closeInventory();
                                player.sendMessage("§7[§c!§7]§e Условия не выполнены!");
                            }
                            return false;
                        }
                    });
                    lore.clear();

                    lore.add("");
                    lore.add("§eДля улучшения необходимо:");
                    lore.add(" §6Золотые блоки - " + Utils.getMoney((getPrice4StarGB(prisonPitPlayer.getStars()[5]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))));
                    lore.add(" §dОсколки - " + (getPrice4StarShards(prisonPitPlayer.getStars()[5]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add(" §fФрагменты звёзд - " + (getPrice4StarFragments(prisonPitPlayer.getStars()[5]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add("");
                    lore.add("§aУлучшить");
                    customInventory.addItem(40, new CustomItem(Material.NETHER_STAR, prisonPitPlayer.getStars()[5]).setName("§6§lЗвёзда " + prisonPitPlayer.getStars()[5] + " уровня").setLore(lore).build());
                    customInventory.addMenuClickHandler(40, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getGoldblocks() >= (getPrice4StarGB(prisonPitPlayer.getStars()[5]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getShards() >= (getPrice4StarShards(prisonPitPlayer.getStars()[5]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getFragments() >= (getPrice4StarFragments(prisonPitPlayer.getStars()[5]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))) {
                                prisonPitPlayer.setGoldblocks(prisonPitPlayer.getGoldblocks() - (getPrice4StarGB(prisonPitPlayer.getStars()[5]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setShards(prisonPitPlayer.getShards() - (getPrice4StarShards(prisonPitPlayer.getStars()[5]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setFragments(prisonPitPlayer.getFragments() - (getPrice4StarFragments(prisonPitPlayer.getStars()[5]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                int[] stars = prisonPitPlayer.getStars();
                                stars[5] = stars[5] + 1;
                                prisonPitPlayer.setStars(stars);
                                showMenuPrestige(player);
                                player.sendMessage("§7[§a!§7]§e Звезда улучшена!");
                                Utils.calcTotalStars(player);
                                if (prisonPitPlayer.getTotalStars() >= 15 && prisonPitPlayer.getBrickspersec() < 1) {
                                    prisonPitPlayer.setBrickspersec(1.0);
                                }
                            } else {
                                player.closeInventory();
                                player.sendMessage("§7[§c!§7]§e Условия не выполнены!");
                            }
                            return false;
                        }
                    });
                    lore.clear();

                    lore.add("");
                    lore.add("§eДля улучшения необходимо:");
                    lore.add(" §6Золотые блоки - " + Utils.getMoney((getPrice4StarGB(prisonPitPlayer.getStars()[6]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))));
                    lore.add(" §dОсколки - " + (getPrice4StarShards(prisonPitPlayer.getStars()[6]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add(" §fФрагменты звёзд - " + (getPrice4StarFragments(prisonPitPlayer.getStars()[6]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add("");
                    lore.add("§aУлучшить");
                    customInventory.addItem(32, new CustomItem(Material.NETHER_STAR, prisonPitPlayer.getStars()[6]).setName("§6§lЗвёзда " + prisonPitPlayer.getStars()[6] + " уровня").setLore(lore).build());
                    customInventory.addMenuClickHandler(32, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getGoldblocks() >= (getPrice4StarGB(prisonPitPlayer.getStars()[6]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getShards() >= (getPrice4StarShards(prisonPitPlayer.getStars()[6]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getFragments() >= (getPrice4StarFragments(prisonPitPlayer.getStars()[6]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))) {
                                prisonPitPlayer.setGoldblocks(prisonPitPlayer.getGoldblocks() - (getPrice4StarGB(prisonPitPlayer.getStars()[6]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setShards(prisonPitPlayer.getShards() - (getPrice4StarShards(prisonPitPlayer.getStars()[6]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setFragments(prisonPitPlayer.getFragments() - (getPrice4StarFragments(prisonPitPlayer.getStars()[6]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                int[] stars = prisonPitPlayer.getStars();
                                stars[6] = stars[6] + 1;
                                prisonPitPlayer.setStars(stars);
                                showMenuPrestige(player);
                                player.sendMessage("§7[§a!§7]§e Звезда улучшена!");
                                Utils.calcTotalStars(player);
                                if (prisonPitPlayer.getTotalStars() >= 15 && prisonPitPlayer.getBrickspersec() < 1) {
                                    prisonPitPlayer.setBrickspersec(1.0);
                                }
                            } else {
                                player.closeInventory();
                                player.sendMessage("§7[§c!§7]§e Условия не выполнены!");
                            }
                            return false;
                        }
                    });
                    lore.clear();

                    lore.add("");
                    lore.add("§eДля улучшения необходимо:");
                    lore.add(" §6Золотые блоки - " + Utils.getMoney((getPrice4StarGB(prisonPitPlayer.getStars()[7]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))));
                    lore.add(" §dОсколки - " + (getPrice4StarShards(prisonPitPlayer.getStars()[7]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add(" §fФрагменты звёзд - " + (getPrice4StarFragments(prisonPitPlayer.getStars()[7]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add("");
                    lore.add("§aУлучшить");
                    customInventory.addItem(24, new CustomItem(Material.NETHER_STAR, prisonPitPlayer.getStars()[7]).setName("§6§lЗвёзда " + prisonPitPlayer.getStars()[7] + " уровня").setLore(lore).build());
                    customInventory.addMenuClickHandler(24, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getGoldblocks() >= (getPrice4StarGB(prisonPitPlayer.getStars()[7]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getShards() >= (getPrice4StarShards(prisonPitPlayer.getStars()[7]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getFragments() >= (getPrice4StarFragments(prisonPitPlayer.getStars()[7]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))) {
                                prisonPitPlayer.setGoldblocks(prisonPitPlayer.getGoldblocks() - (getPrice4StarGB(prisonPitPlayer.getStars()[7]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setShards(prisonPitPlayer.getShards() - (getPrice4StarShards(prisonPitPlayer.getStars()[7]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setFragments(prisonPitPlayer.getFragments() - (getPrice4StarFragments(prisonPitPlayer.getStars()[7]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                int[] stars = prisonPitPlayer.getStars();
                                stars[7] = stars[7] + 1;
                                prisonPitPlayer.setStars(stars);
                                showMenuPrestige(player);
                                player.sendMessage("§7[§a!§7]§e Звезда улучшена!");
                                Utils.calcTotalStars(player);
                                if (prisonPitPlayer.getTotalStars() >= 15 && prisonPitPlayer.getBrickspersec() < 1) {
                                    prisonPitPlayer.setBrickspersec(1.0);
                                }
                            } else {
                                player.closeInventory();
                                player.sendMessage("§7[§c!§7]§e Условия не выполнены!");
                            }
                            return false;
                        }
                    });
                    lore.clear();

                    lore.add("");
                    lore.add("§eДля улучшения необходимо:");
                    lore.add(" §6Золотые блоки - " + Utils.getMoney((getPrice4StarGB(prisonPitPlayer.getStars()[8]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))));
                    lore.add(" §dОсколки - " + (getPrice4StarShards(prisonPitPlayer.getStars()[8]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add(" §fФрагменты звёзд - " + (getPrice4StarFragments(prisonPitPlayer.getStars()[8]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add("");
                    lore.add("§aУлучшить");
                    customInventory.addItem(42, new CustomItem(Material.NETHER_STAR, prisonPitPlayer.getStars()[8]).setName("§6§lЗвёзда " + prisonPitPlayer.getStars()[8] + " уровня").setLore(lore).build());
                    customInventory.addMenuClickHandler(42, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getGoldblocks() >= (getPrice4StarGB(prisonPitPlayer.getStars()[8]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getShards() >= (getPrice4StarShards(prisonPitPlayer.getStars()[8]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getFragments() >= (getPrice4StarFragments(prisonPitPlayer.getStars()[8]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))) {
                                prisonPitPlayer.setGoldblocks(prisonPitPlayer.getGoldblocks() - (getPrice4StarGB(prisonPitPlayer.getStars()[8]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setShards(prisonPitPlayer.getShards() - (getPrice4StarShards(prisonPitPlayer.getStars()[8]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setFragments(prisonPitPlayer.getFragments() - (getPrice4StarFragments(prisonPitPlayer.getStars()[8]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                int[] stars = prisonPitPlayer.getStars();
                                stars[8] = stars[8] + 1;
                                prisonPitPlayer.setStars(stars);
                                showMenuPrestige(player);
                                player.sendMessage("§7[§a!§7]§e Звезда улучшена!");
                                Utils.calcTotalStars(player);
                                if (prisonPitPlayer.getTotalStars() >= 15 && prisonPitPlayer.getBrickspersec() < 1) {
                                    prisonPitPlayer.setBrickspersec(1.0);
                                }
                            } else {
                                player.closeInventory();
                                player.sendMessage("§7[§c!§7]§e Условия не выполнены!");
                            }
                            return false;
                        }
                    });
                    lore.clear();

                    lore.add("");
                    lore.add("§eДля улучшения необходимо:");
                    lore.add(" §6Золотые блоки - " + Utils.getMoney((getPrice4StarGB(prisonPitPlayer.getStars()[9]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))));
                    lore.add(" §dОсколки - " + (getPrice4StarShards(prisonPitPlayer.getStars()[9]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add(" §fФрагменты звёзд - " + (getPrice4StarFragments(prisonPitPlayer.getStars()[9]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                    lore.add("");
                    lore.add("§aУлучшить");
                    customInventory.addItem(34, new CustomItem(Material.NETHER_STAR, prisonPitPlayer.getStars()[9]).setName("§6§lЗвёзда " + prisonPitPlayer.getStars()[9] + " уровня").setLore(lore).build());
                    customInventory.addMenuClickHandler(34, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if (prisonPitPlayer.getGoldblocks() >= (getPrice4StarGB(prisonPitPlayer.getStars()[9]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getShards() >= (getPrice4StarShards(prisonPitPlayer.getStars()[9]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())) && prisonPitPlayer.getFragments() >= (getPrice4StarFragments(prisonPitPlayer.getStars()[9]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level()))) {
                                prisonPitPlayer.setGoldblocks(prisonPitPlayer.getGoldblocks() - (getPrice4StarGB(prisonPitPlayer.getStars()[9]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setShards(prisonPitPlayer.getShards() - (getPrice4StarShards(prisonPitPlayer.getStars()[9]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                prisonPitPlayer.setFragments(prisonPitPlayer.getFragments() - (getPrice4StarFragments(prisonPitPlayer.getStars()[9]) * 100) / (100 + getValueGoldRoom(prisonPitPlayer.getGoldblockroom_level())));
                                int[] stars = prisonPitPlayer.getStars();
                                stars[9] = stars[9] + 1;
                                prisonPitPlayer.setStars(stars);
                                showMenuPrestige(player);
                                player.sendMessage("§7[§a!§7]§e Звезда улучшена!");
                                Utils.calcTotalStars(player);
                                if (prisonPitPlayer.getTotalStars() >= 15 && prisonPitPlayer.getBrickspersec() < 1) {
                                    prisonPitPlayer.setBrickspersec(1.0);
                                }
                            } else {
                                player.closeInventory();
                                player.sendMessage("§7[§c!§7]§e Условия не выполнены!");
                            }
                            return false;
                        }
                    });
                    lore.clear();
                    customInventory.addItem(45, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName("§bВернутся назад").setSkullOwner("MHF_ArrowLeft").build());
                    customInventory.addMenuClickHandler(45, new CustomInventory.MenuClickHandler() {
                        @Override
                        public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                            showGuiMenu(player);
                            return false;
                        }
                    });
                } else {
                    for (int k = 0; k < 36; k++) {
                        customInventory.addItem(k, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
                        customInventory.addMenuClickHandler(k, new CustomInventory.MenuClickHandler() {
                            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                                return false;
                            }
                        });
                    }
                    int[] starneedGB = new int[]{1000, 2000, 5000, 12500, 25000, 75000, 200000, 500000, 1000000, 4000000};
                    double roomdiscount = 1.00;
                    int j = 0;
                    while (prisonPitPlayer.getGoldblockroom_level() > j) {
                        roomdiscount = roomdiscount - (roomdiscount / 100);
                        j++;
                    }
                    lore.add("");
                    lore.add("§7§lКаждая звезда даёт вам увеличение х10!");
                    lore.add("§7§lТекущее увеличение: x" + (i == 0 ? "1" : Math.pow(10, i)));
                    lore.add("");
                    lore.add("§f§lНачать игру заного");
                    lore.add(getenoungcolor(starneedGB[i], (int) prisonPitPlayer.getGoldblocks()) + "" + (int) (starneedGB[i] * roomdiscount) + " золотых блоков");
                    customInventory.addItem(22, new CustomItem(Material.NETHER_STAR).setName("§6§lЗвёзд - " + i + "/10").setLore(lore).build());
                    int finalI = i;
                    double finalRoomdiscount = roomdiscount;
                    customInventory.addMenuClickHandler(22, new CustomInventory.MenuClickHandler() {
                        public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                            if ((int) (starneedGB[finalI] * finalRoomdiscount) <= prisonPitPlayer.getGoldblocks()) {
                                doPrestige(player);
                                prisonPitPlayer.setGoldblocks(0);
                                prisonPitPlayer.setGoldblockroom_level(0);
                                prisonPitPlayer.setGoldblockroom_points(0);
                                int[] stars = prisonPitPlayer.getStars();
                                stars[finalI] = 10;
                                prisonPitPlayer.setStars(stars);
                                player.closeInventory();
                                player.sendTitle("§e§lЗвезда получена!", "");
                                player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1, -1);
                                PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
                                stars = prisonPitPlayer.getStars();
                                int minstar = 0;
                                for (int j = 0; j < 10; j++) {
                                    if (PrisonPitPlayerManager.getPrisonPitPlayer(player.getName()).getStars()[j] > 0) {
                                        minstar++;
                                    }
                                }
                                if (minstar >= 10) {
                                    for (int j : stars) {
                                        if (j < minstar) {
                                            minstar = j;
                                        }
                                    }
                                }
                                prisonPitPlayer.setTotalStars(minstar);
                                if(ClanManager.hasClan(player)){
                                    ClanManager.getClanPlayer(player).broadCastNotify("§7[" + ChatColor.translateAlternateColorCodes('&', ClanManager.getClanPlayer(player).getClanName()) + "§7]§e Игрок " + player.getName() + " достиг " + minstar + " звёзд!");
                                }
                                if (prisonPitPlayer.getTotalStars() >= 4) {
                                    if (onGoldBlockASClick.levelEntitiesHashMap.containsKey(player)) {
                                        PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(onGoldBlockASClick.levelEntitiesHashMap.get(player).getId());
                                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutEntityDestroy);
                                    }
                                    onGoldBlockASClick.sendLevelArmorStand(player);
                                    if (prisonPitPlayer.getTotalStars() >= 10) {
                                        if (onGoldBlockASClick.v2EntitiesHashMap.containsKey(player)) {
                                            PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(onGoldBlockASClick.v2EntitiesHashMap.get(player).getId());
                                            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutEntityDestroy);
                                        }
                                        onGoldBlockASClick.sendV2(player);
                                    }
                                }
                            } else {
                                player.closeInventory();
                                player.sendMessage("§7[§c!§7]§e Недостаточно золотых блоков! Нужно больше золотых блоков чтобы получить новую звезду.");
                            }
                            return false;
                        }
                    });
                    customInventory.addItem(27, new CustomItem(Material.SKULL_ITEM, 1, (byte) 3).setName("§bВернутся назад").setSkullOwner("MHF_ArrowLeft").build());
                    customInventory.addMenuClickHandler(27, new CustomInventory.MenuClickHandler() {
                        @Override
                        public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                            showGuiMenu(player);
                            return false;
                        }
                    });
                }

                customInventory.addItem(2, new CustomItem(Material.GOLD_BLOCK).setName("§6§lЗолотые блоки").setLore(lore).build());
                customInventory.addMenuClickHandler(2, new CustomInventory.MenuClickHandler() {
                    public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                        prestigeChoiceHashMap.replace(player, "Золотые блоки");
                        showMenuPrestige(player);
                        return false;
                    }
                });

                customInventory.addItem(6, new CustomItem(Material.NETHER_STAR).setName("§e§lЗвёзды").setLore(lore).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
                customInventory.addMenuClickHandler(6, new CustomInventory.MenuClickHandler() {
                    public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                        return false;
                    }
                });
                customInventory.open(player);
            }
        } else {
            prestigeChoiceHashMap.put(player, "Золотые блоки");
            showMenuPrestige(player);
        }
    }

    public static int getValueGoldRoom(int level) {
        int i = 0;
        int value = 0;
        while (i < level) {
            if (i > 100) {
                value++;
            }
            if (i > 200) {
                value = value + 2;
            }
            value++;
            i++;
        }
        return value;
    }

    public static ChatColor getenoungcolor(int need, int have) {
        if (need <= have) {
            return ChatColor.WHITE;
        } else {
            return ChatColor.RED;
        }
    }

    public static double getPrice4StarGB(int level) {
        double price = 250000;
        int i = 10;
        while (i < level) {
            if (i < 20) {
                price = price + 100000;
            } else if (i == 20) {
                price = price + 475000;
            } else if (i > 20 && i < 30) {
                price = price + 130000;
            } else if (i == 30) {
                price = price + 1007500;
            } else if (i > 30 && i < 60) {
                price = price + 169000;
            } else if (i == 60) {
                price = price + 2830750;
            } else if (i > 60 && i < 80) {
                price = price + 219700;
            } else if (i == 80) {
                price = price + 4998175;
            } else if (i > 80 && i < 100) {
                price = price + 285610;
            } else if (i == 100) {
                price = price + 10674674;
            } else if (i > 100) {
                price = price + (price / 150);
            }
            i++;
        }
        return price;
    }

    public static int getPrice4StarShards(int level) {
        int price = 1000;
        int i = 10;
        while (i < level) {
            if (i == 11) {
                price = price + 250;
            } else if (i == 12) {
                price = price + 220;
            } else if (i == 13) {
                price = price + 210;
            } else if (i == 14) {
                price = price + 202;
            } else if (i == 15) {
                price = price + 193;
            } else if (i == 16) {
                price = price + 184;
            } else if (i == 17) {
                price = price + 177;
            } else if (i == 18) {
                price = price + 168;
            } else if (i == 19) {
                price = price + 160;
            } else if (i == 20) {
                price = price + 154;
            } else if (i == 21) {
                price = price + 146;
            } else if (i == 22) {
                price = price + 138;
            } else if (i == 23) {
                price = price + 168;
            } else if (i > 23 && i < 70) {
                price = price + 198;
            } else if (i == 70) {
                price = price + 705;
            } else if (i > 70 && i < 75) {
                price = price + 206;
            } else if (i == 75) {
                price = price + 775;
            } else if (i > 75) {
                price = price + (price / 150);
            }
            i++;
        }
        return price;
    }

    public static int getPrice4StarFragments(int level) {
        double price = 4.00;
        int i = 10;
        while (i < level) {
            price = price + (1 + (price / 100));
            i++;
        }
        return (int) price;
    }

    public static void confirmPrestige(Player player) {
        CustomInventory customInventory = new CustomInventory("§a§lПолучить престиж?");
        List<String> lore = new ArrayList<>();

        customInventory.addMenuCloseHandler(new CustomInventory.MenuCloseHandler() {
            @Override
            public void onClose(Player player) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.updateInventory();
                    }
                }.runTaskLater(Main.instance, 2);
            }
        });

        for (int i = 0; i < 27; i++) {
            customInventory.addItem(i, new CustomItem(Material.STAINED_GLASS_PANE, 1, (byte) 3).setName(" ").setLore(lore).build());
            customInventory.addMenuClickHandler(i, new CustomInventory.MenuClickHandler() {
                public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                    return false;
                }
            });
        }
        customInventory.addItem(11, new CustomItem(Material.REDSTONE_BLOCK, 1, (byte) 3).setName("§c§lОтклонить").setLore(lore).build());
        customInventory.addMenuClickHandler(11, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                showMenuPrestige(player);
                return false;
            }
        });

        customInventory.addItem(15, new CustomItem(Material.EMERALD_BLOCK, 1, (byte) 3).setName("§a§lПодтвердить").setLore(lore).build());
        customInventory.addMenuClickHandler(15, new CustomInventory.MenuClickHandler() {
            public boolean onClick(final Player arg0, final int arg1, final ItemStack arg2, final ClickAction arg3) {
                doPrestige(player);
                player.closeInventory();
                player.sendTitle("§a§lПрестиж получен!", "");
                player.playSound(player.getLocation(), Sound.ENTITY_DONKEY_ANGRY, 1, 1);
                return false;
            }
        });
        customInventory.open(player);
    }

    public static void doPrestige(Player player) {
        PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
        int GBAP = 0;
        double money = prisonPitPlayer.getNonspendmoney();
        while (money >= (100000000.0 * (1.0 - (prisonPitPlayer.getBoostergoldblocks_level() / 100.0))) * (GBAP == 0 ? 1 : Math.pow(1.05, GBAP))) {
            money = money - (100000000.0 * (1.0 - (prisonPitPlayer.getBoostergoldblocks_level() / 100.0))) * (GBAP == 0 ? 1 : Math.pow(1.05, GBAP));
            GBAP++;
        }
        int blockslevel = prisonPitPlayer.getBoosterMoney_level();
        if (blockslevel >= 48) {
            int rank = 0;
            while (blockslevel >= 48) {
                rank++;
                blockslevel -= 6;
            }
            prisonPitPlayer.setGoldblocks(prisonPitPlayer.getGoldblocks() + (GBAP * getBosterGB(rank)));
        } else {
            prisonPitPlayer.setGoldblocks(prisonPitPlayer.getGoldblocks() + GBAP);
        }
        prisonPitPlayer.setNonspendmoney(0);
        prisonPitPlayer.setMoney(0);
        prisonPitPlayer.setMoneypersec(0);
        prisonPitPlayer.setPoints(0);
        prisonPitPlayer.setPickaxe_level(0);
        prisonPitPlayer.setShardsFromBlock_level(0);
        player.getInventory().setItem(0, Pickaxes.getPickaxe(prisonPitPlayer.getPickaxe_level()));
        prisonPitPlayer.setBoosterMoney_level(0);
        prisonPitPlayer.setDoubleBlock_level(0);
        prisonPitPlayer.setBoosterMoneyII_level(0);
        prisonPitPlayer.setStorm_level(0);
        new ShaftUpdater().runTask(Main.instance);
    }
}
