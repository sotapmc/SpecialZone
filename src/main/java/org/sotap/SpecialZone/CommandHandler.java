
package org.sotap.SpecialZone;

import java.math.BigDecimal;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
	private SpecialZone plug;
	private Player sender;

	public CommandHandler(SpecialZone plug) {
		this.plug = plug;
	}

	// is numeric string?
	public boolean isNumeric(String arg) {
		@SuppressWarnings("unused")
		String test;
		try {
			test = new BigDecimal(arg).toString();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean validateLength(String[] args, Integer n) {
		// check if there are enough arguments
		if (args.length != n) {
			this.send("[&cFAILED&r] Unexpected argument length, expected " + n.toString() + " but got "
					+ Integer.toString(args.length) + ".");
			return false;
		}
		return true;
	}

	public boolean isNumericBetween(String[] args, Integer j, Integer k) {
		// check if the argument types are correct
		for (int i = j; i <= k; i++) {
			String arg = args[i];
			if (!this.isNumeric(arg)) {
				this.send("[&cFAILED&r] Argument type incorrect, expected Number but got String.");
				return false;
			}
		}
		return true;
	}
	
	public void send(String msg) {
		this.sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		this.sender = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("setspecialzone")) {	
			if (args[0] == "reload") {
				
			}

			if (args.length != 2 && args[0] != "reload") {
				return false;
			} else if (args[0] == "reload") {
				this.plug.reloadConfig();
				return true;
			} else {
				if (!(States.locations.get(1) instanceof Location && States.locations.get(2) instanceof Location)) {
					this.send("[&cFAILED&r] You are not selecting the zone correctly.");
				}
				// usage: /setSpecialZone <zonename> <ignore_Y>
				Zone newzone = new Zone(args[0], this.sender.getWorld().getName(), States.locations, Boolean.parseBoolean(args[1]), this.plug);
				
				if (newzone.create()) {
					this.send("[&aSUCCESS&r] Successfully set a special zone.");
				} else {
					this.send("[&cFAILED&r] Unable to create a special zone. Reason: " + newzone.error + ".");
				}
			}
			return true;
		}

		return true;
	}
}