package homes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Set;


public class HomeCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            String prefix = "Homes." + p.getUniqueId().toString();
            if (args.length == 1) {
                String home = args[0];
                prefix += "." + home;


                if (HomePlugin.homes.contains(prefix)) {
                    teleport(p, home, prefix);
                }else{
                    p.sendMessage(ChatColor.RED + "Home not found!");
                }

            } else {
                if (args.length == 0) {
                    if (HomePlugin.homes.contains(prefix)) {
                        Set<String> homes = HomePlugin.homes.getConfigurationSection(prefix).getKeys(false);
                        if(!homes.isEmpty()) {
                            String home = homes.stream().iterator().next();
                            prefix += "." + home;
                            teleport(p, home, prefix);
                        }else{
                            p.sendMessage(ChatColor.RED + "Home not found!");
                        }
                    }
                }
            }
        }

        return true;
    }

    public void teleport(Player p, String home, String prefix) {

        World w = Bukkit.getWorld(Objects.requireNonNull(HomePlugin.homes.getString(prefix + ".World")));
        double x = HomePlugin.homes.getDouble(prefix + ".X");
        double y = HomePlugin.homes.getDouble(prefix + ".Y");
        double z = HomePlugin.homes.getDouble(prefix + ".Z");
        if (w != null) {
            Location loc = new Location(w, x, y, z);
            p.sendMessage(ChatColor.AQUA + "Please wait for 3s and don't move!");
            int task = Bukkit.getScheduler().runTaskLater(HomePlugin.plugin, () -> {
                p.sendMessage(ChatColor.DARK_AQUA + "Teleporting...");
                p.teleport(loc);
                p.sendMessage(ChatColor.GREEN + "Welcome at '" + ChatColor.AQUA + home + ChatColor.GREEN + "' !");
                Events.hometeleporting.remove(p);
                Events.location.remove(p);
            }, 20 * 3L).getTaskId();
            Events.hometeleporting.put(p, task);
            Events.location.put(p, p.getLocation());
        }
    }
}