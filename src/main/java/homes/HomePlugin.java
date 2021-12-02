package homes;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class HomePlugin extends JavaPlugin {

    public static YamlConfiguration homes;
    public static Logger logger;
    public static Plugin plugin;
    public static File folder = new File("plugins//HomePlugin");
    public static File home_file = new File("plugins//HomePlugin//", "homes.cfg");


    @Override
    public void onEnable() {
        plugin = this;
        logger = Bukkit.getLogger();
        folder.mkdirs();

        if (!home_file.exists()) {
            try {
                home_file.createNewFile();
                logger.info("Created homes.cfg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        homes = YamlConfiguration.loadConfiguration(home_file);

        logger.info("Config loaded");

        Bukkit.getPluginManager().registerEvents(new Events(), this);

        getCommand("mending").setExecutor(new MendingCommand());

        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("sethome").setTabCompleter(new HomeTC());

        getCommand("delhome").setExecutor(new DelHomeCommand());
        getCommand("delhome").setTabCompleter(new HomeTC());

        getCommand("home").setExecutor(new HomeCommand());
        getCommand("home").setTabCompleter(new HomeTC());

        getCommand("homes").setExecutor(new HomesCommand());
        getCommand("homes").setTabCompleter(new HomeTC());
    }

    @Override
    public void onDisable() {

    }
}
