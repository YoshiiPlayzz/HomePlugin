package homes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerCareerChangeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Events implements Listener {

    public static List<Player> villager_pending = new ArrayList<>();
    public static HashMap<Villager, Player> villager_mending = new HashMap<>();

    public static HashMap<Player, Integer> hometeleporting = new HashMap<>();
    public static HashMap<Player, Location> location = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (hometeleporting.containsKey(e.getPlayer())) {
            Location loc = location.get(e.getPlayer());
            if (e.getFrom().getBlockX() != loc.getBlockX() &&
                    e.getFrom().getBlockZ() != loc.getBlockZ()) {
                Bukkit.getScheduler().cancelTask(hometeleporting.get(e.getPlayer()));
                hometeleporting.remove(e.getPlayer());
                location.remove(e.getPlayer());
                e.getPlayer().sendMessage(ChatColor.RED + "Teleportation canceled, because you have moved!");

            }
        }

    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();
        Entity en = e.getRightClicked();
        if (en instanceof Villager || en.getType().equals(EntityType.VILLAGER)) {
            if (villager_pending.contains(p)) {
                villager_pending.remove(p);
                assert en instanceof Villager;
                villager_mending.put((Villager) en, p);
                p.sendMessage(ChatColor.GREEN + "Checking now the Villager you have right-clicked!");
            }
        }
    }


    @EventHandler
    public void onVillager(VillagerCareerChangeEvent e) {
        Villager v = e.getEntity();


        if (villager_mending.containsKey(v)) {
            Bukkit.getScheduler().runTaskLater(HomePlugin.plugin, () -> {
                if (!v.getProfession().equals(Villager.Profession.NONE)) {
                    int x = 0;
                    Player p = villager_mending.get(v);
                    for (MerchantRecipe r : v.getRecipes()) {
                        ItemStack i = r.getResult();
                        if (i.getItemMeta() instanceof EnchantmentStorageMeta) {
                            x++;
                            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) i.getItemMeta();

                            if (meta.getStoredEnchants().containsKey(Enchantment.MENDING)) {
                                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5f, 1f);
                                for (Map.Entry<Enchantment, Integer> en : meta.getStoredEnchants().entrySet())
                                    p.sendMessage(ChatColor.GREEN + "Villager trades " + ChatColor.AQUA + en.getKey().getKey().getKey() + " " + en.getValue());
                            } else {
                                for (Map.Entry<Enchantment, Integer> en : meta.getStoredEnchants().entrySet())
                                    p.sendMessage(ChatColor.GREEN + "Villager trades " + ChatColor.AQUA + en.getKey().getKey().getKey() + " " + en.getValue());
                            }

                        }
                    }
                    if (x == 0) {
                        p.sendMessage(ChatColor.RED + "Villager trades no books!");
                    }
                }
            }, 1L);

        }
    }


}
