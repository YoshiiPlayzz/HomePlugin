package homes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HomeTC implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> l = new ArrayList<>();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (alias.equalsIgnoreCase("home")) {
                if (args.length == 1) {
                    l.addAll(HomePlugin.homes.getConfigurationSection("Homes." + p.getUniqueId().toString()).getKeys(false));
                }
            } else if (alias.equalsIgnoreCase("delhome")) {
                if (args.length == 1) {
                    l.addAll(HomePlugin.homes.getConfigurationSection("Homes." + p.getUniqueId().toString()).getKeys(false));
                }
            }
        }
        return l;
    }
}
