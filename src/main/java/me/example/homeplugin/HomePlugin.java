package me.example.homeplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HomePlugin extends JavaPlugin {

    private FileConfiguration config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player player)) return true;

        String uuid = player.getUniqueId().toString();

        if (cmd.getName().equalsIgnoreCase("sethome")) {

            config.set(uuid + ".world", player.getWorld().getName());
            config.set(uuid + ".x", player.getLocation().getX());
            config.set(uuid + ".y", player.getLocation().getY());
            config.set(uuid + ".z", player.getLocation().getZ());

            saveConfig();

            player.sendMessage("§aHome set!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("home")) {

            if (!config.contains(uuid + ".world")) {
                player.sendMessage("§cNo home set!");
                return true;
            }

            World world = Bukkit.getWorld(config.getString(uuid + ".world"));
            double x = config.getDouble(uuid + ".x");
            double y = config.getDouble(uuid + ".y");
            double z = config.getDouble(uuid + ".z");

            player.teleport(new Location(world, x, y, z));

            player.sendMessage("§aTeleported home!");
            return true;
        }

        return false;
    }
}
