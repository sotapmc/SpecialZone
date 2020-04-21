
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
		if (cmd.getName().equalsIgnoreCase("specialzone")) {
			if (args.length != 3) {
				return false;
			}

			switch (args[0]) {
				case "create":
					if (!(States.locations.get(1) instanceof Location && States.locations.get(2) instanceof Location)) {
						this.send("[&cFAILED&r] You are not selecting the zone correctly.");
					}
					Zone newzone = new Zone(args[1], this.sender.getWorld().getName(), States.locations,
							Boolean.parseBoolean(args[2]), this.plug);
					if (newzone.create()) {
						this.send("[&aSUCCESS&r] Successfully set a special zone.");
					} else {
						this.send("[&cFAILED&r] " + newzone.error);
					}
					break;

				case "reload":
					this.plug.reloadConfig();
					break;

				default:
					return false;
			}

			return true;
		}

		return true;
	}
}