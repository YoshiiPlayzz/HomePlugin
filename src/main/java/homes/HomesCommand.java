package homes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            String m = ChatColor.AQUA + "List of Homes: " + ChatColor.DARK_AQUA;
            for (String s : HomePlugin.homes.getConfigurationSection("Homes." + p.getUniqueId().toString()).getKeys(false)) {

                m += s + ", ";
            }
            p.sendMessage(m);
        }


        return false;
    }
}
