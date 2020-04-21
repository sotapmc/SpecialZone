package org.sotap.SpecialZone;

import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Events implements Listener {
	private SpecialZone plug;
	private States state;

	public Events(SpecialZone plug, States state) {
		this.plug = plug;
		this.state = state;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		// get specialzone location configuration
		Player p = event.getEntity();
		double x = p.getLocation().getX();
		double y = p.getLocation().getY();
		double z = p.getLocation().getZ();
		if (Utils.isInZoneGlobal(x, y, z, this.plug.getConfig())) {
			event.setKeepInventory(true);
			event.setKeepLevel(true);
			event.getDrops().clear();
			event.getEntity().sendMessage("Your inventory was kept up!");
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Action action = event.getAction();
		if (action == Action.RIGHT_CLICK_BLOCK) {
			this.state.nextSelectState();
			Block block = event.getClickedBlock();
			Location location = block.getLocation();
			this.state.storeLocation(States.selectState, location);
		} else {
			this.state.resetSelectState();
		}
	}
}