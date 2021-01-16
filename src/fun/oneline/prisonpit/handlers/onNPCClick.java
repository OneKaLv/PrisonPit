package fun.oneline.prisonpit.handlers;

import fun.oneline.prisonpit.Main;
import fun.oneline.prisonpit.player.PrisonPitPlayer;
import fun.oneline.prisonpit.player.PrisonPitPlayerManager;
import fun.oneline.prisonpit.tokens.TokenManager;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class onNPCClick implements Listener {
    public static List<String> canceledTokenPlayers = new ArrayList<>();
    public static List<String> usedTokenPlayers = new ArrayList<>();
    public static HashMap<Player, String> interactedPlayers = new HashMap<>();
    public static List<String> confirmedPlayers = new ArrayList<>();
    public static List<String> questlist = new ArrayList<>();

    public onNPCClick(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(NPCRightClickEvent event) {
        Player player = event.getClicker();
        if (event.getNPC().getId() == 0) {
            if (!interactedPlayers.containsKey(player)) {
                if (!canceledTokenPlayers.contains(player.getName())) {
                    if (!usedTokenPlayers.contains(player.getName())) {
                        if (player.getInventory().getItem(7) != null && player.getInventory().getItem(7).getType() == Material.FIREWORK_CHARGE) {
                            if (!(TokenManager.fastPickaxe.contains(player) || TokenManager.moreShards.contains(player) || TokenManager.autoBlocks.contains(player) || TokenManager.moreMoney.contains(player))) {
                                String token = TokenManager.getRandomToken();
                                interactedPlayers.put(player, token);
                                player.sendMessage("§7[§b§lЛегезо§7]§e Привет!");
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (event.getNPC().getEntity().getLocation().distance(player.getLocation()) < 10) {
                                            player.sendMessage("§7[§b§lЛегезо§7]§e У тебя есть токен! Что если я предложу тебе обменять его на что-нибудь полезное?");
                                        } else {
                                            player.sendMessage("§7[§b§lЛегезо§7]§e Уже уходишь!? Приходи в следующий раз!");
                                            canceledTokenPlayers.add(player.getName());
                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    canceledTokenPlayers.remove(player.getName());
                                                }
                                            }.runTaskLater(Main.instance, 2400);
                                            interactedPlayers.remove(player);
                                        }
                                    }
                                }.runTaskLater(Main.instance, 40);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (interactedPlayers.containsKey(player)) {
                                            if (event.getNPC().getEntity().getLocation().distance(player.getLocation()) < 10) {
                                                player.sendMessage("§7[§b§lЛегезо§7]§e К примеру на бонус '" + token + "'?");
                                            } else {
                                                player.sendMessage("§7[§b§lЛегезо§7]§e Уже уходишь!? Приходи в следующий раз!");
                                                canceledTokenPlayers.add(player.getName());
                                                new BukkitRunnable() {
                                                    @Override
                                                    public void run() {
                                                        canceledTokenPlayers.remove(player.getName());
                                                    }
                                                }.runTaskLater(Main.instance, 2400);
                                                interactedPlayers.remove(player);
                                            }
                                        }
                                    }
                                }.runTaskLater(Main.instance, 150);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (interactedPlayers.containsKey(player)) {
                                            if (event.getNPC().getEntity().getLocation().distance(player.getLocation()) < 10) {
                                                player.sendMessage("§7[§b§lЛегезо§7]§e Как тебе такое предложение?");
                                            } else {
                                                player.sendMessage("§7[§b§lЛегезо§7]§e Уже уходишь!? Приходи в следующий раз!");
                                                canceledTokenPlayers.add(player.getName());
                                                new BukkitRunnable() {
                                                    @Override
                                                    public void run() {
                                                        canceledTokenPlayers.remove(player.getName());
                                                    }
                                                }.runTaskLater(Main.instance, 2400);
                                                interactedPlayers.remove(player);
                                            }
                                        }
                                    }
                                }.runTaskLater(Main.instance, 200);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (interactedPlayers.containsKey(player)) {
                                            confirmedPlayers.add(player.getName());
                                            player.spigot().sendMessage(new ComponentBuilder("            ").append("Подтвердить  ").color(ChatColor.GREEN).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/token accept")).append("Отклонить").color(ChatColor.RED).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/token deny")).create());
                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    if (confirmedPlayers.contains(player.getName())) {
                                                        player.sendMessage("§7[§b§lЛегезо§7]§e Ты слишком долго думаешь, приходи в следующий раз!");
                                                        canceledTokenPlayers.add(player.getName());
                                                        new BukkitRunnable() {
                                                            @Override
                                                            public void run() {
                                                                canceledTokenPlayers.remove(player.getName());
                                                            }
                                                        }.runTaskLater(Main.instance, 2400);
                                                        interactedPlayers.remove(player);
                                                    }
                                                }
                                            }.runTaskLater(Main.instance, 200);
                                        }
                                    }
                                }.runTaskLater(Main.instance, 220);
                            } else {
                                player.sendMessage("§7[§b§lЛегезо§7]§e У тебя уже активен бонус! Приходи позже!");
                            }
                        } else {
                            player.sendMessage("§7[§b§lЛегезо§7]§e Оуф... я вижу у тебя нету токенов, мне нечего тебе предложить...");
                        }
                    } else {
                        player.sendMessage("§7[§b§lЛегезо§7]§e Я недавно уже обменивал тебе токен, приходи позже.");
                    }
                } else {
                    player.sendMessage("§7[§b§lЛегезо§7]§e Ты отказался от моего предложения, приходи позже.");
                }
            }
        } else if (event.getNPC().getId() == 1) {
            PrisonPitPlayer prisonPitPlayer = PrisonPitPlayerManager.getPrisonPitPlayer(player.getName());
            int stage = prisonPitPlayer.getStageq();
            if (stage == 0) {
                if (!questlist.contains(player.getName())) {
                    questlist.add(player.getName());
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                    player.sendMessage("§7[§9§lКверти§7]§e Привет! Добро пожаловать на Prison Pit! Я - Кверти! Я научу тебя всем правилам нашей 'Ямы'!");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                            player.sendMessage("§7[§9§lКверти§7]§e Для начала давай разберёмся с твоими инструментами. В первом слоте - твой основной инструмент, кирка!");
                        }
                    }.runTaskLater(Main.instance, 150);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                            player.sendMessage("§7[§9§lКверти§7]§e Да, не сказать что она сейчас что-то может, но её можно улучшать в меню, которое находится в твоём последнем слоте!");
                        }
                    }.runTaskLater(Main.instance, 300);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                            player.sendMessage("§7[§9§lКверти§7]§e Но для того чтобы её улучшить, необходимы деньги, но где их взять?");
                        }
                    }.runTaskLater(Main.instance, 450);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                            player.sendMessage("§7[§9§lКверти§7]§e Видишь вон ту белую шахту? Эта и есть наша яма, каждый вскопанный блок в ней, даёт тебе пассивную прибыль!");
                        }
                    }.runTaskLater(Main.instance, 525);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                            player.sendMessage("§7[§9§lКверти§7]§e В твоём случае 1 блок - 1$ в секунду.");
                        }
                    }.runTaskLater(Main.instance, 675);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                            player.sendMessage("§7[§9§lКверти§7]§e Как получать больше я расскажу тебе позже, а сейчас добудь 1000$ и улучши свою кирку во вкладке 'Улучшения'! После чего возвращайся ко мне за наградой!");
                            prisonPitPlayer.setStageq(prisonPitPlayer.getStageq() + 1);
                            questlist.remove(player.getName());
                        }
                    }.runTaskLater(Main.instance, 750);
                }
            } else if (stage == 1) {
                if (prisonPitPlayer.getPickaxe_level() > 0) {
                    if (!questlist.contains(player.getName())) {
                        questlist.add(player.getName());
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                        player.sendMessage("§7[§9§lКверти§7]§e Молодец, держи 2000$!");
                        prisonPitPlayer.setMoney(prisonPitPlayer.getMoney() + 2000);
                        player.sendMessage("§7[§e§lPrison §6§lPit§7]§a На ваш счёт начисленно 2000$");
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Как улучшать кирку ты уже понял, видишь эту фиолетовую полосу сверху экрана с надписью 'До шторма'?");
                            }
                        }.runTaskLater(Main.instance, 50);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e По истечению таймера тебя перенесёт на Шторм, где с неба будут падать осколки, а Ты - должен их ловить");
                            }
                        }.runTaskLater(Main.instance, 200);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e За осколки можно улучшать много полезных вещей, которые находятся всё так же в улучшениях в вкладке 'Осколки'");
                            }
                        }.runTaskLater(Main.instance, 350);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Ты можешь их поизучать, а можешь пойти нарыть ещё себе прибыли, что делать - решать тебе. Возвращайся после того как наловишь немного осколков!");
                            }
                        }.runTaskLater(Main.instance, 500);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Удачи!");
                                prisonPitPlayer.setStageq(prisonPitPlayer.getStageq() + 1);
                                questlist.remove(player.getName());
                            }
                        }.runTaskLater(Main.instance, 650);
                    }
                } else {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                    player.sendMessage("§7[§9§lКверти§7]§e Твоя кирка по прежнему не улучшена...");
                }
            } else if(stage == 2){
                if(prisonPitPlayer.getShards() > 0){
                    if(!questlist.contains(player.getName())){
                        questlist.add(player.getName());
                        if(prisonPitPlayer.getShards() < 20) {
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 2, 4);
                            player.sendMessage("§7[§9§lКверти§7]§e Целых " + prisonPitPlayer.getShards() + " поймал! Молодец! Получай ещё столько же!");
                            player.sendMessage("§7[§e§lPrison §6§lPit§7]§d На ваш счёт начисленно " + prisonPitPlayer.getShards() + " осколков!");
                            prisonPitPlayer.setShards(prisonPitPlayer.getShards() + prisonPitPlayer.getShards());
                        } else {
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 2, 4);
                            player.sendMessage("§7[§9§lКверти§7]§e Целых 20 поймал! Молодец! Получай ещё столько же!");
                            player.sendMessage("§7[§e§lPrison §6§lPit§7]§d На ваш счёт начисленно 20 осколков!");
                            prisonPitPlayer.setShards(prisonPitPlayer.getShards() + 20);
                        }
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e А теперь давай поговорим о том, как можно увеличить твою прибыль, способов просто тьма!");
                            }
                        }.runTaskLater(Main.instance, 100);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Самым доступным для тебя сейчас является улучшение 'Лучшие блоки'. Это улучшение повысит уровень твоих блоков в шахте");
                            }
                        }.runTaskLater(Main.instance, 250);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e А чем выше уровень блоков в шахте - тем они ценнее! За каждый новый уровень блоков ты будешь получать в 3 раза больше предыдущего!");
                            }
                        }.runTaskLater(Main.instance, 400);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Да да! Именно! На 100 уровне блока ты будешь получать 500o$ в секунду. Чтобы ты понимал эти масштабы представь 1 с 47 нолями :)");
                            }
                        }.runTaskLater(Main.instance, 550);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Приходи ко мне, когда улучшишь свои блоки :)");
                                prisonPitPlayer.setStageq(prisonPitPlayer.getStageq() + 1);
                                questlist.remove(player.getName());
                            }
                        }.runTaskLater(Main.instance, 600);
                    }
                } else {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                    player.sendMessage("§7[§9§lКверти§7]§e Ты точно был на шторме?");
                }
            } else if(stage == 3){
                if(prisonPitPlayer.getBoosterMoney_level() > 0){
                    if(!questlist.contains(player.getName())){
                        questlist.add(player.getName());
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                        player.sendMessage("§7[§9§lКверти§7]§e Отлично! Теперь ты получаешь больше денег!");
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Теперь ты знаешь об всех базовых улучшениях!");
                            }
                        }.runTaskLater(Main.instance, 100);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Теперь давай поговорим о 'Золотых блоках'");
                            }
                        }.runTaskLater(Main.instance, 200);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Золотые блоки - твой личный помощник и одна из самых важных валют");
                            }
                        }.runTaskLater(Main.instance, 300);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e За них ты сможешь получать процент к прибыли и звёзды!");
                            }
                        }.runTaskLater(Main.instance, 400);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Ты спросишь наверное, как их получать? Да легко! Достаточно всего-лишь иметь большую прибыль!");
                            }
                        }.runTaskLater(Main.instance, 500);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e После чего блоки сами начнут тебе даватся со временем, отследить их появление ты сможешь во вкладке 'Престиж'");
                            }
                        }.runTaskLater(Main.instance, 650);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Каждый блок даёт 1% к твоей прибыли, но! Есть один ньюанс! Блоки тебе дадутся только после того как ты сделаешь престиж!");
                            }
                        }.runTaskLater(Main.instance, 800);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Почитай внимательно все улучшения за деньги и осколки, но, на твоём месте я бы предпочёл сейчас подкопить 300 осколков :)");
                            }
                        }.runTaskLater(Main.instance, 900);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Приходи когда получить немного золотых блоков! Дальше будет ещё интереснее :)");
                                prisonPitPlayer.setStageq(prisonPitPlayer.getStageq() + 1);
                                questlist.remove(player.getName());
                            }
                        }.runTaskLater(Main.instance, 1050);
                    }
                }else {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                    player.sendMessage("§7[§9§lКверти§7]§e Ты по прежнему получаешь 1$ за блок!");
                }
            } else if(stage == 4){
                if(prisonPitPlayer.getGoldblocks() > 0){
                    if(!questlist.contains(player.getName())){
                        questlist.add(player.getName());
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                        player.sendMessage("§7[§9§lКверти§7]§e Отлично! Теперь у тебя есть " + (int) prisonPitPlayer.getGoldblocks() + " ГБ!(голд блоков), держи ещё чуть-чуть!");
                        player.sendMessage("§7[§e§lPrison §6§lPit§7]§6 На ваш счёт начисленно 25 золотых блоков!");
                        prisonPitPlayer.setGoldblocks(prisonPitPlayer.getGoldblocks() + 25);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Теперь давай поговорим о токенах!");
                            }
                        }.runTaskLater(Main.instance, 100);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Токен - предмет, который в основном нужен для обмена его на бонусы у §b§lЛегезо§e. Легезо - брат разработчика Ямы!");
                            }
                        }.runTaskLater(Main.instance, 200);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e А сам разработчик, OneKa, обещал за каждые 10 токенов покупать ему по Дошираку :D");
                            }
                        }.runTaskLater(Main.instance, 300);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Но суть не в этом, бонусы, которые тебе будет давать Легезо - очень сильно тебе помогут в прокачке!");
                            }
                        }.runTaskLater(Main.instance, 400);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e У меня как раз тут один токен завалялся, сходи обменяй его, но помни, что бонусы бывают разные!");
                            }
                        }.runTaskLater(Main.instance, 500);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e И ты в любой момент можешь отказатся от одного и позже получить другой!");
                            }
                        }.runTaskLater(Main.instance, 600);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Удачи!");
                                prisonPitPlayer.setTokens(prisonPitPlayer.getTokens() + 1);

                                ItemStack token = new ItemStack( Material.FIREWORK_CHARGE, prisonPitPlayer.getTokens());
                                ItemMeta meta = token.getItemMeta();
                                meta.setDisplayName("§b§lТокен");
                                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                                meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                                FireworkEffectMeta metaFw = (FireworkEffectMeta) meta;
                                FireworkEffect aa = FireworkEffect.builder().withColor(Color.AQUA).build();
                                metaFw.setEffect(aa);
                                token.setItemMeta(metaFw);

                                player.getInventory().setItem(7,token);

                                prisonPitPlayer.setStageq(prisonPitPlayer.getStageq() + 1);
                                questlist.remove(player.getName());
                            }
                        }.runTaskLater(Main.instance, 700);
                    }
                } else {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                    player.sendMessage("§7[§9§lКверти§7]§e У тебя сейчас 0 золотых блоков... Помни, они даются только после престижа!");
                }
            } else if(stage == 5){
                if(prisonPitPlayer.getTokens() > 0){
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                    player.sendMessage("§7[§9§lКверти§7]§e У тебя ещё остался токен!");
                } else {
                    if(!questlist.contains(player.getName())) {
                        questlist.add(player.getName());
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                        player.sendMessage("§7[§9§lКверти§7]§e Ну вот теперь ты знаешь что токены - отличная штука :)");
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Получать ты их можешь копая блоки, если ты прокачал нужное улучшение за осколки, ты же изучил их, верно?...");
                            }
                        }.runTaskLater(Main.instance, 100);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Теперь твоя цель - 1 звезда, звёзды - показатель твоей силы, а так же отличный буст!");
                            }
                        }.runTaskLater(Main.instance, 200);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Ведь каждая звезда будет давать тебе ещё х10 прибыли!");
                            }
                        }.runTaskLater(Main.instance, 300);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Как получать золотые блоки ты уже понял, теперь вперёд за твоей первой звездой!");
                            }
                        }.runTaskLater(Main.instance, 400);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e И да, улучшение за 300 осколков - оно тебе очень поможет!");
                                prisonPitPlayer.setStageq(prisonPitPlayer.getStageq() + 1);
                                questlist.remove(player.getName());
                            }
                        }.runTaskLater(Main.instance, 500);
                    }
                }
            } else if(stage == 6){
                if(prisonPitPlayer.getTotalStars() > 0) {
                    if (!questlist.contains(player.getName())) {
                        questlist.add(player.getName());
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                        player.sendMessage("§7[§9§lКверти§7]§e А вот и звёздочку вижу, молодец, справился!");
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Теперь ты понял основную суть режима, всё что ты улучшишь - даёт тебе прогресс.");
                            }
                        }.runTaskLater(Main.instance, 100);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Такова и была задумка данного режима - быстрый а главное, постоянный прогресс.");
                            }
                        }.runTaskLater(Main.instance, 200);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Следующей твоей целью будет двор блоков, после - двор кирпичей.");
                            }
                        }.runTaskLater(Main.instance, 300);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Возможно ты задашься вопросом: 'В звёздах можно качать только 10 звёзд, как получить больше?'");
                            }
                        }.runTaskLater(Main.instance, 400);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Первые свои 10 звёзд ты покупаешь, после чего - прокачиваешь их. Да и на 10 звезде тебе понадобятся 'Фрагменты звёзд'");
                            }
                        }.runTaskLater(Main.instance, 500);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Получать их не сложно, нужно всего-лишь разрушать блоки киркой(пкм по блоку, держа кирку).");
                            }
                        }.runTaskLater(Main.instance, 600);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Если у тебя останутся ещё какие-то вопросы, спроси у других призонеров, они помогут :) А у меня всё");
                            }
                        }.runTaskLater(Main.instance, 700);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                                player.sendMessage("§7[§9§lКверти§7]§e Ах да, чуть не забыл, держи наградку за твою звезду :)");
                            }
                        }.runTaskLater(Main.instance, 800);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.sendMessage("§7[§e§lPrison §6§lPit§7]§6 На ваш счёт начисленно 150 золотых блоков!");
                                prisonPitPlayer.setGoldblocks(prisonPitPlayer.getGoldblocks() + 150);
                                prisonPitPlayer.setStageq(prisonPitPlayer.getStageq() + 1);
                                questlist.remove(player.getName());
                            }
                        }.runTaskLater(Main.instance, 820);
                    }
                } else {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES,2,4);
                    player.sendMessage("§7[§9§lКверти§7]§e У тебя ещё нету звезды... Приходи позже!");
                }
            }
        }
    }
}
