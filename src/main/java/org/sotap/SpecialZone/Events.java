package org.sotap.SpecialZone;

import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Set;

public class Events implements Listener {
	private SpecialZone plug;

	public Events(SpecialZone plug) {
		this.plug = plug;
	}

	public boolean isInSpecialZone(double x1, double x2, double z1, double z2, String worldname, PlayerDeathEvent e) {
		// get player death location
		double x = e.getEntity().getLocation().getX();
		double z = e.getEntity().getLocation().getZ();
		// get player death world
		String c_worldname = e.getEntity().getLocation().getWorld().getName();

		return (x - x1) * (x - x2) < 0 && (z - z1) * (z - z2) < 0 && c_worldname.equalsIgnoreCase(worldname);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		// get specialzone location configuration
		ConfigurationSection specialZone = this.plug.getConfig().getConfigurationSection("SpecialZone");
		Set<String> zoneNames = specialZone.getKeys(false);
		for (String key : zoneNames) {
			ConfigurationSection zone = specialZone.getConfigurationSection(key);
			double compare_x1 = Double.parseDouble(zone.getString("x1"));
			double compare_x2 = Double.parseDouble(zone.getString("x2"));
			double compare_z1 = Double.parseDouble(zone.getString("z1"));
			double compare_z2 = Double.parseDouble(zone.getString("z2"));
			String compare_worldname = zone.getString("world_name");
			if (this.isInSpecialZone(compare_x1, compare_x2, compare_z1, compare_z2, compare_worldname, event)) {
				event.setKeepInventory(true);
				event.getEntity().sendMessage("Your inventory was kept up!");
				break;
			}
		}
	}
}
