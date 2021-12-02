package homes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Objects;

public class SetHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                String home = args[0];
                String prefix = "Homes." + p.getUniqueId().toString() + "." + home ;
                if (!HomePlugin.homes.contains(home)) {
                    try {
                        HomePlugin.homes.set(prefix + ".X", p.getLocation().getX());
                        HomePlugin.homes.set(prefix + ".Y", p.getLocation().getY());
                        HomePlugin.homes.set(prefix + ".Z", p.getLocation().getZ());
                        HomePlugin.homes.set(prefix + ".World", Objects.requireNonNull(p.getLocation().getWorld()).getName());
                        HomePlugin.homes.save(HomePlugin.home_file);
                        p.sendMessage(ChatColor.GREEN + "Your home '" + ChatColor.AQUA + home + ChatColor.GREEN + "' was set at your current location!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "You have already got a home called '" + ChatColor.AQUA + home + ChatColor.RED + "' !");
                }

            } else {
                p.sendMessage(ChatColor.RED + "You have to write the name of the home you want to set!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be a player to execute that command!");
        }

        return true;
    }
}
