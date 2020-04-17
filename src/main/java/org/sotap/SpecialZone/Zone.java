package org.sotap.SpecialZone;

import org.bukkit.entity.Player;

public class Zone {
    private SpecialZone plug;
    public String worldname, zonename, error;
    public double x1, x2, y1, y2, z1, z2;
    public boolean ignore_Y, keepInv, keepExp;

    public Zone(String[] args, SpecialZone plug) {
        this.zonename = args[0];
        this.x1 = Integer.valueOf(args[1]);
        this.x2 = Integer.valueOf(args[2]);
        this.y1 = Integer.valueOf(args[3]);
        this.y2 = Integer.valueOf(args[4]);
        this.z1 = Integer.valueOf(args[5]);
        this.z2 = Integer.valueOf(args[6]);
        this.worldname = args[7];
        // ignore_Y is true when you want to check if a player is in this specialzone
        // without checking his Y coordinate.
        this.ignore_Y = false;
        if (args[8].equalsIgnoreCase("true")) {
            this.ignore_Y = true;
            this.plug = plug;
        }

        this.keepInv = false;
        this.keepExp = false;
    }

    /**
     * Constructor for a new zone
     */
    public boolean create() {
        // This won't be removed until we supported zones with the same name but different world_name attributes in the config.yml
        if (plug.getConfig().contains("SpecialZone." + this.zonename)) {
            this.error = "There is already a special zone named " + this.zonename + ", we won't do anything now.";
            return false;
        }
        plug.getConfig().set("SpecialZone." + this.zonename + ".x1", this.x1);
        plug.getConfig().set("SpecialZone." + this.zonename + ".x2", this.x2);
        plug.getConfig().set("SpecialZone." + this.zonename + ".y1", this.y1);
        plug.getConfig().set("SpecialZone." + this.zonename + ".y2", this.y2);
        plug.getConfig().set("SpecialZone." + this.zonename + ".z1", this.z1);
        plug.getConfig().set("SpecialZone." + this.zonename + ".z2", this.z2);
        plug.getConfig().set("SpecialZone." + this.zonename + ".world_name", this.worldname);
        plug.getConfig().set("SpecialZone." + this.zonename + ".ignore_Y", this.ignore_Y);
        // don't forget to save the configuration
        plug.saveConfig();
        return true;
    }

    /**
     * Check if a player is in `this` zone
     */
    public boolean isInZone(Player p) {
        return Utils.isInZone(this.zonename, p.getLocation().getX() , p.getLocation().getY(), p.getLocation().getZ(), this.worldname, this.ignore_Y, this.plug.getConfig());
    }

    public void setKeepInv(boolean flag) {
        this.keepInv = flag;
    }

    public boolean getKeepInv() {
        return this.keepInv;
    }

    public void setKeepExp(boolean flag) {
        this.keepInv = flag;
    }

    public boolean getKeepExp() {
        return this.keepExp;
    }
}
