package org.sotap.SpecialZone;

import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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
			//do not forget clear exp which can not be clear by getDrops().clear()
			event.getDrops().clear();
			event.setDroppedExp(0);
			event.getEntity().sendMessage("Your inventory was kept up!");
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getHand().name().equals("HAND")){
			Action action = event.getAction();
			ItemStack item = event.getItem();
			Material mat =item.getType();
		if (item == null) {
			// do nothing
		} else {
			if (action == Action.RIGHT_CLICK_BLOCK && mat == Material.ARROW) {
				this.state.nextSelectState();
				Block block = event.getClickedBlock();
				Location location = block.getLocation();
				this.state.storeLocation(location);
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', ("[&bACTION&r] Successfully select the &a" + (States.selectState == 1 ? "first" : "second") + "&r point of the zone." + (States.selectState == 2 ? " &aEnter &e/specialzone create <name> <ignoreY?>&a to create." : ""))));
			} else {
				this.state.resetSelectState();
			}
		}
	}
}
}