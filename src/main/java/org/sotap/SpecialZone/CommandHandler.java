
package org.sotap.SpecialZone;

import java.math.BigDecimal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {
	private SpecialZone plug;

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

	public boolean validate(String[] args, CommandSender sender) {
		// check if there are enough arguments
		if (args.length != 6) {
			sender.sendMessage("[FAILED] Unexpected argument length, expected 6 but got " + Integer.toString(args.length) + ".");
			return false;
		}
		// check if the argument types are correct
		for (int i = 1; i <= 4; i++) {
			String arg = args[i];
			if (!this.isNumeric(arg)) {
				sender.sendMessage("[FAILED] Argument type incorrect, expected Number but got String.");
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		// both player and console can use the command

		if (cmd.getName().equalsIgnoreCase("setspecialzone")) {

			if (!this.validate(args, sender)) {
				return false;
			}

			// usage: /setSpecialZone <zonename> <x1> <x2> <y1> <y2> <world_name>

			// it will automatically create the value path if not exists, so there is no
			// need to create it manually.
			this.plug.getConfig().set("SpecialZone." + args[0] + ".x1", args[1]);
			this.plug.getConfig().set("SpecialZone." + args[0] + ".x2", args[2]);
			this.plug.getConfig().set("SpecialZone." + args[0] + ".y1", args[3]);
			this.plug.getConfig().set("SpecialZone." + args[0] + ".y2", args[4]);
			this.plug.getConfig().set("SpecialZone." + args[0] + ".world_name", args[5]);
			// don't forget to save the configuration
			this.plug.saveConfig();
			sender.sendMessage("[SUCCESS] Successfully set a special zone.");
			return true;
		}

		return true;
	}
}