package org.sotap.SpecialZone;

import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

/// tip: The quotes in this file will be written in Chinese, because they are usually long and hard to understand in English.

public class Utils {
    /**
     * 判断指定坐标是否位于指定的 Zone 范围内。
     * 
     * @param zonename  需要判断的 Zone 名称
     * @param u_x       需要进行判断的坐标 X
     * @param u_y       需要进行判断的坐标 Y
     * @param u_z       需要进行判断的坐标 Z
     * @param worldname 需要判断的 Zone 所在的世界名称
     * @param ignore_Y  是否忽略 Y 坐标进行判断
     * @param config    插件的配置对象
     * @return boolean
     */
    public static boolean isInZone(String zonename, double u_x, double u_y, double u_z, String worldname, boolean ignore_Y, FileConfiguration config) {
        ConfigurationSection sp_main = config.getConfigurationSection("SpecialZone");
        ConfigurationSection sp_zone = sp_main.getConfigurationSection(zonename);
        Double x1 = sp_zone.getDouble("x1");
        Double x2 = sp_zone.getDouble("x2");
        Double y1 = sp_zone.getDouble("y1");
        Double y2 = sp_zone.getDouble("y2");
        Double z1 = sp_zone.getDouble("z1");
        Double z2 = sp_zone.getDouble("z2");
        String zone_world = sp_zone.getString("world_name");
        return (x1 - u_x) * (x2 - u_x) < 0 && (ignore_Y ? true : (y1 - u_y) * (y2 - u_y) < 0)
                && (z1 - u_z) * (z2 - u_z) < 0 && worldname.equalsIgnoreCase(zone_world);
    }

    /**
     * 判断指定坐标是否被包含在任意一个 Zone 范围内
     * 
     * @param u_x      需要进行判断的坐标 X
     * @param u_y      需要进行判断的坐标 Y
     * @param u_z      需要进行判断的坐标 Z
     * @param ignore_Y 是否忽略 Y 坐标进行判断
     * @param config   插件的配置对象
     * @return boolean
     */
    public static boolean isInZoneGlobal(double u_x, double u_y, double u_z, boolean ignore_Y, FileConfiguration config) {
        ConfigurationSection specialZone = config.getConfigurationSection("SpecialZone");
        Set<String> zoneNames = specialZone.getKeys(false);
        for (String key : zoneNames) {
            ConfigurationSection zone = specialZone.getConfigurationSection(key);
            Double x1 = zone.getDouble("x1");
            Double x2 = zone.getDouble("x2");
            Double y1 = zone.getDouble("y1");
            Double y2 = zone.getDouble("y2");
            Double z1 = zone.getDouble("z1");
            Double z2 = zone.getDouble("z2");
            return (x1 - u_x) * (x2 - u_x) < 0 && (ignore_Y ? true : (y1 - u_y) * (y2 - u_y) < 0)
            && (z1 - u_z) * (z2 - u_z) < 0;
        }
        return false;
    }
}