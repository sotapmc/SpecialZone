package org.sotap.SpecialZone;

import java.util.List;

import org.bukkit.Location;

public class States {
    public static String selectState = "";
    public static List<Location> locations;

    private Integer getIndexBySelection(String selectState) {
        return selectState == "selection-1" ? 1 : (selectState == "selection-2" ? 2 : 0);
    }

    public void nextSelectState() {
        String selectStatePrev = States.selectState;
        if (selectStatePrev == "selection-2") {
            this.resetSelectState();
        }
        selectState = selectStatePrev == "" ? "selection-1" : (selectStatePrev == "selection-1" ? "selection-2" : "");    
    }

    public void resetSelectState() {
        selectState = "";
        locations.clear();
    }

    public void storeLocation(String selectState, Location location) {
        locations.set(this.getIndexBySelection(selectState), location);
    }

    public Location getLocation(String selectState) {
        try {
            return locations.get(this.getIndexBySelection(selectState));
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}