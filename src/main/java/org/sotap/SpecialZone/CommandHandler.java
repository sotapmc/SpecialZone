
package org.sotap.SpecialZone;

import java.math.BigDecimal;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {
	private SpecialZone plug;
	private CommandSender sender;

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

	public boolean validateLength(String[] args, CommandSender sender, Integer n) {
		// check if there are enough arguments
		if (args.length != n) {
			this.send("[&cFAILED&r] Unexpected argument length, expected " + n.toString() + " but got "
					+ Integer.toString(args.length) + ".");
			return false;
		}
		return true;
	}

	public boolean isNumericBetween(String[] args, CommandSender sender, Integer j, Integer k) {
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
		this.sender = sender;
		// both player and console can use the command

		if (cmd.getName().equalsIgnoreCase("setspecialzone")) {
			// prevent out of bounds exception
			if (args.length == 0) {
				return false;
			}
		
			if (args[0] == "reload") {
				this.plug.reloadConfig();
				return true;
			}

			if (!this.validateLength(args, sender, 9)) {
				return false;
			}

			if (!this.isNumericBetween(args, sender, 1, 6)) {
				return false;
			}
			// usage: /setSpecialZone <zonename> <x1> <x2> <z1> <z2> <world_name>

			// it will automatically create the value path if not exists, so there is no
			// need to create it manually.
			Zone newzone = new Zone(args, this.plug);
			if(newzone.create()) {
				this.send("[&aSUCCESS&r] Successfully set a special zone.");
			} else {
				this.send("[&cFAILED&r] Unable to create a special zone. Reason: " + newzone.error + ".");
			}
			
			return true;
		}

		return true;
	}
}