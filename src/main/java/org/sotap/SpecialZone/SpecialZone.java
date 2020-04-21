package org.sotap.SpecialZone;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SpecialZone extends JavaPlugin {
    public CommandHandler executor;

	@Override  
    public void onEnable(){
        this.saveDefaultConfig();
        this.getLogger().info("SpecialZone has been enabled successfully.");
        getServer().getPluginManager().registerEvents(new Events(this, new States()), this); // register events
        Bukkit.getPluginCommand("setspecialzone").setExecutor(new CommandHandler(this)); // register command
    }  
  
    @Override      
    public void onDisable(){
        this.getLogger().info("SpecialZone has been disabled successfully.");
    }
}  