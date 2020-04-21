package org.sotap.SpecialZone;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Zone {
    private SpecialZone plug;
    public String worldname, zonename, error;
    public boolean ignore_Y, keepInv, keepExp;
    public double x1, x2, y1, y2, z1, z2;

    public Zone(String zonename, String worldname, List<Location> locations, boolean ignore_Y, SpecialZone plug) {
        this.zonename = zonename;
        Location loc1 = locations.get(1);
        Location loc2 = locations.get(2);
        this.x1 = loc1.getX();
        this.x2 = loc2.getX();
        this.y1 = loc1.getY();
        this.y2 = loc2.getY();
        this.z1 = loc1.getZ();
        this.z2 = loc2.getZ();
        this.worldname = worldname;
        // ignore_Y is true when you want to check if a player is in this specialzone
        // without checking his Y coordinate.
        this.ignore_Y = ignore_Y;
        this.keepInv = false;
        this.keepExp = false;
        this.plug = plug;
    }

    /**
     * Constructor for a new zone
     */
    public boolean create() {
        // This won't be removed until we supported zones with the same name but different world_name attributes in the config.yml
        if (this.plug.getConfig().contains("SpecialZone." + this.zonename)) {
            this.error = "There is already a special zone named " + this.zonename + ", we won't do anything now.";
            return false;
        }
        this.plug.getConfig().set("SpecialZone." + this.zonename + ".x1", this.x1);
        this.plug.getConfig().set("SpecialZone." + this.zonename + ".x2", this.x2);
        this.plug.getConfig().set("SpecialZone." + this.zonename + ".y1", this.y1);
        this.plug.getConfig().set("SpecialZone." + this.zonename + ".y2", this.y2);
        this.plug.getConfig().set("SpecialZone." + this.zonename + ".z1", this.z1);
        this.plug.getConfig().set("SpecialZone." + this.zonename + ".z2", this.z2);
        this.plug.getConfig().set("SpecialZone." + this.zonename + ".world_name", this.worldname);
        this.plug.getConfig().set("SpecialZone." + this.zonename + ".ignore_Y", this.ignore_Y);
        // don't forget to save the configuration
        this.plug.saveConfig();
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
