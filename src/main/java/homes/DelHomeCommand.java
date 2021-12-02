package homes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class DelHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                String home = args[0];
                String prefix = "Homes." + p.getUniqueId().toString() + "." + home;
                if (HomePlugin.homes.contains(prefix)) {
                    try {
                        HomePlugin.homes.set(prefix, null);
                        HomePlugin.homes.save(HomePlugin.home_file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    p.sendMessage(ChatColor.GREEN + "Your home '" + ChatColor.AQUA + home + ChatColor.GREEN + "' was deleted!");

                } else {
                    p.sendMessage(ChatColor.RED + "You have no home called '" + ChatColor.AQUA + home + ChatColor.RED + "' !");
                }
            } else {
                p.sendMessage(ChatColor.RED + "You have to write the name of the home you want to delete!");
            }
        }

        return true;
    }
}

