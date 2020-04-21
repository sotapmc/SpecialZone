package org.sotap.SpecialZone;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class States {
    public static Integer selectState = 0;
    public static List<Location> locations;

    public States() {
        locations = new ArrayList<Location>();
        locations.add(0, null);
        locations.add(1, null);
        locations.add(2, null);
    }

    public void nextSelectState() {
        if (selectState == 2) {
            this.resetSelectState();
        } else {
            selectState += 1;
        }
    }

    public void resetSelectState() {
        selectState = 1;
        locations.clear();
        // add placeholders to prevent nullpointerexception
        locations.add(0, null);
        locations.add(1, null);
        locations.add(2, null);
    }

    public void storeLocation(Location location) {
        locations.add(selectState, location);
    }

    public Location getLocation(Integer selectState) {
        try {
            return locations.get(selectState);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}