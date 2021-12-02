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


public class HomeCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                String home = args[0];
                String prefix = "Homes." + p.getUniqueId().toString() + "." + home;

                if (HomePlugin.homes.contains(prefix)) {

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

                        }, 20 * 3L).getTaskId();
                        Events.hometeleporting.put(p, task);

                    }

                }


            } else {
                p.sendMessage(ChatColor.RED + "You have to write the name of the home you want to teleport to!");
            }
        }

        return true;
    }

}
