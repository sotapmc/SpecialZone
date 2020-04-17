
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

	public boolean valiDateLength(String[] args, CommandSender sender,Integer n) {
		// check if there are enough arguments
		if (args.length != n) {
			sender.sendMessage("[FAILED] Unexpected argument length, expected" + n.toString() + "but got " + Integer.toString(args.length) + ".");
			return false;
		}
		return true;
	}
	public boolean isNumericBetween(String[] args,CommandSender sender,Integer j,Integer k){
		// check if the argument types are correct
		for (int i = j; i <= k; i++) {
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

			if (!this.valiDateLength(args, sender,9)) {
				return false;
			}
			
			if (!this.isNumericBetween(args,sender,1,6)){
				return false;
			}
			// usage: /setSpecialZone <zonename> <x1> <x2> <z1> <z2> <world_name>

			// it will automatically create the value path if not exists, so there is no
			// need to create it manually.
			zone newzone=new zone(args,plug);
			newzone.setASpecialZone();
			// don't forget to save the configuration
			this.plug.saveConfig();
			sender.sendMessage("[SUCCESS] Successfully set a special zone.");
			return true;
		}

		return true;
	}
}	