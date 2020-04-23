package org.sotap.SpecialZone;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class SpecialZone extends JavaPlugin {
    public CommandHandler executor;

    public void log(String msg) {
        this.getLogger().info(ChatColor.translateAlternateColorCodes('&', msg));
    }

	@Override  
    public void onEnable(){
        this.saveDefaultConfig();
        this.getLogger().info("SpecialZone has been enabled successfully.");
        getServer().getPluginManager().registerEvents(new Events(this, new States()), this); // register events
        Bukkit.getPluginCommand("specialzone").setExecutor(new CommandHandler(this)); // register command
    }  
  
    @Override      
    public void onDisable(){
        this.getLogger().info("SpecialZone has been disabled successfully.");
    }
}  