package homes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MendingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!Events.villager_pending.contains(p)) {
                Events.villager_pending.add(p);
                p.sendMessage(ChatColor.GREEN + "Please rightclick a Villager!");
            }else{
                Events.villager_pending.remove(p);
                Events.villager_mending.remove(p);
                p.sendMessage(ChatColor.RED + "Unregistered");
            }
        }

        return true;
    }
}
