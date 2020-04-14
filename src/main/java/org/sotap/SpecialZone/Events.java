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

	public boolean isInSpecialZone(double x1, double x2, double y1, double y2, String worldname, PlayerDeathEvent e) {
		// get player death location
		double x = e.getEntity().getLocation().getX();
		double y = e.getEntity().getLocation().getY();
		// get player death world
		String c_worldname = e.getEntity().getLocation().getWorld().getName();

		return (x - x1) * (x - x2) < 0 && (y - y1) * (y - y2) < 0 && c_worldname.equalsIgnoreCase(worldname);
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
			double compare_y1 = Double.parseDouble(zone.getString("y1"));
			double compare_y2 = Double.parseDouble(zone.getString("y2"));
			String compare_worldname = zone.getString("world_name");
			if (this.isInSpecialZone(compare_x1, compare_x2, compare_y1, compare_y2, compare_worldname, event)) {
				event.setKeepInventory(true);
				event.getEntity().sendMessage("Your inventory was kept up!");
				break;
			}
		}
	}
}
