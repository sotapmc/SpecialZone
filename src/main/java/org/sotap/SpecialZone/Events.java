package org.sotap.SpecialZone;

import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;

public class Events implements Listener {
	private SpecialZone plug;

	public Events(SpecialZone plug) {
		this.plug = plug;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		// get specialzone location configuration
		Player p = event.getEntity();
		double x = p.getLocation().getX();
		double y = p.getLocation().getY();
		double z = p.getLocation().getZ();
		// the ignore_y argument will be configurable very soon.
		if (Utils.isInZoneGlobal(x, y, z, false, this.plug.getConfig())) {
			event.setKeepInventory(true);
			event.setKeepLevel(true);
			event.getDrops().clear();
			event.getEntity().sendMessage("Your inventory was kept up!");
		} else {
			event.setKeepInventory(false);
			event.setKeepLevel(false);
			// only for test, will be removed in production.
			this.plug.getLogger().info("Not in zone");
		}
	}
}